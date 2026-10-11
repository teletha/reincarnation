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
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for varargs operations.
 * 
 * @version 2026/10/11 12:30:00
 */
class VarArgTest extends JavascriptCodeVerifier {

    @Test
    void varargs() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return sum(1, 2, 3);
            }

            private int sum(int... values) {
                int total = 0;
                for (int value : values) {
                    total += value;
                }
                return total;
            }
        });
    }

    @Test
    void varargsWithBase() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return sumWithBase(10, 1, 2, 3);
            }

            private int sumWithBase(int base, int... values) {
                int total = base;
                for (int value : values) {
                    total += value;
                }
                return total;
            }
        });
    }

    @Test
    void varargsFromArray() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return sum(new int[] {1, 2, 3});
            }

            private int sum(int... values) {
                int total = 0;
                for (int value : values) {
                    total += value;
                }
                return total;
            }
        });
    }

    @Test
    void varargsEmpty() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return sum();
            }

            private int sum(int... values) {
                int total = 0;
                for (int value : values) {
                    total += value;
                }
                return total;
            }
        });
    }
}