/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.util.concurrent.atomic;

import reincarnation.classlib.java.io.Serializable;

/**
 * The Java implementation of {@link java.util.concurrent.atomic.AtomicBoolean} which is transpiled
 * into JavaScript.
 */
public class AtomicBoolean implements Serializable {

    /** The value. */
    private boolean value;

    /**
     * Create the atomic boolean.
     */
    public AtomicBoolean() {
    }

    /**
     * Create the atomic boolean.
     * 
     * @param initialValue An initial value.
     */
    public AtomicBoolean(boolean initialValue) {
        this.value = initialValue;
    }

    /**
     * Get the value.
     * 
     * @return A value.
     */
    public final boolean get() {
        return value;
    }

    /**
     * Set the value.
     * 
     * @param newValue A value.
     */
    public final void set(boolean newValue) {
        this.value = newValue;
    }

    /**
     * Set the value.
     * 
     * @param newValue A value.
     */
    public final void lazySet(boolean newValue) {
        this.value = newValue;
    }

    /**
     * Set and get the value.
     * 
     * @param newValue A value.
     * @return A previous value.
     */
    public final boolean getAndSet(boolean newValue) {
        boolean previous = value;
        value = newValue;
        return previous;
    }

    /**
     * Compare and set the value.
     * 
     * @param expect An expected value.
     * @param update An update value.
     * @return A result.
     */
    public final boolean compareAndSet(boolean expect, boolean update) {
        if (value == expect) {
            value = update;
            return true;
        }
        return false;
    }

    /**
     * Compare and set the value.
     * 
     * @param expect An expected value.
     * @param update An update value.
     * @return A result.
     */
    public boolean weakCompareAndSet(boolean expect, boolean update) {
        return compareAndSet(expect, update);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
