/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.bundle;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import reincarnation.transpiler.BundleVerifier;

/**
 * Test the reflection support for the overloaded methods and the hidden fields.
 */
class OverloadReflectionTest extends BundleVerifier {

    @Test
    void method() throws Exception {
        verifyReflection(MethodEntry.class, "run");
    }

    @Test
    void field() throws Exception {
        verifyReflection(FieldEntry.class, "run");
    }

    @Test
    void baseField() throws Exception {
        verifyReflection(BaseFieldEntry.class, "run");
    }

    /** The overloaded methods. */
    public static class Overloaded {

        public static int f(int value) {
            return 1;
        }

        public static int f(String value) {
            return 2;
        }
    }

    /** The base field. */
    public static class Base {

        public int x = 1;
    }

    /** The derived field. */
    public static class Sub extends Base {

        public int x = 2;
    }

    /** Verify the overloaded method lookup. */
    static class MethodEntry {

        static int run() throws Exception {
            Method integer = Overloaded.class.getDeclaredMethod("f", int.class);
            Method string = Overloaded.class.getDeclaredMethod("f", String.class);
            return (int) integer.invoke(null, 1) * 10 + (int) string.invoke(null, "x");
        }
    }

    /** Verify the hidden field lookup. */
    static class FieldEntry {

        static int run() throws Exception {
            Field field = Sub.class.getDeclaredField("x");
            Sub value = new Sub();
            return (int) field.get(value);
        }
    }

    /** Verify the base field lookup. */
    static class BaseFieldEntry {

        static int run() throws Exception {
            Field field = Base.class.getDeclaredField("x");
            Sub value = new Sub();
            return (int) field.get(value);
        }
    }
}
