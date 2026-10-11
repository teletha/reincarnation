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
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the {@link List} API.
 * 
 * @version 2026/10/11 12:00:00
 */
class ListTest extends JavascriptCodeVerifier {

    @Test
    void size() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<String> list = new ArrayList<>();
                list.add("a");
                list.add("b");
                return list.size();
            }
        });
    }

    @Test
    void isEmpty() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                List<String> list = new ArrayList<>();
                return list.isEmpty();
            }
        });
    }

    @Test
    void get() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<Integer> list = new ArrayList<>();
                list.add(10);
                list.add(20);
                list.add(30);
                return list.get(1);
            }
        });
    }

    @Test
    void set() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<Integer> list = new ArrayList<>();
                list.add(10);
                list.add(20);
                list.set(0, 99);
                return list.get(0);
            }
        });
    }

    @Test
    void addReturnsBoolean() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                List<String> list = new ArrayList<>();
                return list.add("a");
            }
        });
    }

    @Test
    void addAt() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                List<String> list = new ArrayList<>();
                list.add("a");
                list.add("c");
                list.add(1, "b");
                return list.toString();
            }
        });
    }

    @Test
    void contains() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                List<String> list = new ArrayList<>();
                list.add("a");
                list.add("b");
                return list.contains("b");
            }
        });
    }

    @Test
    void indexOf() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<String> list = new ArrayList<>();
                list.add("a");
                list.add("b");
                return list.indexOf("b");
            }
        });
    }

    @Test
    void removeByObject() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                List<String> list = new ArrayList<>();
                list.add("a");
                list.add("b");
                return list.remove("a");
            }
        });
    }

    @Test
    void removeByIndex() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                List<String> list = new ArrayList<>();
                list.add("a");
                list.add("b");
                list.add("c");
                String removed = list.remove(1);
                return removed + list;
            }
        });
    }

    @Test
    void toStringValue() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                list.add(2);
                list.add(3);
                return list.toString();
            }
        });
    }

    @Test
    void equalsValue() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                List<String> left = new ArrayList<>();
                left.add("a");
                List<String> right = new ArrayList<>();
                right.add("a");
                return left.equals(right);
            }
        });
    }

    @Test
    void enhancedFor() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                list.add(2);
                list.add(3);
                int sum = 0;
                for (int value : list) {
                    sum += value;
                }
                return sum;
            }
        });
    }

    @Test
    void linkedList() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                List<String> list = new LinkedList<>();
                list.add("a");
                list.add("b");
                return list.toString();
            }
        });
    }

    @Test
    void factory() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                List<Integer> list = List.of(1, 2, 3);
                return list.toString();
            }
        });
    }

    @Test
    void addAll() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                list.addAll(List.of(2, 3));
                return list.size();
            }
        });
    }
}
