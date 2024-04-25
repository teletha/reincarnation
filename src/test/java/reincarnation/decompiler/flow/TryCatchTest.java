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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class TryCatchTest extends CodeVerifier {

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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