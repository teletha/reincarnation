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
 * The Java implementation of {@link java.lang.StringBuffer} which is transpiled into JavaScript.
 * <p>
 * JavaScript is single threaded, so this implementation shares {@link AbstractStringBuilder}.
 * </p>
 */
public final class StringBuffer extends AbstractStringBuilder<StringBuffer> implements Appendable, reincarnation.classlib.java.io.Serializable, Comparable<StringBuffer>, CharSequence {

    /**
     * Create an empty buffer.
     */
    public StringBuffer() {
        super();
    }

    /**
     * Create an empty buffer.
     * 
     * @param capacity A capacity.
     */
    public StringBuffer(int capacity) {
        super(capacity);
    }

    /**
     * Create a buffer with the text.
     * 
     * @param value A text.
     */
    public StringBuffer(String value) {
        super(value);
    }

    /**
     * Create a buffer with the text.
     * 
     * @param value A text.
     */
    public StringBuffer(java.lang.CharSequence value) {
        super(value);
    }

    /**
     * Compare with the other buffer.
     * 
     * @param other Another buffer.
     * @return A comparison result.
     */
    public int compareTo(StringBuffer other) {
        return toString().compareTo(other.toString());
    }
}
