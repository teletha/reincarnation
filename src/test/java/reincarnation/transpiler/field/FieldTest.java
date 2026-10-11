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
 * Test the JavaScript output for field operations.
 * 
 * @version 2026/10/11 11:20:00
 */
class FieldTest extends JavascriptCodeVerifier {

    @Test
    void fieldIncrement() throws Exception {
        verify(new TestCode.Int() {

            private int count = 3;

            @Override
            public int run() {
                count++;
                return count;
            }
        });
    }

    @Test
    void fieldAssign() throws Exception {
        verify(new TestCode.Int() {

            private int value;

            @Override
            public int run() {
                value = 5;
                value += 2;
                return value;
            }
        });
    }

    @Test
    void fieldInExpression() throws Exception {
        verify(new TestCode.IntParam() {

            private int base = 4;

            @Override
            public int run(@Param(ints = {1, 2, 3, 10}) int value) {
                return base + value;
            }
        });
    }

    @Test
    void fieldIncrementByParam() throws Exception {
        verify(new TestCode.IntParam() {

            private int count = 10;

            @Override
            public int run(@Param(ints = {-5, 0, 5}) int value) {
                count -= value;
                return count;
            }
        });
    }

    @Test
    void fieldCondition() throws Exception {
        verify(new TestCode.IntParamBoolean() {

            private int limit = 50;

            @Override
            public boolean run(@Param(ints = {-10, 10, 100}) int value) {
                return limit < value;
            }
        });
    }
}