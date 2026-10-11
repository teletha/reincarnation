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
}