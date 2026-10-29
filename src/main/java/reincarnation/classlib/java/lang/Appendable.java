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
 * The Java implementation of {@link java.lang.Appendable} which is transpiled into JavaScript.
 */
public interface Appendable {

    /**
     * Append the value.
     * 
     * @param value A value.
     * @return This appendable.
     */
    Appendable append(java.lang.CharSequence value);

    /**
     * Append the value.
     * 
     * @param value A value.
     * @param start A start index.
     * @param end An end index.
     * @return This appendable.
     */
    Appendable append(java.lang.CharSequence value, int start, int end);

    /**
     * Append the value.
     * 
     * @param value A value.
     * @return This appendable.
     */
    Appendable append(char value);
}
