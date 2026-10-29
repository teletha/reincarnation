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
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * The Java implementation of {@link java.util.Set} which is transpiled into JavaScript.
 */
public class Set {

    /** The elements. */
    protected Object[] elements = new Object[0];

    /** The size. */
    private int size;

    /**
     * Create an empty set.
     */
    public Set() {
    }

    /**
     * Create a set from the collection.
     * 
     * @param collection A collection.
     */
    public Set(Collection collection) {
        addAll(collection);
    }

    /**
     * Create the set.
     * 
     * @return A set.
     */
    public static Set of() {
        return new Set();
    }

    /**
     * Create the set.
     * 
     * @param value A value.
     * @return A set.
     */
    public static Set of(Object value) {
        return ofArray(new Object[] {value});
    }

    /**
     * Create the set.
     * 
     * @param first A value.
     * @param second A value.
     * @return A set.
     */
    public static Set of(Object first, Object second) {
        return ofArray(new Object[] {first, second});
    }

    /**
     * Create the set.
     * 
     * @param first A value.
     * @param second A value.
     * @param third A value.
     * @return A set.
     */
    public static Set of(Object first, Object second, Object third) {
        return ofArray(new Object[] {first, second, third});
    }

    /**
     * Create the set.
     * 
     * @param first A value.
     * @param second A value.
     * @param third A value.
     * @param fourth A value.
     * @return A set.
     */
    public static Set of(Object first, Object second, Object third, Object fourth) {
        return ofArray(new Object[] {first, second, third, fourth});
    }

    /**
     * Create the set.
     * 
     * @param first A value.
     * @param second A value.
     * @param third A value.
     * @param fourth A value.
     * @param fifth A value.
     * @return A set.
     */
    public static Set of(Object first, Object second, Object third, Object fourth, Object fifth) {
        return ofArray(new Object[] {first, second, third, fourth, fifth});
    }

    /**
     * Create the set.
     * 
     * @param first A value.
     * @param second A value.
     * @param third A value.
     * @param fourth A value.
     * @param fifth A value.
     * @param sixth A value.
     * @return A set.
     */
    public static Set of(Object first, Object second, Object third, Object fourth, Object fifth, Object sixth) {
        return ofArray(new Object[] {first, second, third, fourth, fifth, sixth});
    }

    /**
     * Create the set.
     * 
     * @param first A value.
     * @param second A value.
     * @param third A value.
     * @param fourth A value.
     * @param fifth A value.
     * @param sixth A value.
     * @param seventh A value.
     * @return A set.
     */
    public static Set of(Object first, Object second, Object third, Object fourth, Object fifth, Object sixth,
            Object seventh) {
        return ofArray(new Object[] {first, second, third, fourth, fifth, sixth, seventh});
    }

    /**
     * Create the set.
     * 
     * @param first A value.
     * @param second A value.
     * @param third A value.
     * @param fourth A value.
     * @param fifth A value.
     * @param sixth A value.
     * @param seventh A value.
     * @param eighth A value.
     * @return A set.
     */
    public static Set of(Object first, Object second, Object third, Object fourth, Object fifth, Object sixth,
            Object seventh, Object eighth) {
        return ofArray(new Object[] {first, second, third, fourth, fifth, sixth, seventh, eighth});
    }

    /**
     * Create the set.
     * 
     * @param first A value.
     * @param second A value.
     * @param third A value.
     * @param fourth A value.
     * @param fifth A value.
     * @param sixth A value.
     * @param seventh A value.
     * @param eighth A value.
     * @param ninth A value.
     * @return A set.
     */
    public static Set of(Object first, Object second, Object third, Object fourth, Object fifth, Object sixth,
            Object seventh, Object eighth, Object ninth) {
        return ofArray(new Object[] {first, second, third, fourth, fifth, sixth, seventh, eighth, ninth});
    }

    /**
     * Create the set.
     * 
     * @param first A value.
     * @param second A value.
     * @param third A value.
     * @param fourth A value.
     * @param fifth A value.
     * @param sixth A value.
     * @param seventh A value.
     * @param eighth A value.
     * @param ninth A value.
     * @param tenth A value.
     * @return A set.
     */
    public static Set of(Object first, Object second, Object third, Object fourth, Object fifth, Object sixth,
            Object seventh, Object eighth, Object ninth, Object tenth) {
        return ofArray(new Object[] {first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth});
    }

    /**
     * Create the set.
     * 
     * @param values Values.
     * @return A set.
     */
    public static Set of(Object... values) {
        return ofArray(values);
    }

    /**
     * Create the set from the collection.
     * 
     * @param collection A collection.
     * @return A set.
     */
    public static Set copyOf(Collection collection) {
        return new Set(collection);
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
     * Check whether this set is empty.
     * 
     * @return A result.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Add the value.
     * 
     * @param value A value.
     * @return A result.
     */
    public boolean add(Object value) {
        if (contains(value)) {
            return false;
        }
        ensure(size + 1);
        elements[size++] = value;
        return true;
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
     * Check whether the value exists.
     * 
     * @param value A value.
     * @return A result.
     */
    public boolean contains(Object value) {
        return 0 <= indexOf(value);
    }

    /**
     * Clear this set.
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
            changed = add(value) || changed;
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
        return new SetIterator(this);
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
     * Get the first value.
     * 
     * @return A value.
     */
    public Object first() {
        return get(0);
    }

    /**
     * Get the last value.
     * 
     * @return A value.
     */
    public Object last() {
        return get(size - 1);
    }

    /**
     * Get the comparator.
     * 
     * @return A comparator.
     */
    public java.util.Comparator comparator() {
        return null;
    }

    /**
     * Remove and return the first value.
     * 
     * @return A value.
     */
    public Object pollFirst() {
        return isEmpty() ? null : removeAt(0);
    }

    /**
     * Remove and return the last value.
     * 
     * @return A value.
     */
    public Object pollLast() {
        return isEmpty() ? null : removeAt(size - 1);
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
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Set otherSet) {
            if (size != otherSet.size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!otherSet.contains(elements[i])) {
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
        int hash = 0;
        for (int i = 0; i < size; i++) {
            hash = (hash + Objects.hashCode(elements[i])) | 0;
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
     * Create the set from the array.
     * 
     * @param values Values.
     * @return A set.
     */
    private static Set ofArray(Object[] values) {
        Set set = new Set();
        for (Object value : values) {
            set.add(value);
        }
        return set;
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
     * Remove the value at the index.
     * 
     * @param index An index.
     * @return A removed value.
     */
    Object removeAt(int index) {
        Object removed = get(index);
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[--size] = null;
        return removed;
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
