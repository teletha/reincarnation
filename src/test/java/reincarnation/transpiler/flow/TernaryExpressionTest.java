/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.flow;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.TestCode.Param;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the ternary expression and logical expressions.
 * 
 * @version 2026/10/11 12:00:00
 */
class TernaryExpressionTest extends JavascriptCodeVerifier {

    @Test
    void intValue() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(ints = {-5, -1, 0, 3}) int value) {
                return value < 0 ? -value : value;
            }
        });
    }

    @Test
    void stringValue() throws Exception {
        verify(new TestCode.IntParamText() {

            @Override
            public String run(@Param(ints = {-10, -1, 0, 5}) int value) {
                return 0 <= value ? "positive" : "negative";
            }
        });
    }

    @Test
    void nested() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(ints = {0, 3, -3}) int value) {
                return value == 0 ? 1 : value < 0 ? 2 : 3;
            }
        });
    }

    @Test
    void logicalAnd() throws Exception {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return 0 < value && value < 10;
            }
        });
    }

    @Test
    void logicalOr() throws Exception {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value < 0 || 10 < value;
            }
        });
    }

    @Test
    void logicalMixed() throws Exception {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return !(value == 0) && value % 2 == 0;
            }
        });
    }
}