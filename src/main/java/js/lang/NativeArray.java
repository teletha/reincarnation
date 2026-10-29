/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package js.lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * The JavaScript {@code Array}.
 * <p>
 * The Java implementation uses a list so that the same test can run on the JVM, and the
 * {@link Native} template maps the operation to the real JavaScript array.
 * </p>
 */
@Native
public final class NativeArray {

    /** The JVM representation. */
    private final List<Object> values;

    /**
     * Create an empty array.
     */
    @Native("[]")
    public NativeArray() {
        this.values = new ArrayList<>();
    }

    /**
     * Create an array with the specified length.
     * 
     * @param length A length.
     */
    @Native("new Array($0)")
    public NativeArray(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Invalid array length");
        }
        this.values = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            values.add(NativeUndefined.INSTANCE);
        }
    }

    /**
     * Get the length.
     * 
     * @return A length.
     */
    @Native("$this.length")
    public int length() {
        return values.size();
    }

    /**
     * Get the value at the specified index.
     * 
     * @param index An index.
     * @return A value or {@link NativeUndefined}.
     */
    @Native("$this[$0]")
    public Object get(int index) {
        if (index < 0 || values.size() <= index) {
            return NativeUndefined.INSTANCE;
        }
        return values.get(index);
    }

    /**
     * Set the value at the specified index.
     * 
     * @param index An index.
     * @param value A value.
     */
    @Native("$this[$0] = $1")
    public void set(int index, Object value) {
        if (index < 0) {
            return;
        }
        while (values.size() <= index) {
            values.add(NativeUndefined.INSTANCE);
        }
        values.set(index, value);
    }

    /**
     * Append the value.
     * 
     * @param value A value.
     * @return The new length.
     */
    @Native("$this.push($0)")
    public int push(Object value) {
        values.add(value);
        return values.size();
    }

    /**
     * Remove and return the last value.
     * 
     * @return A value or {@link NativeUndefined}.
     */
    @Native("$this.pop()")
    public Object pop() {
        return values.isEmpty() ? NativeUndefined.INSTANCE : values.remove(values.size() - 1);
    }

    /**
     * Remove and return the first value.
     * 
     * @return A value or {@link NativeUndefined}.
     */
    @Native("$this.shift()")
    public Object shift() {
        return values.isEmpty() ? NativeUndefined.INSTANCE : values.remove(0);
    }

    /**
     * Insert the value at the beginning.
     * 
     * @param value A value.
     * @return The new length.
     */
    @Native("$this.unshift($0)")
    public int unshift(Object value) {
        values.add(0, value);
        return values.size();
    }

    /**
     * Extract a part of this array.
     * 
     * @param start A start index.
     * @return A new array.
     */
    @Native("$this.slice($0)")
    public NativeArray slice(int start) {
        return slice(start, values.size());
    }

    /**
     * Extract a part of this array.
     * 
     * @param start A start index.
     * @param end An end index.
     * @return A new array.
     */
    @Native("$this.slice($0, $1)")
    public NativeArray slice(int start, int end) {
        int size = values.size();
        int from = start < 0 ? Math.max(size + start, 0) : Math.min(start, size);
        int to = end < 0 ? Math.max(size + end, 0) : Math.min(end, size);

        NativeArray array = new NativeArray();
        for (int i = from; i < to; i++) {
            array.push(values.get(i));
        }
        return array;
    }

    /**
     * Find the index of the specified value.
     * 
     * @param value A value.
     * @return An index or -1.
     */
    @Native("$this.indexOf($0)")
    public int indexOf(Object value) {
        return values.indexOf(value);
    }

    /**
     * Check whether this array contains the specified value.
     * 
     * @param value A value.
     * @return A result.
     */
    @Native("$this.includes($0)")
    public boolean includes(Object value) {
        return values.contains(value);
    }

    /**
     * Join all values.
     * 
     * @param separator A separator.
     * @return A joined text.
     */
    @Native("$this.join($0)")
    public String join(String separator) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            if (i != 0) {
                builder.append(separator);
            }
            Object value = values.get(i);
            if (value != null && value != NativeUndefined.INSTANCE) {
                builder.append(value);
            }
        }
        return builder.toString();
    }

    /**
     * Reverse this array.
     * 
     * @return This array.
     */
    @Native("$this.reverse()")
    public NativeArray reverse() {
        Collections.reverse(values);
        return this;
    }

    /**
     * Invoke the action for each value.
     * 
     * @param action An action.
     */
    @Native("$this.forEach($0)")
    public void forEach(Consumer<Object> action) {
        values.forEach(action);
    }

    /**
     * Map each value.
     * 
     * @param mapper A mapper.
     * @return A new array.
     */
    @Native("$this.map($0)")
    public NativeArray map(Function<Object, Object> mapper) {
        NativeArray array = new NativeArray();
        for (Object value : values) {
            array.push(mapper.apply(value));
        }
        return array;
    }

    /**
     * Check whether the specified value is an array.
     * 
     * @param value A target value.
     * @return A result.
     */
    @Native("Array.isArray($0)")
    public static boolean isArray(Object value) {
        return value instanceof NativeArray;
    }

    /**
     * Copy the part of the array.
     * 
     * @param source A source array.
     * @param sourcePosition A source position.
     * @param destination A destination array.
     * @param destinationPosition A destination position.
     * @param length A length.
     */
    @Native("$rt_arraycopy($0, $1, $2, $3, $4)")
    public static void arraycopy(Object source, int sourcePosition, Object destination, int destinationPosition, int length) {
        java.lang.System.arraycopy(source, sourcePosition, destination, destinationPosition, length);
    }

    /**
     * Copy the array.
     * 
     * @param array A source array.
     * @param length A new length.
     * @param defaultValue A default value.
     * @return A copied array.
     */
    @Native("$rt_arrayCopyOf($0, $1, $2)")
    public static Object copyOf(Object array, int length, Object defaultValue) {
        return java.util.Arrays.copyOf((Object[]) array, length);
    }

    /**
     * Copy the range of the array.
     * 
     * @param array A source array.
     * @param from A start index.
     * @param to An end index.
     * @param defaultValue A default value.
     * @return A copied array.
     */
    @Native("$rt_arrayCopyOfRange($0, $1, $2, $3)")
    public static Object copyOfRange(Object array, int from, int to, Object defaultValue) {
        return java.util.Arrays.copyOfRange((Object[]) array, from, to);
    }

    /**
     * Fill the array.
     * 
     * @param array A target array.
     * @param from A start index.
     * @param to An end index.
     * @param value A value.
     */
    @Native("$rt_arrayFill($0, $1, $2, $3)")
    public static void fill(Object array, int from, int to, Object value) {
        java.util.Arrays.fill((Object[]) array, from, to, value);
    }

    /**
     * Compare the arrays.
     * 
     * @param left A left array.
     * @param right A right array.
     * @param deep Whether the comparison is deep.
     * @return A result.
     */
    @Native("$rt_arrayEquals($0, $1, $2)")
    public static boolean arrayEquals(Object left, Object right, boolean deep) {
        return deep ? java.util.Arrays.deepEquals((Object[]) left, (Object[]) right)
                : java.util.Arrays.equals((Object[]) left, (Object[]) right);
    }

    /**
     * Compute the hash code of the array.
     * 
     * @param array A target array.
     * @param deep Whether the hash code is deep.
     * @return A hash code.
     */
    @Native("$rt_arrayHash($0, $1)")
    public static int arrayHash(Object array, boolean deep) {
        return deep ? java.util.Arrays.deepHashCode((Object[]) array) : java.util.Arrays.hashCode((Object[]) array);
    }

    /**
     * Convert the array into the text.
     * 
     * @param array A target array.
     * @param deep Whether the conversion is deep.
     * @return A text.
     */
    @Native("$rt_arrayToString($0, $1)")
    public static String arrayToString(Object array, boolean deep) {
        return deep ? java.util.Arrays.deepToString((Object[]) array) : java.util.Arrays.toString((Object[]) array);
    }

    /**
     * Sort the array.
     * 
     * @param array A target array.
     * @param from A start index.
     * @param to An end index.
     * @param comparator A comparator. It may be null.
     */
    @Native("$rt_sortArray($0, $1, $2, $3)")
    public static void sort(Object array, int from, int to, Object comparator) {
        if (comparator == null) {
            java.util.Arrays.sort((Object[]) array, from, to);
        } else {
            java.util.Arrays.sort((Object[]) array, from, to, (java.util.Comparator) comparator);
        }
    }

    /**
     * Extract the range of the array.
     * 
     * @param array A target array.
     * @param from A start index.
     * @param to An end index.
     * @return An extracted array.
     */
    @Native("$0.slice($1, $2)")
    public static Object slice(Object array, int from, int to) {
        return java.util.Arrays.copyOfRange((Object[]) array, from, to);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return join(",");
    }
}
