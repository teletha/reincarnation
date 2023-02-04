/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class MultiMap<K, V> {

    /** The actual map. */
    private final Map<K, List<V>> map = new LinkedHashMap();

    /** The set mode. */
    private final boolean acceptDuplication;

    /**
     * @param acceptDuplication
     */
    public MultiMap(boolean acceptDuplication) {
        this.acceptDuplication = acceptDuplication;
    }

    /**
     * Get all values by key.
     * 
     * @param key
     * @return
     */
    public List<V> get(K key) {
        return map.getOrDefault(key, Collections.EMPTY_LIST);
    }

    /**
     * Put the value by key.
     * 
     * @param key
     * @param value
     */
    public void put(K key, V value) {
        List<V> list = map.computeIfAbsent(key, k -> new ArrayList());

        if (acceptDuplication || !list.contains(value)) {
            list.add(value);
        }
    }

    /**
     * Remove all values from key.
     * 
     * @param key
     */
    public void remove(K key) {
        map.remove(key);
    }

    /**
     * Remove the specified value from key.
     * 
     * @param key
     * @param value
     */
    public void remove(K key, V value) {
        List<V> list = map.get(key);
        if (list != null) {
            list.remove(value);
        }
    }

    /**
     * Traverse all values.
     * 
     * @param handler
     */
    public void forEach(BiConsumer<K, List<V>> handler) {
        map.forEach(handler);
    }
}
