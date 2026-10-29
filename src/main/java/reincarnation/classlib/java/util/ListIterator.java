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
 * The Java implementation of {@link java.util.ListIterator} which is transpiled into JavaScript.
 */
public interface ListIterator<E> extends Iterator<E> {

    /**
     * Check whether the previous value exists.
     * 
     * @return A result.
     */
    boolean hasPrevious();

    /**
     * Get the previous value.
     * 
     * @return A value.
     */
    Object previous();

    /**
     * Get the next index.
     * 
     * @return An index.
     */
    int nextIndex();

    /**
     * Get the previous index.
     * 
     * @return An index.
     */
    int previousIndex();

    /**
     * Set the current value.
     * 
     * @param value A value.
     */
    void set(Object value);

    /**
     * Add the value.
     * 
     * @param value A value.
     */
    void add(Object value);
}
