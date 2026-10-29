/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.time.temporal;

/**
 * The Java implementation of {@link java.time.temporal.TemporalAccessor} which is transpiled into
 * JavaScript.
 */
public interface TemporalAccessor {

    /**
     * Check whether the field is supported.
     * 
     * @param field A field.
     * @return A result.
     */
    default boolean isSupported(TemporalField field) {
        return field != null;
    }

    /**
     * Get the value of the field.
     * 
     * @param field A field.
     * @return A value.
     */
    default long getLong(TemporalField field) {
        throw new UnsupportedOperationException("Unsupported field: " + field);
    }

    /**
     * Get the value of the field.
     * 
     * @param field A field.
     * @return A value.
     */
    default int get(TemporalField field) {
        return (int) getLong(field);
    }
}
