/*
 * Copyright (C) 2024 The REINCARNATION Development Team
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

class TryFinallyTest extends CodeVerifier {

    @CrossDecompilerTest
    void normal() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                try {
                    return param + 1;
                } finally {
                    param *= 2;
                }
            }
        });
    }

    @CrossDecompilerTest
    void normalAfter() {
        verify(new TestCode.IntParam() {

            @Override
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

    @CrossDecompilerTest
    void nodesInFinally() {
        verify(new TestCode.IntParam() {

            private int field;

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                return count(param) + field;
            }

            private int count(int param) {
                try {
                    return param++;
                } finally {
                    if (param % 2 == 0) {
                        field += 20;
                    } else {
                        field -= 30;
                    }
                }
            }
        });
    }

    @CrossDecompilerTest
    void nodesInFinallyAfter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                try {
                    param++;
                } finally {
                    if (param % 2 == 0) {
                        param *= 2;
                    } else {
                        param *= 5;
                    }
                }
                return param;
            }
        });
    }

    @CrossDecompilerTest
    void nodesInTryAndFinallyAfter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                try {
                    if (param % 2 == 0) {
                        param += 1;
                    } else {
                        param += 2;
                    }
                } finally {
                    if (param % 3 == 0) {
                        param *= 3;
                    } else {
                        param *= 4;
                    }
                }
                return param;
            }
        });
    }

    @CrossDecompilerTest
    void halfNodesInTryAndFinallyAfter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                try {
                    if (param % 2 == 0) {
                        return -1;
                    } else {
                        param += 2;
                    }
                } finally {
                    if (param % 3 == 0) {
                        param *= 3;
                    } else {
                        return -4;
                    }
                }
                return param;
            }
        });
    }

    @CrossDecompilerTest
    void multipleReturnInTry() {
        verify(new TestCode.IntParam() {

            private int field = 0;

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                return count(param) + field;
            }

            private int count(int param) {
                try {
                    if (param % 2 == 0) {
                        return param - 20;
                    }
                    param += 1;

                    if (param % 3 == 0) {
                        return param - 30;
                    }

                    param += 2;

                    if (param % 5 == 0) {
                        return param - 50;
                    }
                } finally {
                    field += 10;
                }
                return param;
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

            private int error(int param) {
                try {
                    return MaybeThrow.error(param);
                } finally {
                    param++;
                }
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
    void tryFinallyInTryAndFinally() {
        verify(new TestCode.IntParam() {

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

    @CrossDecompilerTest
    void tryFinallyWithHeadNodeInTryAndFinally() {
        verify(new TestCode.IntParam() {

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

    @CrossDecompilerTest
    void tryFinallyWithTailNodeInTryAndFinally() {
        verify(new TestCode.IntParam() {

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

    @CrossDecompilerTest
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