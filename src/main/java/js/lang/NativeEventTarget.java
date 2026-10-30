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
 * The JavaScript {@code EventTarget}.
 */
@Native
public final class NativeEventTarget {

    /**
     * Hide constructor.
     */
    private NativeEventTarget() {
    }

    /**
     * Add the event listener.
     * 
     * @param target A target.
     * @param type A type.
     * @param listener A listener.
     */
    @Native("$0.addEventListener($1, $2)")
    public static void addEventListener(Object target, String type, Object listener) {
    }

    /**
     * Add the event listener.
     * 
     * @param target A target.
     * @param type A type.
     * @param listener A listener.
     * @param options Options.
     */
    @Native("$0.addEventListener($1, $2, $3)")
    public static void addEventListener(Object target, String type, Object listener, boolean options) {
    }

    /**
     * Add the event listener.
     * 
     * @param target A target.
     * @param type A type.
     * @param listener A listener.
     * @param options Options.
     */
    @Native("$0.addEventListener($1, $2, $3)")
    public static void addEventListener(Object target, String type, Object listener, Object options) {
    }

    /**
     * Remove the event listener.
     * 
     * @param target A target.
     * @param type A type.
     * @param listener A listener.
     */
    @Native("$0.removeEventListener($1, $2)")
    public static void removeEventListener(Object target, String type, Object listener) {
    }

    /**
     * Remove the event listener.
     * 
     * @param target A target.
     * @param type A type.
     * @param listener A listener.
     * @param options Options.
     */
    @Native("$0.removeEventListener($1, $2, $3)")
    public static void removeEventListener(Object target, String type, Object listener, boolean options) {
    }

    /**
     * Dispatch the event.
     * 
     * @param target A target.
     * @param event An event.
     * @return A result.
     */
    @Native("$0.dispatchEvent($1)")
    public static boolean dispatchEvent(Object target, NativeEvent event) {
        return false;
    }
}
