/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.util.concurrent;

import reincarnation.classlib.java.util.AbstractSet;

/**
 * The Java implementation of {@link java.util.concurrent.CopyOnWriteArraySet}.
 */
public class CopyOnWriteArraySet<E> extends AbstractSet<E>
        implements reincarnation.classlib.java.io.Serializable {

    /**
     * Create an empty set.
     */
    public CopyOnWriteArraySet() {
        super();
    }

    /**
     * Create a set from the collection.
     * 
     * @param collection A collection.
     */
    public CopyOnWriteArraySet(java.util.Collection collection) {
        super();
        for (Object value : collection) {
            add(value);
        }
    }
}
