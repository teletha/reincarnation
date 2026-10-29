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
import java.util.Comparator;

/**
 * The Java implementation of {@link java.util.PriorityQueue}.
 */
public final class PriorityQueue extends Queue {

    /**
     * Create an empty queue.
     */
    public PriorityQueue() {
        super();
    }

    /**
     * Create an empty queue.
     * 
     * @param capacity A capacity.
     */
    public PriorityQueue(int capacity) {
        super();
    }

    /**
     * Create an empty queue.
     * 
     * @param comparator A comparator.
     */
    public PriorityQueue(Comparator comparator) {
        super();
    }

    /**
     * Create an empty queue.
     * 
     * @param capacity A capacity.
     * @param comparator A comparator.
     */
    public PriorityQueue(int capacity, Comparator comparator) {
        super();
    }

    /**
     * Create a queue from the collection.
     * 
     * @param collection A collection.
     */
    public PriorityQueue(Collection collection) {
        super(collection);
    }

    /**
     * Create a queue from the priority queue.
     * 
     * @param queue A priority queue.
     */
    public PriorityQueue(java.util.PriorityQueue queue) {
        super(queue);
    }

    /**
     * Create a queue from the sorted set.
     * 
     * @param set A sorted set.
     */
    public PriorityQueue(java.util.SortedSet set) {
        super(set);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object poll() {
        if (isEmpty()) {
            return null;
        }
        sort();
        return super.poll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object peek() {
        if (isEmpty()) {
            return null;
        }
        sort();
        return super.peek();
    }

    /**
     * Sort the elements by the natural order.
     */
    private void sort() {
        for (int i = 1; i < size(); i++) {
            Object value = elements[i];
            int index = i - 1;
            while (0 <= index && compare(elements[index], value) > 0) {
                elements[index + 1] = elements[index];
                index--;
            }
            elements[index + 1] = value;
        }
    }

    /**
     * Compare the values.
     * 
     * @param left A left value.
     * @param right A right value.
     * @return A compared result.
     */
    private static int compare(Object left, Object right) {
        return ((Comparable) left).compareTo(right);
    }
}
