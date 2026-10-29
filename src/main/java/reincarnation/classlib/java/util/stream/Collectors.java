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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * The Java implementation of {@link java.util.stream.Collectors} which is transpiled into
 * JavaScript.
 * <p>
 * The JavaScript runtime represents a collector as a function which receives the whole stream
 * values, so the implementation matches that representation.
 * </p>
 */
public final class Collectors {

    /**
     * Hide constructor.
     */
    private Collectors() {
    }

    /**
     * Collect the values into a list.
     * 
     * @return A collector.
     */
    public static Object toList() {
        return (Function<Object, Object>) values -> List.of((Object[]) values);
    }

    /**
     * Collect the values into a set.
     * 
     * @return A collector.
     */
    public static Object toSet() {
        return (Function<Object, Object>) values -> {
            Set<Object> set = new HashSet<>();
            for (Object value : (Object[]) values) {
                set.add(value);
            }
            return set;
        };
    }

    /**
     * Count the values.
     * 
     * @return A collector.
     */
    public static Object counting() {
        return (Function<Object, Object>) values -> (long) ((Object[]) values).length;
    }

    /**
     * Join the values.
     * 
     * @param first The delimiter.
     * @param second The prefix.
     * @param third The suffix.
     * @return A collector.
     */
    public static Object joining(Object first, Object second, Object third) {
        String delimiter = first == null ? "" : String.valueOf(first);
        String prefix = second == null ? "" : String.valueOf(second);
        String suffix = third == null ? "" : String.valueOf(third);

        return (Function<Object, Object>) values -> {
            Object[] array = (Object[]) values;
            StringBuilder builder = new StringBuilder(prefix);
            for (int i = 0; i < array.length; i++) {
                if (i != 0) {
                    builder.append(delimiter);
                }
                builder.append(String.valueOf(array[i]));
            }
            return builder.append(suffix).toString();
        };
    }
}
