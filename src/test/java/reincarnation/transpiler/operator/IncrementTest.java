/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.operator;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.TestCode.Param;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for increment, decrement and negative operators.
 * 
 * @version 2026/10/11 12:00:00
 */
class IncrementTest extends JavascriptCodeVerifier {

    @Test
    void postIncrement() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                return value++;
            }
        });
    }

    @Test
    void preIncrement() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                return ++value;
            }
        });
    }

    @Test
    void postDecrement() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                return value--;
            }
        });
    }

    @Test
    void sequence() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                value++;
                ++value;
                value--;
                return value;
            }
        });
    }

    @Test
    void incrementInExpression() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                return value++ + 10;
            }
        });
    }

    @Test
    void negative() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(ints = {-5, -1, 0, 3}) int value) {
                return -value;
            }
        });
    }
}