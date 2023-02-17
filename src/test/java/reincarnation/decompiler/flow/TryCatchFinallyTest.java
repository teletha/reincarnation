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

import reincarnation.CodeVerifier;
import reincarnation.Debuggable;
import reincarnation.TestCode;

@Debuggable
class TryCatchFinallyTest extends CodeVerifier {

    @Test
    void TryCatchFinallyAfter() {
        verify(new TestCode.IntParam() {
            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = MaybeThrow.error(value);
                } catch (Error e) {
                    value = value + 1;
                } finally {
                    value = value + 2;
                    value = value + 4;
                }
                return value;
            }
        });
    }

    @Test
    void TryCatchFinallyReturnImmediately() {
        verify(new TestCode.IntParam() {

            private int counter = 0;

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                return counter + calc(value);
            }

            private int calc(int value) {
                try {
                    return MaybeThrow.error(value);
                } catch (Error e) {
                    return 10;
                } finally {
                    counter = counter + 2;
                }
            }
        });
    }

    @Test
    @Disabled
    void TryCatchFinallyReturnImmediatelyWithAfter() {
        verify(new TestCode.IntParam() {

            private int counter = 0;

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                return counter + calc(value);
            }

            private int calc(int value) {
                try {
                    return MaybeThrow.error(value);
                } catch (Error e) {
                    value = value + 3;
                } finally {
                    counter = counter + 2;
                }
                return value + 1;
            }
        });
    }

    @Test
    @Disabled
    void TryCatchFinallyAfterNestAtFinally() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    if (value == 0) {
                        throw new Error();
                    }
                    value += 1;
                } catch (Error e) {
                    value += 2;
                } finally {
                    value += 3;

                    try {
                        if (value % 2 == 0) {
                            throw new Error();
                        }
                        value += 4;
                    } catch (Error e) {
                        value += 5;
                    } finally {
                        value += 6;
                    }
                    value += 7;
                }
                return value;
            }
        });
    }

    @Test
    void TryCatchThrowFinally() {
        verify(new TestCode.IntParam() {

            private int counter = 0;

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                return count(value) + counter;
            }

            private int count(int value) {
                try {
                    return MaybeThrow.error(value);
                } catch (Error e) {
                    throw e;
                } finally {
                    counter--;
                }
            }
        });
    }

    @Test
    void TryCatchReturnFinally() {
        verify(new TestCode.IntParam() {

            private int counter = 0;

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                return count(value) + counter;
            }

            private int count(int value) {
                try {
                    return MaybeThrow.error(value);
                } catch (Error e) {
                    try {
                        value = MaybeThrow.error(value + 1);
                    } catch (Error x) {
                        value += 5;
                    }
                } finally {
                    counter--;
                }
                return value;
            }
        });
    }

    @Test
    void TryCatchFinallyNodes() {
        verify(new TestCode.IntParam() {

            private int counter = 0;

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                return count(value) + counter;
            }

            private int count(int value) {
                try {
                    if (value % 2 == 0) {
                        throw new Error();
                    }
                    return counter += 1;
                } catch (Error e) {
                    return counter += 2;
                } finally {
                    if (counter % 3 == 0) {
                        counter += 3;
                    } else {
                        counter += 4;
                    }
                }
            }
        });
    }
}
