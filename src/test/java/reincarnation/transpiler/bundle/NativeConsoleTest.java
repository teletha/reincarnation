/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.bundle;

import org.junit.jupiter.api.Test;

import js.lang.NativeConsole;
import reincarnation.transpiler.BundleVerifier;

/**
 * Test the JavaScript {@code console} native bridge.
 */
class NativeConsoleTest extends BundleVerifier {

    @Test
    void log() throws Exception {
        verifyOutput(LogEntry.class, "run", "a 1\n");
    }

    @Test
    void error() throws Exception {
        verifyErrorOutput(ErrorEntry.class, "run", "e\n");
    }

    @Test
    void warn() throws Exception {
        verifyErrorOutput(WarnEntry.class, "run", "w\n");
    }

    @Test
    void writeOut() throws Exception {
        verifyOutput(WriteOutEntry.class, "run", "x");
    }

    @Test
    void writeErr() throws Exception {
        verifyErrorOutput(WriteErrEntry.class, "run", "y");
    }

    /** The log entry. */
    static class LogEntry {

        static void run() {
            NativeConsole.log("a", 1);
        }
    }

    /** The error entry. */
    static class ErrorEntry {

        static void run() {
            NativeConsole.error("e");
        }
    }

    /** The warn entry. */
    static class WarnEntry {

        static void run() {
            NativeConsole.warn("w");
        }
    }

    /** The standard output entry. */
    static class WriteOutEntry {

        static void run() {
            NativeConsole.write(false, "x");
        }
    }

    /** The standard error entry. */
    static class WriteErrEntry {

        static void run() {
            NativeConsole.write(true, "y");
        }
    }
}
