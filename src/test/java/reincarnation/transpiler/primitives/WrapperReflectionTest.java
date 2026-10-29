/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.primitives;

import org.junit.jupiter.api.Test;

import reincarnation.transpiler.BundleVerifier;

/**
 * Test the reflection of the primitive wrappers.
 */
class WrapperReflectionTest extends BundleVerifier {

    @Test
    void type() throws Exception {
        verifyReflection(Type.class, "run");
    }

    @Test
    void isInstance() throws Exception {
        verifyReflection(IsInstance.class, "run");
    }

    /** Verify the wrapper class. */
    static class Type {

        static String run() {
            java.lang.StringBuilder builder = new java.lang.StringBuilder(Integer.class.getName());
            builder.append("|").append(Integer.class.getSuperclass().getName());
            builder.append("|").append(Integer.TYPE == int.class);
            builder.append("|").append(Integer.class.isInterface());
            return builder.toString();
        }
    }

    /** Verify the wrapper instance check. */
    static class IsInstance {

        static String run() {
            return (Integer.class.isInstance(1) ? "T" : "F") + (Integer.class.isInstance(1L) ? "T" : "F")
                    + (Long.class.isInstance(1L) ? "T" : "F") + (Long.class.isInstance(1) ? "T" : "F")
                    + (Character.class.isInstance('a') ? "T" : "F") + (Character.class.isInstance("ab") ? "T" : "F")
                    + (Number.class.isAssignableFrom(Integer.class) ? "T" : "F")
                    + (Integer.class.isAssignableFrom(Number.class) ? "T" : "F");
        }
    }
}
