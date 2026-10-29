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

import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * The Java implementation of {@link java.util.Deque} which is transpiled into JavaScript.
 */
public class Deque extends Queue {

    /**
     * Create an empty deque.
     */
    public Deque() {
        super();
    }

    /**
     * Create a deque from the collection.
     * 
     * @param collection A collection.
     */
    public Deque(Collection collection) {
        super(collection);
    }

    /**
     * Add the value at the first.
     * 
     * @param value A value.
     */
    public void addFirst(Object value) {
        insert(0, value);
    }

    /**
     * Add the value at the last.
     * 
     * @param value A value.
     */
    public void addLast(Object value) {
        add(value);
    }

    /**
     * Offer the value at the first.
     * 
     * @param value A value.
     * @return A result.
     */
    public boolean offerFirst(Object value) {
        insert(0, value);
        return true;
    }

    /**
     * Offer the value at the last.
     * 
     * @param value A value.
     * @return A result.
     */
    public boolean offerLast(Object value) {
        return add(value);
    }

    /**
     * Poll the first value.
     * 
     * @return A value or null.
     */
    public Object pollFirst() {
        return poll();
    }

    /**
     * Poll the last value.
     * 
     * @return A value or null.
     */
    public Object pollLast() {
        return isEmpty() ? null : removeAt(size() - 1);
    }

    /**
     * Peek the first value.
     * 
     * @return A value or null.
     */
    public Object peekFirst() {
        return peek();
    }

    /**
     * Peek the last value.
     * 
     * @return A value or null.
     */
    public Object peekLast() {
        return isEmpty() ? null : get(size() - 1);
    }

    /**
     * Push the value.
     * 
     * @param value A value.
     */
    public void push(Object value) {
        addFirst(value);
    }

    /**
     * Pop the first value.
     * 
     * @return A value.
     */
    public Object pop() {
        return removeFirst();
    }

    /**
     * Remove the first value.
     * 
     * @return A value.
     */
    public Object removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return poll();
    }

    /**
     * Remove the last value.
     * 
     * @return A value.
     */
    public Object removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return removeAt(size() - 1);
    }

    /**
     * Get the first value.
     * 
     * @return A value.
     */
    public Object getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return get(0);
    }

    /**
     * Get the last value.
     * 
     * @return A value.
     */
    public Object getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return get(size() - 1);
    }

    /**
     * Create the descending iterator.
     * 
     * @return An iterator.
     */
    public java.util.Iterator descendingIterator() {
        return new QueueIterator(this, true);
    }
}
