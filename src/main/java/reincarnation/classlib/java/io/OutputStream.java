/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.io;

/**
 * The Java implementation of {@link java.io.OutputStream} which is transpiled into JavaScript.
 */
public abstract class OutputStream implements Closeable, Flushable {

    /**
     * Create an empty stream.
     */
    protected OutputStream() {
    }

    /**
     * Write the value.
     * 
     * @param value A value.
     */
    public void write(int value) {
    }

    /**
     * Write the values.
     * 
     * @param values Values.
     */
    public void write(byte[] values) {
        write(values, 0, values.length);
    }

    /**
     * Write the values.
     * 
     * @param values Values.
     * @param offset An offset.
     * @param length A length.
     */
    public void write(byte[] values, int offset, int length) {
        for (int i = offset; i < offset + length; i++) {
            write(values[i]);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
    }
}
