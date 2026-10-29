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
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

import reincarnation.classlib.java.util.List;
import reincarnation.classlib.java.util.Optional;

/**
 * The implementation of {@link Stream}.
 */
public final class StreamImpl implements Stream {

    /** The values. */
    private final Object[] values;

    /**
     * Create the stream.
     * 
     * @param values Values.
     */
    StreamImpl(Object[] values) {
        this.values = values;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream map(Function mapper) {
        Object[] result = new Object[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = mapper.apply(values[i]);
        }
        return new StreamImpl(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream flatMap(Function mapper) {
        Object[] result = new Object[values.length];
        int size = 0;
        for (Object value : values) {
            StreamImpl mapped = (StreamImpl) mapper.apply(value);
            for (Object item : mapped.values) {
                result[size++] = item;
            }
        }
        return new StreamImpl(Arrays.copyOf(result, size));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream filter(Predicate predicate) {
        Object[] result = new Object[values.length];
        int size = 0;
        for (Object value : values) {
            if (predicate.test(value)) {
                result[size++] = value;
            }
        }
        return new StreamImpl(Arrays.copyOf(result, size));
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
        return new StreamImpl(Arrays.copyOf(result, size));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream sorted() {
        Object[] result = Arrays.copyOf(values, values.length);
        Arrays.sort(result);
        return new StreamImpl(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream sorted(Comparator comparator) {
        Object[] result = Arrays.copyOf(values, values.length);
        Arrays.sort(result, comparator);
        return new StreamImpl(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream limit(long max) {
        return new StreamImpl(Arrays.copyOf(values, (int) Math.min(values.length, max)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream skip(long count) {
        int start = (int) Math.min(values.length, Math.max(0, count));
        return new StreamImpl(Arrays.copyOfRange(values, start, values.length));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream peek(Consumer action) {
        for (Object value : values) {
            action.accept(value);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void forEach(Consumer action) {
        for (Object value : values) {
            action.accept(value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void forEachOrdered(Consumer action) {
        forEach(action);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count() {
        return values.length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean anyMatch(Predicate predicate) {
        for (Object value : values) {
            if (predicate.test(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean allMatch(Predicate predicate) {
        for (Object value : values) {
            if (!predicate.test(value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean noneMatch(Predicate predicate) {
        return !anyMatch(predicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional findFirst() {
        return Optional.ofNullable(values.length == 0 ? null : values[0]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional findAny() {
        return findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
    public Object reduce(Object identity, BinaryOperator accumulator) {
        Object result = identity;
        for (Object value : values) {
            result = accumulator.apply(result, value);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object reduce(Object identity, BiFunction accumulator, BinaryOperator combiner) {
        Object result = identity;
        for (Object value : values) {
            result = accumulator.apply(result, value);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object collect(java.util.stream.Collector collector) {
        return ((Function) collector).apply(values);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(values, values.length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object[] toArray(IntFunction generator) {
        Object[] result = (Object[]) generator.apply(values.length);
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i];
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List toList() {
        return List.of(values);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
    }
}
