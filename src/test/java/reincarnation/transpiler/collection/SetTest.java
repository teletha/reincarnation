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

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the {@link Set} API.
 */
class SetTest extends JavascriptCodeVerifier {

    @Test
    void size() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Set<String> set = new HashSet<>();
                set.add("a");
                set.add("b");
                set.add("a");
                return set.size();
            }
        });
    }

    @Test
    void contains() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                Set<Integer> set = new HashSet<>();
                set.add(1);
                set.add(2);
                return set.contains(2) && !set.contains(3);
            }
        });
    }

    @Test
    void remove() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Set<Integer> set = new HashSet<>();
                set.add(1);
                set.add(2);
                set.remove(1);
                return set.size() + (set.remove(3) ? 10 : 0);
            }
        });
    }

    @Test
    void iterate() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Set<Integer> set = new LinkedHashSet<>();
                set.add(1);
                set.add(2);
                set.add(3);

                int sum = 0;
                for (int value : set) {
                    sum += value;
                }
                return sum;
            }
        });
    }

    @Test
    void treeOrder() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Set<Integer> set = new TreeSet<>();
                set.add(3);
                set.add(1);
                set.add(2);

                int value = 0;
                for (int element : set) {
                    value = value * 10 + element;
                }
                return value;
            }
        });
    }

    @Test
    void of() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Set<Integer> set = Set.of(1, 2, 3);
                return set.size() + (set.contains(3) ? 10 : 0);
            }
        });
    }

    @Test
    void copy() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Set<Integer> source = new HashSet<>();
                source.add(1);
                source.add(2);

                Set<Integer> copy = new HashSet<>(source);
                copy.add(3);
                return copy.size();
            }
        });
    }

    @Test
    void toArray() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Set<Integer> set = new LinkedHashSet<>();
                set.add(1);
                set.add(2);
                set.add(3);

                Integer[] array = set.toArray(new Integer[0]);
                return array.length + array[0];
            }
        });
    }
}
