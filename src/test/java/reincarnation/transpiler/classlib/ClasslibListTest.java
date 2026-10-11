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
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Verify the TeaVM class library based JavaScript program for {@link List}.
 */
class ClasslibListTest extends ClasslibProgramVerifier {

    @Test
    void size() throws Exception {
        verify(Size.class, "run");
    }

    static class Size {
        static int run() {
            List<String> list = new ArrayList<>();
            list.add("a");
            list.add("b");
            return list.size();
        }
    }

    @Test
    void get() throws Exception {
        verify(Get.class, "run");
    }

    static class Get {
        static String run() {
            List<String> list = new ArrayList<>();
            list.add("a");
            list.add("b");
            return list.get(1);
        }
    }

    @Test
    void addReturnsBoolean() throws Exception {
        verify(AddReturn.class, "run");
    }

    static class AddReturn {
        static boolean run() {
            List<String> list = new ArrayList<>();
            return list.add("a");
        }
    }

    @Test
    void contains() throws Exception {
        verify(Contains.class, "run");
    }

    static class Contains {
        static boolean run() {
            List<String> list = new ArrayList<>();
            list.add("a");
            return list.contains("a");
        }
    }

    @Test
    void isEmpty() throws Exception {
        verify(IsEmpty.class, "run");
    }

    static class IsEmpty {
        static boolean run() {
            List<String> list = new ArrayList<>();
            list.add("a");
            return list.isEmpty();
        }
    }

    @Test
    void toStringValue() throws Exception {
        verify(ToStringValue.class, "run");
    }

    static class ToStringValue {
        static String run() {
            List<Integer> list = new ArrayList<>();
            list.add(1);
            list.add(2);
            list.add(3);
            return list.toString();
        }
    }

    @Test
    void removeByObject() throws Exception {
        verify(RemoveByObject.class, "run");
    }

    static class RemoveByObject {
        static boolean run() {
            List<String> list = new ArrayList<>();
            list.add("a");
            list.add("b");
            return list.remove("a");
        }
    }

    @Test
    void removeByIndex() throws Exception {
        verify(RemoveByIndex.class, "run");
    }

    static class RemoveByIndex {
        static String run() {
            List<String> list = new ArrayList<>();
            list.add("a");
            list.add("b");
            list.add("c");
            return list.remove(1);
        }
    }

    @Test
    void indexOf() throws Exception {
        verify(IndexOf.class, "run");
    }

    static class IndexOf {
        static int run() {
            List<String> list = new ArrayList<>();
            list.add("a");
            list.add("b");
            return list.indexOf("b");
        }
    }
}
