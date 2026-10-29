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
 * The JavaScript date and time functions.
 */
@Native
public final class NativeDate {

    /**
     * Hide constructor.
     */
    private NativeDate() {
    }

    /**
     * Get the current time in milliseconds.
     * 
     * @return A time.
     */
    @Native("$rt_l(Date.now())")
    public static long now() {
        return java.lang.System.currentTimeMillis();
    }

    /**
     * Get the current time in nanoseconds.
     * 
     * @return A time.
     */
    @Native("$rt_l($rt_nanoTime())")
    public static long nanoTime() {
        return java.lang.System.nanoTime();
    }
}
