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
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * The Java implementation of {@link java.util.List} which is transpiled into JavaScript.
 */
public class List {

    /** The elements. */
    private Object[] elements = new Object[0];

    /** The size. */
    private int size;

    /**
     * Create an empty list.
     */
    public List() {
    }

    /**
     * Create a list from the collection.
     * 
     * @param collection A collection.
     */
    public List(Collection collection) {
        addAll(collection);
    }

    /**
     * Create the list.
     * 
     * @param values Values.
     * @return A list.
     */
    public static List of() {
        return new List();
    }

    /**
     * Create the list.
     * 
     * @param value A value.
     * @return A list.
     */
    public static List of(Object value) {
        return ofArray(new Object[] {value});
    }

    /**
     * Create the list.
     * 
     * @param first A value.
     * @param second A value.
     * @return A list.
     */
    public static List of(Object first, Object second) {
        return ofArray(new Object[] {first, second});
    }

    /**
     * Create the list.
     * 
     * @param first A value.
     * @param second A value.
     * @param third A value.
     * @return A list.
     */
    public static List of(Object first, Object second, Object third) {
        return ofArray(new Object[] {first, second, third});
    }

    /**
     * Create the list.
     * 
     * @param first A value.
     * @param second A value.
     * @param third A value.
     * @param fourth A value.
     * @return A list.
     */
    public static List of(Object first, Object second, Object third, Object fourth) {
        return ofArray(new Object[] {first, second, third, fourth});
    }

    /**
     * Create the list.
     * 
     * @param first A value.
     * @param second A value.
     * @param third A value.
     * @param fourth A value.
     * @param fifth A value.
     * @return A list.
     */
    public static List of(Object first, Object second, Object third, Object fourth, Object fifth) {
        return ofArray(new Object[] {first, second, third, fourth, fifth});
    }

    /**
     * Create the list.
     * 
     * @param first A value.
     * @param second A value.
     * @param third A value.
     * @param fourth A value.
     * @param fifth A value.
     * @param sixth A value.
     * @return A list.
     */
    public static List of(Object first, Object second, Object third, Object fourth, Object fifth, Object sixth) {
        return ofArray(new Object[] {first, second, third, fourth, fifth, sixth});
    }

    /**
     * Create the list.
     * 
     * @param first A value.
     * @param second A value.
     * @param third A value.
     * @param fourth A value.
     * @param fifth A value.
     * @param sixth A value.
     * @param seventh A value.
     * @return A list.
     */
    public static List of(Object first, Object second, Object third, Object fourth, Object fifth, Object sixth,
            Object seventh) {
        return ofArray(new Object[] {first, second, third, fourth, fifth, sixth, seventh});
    }

    /**
     * Create the list.
     * 
     * @param first A value.
     * @param second A value.
     * @param third A value.
     * @param fourth A value.
     * @param fifth A value.
     * @param sixth A value.
     * @param seventh A value.
     * @param eighth A value.
     * @return A list.
     */
    public static List of(Object first, Object second, Object third, Object fourth, Object fifth, Object sixth,
            Object seventh, Object eighth) {
        return ofArray(new Object[] {first, second, third, fourth, fifth, sixth, seventh, eighth});
    }

    /**
     * Create the list.
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
     * @return A list.
     */
    public static List of(Object first, Object second, Object third, Object fourth, Object fifth, Object sixth,
            Object seventh, Object eighth, Object ninth) {
        return ofArray(new Object[] {first, second, third, fourth, fifth, sixth, seventh, eighth, ninth});
    }

    /**
     * Create the list.
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
     * @return A list.
     */
    public static List of(Object first, Object second, Object third, Object fourth, Object fifth, Object sixth,
            Object seventh, Object eighth, Object ninth, Object tenth) {
        return ofArray(new Object[] {first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth});
    }

    /**
     * Create the list.
     * 
     * @param values Values.
     * @return A list.
     */
    public static List of(Object... values) {
        return ofArray(values);
    }

    /**
     * Create the list from the array.
     * 
     * @param values Values.
     * @return A list.
     */
    private static List ofArray(Object[] values) {
        List list = new List();
        for (Object value : values) {
            list.add(value);
        }
        return list;
    }

    /**
     * Create the list from the collection.
     * 
     * @param collection A collection.
     * @return A list.
     */
    public static List copyOf(Collection collection) {
        return new List(collection);
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
     * Check whether this list is empty.
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
        ensure(size + 1);
        elements[size++] = value;
        return true;
    }

    /**
     * Add the value at the index.
     * 
     * @param index An index.
     * @param value A value.
     */
    public void add(int index, Object value) {
        ensure(size + 1);
        for (int i = size; index < i; i--) {
            elements[i] = elements[i - 1];
        }
        elements[index] = value;
        size++;
    }

    /**
     * Get the value.
     * 
     * @param index An index.
     * @return A value.
     */
    public Object get(int index) {
        check(index);
        return elements[index];
    }

    /**
     * Set the value.
     * 
     * @param index An index.
     * @param value A value.
     * @return A previous value.
     */
    public Object set(int index, Object value) {
        check(index);
        Object previous = elements[index];
        elements[index] = value;
        return previous;
    }

    /**
     * Remove the value at the index.
     * 
     * @param index An index.
     * @return A removed value.
     */
    public Object remove(int index) {
        check(index);
        Object removed = elements[index];
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[--size] = null;
        return removed;
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
        remove(index);
        return true;
    }

    /**
     * Find the value.
     * 
     * @param value A value.
     * @return An index or -1.
     */
    public int indexOf(Object value) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(elements[i], value)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Find the value from the end.
     * 
     * @param value A value.
     * @return An index or -1.
     */
    public int lastIndexOf(Object value) {
        for (int i = size - 1; 0 <= i; i--) {
            if (Objects.equals(elements[i], value)) {
                return i;
            }
        }
        return -1;
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
     * Clear this list.
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
     * Add all values at the index.
     * 
     * @param index An index.
     * @param collection A collection.
     * @return A result.
     */
    public boolean addAll(int index, Collection collection) {
        int current = index;
        for (Object value : collection) {
            add(current++, value);
        }
        return current != index;
    }

    /**
     * Remove all values.
     * 
     * @param collection A collection.
     * @return A result.
     */
    public boolean removeAll(Collection collection) {
        boolean changed = false;
        for (int i = size - 1; 0 <= i; i--) {
            if (containsValue(collection, elements[i])) {
                remove(i);
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
                remove(i);
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
        return new Iterator(this);
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
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof List otherList) {
            if (size != otherList.size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!Objects.equals(elements[i], otherList.elements[i])) {
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
     * Sort the list.
     * 
     * @param comparator A comparator.
     */
    public void sort(Comparator comparator) {
        Arrays.sort(elements, 0, size, comparator);
    }

    /**
     * Replace the values.
     * 
     * @param operator An operator.
     */
    public void replaceAll(UnaryOperator operator) {
        for (int i = 0; i < size; i++) {
            elements[i] = operator.apply(elements[i]);
        }
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
                remove(i);
                changed = true;
            }
        }
        return changed;
    }

    /**
     * Get the sub list.
     * 
     * @param from A start index.
     * @param to An end index.
     * @return A list.
     */
    public List subList(int from, int to) {
        List list = new List();
        for (int i = from; i < to; i++) {
            list.add(elements[i]);
        }
        return list;
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
     * Check the index.
     * 
     * @param index An index.
     */
    private void check(int index) {
        if (index < 0 || size <= index) {
            throw new IndexOutOfBoundsException();
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
