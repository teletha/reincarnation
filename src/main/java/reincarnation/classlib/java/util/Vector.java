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
 * The Java implementation of {@link java.util.Vector}.
 */
public class Vector<E> extends AbstractList<E> implements List<E>, RandomAccess,
        reincarnation.classlib.java.lang.Cloneable, reincarnation.classlib.java.io.Serializable {

    /**
     * Create an empty list.
     */
    public Vector() {
        super();
    }

    /**
     * Create an empty list.
     * 
     * @param capacity A capacity.
     */
    public Vector(int capacity) {
        super();
    }

    /**
     * Create an empty list.
     * 
     * @param capacity A capacity.
     * @param increment A capacity increment.
     */
    public Vector(int capacity, int increment) {
        super();
    }

    /**
     * Create a list from the collection.
     * 
     * @param collection A collection.
     */
    public Vector(Collection collection) {
        super();
        for (Object value : collection) {
            add(value);
        }
    }
}
