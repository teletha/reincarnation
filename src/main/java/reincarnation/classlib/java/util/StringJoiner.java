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

/**
 * The Java implementation of {@link java.util.StringJoiner} which is transpiled into JavaScript.
 */
public final class StringJoiner {

    /** The delimiter. */
    private final String delimiter;

    /** The prefix. */
    private final String prefix;

    /** The suffix. */
    private final String suffix;

    /** The current value. */
    private String value = "";

    /** The value when empty. */
    private String emptyValue;

    /** Whether this joiner has any value. */
    private boolean empty = true;

    /**
     * Create a joiner.
     * 
     * @param delimiter A delimiter.
     */
    public StringJoiner(CharSequence delimiter) {
        this(delimiter, "", "");
    }

    /**
     * Create a joiner.
     * 
     * @param delimiter A delimiter.
     * @param prefix A prefix.
     * @param suffix A suffix.
     */
    public StringJoiner(CharSequence delimiter, CharSequence prefix, CharSequence suffix) {
        this.delimiter = String.valueOf(delimiter);
        this.prefix = String.valueOf(prefix);
        this.suffix = String.valueOf(suffix);
    }

    /**
     * Add the value.
     * 
     * @param value A value.
     * @return This joiner.
     */
    public StringJoiner add(CharSequence value) {
        String text = String.valueOf(value);
        if (empty) {
            this.value = text;
            empty = false;
        } else {
            this.value += delimiter + text;
        }
        return this;
    }

    /**
     * Merge the other joiner.
     * 
     * @param other Another joiner.
     * @return This joiner.
     */
    public StringJoiner merge(StringJoiner other) {
        if (!other.empty) {
            if (empty) {
                value = other.value;
                empty = false;
            } else {
                value += delimiter + other.value;
            }
        }
        return this;
    }

    /**
     * Set the value when empty.
     * 
     * @param value A value.
     * @return This joiner.
     */
    public StringJoiner setEmptyValue(CharSequence value) {
        this.emptyValue = String.valueOf(value);
        return this;
    }

    /**
     * Get the length.
     * 
     * @return A length.
     */
    public int length() {
        return toString().length();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if (empty) {
            return emptyValue == null ? prefix + suffix : emptyValue;
        }
        return prefix + value + suffix;
    }
}
