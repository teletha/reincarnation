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
 * Test the JavaScript output for arithmetic operators.
 * 
 * @version 2026/10/11 12:00:00
 */
class ArithmeticOperatorTest extends JavascriptCodeVerifier {

    @Test
    void addition() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                return value + 1;
            }
        });
    }

    @Test
    void subtraction() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                return value - 1;
            }
        });
    }

    @Test
    void multiplication() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                return value * 3;
            }
        });
    }

    @Test
    void modulo() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -10, to = 10) int value) {
                return value % 5;
            }
        });
    }

    @Test
    void parentheses() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                return (value + 2) * 3;
            }
        });
    }

    @Test
    void mixed() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                return value + value * 2;
            }
        });
    }
}