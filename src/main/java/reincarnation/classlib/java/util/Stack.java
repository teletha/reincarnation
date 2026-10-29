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

/**
 * The Java implementation of {@link java.util.Stack}.
 */
public final class Stack extends List {

    /**
     * Create an empty stack.
     */
    public Stack() {
        super();
    }

    /**
     * Push the value.
     * 
     * @param value A value.
     * @return A value.
     */
    public Object push(Object value) {
        add(value);
        return value;
    }

    /**
     * Pop the value.
     * 
     * @return A value.
     */
    public Object pop() {
        return remove(size() - 1);
    }

    /**
     * Peek the value.
     * 
     * @return A value.
     */
    public Object peek() {
        return get(size() - 1);
    }

    /**
     * Check whether this stack is empty.
     * 
     * @return A result.
     */
    public boolean empty() {
        return isEmpty();
    }
}
