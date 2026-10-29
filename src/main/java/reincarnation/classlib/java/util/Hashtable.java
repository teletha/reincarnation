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
 * The Java implementation of {@link java.util.Hashtable}.
 */
public class Hashtable<K, V> extends Dictionary<K, V>
        implements Map, reincarnation.classlib.java.lang.Cloneable, reincarnation.classlib.java.io.Serializable {

    /**
     * Create an empty table.
     */
    public Hashtable() {
        super();
    }

    /**
     * Create an empty table.
     * 
     * @param capacity A capacity.
     */
    public Hashtable(int capacity) {
        super();
    }

    /**
     * Create an empty table.
     * 
     * @param capacity A capacity.
     * @param loadFactor A load factor.
     */
    public Hashtable(int capacity, float loadFactor) {
        super();
    }

    /**
     * Create an empty table.
     * 
     * @param dummy A dummy value.
     */
    Hashtable(Void dummy) {
        super();
    }

    /**
     * Create a table from the map.
     * 
     * @param map A map.
     */
    public Hashtable(java.util.Map map) {
        super(map);
    }
}
