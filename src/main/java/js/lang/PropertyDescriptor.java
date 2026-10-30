/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package js.lang;

/**
 * The JavaScript property descriptor.
 * 
 * @param <T> A value type.
 */
public final class PropertyDescriptor<T> {

    /** The value. */
    public final T value;

    /** Whether the property is writable. */
    public final boolean writable;

    /** Whether the property is enumerable. */
    public final boolean enumerable;

    /** Whether the property is configurable. */
    public final boolean configurable;

    /**
     * Create the descriptor.
     * 
     * @param value A value.
     */
    public PropertyDescriptor(T value) {
        this(value, true, true, true);
    }

    /**
     * Create the descriptor.
     * 
     * @param value A value.
     * @param writable Whether the property is writable.
     * @param enumerable Whether the property is enumerable.
     * @param configurable Whether the property is configurable.
     */
    public PropertyDescriptor(T value, boolean writable, boolean enumerable, boolean configurable) {
        this.value = value;
        this.writable = writable;
        this.enumerable = enumerable;
        this.configurable = configurable;
    }
}
