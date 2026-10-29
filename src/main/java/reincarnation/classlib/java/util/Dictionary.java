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
 * The Java implementation of {@link java.util.Dictionary} which is transpiled into JavaScript.
 * <p>
 * The implementation is shared with {@link AbstractMap}.
 * </p>
 */
public abstract class Dictionary<K, V> extends AbstractMap<K, V> {

    /**
     * Create an empty dictionary.
     */
    protected Dictionary() {
        super();
    }

    /**
     * Create a dictionary from the map.
     * 
     * @param map A map.
     */
    protected Dictionary(java.util.Map map) {
        super(map);
    }
}
