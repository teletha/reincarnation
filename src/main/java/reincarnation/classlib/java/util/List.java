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

import java.util.Comparator;
import java.util.function.UnaryOperator;

/**
 * The Java implementation of {@link java.util.List} which is transpiled into JavaScript.
 */
public interface List<E> extends SequencedCollection<E> {

    /**
     * Create the empty list.
     * 
     * @return A list.
     */
    static List of() {
        return new ArrayList();
    }

    /**
     * Create the list.
     * 
     * @param value A value.
     * @return A list.
     */
    static List of(Object value) {
        return ofArray(new Object[] {value});
    }

    /**
     * Create the list.
     * 
     * @param first A value.
     * @param second A value.
     * @return A list.
     */
    static List of(Object first, Object second) {
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
    static List of(Object first, Object second, Object third) {
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
    static List of(Object first, Object second, Object third, Object fourth) {
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
    static List of(Object first, Object second, Object third, Object fourth, Object fifth) {
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
    static List of(Object first, Object second, Object third, Object fourth, Object fifth, Object sixth) {
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
    static List of(Object first, Object second, Object third, Object fourth, Object fifth, Object sixth,
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
    static List of(Object first, Object second, Object third, Object fourth, Object fifth, Object sixth,
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
    static List of(Object first, Object second, Object third, Object fourth, Object fifth, Object sixth,
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
    static List of(Object first, Object second, Object third, Object fourth, Object fifth, Object sixth,
            Object seventh, Object eighth, Object ninth, Object tenth) {
        return ofArray(new Object[] {first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth});
    }

    /**
     * Create the list.
     * 
     * @param values Values.
     * @return A list.
     */
    static List of(Object... values) {
        return ofArray(values);
    }

    /**
     * Create the list from the collection.
     * 
     * @param collection A collection.
     * @return A list.
     */
    static List copyOf(Collection collection) {
        List list = new ArrayList();
        list.addAll(collection);
        return list;
    }

    /**
     * Get the value.
     * 
     * @param index An index.
     * @return A value.
     */
    Object get(int index);

    /**
     * Set the value.
     * 
     * @param index An index.
     * @param value A value.
     * @return A previous value.
     */
    Object set(int index, Object value);

    /**
     * Add the value at the index.
     * 
     * @param index An index.
     * @param value A value.
     */
    void add(int index, Object value);

    /**
     * Add all values at the index.
     * 
     * @param index An index.
     * @param collection A collection.
     * @return A result.
     */
    boolean addAll(int index, Collection collection);

    /**
     * Remove the value at the index.
     * 
     * @param index An index.
     * @return A removed value.
     */
    Object remove(int index);

    /**
     * Find the value.
     * 
     * @param value A value.
     * @return An index or -1.
     */
    int indexOf(Object value);

    /**
     * Find the value from the end.
     * 
     * @param value A value.
     * @return An index or -1.
     */
    int lastIndexOf(Object value);

    /**
     * Get the sub list.
     * 
     * @param from A start index.
     * @param to An end index.
     * @return A list.
     */
    List subList(int from, int to);

    /**
     * Sort the list.
     * 
     * @param comparator A comparator.
     */
    void sort(Comparator comparator);

    /**
     * Replace the values.
     * 
     * @param operator An operator.
     */
    void replaceAll(UnaryOperator operator);

    /**
     * Create the list from the array.
     * 
     * @param values Values.
     * @return A list.
     */
    private static List ofArray(Object[] values) {
        List list = new ArrayList();
        for (Object value : values) {
            list.add(value);
        }
        return list;
    }
}
