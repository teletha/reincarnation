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

import java.util.Comparator;

import reincarnation.classlib.java.util.AbstractMap;

/**
 * The Java implementation of {@link java.util.concurrent.ConcurrentSkipListMap}.
 */
public class ConcurrentSkipListMap<K, V> extends AbstractMap<K, V> implements ConcurrentNavigableMap<K, V>,
        reincarnation.classlib.java.lang.Cloneable, reincarnation.classlib.java.io.Serializable {

    /**
     * Create an empty map.
     */
    public ConcurrentSkipListMap() {
        super();
    }

    /**
     * Create an empty map.
     * 
     * @param comparator A comparator.
     */
    public ConcurrentSkipListMap(Comparator comparator) {
        super();
    }

    /**
     * Create a map from the map.
     * 
     * @param map A map.
     */
    public ConcurrentSkipListMap(java.util.Map map) {
        super(map);
    }

    /**
     * Create a map from the sorted map.
     * 
     * @param map A sorted map.
     */
    public ConcurrentSkipListMap(java.util.SortedMap map) {
        super(map);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object put(Object key, Object value) {
        Object previous = super.put(key, value);
        sort();
        return previous;
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
    public Object firstKey() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        return keys[0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object lastKey() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        return keys[size() - 1];
    }

    /**
     * Sort the entries by the key.
     */
    private void sort() {
        for (int i = 1; i < size(); i++) {
            Object key = keys[i];
            Object value = values[i];
            int index = i - 1;
            while (0 <= index && compare(keys[index], key) > 0) {
                keys[index + 1] = keys[index];
                values[index + 1] = values[index];
                index--;
            }
            keys[index + 1] = key;
            values[index + 1] = value;
        }
    }

    /**
     * Compare the keys.
     * 
     * @param left A left key.
     * @param right A right key.
     * @return A compared result.
     */
    private static int compare(Object left, Object right) {
        return ((Comparable) left).compareTo(right);
    }
}
