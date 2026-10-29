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
 * The Java implementation of {@link java.io.FilterOutputStream} which is transpiled into
 * JavaScript.
 */
public class FilterOutputStream extends OutputStream {

    /** The output stream. */
    protected OutputStream out;

    /**
     * Create the filter stream.
     * 
     * @param out An output stream.
     */
    public FilterOutputStream(OutputStream out) {
        this.out = out;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(int value) {
        if (out != null) {
            out.write(value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(byte[] values, int offset, int length) {
        if (out != null) {
            out.write(values, offset, length);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() {
        if (out != null) {
            out.flush();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        if (out != null) {
            out.close();
        }
    }
}
