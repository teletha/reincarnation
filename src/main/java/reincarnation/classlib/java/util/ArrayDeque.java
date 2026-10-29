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
 * The Java implementation of {@link java.util.ArrayDeque}.
 */
public final class ArrayDeque extends Deque {

    /**
     * Create an empty deque.
     */
    public ArrayDeque() {
        super();
    }

    /**
     * Create an empty deque.
     * 
     * @param capacity A capacity.
     */
    public ArrayDeque(int capacity) {
        super();
    }

    /**
     * Create a deque from the collection.
     * 
     * @param collection A collection.
     */
    public ArrayDeque(Collection collection) {
        super(collection);
    }
}
