/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.lang;

/**
 * The Java implementation of {@link java.lang.Comparable} which is transpiled into JavaScript.
 */
public interface Comparable<T> extends java.lang.Comparable<T> {

    /**
     * {@inheritDoc}
     */
    @Override
    int compareTo(T other);
}
