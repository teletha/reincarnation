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

import reincarnation.classlib.java.io.Serializable;
import reincarnation.classlib.java.lang.Cloneable;
import js.lang.NativeObject;

/**
 * The Java implementation of {@link java.util.EnumMap} which is transpiled into JavaScript.
 */
public class EnumMap<K extends Enum<K>, V> extends AbstractMap<K, V>
        implements Map, Cloneable, Serializable {

    /** The key type. */
    private final Class<K> keyType;

    /**
     * Create the map.
     * 
     * @param keyType A key type.
     */
    public EnumMap(Class<K> keyType) {
        if (keyType == null) {
            throw new NullPointerException("keyType");
        }
        this.keyType = keyType;
    }

    /**
     * Create the map.
     * 
     * @param map A map.
     */
    public EnumMap(EnumMap<K, ? extends V> map) {
        this(map.keyType);
        for (Object item : map.entrySet()) {
            java.util.Map.Entry entry = (java.util.Map.Entry) item;
            put((Enum) entry.getKey(), entry.getValue());
        }
    }

    /**
     * Create the map.
     * 
     * @param map A map.
     */
    public EnumMap(java.util.Map<K, ? extends V> map) {
        if (map == null) {
            throw new NullPointerException("map");
        }
        if (map.isEmpty()) {
            throw new IllegalArgumentException("Specified map is empty");
        }
        Object key = map.keySet().iterator().next();
        this.keyType = (Class<K>) NativeObject.classOf(key);
        putAll(map);
    }

    /**
     * Put the value.
     * 
     * @param key A key.
     * @param value A value.
     * @return A previous value.
     */
    public Object put(Enum key, Object value) {
        if (key == null) {
            throw new NullPointerException("key");
        }
        return super.put(key, value);
    }

    /**
     * Put all values.
     * 
     * @param map A map.
     */
    @Override
    public void putAll(java.util.Map map) {
        for (Object item : map.entrySet()) {
            java.util.Map.Entry entry = (java.util.Map.Entry) item;
            put((Enum) entry.getKey(), entry.getValue());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EnumMap<K, V> clone() {
        EnumMap<K, V> map = new EnumMap<>(keyType);
        for (int i = 0; i < size(); i++) {
            map.put((Enum) keys[i], values[i]);
        }
        return map;
    }
}
