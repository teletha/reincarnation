/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.type;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.TestCode.Param;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for type conversion operations.
 * 
 * @version 2026/10/11 11:20:00
 */
class TypeTest extends JavascriptCodeVerifier {

    @Test
    void booleanToInt() throws Exception {
        verify(new TestCode.BooleanParamInt() {

            @Override
            public int run(boolean value) {
                return value ? 1 : 0;
            }
        });
    }

    @Test
    void longToDouble() throws Exception {
        verify(new TestCode.LongParamDouble() {

            @Override
            public double run(@Param(longs = {-100L, 0L, 100L}) long value) {
                return value;
            }
        });
    }

    @Test
    void longToInt() throws Exception {
        verify(new TestCode.LongParamInt() {

            @Override
            public int run(@Param(longs = {-100L, 0L, 100L}) long value) {
                return (int) value;
            }
        });
    }

    @Test
    void intToString() throws Exception {
        verify(new TestCode.IntParamText() {

            @Override
            public String run(@Param(ints = {-1, 0, 3}) int value) {
                return "" + value;
            }
        });
    }
}