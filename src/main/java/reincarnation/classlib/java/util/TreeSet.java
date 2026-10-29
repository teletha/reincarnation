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

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

/**
 * The Java implementation of {@link java.util.TreeSet}.
 */
public class TreeSet<E> extends AbstractSet<E> implements NavigableSet<E>,
        reincarnation.classlib.java.lang.Cloneable, reincarnation.classlib.java.io.Serializable {

    /**
     * Create an empty set.
     */
    public TreeSet() {
        super();
    }

    /**
     * Create a set from the collection.
     * 
     * @param collection A collection.
     */
    public TreeSet(Collection collection) {
        super();
        for (Object value : collection) {
            add(value);
        }
    }

    /**
     * Create an empty set.
     * 
     * @param comparator A comparator.
     */
    public TreeSet(Comparator comparator) {
        super();
    }

    /**
     * Create a set from the sorted set.
     * 
     * @param set A sorted set.
     */
    public TreeSet(java.util.SortedSet set) {
        super();
        for (Object value : set) {
            add(value);
        }
    }

    /**
     * Create a set from the navigable map.
     * 
     * @param map A navigable map.
     */
    TreeSet(java.util.NavigableMap map) {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(Object value) {
        boolean changed = super.add(value);
        if (changed) {
            Arrays.sort(elements, 0, size());
        }
        return changed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object comparator() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object first() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        return elements[0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object last() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        return elements[size() - 1];
    }

    /**
     * Remove and return the first value.
     * 
     * @return A value or null.
     */
    public Object pollFirst() {
        return isEmpty() ? null : removeAt(0);
    }

    /**
     * Remove and return the last value.
     * 
     * @return A value or null.
     */
    public Object pollLast() {
        return isEmpty() ? null : removeAt(size() - 1);
    }
}
