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

import java.util.Objects;

/**
 * The Java implementation of {@link java.util.AbstractSet} which is transpiled into JavaScript.
 */
public abstract class AbstractSet<E> extends AbstractCollection<E> implements Set<E> {

    /**
     * Create an empty set.
     */
    protected AbstractSet() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(Object value) {
        if (contains(value)) {
            return false;
        }
        return super.add(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Set otherSet) {
            if (size() != otherSet.size()) {
                return false;
            }
            for (int i = 0; i < size(); i++) {
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
        for (int i = 0; i < size(); i++) {
            hash = (hash + Objects.hashCode(elements[i])) | 0;
        }
        return hash;
    }
}
