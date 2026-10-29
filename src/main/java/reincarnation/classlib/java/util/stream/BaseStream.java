/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.util.stream;

/**
 * The Java implementation of {@link java.util.stream.BaseStream} which is transpiled into JavaScript.
 */
public interface BaseStream<T, S extends BaseStream<T, S>> extends reincarnation.classlib.java.lang.AutoCloseable {

    /**
     * {@inheritDoc}
     */
    @Override
    void close();
}
