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
import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 * The Java implementation of {@link java.util.AbstractList} which is transpiled into JavaScript.
 */
public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E> {

    /**
     * Create an empty list.
     */
    protected AbstractList() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object get(int index) {
        return super.get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object set(int index, Object value) {
        return super.set(index, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(int index, Object value) {
        insert(index, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(int index, Collection collection) {
        int current = index;
        boolean changed = false;
        for (Object value : collection) {
            add(current++, value);
            changed = true;
        }
        return changed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object remove(int index) {
        return removeAt(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int indexOf(Object value) {
        return super.indexOf(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int lastIndexOf(Object value) {
        for (int i = size() - 1; 0 <= i; i--) {
            if (Objects.equals(elements[i], value)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List subList(int from, int to) {
        List list = new ArrayList();
        for (int i = from; i < to; i++) {
            list.add(elements[i]);
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListIterator listIterator() {
        return new ArrayIterator(this, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListIterator listIterator(int index) {
        ListIterator iterator = new ArrayIterator(this, false);
        for (int i = 0; i < index; i++) {
            iterator.next();
        }
        return iterator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sort(Comparator comparator) {
        Arrays.sort(elements, 0, size(), comparator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void replaceAll(UnaryOperator operator) {
        for (int i = 0; i < size(); i++) {
            elements[i] = operator.apply(elements[i]);
        }
    }
}
