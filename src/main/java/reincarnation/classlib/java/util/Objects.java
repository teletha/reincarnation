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

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * The Java implementation of {@link java.util.Objects} which is transpiled into JavaScript.
 */
public final class Objects {

    /**
     * Hide constructor.
     */
    private Objects() {
    }

    /**
     * Check whether the values are equal.
     * 
     * @param left A value.
     * @param right A value.
     * @return A result.
     */
    public static boolean equals(Object left, Object right) {
        return left == right || (left != null && left.equals(right));
    }

    /**
     * Check whether the values are deeply equal.
     * 
     * @param left A value.
     * @param right A value.
     * @return A result.
     */
    public static boolean deepEquals(Object left, Object right) {
        return Arrays.deepEquals((Object[]) left, (Object[]) right);
    }

    /**
     * Get the hash code.
     * 
     * @param value A value.
     * @return A hash code.
     */
    public static int hashCode(Object value) {
        return value == null ? 0 : value.hashCode();
    }

    /**
     * Get the combined hash code.
     * 
     * @param values Values.
     * @return A hash code.
     */
    public static int hash(Object... values) {
        return Arrays.hashCode(values);
    }

    /**
     * Get the text.
     * 
     * @param value A value.
     * @return A text.
     */
    public static String toString(Object value) {
        return String.valueOf(value);
    }

    /**
     * Get the text.
     * 
     * @param value A value.
     * @param defaultValue A default value.
     * @return A text.
     */
    public static String toString(Object value, String defaultValue) {
        return value == null ? defaultValue : value.toString();
    }

    /**
     * Check whether the value is null.
     * 
     * @param value A value.
     * @return A result.
     */
    public static boolean isNull(Object value) {
        return value == null;
    }

    /**
     * Check whether the value is not null.
     * 
     * @param value A value.
     * @return A result.
     */
    public static boolean nonNull(Object value) {
        return value != null;
    }

    /**
     * Require the non-null value.
     * 
     * @param value A value.
     * @return A value.
     */
    public static Object requireNonNull(Object value) {
        if (value == null) {
            throw new NullPointerException();
        }
        return value;
    }

    /**
     * Require the non-null value.
     * 
     * @param value A value.
     * @param message A message.
     * @return A value.
     */
    public static Object requireNonNull(Object value, String message) {
        if (value == null) {
            throw new NullPointerException(message);
        }
        return value;
    }

    /**
     * Require the non-null value.
     * 
     * @param value A value.
     * @param message A message supplier.
     * @return A value.
     */
    public static Object requireNonNull(Object value, Supplier<String> message) {
        if (value == null) {
            throw new NullPointerException(message.get());
        }
        return value;
    }

    /**
     * Return the value or the default value.
     * 
     * @param value A value.
     * @param defaultValue A default value.
     * @return A value.
     */
    public static Object requireNonNullElse(Object value, Object defaultValue) {
        return value == null ? requireNonNull(defaultValue) : value;
    }

    /**
     * Return the value or the supplied value.
     * 
     * @param value A value.
     * @param supplier A supplier.
     * @return A value.
     */
    public static Object requireNonNullElseGet(Object value, Supplier<Object> supplier) {
        return value == null ? requireNonNull(supplier.get()) : value;
    }
}
