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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.Debuggable;
import reincarnation.TestCode;

class TryTest extends CodeVerifier {

    private static class Throw {

        private static int error(int value) {
            if (value % 2 == 0) {
                throw new Error();
            }
            return value + 1000;
        }

        private static int exception(int value) throws Exception {
            if (value % 3 == 0) {
                throw new Exception();
            }
            return value + 10000;
        }

        private static int io(int value) throws IOException {
            if (value % 4 == 0) {
                throw new IOException();
            }
            return value + 100000;
        }
    }

    @Test
    void TryCatch() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    return Throw.error(value);
                } catch (Error e) {
                    return -1;
                }
            }
        });
    }

    @Test
    void TryCatchAfter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = Throw.error(value);
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
                    value = Throw.error(value);
                    value = Throw.error(value);
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
                    value = Throw.error(value);
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
                    value = Throw.error(value);
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

            private int error(int value) {
                try {
                    if (value != 3) {
                        return Throw.exception(value);
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

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = Throw.error(value);
                    value = Throw.exception(value);

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
    @Disabled
    void TryMultipleCatchInherited() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = Throw.io(value);
                    value = Throw.exception(value);
                } catch (IOException e) {
                    value = value + 2;
                } catch (Exception e) {
                    value = value + 3;
                }
                return value;
            }
        });
    }

    @Test
    @Disabled
    void TryMultipleCatchAfter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = Throw.error(value);
                    value = Throw.exception(value);
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

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    return Throw.error(value);
                } catch (Error e) {
                    try {
                        return Throw.exception(value);
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
                    value = Throw.error(value);
                } catch (Error e) {
                    try {
                        value = Throw.exception(value);
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

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = Throw.exception(value);

                    try {
                        value = Throw.error(value);
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

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    try {
                        value = Throw.error(value);
                    } catch (Error e) {
                        return value + 1;
                    }
                    return Throw.exception(value);
                } catch (Exception e) {
                    return value + 3;
                }
            }
        });
    }

    @Test
    @Disabled
    void TryCatchInTryAfter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = Throw.exception(value);

                    try {
                        value = Throw.error(value);
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
                    value = Throw.error(value);
                } catch (Error e) {
                    return -1;
                }

                try {
                    value = Throw.exception(value);
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

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = Throw.error(value);
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
                    return Throw.error(value);
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
                    return Throw.error(value);
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
    @Disabled
    void TryFinally() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = error(value);
                } catch (Error e) {
                    // do nothing
                }
                return value;
            }

            private int error(int value) {
                try {
                    value = Throw.error(value);
                } finally {
                    value++;
                }
                return value;
            }
        });
    }

    @Test
    @Disabled
    void TryFinally2() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = error(value);
                } catch (Error e) {
                    // do nothing
                }
                return value;
            }

            private int error(int value) {
                try {
                    for (int i = 0; i < 3; i++) {
                        value = Throw.error(value);
                    }
                } finally {
                    value++;
                }
                return value;
            }
        });
    }

    @Test
    @Disabled
    void TryFinallyVoid() {
        verify(new TestCode.IntParam() {

            private int counter = 0;

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    if (value != 0) {
                        error(value);
                    }
                } catch (Error e) {
                    // do nothing
                }
                return counter;
            }

            private void error(int value) {
                try {
                    counter = Throw.error(value);
                } finally {
                    counter++;
                }
            }
        });
    }

    @Test
    @Disabled
    void TryFinallyNest1() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    try {
                        value += 1;
                    } finally {
                        value += 2;
                    }
                } finally {
                    try {
                        value += 3;
                    } finally {
                        value += 4;
                    }
                }
                return value;
            }
        });
    }

    @Test
    @Disabled
    void TryFinallyNest2() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value += 1;

                    try {
                        value += 2;
                    } finally {
                        value += 3;
                    }
                } finally {
                    value += 4;

                    try {
                        value += 5;
                    } finally {
                        value += 6;
                    }
                }
                return value;
            }
        });
    }

    @Test
    @Disabled
    void TryFinallyNest3() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    try {
                        value += 1;
                    } finally {
                        value += 2;
                    }
                    value += 3;
                } finally {
                    try {
                        value += 4;
                    } finally {
                        value += 5;
                    }
                    value += 6;
                }
                return value;
            }
        });
    }

    @Test
    void TryCatchFinally() {
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
                    counter += 3;
                }
            }
        });
    }

    @Test
    @Disabled
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

    @Test
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