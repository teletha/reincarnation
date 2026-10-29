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
 */
final class JavetSupport {

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
     * Create the runtime.
     * 
     * @return A runtime.
     */
    static V8Runtime create() throws JavetException {
        return V8Host.getNodeI18nInstance().createV8Runtime();
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
