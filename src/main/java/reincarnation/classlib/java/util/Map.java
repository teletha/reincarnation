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

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * The Java implementation of {@link java.util.Map} which is transpiled into JavaScript.
 * <p>
 * The JDK type is an interface and this implementation keeps the same type hierarchy so that
 * the generated reflection metadata reports the same modifiers, interfaces and super class.
 * </p>
 */
public interface Map {

    /**
     * The Java implementation of {@link java.util.Map.Entry} which is transpiled into JavaScript.
     */
    interface Entry<K, V> {

        /**
         * Get the key.
         * 
         * @return A key.
         */
        Object getKey();

        /**
         * Get the value.
         * 
         * @return A value.
         */
        Object getValue();

        /**
         * Set the value.
         * 
         * @param value A value.
         * @return A previous value.
         */
        Object setValue(Object value);

        /**
         * {@inheritDoc}
         */
        @Override
        boolean equals(Object other);

        /**
         * {@inheritDoc}
         */
        @Override
        int hashCode();
    }

    /**
     * Create the empty map.
     * 
     * @return A map.
     */
    static Map of() {
        return new HashMap();
    }

    /**
     * Create the map.
     * 
     * @param firstKey A key.
     * @param firstValue A value.
     * @return A map.
     */
    static Map of(Object firstKey, Object firstValue) {
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
    static Map of(Object firstKey, Object firstValue, Object secondKey, Object secondValue) {
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
    static Map of(Object firstKey, Object firstValue, Object secondKey, Object secondValue, Object thirdKey,
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
    static Map of(Object firstKey, Object firstValue, Object secondKey, Object secondValue, Object thirdKey,
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
    static Map of(Object firstKey, Object firstValue, Object secondKey, Object secondValue, Object thirdKey,
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
    static Map of(Object firstKey, Object firstValue, Object secondKey, Object secondValue, Object thirdKey,
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
    static Map of(Object firstKey, Object firstValue, Object secondKey, Object secondValue, Object thirdKey,
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
    static Map of(Object firstKey, Object firstValue, Object secondKey, Object secondValue, Object thirdKey,
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
    static Map of(Object firstKey, Object firstValue, Object secondKey, Object secondValue, Object thirdKey,
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
    static Map of(Object firstKey, Object firstValue, Object secondKey, Object secondValue, Object thirdKey,
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
    static Map ofEntries(java.util.Map.Entry... entries) {
        Map map = new HashMap();
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
    static Entry entry(Object key, Object value) {
        return new AbstractMap.SimpleEntry(key, value);
    }

    /**
     * Create the map from the map.
     * 
     * @param map A map.
     * @return A map.
     */
    static Map copyOf(java.util.Map map) {
        return new HashMap(map);
    }

    /**
     * Get the size.
     * 
     * @return A size.
     */
    int size();

    /**
     * Check whether this map is empty.
     * 
     * @return A result.
     */
    boolean isEmpty();

    /**
     * Check whether the key exists.
     * 
     * @param key A key.
     * @return A result.
     */
    boolean containsKey(Object key);

    /**
     * Check whether the value exists.
     * 
     * @param value A value.
     * @return A result.
     */
    boolean containsValue(Object value);

    /**
     * Get the value of the key.
     * 
     * @param key A key.
     * @return A value or null.
     */
    Object get(Object key);

    /**
     * Get the value of the key.
     * 
     * @param key A key.
     * @param defaultValue A default value.
     * @return A value.
     */
    Object getOrDefault(Object key, Object defaultValue);

    /**
     * Put the value.
     * 
     * @param key A key.
     * @param value A value.
     * @return A previous value or null.
     */
    Object put(Object key, Object value);

    /**
     * Remove the key.
     * 
     * @param key A key.
     * @return A removed value or null.
     */
    Object remove(Object key);

    /**
     * Remove the key and the value.
     * 
     * @param key A key.
     * @param value A value.
     * @return A result.
     */
    boolean remove(Object key, Object value);

    /**
     * Put all values.
     * 
     * @param map A map.
     */
    void putAll(java.util.Map map);

    /**
     * Clear this map.
     */
    void clear();

    /**
     * Get the keys.
     * 
     * @return A set.
     */
    Set keySet();

    /**
     * Get the values.
     * 
     * @return A list.
     */
    List values();

    /**
     * Get the entries.
     * 
     * @return A set.
     */
    Set entrySet();

    /**
     * Invoke the action for each entry.
     * 
     * @param action An action.
     */
    void forEach(BiConsumer action);

    /**
     * Put the value if the key does not exist.
     * 
     * @param key A key.
     * @param value A value.
     * @return A current value or null.
     */
    Object putIfAbsent(Object key, Object value);

    /**
     * Compute the value if the key does not exist.
     * 
     * @param key A key.
     * @param mappingFunction A mapping function.
     * @return A value or null.
     */
    Object computeIfAbsent(Object key, Function mappingFunction);

    /**
     * Compute the value if the key exists.
     * 
     * @param key A key.
     * @param remappingFunction A remapping function.
     * @return A value or null.
     */
    Object computeIfPresent(Object key, BiFunction remappingFunction);

    /**
     * Compute the value.
     * 
     * @param key A key.
     * @param remappingFunction A remapping function.
     * @return A value or null.
     */
    Object compute(Object key, BiFunction remappingFunction);

    /**
     * Merge the value.
     * 
     * @param key A key.
     * @param value A value.
     * @param remappingFunction A remapping function.
     * @return A merged value or null.
     */
    Object merge(Object key, Object value, BiFunction remappingFunction);

    /**
     * Replace the value.
     * 
     * @param key A key.
     * @param value A value.
     * @return A previous value or null.
     */
    Object replace(Object key, Object value);

    /**
     * Replace the value.
     * 
     * @param key A key.
     * @param oldValue An expected value.
     * @param newValue A new value.
     * @return A result.
     */
    boolean replace(Object key, Object oldValue, Object newValue);

    /**
     * Replace all values.
     * 
     * @param function A function.
     */
    void replaceAll(BiFunction function);

    /**
     * Create the map from the array.
     * 
     * @param pairs Key-value pairs.
     * @return A map.
     */
    private static Map ofArray(Object[] pairs) {
        Map map = new HashMap();
        for (int i = 0; i < pairs.length; i += 2) {
            map.put(pairs[i], pairs[i + 1]);
        }
        return map;
    }
}
