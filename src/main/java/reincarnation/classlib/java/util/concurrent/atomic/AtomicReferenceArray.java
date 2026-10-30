/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.util.concurrent.atomic;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import reincarnation.classlib.java.io.Serializable;

/**
 * The Java implementation of {@link java.util.concurrent.atomic.AtomicReferenceArray} which is
 * transpiled into JavaScript.
 */
public class AtomicReferenceArray<E> implements Serializable {

    /** The values. */
    private final Object[] values;

    /**
     * Create the atomic reference array.
     * 
     * @param length A length.
     */
    public AtomicReferenceArray(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length is negative");
        }
        this.values = new Object[length];
    }

    /**
     * Create the atomic reference array.
     * 
     * @param array An array.
     */
    public AtomicReferenceArray(E[] array) {
        this.values = new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            values[i] = array[i];
        }
    }

    /**
     * Get the length.
     * 
     * @return A length.
     */
    public final int length() {
        return values.length;
    }

    /**
     * Get the value.
     * 
     * @param index An index.
     * @return A value.
     */
    public final E get(int index) {
        return (E) values[index];
    }

    /**
     * Set the value.
     * 
     * @param index An index.
     * @param value A value.
     */
    public final void set(int index, E value) {
        values[index] = value;
    }

    /**
     * Set the value.
     * 
     * @param index An index.
     * @param value A value.
     */
    public final void lazySet(int index, E value) {
        values[index] = value;
    }

    /**
     * Set and get the value.
     * 
     * @param index An index.
     * @param value A value.
     * @return A previous value.
     */
    public final E getAndSet(int index, E value) {
        Object previous = values[index];
        values[index] = value;
        return (E) previous;
    }

    /**
     * Compare and set the value.
     * 
     * @param index An index.
     * @param expect An expected value.
     * @param update An update value.
     * @return A result.
     */
    public final boolean compareAndSet(int index, E expect, E update) {
        if (values[index] == expect) {
            values[index] = update;
            return true;
        }
        return false;
    }

    /**
     * Compare and set the value.
     * 
     * @param index An index.
     * @param expect An expected value.
     * @param update An update value.
     * @return A result.
     */
    public final boolean weakCompareAndSet(int index, E expect, E update) {
        return compareAndSet(index, expect, update);
    }

    /**
     * Update and get the value.
     * 
     * @param index An index.
     * @param updateFunction An update function.
     * @return A value.
     */
    public final E updateAndGet(int index, UnaryOperator<E> updateFunction) {
        E value = updateFunction.apply((E) values[index]);
        values[index] = value;
        return value;
    }

    /**
     * Get and update the value.
     * 
     * @param index An index.
     * @param updateFunction An update function.
     * @return A previous value.
     */
    public final E getAndUpdate(int index, UnaryOperator<E> updateFunction) {
        E previous = (E) values[index];
        values[index] = updateFunction.apply(previous);
        return previous;
    }

    /**
     * Accumulate and get the value.
     * 
     * @param index An index.
     * @param value A value.
     * @param accumulatorFunction An accumulator function.
     * @return A value.
     */
    public final E accumulateAndGet(int index, E value, BinaryOperator<E> accumulatorFunction) {
        E updated = accumulatorFunction.apply((E) values[index], value);
        values[index] = updated;
        return updated;
    }

    /**
     * Get and accumulate the value.
     * 
     * @param index An index.
     * @param value A value.
     * @param accumulatorFunction An accumulator function.
     * @return A previous value.
     */
    public final E getAndAccumulate(int index, E value, BinaryOperator<E> accumulatorFunction) {
        E previous = (E) values[index];
        values[index] = accumulatorFunction.apply(previous, value);
        return previous;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(String.valueOf(values[i]));
        }
        return builder.append(']').toString();
    }
}
