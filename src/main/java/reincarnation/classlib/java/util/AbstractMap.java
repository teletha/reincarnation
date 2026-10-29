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
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * The Java implementation of {@link java.util.AbstractMap} which is transpiled into JavaScript.
 */
public abstract class AbstractMap<K, V> implements Map {

    /** The keys. */
    protected Object[] keys = new Object[0];

    /** The values. */
    protected Object[] values = new Object[0];

    /** The size. */
    private int size;

    /**
     * Create an empty map.
     */
    protected AbstractMap() {
    }

    /**
     * Create a map from the map.
     * 
     * @param map A map.
     */
    protected AbstractMap(java.util.Map map) {
        putAll(map);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object get(Object key) {
        int index = indexOf(key);
        return index < 0 ? null : values[index];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getOrDefault(Object key, Object defaultValue) {
        int index = indexOf(key);
        return index < 0 ? defaultValue : values[index];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object put(Object key, Object value) {
        int index = indexOf(key);
        if (index < 0) {
            ensure(size + 1);
            keys[size] = key;
            values[size++] = value;
            return null;
        }
        Object previous = values[index];
        values[index] = value;
        return previous;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object remove(Object key) {
        int index = indexOf(key);
        if (index < 0) {
            return null;
        }
        Object removed = values[index];
        removeAt(index);
        return removed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object key, Object value) {
        int index = indexOf(key);
        if (index < 0 || !Objects.equals(values[index], value)) {
            return false;
        }
        removeAt(index);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putAll(java.util.Map map) {
        for (Object item : map.entrySet()) {
            java.util.Map.Entry entry = (java.util.Map.Entry) item;
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(Object key) {
        return 0 <= indexOf(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(values[i], value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            keys[i] = null;
            values[i] = null;
        }
        size = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set keySet() {
        return Set.of(Arrays.copyOf(keys, size));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List values() {
        return List.of(Arrays.copyOf(values, size));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set entrySet() {
        Map.Entry[] entries = new Map.Entry[size];
        for (int i = 0; i < size; i++) {
            entries[i] = new SimpleEntry(keys[i], values[i]);
        }
        return Set.of(entries);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void forEach(BiConsumer action) {
        for (int i = 0; i < size; i++) {
            action.accept(keys[i], values[i]);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object putIfAbsent(Object key, Object value) {
        int index = indexOf(key);
        if (index < 0) {
            put(key, value);
            return null;
        }
        return values[index];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object computeIfAbsent(Object key, Function mappingFunction) {
        int index = indexOf(key);
        if (0 <= index) {
            return values[index];
        }
        Object value = mappingFunction.apply(key);
        if (value != null) {
            put(key, value);
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object computeIfPresent(Object key, BiFunction remappingFunction) {
        int index = indexOf(key);
        if (index < 0) {
            return null;
        }
        Object value = remappingFunction.apply(key, values[index]);
        if (value == null) {
            removeAt(index);
        } else {
            values[index] = value;
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object compute(Object key, BiFunction remappingFunction) {
        int index = indexOf(key);
        Object value = remappingFunction.apply(key, index < 0 ? null : values[index]);
        if (value == null) {
            if (0 <= index) {
                removeAt(index);
            }
        } else if (0 <= index) {
            values[index] = value;
        } else {
            put(key, value);
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object merge(Object key, Object value, BiFunction remappingFunction) {
        int index = indexOf(key);
        if (index < 0) {
            if (value != null) {
                put(key, value);
            }
            return value;
        }
        Object merged = remappingFunction.apply(values[index], value);
        if (merged == null) {
            removeAt(index);
        } else {
            values[index] = merged;
        }
        return merged;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object replace(Object key, Object value) {
        int index = indexOf(key);
        if (index < 0) {
            return null;
        }
        Object previous = values[index];
        values[index] = value;
        return previous;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean replace(Object key, Object oldValue, Object newValue) {
        int index = indexOf(key);
        if (index < 0 || !Objects.equals(values[index], oldValue)) {
            return false;
        }
        values[index] = newValue;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void replaceAll(BiFunction function) {
        for (int i = 0; i < size; i++) {
            values[i] = function.apply(keys[i], values[i]);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Map otherMap) {
            if (size() != otherMap.size()) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!Objects.equals(values[i], otherMap.get(keys[i]))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 0;
        for (int i = 0; i < size; i++) {
            hash = (hash + (Objects.hashCode(keys[i]) ^ Objects.hashCode(values[i]))) | 0;
        }
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String text = "{";
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                text += ", ";
            }
            text += keys[i] + "=" + values[i];
        }
        return text + "}";
    }

    /**
     * Get the index of the key.
     * 
     * @param key A key.
     * @return An index or -1.
     */
    protected int indexOf(Object key) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(keys[i], key)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Remove the entry at the index.
     * 
     * @param index An index.
     */
    protected void removeAt(int index) {
        for (int i = index; i < size - 1; i++) {
            keys[i] = keys[i + 1];
            values[i] = values[i + 1];
        }
        keys[--size] = null;
        values[size] = null;
    }

    /**
     * Ensure the capacity.
     * 
     * @param capacity A capacity.
     */
    private void ensure(int capacity) {
        if (keys.length < capacity) {
            int length = Math.max(capacity, keys.length == 0 ? 10 : keys.length * 2);
            keys = Arrays.copyOf(keys, length);
            values = Arrays.copyOf(values, length);
        }
    }

    /**
     * The Java implementation of {@link java.util.AbstractMap.SimpleEntry} which is transpiled
     * into JavaScript.
     */
    public static class SimpleEntry<K, V> implements Map.Entry, java.io.Serializable {

        /** The key. */
        private final Object key;

        /** The value. */
        private Object value;

        /**
         * Create the entry.
         * 
         * @param key A key.
         * @param value A value.
         */
        public SimpleEntry(Object key, Object value) {
            this.key = key;
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object getKey() {
            return key;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object getValue() {
            return value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object setValue(Object value) {
            Object previous = this.value;
            this.value = value;
            return previous;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object other) {
            if (other instanceof Map.Entry otherEntry) {
                return Objects.equals(key, otherEntry.getKey()) && Objects.equals(value, otherEntry.getValue());
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return key + "=" + value;
        }
    }
}
