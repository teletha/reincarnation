/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.method;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.TestCode.Param;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for static method calls and method invocation chains.
 * 
 * @version 2026/10/11 12:00:00
 */
class StaticMethodTest extends JavascriptCodeVerifier {

    @Test
    void mathAbs() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(ints = {-10, -5, 0, 3}) int value) {
                return Math.abs(value);
            }
        });
    }

    @Test
    void mathMax() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(ints = {-10, 0, 10}) int value) {
                return Math.max(value, 5);
            }
        });
    }

    @Test
    void mathMin() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(ints = {-10, 0, 10}) int value) {
                return Math.min(value, 10);
            }
        });
    }

    @Test
    void mathFloor() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                return (int) Math.floor(value / 2.0);
            }
        });
    }

    @Test
    void instanceCall() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                return helper(value) + 1;
            }

            private int helper(int value) {
                return value * 2;
            }
        });
    }
}