/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.util.concurrent;

import java.util.NoSuchElementException;

import reincarnation.classlib.java.util.AbstractCollection;
import reincarnation.classlib.java.util.ArrayIterator;
import reincarnation.classlib.java.util.Deque;

/**
 * The Java implementation of {@link java.util.concurrent.ConcurrentLinkedDeque}.
 */
public class ConcurrentLinkedDeque<E> extends AbstractCollection<E>
        implements Deque<E>, reincarnation.classlib.java.io.Serializable {

    /**
     * Create an empty deque.
     */
    public ConcurrentLinkedDeque() {
        super();
    }

    /**
     * Create a deque from the collection.
     * 
     * @param collection A collection.
     */
    public ConcurrentLinkedDeque(java.util.Collection collection) {
        super();
        for (Object value : collection) {
            add(value);
        }
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
    @Override
    public Object remove() {
        return removeFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object poll() {
        return pollFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object element() {
        return getFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object peek() {
        return peekFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addFirst(Object value) {
        insert(0, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addLast(Object value) {
        add(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean offerFirst(Object value) {
        insert(0, value);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean offerLast(Object value) {
        return add(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return removeAt(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return removeAt(size() - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object pollFirst() {
        return isEmpty() ? null : removeAt(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object pollLast() {
        return isEmpty() ? null : removeAt(size() - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return get(size() - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object peekFirst() {
        return isEmpty() ? null : get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object peekLast() {
        return isEmpty() ? null : get(size() - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void push(Object value) {
        addFirst(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object pop() {
        return removeFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public reincarnation.classlib.java.util.Iterator descendingIterator() {
        return new ArrayIterator(this, true);
    }
}
