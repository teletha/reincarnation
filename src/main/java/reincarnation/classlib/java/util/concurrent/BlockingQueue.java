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

/**
 * The Java implementation of {@link java.util.concurrent.BlockingQueue} which is transpiled into
 * JavaScript.
 */
public interface BlockingQueue<E> extends reincarnation.classlib.java.util.Queue<E> {

    /**
     * Put the value.
     * 
     * @param value A value.
     */
    default void put(Object value) {
        add(value);
    }

    /**
     * Take the value.
     * 
     * @return A value.
     */
    default Object take() {
        return remove();
    }
}
