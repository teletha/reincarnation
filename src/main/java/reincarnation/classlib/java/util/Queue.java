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

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * The Java implementation of {@link java.util.Queue} which is transpiled into JavaScript.
 */
public class Queue {

    /** The elements. */
    protected Object[] elements = new Object[0];

    /** The size. */
    private int size;

    /**
     * Create an empty queue.
     */
    public Queue() {
    }

    /**
     * Create a queue from the collection.
     * 
     * @param collection A collection.
     */
    public Queue(Collection collection) {
        addAll(collection);
    }

    /**
     * Add the value.
     * 
     * @param value A value.
     * @return A result.
     */
    public boolean add(Object value) {
        ensure(size + 1);
        elements[size++] = value;
        return true;
    }

    /**
     * Offer the value.
     * 
     * @param value A value.
     * @return A result.
     */
    public boolean offer(Object value) {
        return add(value);
    }

    /**
     * Remove the first value.
     * 
     * @return A value.
     */
    public Object remove() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return poll();
    }

    /**
     * Poll the first value.
     * 
     * @return A value or null.
     */
    public Object poll() {
        return isEmpty() ? null : removeAt(0);
    }

    /**
     * Get the first value.
     * 
     * @return A value.
     */
    public Object element() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return elements[0];
    }

    /**
     * Peek the first value.
     * 
     * @return A value or null.
     */
    public Object peek() {
        return isEmpty() ? null : elements[0];
    }

    /**
     * Remove the value.
     * 
     * @param value A value.
     * @return A result.
     */
    public boolean remove(Object value) {
        int index = indexOf(value);
        if (index < 0) {
            return false;
        }
        removeAt(index);
        return true;
    }

    /**
     * Get the size.
     * 
     * @return A size.
     */
    public int size() {
        return size;
    }

    /**
     * Check whether this queue is empty.
     * 
     * @return A result.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Check whether the value exists.
     * 
     * @param value A value.
     * @return A result.
     */
    public boolean contains(Object value) {
        return 0 <= indexOf(value);
    }

    /**
     * Clear this queue.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    /**
     * Add all values.
     * 
     * @param collection A collection.
     * @return A result.
     */
    public boolean addAll(Collection collection) {
        boolean changed = false;
        for (Object value : collection) {
            add(value);
            changed = true;
        }
        return changed;
    }

    /**
     * Remove all values.
     * 
     * @param collection A collection.
     * @return A result.
     */
    public boolean removeAll(Collection collection) {
        boolean changed = false;
        for (Object value : collection) {
            while (remove(value)) {
                changed = true;
            }
        }
        return changed;
    }

    /**
     * Retain all values.
     * 
     * @param collection A collection.
     * @return A result.
     */
    public boolean retainAll(Collection collection) {
        boolean changed = false;
        for (int i = size - 1; 0 <= i; i--) {
            if (!containsValue(collection, elements[i])) {
                removeAt(i);
                changed = true;
            }
        }
        return changed;
    }

    /**
     * Check whether all values exist.
     * 
     * @param collection A collection.
     * @return A result.
     */
    public boolean containsAll(Collection collection) {
        for (Object value : collection) {
            if (!contains(value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Remove the matched values.
     * 
     * @param predicate A predicate.
     * @return A result.
     */
    public boolean removeIf(Predicate predicate) {
        boolean changed = false;
        for (int i = size - 1; 0 <= i; i--) {
            if (predicate.test(elements[i])) {
                removeAt(i);
                changed = true;
            }
        }
        return changed;
    }

    /**
     * Create the array.
     * 
     * @return An array.
     */
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    /**
     * Create the array.
     * 
     * @param array A destination array.
     * @return An array.
     */
    public Object[] toArray(Object[] array) {
        Object[] result = array.length < size ? new Object[size] : array;
        for (int i = 0; i < size; i++) {
            result[i] = elements[i];
        }
        return result;
    }

    /**
     * Create the iterator.
     * 
     * @return An iterator.
     */
    public java.util.Iterator iterator() {
        return new QueueIterator(this, false);
    }

    /**
     * Invoke the action for each value.
     * 
     * @param action An action.
     */
    public void forEach(Consumer action) {
        for (int i = 0; i < size; i++) {
            action.accept(elements[i]);
        }
    }

    /**
     * Create the stream.
     * 
     * @return A stream.
     */
    public java.util.stream.Stream stream() {
        return java.util.stream.Stream.of(toArray());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Queue otherQueue) {
            if (size != otherQueue.size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!Objects.equals(elements[i], otherQueue.elements[i])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 1;
        for (int i = 0; i < size; i++) {
            hash = (hash * 31 + Objects.hashCode(elements[i])) | 0;
        }
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String text = "[";
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                text += ", ";
            }
            text += elements[i];
        }
        return text + "]";
    }

    /**
     * Get the value.
     * 
     * @param index An index.
     * @return A value.
     */
    protected Object get(int index) {
        if (index < 0 || size <= index) {
            throw new IndexOutOfBoundsException();
        }
        return elements[index];
    }

    /**
     * Insert the value at the index.
     * 
     * @param index An index.
     * @param value A value.
     */
    protected void insert(int index, Object value) {
        ensure(size + 1);
        for (int i = size; index < i; i--) {
            elements[i] = elements[i - 1];
        }
        elements[index] = value;
        size++;
    }

    /**
     * Remove the value at the index.
     * 
     * @param index An index.
     * @return A removed value.
     */
    protected Object removeAt(int index) {
        Object removed = get(index);
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[--size] = null;
        return removed;
    }

    /**
     * Get the index of the value.
     * 
     * @param value A value.
     * @return An index or -1.
     */
    private int indexOf(Object value) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(elements[i], value)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Ensure the capacity.
     * 
     * @param capacity A capacity.
     */
    private void ensure(int capacity) {
        if (elements.length < capacity) {
            elements = Arrays.copyOf(elements, Math.max(capacity, elements.length == 0 ? 10 : elements.length * 2));
        }
    }

    /**
     * Check whether the collection contains the value.
     * 
     * @param collection A collection.
     * @param value A value.
     * @return A result.
     */
    private static boolean containsValue(Collection collection, Object value) {
        for (Object item : collection) {
            if (Objects.equals(item, value)) {
                return true;
            }
        }
        return false;
    }
}
