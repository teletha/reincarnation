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

import reincarnation.classlib.java.io.Serializable;
import reincarnation.classlib.java.lang.Cloneable;
import js.lang.NativeObject;

/**
 * The Java implementation of {@link java.util.EnumSet} which is transpiled into JavaScript.
 */
public class EnumSet<E extends Enum<E>> extends AbstractSet<E> implements Cloneable, Serializable {

    /** The element type. */
    private final Class<E> elementType;

    /** The enum constants. */
    private final E[] constants;

    /** The present flags. */
    private final boolean[] present;

    /**
     * Create the set.
     * 
     * @param elementType An element type.
     */
    private EnumSet(Class<E> elementType) {
        this.elementType = elementType;
        this.constants = (E[]) constants(elementType);
        this.present = new boolean[constants.length];
    }

    /**
     * Get the enum constants.
     * 
     * @param elementType An element type.
     * @return Constants.
     */
    private static Object[] constants(Class elementType) {
        return js.lang.NativeObject.enumConstants(elementType);
    }

    /**
     * Create the empty set.
     * 
     * @param <E> An element type.
     * @param elementType An element type.
     * @return A set.
     */
    public static <E extends Enum<E>> EnumSet<E> noneOf(Class<E> elementType) {
        if (elementType == null) {
            throw new NullPointerException("elementType");
        }
        return new EnumSet(elementType);
    }

    /**
     * Create the set with all elements.
     * 
     * @param <E> An element type.
     * @param elementType An element type.
     * @return A set.
     */
    public static <E extends Enum<E>> EnumSet<E> allOf(Class<E> elementType) {
        EnumSet<E> set = noneOf(elementType);
        for (int i = 0; i < set.constants.length; i++) {
            set.present[i] = true;
        }
        return set;
    }

    /**
     * Create the set.
     * 
     * @param <E> An element type.
     * @param value A value.
     * @return A set.
     */
    public static <E extends Enum<E>> EnumSet<E> of(E value) {
        EnumSet<E> set = noneOf((Class<E>) NativeObject.classOf(value));
        set.add(value);
        return set;
    }

    /**
     * Create the set.
     * 
     * @param <E> An element type.
     * @param first A first value.
     * @param second A second value.
     * @return A set.
     */
    public static <E extends Enum<E>> EnumSet<E> of(E first, E second) {
        EnumSet<E> set = of(first);
        set.add(second);
        return set;
    }

    /**
     * Create the set.
     * 
     * @param <E> An element type.
     * @param first A first value.
     * @param second A second value.
     * @param third A third value.
     * @return A set.
     */
    public static <E extends Enum<E>> EnumSet<E> of(E first, E second, E third) {
        EnumSet<E> set = of(first, second);
        set.add(third);
        return set;
    }

    /**
     * Create the set.
     * 
     * @param <E> An element type.
     * @param first A first value.
     * @param second A second value.
     * @param third A third value.
     * @param fourth A fourth value.
     * @return A set.
     */
    public static <E extends Enum<E>> EnumSet<E> of(E first, E second, E third, E fourth) {
        EnumSet<E> set = of(first, second, third);
        set.add(fourth);
        return set;
    }

    /**
     * Create the set.
     * 
     * @param <E> An element type.
     * @param first A first value.
     * @param second A second value.
     * @param third A third value.
     * @param fourth A fourth value.
     * @param fifth A fifth value.
     * @return A set.
     */
    public static <E extends Enum<E>> EnumSet<E> of(E first, E second, E third, E fourth, E fifth) {
        EnumSet<E> set = of(first, second, third, fourth);
        set.add(fifth);
        return set;
    }

    /**
     * Create the set of the range.
     * 
     * @param <E> An element type.
     * @param from A start value.
     * @param to An end value.
     * @return A set.
     */
    public static <E extends Enum<E>> EnumSet<E> range(E from, E to) {
        if (from == null || to == null) {
            throw new NullPointerException();
        }
        EnumSet<E> set = noneOf((Class<E>) NativeObject.classOf(from));
        int start = ((Enum) from).ordinal();
        int end = ((Enum) to).ordinal();
        if (start > end) {
            throw new IllegalArgumentException("from > to");
        }
        for (int i = start; i <= end; i++) {
            set.present[i] = true;
        }
        return set;
    }

    /**
     * Copy the set.
     * 
     * @param <E> An element type.
     * @param set A set.
     * @return A set.
     */
    public static <E extends Enum<E>> EnumSet<E> copyOf(EnumSet<E> set) {
        return (EnumSet<E>) set.clone();
    }

    /**
     * Copy the collection.
     * 
     * @param <E> An element type.
     * @param collection A collection.
     * @return A set.
     */
    public static <E extends Enum<E>> EnumSet<E> copyOf(java.util.Collection<E> collection) {
        if (collection == null) {
            throw new NullPointerException("collection");
        }
        if (collection instanceof EnumSet) {
            return copyOf((EnumSet<E>) collection);
        }
        EnumSet<E> set = null;
        for (Object value : collection) {
            if (set == null) {
                set = of((E) value);
            } else {
                set.add(value);
            }
        }
        if (set == null) {
            throw new IllegalArgumentException("Collection is empty");
        }
        return set;
    }

    /**
     * Create the complement set.
     * 
     * @param <E> An element type.
     * @param set A set.
     * @return A set.
     */
    public static <E extends Enum<E>> EnumSet<E> complementOf(EnumSet<E> set) {
        EnumSet<E> result = allOf((Class<E>) set.elementType);
        for (int i = 0; i < set.present.length; i++) {
            if (set.present[i]) {
                result.present[i] = false;
            }
        }
        return result;
    }

    /**
     * Get the index of the value.
     * 
     * @param value A value.
     * @return An index or -1.
     */
    private int indexOfValue(Object value) {
        for (int i = 0; i < constants.length; i++) {
            if (constants[i] == value) {
                return i;
            }
        }
        return -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        int size = 0;
        for (int i = 0; i < present.length; i++) {
            if (present[i]) {
                size++;
            }
        }
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Object value) {
        boolean contains = false;
        int index = indexOfValue(value);
        if (index >= 0) {
            contains = present[index];
        }
        return contains;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(Object value) {
        boolean added = false;
        int index = indexOfValue(value);
        if (index >= 0) {
            added = present[index] == false;
            present[index] = true;
        } else {
            throw new ClassCastException("Cannot add " + value + " to " + elementType.getName());
        }
        return added;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object value) {
        int index = indexOfValue(value);
        if (index < 0 || !present[index]) {
            return false;
        }
        present[index] = false;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        for (int i = 0; i < present.length; i++) {
            present[i] = false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.Iterator<E> iterator() {
        List<E> list = new ArrayList();
        for (int i = 0; i < present.length; i++) {
            if (present[i]) {
                list.add(constants[i]);
            }
        }
        return list.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < present.length; i++) {
            if (present[i]) {
                if (builder.length() > 1) {
                    builder.append(", ");
                }
                builder.append(constants[i]);
            }
        }
        return builder.append(']').toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EnumSet<E> clone() {
        EnumSet<E> set = new EnumSet<>(elementType);
        for (int i = 0; i < present.length; i++) {
            set.present[i] = present[i];
        }
        return set;
    }
}
