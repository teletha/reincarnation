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
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Function;

import kiss.I;
import kiss.Signal;

/**
 * MultiMap provides a generic way to store multiple values associated with the same key. This class
 * is similar to a Map<K, List<V>>, but it provides additional convenience methods to make it easier
 * to work with a list of values for a given key. By default, duplicate values for a key are not
 * allowed, but this can be configured using the acceptDuplication flag in the constructor.
 * 
 * @param <K> The type of keys maintained by this map.
 * @param <V> The type of mapped values.
 */
public class MultiMap<K, V> {

    /** The actual map. */
    private Map<K, List<V>> map = new LinkedHashMap();

    /** The flag for whether to accept duplicated values for the same key or not. */
    private final boolean acceptDuplication;

    /**
     * Constructs a new instance of {@link MultiMap}.
     * 
     * @param acceptDuplication The flag for whether to accept duplicated values for the same key or
     *            not.
     */
    public MultiMap(boolean acceptDuplication) {
        this.acceptDuplication = acceptDuplication;
    }

    /**
     * Sorts this map by key using the specified {@link Comparator}.
     * 
     * @param comparator The {@link Comparator} to be used to sort the map.
     */
    public void sort(Comparator<? super K> comparator) {
        TreeMap<K, List<V>> sortable = new TreeMap(comparator);
        sortable.putAll(map);
        this.map = sortable;
    }

    /**
     * Gets all values associated with the specified key.
     * 
     * @param key The key for which the values are to be retrieved.
     * @return A list of all values associated with the specified key.
     */
    public List<V> get(K key) {
        return map.getOrDefault(key, Collections.EMPTY_LIST);
    }

    /**
     * Associates the specified value with the specified key in this map.
     * 
     * @param key The key with which to associate the specified value.
     * @param value The value to be associated with the specified key.
     */
    public void put(K key, V value) {
        List<V> list = map.computeIfAbsent(key, k -> new ArrayList());

        if (acceptDuplication || !list.contains(value)) {
            list.add(value);
        }
    }

    /**
     * Associates all the specified values with the specified key in this map.
     * 
     * @param key The key with which to associate the specified values.
     * @param values The values to be associated with the specified key.
     */
    public void putAll(K key, List<V> values) {
        for (V value : values) {
            put(key, value);
        }
    }

    /**
     * Removes all values associated with the specified key from this map.
     * 
     * @param key The key for which all values are to be removed.
     */
    public void remove(K key) {
        // don't use Map#remove
        // map.remove(key);
        // why?
        map.keySet().removeIf(x -> Objects.equals(x, key));
    }

    /**
     * Removes the specified value associated with the specified key from this map.
     * 
     * @param key The key with which the specified value is associated.
     * @param value The value to be removed from the specified key.
     */
    public void remove(K key, V value) {
        List<V> list = map.get(key);
        if (list != null) {
            list.remove(value);
        }
    }

    /**
     * Traverses all keys in this {@link MultiMap} and returns a {@link Signal} that emits each key
     * sequentially.
     * 
     * @return
     */
    public Signal<K> keys() {
        return I.signal(map.keySet());
    }

    /**
     * Returns a new {@link MultiMap} with the keys of this map converted using the specified
     * {@link Function}.
     * 
     * @param <N> The new type of keys in the returned map.
     * @param converter The {@link Function} to be applied to each key in this map to obtain the new
     *            key in the returned map.
     * @return A new {@link MultiMap} with the keys of this map converted using the specified
     *         {@link Function}.
     */
    public <N> MultiMap<N, V> convertKeys(Function<K, N> converter) {
        MultiMap created = new MultiMap(acceptDuplication);
        forEach((key, values) -> {
            created.putAll(converter.apply(key), values);
        });
        return created;
    }

    /**
     * Performs the given action for each key-value pair in this map until all pairs have been
     * processed or the action throws an exception.
     * 
     * @param handler The action to be performed for each key-value pair.
     */
    public void forEach(BiConsumer<K, List<V>> handler) {
        map.forEach(handler);
    }
}
