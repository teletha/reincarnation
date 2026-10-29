/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.js;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the symbol allocation for the overloaded methods and the hidden fields.
 */
class OverloadTest extends JavascriptCodeVerifier {

    @Test
    void staticOverload() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return Overloaded.f(1) * 10 + Overloaded.f("x");
            }
        });
    }

    @Test
    void instanceOverload() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Overloaded instance = new Overloaded();
                return instance.g(2) * 10 + instance.g("y");
            }
        });
    }

    @Test
    void overrideAndOverload() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Base base = new Sub();
                return base.f("x").length() * 10 + ((Sub) base).f(3);
            }
        });
    }

    @Test
    void fieldHiding() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                FieldSub value = new FieldSub();
                return ((FieldBase) value).x * 10 + value.x;
            }
        });
    }

    @Test
    void classlibCollision() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                StringBuilder builder = new StringBuilder();
                builder.append(new Appender().append("a"));
                return builder.toString();
            }
        });
    }

    /** The overloaded methods. */
    public static class Overloaded {

        public static int f(int value) {
            return 1;
        }

        public static int f(String value) {
            return 2;
        }

        public int g(long value) {
            return 3;
        }

        public int g(Object value) {
            return 4;
        }
    }

    /** The appender. */
    public static class Appender {

        public String append(String value) {
            return value + "!";
        }
    }

    /** The base class. */
    public static class Base {

        public String f(String value) {
            return "base";
        }
    }

    /** The derived class. */
    public static class Sub extends Base {

        public int f(int value) {
            return 7;
        }
    }

    /** The base field. */
    public static class FieldBase {

        public int x = 1;
    }

    /** The derived field. */
    public static class FieldSub extends FieldBase {

        public int x = 2;
    }
}
