/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package js.lang;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * The JavaScript {@code Map}.
 * <p>
 * The Java implementation uses a {@link LinkedHashMap} so that the same test can run on the JVM.
 * </p>
 */
@Native
public final class NativeMap {

    /** The JVM representation. */
    private final Map<Object, Object> map = new LinkedHashMap<>();

    /**
     * Create an empty map.
     */
    @Native("new Map()")
    public NativeMap() {
    }

    /**
     * Put the value.
     * 
     * @param key A key.
     * @param value A value.
     * @return This map.
     */
    @Native("$this.set($0, $1)")
    public NativeMap set(Object key, Object value) {
        map.put(key, value);
        return this;
    }

    /**
     * Get the value.
     * 
     * @param key A key.
     * @return A value or {@link NativeUndefined}.
     */
    @Native("$this.get($0)")
    public Object get(Object key) {
        return map.containsKey(key) ? map.get(key) : NativeUndefined.INSTANCE;
    }

    /**
     * Check whether the key exists.
     * 
     * @param key A key.
     * @return A result.
     */
    @Native("$this.has($0)")
    public boolean has(Object key) {
        return map.containsKey(key);
    }

    /**
     * Remove the key.
     * 
     * @param key A key.
     * @return Whether the key was removed.
     */
    @Native("$this.delete($0)")
    public boolean delete(Object key) {
        if (!map.containsKey(key)) {
            return false;
        }
        map.remove(key);
        return true;
    }

    /**
     * Clear this map.
     */
    @Native("$this.clear()")
    public void clear() {
        map.clear();
    }

    /**
     * Get the size.
     * 
     * @return A size.
     */
    @Native("$this.size")
    public int size() {
        return map.size();
    }

    /**
     * Get all keys.
     * 
     * @return An array of keys.
     */
    @Native("Array.from($this.keys())")
    public NativeArray keys() {
        NativeArray array = new NativeArray();
        for (Object key : map.keySet()) {
            array.push(key);
        }
        return array;
    }

    /**
     * Get all values.
     * 
     * @return An array of values.
     */
    @Native("Array.from($this.values())")
    public NativeArray values() {
        NativeArray array = new NativeArray();
        for (Object value : map.values()) {
            array.push(value);
        }
        return array;
    }

    /**
     * Get all entries.
     * 
     * @return An array of entries. Each entry is a two-element array.
     */
    @Native("Array.from($this.entries())")
    public NativeArray entries() {
        NativeArray array = new NativeArray();
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            NativeArray pair = new NativeArray();
            pair.push(entry.getKey());
            pair.push(entry.getValue());
            array.push(pair);
        }
        return array;
    }

    /**
     * Invoke the action for each entry.
     * <p>
     * The action receives the value first and the key second, matching the JavaScript
     * {@code Map.forEach} behavior.
     * </p>
     * 
     * @param action An action.
     */
    @Native("$this.forEach($0)")
    public void forEach(BiConsumer<Object, Object> action) {
        map.forEach((key, value) -> action.accept(value, key));
    }

    /**
     * Check whether the specified value is a map.
     * 
     * @param value A target value.
     * @return A result.
     */
    @Native("$0 instanceof Map")
    public static boolean isMap(Object value) {
        return value instanceof NativeMap;
    }
}
