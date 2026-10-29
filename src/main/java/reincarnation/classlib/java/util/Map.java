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
 * The Java implementation of {@link java.util.Map} which is transpiled into JavaScript.
 * <p>
 * The JDK type is an interface, but the transpiled JavaScript has no interface concept and the
 * class library resolution is based on the method name and the parameter descriptor. This
 * implementation is therefore a concrete class, which is also used as the common super class of
 * {@link HashMap}, {@link LinkedHashMap}, {@link TreeMap} and {@link Hashtable}.
 * </p>
 */
public class Map {

    /** The keys. */
    protected Object[] keys = new Object[0];

    /** The values. */
    protected Object[] values = new Object[0];

    /** The size. */
    private int size;

    /**
     * Create an empty map.
     */
    public Map() {
    }

    /**
     * Create a map from the map.
     * 
     * @param map A map.
     */
    public Map(java.util.Map map) {
        putAll(map);
    }

    /**
     * Create the empty map.
     * 
     * @return A map.
     */
    public static Map of() {
        return new Map();
    }

    /**
     * Create the map.
     * 
     * @param firstKey A key.
     * @param firstValue A value.
     * @return A map.
     */
    public static Map of(Object firstKey, Object firstValue) {
        return ofArray(new Object[] {firstKey, firstValue});
    }

    /**
     * Create the map.
     * 
     * @param firstKey A key.
     * @param firstValue A value.
     * @param secondKey A key.
     * @param secondValue A value.
     * @return A map.
     */
    public static Map of(Object firstKey, Object firstValue, Object secondKey, Object secondValue) {
        return ofArray(new Object[] {firstKey, firstValue, secondKey, secondValue});
    }

    /**
     * Create the map.
     * 
     * @param firstKey A key.
     * @param firstValue A value.
     * @param secondKey A key.
     * @param secondValue A value.
     * @param thirdKey A key.
     * @param thirdValue A value.
     * @return A map.
     */
    public static Map of(Object firstKey, Object firstValue, Object secondKey, Object secondValue, Object thirdKey,
            Object thirdValue) {
        return ofArray(new Object[] {firstKey, firstValue, secondKey, secondValue, thirdKey, thirdValue});
    }

    /**
     * Create the map.
     * 
     * @param firstKey A key.
     * @param firstValue A value.
     * @param secondKey A key.
     * @param secondValue A value.
     * @param thirdKey A key.
     * @param thirdValue A value.
     * @param fourthKey A key.
     * @param fourthValue A value.
     * @return A map.
     */
    public static Map of(Object firstKey, Object firstValue, Object secondKey, Object secondValue, Object thirdKey,
            Object thirdValue, Object fourthKey, Object fourthValue) {
        return ofArray(new Object[] {firstKey, firstValue, secondKey, secondValue, thirdKey, thirdValue, fourthKey,
                fourthValue});
    }

    /**
     * Create the map.
     * 
     * @param firstKey A key.
     * @param firstValue A value.
     * @param secondKey A key.
     * @param secondValue A value.
     * @param thirdKey A key.
     * @param thirdValue A value.
     * @param fourthKey A key.
     * @param fourthValue A value.
     * @param fifthKey A key.
     * @param fifthValue A value.
     * @return A map.
     */
    public static Map of(Object firstKey, Object firstValue, Object secondKey, Object secondValue, Object thirdKey,
            Object thirdValue, Object fourthKey, Object fourthValue, Object fifthKey, Object fifthValue) {
        return ofArray(new Object[] {firstKey, firstValue, secondKey, secondValue, thirdKey, thirdValue, fourthKey,
                fourthValue, fifthKey, fifthValue});
    }

    /**
     * Create the map.
     * 
     * @param firstKey A key.
     * @param firstValue A value.
     * @param secondKey A key.
     * @param secondValue A value.
     * @param thirdKey A key.
     * @param thirdValue A value.
     * @param fourthKey A key.
     * @param fourthValue A value.
     * @param fifthKey A key.
     * @param fifthValue A value.
     * @param sixthKey A key.
     * @param sixthValue A value.
     * @return A map.
     */
    public static Map of(Object firstKey, Object firstValue, Object secondKey, Object secondValue, Object thirdKey,
            Object thirdValue, Object fourthKey, Object fourthValue, Object fifthKey, Object fifthValue, Object sixthKey,
            Object sixthValue) {
        return ofArray(new Object[] {firstKey, firstValue, secondKey, secondValue, thirdKey, thirdValue, fourthKey,
                fourthValue, fifthKey, fifthValue, sixthKey, sixthValue});
    }

    /**
     * Create the map.
     * 
     * @param firstKey A key.
     * @param firstValue A value.
     * @param secondKey A key.
     * @param secondValue A value.
     * @param thirdKey A key.
     * @param thirdValue A value.
     * @param fourthKey A key.
     * @param fourthValue A value.
     * @param fifthKey A key.
     * @param fifthValue A value.
     * @param sixthKey A key.
     * @param sixthValue A value.
     * @param seventhKey A key.
     * @param seventhValue A value.
     * @return A map.
     */
    public static Map of(Object firstKey, Object firstValue, Object secondKey, Object secondValue, Object thirdKey,
            Object thirdValue, Object fourthKey, Object fourthValue, Object fifthKey, Object fifthValue, Object sixthKey,
            Object sixthValue, Object seventhKey, Object seventhValue) {
        return ofArray(new Object[] {firstKey, firstValue, secondKey, secondValue, thirdKey, thirdValue, fourthKey,
                fourthValue, fifthKey, fifthValue, sixthKey, sixthValue, seventhKey, seventhValue});
    }

    /**
     * Create the map.
     * 
     * @param firstKey A key.
     * @param firstValue A value.
     * @param secondKey A key.
     * @param secondValue A value.
     * @param thirdKey A key.
     * @param thirdValue A value.
     * @param fourthKey A key.
     * @param fourthValue A value.
     * @param fifthKey A key.
     * @param fifthValue A value.
     * @param sixthKey A key.
     * @param sixthValue A value.
     * @param seventhKey A key.
     * @param seventhValue A value.
     * @param eighthKey A key.
     * @param eighthValue A value.
     * @return A map.
     */
    public static Map of(Object firstKey, Object firstValue, Object secondKey, Object secondValue, Object thirdKey,
            Object thirdValue, Object fourthKey, Object fourthValue, Object fifthKey, Object fifthValue, Object sixthKey,
            Object sixthValue, Object seventhKey, Object seventhValue, Object eighthKey, Object eighthValue) {
        return ofArray(new Object[] {firstKey, firstValue, secondKey, secondValue, thirdKey, thirdValue, fourthKey,
                fourthValue, fifthKey, fifthValue, sixthKey, sixthValue, seventhKey, seventhValue, eighthKey,
                eighthValue});
    }

    /**
     * Create the map.
     * 
     * @param firstKey A key.
     * @param firstValue A value.
     * @param secondKey A key.
     * @param secondValue A value.
     * @param thirdKey A key.
     * @param thirdValue A value.
     * @param fourthKey A key.
     * @param fourthValue A value.
     * @param fifthKey A key.
     * @param fifthValue A value.
     * @param sixthKey A key.
     * @param sixthValue A value.
     * @param seventhKey A key.
     * @param seventhValue A value.
     * @param eighthKey A key.
     * @param eighthValue A value.
     * @param ninthKey A key.
     * @param ninthValue A value.
     * @return A map.
     */
    public static Map of(Object firstKey, Object firstValue, Object secondKey, Object secondValue, Object thirdKey,
            Object thirdValue, Object fourthKey, Object fourthValue, Object fifthKey, Object fifthValue, Object sixthKey,
            Object sixthValue, Object seventhKey, Object seventhValue, Object eighthKey, Object eighthValue,
            Object ninthKey, Object ninthValue) {
        return ofArray(new Object[] {firstKey, firstValue, secondKey, secondValue, thirdKey, thirdValue, fourthKey,
                fourthValue, fifthKey, fifthValue, sixthKey, sixthValue, seventhKey, seventhValue, eighthKey,
                eighthValue, ninthKey, ninthValue});
    }

    /**
     * Create the map.
     * 
     * @param firstKey A key.
     * @param firstValue A value.
     * @param secondKey A key.
     * @param secondValue A value.
     * @param thirdKey A key.
     * @param thirdValue A value.
     * @param fourthKey A key.
     * @param fourthValue A value.
     * @param fifthKey A key.
     * @param fifthValue A value.
     * @param sixthKey A key.
     * @param sixthValue A value.
     * @param seventhKey A key.
     * @param seventhValue A value.
     * @param eighthKey A key.
     * @param eighthValue A value.
     * @param ninthKey A key.
     * @param ninthValue A value.
     * @param tenthKey A key.
     * @param tenthValue A value.
     * @return A map.
     */
    public static Map of(Object firstKey, Object firstValue, Object secondKey, Object secondValue, Object thirdKey,
            Object thirdValue, Object fourthKey, Object fourthValue, Object fifthKey, Object fifthValue, Object sixthKey,
            Object sixthValue, Object seventhKey, Object seventhValue, Object eighthKey, Object eighthValue,
            Object ninthKey, Object ninthValue, Object tenthKey, Object tenthValue) {
        return ofArray(new Object[] {firstKey, firstValue, secondKey, secondValue, thirdKey, thirdValue, fourthKey,
                fourthValue, fifthKey, fifthValue, sixthKey, sixthValue, seventhKey, seventhValue, eighthKey,
                eighthValue, ninthKey, ninthValue, tenthKey, tenthValue});
    }

    /**
     * Create the map.
     * 
     * @param entries Entries.
     * @return A map.
     */
    public static Map ofEntries(java.util.Map.Entry... entries) {
        Map map = new Map();
        for (java.util.Map.Entry entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    /**
     * Create the entry.
     * 
     * @param key A key.
     * @param value A value.
     * @return An entry.
     */
    public static java.util.Map.Entry entry(Object key, Object value) {
        return new Entry(key, value);
    }

    /**
     * Create the map from the map.
     * 
     * @param map A map.
     * @return A map.
     */
    public static Map copyOf(java.util.Map map) {
        return new Map(map);
    }

    /**
     * Get the size.
     * 
     * @return A size.
     */
    public int size() {
        return size;
    }

    /**
     * Check whether this map is empty.
     * 
     * @return A result.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Get the value of the key.
     * 
     * @param key A key.
     * @return A value or null.
     */
    public Object get(Object key) {
        int index = indexOf(key);
        return index < 0 ? null : values[index];
    }

    /**
     * Get the value of the key.
     * 
     * @param key A key.
     * @param defaultValue A default value.
     * @return A value.
     */
    public Object getOrDefault(Object key, Object defaultValue) {
        int index = indexOf(key);
        return index < 0 ? defaultValue : values[index];
    }

    /**
     * Put the value.
     * 
     * @param key A key.
     * @param value A value.
     * @return A previous value or null.
     */
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
     * Remove the key.
     * 
     * @param key A key.
     * @return A removed value or null.
     */
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
     * Remove the key and the value.
     * 
     * @param key A key.
     * @param value A value.
     * @return A result.
     */
    public boolean remove(Object key, Object value) {
        int index = indexOf(key);
        if (index < 0 || !Objects.equals(values[index], value)) {
            return false;
        }
        removeAt(index);
        return true;
    }

    /**
     * Put all values.
     * 
     * @param map A map.
     */
    public void putAll(java.util.Map map) {
        for (Object item : map.entrySet()) {
            java.util.Map.Entry entry = (java.util.Map.Entry) item;
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Check whether the key exists.
     * 
     * @param key A key.
     * @return A result.
     */
    public boolean containsKey(Object key) {
        return 0 <= indexOf(key);
    }

    /**
     * Check whether the value exists.
     * 
     * @param value A value.
     * @return A result.
     */
    public boolean containsValue(Object value) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(values[i], value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Clear this map.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            keys[i] = null;
            values[i] = null;
        }
        size = 0;
    }

    /**
     * Get the keys.
     * 
     * @return A set.
     */
    public Set keySet() {
        return Set.of(Arrays.copyOf(keys, size));
    }

    /**
     * Get the values.
     * 
     * @return A list.
     */
    public List values() {
        return List.of(Arrays.copyOf(values, size));
    }

    /**
     * Get the entries.
     * 
     * @return A set.
     */
    public Set entrySet() {
        Entry[] entries = new Entry[size];
        for (int i = 0; i < size; i++) {
            entries[i] = new Entry(keys[i], values[i]);
        }
        return Set.of(entries);
    }

    /**
     * Invoke the action for each entry.
     * 
     * @param action An action.
     */
    public void forEach(BiConsumer action) {
        for (int i = 0; i < size; i++) {
            action.accept(keys[i], values[i]);
        }
    }

    /**
     * Put the value if the key does not exist.
     * 
     * @param key A key.
     * @param value A value.
     * @return A current value or null.
     */
    public Object putIfAbsent(Object key, Object value) {
        int index = indexOf(key);
        if (index < 0) {
            put(key, value);
            return null;
        }
        return values[index];
    }

    /**
     * Compute the value if the key does not exist.
     * 
     * @param key A key.
     * @param mappingFunction A mapping function.
     * @return A value or null.
     */
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
     * Compute the value if the key exists.
     * 
     * @param key A key.
     * @param remappingFunction A remapping function.
     * @return A value or null.
     */
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
     * Compute the value.
     * 
     * @param key A key.
     * @param remappingFunction A remapping function.
     * @return A value or null.
     */
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
     * Merge the value.
     * 
     * @param key A key.
     * @param value A value.
     * @param remappingFunction A remapping function.
     * @return A merged value or null.
     */
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
     * Replace the value.
     * 
     * @param key A key.
     * @param value A value.
     * @return A previous value or null.
     */
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
     * Replace the value.
     * 
     * @param key A key.
     * @param oldValue An expected value.
     * @param newValue A new value.
     * @return A result.
     */
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
    public boolean equals(Object other) {
        if (other instanceof Map otherMap) {
            if (size != otherMap.size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                int index = otherMap.indexOf(keys[i]);
                if (index < 0 || !Objects.equals(values[i], otherMap.values[index])) {
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
     * Create the map from the array.
     * 
     * @param pairs Key-value pairs.
     * @return A map.
     */
    private static Map ofArray(Object[] pairs) {
        Map map = new Map();
        for (int i = 0; i < pairs.length; i += 2) {
            map.put(pairs[i], pairs[i + 1]);
        }
        return map;
    }
}
