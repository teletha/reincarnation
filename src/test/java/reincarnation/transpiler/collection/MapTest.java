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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the {@link Map} API.
 * 
 * @version 2026/10/11 12:00:00
 */
class MapTest extends JavascriptCodeVerifier {

    @Test
    void putAndGet() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Map<String, Integer> map = new HashMap<>();
                map.put("a", 1);
                map.put("b", 2);
                return map.get("b");
            }
        });
    }

    @Test
    void putReturnsPrevious() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Map<String, Integer> map = new HashMap<>();
                map.put("a", 1);
                return map.put("a", 2);
            }
        });
    }

    @Test
    void containsKey() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                Map<String, Integer> map = new HashMap<>();
                map.put("a", 1);
                return map.containsKey("a");
            }
        });
    }

    @Test
    void containsValue() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                Map<String, Integer> map = new HashMap<>();
                map.put("a", 1);
                return map.containsValue(1);
            }
        });
    }

    @Test
    void size() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Map<String, Integer> map = new HashMap<>();
                map.put("a", 1);
                map.put("b", 2);
                map.put("a", 3);
                return map.size();
            }
        });
    }

    @Test
    void remove() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Map<String, Integer> map = new HashMap<>();
                map.put("a", 1);
                map.put("b", 2);
                return map.remove("a");
            }
        });
    }

    @Test
    void getOrDefault() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Map<String, Integer> map = new HashMap<>();
                map.put("a", 1);
                return map.getOrDefault("z", 42);
            }
        });
    }

    @Test
    void isEmpty() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                Map<String, Integer> map = new HashMap<>();
                map.put("a", 1);
                map.clear();
                return map.isEmpty();
            }
        });
    }

    @Test
    void equalsValue() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                Map<String, Integer> left = new HashMap<>();
                left.put("a", 1);
                Map<String, Integer> right = new HashMap<>();
                right.put("a", 1);
                return left.equals(right);
            }
        });
    }

    @Test
    void valuesForEach() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Map<String, Integer> map = new LinkedHashMap<>();
                map.put("a", 1);
                map.put("b", 2);
                map.put("c", 3);
                int sum = 0;
                for (int value : map.values()) {
                    sum += value;
                }
                return sum;
            }
        });
    }

    @Test
    void keySetSize() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Map<String, Integer> map = new LinkedHashMap<>();
                map.put("a", 1);
                map.put("b", 2);
                return map.keySet().size();
            }
        });
    }

    @Test
    void entrySetValue() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Map<String, Integer> map = new LinkedHashMap<>();
                map.put("a", 1);
                map.put("b", 2);
                return map.entrySet().iterator().next().getValue();
            }
        });
    }

    @Test
    void entrySet() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Map<String, Integer> map = new LinkedHashMap<>();
                map.put("a", 1);
                map.put("b", 2);
                map.put("c", 3);
                int sum = 0;
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    sum += entry.getValue();
                }
                return sum;
            }
        });
    }

    @Test
    void linkedHashMapToString() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Map<String, Integer> map = new LinkedHashMap<>();
                map.put("a", 1);
                map.put("b", 2);
                return map.toString();
            }
        });
    }

    @Test
    void factory() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Map<String, Integer> map = Map.of("a", 1, "b", 2);
                return map.get("b");
            }
        });
    }
}
