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
 * Test the JavaScript output for parameter and local variable operations.
 * 
 * @version 2026/10/11 12:00:00
 */
class ParameterTest extends JavascriptCodeVerifier {

    @Test
    void parameterUse() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(ints = {1, 2, 3, 10}) int value) {
                return value + value * 2;
            }
        });
    }

    @Test
    void parameterReassign() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                value += 5;
                value -= 2;
                return value;
            }
        });
    }

    @Test
    void localVariable() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                int result = value * 10;
                int extra = 3;
                return result + extra;
            }
        });
    }

    @Test
    void localVariableReuse() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int value = 1;
                value += 2;
                value *= 3;
                return value;
            }
        });
    }

    @Test
    void scopedLocal() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                int result = 1;
                if (value < 3) {
                    result = 10;
                }
                return result + value;
            }
        });
    }

    @Test
    void methodChain() throws Exception {
        verify(new TestCode.TextParam() {

            @Override
            public String run(@Param(strings = {"abcdef", "xyz"}) String value) {
                return value.substring(1).toUpperCase();
            }
        });
    }
}