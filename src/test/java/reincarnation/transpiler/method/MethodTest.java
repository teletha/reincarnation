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
 * Test the JavaScript output for method operations.
 * 
 * @version 2026/10/11 11:20:00
 */
class MethodTest extends JavascriptCodeVerifier {

    @Test
    void recursion() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(ints = {0, 1, 2, 3, 10}) int value) {
                return value <= 0 ? 0 : value + run(value - 1);
            }
        });
    }

    @Test
    void staticCall() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(ints = {-10, -5, -1, 0, 3}) int value) {
                return Math.abs(value);
            }
        });
    }

    @Test
    void ternary() throws Exception {
        verify(new TestCode.IntParamText() {

            @Override
            public String run(@Param(ints = {-10, -1, 0, 5}) int value) {
                return value < 0 ? "negative" : "positive";
            }
        });
    }

    @Test
    void methodChain() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(ints = {-5, 0, 5, 20}) int value) {
                return (value + 2) % 7;
            }
        });
    }

    @Test
    void resultOfLoop() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int total = 0;
                while (total < 5) {
                    total++;
                }
                return total;
            }
        });
    }

    @Test
    void conditionalStatement() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                if (Math.abs(-3) == 3) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }
}