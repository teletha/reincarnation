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
import java.util.function.Predicate;

/**
 * The Java implementation of {@link java.util.Collection} which is transpiled into JavaScript.
 */
public interface Collection<E> extends reincarnation.classlib.java.lang.Iterable<E> {

    /**
     * Get the size.
     * 
     * @return A size.
     */
    int size();

    /**
     * Check whether this collection is empty.
     * 
     * @return A result.
     */
    boolean isEmpty();

    /**
     * Check whether the value exists.
     * 
     * @param value A value.
     * @return A result.
     */
    boolean contains(Object value);

    /**
     * Create the iterator.
     * 
     * @return An iterator.
     */
    @Override
    java.util.Iterator<E> iterator();

    /**
     * Create the array.
     * 
     * @return An array.
     */
    Object[] toArray();

    /**
     * Create the array.
     * 
     * @param array A destination array.
     * @return An array.
     */
    Object[] toArray(Object[] array);

    /**
     * Add the value.
     * 
     * @param value A value.
     * @return A result.
     */
    boolean add(Object value);

    /**
     * Remove the value.
     * 
     * @param value A value.
     * @return A result.
     */
    boolean remove(Object value);

    /**
     * Check whether all values exist.
     * 
     * @param collection A collection.
     * @return A result.
     */
    boolean containsAll(Collection collection);

    /**
     * Add all values.
     * 
     * @param collection A collection.
     * @return A result.
     */
    boolean addAll(Collection collection);

    /**
     * Remove all values.
     * 
     * @param collection A collection.
     * @return A result.
     */
    boolean removeAll(Collection collection);

    /**
     * Retain all values.
     * 
     * @param collection A collection.
     * @return A result.
     */
    boolean retainAll(Collection collection);

    /**
     * Remove the matched values.
     * 
     * @param predicate A predicate.
     * @return A result.
     */
    boolean removeIf(Predicate predicate);

    /**
     * Clear this collection.
     */
    void clear();

    /**
     * Invoke the action for each value.
     * 
     * @param action An action.
     */
    void forEach(Consumer action);

    /**
     * Create the stream.
     * 
     * @return A stream.
     */
    java.util.stream.Stream stream();

    /**
     * {@inheritDoc}
     */
    @Override
    boolean equals(Object other);

    /**
     * {@inheritDoc}
     */
    @Override
    int hashCode();
}
