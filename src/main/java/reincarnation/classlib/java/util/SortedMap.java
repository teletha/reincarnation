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

/**
 * The Java implementation of {@link java.util.SortedMap} which is transpiled into JavaScript.
 */
public interface SortedMap<K, V> extends Map {

    /**
     * Get the comparator.
     * 
     * @return A comparator or null.
     */
    Object comparator();

    /**
     * Get the first key.
     * 
     * @return A key.
     */
    Object firstKey();

    /**
     * Get the last key.
     * 
     * @return A key.
     */
    Object lastKey();
}
