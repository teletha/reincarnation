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
 * Test the JavaScript output for assignment operators.
 * 
 * @version 2026/10/11 12:00:00
 */
class AssignmentTest extends JavascriptCodeVerifier {

    @Test
    void plus() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                int result = 10;
                result += value;
                return result;
            }
        });
    }

    @Test
    void minus() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                int result = 10;
                result -= value;
                return result;
            }
        });
    }

    @Test
    void multiply() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                int result = 3;
                result *= value;
                return result;
            }
        });
    }

    @Test
    void modulo() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 1, to = 10) int value) {
                int result = 100;
                result %= value;
                return result;
            }
        });
    }

    @Test
    void shift() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                int result = 4;
                result <<= value;
                return result;
            }
        });
    }

    @Test
    void bitwise() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                int result = 7;
                result &= value;
                return result;
            }
        });
    }
}