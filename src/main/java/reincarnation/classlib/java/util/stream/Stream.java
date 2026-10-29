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

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

import reincarnation.classlib.java.util.List;
import reincarnation.classlib.java.util.Optional;

/**
 * The Java implementation of {@link java.util.stream.Stream} which is transpiled into JavaScript.
 */
public interface Stream<T> extends BaseStream<T, Stream<T>> {

    /**
     * Create the stream.
     * 
     * @param values Values.
     * @return A stream.
     */
    static Stream of(Object... values) {
        return new StreamImpl(values);
    }

    /**
     * Create the stream.
     * 
     * @param value A value.
     * @return A stream.
     */
    static Stream of(Object value) {
        return new StreamImpl(new Object[] {value});
    }

    /**
     * Create the stream.
     * 
     * @param value A value.
     * @return A stream.
     */
    static Stream ofNullable(Object value) {
        return value == null ? empty() : new StreamImpl(new Object[] {value});
    }

    /**
     * Create the empty stream.
     * 
     * @return A stream.
     */
    static Stream empty() {
        return new StreamImpl(new Object[0]);
    }

    /**
     * Map the values.
     * 
     * @param mapper A mapper.
     * @return A stream.
     */
    Stream map(Function mapper);

    /**
     * Flat-map the values.
     * 
     * @param mapper A mapper.
     * @return A stream.
     */
    Stream flatMap(Function mapper);

    /**
     * Filter the values.
     * 
     * @param predicate A predicate.
     * @return A stream.
     */
    Stream filter(Predicate predicate);

    /**
     * Remove the duplicated values.
     * 
     * @return A stream.
     */
    Stream distinct();

    /**
     * Sort the values.
     * 
     * @return A stream.
     */
    Stream sorted();

    /**
     * Sort the values.
     * 
     * @param comparator A comparator.
     * @return A stream.
     */
    Stream sorted(Comparator comparator);

    /**
     * Limit the values.
     * 
     * @param max A maximum count.
     * @return A stream.
     */
    Stream limit(long max);

    /**
     * Skip the values.
     * 
     * @param count A count.
     * @return A stream.
     */
    Stream skip(long count);

    /**
     * Peek the values.
     * 
     * @param action An action.
     * @return This stream.
     */
    Stream peek(Consumer action);

    /**
     * Invoke the action for each value.
     * 
     * @param action An action.
     */
    void forEach(Consumer action);

    /**
     * Invoke the action for each value.
     * 
     * @param action An action.
     */
    void forEachOrdered(Consumer action);

    /**
     * Count the values.
     * 
     * @return A count.
     */
    long count();

    /**
     * Check whether any value matches.
     * 
     * @param predicate A predicate.
     * @return A result.
     */
    boolean anyMatch(Predicate predicate);

    /**
     * Check whether all values match.
     * 
     * @param predicate A predicate.
     * @return A result.
     */
    boolean allMatch(Predicate predicate);

    /**
     * Check whether no value matches.
     * 
     * @param predicate A predicate.
     * @return A result.
     */
    boolean noneMatch(Predicate predicate);

    /**
     * Get the first value.
     * 
     * @return An optional.
     */
    Optional findFirst();

    /**
     * Get any value.
     * 
     * @return An optional.
     */
    Optional findAny();

    /**
     * Reduce the values.
     * 
     * @param accumulator An accumulator.
     * @return An optional.
     */
    Optional reduce(BinaryOperator accumulator);

    /**
     * Reduce the values.
     * 
     * @param identity An identity.
     * @param accumulator An accumulator.
     * @return A result.
     */
    Object reduce(Object identity, BinaryOperator accumulator);

    /**
     * Reduce the values.
     * 
     * @param identity An identity.
     * @param accumulator An accumulator.
     * @param combiner A combiner.
     * @return A result.
     */
    Object reduce(Object identity, BiFunction accumulator, BinaryOperator combiner);

    /**
     * Collect the values.
     * 
     * @param collector A collector.
     * @return A result.
     */
    Object collect(java.util.stream.Collector collector);

    /**
     * Create the array.
     * 
     * @return An array.
     */
    Object[] toArray();

    /**
     * Create the array.
     * 
     * @param generator A generator.
     * @return An array.
     */
    Object[] toArray(IntFunction generator);

    /**
     * Create the list.
     * 
     * @return A list.
     */
    List toList();

    /**
     * {@inheritDoc}
     */
    @Override
    void close();
}
