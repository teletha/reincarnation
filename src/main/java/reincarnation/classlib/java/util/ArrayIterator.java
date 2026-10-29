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

import java.util.NoSuchElementException;

/**
 * The {@link Iterator} of {@link AbstractCollection}.
 */
final class ArrayIterator implements Iterator {

    /** The collection. */
    private final AbstractCollection owner;

    /** Whether this iterator walks from the last value to the first one. */
    private final boolean descending;

    /** The current index. */
    private int index;

    /** The index of the value which was returned by the last {@code next} call. */
    private int last = -1;

    /**
     * Create the iterator.
     * 
     * @param owner A collection.
     * @param descending Whether this iterator walks from the last value to the first one.
     */
    ArrayIterator(AbstractCollection owner, boolean descending) {
        this.owner = owner;
        this.descending = descending;
        this.index = descending ? owner.size() - 1 : 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext() {
        return descending ? 0 <= index : index < owner.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        last = index;
        return owner.get(descending ? index-- : index++);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove() {
        if (last < 0) {
            throw new IllegalStateException();
        }
        owner.removeAt(last);
        index = descending ? last - 1 : last;
        last = -1;
    }
}
