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

import java.util.function.BiConsumer;

/**
 * The JavaScript {@code ResizeObserver}.
 */
@Native
public final class NativeResizeObserver {

    /** The callback. */
    private final BiConsumer<NativeArray, NativeResizeObserver> callback;

    /** The observed target. */
    private Object target;

    /** The observed box. */
    private String box;

    /**
     * Create the observer.
     * 
     * @param callback A callback.
     */
    @Native("typeof ResizeObserver === 'undefined' ? (() => ({observe: function(target, options) {}, unobserve: function(target) {}, disconnect: function() {}}))() : new ResizeObserver($0)")
    public NativeResizeObserver(BiConsumer<NativeArray, NativeResizeObserver> callback) {
        this.callback = callback;
    }

    /**
     * Observe the target.
     * 
     * @param target A target.
     */
    @Native("$this.observe($0)")
    public void observe(Object target) {
        this.target = target;
    }

    /**
     * Observe the target with the box.
     * 
     * @param target A target.
     * @param box A box.
     */
    @Native("$this.observe($0, {box: $1})")
    public void observe(Object target, String box) {
        this.target = target;
        this.box = box;
    }

    /**
     * Unobserve the target.
     * 
     * @param target A target.
     */
    @Native("$this.unobserve($0)")
    public void unobserve(Object target) {
        if (this.target == target) {
            this.target = null;
        }
    }

    /**
     * Disconnect the observer.
     */
    @Native("$this.disconnect()")
    public void disconnect() {
        this.target = null;
    }
}
