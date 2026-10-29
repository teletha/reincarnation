/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.collection;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the {@link Arrays} API.
 */
class ArraysTest extends JavascriptCodeVerifier {

    @Test
    void asList() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<Integer> list = Arrays.asList(1, 2, 3);
                return list.size() + list.get(1);
            }
        });
    }

    @Test
    void copyOf() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int[] source = {1, 2, 3};
                int[] copy = Arrays.copyOf(source, 5);
                return copy.length + copy[0] + copy[4];
            }
        });
    }

    @Test
    void copyOfRange() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int[] source = {1, 2, 3, 4, 5};
                int[] copy = Arrays.copyOfRange(source, 1, 4);
                return copy.length * 100 + copy[0] * 10 + copy[2];
            }
        });
    }

    @Test
    void fill() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int[] array = new int[4];
                Arrays.fill(array, 7);
                return array[0] + array[3];
            }
        });
    }

    @Test
    void equals() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return Arrays.equals(new int[] {1, 2, 3}, new int[] {1, 2, 3}) //
                        && !Arrays.equals(new int[] {1, 2, 3}, new int[] {1, 2});
            }
        });
    }

    @Test
    void hashCodeOfArray() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return Arrays.hashCode(new int[] {1, 2, 3});
            }
        });
    }

    @Test
    void toStringOfArray() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return Arrays.toString(new int[] {1, 2, 3});
            }
        });
    }

    @Test
    void sort() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int[] array = {3, 1, 2};
                Arrays.sort(array);
                return array[0] * 100 + array[1] * 10 + array[2];
            }
        });
    }
}
