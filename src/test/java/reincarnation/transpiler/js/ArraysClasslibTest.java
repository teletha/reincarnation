/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.js;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the Java implementation of {@link Arrays}.
 */
class ArraysClasslibTest extends JavascriptCodeVerifier {

    @Test
    void objectSort() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                String[] values = {"b", "c", "a"};
                Arrays.sort(values);
                return values[0] + values[1] + values[2];
            }
        });
    }

    @Test
    void comparatorSort() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Integer[] values = {1, 3, 2};
                Arrays.sort(values, (left, right) -> right - left);
                return values[0] * 100 + values[1] * 10 + values[2];
            }
        });
    }

    @Test
    void objectToString() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return Arrays.toString(new String[] {"a", "b"});
            }
        });
    }

    @Test
    void fillRange() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int[] values = {1, 2, 3, 4};
                Arrays.fill(values, 1, 3, 9);
                return values[0] * 1000 + values[1] * 100 + values[2] * 10 + values[3];
            }
        });
    }

    @Test
    void objectCopy() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                String[] values = {"a", "b"};
                String[] copy = Arrays.copyOf(values, 3);
                return copy.length == 3 && copy[0].equals("a") && copy[2] == null;
            }
        });
    }

    @Test
    void stream() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return (int) Arrays.stream(new String[] {"a", "b"}).count();
            }
        });
    }
}
