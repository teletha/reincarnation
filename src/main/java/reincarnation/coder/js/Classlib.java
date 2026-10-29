/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder.js;

import java.util.Map;

/**
 * The mapping from the Java standard library to the Java implementation which is transpiled into
 * JavaScript.
 * <p>
 * The primitive operations are provided by {@link JavascriptRuntime}, but the API level
 * classes are implemented in Java and included in the dependency closure so that the transpiler
 * itself verifies their translation.
 * </p>
 */
final class Classlib {

    /** The replacement table. */
    private static final Map<Class, Class> replacements = Map
            .of(java.util.stream.Collectors.class, reincarnation.classlib.java.util.stream.Collectors.class);

    /**
     * Hide constructor.
     */
    private Classlib() {
    }

    /**
     * Replace the specified class with the Java implementation which is transpiled into JavaScript.
     * 
     * @param type A target class.
     * @return A replacement class or the given class.
     */
    static Class replace(Class type) {
        return replacements.getOrDefault(type, type);
    }

    /**
     * Check whether the specified class has the Java implementation.
     * 
     * @param type A target class.
     * @return A result.
     */
    static boolean isReplaced(Class type) {
        return replacements.containsKey(type);
    }
}
