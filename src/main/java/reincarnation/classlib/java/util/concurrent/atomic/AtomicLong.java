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

import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;
import reincarnation.classlib.java.io.Serializable;

/**
 * The Java implementation of {@link java.util.concurrent.atomic.AtomicLong} which is transpiled
 * into JavaScript.
 */
public class AtomicLong extends Number implements Serializable {

    /** The value. */
    private long value;

    /**
     * Create the atomic long.
     */
    public AtomicLong() {
    }

    /**
     * Create the atomic long.
     * 
     * @param initialValue An initial value.
     */
    public AtomicLong(long initialValue) {
        this.value = initialValue;
    }

    /**
     * Get the value.
     * 
     * @return A value.
     */
    public final long get() {
        return value;
    }

    /**
     * Set the value.
     * 
     * @param newValue A value.
     */
    public final void set(long newValue) {
        this.value = newValue;
    }

    /**
     * Set the value.
     * 
     * @param newValue A value.
     */
    public final void lazySet(long newValue) {
        this.value = newValue;
    }

    /**
     * Set and get the value.
     * 
     * @param newValue A value.
     * @return A previous value.
     */
    public final long getAndSet(long newValue) {
        long previous = value;
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
    public final boolean compareAndSet(long expect, long update) {
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
    public final boolean weakCompareAndSet(long expect, long update) {
        return compareAndSet(expect, update);
    }

    /**
     * Increment and get the value.
     * 
     * @return A value.
     */
    public final long incrementAndGet() {
        return ++value;
    }

    /**
     * Decrement and get the value.
     * 
     * @return A value.
     */
    public final long decrementAndGet() {
        return --value;
    }

    /**
     * Get and increment the value.
     * 
     * @return A previous value.
     */
    public final long getAndIncrement() {
        return value++;
    }

    /**
     * Get and decrement the value.
     * 
     * @return A previous value.
     */
    public final long getAndDecrement() {
        return value--;
    }

    /**
     * Add and get the value.
     * 
     * @param delta A delta.
     * @return A value.
     */
    public final long addAndGet(long delta) {
        value += delta;
        return value;
    }

    /**
     * Get and add the value.
     * 
     * @param delta A delta.
     * @return A previous value.
     */
    public final long getAndAdd(long delta) {
        long previous = value;
        value += delta;
        return previous;
    }

    /**
     * Update and get the value.
     * 
     * @param updateFunction An update function.
     * @return A value.
     */
    public final long updateAndGet(LongUnaryOperator updateFunction) {
        value = updateFunction.applyAsLong(value);
        return value;
    }

    /**
     * Get and update the value.
     * 
     * @param updateFunction An update function.
     * @return A previous value.
     */
    public final long getAndUpdate(LongUnaryOperator updateFunction) {
        long previous = value;
        value = updateFunction.applyAsLong(previous);
        return previous;
    }

    /**
     * Accumulate and get the value.
     * 
     * @param value A value.
     * @param accumulatorFunction An accumulator function.
     * @return A value.
     */
    public final long accumulateAndGet(long value, LongBinaryOperator accumulatorFunction) {
        this.value = accumulatorFunction.applyAsLong(this.value, value);
        return this.value;
    }

    /**
     * Get and accumulate the value.
     * 
     * @param value A value.
     * @param accumulatorFunction An accumulator function.
     * @return A previous value.
     */
    public final long getAndAccumulate(long value, LongBinaryOperator accumulatorFunction) {
        long previous = this.value;
        this.value = accumulatorFunction.applyAsLong(previous, value);
        return previous;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int intValue() {
        return (int) value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long longValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float floatValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double doubleValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
