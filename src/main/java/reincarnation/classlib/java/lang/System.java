/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.lang;

import js.lang.NativeArray;
import js.lang.NativeDate;
import js.lang.NativeObject;
import js.lang.NativeString;
import reincarnation.classlib.java.io.PrintStream;

/**
 * The Java implementation of {@link java.lang.System} which is transpiled into JavaScript.
 */
public final class System {

    /** The standard output. */
    public static final PrintStream out = new PrintStream(false);

    /** The standard error. */
    public static final PrintStream err = new PrintStream(true);

    /**
     * Hide constructor.
     */
    private System() {
    }

    /**
     * Get the current time.
     * 
     * @return A time.
     */
    public static long currentTimeMillis() {
        return NativeDate.now();
    }

    /**
     * Get the current time in nanoseconds.
     * 
     * @return A time.
     */
    public static long nanoTime() {
        return NativeDate.nanoTime();
    }

    /**
     * Copy the array.
     * 
     * @param source A source array.
     * @param sourcePosition A source position.
     * @param destination A destination array.
     * @param destinationPosition A destination position.
     * @param length A length.
     */
    public static void arraycopy(Object source, int sourcePosition, Object destination, int destinationPosition, int length) {
        NativeArray.arraycopy(source, sourcePosition, destination, destinationPosition, length);
    }

    /**
     * Get the identity hash code.
     * 
     * @param value A value.
     * @return An identity hash code.
     */
    public static int identityHashCode(Object value) {
        return NativeObject.identityHashCode(value);
    }

    /**
     * Get the line separator.
     * 
     * @return A line separator.
     */
    public static String lineSeparator() {
        return NativeString.lineSeparator();
    }

    /**
     * Get the system property.
     * 
     * @param key A key.
     * @return A value or null.
     */
    public static String getProperty(String key) {
        return null;
    }

    /**
     * Get the system property.
     * 
     * @param key A key.
     * @param defaultValue A default value.
     * @return A value.
     */
    public static String getProperty(String key, String defaultValue) {
        return defaultValue;
    }

    /**
     * Get the environment variable.
     * 
     * @param name A name.
     * @return A value or null.
     */
    public static String getenv(String name) {
        return null;
    }

    /**
     * Run the garbage collector.
     */
    public static void gc() {
    }

    /**
     * Run the finalization.
     */
    public static void runFinalization() {
    }

    /**
     * Exit the program.
     * 
     * @param status A status.
     */
    public static void exit(int status) {
    }
}
