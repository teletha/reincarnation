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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.junit.jupiter.api.Test;

class ClasslibApiTest extends ClasslibProgramVerifier {

    @Test
    void mathMax() throws Exception {
        verify(MathMax.class, "run");
    }

    static class MathMax {
        static int run() {
            return Math.max(3, 7);
        }
    }

    @Test
    void mathAbs() throws Exception {
        verify(MathAbs.class, "run");
    }

    static class MathAbs {
        static int run() {
            return Math.abs(-5);
        }
    }

    @Test
    void mathSqrt() throws Exception {
        verify(MathSqrt.class, "run");
    }

    static class MathSqrt {
        static double run() {
            return Math.sqrt(16);
        }
    }

    @Test
    void mathRound() throws Exception {
        verify(MathRound.class, "run");
    }

    static class MathRound {
        static long run() {
            return Math.round(3.6);
        }
    }

    @Test
    void integerParse() throws Exception {
        verify(IntegerParse.class, "run");
    }

    static class IntegerParse {
        static int run() {
            return Integer.parseInt("123");
        }
    }

    @Test
    void integerToString() throws Exception {
        verify(IntegerToString.class, "run");
    }

    static class IntegerToString {
        static String run() {
            return Integer.toString(123);
        }
    }

    @Test
    void integerToHex() throws Exception {
        verify(IntegerToHex.class, "run");
    }

    static class IntegerToHex {
        static String run() {
            return Integer.toHexString(255);
        }
    }

    @Test
    void longParse() throws Exception {
        verify(LongParse.class, "run");
    }

    static class LongParse {
        static long run() {
            return Long.parseLong("123");
        }
    }

    @Test
    void characterIsDigit() throws Exception {
        verify(CharacterIsDigit.class, "run");
    }

    static class CharacterIsDigit {
        static boolean run() {
            return Character.isDigit('7');
        }
    }

    @Test
    void characterToUpper() throws Exception {
        verify(CharacterToUpper.class, "run");
    }

    static class CharacterToUpper {
        static char run() {
            return Character.toUpperCase('a');
        }
    }

    @Test
    void arraysSort() throws Exception {
        verify(ArraysSort.class, "run");
    }

    static class ArraysSort {
        static String run() {
            int[] values = {3, 1, 2};
            Arrays.sort(values);
            return Arrays.toString(values);
        }
    }

    @Test
    void arraysFill() throws Exception {
        verify(ArraysFill.class, "run");
    }

    static class ArraysFill {
        static String run() {
            int[] values = new int[3];
            Arrays.fill(values, 7);
            return Arrays.toString(values);
        }
    }

    @Test
    void arraysCopyOf() throws Exception {
        verify(ArraysCopyOf.class, "run");
    }

    static class ArraysCopyOf {
        static String run() {
            int[] values = {1, 2};
            return Arrays.toString(Arrays.copyOf(values, 3));
        }
    }

    @Test
    void arraysAsList() throws Exception {
        verify(ArraysAsList.class, "run");
    }

    static class ArraysAsList {
        static int run() {
            return Arrays.asList("a", "b", "c").size();
        }
    }

    @Test
    void collectionsSort() throws Exception {
        verify(CollectionsSort.class, "run");
    }

    static class CollectionsSort {
        static String run() {
            List<Integer> list = new ArrayList<>();
            list.add(3);
            list.add(1);
            list.add(2);
            Collections.sort(list);
            return list.toString();
        }
    }

    @Test
    void collectionsReverse() throws Exception {
        verify(CollectionsReverse.class, "run");
    }

    static class CollectionsReverse {
        static String run() {
            List<String> list = new ArrayList<>();
            list.add("a");
            list.add("b");
            Collections.reverse(list);
            return list.toString();
        }
    }

    @Test
    void objectsEquals() throws Exception {
        verify(ObjectsEquals.class, "run");
    }

    static class ObjectsEquals {
        static boolean run() {
            return Objects.equals("a", "a");
        }
    }

    @Test
    void objectsHashCode() throws Exception {
        verify(ObjectsHashCode.class, "run");
    }

    static class ObjectsHashCode {
        static int run() {
            return Objects.hashCode("a");
        }
    }

    @Test
    void objectsRequireNonNull() throws Exception {
        verify(ObjectsRequireNonNull.class, "run");
    }

    static class ObjectsRequireNonNull {
        static String run() {
            return Objects.requireNonNull("a");
        }
    }

    @Test
    void stringSubstring() throws Exception {
        verify(StringSubstring.class, "run");
    }

    static class StringSubstring {
        static String run() {
            return "abcdef".substring(1, 4);
        }
    }

    @Test
    void stringIndexOf() throws Exception {
        verify(StringIndexOf.class, "run");
    }

    static class StringIndexOf {
        static int run() {
            return "abcdef".indexOf('c');
        }
    }

    @Test
    void stringCharAt() throws Exception {
        verify(StringCharAt.class, "run");
    }

    static class StringCharAt {
        static char run() {
            return "abc".charAt(1);
        }
    }

    @Test
    void stringToUpper() throws Exception {
        verify(StringToUpper.class, "run");
    }

    static class StringToUpper {
        static String run() {
            return "abc".toUpperCase();
        }
    }

    @Test
    void stringTrim() throws Exception {
        verify(StringTrim.class, "run");
    }

    static class StringTrim {
        static String run() {
            return "  abc  ".trim();
        }
    }

    @Test
    void stringStartsWith() throws Exception {
        verify(StringStartsWith.class, "run");
    }

    static class StringStartsWith {
        static boolean run() {
            return "abcdef".startsWith("abc");
        }
    }

    @Test
    void stringSplit() throws Exception {
        verify(StringSplit.class, "run");
    }

    static class StringSplit {
        static int run() {
            return "a,b,c".split(",").length;
        }
    }

    @Test
    void stringJoin() throws Exception {
        verify(StringJoin.class, "run");
    }

    static class StringJoin {
        static String run() {
            return String.join("-", "a", "b", "c");
        }
    }

    @Test
    void stringFormat() throws Exception {
        verify(StringFormat.class, "run");
    }

    static class StringFormat {
        static String run() {
            return String.format("%d:%s", 5, "x");
        }
    }

    @Test
    void stringRepeat() throws Exception {
        verify(StringRepeat.class, "run");
    }

    static class StringRepeat {
        static String run() {
            return "ab".repeat(3);
        }
    }

    @Test
    void stringValueOf() throws Exception {
        verify(StringValueOf.class, "run");
    }

    static class StringValueOf {
        static String run() {
            return String.valueOf(123);
        }
    }

    @Test
    void stringCompareTo() throws Exception {
        verify(StringCompareTo.class, "run");
    }

    static class StringCompareTo {
        static int run() {
            return "a".compareTo("b");
        }
    }

    @Test
    void stringBuilder() throws Exception {
        verify(StringBuilderCase.class, "run");
    }

    static class StringBuilderCase {
        static String run() {
            StringBuilder builder = new StringBuilder();
            builder.append("a");
            builder.append(1);
            builder.append('c');
            return builder.toString();
        }
    }

    @Test
    void stringBuilderReverse() throws Exception {
        verify(StringBuilderReverse.class, "run");
    }

    static class StringBuilderReverse {
        static String run() {
            return new StringBuilder("abc").reverse().toString();
        }
    }

    @Test
    void iterator() throws Exception {
        verify(IteratorCase.class, "run");
    }

    static class IteratorCase {
        static int run() {
            List<String> list = new ArrayList<>();
            list.add("a");
            list.add("b");
            java.util.Iterator<String> iterator = list.iterator();
            int count = 0;
            while (iterator.hasNext()) {
                iterator.next();
                count++;
            }
            return count;
        }
    }

    @Test
    void hashSet() throws Exception {
        verify(HashSetCase.class, "run");
    }

    static class HashSetCase {
        static String run() {
            Set<String> set = new HashSet<>();
            set.add("a");
            set.add("b");
            set.add("a");
            return set.toString();
        }
    }

    @Test
    void linkedHashSet() throws Exception {
        verify(LinkedHashSetCase.class, "run");
    }

    static class LinkedHashSetCase {
        static String run() {
            Set<String> set = new LinkedHashSet<>();
            set.add("b");
            set.add("a");
            return set.toString();
        }
    }

    @Test
    void mapOf() throws Exception {
        verify(MapOf.class, "run");
    }

    static class MapOf {
        static int run() {
            Map<String, Integer> map = Map.of("a", 1, "b", 2);
            return map.size();
        }
    }

    @Test
    void listOf() throws Exception {
        verify(ListOf.class, "run");
    }

    static class ListOf {
        static int run() {
            return List.of("a", "b").size();
        }
    }

    @Test
    void systemArraycopy() throws Exception {
        verify(SystemArraycopy.class, "run");
    }

    static class SystemArraycopy {
        static String run() {
            int[] source = {1, 2, 3};
            int[] target = new int[3];
            System.arraycopy(source, 0, target, 0, 3);
            return Arrays.toString(target);
        }
    }

    @Test
    void hashMapOrder() throws Exception {
        verify(HashMapOrder.class, "run");
    }

    static class HashMapOrder {
        static int run() {
            Map<String, Integer> map = new LinkedHashMap<>();
            map.put("a", 1);
            map.put("b", 2);
            int sum = 0;
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                sum += entry.getKey().length() + entry.getValue();
            }
            return sum;
        }
    }
}
