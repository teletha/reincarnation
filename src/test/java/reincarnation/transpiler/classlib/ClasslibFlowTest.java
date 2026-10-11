/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.classlib;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Verify the TeaVM class library based JavaScript program for the iterator and the exception.
 */
class ClasslibFlowTest extends ClasslibProgramVerifier {

    @Test
    void iterableFor() throws Exception {
        verify(IterableFor.class, "run");
    }

    static class IterableFor {
        static int run() {
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
    }

    @Test
    void entrySet() throws Exception {
        verify(EntrySet.class, "run");
    }

    static class EntrySet {
        static int run() {
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
    }

    @Test
    void keySet() throws Exception {
        verify(KeySet.class, "run");
    }

    static class KeySet {
        static int run() {
            Map<String, Integer> map = new LinkedHashMap<>();
            map.put("a", 1);
            map.put("bb", 2);
            int sum = 0;
            for (String key : map.keySet()) {
                sum += key.length();
            }
            return sum;
        }
    }

    @Test
    void valuesFor() throws Exception {
        verify(ValuesFor.class, "run");
    }

    static class ValuesFor {
        static int run() {
            Map<String, Integer> map = new LinkedHashMap<>();
            map.put("a", 1);
            map.put("b", 2);
            int sum = 0;
            for (int value : map.values()) {
                sum += value;
            }
            return sum;
        }
    }

    @Test
    void throwCatch() throws Exception {
        verify(ThrowCatch.class, "run");
    }

    static class ThrowCatch {
        static String run() {
            List<String> list = new ArrayList<>();
            try {
                list.get(10);
                return "no";
            } catch (IndexOutOfBoundsException e) {
                return "caught";
            }
        }
    }

    @Test
    void throwCatchFinally() throws Exception {
        verify(ThrowCatchFinally.class, "run");
    }

    static class ThrowCatchFinally {
        static int run() {
            int value = 0;
            try {
                value = 1;
                if (value == 1) {
                    throw new IllegalStateException("x");
                }
                value = 2;
            } catch (IllegalStateException e) {
                value = 3;
            } finally {
                value += 10;
            }
            return value;
        }
    }
}
