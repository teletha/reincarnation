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

import reincarnation.classlib.java.util.Iterator;

/**
 * The Java implementation of {@link java.lang.Iterable} which is transpiled into JavaScript.
 */
public interface Iterable<T> extends java.lang.Iterable<T> {

    /**
     * Create the iterator.
     * 
     * @return An iterator.
     */
    java.util.Iterator<T> iterator();
}
