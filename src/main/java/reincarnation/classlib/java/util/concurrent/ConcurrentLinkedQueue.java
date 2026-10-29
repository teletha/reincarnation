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

import reincarnation.classlib.java.util.AbstractQueue;

/**
 * The Java implementation of {@link java.util.concurrent.ConcurrentLinkedQueue}.
 */
public class ConcurrentLinkedQueue<E> extends AbstractQueue<E>
        implements reincarnation.classlib.java.util.Queue<E>, reincarnation.classlib.java.io.Serializable {

    /**
     * Create an empty queue.
     */
    public ConcurrentLinkedQueue() {
        super();
    }

    /**
     * Create a queue from the collection.
     * 
     * @param collection A collection.
     */
    public ConcurrentLinkedQueue(java.util.Collection collection) {
        super();
        for (Object value : collection) {
            add(value);
        }
    }
}
