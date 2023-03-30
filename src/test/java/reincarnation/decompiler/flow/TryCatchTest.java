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

@Debuggable
class TryCatchTest extends CodeVerifier {

    @Test
    void TryCatch() {
        verify(new TestCode.IntParam() {

            @Override
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

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                try {
                    value = MaybeThrow.runtime(value);
                    value = MaybeThrow.exception(value);
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
    void TryMultipleCatchAfter() {
        verify(new TestCode.IntParam() {

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

    @Test
    void insideSwitch() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                case 0, 1, 2:
                    try {
                        param = MaybeThrow.error(param);
                    } catch (Error e) {
                        param = param + 1;
                    }
                    return param + 5;

                default:
                    return param;
                }
            }
        });
    }

    @Test
    @Debuggable
    @Disabled
    void insideSwitchWithBreak() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                case 0, 1:
                    try {
                        param = MaybeThrow.error(param);
                    } catch (Error e) {
                        param = param + 1;
                    }
                    break;

                default:
                    param += 2;
                    break;
                }
                return param + 10;
            }
        });
    }
}