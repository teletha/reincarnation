/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.lang;

import js.lang.NativeString;

/**
 * The Java implementation of {@link java.lang.StringBuilder} which is transpiled into JavaScript.
 * <p>
 * JavaScript strings are immutable, so this implementation keeps the current text and replaces it
 * on every mutation. Every Java overload is declared explicitly because the symbol allocator gives
 * each overload a distinct JavaScript name.
 * </p>
 */
public abstract class AbstractStringBuilder<T extends Appendable> implements Appendable, CharSequence {

    /** The current text. */
    private String value;

    /** The current capacity. */
    private int capacityValue;

    /**
     * Create an empty builder.
     */
    protected AbstractStringBuilder() {
        this.value = "";
        this.capacityValue = 16;
    }

    /**
     * Create an empty builder.
     * 
     * @param capacity A capacity.
     */
    protected AbstractStringBuilder(int capacity) {
        this.value = "";
        this.capacityValue = Math.max(capacity, 0);
    }

    /**
     * Create a builder with the text.
     * 
     * @param value A text.
     */
    protected AbstractStringBuilder(String value) {
        this.value = value == null ? "null" : value;
        this.capacityValue = this.value.length() + 16;
    }

    /**
     * Create a builder with the text.
     * 
     * @param value A text.
     */
    protected AbstractStringBuilder(java.lang.CharSequence value) {
        this(value == null ? "null" : value.toString());
    }

    /**
     * Append the value.
     * 
     * @param value A value.
     * @return This builder.
     */
    public AbstractStringBuilder<T> append(Object value) {
        return appendText(value);
    }

    /**
     * Append the value.
     * 
     * @param value A value.
     * @return This builder.
     */
    public AbstractStringBuilder<T> append(String value) {
        return appendText(value);
    }

    /**
     * Append the value.
     * 
     * @param value A value.
     * @return This builder.
     */
    public AbstractStringBuilder<T> append(StringBuffer value) {
        return appendText(value);
    }

    /**
     * Append the value.
     * 
     * @param value A value.
     * @return This builder.
     */
    public AbstractStringBuilder<T> append(java.lang.CharSequence value) {
        return appendText(value);
    }

    /**
     * Append the range of the value.
     * 
     * @param value A value.
     * @param start A start index.
     * @param end An end index.
     * @return This builder.
     */
    public AbstractStringBuilder<T> append(java.lang.CharSequence value, int start, int end) {
        return appendText(String.valueOf(value).substring(start, end));
    }

    /**
     * Append the characters.
     * 
     * @param value Characters.
     * @return This builder.
     */
    public AbstractStringBuilder<T> append(char[] value) {
        return appendChars(value, 0, value.length);
    }

    /**
     * Append the characters.
     * 
     * @param value Characters.
     * @param offset An offset.
     * @param length A length.
     * @return This builder.
     */
    public AbstractStringBuilder<T> append(char[] value, int offset, int length) {
        return appendChars(value, offset, length);
    }

    /**
     * Append the value.
     * 
     * @param value A value.
     * @return This builder.
     */
    public AbstractStringBuilder<T> append(boolean value) {
        return appendText(String.valueOf(value));
    }

    /**
     * Append the value.
     * 
     * @param value A value.
     * @return This builder.
     */
    public AbstractStringBuilder<T> append(char value) {
        return appendText(String.valueOf(value));
    }

    /**
     * Append the value.
     * 
     * @param value A value.
     * @return This builder.
     */
    public AbstractStringBuilder<T> append(int value) {
        return appendText(String.valueOf(value));
    }

    /**
     * Append the value.
     * 
     * @param value A value.
     * @return This builder.
     */
    public AbstractStringBuilder<T> append(long value) {
        return appendText(String.valueOf(value));
    }

    /**
     * Append the value.
     * 
     * @param value A value.
     * @return This builder.
     */
    public AbstractStringBuilder<T> append(float value) {
        return appendText(String.valueOf(value));
    }

    /**
     * Append the value.
     * 
     * @param value A value.
     * @return This builder.
     */
    public AbstractStringBuilder<T> append(double value) {
        return appendText(String.valueOf(value));
    }

    /**
     * Append the code point.
     * 
     * @param codePoint A code point.
     * @return This builder.
     */
    public AbstractStringBuilder<T> appendCodePoint(int codePoint) {
        return appendText(NativeString.fromCodePoint(codePoint));
    }

    /**
     * Insert the characters.
     * 
     * @param index An index.
     * @param value Characters.
     * @param offset An offset.
     * @param length A length.
     * @return This builder.
     */
    public AbstractStringBuilder<T> insert(int index, char[] value, int offset, int length) {
        String text = "";
        for (int i = offset; i < offset + length; i++) {
            text += value[i];
        }
        return insertText(index, text);
    }

    /**
     * Insert the value.
     * 
     * @param index An index.
     * @param value A value.
     * @return This builder.
     */
    public AbstractStringBuilder<T> insert(int index, Object value) {
        return insertText(index, stringOf(value));
    }

    /**
     * Insert the value.
     * 
     * @param index An index.
     * @param value A value.
     * @return This builder.
     */
    public AbstractStringBuilder<T> insert(int index, String value) {
        return insertText(index, stringOf(value));
    }

    /**
     * Insert the characters.
     * 
     * @param index An index.
     * @param value Characters.
     * @return This builder.
     */
    public AbstractStringBuilder<T> insert(int index, char[] value) {
        String text = "";
        for (char ch : value) {
            text += ch;
        }
        return insertText(index, text);
    }

    /**
     * Insert the value.
     * 
     * @param index An index.
     * @param value A value.
     * @return This builder.
     */
    public AbstractStringBuilder<T> insert(int index, java.lang.CharSequence value) {
        return insertText(index, stringOf(value));
    }

    /**
     * Insert the range of the value.
     * 
     * @param index An index.
     * @param value A value.
     * @param start A start index.
     * @param end An end index.
     * @return This builder.
     */
    public AbstractStringBuilder<T> insert(int index, java.lang.CharSequence value, int start, int end) {
        return insertText(index, String.valueOf(value).substring(start, end));
    }

    /**
     * Insert the value.
     * 
     * @param index An index.
     * @param value A value.
     * @return This builder.
     */
    public AbstractStringBuilder<T> insert(int index, boolean value) {
        return insertText(index, String.valueOf(value));
    }

    /**
     * Insert the value.
     * 
     * @param index An index.
     * @param value A value.
     * @return This builder.
     */
    public AbstractStringBuilder<T> insert(int index, char value) {
        return insertText(index, String.valueOf(value));
    }

    /**
     * Insert the value.
     * 
     * @param index An index.
     * @param value A value.
     * @return This builder.
     */
    public AbstractStringBuilder<T> insert(int index, int value) {
        return insertText(index, String.valueOf(value));
    }

    /**
     * Insert the value.
     * 
     * @param index An index.
     * @param value A value.
     * @return This builder.
     */
    public AbstractStringBuilder<T> insert(int index, long value) {
        return insertText(index, String.valueOf(value));
    }

    /**
     * Insert the value.
     * 
     * @param index An index.
     * @param value A value.
     * @return This builder.
     */
    public AbstractStringBuilder<T> insert(int index, float value) {
        return insertText(index, String.valueOf(value));
    }

    /**
     * Insert the value.
     * 
     * @param index An index.
     * @param value A value.
     * @return This builder.
     */
    public AbstractStringBuilder<T> insert(int index, double value) {
        return insertText(index, String.valueOf(value));
    }

    /**
     * Delete the range.
     * 
     * @param start A start index.
     * @param end An end index.
     * @return This builder.
     */
    public AbstractStringBuilder<T> delete(int start, int end) {
        this.value = value.substring(0, start) + value.substring(end);
        return this;
    }

    /**
     * Delete the character.
     * 
     * @param index An index.
     * @return This builder.
     */
    public AbstractStringBuilder<T> deleteCharAt(int index) {
        this.value = value.substring(0, index) + value.substring(index + 1);
        return this;
    }

    /**
     * Replace the range.
     * 
     * @param start A start index.
     * @param end An end index.
     * @param value A replacement.
     * @return This builder.
     */
    public AbstractStringBuilder<T> replace(int start, int end, String value) {
        this.value = this.value.substring(0, start) + value + this.value.substring(end);
        return this;
    }

    /**
     * Reverse this builder.
     * 
     * @return This builder.
     */
    public AbstractStringBuilder<T> reverse() {
        String reversed = "";
        for (int i = value.length() - 1; 0 <= i; i--) {
            reversed += value.charAt(i);
        }
        this.value = reversed;
        return this;
    }

    /**
     * Set the character.
     * 
     * @param index An index.
     * @param value A character.
     */
    public void setCharAt(int index, char value) {
        this.value = this.value.substring(0, index) + value + this.value.substring(index + 1);
    }

    /**
     * Set the length.
     * 
     * @param length A new length.
     */
    public void setLength(int length) {
        if (length < value.length()) {
            this.value = value.substring(0, length);
        } else {
            while (value.length() < length) {
                this.value += "\0";
            }
        }
    }

    /**
     * Get the character.
     * 
     * @param index An index.
     * @return A character.
     */
    public char charAt(int index) {
        return value.charAt(index);
    }

    /**
     * Get the substring.
     * 
     * @param start A start index.
     * @return A substring.
     */
    public String substring(int start) {
        return value.substring(start);
    }

    /**
     * Get the substring.
     * 
     * @param start A start index.
     * @param end An end index.
     * @return A substring.
     */
    public String substring(int start, int end) {
        return value.substring(start, end);
    }

    /**
     * Get the subsequence.
     * 
     * @param start A start index.
     * @param end An end index.
     * @return A subsequence.
     */
    public String subSequence(int start, int end) {
        return value.substring(start, end);
    }

    /**
     * Find the text.
     * 
     * @param search A search text.
     * @return An index or -1.
     */
    public int indexOf(String search) {
        return value.indexOf(search);
    }

    /**
     * Find the text.
     * 
     * @param search A search text.
     * @param fromIndex A from index.
     * @return An index or -1.
     */
    public int indexOf(String search, int fromIndex) {
        return value.indexOf(search, fromIndex);
    }

    /**
     * Find the text from the end.
     * 
     * @param search A search text.
     * @return An index or -1.
     */
    public int lastIndexOf(String search) {
        return value.lastIndexOf(search);
    }

    /**
     * Find the text from the end.
     * 
     * @param search A search text.
     * @param fromIndex A from index.
     * @return An index or -1.
     */
    public int lastIndexOf(String search, int fromIndex) {
        return value.lastIndexOf(search, fromIndex);
    }

    /**
     * Copy the characters.
     * 
     * @param start A start index.
     * @param end An end index.
     * @param destination A destination array.
     * @param destinationStart A destination start index.
     */
    public void getChars(int start, int end, char[] destination, int destinationStart) {
        for (int i = start; i < end; i++) {
            destination[destinationStart + i - start] = value.charAt(i);
        }
    }

    /**
     * Get the code point.
     * 
     * @param index An index.
     * @return A code point.
     */
    public int codePointAt(int index) {
        return value.codePointAt(index);
    }

    /**
     * Get the previous code point.
     * 
     * @param index An index.
     * @return A code point.
     */
    public int codePointBefore(int index) {
        return value.codePointBefore(index);
    }

    /**
     * Count the code points.
     * 
     * @param start A start index.
     * @param end An end index.
     * @return A count.
     */
    public int codePointCount(int start, int end) {
        return value.codePointCount(start, end);
    }

    /**
     * Get the index by code points.
     * 
     * @param index An index.
     * @param offset A code point offset.
     * @return A new index.
     */
    public int offsetByCodePoints(int index, int offset) {
        return value.offsetByCodePoints(index, offset);
    }

    /**
     * Get the length.
     * 
     * @return A length.
     */
    public int length() {
        return value.length();
    }

    /**
     * Get the capacity.
     * 
     * @return A capacity.
     */
    public int capacity() {
        return capacityValue;
    }

    /**
     * Check whether this builder is empty.
     * 
     * @return A result.
     */
    public boolean isEmpty() {
        return value.length() == 0;
    }

    /**
     * Ensure the capacity.
     * 
     * @param capacity A capacity.
     */
    public void ensureCapacity(int capacity) {
        if (this.capacityValue < capacity) {
            this.capacityValue = capacity;
        }
    }

    /**
     * Trim the capacity.
     */
    public void trimToSize() {
        this.capacityValue = value.length();
    }


    /**
     * Get the text.
     * 
     * @return A text.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Append the value.
     * 
     * @param value A value.
     * @return This builder.
     */
    private AbstractStringBuilder<T> appendText(Object value) {
        this.value += stringOf(value);
        return this;
    }

    /**
     * Append the characters.
     * 
     * @param value Characters.
     * @param offset An offset.
     * @param length A length.
     * @return This builder.
     */
    private AbstractStringBuilder<T> appendChars(char[] value, int offset, int length) {
        for (int i = offset; i < offset + length; i++) {
            this.value += value[i];
        }
        return this;
    }

    /**
     * Insert the text.
     * 
     * @param index An index.
     * @param value A text.
     * @return This builder.
     */
    private AbstractStringBuilder<T> insertText(int index, String value) {
        this.value = this.value.substring(0, index) + value + this.value.substring(index);
        return this;
    }

    /**
     * Convert the value into a string.
     * 
     * @param value A value.
     * @return A text.
     */
    private static String stringOf(Object value) {
        return value == null ? "null" : String.valueOf(value);
    }
}
