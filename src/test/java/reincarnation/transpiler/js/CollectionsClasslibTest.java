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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the Java implementation of {@link Collections}.
 */
class CollectionsClasslibTest extends JavascriptCodeVerifier {

    @Test
    void sort() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                List<String> list = new ArrayList<>();
                list.add("b");
                list.add("a");
                Collections.sort(list);
                return list.get(0) + list.get(1);
            }
        });
    }

    @Test
    void sortReverse() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                list.add(3);
                list.add(2);
                Collections.sort(list, Collections.reverseOrder());
                return list.get(0) * 100 + list.get(1) * 10 + list.get(2);
            }
        });
    }

    @Test
    void reverseAndSwap() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                list.add(2);
                list.add(3);
                Collections.reverse(list);
                Collections.swap(list, 0, 2);
                return list.get(0) * 100 + list.get(1) * 10 + list.get(2);
            }
        });
    }

    @Test
    void fillAndCopy() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<Integer> source = new ArrayList<>();
                source.add(1);
                source.add(2);
                List<Integer> destination = new ArrayList<>();
                destination.add(0);
                destination.add(0);
                Collections.copy(destination, source);
                Collections.fill(destination, 7);
                return destination.get(0) + destination.get(1);
            }
        });
    }

    @Test
    void maxAndMin() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<Integer> list = new ArrayList<>();
                list.add(3);
                list.add(1);
                list.add(2);
                return (int) Collections.max(list) * 10 + (int) Collections.min(list);
            }
        });
    }

    @Test
    void binarySearch() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                list.add(3);
                list.add(5);
                return Collections.binarySearch(list, 3) * 10 + Collections.binarySearch(list, 2);
            }
        });
    }

    @Test
    void factories() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return Collections.emptyList().size() + Collections.singletonList("a").size()
                        + Collections.nCopies(3, "x").size();
            }
        });
    }

    @Test
    void unmodifiable() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                list.add(2);
                return Collections.unmodifiableList(list).size();
            }
        });
    }
}
