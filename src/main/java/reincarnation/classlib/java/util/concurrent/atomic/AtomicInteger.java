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

import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;
import reincarnation.classlib.java.io.Serializable;

/**
 * The Java implementation of {@link java.util.concurrent.atomic.AtomicInteger} which is transpiled
 * into JavaScript.
 */
public class AtomicInteger extends Number implements Serializable {

    /** The value. */
    private int value;

    /**
     * Create the atomic integer.
     */
    public AtomicInteger() {
    }

    /**
     * Create the atomic integer.
     * 
     * @param initialValue An initial value.
     */
    public AtomicInteger(int initialValue) {
        this.value = initialValue;
    }

    /**
     * Get the value.
     * 
     * @return A value.
     */
    public final int get() {
        return value;
    }

    /**
     * Set the value.
     * 
     * @param newValue A value.
     */
    public final void set(int newValue) {
        this.value = newValue;
    }

    /**
     * Set the value.
     * 
     * @param newValue A value.
     */
    public final void lazySet(int newValue) {
        this.value = newValue;
    }

    /**
     * Set and get the value.
     * 
     * @param newValue A value.
     * @return A previous value.
     */
    public final int getAndSet(int newValue) {
        int previous = value;
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
    public final boolean compareAndSet(int expect, int update) {
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
    public final boolean weakCompareAndSet(int expect, int update) {
        return compareAndSet(expect, update);
    }

    /**
     * Increment and get the value.
     * 
     * @return A value.
     */
    public final int incrementAndGet() {
        return ++value;
    }

    /**
     * Decrement and get the value.
     * 
     * @return A value.
     */
    public final int decrementAndGet() {
        return --value;
    }

    /**
     * Get and increment the value.
     * 
     * @return A previous value.
     */
    public final int getAndIncrement() {
        return value++;
    }

    /**
     * Get and decrement the value.
     * 
     * @return A previous value.
     */
    public final int getAndDecrement() {
        return value--;
    }

    /**
     * Add and get the value.
     * 
     * @param delta A delta.
     * @return A value.
     */
    public final int addAndGet(int delta) {
        value += delta;
        return value;
    }

    /**
     * Get and add the value.
     * 
     * @param delta A delta.
     * @return A previous value.
     */
    public final int getAndAdd(int delta) {
        int previous = value;
        value += delta;
        return previous;
    }

    /**
     * Update and get the value.
     * 
     * @param updateFunction An update function.
     * @return A value.
     */
    public final int updateAndGet(IntUnaryOperator updateFunction) {
        value = updateFunction.applyAsInt(value);
        return value;
    }

    /**
     * Get and update the value.
     * 
     * @param updateFunction An update function.
     * @return A previous value.
     */
    public final int getAndUpdate(IntUnaryOperator updateFunction) {
        int previous = value;
        value = updateFunction.applyAsInt(previous);
        return previous;
    }

    /**
     * Accumulate and get the value.
     * 
     * @param value A value.
     * @param accumulatorFunction An accumulator function.
     * @return A value.
     */
    public final int accumulateAndGet(int value, IntBinaryOperator accumulatorFunction) {
        this.value = accumulatorFunction.applyAsInt(this.value, value);
        return this.value;
    }

    /**
     * Get and accumulate the value.
     * 
     * @param value A value.
     * @param accumulatorFunction An accumulator function.
     * @return A previous value.
     */
    public final int getAndAccumulate(int value, IntBinaryOperator accumulatorFunction) {
        int previous = this.value;
        this.value = accumulatorFunction.applyAsInt(previous, value);
        return previous;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int intValue() {
        return value;
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
