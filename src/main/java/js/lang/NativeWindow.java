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
 * The JavaScript {@code Window}.
 */
@Native
public final class NativeWindow {

    /**
     * Hide constructor.
     */
    private NativeWindow() {
    }

    /**
     * Get the global object.
     * 
     * @return A global object.
     */
    @Native("typeof window === 'undefined' ? globalThis : window")
    public static Object global() {
        return null;
    }

    /**
     * Get the document.
     * 
     * @return A document or null.
     */
    @Native("typeof document === 'undefined' ? null : document")
    public static NativeDocument document() {
        return null;
    }

    /**
     * Get the local storage.
     * 
     * @return A storage or null.
     */
    @Native("typeof localStorage === 'undefined' ? null : localStorage")
    public static NativeStorage localStorage() {
        return NativeStorage.local();
    }

    /**
     * Get the session storage.
     * 
     * @return A storage or null.
     */
    @Native("typeof sessionStorage === 'undefined' ? null : sessionStorage")
    public static NativeStorage sessionStorage() {
        return NativeStorage.session();
    }

    /**
     * Show the alert.
     * 
     * @param message A message.
     */
    @Native("typeof window === 'undefined' || typeof window.alert !== 'function' ? console.log($0) : window.alert($0)")
    public static void alert(String message) {
    }

    /**
     * Show the confirmation.
     * 
     * @param message A message.
     * @return A result.
     */
    @Native("typeof window === 'undefined' || typeof window.confirm !== 'function' ? false : window.confirm($0)")
    public static boolean confirm(String message) {
        return false;
    }

    /**
     * Show the prompt.
     * 
     * @param message A message.
     * @return A value or null.
     */
    @Native("typeof window === 'undefined' || typeof window.prompt !== 'function' ? null : window.prompt($0)")
    public static String prompt(String message) {
        return null;
    }

    /**
     * Open the window.
     * 
     * @param url A URL.
     * @param target A target.
     * @param features Features.
     * @return A window or null.
     */
    @Native("typeof window === 'undefined' || typeof window.open !== 'function' ? null : window.open($0, $1, $2)")
    public static Object open(String url, String target, String features) {
        return null;
    }

    /**
     * Close the window.
     */
    @Native("typeof window === 'undefined' || typeof window.close !== 'function' ? undefined : window.close()")
    public static void close() {
    }

    /**
     * Get the inner width.
     * 
     * @return A width.
     */
    @Native("typeof window === 'undefined' ? 0 : window.innerWidth")
    public static int innerWidth() {
        return 0;
    }

    /**
     * Get the inner height.
     * 
     * @return A height.
     */
    @Native("typeof window === 'undefined' ? 0 : window.innerHeight")
    public static int innerHeight() {
        return 0;
    }

    /**
     * Get the device pixel ratio.
     * 
     * @return A ratio.
     */
    @Native("typeof window === 'undefined' ? 1 : window.devicePixelRatio")
    public static double devicePixelRatio() {
        return 1;
    }

    /**
     * Get the scroll X.
     * 
     * @return A scroll X.
     */
    @Native("typeof window === 'undefined' ? 0 : window.scrollX")
    public static double scrollX() {
        return 0;
    }

    /**
     * Get the scroll Y.
     * 
     * @return A scroll Y.
     */
    @Native("typeof window === 'undefined' ? 0 : window.scrollY")
    public static double scrollY() {
        return 0;
    }

    /**
     * Schedule the callback.
     * 
     * @param callback A callback.
     * @param delay A delay in milliseconds.
     * @return A timer id.
     */
    @Native("typeof setTimeout === 'function' ? setTimeout($0, Number($1)) : 0")
    public static int setTimeout(Runnable callback, long delay) {
        return 0;
    }

    /**
     * Cancel the timer.
     * 
     * @param id A timer id.
     */
    @Native("typeof clearTimeout === 'function' ? clearTimeout($0) : undefined")
    public static void clearTimeout(int id) {
    }
}
