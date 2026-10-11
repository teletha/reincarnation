/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.primitives;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for int operations.
 * 
 * @version 2026/10/11 11:20:00
 */
class IntTest extends JavascriptCodeVerifier {

    @Test
    void modulo() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value % 7;
            }
        });
    }

    @Test
    void bitwiseAnd() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value & 3;
            }
        });
    }

    @Test
    void bitwiseOr() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value | 1;
            }
        });
    }

    @Test
    void bitwiseXor() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value ^ 5;
            }
        });
    }

    @Test
    void leftShift() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value << 2;
            }
        });
    }

    @Test
    void rightShift() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value >> 2;
            }
        });
    }

    @Test
    void unsignedRightShift() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value >>> 2;
            }
        });
    }

    @Test
    void complement() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return ~value;
            }
        });
    }

    @Test
    void comparison() throws Exception {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return 0 < value;
            }
        });
    }

    @Test
    void range() throws Exception {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return 0 <= value && value < 10;
            }
        });
    }
}