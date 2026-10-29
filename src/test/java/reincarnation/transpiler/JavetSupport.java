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

import com.caoccao.javet.exceptions.JavetException;
import com.caoccao.javet.interop.V8Host;
import com.caoccao.javet.interop.V8Runtime;
import com.caoccao.javet.interop.options.NodeRuntimeOptions;

/**
 * Provides the i18n-enabled Javet runtime used by the JavaScript verification.
 * <p>
 * The ICU data directory can be configured by the {@code javet.icu.dir} system property or the
 * {@code JAVET_ICU_DIR} environment variable. The i18n native library of Javet 6.0.2 contains the
 * ICU data, so the configuration is optional on the supported platforms.
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
            if (directory != null && !directory.isEmpty()) {
                NodeRuntimeOptions.NODE_FLAGS.setIcuDataDir(directory);
            }
            V8Host.getNodeI18nInstance();
            return true;
        } catch (Throwable e) {
            return false;
        }
    }
}
