/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.field;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.TestCode.Param;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for static field and static method operations.
 * 
 * @version 2026/10/11 11:30:00
 */
class StaticFieldTest extends JavascriptCodeVerifier {

    @Test
    void staticField() throws Exception {
        verify(new Counter());
    }

    @Test
    void staticMethod() throws Exception {
        verify(new Twice());
    }

    @Test
    void staticFieldAndMethod() throws Exception {
        verify(new Both());
    }

    /** The counter test. */
    static class Counter implements TestCode.Int {

        /** The static field. */
        static int base = 2;

        @Override
        public int run() {
            return base + 1;
        }
    }

    /** The static method test. */
    static class Twice implements TestCode.IntParam {

        @Override
        public int run(@Param(ints = {-5, 0, 3}) int value) {
            return twice(value);
        }

        static int twice(int value) {
            return value * 2;
        }
    }

    /** The combined test. */
    static class Both implements TestCode.Int {

        /** The static field. */
        static int base = 3;

        @Override
        public int run() {
            return helper() + twice(base);
        }

        int helper() {
            return 1;
        }

        static int twice(int value) {
            return value * 2;
        }
    }
}