/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.util;

import java.util.Comparator;
import java.util.List;
import reincarnation.classlib.java.util.stream.Stream;

import js.lang.NativeArray;

/**
 * The Java implementation of {@link java.util.Arrays} which is transpiled into JavaScript.
 */
public final class Arrays {

    /**
     * Hide constructor.
     */
    private Arrays() {
    }

    /**
     * Create the list.
     * 
     * @param values Values.
     * @return A list.
     */
    public static <T> List<T> asList(T... values) {
        return List.of(values);
    }

    /**
     * Convert the array into the text.
     * 
     * @param array An array.
     * @return A text.
     */
    public static String toString(Object[] array) {
        return NativeArray.arrayToString(array, false);
    }

    /**
     * Convert the array into the text.
     * 
     * @param array An array.
     * @return A text.
     */
    public static String toString(boolean[] array) {
        return NativeArray.arrayToString(array, false);
    }

    /**
     * Convert the array into the text.
     * 
     * @param array An array.
     * @return A text.
     */
    public static String toString(char[] array) {
        return NativeArray.arrayToString(array, false);
    }

    /**
     * Convert the array into the text.
     * 
     * @param array An array.
     * @return A text.
     */
    public static String toString(byte[] array) {
        return NativeArray.arrayToString(array, false);
    }

    /**
     * Convert the array into the text.
     * 
     * @param array An array.
     * @return A text.
     */
    public static String toString(short[] array) {
        return NativeArray.arrayToString(array, false);
    }

    /**
     * Convert the array into the text.
     * 
     * @param array An array.
     * @return A text.
     */
    public static String toString(int[] array) {
        return NativeArray.arrayToString(array, false);
    }

    /**
     * Convert the array into the text.
     * 
     * @param array An array.
     * @return A text.
     */
    public static String toString(long[] array) {
        return NativeArray.arrayToString(array, false);
    }

    /**
     * Convert the array into the text.
     * 
     * @param array An array.
     * @return A text.
     */
    public static String toString(float[] array) {
        return NativeArray.arrayToString(array, false);
    }

    /**
     * Convert the array into the text.
     * 
     * @param array An array.
     * @return A text.
     */
    public static String toString(double[] array) {
        return NativeArray.arrayToString(array, false);
    }

    /**
     * Convert the array into the text.
     * 
     * @param array An array.
     * @return A text.
     */
    public static String deepToString(Object[] array) {
        return NativeArray.arrayToString(array, true);
    }

    /**
     * Copy the array.
     * 
     * @param array An array.
     * @param length A new length.
     * @return A copied array.
     */
    public static Object[] copyOf(Object[] array, int length) {
        return (Object[]) NativeArray.copyOf(array, length, null);
    }

    /**
     * Copy the array.
     * 
     * @param array An array.
     * @param length A new length.
     * @return A copied array.
     */
    public static boolean[] copyOf(boolean[] array, int length) {
        return (boolean[]) NativeArray.copyOf(array, length, false);
    }

    /**
     * Copy the array.
     * 
     * @param array An array.
     * @param length A new length.
     * @return A copied array.
     */
    public static char[] copyOf(char[] array, int length) {
        return (char[]) NativeArray.copyOf(array, length, '\0');
    }

    /**
     * Copy the array.
     * 
     * @param array An array.
     * @param length A new length.
     * @return A copied array.
     */
    public static byte[] copyOf(byte[] array, int length) {
        return (byte[]) NativeArray.copyOf(array, length, (byte) 0);
    }

    /**
     * Copy the array.
     * 
     * @param array An array.
     * @param length A new length.
     * @return A copied array.
     */
    public static short[] copyOf(short[] array, int length) {
        return (short[]) NativeArray.copyOf(array, length, (short) 0);
    }

    /**
     * Copy the array.
     * 
     * @param array An array.
     * @param length A new length.
     * @return A copied array.
     */
    public static int[] copyOf(int[] array, int length) {
        return (int[]) NativeArray.copyOf(array, length, 0);
    }

    /**
     * Copy the array.
     * 
     * @param array An array.
     * @param length A new length.
     * @return A copied array.
     */
    public static long[] copyOf(long[] array, int length) {
        return (long[]) NativeArray.copyOf(array, length, 0L);
    }

    /**
     * Copy the array.
     * 
     * @param array An array.
     * @param length A new length.
     * @return A copied array.
     */
    public static float[] copyOf(float[] array, int length) {
        return (float[]) NativeArray.copyOf(array, length, 0F);
    }

    /**
     * Copy the array.
     * 
     * @param array An array.
     * @param length A new length.
     * @return A copied array.
     */
    public static double[] copyOf(double[] array, int length) {
        return (double[]) NativeArray.copyOf(array, length, 0D);
    }

    /**
     * Copy the range of the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     * @return A copied array.
     */
    public static Object[] copyOfRange(Object[] array, int from, int to) {
        return (Object[]) NativeArray.copyOfRange(array, from, to, null);
    }

    /**
     * Copy the range of the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     * @return A copied array.
     */
    public static boolean[] copyOfRange(boolean[] array, int from, int to) {
        return (boolean[]) NativeArray.copyOfRange(array, from, to, false);
    }

    /**
     * Copy the range of the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     * @return A copied array.
     */
    public static char[] copyOfRange(char[] array, int from, int to) {
        return (char[]) NativeArray.copyOfRange(array, from, to, '\0');
    }

    /**
     * Copy the range of the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     * @return A copied array.
     */
    public static byte[] copyOfRange(byte[] array, int from, int to) {
        return (byte[]) NativeArray.copyOfRange(array, from, to, (byte) 0);
    }

    /**
     * Copy the range of the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     * @return A copied array.
     */
    public static short[] copyOfRange(short[] array, int from, int to) {
        return (short[]) NativeArray.copyOfRange(array, from, to, (short) 0);
    }

    /**
     * Copy the range of the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     * @return A copied array.
     */
    public static int[] copyOfRange(int[] array, int from, int to) {
        return (int[]) NativeArray.copyOfRange(array, from, to, 0);
    }

    /**
     * Copy the range of the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     * @return A copied array.
     */
    public static long[] copyOfRange(long[] array, int from, int to) {
        return (long[]) NativeArray.copyOfRange(array, from, to, 0L);
    }

    /**
     * Copy the range of the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     * @return A copied array.
     */
    public static float[] copyOfRange(float[] array, int from, int to) {
        return (float[]) NativeArray.copyOfRange(array, from, to, 0F);
    }

    /**
     * Copy the range of the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     * @return A copied array.
     */
    public static double[] copyOfRange(double[] array, int from, int to) {
        return (double[]) NativeArray.copyOfRange(array, from, to, 0D);
    }

    /**
     * Fill the array.
     * 
     * @param array An array.
     * @param value A value.
     */
    public static void fill(Object[] array, Object value) {
        NativeArray.fill(array, 0, array.length, value);
    }

    /**
     * Fill the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     * @param value A value.
     */
    public static void fill(Object[] array, int from, int to, Object value) {
        NativeArray.fill(array, from, to, value);
    }

    /**
     * Fill the array.
     * 
     * @param array An array.
     * @param value A value.
     */
    public static void fill(boolean[] array, boolean value) {
        NativeArray.fill(array, 0, array.length, value);
    }

    /**
     * Fill the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     * @param value A value.
     */
    public static void fill(boolean[] array, int from, int to, boolean value) {
        NativeArray.fill(array, from, to, value);
    }

    /**
     * Fill the array.
     * 
     * @param array An array.
     * @param value A value.
     */
    public static void fill(char[] array, char value) {
        NativeArray.fill(array, 0, array.length, value);
    }

    /**
     * Fill the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     * @param value A value.
     */
    public static void fill(char[] array, int from, int to, char value) {
        NativeArray.fill(array, from, to, value);
    }

    /**
     * Fill the array.
     * 
     * @param array An array.
     * @param value A value.
     */
    public static void fill(byte[] array, byte value) {
        NativeArray.fill(array, 0, array.length, value);
    }

    /**
     * Fill the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     * @param value A value.
     */
    public static void fill(byte[] array, int from, int to, byte value) {
        NativeArray.fill(array, from, to, value);
    }

    /**
     * Fill the array.
     * 
     * @param array An array.
     * @param value A value.
     */
    public static void fill(short[] array, short value) {
        NativeArray.fill(array, 0, array.length, value);
    }

    /**
     * Fill the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     * @param value A value.
     */
    public static void fill(short[] array, int from, int to, short value) {
        NativeArray.fill(array, from, to, value);
    }

    /**
     * Fill the array.
     * 
     * @param array An array.
     * @param value A value.
     */
    public static void fill(int[] array, int value) {
        NativeArray.fill(array, 0, array.length, value);
    }

    /**
     * Fill the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     * @param value A value.
     */
    public static void fill(int[] array, int from, int to, int value) {
        NativeArray.fill(array, from, to, value);
    }

    /**
     * Fill the array.
     * 
     * @param array An array.
     * @param value A value.
     */
    public static void fill(long[] array, long value) {
        NativeArray.fill(array, 0, array.length, value);
    }

    /**
     * Fill the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     * @param value A value.
     */
    public static void fill(long[] array, int from, int to, long value) {
        NativeArray.fill(array, from, to, value);
    }

    /**
     * Fill the array.
     * 
     * @param array An array.
     * @param value A value.
     */
    public static void fill(float[] array, float value) {
        NativeArray.fill(array, 0, array.length, value);
    }

    /**
     * Fill the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     * @param value A value.
     */
    public static void fill(float[] array, int from, int to, float value) {
        NativeArray.fill(array, from, to, value);
    }

    /**
     * Fill the array.
     * 
     * @param array An array.
     * @param value A value.
     */
    public static void fill(double[] array, double value) {
        NativeArray.fill(array, 0, array.length, value);
    }

    /**
     * Fill the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     * @param value A value.
     */
    public static void fill(double[] array, int from, int to, double value) {
        NativeArray.fill(array, from, to, value);
    }

    /**
     * Compare the arrays.
     * 
     * @param left A left array.
     * @param right A right array.
     * @return A result.
     */
    public static boolean equals(Object[] left, Object[] right) {
        return NativeArray.arrayEquals(left, right, false);
    }

    /**
     * Compare the arrays.
     * 
     * @param left A left array.
     * @param right A right array.
     * @return A result.
     */
    public static boolean equals(boolean[] left, boolean[] right) {
        return NativeArray.arrayEquals(left, right, false);
    }

    /**
     * Compare the arrays.
     * 
     * @param left A left array.
     * @param right A right array.
     * @return A result.
     */
    public static boolean equals(char[] left, char[] right) {
        return NativeArray.arrayEquals(left, right, false);
    }

    /**
     * Compare the arrays.
     * 
     * @param left A left array.
     * @param right A right array.
     * @return A result.
     */
    public static boolean equals(byte[] left, byte[] right) {
        return NativeArray.arrayEquals(left, right, false);
    }

    /**
     * Compare the arrays.
     * 
     * @param left A left array.
     * @param right A right array.
     * @return A result.
     */
    public static boolean equals(short[] left, short[] right) {
        return NativeArray.arrayEquals(left, right, false);
    }

    /**
     * Compare the arrays.
     * 
     * @param left A left array.
     * @param right A right array.
     * @return A result.
     */
    public static boolean equals(int[] left, int[] right) {
        return NativeArray.arrayEquals(left, right, false);
    }

    /**
     * Compare the arrays.
     * 
     * @param left A left array.
     * @param right A right array.
     * @return A result.
     */
    public static boolean equals(long[] left, long[] right) {
        return NativeArray.arrayEquals(left, right, false);
    }

    /**
     * Compare the arrays.
     * 
     * @param left A left array.
     * @param right A right array.
     * @return A result.
     */
    public static boolean equals(float[] left, float[] right) {
        return NativeArray.arrayEquals(left, right, false);
    }

    /**
     * Compare the arrays.
     * 
     * @param left A left array.
     * @param right A right array.
     * @return A result.
     */
    public static boolean equals(double[] left, double[] right) {
        return NativeArray.arrayEquals(left, right, false);
    }

    /**
     * Compare the arrays deeply.
     * 
     * @param left A left array.
     * @param right A right array.
     * @return A result.
     */
    public static boolean deepEquals(Object[] left, Object[] right) {
        return NativeArray.arrayEquals(left, right, true);
    }

    /**
     * Compute the hash code.
     * 
     * @param array An array.
     * @return A hash code.
     */
    public static int hashCode(Object[] array) {
        return NativeArray.arrayHash(array, false);
    }

    /**
     * Compute the hash code.
     * 
     * @param array An array.
     * @return A hash code.
     */
    public static int hashCode(boolean[] array) {
        return NativeArray.arrayHash(array, false);
    }

    /**
     * Compute the hash code.
     * 
     * @param array An array.
     * @return A hash code.
     */
    public static int hashCode(char[] array) {
        return NativeArray.arrayHash(array, false);
    }

    /**
     * Compute the hash code.
     * 
     * @param array An array.
     * @return A hash code.
     */
    public static int hashCode(byte[] array) {
        return NativeArray.arrayHash(array, false);
    }

    /**
     * Compute the hash code.
     * 
     * @param array An array.
     * @return A hash code.
     */
    public static int hashCode(short[] array) {
        return NativeArray.arrayHash(array, false);
    }

    /**
     * Compute the hash code.
     * 
     * @param array An array.
     * @return A hash code.
     */
    public static int hashCode(int[] array) {
        return NativeArray.arrayHash(array, false);
    }

    /**
     * Compute the hash code.
     * 
     * @param array An array.
     * @return A hash code.
     */
    public static int hashCode(long[] array) {
        return NativeArray.arrayHash(array, false);
    }

    /**
     * Compute the hash code.
     * 
     * @param array An array.
     * @return A hash code.
     */
    public static int hashCode(float[] array) {
        return NativeArray.arrayHash(array, false);
    }

    /**
     * Compute the hash code.
     * 
     * @param array An array.
     * @return A hash code.
     */
    public static int hashCode(double[] array) {
        return NativeArray.arrayHash(array, false);
    }

    /**
     * Compute the deep hash code.
     * 
     * @param array An array.
     * @return A hash code.
     */
    public static int deepHashCode(Object[] array) {
        return NativeArray.arrayHash(array, true);
    }

    /**
     * Sort the array.
     * 
     * @param array An array.
     */
    public static void sort(Object[] array) {
        NativeArray.sort(array, 0, array.length, null);
    }

    /**
     * Sort the range of the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     */
    public static void sort(Object[] array, int from, int to) {
        NativeArray.sort(array, from, to, null);
    }

    /**
     * Sort the array.
     * 
     * @param array An array.
     * @param comparator A comparator.
     */
    public static void sort(Object[] array, Comparator comparator) {
        NativeArray.sort(array, 0, array.length, comparator);
    }

    /**
     * Sort the range of the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     * @param comparator A comparator.
     */
    public static void sort(Object[] array, int from, int to, Comparator comparator) {
        NativeArray.sort(array, from, to, comparator);
    }

    /**
     * Sort the array.
     * 
     * @param array An array.
     */
    public static void sort(boolean[] array) {
        NativeArray.sort(array, 0, array.length, null);
    }

    /**
     * Sort the range of the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     */
    public static void sort(boolean[] array, int from, int to) {
        NativeArray.sort(array, from, to, null);
    }

    /**
     * Sort the array.
     * 
     * @param array An array.
     */
    public static void sort(char[] array) {
        NativeArray.sort(array, 0, array.length, null);
    }

    /**
     * Sort the range of the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     */
    public static void sort(char[] array, int from, int to) {
        NativeArray.sort(array, from, to, null);
    }

    /**
     * Sort the array.
     * 
     * @param array An array.
     */
    public static void sort(byte[] array) {
        NativeArray.sort(array, 0, array.length, null);
    }

    /**
     * Sort the range of the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     */
    public static void sort(byte[] array, int from, int to) {
        NativeArray.sort(array, from, to, null);
    }

    /**
     * Sort the array.
     * 
     * @param array An array.
     */
    public static void sort(short[] array) {
        NativeArray.sort(array, 0, array.length, null);
    }

    /**
     * Sort the range of the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     */
    public static void sort(short[] array, int from, int to) {
        NativeArray.sort(array, from, to, null);
    }

    /**
     * Sort the array.
     * 
     * @param array An array.
     */
    public static void sort(int[] array) {
        NativeArray.sort(array, 0, array.length, null);
    }

    /**
     * Sort the range of the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     */
    public static void sort(int[] array, int from, int to) {
        NativeArray.sort(array, from, to, null);
    }

    /**
     * Sort the array.
     * 
     * @param array An array.
     */
    public static void sort(long[] array) {
        NativeArray.sort(array, 0, array.length, null);
    }

    /**
     * Sort the range of the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     */
    public static void sort(long[] array, int from, int to) {
        NativeArray.sort(array, from, to, null);
    }

    /**
     * Sort the array.
     * 
     * @param array An array.
     */
    public static void sort(float[] array) {
        NativeArray.sort(array, 0, array.length, null);
    }

    /**
     * Sort the range of the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     */
    public static void sort(float[] array, int from, int to) {
        NativeArray.sort(array, from, to, null);
    }

    /**
     * Sort the array.
     * 
     * @param array An array.
     */
    public static void sort(double[] array) {
        NativeArray.sort(array, 0, array.length, null);
    }

    /**
     * Sort the range of the array.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     */
    public static void sort(double[] array, int from, int to) {
        NativeArray.sort(array, from, to, null);
    }

    /**
     * Sort the array.
     * 
     * @param array An array.
     */
    public static void parallelSort(Object[] array) {
        sort(array);
    }

    /**
     * Sort the array.
     * 
     * @param array An array.
     * @param comparator A comparator.
     */
    public static void parallelSort(Object[] array, Comparator comparator) {
        sort(array, comparator);
    }

    /**
     * Sort the array.
     * 
     * @param array An array.
     */
    public static void parallelSort(int[] array) {
        sort(array);
    }

    /**
     * Sort the array.
     * 
     * @param array An array.
     */
    public static void parallelSort(long[] array) {
        sort(array);
    }

    /**
     * Create the stream.
     * 
     * @param array An array.
     * @return A stream.
     */
    public static Stream stream(Object[] array) {
        return Stream.of(array);
    }

    /**
     * Create the stream.
     * 
     * @param array An array.
     * @param from A start index.
     * @param to An end index.
     * @return A stream.
     */
    public static Stream stream(Object[] array, int from, int to) {
        return Stream.of(NativeArray.slice(array, from, to));
    }

    /**
     * Create the stream.
     * 
     * @param array An array.
     * @return A stream.
     */
    public static Stream stream(int[] array) {
        return Stream.of(array);
    }

    /**
     * Create the stream.
     * 
     * @param array An array.
     * @return A stream.
     */
    public static Stream stream(long[] array) {
        return Stream.of(array);
    }

    /**
     * Create the stream.
     * 
     * @param array An array.
     * @return A stream.
     */
    public static Stream stream(double[] array) {
        return Stream.of(array);
    }
}
