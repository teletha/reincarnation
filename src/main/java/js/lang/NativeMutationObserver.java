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
 * The JavaScript {@code MutationObserver}.
 */
@Native
public final class NativeMutationObserver {

    /** The callback. */
    private final BiConsumer<NativeArray, NativeMutationObserver> callback;

    /** The observed target. */
    private Object target;

    /** The observed options. */
    private NativeMutationObserverInit options;

    /**
     * Create the observer.
     * 
     * @param callback A callback.
     */
    @Native("typeof MutationObserver === 'undefined' ? (() => ({observe: function(target, options) {}, disconnect: function() {}, takeRecords: function() { return []; }}))() : new MutationObserver($0)")
    public NativeMutationObserver(BiConsumer<NativeArray, NativeMutationObserver> callback) {
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
     * Observe the target with the options.
     * 
     * @param target A target.
     * @param options Options.
     */
    @Native("$this.observe($0, $1)")
    public void observe(Object target, NativeMutationObserverInit options) {
        this.target = target;
        this.options = options;
    }

    /**
     * Disconnect the observer.
     */
    @Native("$this.disconnect()")
    public void disconnect() {
        this.target = null;
        this.options = null;
    }

    /**
     * Take the records.
     * 
     * @return Records.
     */
    @Native("$this.takeRecords()")
    public NativeArray takeRecords() {
        return new NativeArray();
    }
}
