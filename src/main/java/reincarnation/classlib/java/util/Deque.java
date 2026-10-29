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
 * The Java implementation of {@link java.util.Deque} which is transpiled into JavaScript.
 */
public interface Deque<E> extends Queue<E>, SequencedCollection<E> {

    /**
     * Add the value at the first.
     * 
     * @param value A value.
     */
    void addFirst(Object value);

    /**
     * Add the value at the last.
     * 
     * @param value A value.
     */
    void addLast(Object value);

    /**
     * Offer the value at the first.
     * 
     * @param value A value.
     * @return A result.
     */
    boolean offerFirst(Object value);

    /**
     * Offer the value at the last.
     * 
     * @param value A value.
     * @return A result.
     */
    boolean offerLast(Object value);

    /**
     * Remove the first value.
     * 
     * @return A value.
     */
    Object removeFirst();

    /**
     * Remove the last value.
     * 
     * @return A value.
     */
    Object removeLast();

    /**
     * Poll the first value.
     * 
     * @return A value or null.
     */
    Object pollFirst();

    /**
     * Poll the last value.
     * 
     * @return A value or null.
     */
    Object pollLast();

    /**
     * Get the first value.
     * 
     * @return A value.
     */
    Object getFirst();

    /**
     * Get the last value.
     * 
     * @return A value.
     */
    Object getLast();

    /**
     * Peek the first value.
     * 
     * @return A value or null.
     */
    Object peekFirst();

    /**
     * Peek the last value.
     * 
     * @return A value or null.
     */
    Object peekLast();

    /**
     * Push the value.
     * 
     * @param value A value.
     */
    void push(Object value);

    /**
     * Pop the first value.
     * 
     * @return A value.
     */
    Object pop();

    /**
     * Create the descending iterator.
     * 
     * @return An iterator.
     */
    Iterator descendingIterator();
}
