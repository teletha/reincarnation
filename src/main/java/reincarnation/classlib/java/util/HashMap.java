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
 * The Java implementation of {@link java.util.HashMap}.
 */
public class HashMap<K, V> extends AbstractMap<K, V>
        implements Map, reincarnation.classlib.java.lang.Cloneable, reincarnation.classlib.java.io.Serializable {

    /**
     * Create an empty map.
     */
    public HashMap() {
        super();
    }

    /**
     * Create an empty map.
     * 
     * @param capacity A capacity.
     */
    public HashMap(int capacity) {
        super();
    }

    /**
     * Create an empty map.
     * 
     * @param capacity A capacity.
     * @param loadFactor A load factor.
     */
    public HashMap(int capacity, float loadFactor) {
        super();
    }

    /**
     * Create a map from the map.
     * 
     * @param map A map.
     */
    public HashMap(java.util.Map map) {
        super(map);
    }
}
