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

import java.util.NoSuchElementException;

/**
 * The Java implementation of {@link java.util.Iterator} which is transpiled into JavaScript.
 */
public final class Iterator implements java.util.Iterator {

    /** The list. */
    private final List owner;

    /** The current index. */
    private int index;

    /** The index of the value which was returned by the last {@code next} call. */
    private int last = -1;

    /**
     * Create the iterator.
     * 
     * @param owner A list.
     */
    Iterator(List owner) {
        this.owner = owner;
    }

    /**
     * Check whether the next value exists.
     * 
     * @return A result.
     */
    @Override
    public boolean hasNext() {
        return index < owner.size();
    }

    /**
     * Get the next value.
     * 
     * @return A value.
     */
    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        last = index;
        return owner.get(index++);
    }

    /**
     * Remove the current value.
     */
    @Override
    public void remove() {
        if (last < 0) {
            throw new IllegalStateException();
        }
        owner.remove(last);
        index = last;
        last = -1;
    }
}
