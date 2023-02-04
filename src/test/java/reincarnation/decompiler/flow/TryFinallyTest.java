/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.flow;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import reincarnation.CodeVerifier;
import reincarnation.Debuggable;
import reincarnation.TestCode;

@Execution(ExecutionMode.SAME_THREAD)
class TryFinallyTest extends CodeVerifier {

    @Test
    void normal() {
        verify(new TestCode.IntParam() {

            @Override
            @Debuggable
            public int run(@Param(from = 0, to = 10) int param) {
                try {
                    return param + 1;
                } finally {
                    param *= 2;
                }
            }
        });
    }

    @Test
    void normalAfter() {
        verify(new TestCode.IntParam() {

            @Override
            @Debuggable
            public int run(@Param(from = 0, to = 10) int param) {
                try {
                    param += 1;
                } finally {
                    param *= 2;
                }
                return param;
            }
        });
    }

    @Test
    void emptyTry() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                try {
                    // empty
                } finally {
                    param *= 2;
                }
                return param;
            }
        });
    }

    @Test
    void emptyFinally() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                try {
                    param += 2;
                } finally {
                    // empty
                }
                return param;
            }
        });
    }

    @Test
    void returnInTry() {
        verify(new TestCode.IntParam() {

            private int field = 1;

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                try {
                    field *= error(param);
                } catch (Error e) {
                    field -= 10;
                }
                return field;
            }

            @Debuggable
            private int error(int param) {
                try {
                    return MaybeThrow.error(param);
                } finally {
                    param++;
                }
            }
        });
    }

    @Test
    void returnInAfter() {
        verify(new TestCode.IntParam() {

            private int field = 1;

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                try {
                    field *= error(param);
                } catch (Error e) {
                    field -= 10;
                }
                return field;
            }

            @Debuggable
            private int error(int param) {
                try {
                    param = MaybeThrow.error(param);
                } finally {
                    param++;
                }
                return param;
            }
        });
    }

    @Test
    void tryFinallyInTryAndFinally() {
        verify(new TestCode.IntParam() {

            @Debuggable
            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    try {
                        value += 1;
                    } finally {
                        value *= 2;
                    }
                } finally {
                    try {
                        value += 3;
                    } finally {
                        value *= 4;
                    }
                }
                return value;
            }
        });
    }

    @Test
    void tryFinallyWithHeadNodeInTryAndFinally() {
        verify(new TestCode.IntParam() {

            @Debuggable
            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value -= 1;

                    try {
                        value += 2;
                    } finally {
                        value *= 3;
                    }
                } finally {
                    value -= 4;

                    try {
                        value += 5;
                    } finally {
                        value *= 6;
                    }
                }
                return value;
            }
        });
    }

    @Test
    void tryFinallyWithTailNodeInTryAndFinally() {
        verify(new TestCode.IntParam() {

            @Debuggable
            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    try {
                        value += 1;
                    } finally {
                        value *= 2;
                    }
                    value -= 3;
                } finally {
                    try {
                        value += 4;
                    } finally {
                        value *= 5;
                    }
                    value -= 6;
                }
                return value;
            }
        });
    }

    @Test
    @Disabled
    void TryFinallyAfterNest2() {
        verify(new TestCode.IntParam() {

            private int id = 0;

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                count(value);
                return id;
            }

            private int count(int value) {
                try {
                    try {
                        if (value % 2 == 0) {
                            throw new Exception();
                        }

                        id += value;
                    } catch (Exception e) {
                        return id += value * 2;
                    } finally {
                        id += 3;
                    }

                    id += 4;
                } finally {
                    id += 5;
                }
                return 0;
            }
        });
    }
}
