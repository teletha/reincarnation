/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.flow;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.TestCode.Param;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the try-catch-finally statement.
 * 
 * @version 2026/10/11 12:00:00
 */
class TryCatchFinallyTest extends JavascriptCodeVerifier {

    @Test
    void after() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = check(value);
                } catch (Error e) {
                    value = value + 1;
                } finally {
                    value = value + 2;
                }
                return value;
            }

            private int check(int value) {
                if (value % 2 == 0) {
                    throw new Error();
                }
                return value;
            }
        });
    }

    @Test
    void finallyOnly() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = value + 1;
                } finally {
                    value = value + 2;
                }
                return value;
            }
        });
    }

    @Test
    void tryCatchInCatch() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = check(value);
                } catch (Error e) {
                    try {
                        value = check(value + 1);
                    } catch (Error x) {
                        value += 5;
                    }
                } finally {
                    value += 2;
                }
                return value;
            }

            private int check(int value) {
                if (value % 2 == 0) {
                    throw new Error();
                }
                return value;
            }
        });
    }

    @Test
    void tryCatchInFinally() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = check(value + 1);
                } catch (Error e) {
                    value += 2;
                } finally {
                    try {
                        value = check(value + 3);
                    } catch (Error e) {
                        value += 4;
                    }
                }
                return value;
            }

            private int check(int value) {
                if (value % 2 == 0) {
                    throw new Error();
                }
                return value;
            }
        });
    }
}