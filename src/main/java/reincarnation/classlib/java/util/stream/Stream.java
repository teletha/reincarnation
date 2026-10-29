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

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/**
 * The Java implementation of {@link java.util.stream.Stream} which is transpiled into JavaScript.
 */
public final class Stream {

    /** The values. */
    private final Object[] values;

    /**
     * Create the stream.
     * 
     * @param values Values.
     */
    private Stream(Object[] values) {
        this.values = values;
    }

    /**
     * Create the stream.
     * 
     * @param values Values.
     * @return A stream.
     */
    public static Stream of(Object... values) {
        return new Stream(values);
    }

    /**
     * Create the empty stream.
     * 
     * @return A stream.
     */
    public static Stream empty() {
        return new Stream(new Object[0]);
    }

    /**
     * Map the values.
     * 
     * @param mapper A mapper.
     * @return A stream.
     */
    public Stream map(Function mapper) {
        Object[] result = new Object[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = mapper.apply(values[i]);
        }
        return new Stream(result);
    }

    /**
     * Flat-map the values.
     * 
     * @param mapper A mapper.
     * @return A stream.
     */
    public Stream flatMap(Function mapper) {
        Object[] result = new Object[values.length];
        int size = 0;
        for (Object value : values) {
            Stream mapped = (Stream) mapper.apply(value);
            for (Object item : mapped.values) {
                result[size++] = item;
            }
        }
        return new Stream(Arrays.copyOf(result, size));
    }

    /**
     * Filter the values.
     * 
     * @param predicate A predicate.
     * @return A stream.
     */
    public Stream filter(Predicate predicate) {
        Object[] result = new Object[values.length];
        int size = 0;
        for (Object value : values) {
            if (predicate.test(value)) {
                result[size++] = value;
            }
        }
        return new Stream(Arrays.copyOf(result, size));
    }

    /**
     * Remove the duplicated values.
     * 
     * @return A stream.
     */
    public Stream distinct() {
        Object[] result = new Object[values.length];
        int size = 0;
        for (Object value : values) {
            boolean duplicated = false;
            for (int i = 0; i < size; i++) {
                if (Objects.equals(result[i], value)) {
                    duplicated = true;
                    break;
                }
            }
            if (!duplicated) {
                result[size++] = value;
            }
        }
        return new Stream(Arrays.copyOf(result, size));
    }

    /**
     * Sort the values.
     * 
     * @return A stream.
     */
    public Stream sorted() {
        Object[] result = Arrays.copyOf(values, values.length);
        Arrays.sort(result);
        return new Stream(result);
    }

    /**
     * Sort the values.
     * 
     * @param comparator A comparator.
     * @return A stream.
     */
    public Stream sorted(Comparator comparator) {
        Object[] result = Arrays.copyOf(values, values.length);
        Arrays.sort(result, comparator);
        return new Stream(result);
    }

    /**
     * Limit the values.
     * 
     * @param max A maximum count.
     * @return A stream.
     */
    public Stream limit(long max) {
        return new Stream(Arrays.copyOf(values, (int) Math.min(values.length, max)));
    }

    /**
     * Skip the values.
     * 
     * @param count A count.
     * @return A stream.
     */
    public Stream skip(long count) {
        int start = (int) Math.min(values.length, Math.max(0, count));
        return new Stream(Arrays.copyOfRange(values, start, values.length));
    }

    /**
     * Peek the values.
     * 
     * @param action An action.
     * @return This stream.
     */
    public Stream peek(Consumer action) {
        for (Object value : values) {
            action.accept(value);
        }
        return this;
    }

    /**
     * Invoke the action for each value.
     * 
     * @param action An action.
     */
    public void forEach(Consumer action) {
        for (Object value : values) {
            action.accept(value);
        }
    }

    /**
     * Invoke the action for each value.
     * 
     * @param action An action.
     */
    public void forEachOrdered(Consumer action) {
        forEach(action);
    }

    /**
     * Count the values.
     * 
     * @return A count.
     */
    public long count() {
        return values.length;
    }

    /**
     * Check whether any value matches.
     * 
     * @param predicate A predicate.
     * @return A result.
     */
    public boolean anyMatch(Predicate predicate) {
        for (Object value : values) {
            if (predicate.test(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether all values match.
     * 
     * @param predicate A predicate.
     * @return A result.
     */
    public boolean allMatch(Predicate predicate) {
        for (Object value : values) {
            if (!predicate.test(value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check whether no value matches.
     * 
     * @param predicate A predicate.
     * @return A result.
     */
    public boolean noneMatch(Predicate predicate) {
        return !anyMatch(predicate);
    }

    /**
     * Get the first value.
     * 
     * @return An optional.
     */
    public Optional findFirst() {
        return Optional.ofNullable(values.length == 0 ? null : values[0]);
    }

    /**
     * Get any value.
     * 
     * @return An optional.
     */
    public Optional findAny() {
        return findFirst();
    }

    /**
     * Reduce the values.
     * 
     * @param accumulator An accumulator.
     * @return An optional.
     */
    public Optional reduce(BinaryOperator accumulator) {
        if (values.length == 0) {
            return Optional.empty();
        }
        Object result = values[0];
        for (int i = 1; i < values.length; i++) {
            result = accumulator.apply(result, values[i]);
        }
        return Optional.ofNullable(result);
    }

    /**
     * Reduce the values.
     * 
     * @param identity An identity.
     * @param accumulator An accumulator.
     * @return A result.
     */
    public Object reduce(Object identity, BinaryOperator accumulator) {
        Object result = identity;
        for (Object value : values) {
            result = accumulator.apply(result, value);
        }
        return result;
    }

    /**
     * Reduce the values.
     * 
     * @param identity An identity.
     * @param accumulator An accumulator.
     * @param combiner A combiner.
     * @return A result.
     */
    public Object reduce(Object identity, BiFunction accumulator, BinaryOperator combiner) {
        Object result = identity;
        for (Object value : values) {
            result = accumulator.apply(result, value);
        }
        return result;
    }

    /**
     * Collect the values.
     * 
     * @param collector A collector.
     * @return A result.
     */
    public Object collect(java.util.stream.Collector collector) {
        return ((Function) collector).apply(values);
    }

    /**
     * Create the array.
     * 
     * @return An array.
     */
    public Object[] toArray() {
        return Arrays.copyOf(values, values.length);
    }

    /**
     * Create the array.
     * 
     * @param generator A generator.
     * @return An array.
     */
    public Object[] toArray(IntFunction generator) {
        Object[] result = (Object[]) generator.apply(values.length);
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i];
        }
        return result;
    }

    /**
     * Create the list.
     * 
     * @return A list.
     */
    public List toList() {
        return List.of(values);
    }
}
