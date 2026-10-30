/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler;

import java.io.File;
import java.net.URL;

import com.caoccao.javet.exceptions.JavetException;
import com.caoccao.javet.interop.V8Host;
import com.caoccao.javet.interop.V8Runtime;
import com.caoccao.javet.interop.options.NodeRuntimeOptions;

/**
 * Provides the i18n-enabled Javet runtime used by the JavaScript verification.
 * <p>
 * The ICU data directory is resolved from the {@code javet.icu.dir} system property, the
 * {@code JAVET_ICU_DIR} environment variable, or the bundled {@code icu/icudt78l.dat} test
 * resource. It must be configured before the first runtime is created.
 * </p>
 * <p>
 * A DOM implementation can be installed into the runtime through {@link #createWithDOM()}. The
 * implementation is resolved from the {@code reincarnation.dom} system property, the
 * {@code REINCARNATION_DOM} environment variable, or the local {@code node_modules/happy-dom}
 * directory. It is installed only on demand so the ordinary runtime keeps the browser-less
 * fallbacks.
 * </p>
 */
final class JavetSupport {

    /** The system property for the DOM implementation. */
    private static final String DOM_PROPERTY = "reincarnation.dom";

    /** The environment variable for the DOM implementation. */
    private static final String DOM_ENVIRONMENT = "REINCARNATION_DOM";

    /** The default DOM implementation. */
    private static final String DEFAULT_DOM = "happy-dom";

    /** The resolved DOM implementation or null. */
    private static final String dom = resolveDOM();

    /** The availability of the i18n runtime. */
    private static final boolean available = load();

    /**
     * Hide constructor.
     */
    private JavetSupport() {
    }

    /**
     * Check whether the i18n runtime is available.
     * 
     * @return A result.
     */
    static boolean available() {
        return available;
    }

    /**
     * Check whether the DOM implementation is available.
     * 
     * @return A result.
     */
    static boolean domAvailable() {
        return available && dom != null;
    }

    /**
     * Create the runtime.
     * 
     * @return A runtime.
     */
    static V8Runtime create() throws JavetException {
        return V8Host.getNodeI18nInstance().createV8Runtime();
    }

    /**
     * Create the runtime with the DOM implementation installed.
     * 
     * @return A runtime.
     * @throws JavetException If the runtime or the DOM implementation fails.
     */
    static V8Runtime createWithDOM() throws JavetException {
        if (dom == null) {
            throw new IllegalStateException("The DOM implementation is not available. Install happy-dom or set " + DOM_PROPERTY + ".");
        }
        V8Runtime runtime = create();
        try {
            runtime.getExecutor(bootstrap(dom)).executeVoid();
            return runtime;
        } catch (JavetException | RuntimeException e) {
            try {
                runtime.close();
            } catch (JavetException ignored) {
            }
            throw e;
        }
    }

    /**
     * Resolve the DOM implementation.
     * 
     * @return A module specifier or null.
     */
    private static String resolveDOM() {
        String module = System.getProperty(DOM_PROPERTY);
        if (module == null || module.isBlank()) {
            module = System.getenv(DOM_ENVIRONMENT);
        }
        if (module != null && !module.isBlank()) {
            return exists(module) ? module.replace('\\', '/') : null;
        }
        return exists(DEFAULT_DOM) ? DEFAULT_DOM : null;
    }

    /**
     * Check whether the DOM implementation exists.
     * 
     * @param module A module specifier or path.
     * @return A result.
     */
    private static boolean exists(String module) {
        File file = new File(module);
        if (file.isAbsolute() || module.indexOf('/') >= 0 || module.indexOf('\\') >= 0) {
            return new File(file, "package.json").isFile() || file.isFile();
        }
        File installed = new File("node_modules", module);
        return new File(installed, "package.json").isFile() || installed.isDirectory();
    }

    /**
     * Create the DOM bootstrap script.
     * 
     * @param module A module specifier or path.
     * @return A script.
     */
    private static String bootstrap(String module) {
        return """
                (() => {
                    const vm = require('vm');
                    const runInContext = vm.Script.prototype.runInContext;
                    vm.Script.prototype.runInContext = function (context, options) {
                        try {
                            return runInContext.call(this, context, options);
                        } catch (error) {
                            // Javet exposes Infinity, NaN, and undefined as read-only globals, while
                            // happy-dom assigns them to the window context. The remaining globals are
                            // still installed, so only this assignment is ignored.
                            if (!/read only property/.test(String(error))) {
                                throw error;
                            }
                            return undefined;
                        }
                    };
                    const happy = require(%s);
                    const window = new happy.Window({url: 'http://localhost/'});
                    const names = ['Node', 'NodeList', 'HTMLCollection', 'Element', 'HTMLElement', 'Event',
                        'CustomEvent', 'EventTarget', 'DOMTokenList', 'DOMException', 'MutationObserver',
                        'MutationRecord', 'ResizeObserver', 'ResizeObserverEntry', 'DOMParser', 'XMLSerializer',
                        'getComputedStyle', 'navigator', 'location', 'history', 'localStorage', 'sessionStorage'];
                    globalThis.window = window;
                    globalThis.document = window.document;
                    for (const name of names) {
                        try {
                            if (window[name] !== undefined) {
                                globalThis[name] = window[name];
                            }
                        } catch (error) {
                        }
                    }
                })();
                """
                .formatted(quote(module));
    }

    /**
     * Quote the value as a JavaScript string literal.
     * 
     * @param value A value.
     * @return A literal.
     */
    private static String quote(String value) {
        return "'" + value.replace("\\", "\\\\").replace("'", "\\'") + "'";
    }

    /**
     * Load the i18n runtime.
     * 
     * @return A result.
     */
    private static boolean load() {
        try {
            String directory = System.getProperty("javet.icu.dir");
            if (directory == null) {
                directory = System.getenv("JAVET_ICU_DIR");
            }
            if (directory == null) {
                directory = resourceDirectory();
            }
            if (directory != null && !directory.isEmpty()) {
                NodeRuntimeOptions.NODE_FLAGS.setIcuDataDir(directory);
            }
            V8Host.getNodeI18nInstance();
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * Find the directory of the bundled ICU data.
     * 
     * @return A directory or null.
     */
    private static String resourceDirectory() {
        URL resource = JavetSupport.class.getResource("/icu/icudt78l.dat");
        if (resource == null) {
            return null;
        }
        try {
            return new File(resource.toURI()).getParent();
        } catch (Throwable e) {
            return null;
        }
    }
}
