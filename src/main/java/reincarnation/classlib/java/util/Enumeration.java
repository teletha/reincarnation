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
 * The Java implementation of {@link java.util.Enumeration} which is transpiled into JavaScript.
 */
public interface Enumeration<E> {

    /**
     * Check whether the next element exists.
     * 
     * @return A result.
     */
    boolean hasMoreElements();

    /**
     * Get the next element.
     * 
     * @return An element.
     */
    E nextElement();
}
