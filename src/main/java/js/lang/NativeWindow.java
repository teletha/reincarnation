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
 * <p>
 * The window properties are exposed as fields so that the decompiled Java code reads them as
 * {@code window.document} rather than {@code window.document()}.
 * </p>
 */
@Native
public final class NativeWindow {

    /** The document. */
    @Native("document")
    public static final NativeDocument document = new NativeDocument();

    /** The local storage. */
    @Native("window.localStorage ?? null")
    public static final NativeStorage localStorage = NativeStorage.local();

    /** The session storage. */
    @Native("window.sessionStorage ?? null")
    public static final NativeStorage sessionStorage = NativeStorage.session();

    /** The inner width. */
    @Native("innerWidth")
    public static final int innerWidth = zero();

    /** The inner height. */
    @Native("innerHeight")
    public static final int innerHeight = zero();

    /** The device pixel ratio. */
    @Native("devicePixelRatio")
    public static final double devicePixelRatio = one();

    /** The scroll X. */
    @Native("scrollX")
    public static final double scrollX = zero();

    /** The scroll Y. */
    @Native("scrollY")
    public static final double scrollY = zero();

    /**
     * Get the zero value. The method call prevents javac from inlining the final field, which
     * would bypass the native accessor.
     * 
     * @return Zero.
     */
    private static int zero() {
        return 0;
    }

    /**
     * Get the one value.
     * 
     * @return One.
     */
    private static double one() {
        return 1;
    }

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
    @Native("window")
    public static Object global() {
        return new Object();
    }

    /**
     * Show the alert.
     * 
     * @param message A message.
     */
    @Native("window.alert($0)")
    public static void alert(String message) {
    }

    /**
     * Show the confirmation.
     * 
     * @param message A message.
     * @return A result.
     */
    @Native("window.confirm($0)")
    public static boolean confirm(String message) {
        return false;
    }

    /**
     * Show the prompt.
     * 
     * @param message A message.
     * @return A value or null.
     */
    @Native("window.prompt($0)")
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
    @Native("window.open($0, $1, $2)")
    public static Object open(String url, String target, String features) {
        return null;
    }

    /**
     * Close the window.
     */
    @Native("window.close()")
    public static void close() {
    }

    /**
     * Schedule the callback.
     * 
     * @param callback A callback.
     * @param delay A delay in milliseconds.
     * @return A timer id.
     */
    @Native("window.setTimeout($0, Number($1))")
    public static int setTimeout(Runnable callback, long delay) {
        return 0;
    }

    /**
     * Cancel the timer.
     * 
     * @param id A timer id.
     */
    @Native("window.clearTimeout($0)")
    public static void clearTimeout(int id) {
    }
}
