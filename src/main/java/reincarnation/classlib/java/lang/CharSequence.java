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
 * The Java implementation of {@link java.lang.CharSequence} which is transpiled into JavaScript.
 */
public interface CharSequence extends java.lang.CharSequence {

    /**
     * {@inheritDoc}
     */
    @Override
    int length();

    /**
     * {@inheritDoc}
     */
    @Override
    char charAt(int index);

    /**
     * {@inheritDoc}
     */
    @Override
    java.lang.CharSequence subSequence(int start, int end);

    /**
     * {@inheritDoc}
     */
    @Override
    String toString();
}
