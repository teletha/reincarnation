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

/**
 * The JavaScript {@code Storage}.
 */
@Native
public final class NativeStorage {

    /** The local values for the Java fallback. */
    private static final Map<String, String> localValues = new LinkedHashMap();

    /** The session values for the Java fallback. */
    private static final Map<String, String> sessionValues = new LinkedHashMap();

    /** The values. */
    private final Map<String, String> values;

    /**
     * Create the storage.
     * 
     * @param values Values.
     */
    private NativeStorage(Map<String, String> values) {
        this.values = values;
    }

    /**
     * Get the local storage.
     * 
     * @return A storage.
     */
    @Native("typeof localStorage === 'undefined' ? (globalThis.$rt_localStorage || (globalThis.$rt_localStorage = (() => { const values = new Map(); return {getItem: key => values.has(key) ? values.get(key) : null, setItem: (key, value) => values.set(key, String(value)), removeItem: key => values.delete(key), clear: () => values.clear(), key: index => Array.from(values.keys())[index] || null, get length() { return values.size; }}; })())) : localStorage")
    public static NativeStorage local() {
        return new NativeStorage(localValues);
    }

    /**
     * Get the session storage.
     * 
     * @return A storage.
     */
    @Native("typeof sessionStorage === 'undefined' ? (globalThis.$rt_sessionStorage || (globalThis.$rt_sessionStorage = (() => { const values = new Map(); return {getItem: key => values.has(key) ? values.get(key) : null, setItem: (key, value) => values.set(key, String(value)), removeItem: key => values.delete(key), clear: () => values.clear(), key: index => Array.from(values.keys())[index] || null, get length() { return values.size; }}; })())) : sessionStorage")
    public static NativeStorage session() {
        return new NativeStorage(sessionValues);
    }

    /**
     * Get the value.
     * 
     * @param key A key.
     * @return A value or null.
     */
    @Native("$this.getItem($0)")
    public String getItem(String key) {
        return values.get(key);
    }

    /**
     * Set the value.
     * 
     * @param key A key.
     * @param value A value.
     */
    @Native("$this.setItem($0, $1)")
    public void setItem(String key, String value) {
        values.put(key, value);
    }

    /**
     * Remove the value.
     * 
     * @param key A key.
     */
    @Native("$this.removeItem($0)")
    public void removeItem(String key) {
        values.remove(key);
    }

    /**
     * Clear the values.
     */
    @Native("$this.clear()")
    public void clear() {
        values.clear();
    }

    /**
     * Get the key of the index.
     * 
     * @param index An index.
     * @return A key or null.
     */
    @Native("$this.key($0)")
    public String key(int index) {
        if (index < 0 || index >= values.size()) {
            return null;
        }
        int current = 0;
        for (String key : values.keySet()) {
            if (current++ == index) {
                return key;
            }
        }
        return null;
    }

    /**
     * Get the length.
     * 
     * @return A length.
     */
    @Native("$this.length")
    public int length() {
        return values.size();
    }
}
