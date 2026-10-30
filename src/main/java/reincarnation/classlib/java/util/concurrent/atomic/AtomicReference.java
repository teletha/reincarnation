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
 * The Java implementation of {@link java.util.concurrent.atomic.AtomicReference} which is
 * transpiled into JavaScript.
 */
public class AtomicReference<T> implements Serializable {

    /** The value. */
    private T value;

    /**
     * Create the atomic reference.
     */
    public AtomicReference() {
    }

    /**
     * Create the atomic reference.
     * 
     * @param initialValue An initial value.
     */
    public AtomicReference(T initialValue) {
        this.value = initialValue;
    }

    /**
     * Get the value.
     * 
     * @return A value.
     */
    public final T get() {
        return value;
    }

    /**
     * Set the value.
     * 
     * @param newValue A value.
     */
    public final void set(T newValue) {
        this.value = newValue;
    }

    /**
     * Set the value.
     * 
     * @param newValue A value.
     */
    public final void lazySet(T newValue) {
        this.value = newValue;
    }

    /**
     * Set and get the value.
     * 
     * @param newValue A value.
     * @return A previous value.
     */
    public final T getAndSet(T newValue) {
        T previous = value;
        value = newValue;
        return previous;
    }

    /**
     * Compare and set the value.
     * 
     * @param expect An expected value.
     * @param update An update value.
     * @return A result.
     */
    public final boolean compareAndSet(T expect, T update) {
        if (value == expect) {
            value = update;
            return true;
        }
        return false;
    }

    /**
     * Compare and set the value.
     * 
     * @param expect An expected value.
     * @param update An update value.
     * @return A result.
     */
    public final boolean weakCompareAndSet(T expect, T update) {
        return compareAndSet(expect, update);
    }

    /**
     * Update and get the value.
     * 
     * @param updateFunction An update function.
     * @return A value.
     */
    public final T updateAndGet(UnaryOperator<T> updateFunction) {
        value = updateFunction.apply(value);
        return value;
    }

    /**
     * Get and update the value.
     * 
     * @param updateFunction An update function.
     * @return A previous value.
     */
    public final T getAndUpdate(UnaryOperator<T> updateFunction) {
        T previous = value;
        value = updateFunction.apply(previous);
        return previous;
    }

    /**
     * Accumulate and get the value.
     * 
     * @param value A value.
     * @param accumulatorFunction An accumulator function.
     * @return A value.
     */
    public final T accumulateAndGet(T value, BinaryOperator<T> accumulatorFunction) {
        this.value = accumulatorFunction.apply(this.value, value);
        return this.value;
    }

    /**
     * Get and accumulate the value.
     * 
     * @param value A value.
     * @param accumulatorFunction An accumulator function.
     * @return A previous value.
     */
    public final T getAndAccumulate(T value, BinaryOperator<T> accumulatorFunction) {
        T previous = this.value;
        this.value = accumulatorFunction.apply(previous, value);
        return previous;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
