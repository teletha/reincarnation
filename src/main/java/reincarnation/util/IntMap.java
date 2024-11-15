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

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A simple array-based map implementation that uses integer indices as keys.
 * This implementation does not allow null values and treats negative indices as invalid.
 *
 * @param <V> The type of values stored in the map
 */
public class IntMap<V> {
    private Object[] values;

    /**
     * Constructs an IntMap with an initial capacity of 8 elements.
     */
    public IntMap() {
        values = new Object[8];
    }

    /**
     * Retrieves the value associated with the specified index.
     *
     * @param index The index whose associated value is to be returned
     * @return The value associated with the specified index, or null if the index is invalid or has
     *         no value
     */
    public V get(int index) {
        return index >= 0 && index < values.length ? (V) values[index] : null;
    }

    /**
     * Returns the value associated with the specified index, or computes a new value using
     * the provided supplier if no value exists. If a new value is computed, it is stored
     * in the map before being returned.
     *
     * @param index The index whose associated value is to be returned or computed
     * @param defaultProvider The supplier to generate a default value if none exists
     * @return The current (existing or computed) value associated with the specified index
     */
    public V get(int index, Supplier<V> defaultProvider) {
        if (!has(index)) {
            V value = defaultProvider.get();
            put(index, value);
            return value;
        }
        return get(index);
    }

    /**
     * Associates the specified value with the specified index in this map.
     * If the map previously contained a mapping for the index, the old value is replaced.
     * Automatically resizes the internal array if the index exceeds current capacity.
     *
     * @param index The index with which the specified value is to be associated
     * @param value The value to be associated with the specified index (must not be null)
     */
    public void put(int index, V value) {
        if (index < 0 || value == null) return;

        if (index >= values.length) {
            Object[] newValues = new Object[index * 2];
            System.arraycopy(values, 0, newValues, 0, values.length);
            values = newValues;
        }
        values[index] = value;
    }

    /**
     * Returns true if this map contains a value for the specified index.
     *
     * @param index The index whose presence in this map is to be tested
     * @return true if this map contains a non-null value for the specified index
     */
    public boolean has(int index) {
        return index >= 0 && index < values.length && values[index] != null;
    }

    /**
     * Removes the mapping for the specified index if present.
     *
     * @param index The index whose mapping is to be removed from the map
     * @return The previous value associated with the specified index, or null if there was no
     *         mapping
     */
    public V delete(int index) {
        if (!has(index)) return null;

        V old = (V) values[index];
        values[index] = null;
        return old;
    }

    /**
     * Returns an Optional containing the first value that matches the given predicate,
     * or an empty Optional if no value matches. The search is performed in ascending
     * order of indices.
     *
     * @param condition The predicate to match values against
     * @return An Optional containing the first matching value, or empty if none found
     */
    public Optional<V> find(Predicate<V> condition) {
        for (int i = 0; i < values.length; i++) {
            V value = (V) values[i];
            if (value != null && condition.test(value)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }
}