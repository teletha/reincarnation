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
 * The Java implementation of {@link java.util.AbstractQueue} which is transpiled into JavaScript.
 */
public abstract class AbstractQueue<E> extends AbstractCollection<E> implements Queue<E> {

    /**
     * Create an empty queue.
     */
    protected AbstractQueue() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean offer(Object value) {
        return add(value);
    }

    /**
     * {@inheritDoc}
     */
    public Object remove() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return poll();
    }

    /**
     * {@inheritDoc}
     */
    public Object poll() {
        return isEmpty() ? null : removeAt(0);
    }

    /**
     * {@inheritDoc}
     */
    public Object element() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return elements[0];
    }

    /**
     * {@inheritDoc}
     */
    public Object peek() {
        return isEmpty() ? null : elements[0];
    }
}
