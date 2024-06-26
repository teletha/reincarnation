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

class TryCatchFinallyTest extends CodeVerifier {

    @CrossDecompilerTest
    void after() {
        verify(new TestCode.IntParam() {
            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = MaybeThrow.error(value);
                } catch (Error e) {
                    value = value + 1;
                } finally {
                    value = value + 2;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void immediateReturnInTryAndCatch() {
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

    @CrossDecompilerTest
    void immediateReturnInTryWithAfter() {
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

    @CrossDecompilerTest
    void immediateReturnInCatchWithAfter() {
        verify(new TestCode.IntParam() {

            private int counter = 0;

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                return counter + calc(value);
            }

            private int calc(int value) {
                try {
                    value = MaybeThrow.error(value);
                } catch (Error e) {
                    return 30;
                } finally {
                    counter = counter + 2;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void tryCatchInTry() {
        verify(new TestCode.IntParam() {

            private int counter = 0;

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                return count(value) + counter;
            }

            private int count(int value) {
                try {
                    try {
                        value = MaybeThrow.exception(value + 1);
                    } catch (Exception e) {
                        value = MaybeThrow.error(value + 2);
                    }
                } catch (Error e) {
                    value += 10;
                } finally {
                    counter--;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void tryCatchInCatch() {
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

    @CrossDecompilerTest
    void tryCatchInFinally() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = MaybeThrow.error(value + 1);
                } catch (Error e) {
                    value += 2;
                } finally {
                    try {
                        value = MaybeThrow.error(value + 3);
                    } catch (Error e) {
                        value += 4;
                    }
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void tryCatchNodesInFinally() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = MaybeThrow.error(value + 1);
                } catch (Error e) {
                    value += 2;
                } finally {
                    try {
                        value = MaybeThrow.error(value + 3);
                    } catch (Error e) {
                        value += 4;
                        value *= 5;
                    }
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void rethrow() {
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

    @CrossDecompilerTest
    void nodesInFinally() {
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

    @CrossDecompilerTest
    void insideSwitch() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                case 0, 1, 2:
                    try {
                        param = MaybeThrow.error(param);
                    } catch (Error e) {
                        param += 1;
                    } finally {
                        param += 2;
                    }
                    return param + 5;

                default:
                    return param;
                }
            }
        });
    }

    @CrossDecompilerTest
    void insideSwitchWithBreak() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                case 0, 1:
                    try {
                        param = MaybeThrow.error(param);
                    } catch (Error e) {
                        param += 1;
                    } finally {
                        param += 2;
                    }
                    break;

                default:
                    param += 3;
                    break;
                }
                return param + 10;
            }
        });
    }
}