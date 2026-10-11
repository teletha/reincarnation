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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Verify the TeaVM class library based JavaScript program for {@link Map}.
 */
class ClasslibMapTest extends ClasslibProgramVerifier {

    @Test
    void putAndGet() throws Exception {
        verify(PutAndGet.class, "run");
    }

    static class PutAndGet {
        static int run() {
            Map<String, Integer> map = new HashMap<>();
            map.put("a", 1);
            map.put("b", 2);
            return map.get("b");
        }
    }

    @Test
    void containsKey() throws Exception {
        verify(ContainsKey.class, "run");
    }

    static class ContainsKey {
        static boolean run() {
            Map<String, Integer> map = new HashMap<>();
            map.put("a", 1);
            return map.containsKey("a");
        }
    }

    @Test
    void size() throws Exception {
        verify(Size.class, "run");
    }

    static class Size {
        static int run() {
            Map<String, Integer> map = new HashMap<>();
            map.put("a", 1);
            map.put("b", 2);
            map.put("b", 3);
            return map.size();
        }
    }

    @Test
    void remove() throws Exception {
        verify(Remove.class, "run");
    }

    static class Remove {
        static int run() {
            Map<String, Integer> map = new HashMap<>();
            map.put("a", 1);
            map.put("b", 2);
            return map.remove("a");
        }
    }

    @Test
    void linkedToString() throws Exception {
        verify(LinkedToString.class, "run");
    }

    static class LinkedToString {
        static String run() {
            Map<String, Integer> map = new LinkedHashMap<>();
            map.put("a", 1);
            map.put("b", 2);
            return map.toString();
        }
    }
}
