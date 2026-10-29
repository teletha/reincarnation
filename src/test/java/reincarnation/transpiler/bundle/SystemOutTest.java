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

import reincarnation.transpiler.BundleVerifier;

/**
 * Test the standard input and output support.
 */
class SystemOutTest extends BundleVerifier {

    @Test
    void print() throws Exception {
        verifyOutput(PrintEntry.class, "run", "ab\nc3\n1.5\n");
    }

    @Test
    void printf() throws Exception {
        verifyOutput(PrintfEntry.class, "run", "7-x8-y");
    }

    @Test
    void charArray() throws Exception {
        verifyOutput(CharArrayEntry.class, "run", "hi\n");
    }

    @Test
    void error() throws Exception {
        verifyErrorOutput(ErrorEntry.class, "run", "error\n");
    }

    @Test
    void write() throws Exception {
        verifyOutput(WriteEntry.class, "run", "AB");
    }

    /** The print entry. */
    static class PrintEntry {

        static void run() {
            System.out.print("a");
            System.out.println("b");
            System.out.print('c');
            System.out.println(3);
            System.out.println(1.5);
        }
    }

    /** The formatted print entry. */
    static class PrintfEntry {

        static void run() {
            System.out.printf("%d-%s", 7, "x");
            System.out.printf("%d-%s", 8, "y");
        }
    }

    /** The char array entry. */
    static class CharArrayEntry {

        static void run() {
            char[] chars = {'h', 'i'};
            System.out.println(chars);
        }
    }

    /** The error stream entry. */
    static class ErrorEntry {

        static void run() {
            System.err.println("error");
        }
    }

    /** The write entry. */
    static class WriteEntry {

        static void run() {
            System.out.write('A');
            System.out.write('B');
            System.out.flush();
        }
    }
}
