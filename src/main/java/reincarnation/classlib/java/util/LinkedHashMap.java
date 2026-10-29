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
 * The Java implementation of {@link java.util.LinkedHashMap}.
 */
public class LinkedHashMap<K, V> extends HashMap<K, V> implements SequencedMap<K, V> {

    /**
     * Create an empty map.
     */
    public LinkedHashMap() {
        super();
    }

    /**
     * Create an empty map.
     * 
     * @param capacity A capacity.
     */
    public LinkedHashMap(int capacity) {
        super();
    }

    /**
     * Create an empty map.
     * 
     * @param capacity A capacity.
     * @param loadFactor A load factor.
     */
    public LinkedHashMap(int capacity, float loadFactor) {
        super();
    }

    /**
     * Create an empty map.
     * 
     * @param capacity A capacity.
     * @param loadFactor A load factor.
     * @param accessOrder Whether the order is the access order.
     */
    public LinkedHashMap(int capacity, float loadFactor, boolean accessOrder) {
        super();
    }

    /**
     * Create a map from the map.
     * 
     * @param map A map.
     */
    public LinkedHashMap(java.util.Map map) {
        super(map);
    }
}
