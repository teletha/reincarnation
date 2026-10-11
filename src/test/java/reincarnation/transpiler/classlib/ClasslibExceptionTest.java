/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.classlib;

import org.junit.jupiter.api.Test;

/**
 * Verify the TeaVM class library based JavaScript program for the exception message, the assert
 * statement and the synchronized block.
 */
class ClasslibExceptionTest extends ClasslibProgramVerifier {

    @Test
    void exceptionMessage() throws Exception {
        verify(ExceptionMessage.class, "run");
    }

    static class ExceptionMessage {
        static String run() {
            try {
                throw new IllegalStateException("boom");
            } catch (IllegalStateException e) {
                return e.getMessage();
            }
        }
    }

    @Test
    void assertPass() throws Exception {
        verify(AssertPass.class, "run");
    }

    static class AssertPass {
        static int run() {
            int value = 5;
            assert value == 5;
            return value;
        }
    }

    @Test
    void assertFail() throws Exception {
        verify(AssertFail.class, "run");
    }

    static class AssertFail {
        static String run() {
            int value = 5;
            try {
                assert value == 6;
                return "no";
            } catch (AssertionError e) {
                return "asserted";
            }
        }
    }

    @Test
    void synchronizedBlock() throws Exception {
        verify(SynchronizedBlock.class, "run");
    }

    static class SynchronizedBlock {
        static int run() {
            Object lock = new Object();
            int value = 0;
            synchronized (lock) {
                value = 7;
            }
            return value;
        }
    }

    @Test
    void synchronizedMethod() throws Exception {
        verify(SynchronizedMethod.class, "run");
    }

    static class SynchronizedMethod {
        static synchronized int run() {
            return 9;
        }
    }
}
