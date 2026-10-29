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
public final class TreeSet extends Set {

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
        super(collection);
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
        super(set);
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
}
