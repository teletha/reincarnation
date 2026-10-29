/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * The Java implementation of {@link java.util.Optional} which is transpiled into JavaScript.
 */
public final class Optional {

    /** The value. */
    private final Object value;

    /**
     * Create the optional.
     * 
     * @param value A value.
     */
    private Optional(Object value) {
        this.value = value;
    }

    /**
     * Create the optional.
     * 
     * @param value A value.
     * @return An optional.
     */
    public static Optional of(Object value) {
        if (value == null) {
            throw new NullPointerException();
        }
        return new Optional(value);
    }

    /**
     * Create the optional.
     * 
     * @param value A value.
     * @return An optional.
     */
    public static Optional ofNullable(Object value) {
        return new Optional(value);
    }

    /**
     * Create the empty optional.
     * 
     * @return An optional.
     */
    public static Optional empty() {
        return new Optional(null);
    }

    /**
     * Check whether the value exists.
     * 
     * @return A result.
     */
    public boolean isPresent() {
        return value != null;
    }

    /**
     * Check whether the value is empty.
     * 
     * @return A result.
     */
    public boolean isEmpty() {
        return value == null;
    }

    /**
     * Get the value.
     * 
     * @return A value.
     */
    public Object get() {
        if (value == null) {
            throw new java.util.NoSuchElementException();
        }
        return value;
    }

    /**
     * Get the value or the alternative.
     * 
     * @param alternative An alternative.
     * @return A value.
     */
    public Object orElse(Object alternative) {
        return value == null ? alternative : value;
    }

    /**
     * Get the value or the supplied value.
     * 
     * @param supplier A supplier.
     * @return A value.
     */
    public Object orElseGet(Supplier supplier) {
        return value == null ? supplier.get() : value;
    }

    /**
     * Get the value or throw.
     * 
     * @return A value.
     */
    public Object orElseThrow() {
        if (value == null) {
            throw new java.util.NoSuchElementException();
        }
        return value;
    }

    /**
     * Get the value or throw.
     * 
     * @param supplier An exception supplier.
     * @return A value.
     */
    public Object orElseThrow(Supplier supplier) {
        if (value == null) {
            throw (RuntimeException) supplier.get();
        }
        return value;
    }

    /**
     * Run the action.
     * 
     * @param action An action.
     */
    public void ifPresent(Consumer action) {
        if (value != null) {
            action.accept(value);
        }
    }

    /**
     * Run the action.
     * 
     * @param action An action.
     * @param emptyAction An empty action.
     */
    public void ifPresentOrElse(Consumer action, Runnable emptyAction) {
        if (value != null) {
            action.accept(value);
        } else {
            emptyAction.run();
        }
    }

    /**
     * Map the value.
     * 
     * @param mapper A mapper.
     * @return An optional.
     */
    public Optional map(Function mapper) {
        return value == null ? this : Optional.ofNullable(mapper.apply(value));
    }

    /**
     * Flat-map the value.
     * 
     * @param mapper A mapper.
     * @return An optional.
     */
    public Optional flatMap(Function mapper) {
        return value == null ? this : (Optional) mapper.apply(value);
    }

    /**
     * Filter the value.
     * 
     * @param predicate A predicate.
     * @return An optional.
     */
    public Optional filter(Predicate predicate) {
        return value == null || predicate.test(value) ? this : Optional.empty();
    }

    /**
     * Create the stream.
     * 
     * @return A stream.
     */
    public java.util.stream.Stream stream() {
        return java.util.stream.Stream
                .of(value == null ? new Object[0] : new Object[] {value});
    }

    /**
     * Get this optional or the supplied optional.
     * 
     * @param supplier A supplier.
     * @return An optional.
     */
    public Optional or(Supplier supplier) {
        return value == null ? (Optional) supplier.get() : this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof Optional && Objects.equals(value, ((Optional) other).value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return value == null ? "Optional.empty" : "Optional[" + value + "]";
    }}
