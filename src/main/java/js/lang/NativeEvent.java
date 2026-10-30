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
 * The JavaScript {@code Event}.
 */
@Native
public final class NativeEvent {

    /** The type. */
    private String type;

    /** The target. */
    private Object target;

    /** The current target. */
    private Object currentTarget;

    /** Whether the event bubbles. */
    private boolean bubbles;

    /** Whether the event is cancelable. */
    private boolean cancelable;

    /** Whether the default is prevented. */
    private boolean defaultPrevented;

    /** The event phase. */
    private int eventPhase;

    /** The time stamp. */
    private double timeStamp;

    /** Whether the event is trusted. */
    private boolean trusted;

    /**
     * Create the event.
     * 
     * @param type A type.
     */
    @Native("typeof Event === 'undefined' ? ({type: $0, target: null, currentTarget: null, bubbles: false, cancelable: false, defaultPrevented: false, eventPhase: 0, timeStamp: 0, isTrusted: false, returnValue: true, preventDefault: function() {}, stopPropagation: function() {}, stopImmediatePropagation: function() {}, composedPath: function() { return []; }}) : new Event($0)")
    public NativeEvent(String type) {
        this.type = type;
    }

    /**
     * Create the event.
     * 
     * @param type A type.
     * @param options Options.
     */
    @Native("typeof Event === 'undefined' ? ({type: $0, target: null, currentTarget: null, bubbles: false, cancelable: false, defaultPrevented: false, eventPhase: 0, timeStamp: 0, isTrusted: false, returnValue: true, preventDefault: function() {}, stopPropagation: function() {}, stopImmediatePropagation: function() {}, composedPath: function() { return []; }}) : new Event($0, $1)")
    public NativeEvent(String type, Object options) {
        this.type = type;
    }

    /**
     * Get the type.
     * 
     * @return A type.
     */
    @Native("$this.type")
    public String type() {
        return type;
    }

    /**
     * Get the target.
     * 
     * @return A target.
     */
    @Native("$this.target")
    public Object target() {
        return target;
    }

    /**
     * Get the current target.
     * 
     * @return A current target.
     */
    @Native("$this.currentTarget")
    public Object currentTarget() {
        return currentTarget;
    }

    /**
     * Get the source element.
     * 
     * @return A source element.
     */
    @Native("$this.srcElement")
    public Object srcElement() {
        return currentTarget;
    }

    /**
     * Check whether the event bubbles.
     * 
     * @return A result.
     */
    @Native("$this.bubbles")
    public boolean bubbles() {
        return bubbles;
    }

    /**
     * Check whether the event is cancelable.
     * 
     * @return A result.
     */
    @Native("$this.cancelable")
    public boolean cancelable() {
        return cancelable;
    }

    /**
     * Check whether the default is prevented.
     * 
     * @return A result.
     */
    @Native("$this.defaultPrevented")
    public boolean defaultPrevented() {
        return defaultPrevented;
    }

    /**
     * Get the event phase.
     * 
     * @return An event phase.
     */
    @Native("$this.eventPhase")
    public int eventPhase() {
        return eventPhase;
    }

    /**
     * Get the time stamp.
     * 
     * @return A time stamp.
     */
    @Native("$this.timeStamp")
    public double timeStamp() {
        return timeStamp;
    }

    /**
     * Check whether the event is trusted.
     * 
     * @return A result.
     */
    @Native("$this.isTrusted")
    public boolean isTrusted() {
        return trusted;
    }

    /**
     * Get the return value.
     * 
     * @return A return value.
     */
    @Native("$this.returnValue")
    public boolean returnValue() {
        return !defaultPrevented;
    }

    /**
     * Set the return value.
     * 
     * @param value A value.
     */
    @Native("$this.returnValue = $0")
    public void returnValue(boolean value) {
        this.defaultPrevented = !value;
    }

    /**
     * Prevent the default behavior.
     */
    @Native("$this.preventDefault()")
    public void preventDefault() {
        if (cancelable) {
            defaultPrevented = true;
        }
    }

    /**
     * Stop the propagation.
     */
    @Native("$this.stopPropagation()")
    public void stopPropagation() {
    }

    /**
     * Stop the immediate propagation.
     */
    @Native("$this.stopImmediatePropagation()")
    public void stopImmediatePropagation() {
    }

    /**
     * Get the composed path.
     * 
     * @return A path.
     */
    @Native("$this.composedPath()")
    public NativeArray composedPath() {
        return new NativeArray();
    }
}
