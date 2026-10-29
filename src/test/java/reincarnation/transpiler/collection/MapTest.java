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

    @Test
    void computeIfAbsent() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Map<String, Integer> map = new HashMap<>();
                map.computeIfAbsent("a", key -> 1);
                map.computeIfAbsent("a", key -> 2);
                return map.get("a");
            }
        });
    }

    @Test
    void merge() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Map<String, Integer> map = new HashMap<>();
                map.merge("a", 1, (left, right) -> left + right);
                map.merge("a", 2, (left, right) -> left + right);
                return map.get("a");
            }
        });
    }

    @Test
    void putIfAbsent() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Map<String, Integer> map = new HashMap<>();
                map.putIfAbsent("a", 1);
                map.putIfAbsent("a", 2);
                return map.get("a");
            }
        });
    }

    @Test
    void treeOrder() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Map<Integer, String> map = new java.util.TreeMap<>();
                map.put(3, "c");
                map.put(1, "a");
                map.put(2, "b");

                int value = 0;
                for (int key : map.keySet()) {
                    value = value * 10 + key;
                }
                return value;
            }
        });
    }

    @Test
    void copyConstructor() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Map<String, Integer> source = new HashMap<>();
                source.put("a", 1);
                Map<String, Integer> copy = new HashMap<>(source);
                copy.put("b", 2);
                return copy.size() * 10 + copy.get("a");
            }
        });
    }

    @Test
    void hashtable() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Map<String, Integer> map = new java.util.Hashtable<>();
                map.put("a", 1);
                map.put("b", 2);
                return map.size() * 10 + map.get("b");
            }
        });
    }

    @Test
    void removeByKeyAndValue() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                Map<String, Integer> map = new HashMap<>();
                map.put("a", 1);
                map.put("b", 2);
                return map.remove("a", 1) && !map.remove("b", 1) && map.size() == 1;
            }
        });
    }

    @Test
    void replace() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Map<String, Integer> map = new HashMap<>();
                map.put("a", 1);
                map.replace("a", 10);
                boolean replaced = map.replace("a", 10, 20);
                return replaced ? map.get("a") : -1;
            }
        });
    }

    @Test
    void compute() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Map<String, Integer> map = new HashMap<>();
                map.put("a", 1);
                map.compute("a", (key, value) -> value + 10);
                map.computeIfPresent("a", (key, value) -> value + 100);
                return map.get("a");
            }
        });
    }

    @Test
    void forEach() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Map<String, Integer> map = new LinkedHashMap<>();
                map.put("a", 1);
                map.put("b", 2);

                int[] sum = new int[1];
                for (java.util.Map.Entry<String, Integer> entry : map.entrySet()) {
                    sum[0] += entry.getValue();
                }
                map.forEach((key, value) -> sum[0] += value);
                return sum[0];
            }
        });
    }

    @Test
    void entryFactory() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Map<String, Integer> map = new LinkedHashMap<>();
                map.put("a", 1);
                java.util.Map.Entry<String, Integer> entry = map.entrySet().iterator().next();
                entry.setValue(2);
                return entry.getKey() + entry.getValue();
            }
        });
    }

    @Test
    void ofEntries() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Map<String, Integer> map = Map.ofEntries(Map.entry("a", 1), Map.entry("b", 2));
                return map.size() * 10 + map.get("b");
            }
        });
    }

    @Test
    void copyOf() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Map<String, Integer> source = new HashMap<>();
                source.put("a", 1);
                return Map.copyOf(source).get("a");
            }
        });
    }
}
