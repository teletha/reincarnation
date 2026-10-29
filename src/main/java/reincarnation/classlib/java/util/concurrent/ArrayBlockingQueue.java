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
 * The Java implementation of {@link java.util.concurrent.ArrayBlockingQueue}.
 */
public class ArrayBlockingQueue<E> extends AbstractQueue<E>
        implements BlockingQueue<E>, reincarnation.classlib.java.io.Serializable {

    /**
     * Create an empty queue.
     * 
     * @param capacity A capacity.
     */
    public ArrayBlockingQueue(int capacity) {
        super();
    }

    /**
     * Create an empty queue.
     * 
     * @param capacity A capacity.
     * @param fair Whether the queue is fair.
     */
    public ArrayBlockingQueue(int capacity, boolean fair) {
        super();
    }

    /**
     * Create a queue from the collection.
     * 
     * @param capacity A capacity.
     * @param fair Whether the queue is fair.
     * @param collection A collection.
     */
    public ArrayBlockingQueue(int capacity, boolean fair, java.util.Collection collection) {
        super();
        for (Object value : collection) {
            add(value);
        }
    }
}
