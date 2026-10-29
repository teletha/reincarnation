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
 * The Java implementation of {@link java.util.LinkedList}.
 */
public class LinkedList<E> extends AbstractSequentialList<E>
        implements List<E>, Deque<E>, reincarnation.classlib.java.lang.Cloneable, reincarnation.classlib.java.io.Serializable {

    /**
     * Create an empty list.
     */
    public LinkedList() {
        super();
    }

    /**
     * Create a list from the collection.
     * 
     * @param collection A collection.
     */
    public LinkedList(Collection collection) {
        super();
        for (Object value : collection) {
            add(value);
        }
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
     * Offer the value.
     * 
     * @param value A value.
     * @return A result.
     */
    public boolean offer(Object value) {
        return add(value);
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
     * Poll the first value.
     * 
     * @return A value or null.
     */
    public Object poll() {
        return isEmpty() ? null : removeAt(0);
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
    public Object peek() {
        return isEmpty() ? null : get(0);
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
     * Remove the first value.
     * 
     * @return A value.
     */
    public Object remove() {
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
        return removeAt(0);
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
     * Get the element.
     * 
     * @return A value.
     */
    public Object element() {
        return getFirst();
    }

    /**
     * Create the descending iterator.
     * 
     * @return An iterator.
     */
    public Iterator descendingIterator() {
        return new ArrayIterator(this, true);
    }
}
