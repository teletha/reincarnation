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

import java.util.ArrayList;
import java.util.List;

/**
 * The JavaScript {@code NodeList} or {@code HTMLCollection}.
 */
@Native
public final class NativeNodeList {

    /** The JVM representation. */
    private final List<Object> values;

    /**
     * Create the empty node list.
     */
    @Native("[]")
    public NativeNodeList() {
        this.values = new ArrayList();
    }

    /**
     * Create the node list.
     * 
     * @param values Values.
     */
    @Native("$0")
    public NativeNodeList(Object[] values) {
        this.values = new ArrayList();
        for (Object value : values) {
            this.values.add(value);
        }
    }

    /**
     * Get the length.
     * 
     * @return A length.
     */
    @Native("$this.length")
    public int length() {
        return values.size();
    }

    /**
     * Get the item.
     * 
     * @param index An index.
     * @return An item or null.
     */
    @Native("typeof $this.item === 'function' ? $this.item($0) : $this[$0]")
    public Object item(int index) {
        return index < 0 || values.size() <= index ? null : values.get(index);
    }

    /**
     * Get the item.
     * 
     * @param index An index.
     * @return An item or null.
     */
    @Native("$this[$0]")
    public Object get(int index) {
        return item(index);
    }

    /**
     * Get the array.
     * 
     * @return An array.
     */
    @Native("Array.from($this)")
    public NativeArray toArray() {
        NativeArray array = new NativeArray();
        for (Object value : values) {
            array.push(value);
        }
        return array;
    }
}
