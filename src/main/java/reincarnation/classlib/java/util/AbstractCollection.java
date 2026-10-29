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
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * The Java implementation of {@link java.util.AbstractCollection} which is transpiled into
 * JavaScript.
 */
public abstract class AbstractCollection<E> implements Collection<E> {

    /** The elements. */
    protected Object[] elements = new Object[0];

    /** The size. */
    private int size;

    /**
     * Create an empty collection.
     */
    protected AbstractCollection() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Object value) {
        return 0 <= indexOf(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(Object value) {
        ensure(size + 1);
        elements[size++] = value;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object value) {
        int index = indexOf(value);
        if (index < 0) {
            return false;
        }
        removeAt(index);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(Collection collection) {
        for (Object value : collection) {
            if (!contains(value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(Collection collection) {
        boolean changed = false;
        for (Object value : collection) {
            changed = add(value) || changed;
        }
        return changed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAll(Collection collection) {
        boolean changed = false;
        for (int i = size - 1; 0 <= i; i--) {
            if (containsValue(collection, elements[i])) {
                removeAt(i);
                changed = true;
            }
        }
        return changed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object[] toArray(Object[] array) {
        Object[] result = array.length < size ? new Object[size] : array;
        for (int i = 0; i < size; i++) {
            result[i] = elements[i];
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public java.util.Iterator<E> iterator() {
        return (java.util.Iterator<E>) (java.util.Iterator) new ArrayIterator(this, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void forEach(Consumer action) {
        for (int i = 0; i < size; i++) {
            action.accept(elements[i]);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.stream.Stream stream() {
        return java.util.stream.Stream.of(toArray());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Collection otherCollection) {
            if (size != otherCollection.size()) {
                return false;
            }
            java.util.Iterator iterator = otherCollection.iterator();
            for (int i = 0; i < size; i++) {
                if (!iterator.hasNext() || !Objects.equals(elements[i], iterator.next())) {
                    return false;
                }
            }
            return !iterator.hasNext();
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
     * Set the value.
     * 
     * @param index An index.
     * @param value A value.
     * @return A previous value.
     */
    protected Object set(int index, Object value) {
        Object previous = get(index);
        elements[index] = value;
        return previous;
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
    protected int indexOf(Object value) {
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
    protected void ensure(int capacity) {
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
