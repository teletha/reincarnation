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

import java.util.Collection;

/**
 * The Java implementation of {@link java.util.HashSet}.
 */
public final class HashSet extends Set {

    /**
     * Create an empty set.
     */
    public HashSet() {
        super();
    }

    /**
     * Create an empty set.
     * 
     * @param capacity A capacity.
     */
    public HashSet(int capacity) {
        super();
    }

    /**
     * Create an empty set.
     * 
     * @param capacity A capacity.
     * @param loadFactor A load factor.
     */
    public HashSet(int capacity, float loadFactor) {
        super();
    }

    /**
     * Create a set from the collection.
     * 
     * @param collection A collection.
     */
    public HashSet(Collection collection) {
        super(collection);
    }

    /**
     * Create an empty set.
     * 
     * @param capacity A capacity.
     * @param loadFactor A load factor.
     * @param dummy A dummy flag.
     */
    HashSet(int capacity, float loadFactor, boolean dummy) {
        super();
    }
}
