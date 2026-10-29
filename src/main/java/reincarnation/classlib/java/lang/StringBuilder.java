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

/**
 * The Java implementation of {@link java.lang.StringBuilder} which is transpiled into JavaScript.
 */
public final class StringBuilder extends AbstractStringBuilder<StringBuilder> {

    /**
     * Create an empty builder.
     */
    public StringBuilder() {
        super();
    }

    /**
     * Create an empty builder.
     * 
     * @param capacity A capacity.
     */
    public StringBuilder(int capacity) {
        super(capacity);
    }

    /**
     * Create a builder with the text.
     * 
     * @param value A text.
     */
    public StringBuilder(String value) {
        super(value);
    }

    /**
     * Create a builder with the text.
     * 
     * @param value A text.
     */
    public StringBuilder(CharSequence value) {
        super(value);
    }

    /**
     * Compare with the other builder.
     * 
     * @param other Another builder.
     * @return A comparison result.
     */
    public int compareTo(StringBuilder other) {
        return toString().compareTo(other.toString());
    }
}
