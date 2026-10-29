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

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * The JavaScript {@code Set}.
 * <p>
 * The Java implementation uses a {@link LinkedHashSet} so that the same test can run on the JVM.
 * </p>
 */
@Native
public final class NativeSet {

    /** The JVM representation. */
    private final Set<Object> values = new LinkedHashSet<>();

    /**
     * Create an empty set.
     */
    @Native("new Set()")
    public NativeSet() {
    }

    /**
     * Create a set from the array.
     * 
     * @param array An array.
     */
    @Native("new Set($0)")
    public NativeSet(NativeArray array) {
        for (int i = 0; i < array.length(); i++) {
            values.add(array.get(i));
        }
    }

    /**
     * Add the value.
     * 
     * @param value A value.
     * @return This set.
     */
    @Native("$this.add($0)")
    public NativeSet add(Object value) {
        values.add(value);
        return this;
    }

    /**
     * Check whether the value exists.
     * 
     * @param value A value.
     * @return A result.
     */
    @Native("$this.has($0)")
    public boolean has(Object value) {
        return values.contains(value);
    }

    /**
     * Remove the value.
     * 
     * @param value A value.
     * @return Whether the value was removed.
     */
    @Native("$this.delete($0)")
    public boolean delete(Object value) {
        return values.remove(value);
    }

    /**
     * Clear this set.
     */
    @Native("$this.clear()")
    public void clear() {
        values.clear();
    }

    /**
     * Get the size.
     * 
     * @return A size.
     */
    @Native("$this.size")
    public int size() {
        return values.size();
    }

    /**
     * Get all values.
     * 
     * @return An array of values.
     */
    @Native("Array.from($this.values())")
    public NativeArray values() {
        NativeArray array = new NativeArray();
        for (Object value : values) {
            array.push(value);
        }
        return array;
    }

    /**
     * Invoke the action for each value.
     * 
     * @param action An action.
     */
    @Native("$this.forEach($0)")
    public void forEach(Consumer<Object> action) {
        values.forEach(action);
    }

    /**
     * Check whether the specified value is a set.
     * 
     * @param value A target value.
     * @return A result.
     */
    @Native("$0 instanceof Set")
    public static boolean isSet(Object value) {
        return value instanceof NativeSet;
    }
}
