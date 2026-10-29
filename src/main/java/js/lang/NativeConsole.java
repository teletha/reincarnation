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

import java.io.PrintStream;

/**
 * The JavaScript {@code console}.
 * <p>
 * The Java implementation writes to the standard output or error so that the same test can run on
 * the JVM.
 * </p>
 */
@Native
public final class NativeConsole {

    /**
     * Hide constructor.
     */
    private NativeConsole() {
    }

    /**
     * Log the values.
     * 
     * @param values Values.
     */
    @Native("console.log(...($0))")
    public static void log(Object... values) {
        print(System.out, values);
    }

    /**
     * Log the values as an error.
     * 
     * @param values Values.
     */
    @Native("console.error(...($0))")
    public static void error(Object... values) {
        print(System.err, values);
    }

    /**
     * Log the values as a warning.
     * 
     * @param values Values.
     */
    @Native("console.warn(...($0))")
    public static void warn(Object... values) {
        print(System.err, values);
    }

    /**
     * Log the values as information.
     * 
     * @param values Values.
     */
    @Native("console.info(...($0))")
    public static void info(Object... values) {
        print(System.out, values);
    }

    /**
     * Log the values for debugging.
     * 
     * @param values Values.
     */
    @Native("console.debug(...($0))")
    public static void debug(Object... values) {
        print(System.out, values);
    }

    /**
     * Write the text without a newline.
     * 
     * @param error Whether the error stream is used.
     * @param text A text.
     */
    @Native("(typeof process !== \"undefined\" && process.stdout ? ($0 ? process.stderr : process.stdout).write($1) : ($0 ? console.error : console.log)($1))")
    public static void write(boolean error, String text) {
        (error ? System.err : System.out).print(text);
    }

    /**
     * Print the values.
     * 
     * @param stream An output stream.
     * @param values Values.
     */
    private static void print(PrintStream stream, Object[] values) {
        for (int i = 0; i < values.length; i++) {
            if (i != 0) {
                stream.print(' ');
            }
            stream.print(values[i]);
        }
        stream.println();
    }
}
