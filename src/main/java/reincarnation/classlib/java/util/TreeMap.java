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

import java.util.Comparator;

/**
 * The Java implementation of {@link java.util.TreeMap}.
 */
public final class TreeMap extends Map {

    /**
     * Create an empty map.
     */
    public TreeMap() {
        super();
    }

    /**
     * Create an empty map.
     * 
     * @param comparator A comparator.
     */
    public TreeMap(Comparator comparator) {
        super();
    }

    /**
     * Create a map from the map.
     * 
     * @param map A map.
     */
    public TreeMap(java.util.Map map) {
        super(map);
    }

    /**
     * Create a map from the sorted map.
     * 
     * @param map A sorted map.
     */
    public TreeMap(java.util.SortedMap map) {
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
