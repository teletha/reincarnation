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
 * The Java implementation of {@link java.util.Queue} which is transpiled into JavaScript.
 */
public interface Queue<E> extends Collection<E> {

    /**
     * Offer the value.
     * 
     * @param value A value.
     * @return A result.
     */
    boolean offer(Object value);

    /**
     * Remove the first value.
     * 
     * @return A value.
     */
    Object remove();

    /**
     * Poll the first value.
     * 
     * @return A value or null.
     */
    Object poll();

    /**
     * Get the first value.
     * 
     * @return A value.
     */
    Object element();

    /**
     * Peek the first value.
     * 
     * @return A value or null.
     */
    Object peek();
}
