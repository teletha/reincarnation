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
 * The Java implementation of {@link java.util.Iterator} which is transpiled into JavaScript.
 */
public interface Iterator<E> extends java.util.Iterator<E> {

    /**
     * Check whether the next value exists.
     * 
     * @return A result.
     */
    @Override
    boolean hasNext();

    /**
     * Get the next value.
     * 
     * @return A value.
     */
    @Override
    E next();

    /**
     * Remove the current value.
     */
    @Override
    void remove();
}
