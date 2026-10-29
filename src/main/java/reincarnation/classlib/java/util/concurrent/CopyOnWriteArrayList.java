/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.util.concurrent;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import reincarnation.classlib.java.util.ArrayList;
import reincarnation.classlib.java.util.Collection;
import reincarnation.classlib.java.util.Iterator;
import reincarnation.classlib.java.util.List;
import reincarnation.classlib.java.util.ListIterator;

/**
 * The Java implementation of {@link java.util.concurrent.CopyOnWriteArrayList}.
 */
public class CopyOnWriteArrayList<E> implements List, reincarnation.classlib.java.util.RandomAccess,
        reincarnation.classlib.java.lang.Cloneable, reincarnation.classlib.java.io.Serializable {

    /** The delegate. */
    private final ArrayList delegate = new ArrayList();

    /**
     * Create an empty list.
     */
    public CopyOnWriteArrayList() {
        super();
    }

    /**
     * Create a list from the collection.
     * 
     * @param collection A collection.
     */
    public CopyOnWriteArrayList(java.util.Collection collection) {
        for (Object value : collection) {
            delegate.add(value);
        }
    }

    /**
     * Create a list from the array.
     * 
     * @param array An array.
     */
    public CopyOnWriteArrayList(Object[] array) {
        for (Object value : array) {
            delegate.add(value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return delegate.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Object value) {
        return delegate.contains(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.Iterator iterator() {
        return delegate.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object[] toArray() {
        return delegate.toArray();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object[] toArray(Object[] array) {
        return delegate.toArray(array);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(Object value) {
        return delegate.add(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object value) {
        return delegate.remove(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(Collection collection) {
        return delegate.containsAll(collection);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(Collection collection) {
        return delegate.addAll(collection);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(int index, Collection collection) {
        return delegate.addAll(index, collection);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAll(Collection collection) {
        return delegate.removeAll(collection);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean retainAll(Collection collection) {
        return delegate.retainAll(collection);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeIf(Predicate predicate) {
        return delegate.removeIf(predicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        delegate.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void forEach(Consumer action) {
        delegate.forEach(action);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public reincarnation.classlib.java.util.stream.Stream stream() {
        return delegate.stream();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object get(int index) {
        return delegate.get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object set(int index, Object value) {
        return delegate.set(index, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(int index, Object value) {
        delegate.add(index, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object remove(int index) {
        return delegate.remove(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int indexOf(Object value) {
        return delegate.indexOf(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int lastIndexOf(Object value) {
        return delegate.lastIndexOf(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List subList(int from, int to) {
        return delegate.subList(from, to);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListIterator listIterator() {
        return delegate.listIterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListIterator listIterator(int index) {
        return delegate.listIterator(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sort(Comparator comparator) {
        delegate.sort(comparator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void replaceAll(UnaryOperator operator) {
        delegate.replaceAll(operator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        return delegate.equals(other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return delegate.toString();
    }
}
