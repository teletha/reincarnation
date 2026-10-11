/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.flow;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class SynchronizedTest extends CodeVerifier {

    @CrossDecompilerTest
    void block() {
        verify(new TestCode.IntParam() {

            private final java.lang.Object lock = new java.lang.Object();

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                synchronized (lock) { // LLV
                    param += 1;
                }
                return param;
            }
        });
    }

    @CrossDecompilerTest
    void nested() {
        verify(new TestCode.IntParam() {

            private final java.lang.Object lock = new java.lang.Object();

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                synchronized (this) { // LLV
                    synchronized (lock) { // LLV
                        param += 1;
                    }
                }
                return param;
            }
        });
    }

    @CrossDecompilerTest
    void returnInside() {
        verify(new TestCode.IntParam() {

            private final java.lang.Object lock = new java.lang.Object();

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                synchronized (lock) { // LLV
                    return param + 1;
                }
            }
        });
    }

    @CrossDecompilerTest
    void afterBlock() {
        verify(new TestCode.IntParam() {

            private final java.lang.Object lock = new java.lang.Object();

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                synchronized (lock) { // LLV
                    param += 1;
                }
                return param * 2;
            }
        });
    }

    @CrossDecompilerTest
    void localLock() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                java.lang.Object lock = new java.lang.Object();
                synchronized (lock) { // LLV
                    param += 1;
                }
                return param;
            }
        });
    }
}
