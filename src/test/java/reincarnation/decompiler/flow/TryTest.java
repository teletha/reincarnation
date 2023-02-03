/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.flow;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import reincarnation.CodeVerifier;
import reincarnation.Debuggable;
import reincarnation.TestCode;

@Execution(ExecutionMode.SAME_THREAD)
class TryTest extends CodeVerifier {

    @Test
    void TryCatch() {
        verify(new TestCode.IntParam() {

            @Override
            @Debuggable
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    return MaybeThrow.error(value);
                } catch (Error e) {
                    return -1;
                }
            }
        });
    }

    @Test
    void TryCatchAfter() {
        verify(new TestCode.IntParam() {

            @Debuggable
            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = MaybeThrow.error(value);
                } catch (Error e) {
                    value += 2;
                }
                return value;
            }
        });
    }

    @Test
    void TryCatchAfter2() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = MaybeThrow.error(value);
                    value = MaybeThrow.error(value);
                } catch (Error e) {
                    value += 2;
                }
                return value;
            }
        });
    }

    @Test
    void TryCatchAfter3() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = MaybeThrow.error(value);
                } catch (Error e) {
                    return 50;
                }
                return value;
            }
        });
    }

    @Test
    void TryEmptyCatch() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = MaybeThrow.error(value);
                } catch (Error e) {
                    // do nothing
                }
                return value;
            }
        });
    }

    @Test
    void TryEmptyCatchAfterThrow() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    return error(value);
                } catch (Error e) {
                    return -10;
                }
            }

            @Debuggable
            private int error(int value) {
                try {
                    if (value != 3) {
                        return MaybeThrow.exception(value);
                    }
                } catch (Exception e) {
                    // do nothing
                }
                throw new Error();
            }
        });
    }

    @Test
    void TryMultipleCatch() {
        verify(new TestCode.IntParam() {

            @Debuggable
            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = MaybeThrow.error(value);
                    value = MaybeThrow.exception(value);

                    return value + 100;
                } catch (Exception e) {
                    return 2;
                } catch (Error e) {
                    return 3;
                }
            }
        });
    }

    @Test
    void TryMultipleCatchInherited() {
        verify(new TestCode.IntParam() {
            @Debuggable
            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = MaybeThrow.runtime(value);
                    value = MaybeThrow.exception(value);
                } catch (RuntimeException e) {
                    value = value + 2;
                } catch (Exception e) {
                    value = value + 3;
                }
                return value;
            }
        });
    }

    @Test
    void TryMultipleCatchAfter() {
        verify(new TestCode.IntParam() {

            @Debuggable
            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = MaybeThrow.error(value);
                    value = MaybeThrow.exception(value);
                } catch (Exception e) {
                    value = 20;
                } catch (Error e) {
                    value = 30;
                }
                return value;
            }
        });
    }

    @Test
    void TryCatchInCatch() {
        verify(new TestCode.IntParam() {

            @Debuggable
            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    return MaybeThrow.error(value);
                } catch (Error e) {
                    try {
                        return MaybeThrow.exception(value);
                    } catch (Exception e2) {
                        return value;
                    }
                }
            }
        });
    }

    @Test
    void TryCatchInCatchAfter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = MaybeThrow.error(value);
                } catch (Error e) {
                    try {
                        value = MaybeThrow.exception(value);
                    } catch (Exception e2) {
                        value = value + 3;
                    }
                    value = value + 2;
                }
                return value + 1;
            }
        });
    }

    @Test
    void TryCatchInTry() {
        verify(new TestCode.IntParam() {

            @Debuggable
            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = MaybeThrow.exception(value);

                    try {
                        value = MaybeThrow.error(value);
                    } catch (Error e) {
                        return value + 1;
                    }
                    return value + 2;
                } catch (Exception e) {
                    return value + 3;
                }
            }
        });
    }

    @Test
    void TryCatchInTryImmediately() {
        verify(new TestCode.IntParam() {

            @Debuggable
            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    try {
                        value = MaybeThrow.error(value);
                    } catch (Error e) {
                        return value + 1;
                    }
                    return MaybeThrow.exception(value);
                } catch (Exception e) {
                    return value + 3;
                }
            }
        });
    }

    @Test
    void TryCatchsInTry() {
        verify(new TestCode.IntParam() {

            @Debuggable
            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    try {
                        value = MaybeThrow.error(value);
                    } catch (Error e) {
                        return value + 10;
                    }

                    value += 5;

                    try {
                        value = MaybeThrow.exception(value);
                    } catch (Exception e) {
                        return value + 20;
                    }
                    return MaybeThrow.runtime(value);
                } catch (RuntimeException e) {
                    return value + 30;
                }
            }
        });
    }

    @Test
    void TryCatchsInTryMultiCatches() {
        verify(new TestCode.IntParam() {

            @Debuggable
            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    try {
                        value = MaybeThrow.error(value);
                    } catch (Error e) {
                        return value + 1;
                    }

                    value += 5;

                    try {
                        value = MaybeThrow.error(value);
                    } catch (Error e) {
                        return value + 2;
                    }
                    return MaybeThrow.exception(value);
                } catch (Exception e) {
                    return value + 3;
                } catch (Error e) {
                    return value + 4;
                }
            }
        });
    }

    @Test
    void TryCatchInTryAfter() {
        verify(new TestCode.IntParam() {

            @Debuggable
            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = MaybeThrow.exception(value);

                    try {
                        value = MaybeThrow.error(value);
                    } catch (Error e) {
                        value = value + 4;
                    }
                    value = value + 3;
                } catch (Exception e) {
                    value = value + 4;
                }
                return value + 1;
            }
        });
    }

    @Test
    @Disabled
    void TryCatchWithFrameFull() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                for (int i = 0; i < 1; i++) {
                    value++;
                }

                try {
                    return value;
                } catch (Exception e) {
                    return 10; // unexecutable
                }
            }
        });
    }

    @Test
    void TryCatchSequencial() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 3) int value) {
                try {
                    value = MaybeThrow.error(value);
                } catch (Error e) {
                    return -1;
                }

                try {
                    value = MaybeThrow.exception(value);
                } catch (Exception e) {
                    return -1;
                }
                return value;
            }
        });
    }

    @Test
    void TryCatchFinallyAfter() {
        verify(new TestCode.IntParam() {

            @Debuggable
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
    @Disabled
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

            @Debuggable
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

            @Debuggable
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
    void TryCatchFinallyAfterr() {
        verify(new TestCode.IntParam() {

            private int counter = 0;

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                return count(value) + counter;
            }

            @Debuggable
            private int count(int value) {
                try {
                    value = MaybeThrow.error(value);
                } catch (Error e) {
                    counter++;
                } finally {
                    counter--;
                }
                return counter;
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

            @Debuggable
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

    @Test
    void insideFor() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Map<String, String> map = new HashMap();
                map.put("1", "one");
                map.put("2", "two");
                String value = "";

                for (Entry<String, String> entry : map.entrySet()) {
                    try {
                        value += "1";
                    } catch (IllegalArgumentException e) {
                        value += "2";
                    }
                }
                return value;
            }
        });
    }

    @Test
    void insideForWithContinue() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int value = 0;

                for (int i = 0; i < 4; i++) {
                    try {
                        if (i % 2 == 0) {
                            continue;
                        }
                    } catch (IllegalArgumentException e) {
                        // do nothing
                    }
                    value += i;
                }
                return value;
            }
        });
    }

    @Test
    void insideForWithBreak() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int value = 0;

                for (int i = 0; i < 4; i++) {
                    try {
                        if (i % 2 == 0) {
                            break;
                        }
                    } catch (IllegalArgumentException e) {
                        // do nothing
                    }
                    value += i;
                }
                return value;
            }
        });
    }
}