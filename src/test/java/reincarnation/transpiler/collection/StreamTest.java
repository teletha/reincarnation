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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the {@link Stream} API.
 */
class StreamTest extends JavascriptCodeVerifier {

    @Test
    void of() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return Stream.of(1, 2, 3).map(value -> value * 2).collect(Collectors.toList()).size();
            }
        });
    }

    @Test
    void filter() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return (int) Stream.of(1, 2, 3, 4, 5).filter(value -> value % 2 == 0).count();
            }
        });
    }

    @Test
    void collect() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<Integer> list = Stream.of(3, 1, 2).sorted().collect(Collectors.toList());
                return list.get(0) * 100 + list.get(1) * 10 + list.get(2);
            }
        });
    }

    @Test
    void joining() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return Stream.of("a", "b", "c").collect(Collectors.joining("-"));
            }
        });
    }

    @Test
    void collectionStream() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                list.add(2);
                list.add(3);
                return list.stream().map(value -> value + 1).collect(Collectors.toList()).size();
            }
        });
    }

    @Test
    void match() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return Stream.of(1, 2, 3).anyMatch(value -> value == 2) //
                        && Stream.of(1, 2, 3).allMatch(value -> value > 0) //
                        && Stream.of(1, 2, 3).noneMatch(value -> value > 5);
            }
        });
    }

    @Test
    void reduce() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return Stream.of(1, 2, 3, 4).reduce(0, (left, right) -> left + right);
            }
        });
    }

    @Test
    void distinct() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return (int) Stream.of(1, 1, 2, 2, 3).distinct().count();
            }
        });
    }
}
