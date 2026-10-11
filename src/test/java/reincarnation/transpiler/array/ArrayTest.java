/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.array;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for array operations.
 * 
 * @version 2026/10/11 11:20:00
 */
class ArrayTest extends JavascriptCodeVerifier {

    @Test
    void intArray() throws Exception {
        verify(new TestCode.IntArray() {

            @Override
            public int[] run() {
                return new int[] {1, 2, 3};
            }
        });
    }

    @Test
    void booleanArray() throws Exception {
        verify(new TestCode.BooleanArray() {

            @Override
            public boolean[] run() {
                return new boolean[] {true, false};
            }
        });
    }

    @Test
    void textArray() throws Exception {
        verify(new TestCode.TextArray() {

            @Override
            public String[] run() {
                return new String[] {"a", "b", "c"};
            }
        });
    }

    @Test
    void longArray() throws Exception {
        verify(new TestCode.LongArray() {

            @Override
            public long[] run() {
                return new long[] {1L, 2L, 3L};
            }
        });
    }

    @Test
    void doubleArray() throws Exception {
        verify(new TestCode.DoubleArray() {

            @Override
            public double[] run() {
                return new double[] {1.5, 2.5};
            }
        });
    }

    @Test
    void arrayLength() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return new int[] {5, 6, 7}.length;
            }
        });
    }

    @Test
    void arrayAccess() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int[] values = new int[3];
                values[0] = 5;
                values[1] = values[0] + 2;
                return values[1];
            }
        });
    }

    @Test
    void iterableFor() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int total = 0;
                for (int value : new int[] {1, 2, 3}) {
                    total += value;
                }
                return total;
            }
        });
    }

    @Test
    void multiDimensionAccess() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int[][] values = new int[2][3];
                values[1][2] = 5;
                return values[1][2];
            }
        });
    }

    @Test
    void multiDimensionLiteral() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int[][] values = new int[][] {{1, 2}, {3, 4}};
                return values[0][1] + values[1][0];
            }
        });
    }

    @Test
    void multiDimensionIterableFor() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int total = 0;
                for (int[] row : new int[][] {{1, 2, 3}, {4, 5, 6}}) {
                    for (int value : row) {
                        total += value;
                    }
                }
                return total;
            }
        });
    }
}