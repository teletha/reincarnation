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

import java.util.Objects;

/**
 * The Java implementation of {@link java.util.Map.Entry} which is transpiled into JavaScript.
 */
public final class Entry implements java.util.Map.Entry {

    /** The key. */
    private final Object key;

    /** The value. */
    private Object value;

    /**
     * Create the entry.
     * 
     * @param key A key.
     * @param value A value.
     */
    public Entry(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Get the key.
     * 
     * @return A key.
     */
    @Override
    public Object getKey() {
        return key;
    }

    /**
     * Get the value.
     * 
     * @return A value.
     */
    @Override
    public Object getValue() {
        return value;
    }

    /**
     * Set the value.
     * 
     * @param value A value.
     * @return A previous value.
     */
    @Override
    public Object setValue(Object value) {
        Object previous = this.value;
        this.value = value;
        return previous;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Entry otherEntry) {
            return Objects.equals(key, otherEntry.key) && Objects.equals(value, otherEntry.value);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(key) ^ Objects.hashCode(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return key + "=" + value;
    }
}
