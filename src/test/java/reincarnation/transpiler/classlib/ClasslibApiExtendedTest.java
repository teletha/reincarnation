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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

class ClasslibApiExtendedTest extends ClasslibProgramVerifier {

    @Test
    void mathMinDouble() throws Exception {
        verify(MathMinDouble.class, "run");
    }

    static class MathMinDouble {
        static double run() {
            return Math.min(2.5, -1.5);
        }
    }

    @Test
    void mathPow() throws Exception {
        verify(MathPow.class, "run");
    }

    static class MathPow {
        static double run() {
            return Math.pow(2, 10);
        }
    }

    @Test
    void mathFloorDiv() throws Exception {
        verify(MathFloorDiv.class, "run");
    }

    static class MathFloorDiv {
        static int run() {
            return Math.floorDiv(-7, 2) + Math.floorMod(-7, 2);
        }
    }

    @Test
    void mathHypot() throws Exception {
        verify(MathHypot.class, "run");
    }

    static class MathHypot {
        static double run() {
            return Math.hypot(3, 4);
        }
    }

    @Test
    void mathPi() throws Exception {
        verify(MathPi.class, "run");
    }

    static class MathPi {
        static String run() {
            return Math.PI > 3.14 && Math.PI < 3.15 ? "pi" : "no";
        }
    }

    @Test
    void integerParseRadix() throws Exception {
        verify(IntegerParseRadix.class, "run");
    }

    static class IntegerParseRadix {
        static int run() {
            return Integer.parseInt("ff", 16);
        }
    }

    @Test
    void integerToStringRadix() throws Exception {
        verify(IntegerToStringRadix.class, "run");
    }

    static class IntegerToStringRadix {
        static String run() {
            return Integer.toString(255, 16);
        }
    }

    @Test
    void integerToHexNegative() throws Exception {
        verify(IntegerToHexNegative.class, "run");
    }

    static class IntegerToHexNegative {
        static String run() {
            return Integer.toHexString(-1);
        }
    }

    @Test
    void integerBitCount() throws Exception {
        verify(IntegerBitCount.class, "run");
    }

    static class IntegerBitCount {
        static int run() {
            return Integer.bitCount(255) + Integer.highestOneBit(100) + Integer.numberOfLeadingZeros(1);
        }
    }

    @Test
    void integerCompare() throws Exception {
        verify(IntegerCompare.class, "run");
    }

    static class IntegerCompare {
        static int run() {
            return Integer.compare(1, 2) + Integer.max(3, 4) + Integer.min(5, 6);
        }
    }

    @Test
    void integerValueOf() throws Exception {
        verify(IntegerValueOf.class, "run");
    }

    static class IntegerValueOf {
        static int run() {
            Integer value = Integer.valueOf("42");
            return value + 1;
        }
    }

    @Test
    void integerHash() throws Exception {
        verify(IntegerHash.class, "run");
    }

    static class IntegerHash {
        static int run() {
            return Integer.valueOf(7).hashCode() + Integer.hashCode(7);
        }
    }

    @Test
    void longParseAndToString() throws Exception {
        verify(LongParseAndToString.class, "run");
    }

    static class LongParseAndToString {
        static String run() {
            long value = Long.parseLong("123456789");
            return Long.toString(value + 1);
        }
    }

    @Test
    void doubleParse() throws Exception {
        verify(DoubleParse.class, "run");
    }

    static class DoubleParse {
        static double run() {
            return Double.parseDouble("1.5") + Double.parseDouble("2.5");
        }
    }

    @Test
    void doubleToString() throws Exception {
        verify(DoubleToString.class, "run");
    }

    static class DoubleToString {
        static String run() {
            return Double.toString(1.5);
        }
    }

    @Test
    void doubleIsNaN() throws Exception {
        verify(DoubleIsNaN.class, "run");
    }

    static class DoubleIsNaN {
        static boolean run() {
            double value = 0.0 / 0.0;
            return Double.isNaN(value) && !Double.isNaN(1.0);
        }
    }

    @Test
    void doubleCompare() throws Exception {
        verify(DoubleCompare.class, "run");
    }

    static class DoubleCompare {
        static int run() {
            return Double.compare(1.5, 2.5) + Double.compare(2.5, 2.5);
        }
    }

    @Test
    void booleanParse() throws Exception {
        verify(BooleanParse.class, "run");
    }

    static class BooleanParse {
        static boolean run() {
            return Boolean.parseBoolean("TRUE") && !Boolean.parseBoolean("no");
        }
    }

    @Test
    void booleanLogical() throws Exception {
        verify(BooleanLogical.class, "run");
    }

    static class BooleanLogical {
        static int run() {
            return (Boolean.logicalAnd(true, false) ? 1 : 0) + (Boolean.logicalOr(true, false) ? 2 : 0) + (Boolean.logicalXor(true, true) ? 4 : 0);
        }
    }

    @Test
    void characterDigitAndForDigit() throws Exception {
        verify(CharacterDigitAndForDigit.class, "run");
    }

    static class CharacterDigitAndForDigit {
        static String run() {
            int digit = Character.digit('f', 16);
            char ch = Character.forDigit(11, 16);
            return digit + ":" + ch + ":" + Character.getNumericValue('7');
        }
    }

    @Test
    void characterClassify() throws Exception {
        verify(CharacterClassify.class, "run");
    }

    static class CharacterClassify {
        static int run() {
            int value = 0;
            if (Character.isLetter('a')) value += 1;
            if (Character.isWhitespace(' ')) value += 2;
            if (Character.isUpperCase('A')) value += 4;
            if (Character.isLowerCase('a')) value += 8;
            return value;
        }
    }

    @Test
    void stringLastIndexOfChar() throws Exception {
        verify(StringLastIndexOfChar.class, "run");
    }

    static class StringLastIndexOfChar {
        static int run() {
            return "abca".lastIndexOf('a');
        }
    }

    @Test
    void stringEndsWith() throws Exception {
        verify(StringEndsWith.class, "run");
    }

    static class StringEndsWith {
        static boolean run() {
            return "abcdef".endsWith("def");
        }
    }

    @Test
    void stringJoinIterable() throws Exception {
        verify(StringJoinIterable.class, "run");
    }

    static class StringJoinIterable {
        static String run() {
            List<String> list = new ArrayList<>();
            list.add("a");
            list.add("b");
            return String.join("-", list);
        }
    }

    @Test
    void stringFormatFloat() throws Exception {
        verify(StringFormatFloat.class, "run");
    }

    static class StringFormatFloat {
        static String run() {
            return String.format("%s=%d %.1f %x", "v", 5, 1.25, 255);
        }
    }

    @Test
    void stringValueOfObject() throws Exception {
        verify(StringValueOfObject.class, "run");
    }

    static class StringValueOfObject {
        static String run() {
            Object value = new Object() {

                @Override
                public String toString() {
                    return "object";
                }
            };
            return String.valueOf(value);
        }
    }

    @Test
    void stringValueOfChars() throws Exception {
        verify(StringValueOfChars.class, "run");
    }

    static class StringValueOfChars {
        static String run() {
            char[] chars = {'a', 'b', 'c'};
            return String.valueOf(chars);
        }
    }

    @Test
    void stringToCharArray() throws Exception {
        verify(StringToCharArray.class, "run");
    }

    static class StringToCharArray {
        static int run() {
            return "abc".toCharArray().length;
        }
    }

    @Test
    void stringMatches() throws Exception {
        verify(StringMatches.class, "run");
    }

    static class StringMatches {
        static boolean run() {
            return "abc123".matches("[a-z]+[0-9]+");
        }
    }

    @Test
    void stringReplaceAll() throws Exception {
        verify(StringReplaceAll.class, "run");
    }

    static class StringReplaceAll {
        static String run() {
            return "a-b-c".replaceAll("-", "+");
        }
    }

    @Test
    void stringHashCode() throws Exception {
        verify(StringHashCode.class, "run");
    }

    static class StringHashCode {
        static int run() {
            return "abc".hashCode();
        }
    }

    @Test
    void stringStrip() throws Exception {
        verify(StringStrip.class, "run");
    }

    static class StringStrip {
        static String run() {
            return "<" + "  abc  ".strip() + ">";
        }
    }

    @Test
    void stringCodePoint() throws Exception {
        verify(StringCodePoint.class, "run");
    }

    static class StringCodePoint {
        static String run() {
            String text = "a\uD83D\uDE00b";
            return text.codePointAt(1) + ":" + text.codePointBefore(3) + ":" + text.codePointCount(0, text.length());
        }
    }

    @Test
    void stringBuilderInsertDelete() throws Exception {
        verify(StringBuilderInsertDelete.class, "run");
    }

    static class StringBuilderInsertDelete {
        static String run() {
            StringBuilder builder = new StringBuilder("abcdef");
            builder.insert(2, "-");
            builder.delete(3, 5);
            return builder.toString();
        }
    }

    @Test
    void stringBuilderReplaceAndSetCharAt() throws Exception {
        verify(StringBuilderReplaceAndSetCharAt.class, "run");
    }

    static class StringBuilderReplaceAndSetCharAt {
        static String run() {
            StringBuilder builder = new StringBuilder("abcdef");
            builder.replace(1, 3, "XY");
            builder.setCharAt(0, 'Z');
            return builder.toString();
        }
    }

    @Test
    void stringBuilderSetLength() throws Exception {
        verify(StringBuilderSetLength.class, "run");
    }

    static class StringBuilderSetLength {
        static String run() {
            StringBuilder builder = new StringBuilder("abcdef");
            builder.setLength(3);
            builder.setLength(5);
            return builder.toString() + ":" + builder.length();
        }
    }

    @Test
    void stringBuilderCharAtAndIndexOf() throws Exception {
        verify(StringBuilderCharAtAndIndexOf.class, "run");
    }

    static class StringBuilderCharAtAndIndexOf {
        static String run() {
            StringBuilder builder = new StringBuilder("abcabc");
            return builder.charAt(1) + ":" + builder.indexOf("b") + ":" + builder.lastIndexOf("b") + ":" + builder.substring(2, 4);
        }
    }

    @Test
    void stringBuilderAppendChars() throws Exception {
        verify(StringBuilderAppendChars.class, "run");
    }

    static class StringBuilderAppendChars {
        static String run() {
            StringBuilder builder = new StringBuilder();
            builder.append(new char[] {'a', 'b'});
            builder.append(new char[] {'c', 'd'}, 1, 1);
            return builder.toString();
        }
    }

    @Test
    void stringBuilderAppendObject() throws Exception {
        verify(StringBuilderAppendObject.class, "run");
    }

    static class StringBuilderAppendObject {
        static String run() {
            Object value = new Object() {

                @Override
                public String toString() {
                    return "x";
                }
            };
            return new StringBuilder().append(value).toString();
        }
    }

    @Test
    void systemArraycopyObject() throws Exception {
        verify(SystemArraycopyObject.class, "run");
    }

    static class SystemArraycopyObject {
        static String run() {
            String[] source = {"a", "b", "c"};
            String[] target = new String[3];
            System.arraycopy(source, 1, target, 0, 2);
            return Arrays.toString(target);
        }
    }

    @Test
    void systemLineSeparator() throws Exception {
        verify(SystemLineSeparator.class, "run");
    }

    static class SystemLineSeparator {
        static boolean run() {
            return System.lineSeparator().length() > 0;
        }
    }

    @Test
    void arraysSortRange() throws Exception {
        verify(ArraysSortRange.class, "run");
    }

    static class ArraysSortRange {
        static String run() {
            int[] values = {3, 1, 2, 0};
            Arrays.sort(values, 1, 3);
            return Arrays.toString(values);
        }
    }

    @Test
    void arraysSortStrings() throws Exception {
        verify(ArraysSortStrings.class, "run");
    }

    static class ArraysSortStrings {
        static String run() {
            String[] values = {"b", "c", "a"};
            Arrays.sort(values);
            return Arrays.toString(values);
        }
    }

    @Test
    void arraysSortComparator() throws Exception {
        verify(ArraysSortComparator.class, "run");
    }

    static class ArraysSortComparator {
        static String run() {
            Integer[] values = {1, 3, 2};
            Arrays.sort(values, Comparator.reverseOrder());
            return Arrays.toString(values);
        }
    }

    @Test
    void arraysEquals() throws Exception {
        verify(ArraysEquals.class, "run");
    }

    static class ArraysEquals {
        static boolean run() {
            return Arrays.equals(new int[] {1, 2}, new int[] {1, 2});
        }
    }

    @Test
    void arraysDeepToString() throws Exception {
        verify(ArraysDeepToString.class, "run");
    }

    static class ArraysDeepToString {
        static String run() {
            return Arrays.deepToString(new int[][] {{1, 2}, {3}});
        }
    }

    @Test
    void collectionsSortComparator() throws Exception {
        verify(CollectionsSortComparator.class, "run");
    }

    static class CollectionsSortComparator {
        static String run() {
            List<Integer> list = new ArrayList<>();
            list.add(1);
            list.add(3);
            list.add(2);
            Collections.sort(list, Comparator.reverseOrder());
            return list.toString();
        }
    }

    @Test
    void collectionsSwap() throws Exception {
        verify(CollectionsSwap.class, "run");
    }

    static class CollectionsSwap {
        static String run() {
            List<String> list = new ArrayList<>();
            list.add("a");
            list.add("b");
            Collections.swap(list, 0, 1);
            return list.toString();
        }
    }

    @Test
    void collectionsMaxMin() throws Exception {
        verify(CollectionsMaxMin.class, "run");
    }

    static class CollectionsMaxMin {
        static String run() {
            List<Integer> list = new ArrayList<>();
            list.add(1);
            list.add(3);
            list.add(2);
            return Collections.max(list) + ":" + Collections.min(list);
        }
    }

    @Test
    void collectionsBinarySearch() throws Exception {
        verify(CollectionsBinarySearch.class, "run");
    }

    static class CollectionsBinarySearch {
        static int run() {
            List<Integer> list = new ArrayList<>();
            list.add(1);
            list.add(3);
            list.add(5);
            return Collections.binarySearch(list, 3);
        }
    }

    @Test
    void collectionsUnmodifiable() throws Exception {
        verify(CollectionsUnmodifiable.class, "run");
    }

    static class CollectionsUnmodifiable {
        static int run() {
            List<String> list = new ArrayList<>();
            list.add("a");
            List<String> view = Collections.unmodifiableList(list);
            return view.size();
        }
    }

    @Test
    void setOperations() throws Exception {
        verify(SetOperations.class, "run");
    }

    static class SetOperations {
        static String run() {
            Set<String> set = new LinkedHashSet<>();
            set.add("a");
            set.add("b");
            set.add("a");
            set.remove("a");
            return set.toString() + ":" + set.contains("b") + ":" + set.size();
        }
    }

    @Test
    void deque() throws Exception {
        verify(DequeCase.class, "run");
    }

    static class DequeCase {
        static String run() {
            Deque<Integer> deque = new ArrayDeque<>();
            deque.addLast(1);
            deque.addFirst(0);
            return deque.toString() + ":" + deque.size();
        }
    }

    @Test
    void iteratorMap() throws Exception {
        verify(IteratorMap.class, "run");
    }

    static class IteratorMap {
        static int run() {
            Map<String, Integer> map = new HashMap<>();
            map.put("a", 1);
            map.put("b", 2);
            int sum = 0;
            for (String key : map.keySet()) {
                sum += key.length();
            }
            return sum;
        }
    }

    @Test
    void collectionsCopy() throws Exception {
        verify(CollectionsCopy.class, "run");
    }

    static class CollectionsCopy {
        static String run() {
            List<String> source = new ArrayList<>();
            source.add("a");
            source.add("b");
            List<String> destination = new ArrayList<>();
            destination.add("c");
            destination.add("d");
            Collections.copy(destination, source);
            return destination.toString();
        }
    }

    @Test
    void stringBuilderInsertChars() throws Exception {
        verify(StringBuilderInsertChars.class, "run");
    }

    static class StringBuilderInsertChars {
        static String run() {
            StringBuilder builder = new StringBuilder("abcd");
            builder.insert(2, new char[] {'x', 'y', 'z'}, 1, 2);
            return builder.toString();
        }
    }

    @Test
    void integerParseRange() throws Exception {
        verify(IntegerParseRange.class, "run");
    }

    static class IntegerParseRange {
        static int run() {
            return Integer.parseInt("xx123yy", 2, 5, 10);
        }
    }

    @Test
    void collectionsEmpty() throws Exception {
        verify(CollectionsEmpty.class, "run");
    }

    static class CollectionsEmpty {
        static String run() {
            List<String> list = Collections.emptyList();
            return list.size() + ":" + list.isEmpty();
        }
    }

}
