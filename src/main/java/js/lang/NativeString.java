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

import java.util.regex.Pattern;

/**
 * The JavaScript {@code String}.
 * <p>
 * The Java implementation mimics the JavaScript behavior so that the same test can run on the JVM.
 * </p>
 */
@Native
public final class NativeString {

    /**
     * Hide constructor.
     */
    private NativeString() {
    }

    /**
     * Create a string from the character code.
     * 
     * @param code A character code.
     * @return A string.
     */
    @Native("String.fromCharCode($0)")
    public static String fromCharCode(int code) {
        return String.valueOf((char) code);
    }

    /**
     * Create a string from the code point.
     * 
     * @param code A code point.
     * @return A string.
     */
    @Native("String.fromCodePoint($0)")
    public static String fromCodePoint(int code) {
        return new String(Character.toChars(code));
    }

    /**
     * Get the length.
     * 
     * @param value A string.
     * @return A length.
     */
    @Native("$0.length")
    public static int length(String value) {
        return value.length();
    }

    /**
     * Get the character code at the specified index.
     * 
     * @param value A string.
     * @param index An index.
     * @return A character code.
     */
    @Native("$0.charCodeAt($1)")
    public static int charCodeAt(String value, int index) {
        return value.charAt(index);
    }

    /**
     * Get the code point at the specified index.
     * 
     * @param value A string.
     * @param index An index.
     * @return A code point.
     */
    @Native("$0.codePointAt($1)")
    public static int codePointAt(String value, int index) {
        return value.codePointAt(index);
    }

    /**
     * Get the character at the specified index.
     * 
     * @param value A string.
     * @param index An index.
     * @return A character.
     */
    @Native("$0.charAt($1)")
    public static String charAt(String value, int index) {
        return String.valueOf(value.charAt(index));
    }

    /**
     * Extract a substring.
     * 
     * @param value A string.
     * @param start A start index.
     * @return A substring.
     */
    @Native("$0.substring($1)")
    public static String substring(String value, int start) {
        return value.substring(clamp(value.length(), start), value.length());
    }

    /**
     * Extract a substring.
     * 
     * @param value A string.
     * @param start A start index.
     * @param end An end index.
     * @return A substring.
     */
    @Native("$0.substring($1, $2)")
    public static String substring(String value, int start, int end) {
        int from = clamp(value.length(), start);
        int to = clamp(value.length(), end);
        return value.substring(Math.min(from, to), Math.max(from, to));
    }

    /**
     * Find the index of the specified text.
     * 
     * @param value A string.
     * @param search A search text.
     * @return An index or -1.
     */
    @Native("$0.indexOf($1)")
    public static int indexOf(String value, String search) {
        return value.indexOf(search);
    }

    /**
     * Check whether the string contains the specified text.
     * 
     * @param value A string.
     * @param search A search text.
     * @return A result.
     */
    @Native("$0.includes($1)")
    public static boolean includes(String value, String search) {
        return value.contains(search);
    }

    /**
     * Check whether the string starts with the specified text.
     * 
     * @param value A string.
     * @param search A search text.
     * @return A result.
     */
    @Native("$0.startsWith($1)")
    public static boolean startsWith(String value, String search) {
        return value.startsWith(search);
    }

    /**
     * Check whether the string ends with the specified text.
     * 
     * @param value A string.
     * @param search A search text.
     * @return A result.
     */
    @Native("$0.endsWith($1)")
    public static boolean endsWith(String value, String search) {
        return value.endsWith(search);
    }

    /**
     * Convert to lower case.
     * 
     * @param value A string.
     * @return A converted string.
     */
    @Native("$0.toLowerCase()")
    public static String toLowerCase(String value) {
        return value.toLowerCase();
    }

    /**
     * Convert to upper case.
     * 
     * @param value A string.
     * @return A converted string.
     */
    @Native("$0.toUpperCase()")
    public static String toUpperCase(String value) {
        return value.toUpperCase();
    }

    /**
     * Trim the whitespace.
     * 
     * @param value A string.
     * @return A trimmed string.
     */
    @Native("$0.trim()")
    public static String trim(String value) {
        return value.trim();
    }

    /**
     * Split the string.
     * 
     * @param value A string.
     * @param separator A separator.
     * @return An array.
     */
    @Native("$0.split($1)")
    public static NativeArray split(String value, String separator) {
        NativeArray array = new NativeArray();
        for (String part : value.split(Pattern.quote(separator), -1)) {
            array.push(part);
        }
        return array;
    }

    /**
     * Format the values.
     * 
     * @param format A format.
     * @param values Values.
     * @return A formatted text.
     */
    @Native("$rt_format($0, $1)")
    public static String format(String format, Object... values) {
        return String.format(format, values);
    }

    /**
     * Get the platform line separator.
     * 
     * @return A line separator.
     */
    @Native("(typeof process !== \"undefined\" && process.platform === \"win32\" ? \"\\r\\n\" : \"\\n\")")
    public static String lineSeparator() {
        return java.lang.System.lineSeparator();
    }

    /**
     * Clamp the index.
     * 
     * @param length A string length.
     * @param index An index.
     * @return A clamped index.
     */
    private static int clamp(int length, int index) {
        return index < 0 ? 0 : Math.min(index, length);
    }
}
