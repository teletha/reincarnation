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

import java.lang.annotation.RetentionPolicy;

import reincarnation.CodeVerifier;
import reincarnation.CompilerType;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class SwitchTest extends CodeVerifier {

    @CrossDecompilerTest
    void natural() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                case 0:
                    return 10;

                case 1:
                    return 15;

                case 2:
                    return 20;

                default:
                    return 25;
                }
            }
        });
    }

    @CrossDecompilerTest
    void firstDefault() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                default:
                    return 25;

                case 0:
                    return 10;

                case 1:
                    return 15;

                case 2:
                    return 20;
                }
            }
        });
    }

    @CrossDecompilerTest
    void reverse() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                case 2:
                    return 10;

                case 1:
                    return 15;

                case 0:
                    return 20;

                default:
                    return 25;
                }
            }
        });
    }

    @CrossDecompilerTest
    void mix() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                case 1:
                    return 10;

                case 2:
                    return 15;

                case 0:
                    return 20;

                default:
                    return 25;
                }
            }
        });
    }

    @CrossDecompilerTest
    void gap() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                case 1:
                    return 10;

                case 3:
                    return 15;

                default:
                    return 25;
                }
            }
        });
    }

    @CrossDecompilerTest
    void gapReverse() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                case 5:
                    return 10;

                case 0:
                    return 15;

                default:
                    return 25;
                }
            }
        });
    }

    @CrossDecompilerTest
    void sparse() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                case 5:
                    return 10;

                case 100:
                    return 15;

                default:
                    return 25;
                }
            }
        });
    }

    @CrossDecompilerTest
    void sparseReverse() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                case 100:
                    return 10;

                case 5:
                    return 15;

                default:
                    return 25;
                }
            }
        });
    }

    @CrossDecompilerTest
    void noDefault() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                switch (param) {
                case 0:
                    return 10;

                case 7:
                    return 20;
                }

                return 30;
            }
        });
    }

    @CrossDecompilerTest
    void methodCondition() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                switch (increment(param)) {
                case 1:
                    return 10;

                case 2:
                    return 20;
                }

                return 30;
            }

            private int increment(int value) {
                return value + 1;
            }
        });
    }

    @CrossDecompilerTest
    void conditional() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                switch (param % 2) {
                case 0:
                    if (param < 5) {
                        return 0;
                    }
                }
                return 30;
            }
        });
    }

    @CrossDecompilerTest
    void loop() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                case 0, 1, 2:
                    int value = 0;
                    for (int i = 0; i < param; i++) {
                        value += 2;
                    }
                    return value;

                default:
                    return param;
                }
            }
        });
    }

    @CrossDecompilerTest
    void infinitLoop() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                case 0, 1, 2:
                    while (true) {
                        param += 4;

                        if (10 < param) {
                            return param;
                        }
                    }

                default:
                    return param;
                }
            }
        });
    }

    @CrossDecompilerTest
    void tryCatch() {
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

    @CrossDecompilerTest
    void tryCatchFinally() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                case 0, 1, 2:
                    try {
                        param = MaybeThrow.error(param);
                    } catch (Error e) {
                        param = param + 1;
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
    void withThrow() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                try {
                    switch (param) {
                    case 0:
                        return 10;

                    case 1:
                        return 20;

                    default:
                        throw new Error();
                    }
                } catch (Error e) {
                    return 30;
                }
            }
        });
    }

    @CrossDecompilerTest
    void nest() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                switch (param % 2) {
                case 0:
                    switch (param % 3) {
                    case 0:
                        return 3;
                    default:
                        return -3;
                    }

                default:
                    switch (param % 3) {
                    case 0:
                        return 1;
                    default:
                        return -1;
                    }
                }
            }
        });
    }

    @CrossDecompilerTest
    void fallThrough() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                case 0:
                    param += 1;

                case 1:
                    return 1 + param;

                default:
                    return 25;
                }
            }
        });
    }

    @CrossDecompilerTest
    void fallThroughFirstDefault() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                default:
                    param += 1;

                case 1:
                    return 1 + param;

                case 0:
                    return 25;
                }
            }
        });
    }

    @CrossDecompilerTest
    void fallThroughReverse() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                case 2:
                    param += 1;

                case 1:
                    return 1 + param;

                default:
                    return 25;
                }
            }
        });
    }

    @CrossDecompilerTest
    void fallThroughMix() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                case 2:
                    param += 1;

                case 1:
                    param -= 2;

                case 0:
                    return 1 + param;

                default:
                    return 25;
                }
            }
        });
    }

    @CrossDecompilerTest
    void fallThroughGap() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                case 1:
                    param += 1;

                case 2:
                    return param + 20;

                case 3:
                    param += 2;

                default:
                    return param + 30;
                }
            }
        });
    }

    @CrossDecompilerTest
    void fallThroughGapReverse() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                case 5:
                    param += 1;

                case 3:
                    param += 2;

                case 0:
                    param += 3;

                default:
                    return param + 10;
                }
            }
        });
    }

    @CrossDecompilerTest
    void fallThroughSpase() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                case 1:
                    param += 1;

                case 100:
                    return 1 + param;

                default:
                    return 25;
                }
            }
        });
    }

    @CrossDecompilerTest
    void fallThroughSpaseReverse() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                case 5:
                    param += 1;

                case -100:
                    return 1 + param;

                default:
                    return 25;
                }
            }
        });
    }

    @CrossDecompilerTest
    void fallThroughNoDefault() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                switch (param) {
                case 0:
                    param += 1;

                case 1:
                    param += 2;

                case 2:
                    return param;

                case 3:
                    param += 3;

                case 7:
                    return param + 7;
                }

                return 30;
            }
        });
    }

    @CrossDecompilerTest
    void fallThroughBlock() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                switch (param % 3) {
                case 0:
                    if (param % 2 == 0) {
                        return 2;
                    } else {
                        param += 1;
                    }

                case 1:
                    if (param % 2 == 0) {
                        return 3;
                    } else {
                        param += 3;
                    }

                default:
                    if (param % 5 == 0) {
                        return 5;
                    } else {
                        return 1;
                    }
                }
            }
        });
    }

    @CrossDecompilerTest
    void fallThroughNest() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                switch (param % 2) {
                case 0:
                    switch (param % 3) {
                    case 0:
                        return 3;
                    case 1:
                        return 2;
                    }

                default:
                    switch (param % 5) {
                    case 0:
                        return 1;
                    default:
                        return -1;
                    }
                }
            }
        });
    }

    @CrossDecompilerTest
    void breakNatural() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value;
                switch (param) {
                case 0:
                    value = 10;
                    break;

                case 1:
                    value = 11;
                    break;

                default:
                    value = 12;
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakReverse() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value;
                switch (param) {
                case 1:
                    value = 10;
                    break;

                case 0:
                    value = 11;
                    break;

                default:
                    value = 12;
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakFirstDefault() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value;
                switch (param) {
                default:
                    value = 12;
                    break;

                case 1:
                    value = 10;
                    break;

                case 0:
                    value = 11;
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakMix() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value;
                switch (param) {
                case 3:
                    value = 2;
                    break;

                case 0:
                    value = 10;
                    break;

                case 4:
                    value = 11;
                    break;

                default:
                    value = 12;
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakMixWithSingleBlock1() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value = 0;
                switch (param) {
                case 3:
                    value = 2;
                    break;

                case 0:
                    value = 10;
                    break;

                case 4:
                    value = 11;
                    break;

                default:
                    if (param == 2) {
                        value = -1;
                    }
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakMixWithSingleBlock2() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value;
                switch (param) {
                case 3:
                    value = 2;
                    break;

                case 0:
                    value = 10;
                    break;

                case 4:
                    value = 11;
                    break;

                default:
                    if (param == 2) {
                        value = -1;
                    } else {
                        value = -2;
                    }
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakMixWithNestedBlock() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value = 0;
                switch (param) {
                case 1:
                    value = 1;
                    break;

                default:
                    if (param % 2 == 0) {
                        if (param == 2) {
                            break;
                        }
                        value = -1;
                    } else {
                        value = -2;
                    }
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakGap() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value;
                switch (param) {
                case 0:
                    value = 2;
                    break;

                case 2:
                    value = 10;
                    break;

                case 4:
                    value = 11;
                    break;

                default:
                    value = 12;
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakGapReverse() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value;
                switch (param) {
                case 4:
                    value = 2;
                    break;

                case 2:
                    value = 10;
                    break;

                case 0:
                    value = 11;
                    break;

                default:
                    value = 12;
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakSparse() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value;
                switch (param) {
                case 0:
                    value = 2;
                    break;

                case 100:
                    value = 10;
                    break;

                default:
                    value = 12;
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakSparseReverse() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value;
                switch (param) {
                case 100:
                    value = 2;
                    break;

                case 0:
                    value = 10;
                    break;

                default:
                    value = 12;
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakNoDefault() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                int value = 0;
                switch (param) {
                case 0:
                    value = 1;
                    break;
                case 1:
                    value = 3;
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakBlock() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                int value = 0;
                switch (param % 2) {
                case 0:
                    if (param % 3 == 0) {
                        value = 3;
                    } else {
                        value = 4;
                    }
                    break;

                default:
                    if (param % 5 == 0) {
                        value = 5;
                    } else {
                        value = 1;
                    }
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakInfinitLoop() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                root: switch (param) {
                case 0, 1, 2:
                    while (true) {
                        param += 4;

                        if (10 < param) {
                            break root;
                        }
                    }

                default:
                    param = 100;
                    break;
                }
                return param;
            }
        });
    }

    @CrossDecompilerTest
    void breakConditionalBlock() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                switch (param % 2) {
                case 0:
                    if (param < 5) {
                        param += 10;
                        break;
                    }
                }
                return param;
            }
        });
    }

    @CrossDecompilerTest
    void breakTryCatch() {
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

    @CrossDecompilerTest
    void breakTryCatchFinally() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                case 0, 1:
                    try {
                        param = MaybeThrow.error(param);
                    } catch (Error e) {
                        param = param + 1;
                        break;
                    } finally {
                        param += 2;
                    }
                    param *= 3;
                    break;

                default:
                    param *= 4;
                    break;
                }
                return param + 10;
            }
        });
    }

    @CrossDecompilerTest(CompilerType.Javac)
    void breakNest() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                int value = 0;
                switch (param % 2) {
                case 0:
                    switch (param % 3) {
                    case 0:
                        value = 3;
                        break;
                    default:
                        value = -3;
                        break;
                    }
                    break;

                default:
                    switch (param % 5) {
                    case 0:
                        value = 1;
                        break;
                    default:
                        value = -1;
                        break;
                    }
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakDoubleNest() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                int value = 0;
                root: switch (param % 2) {
                case 0:
                    switch (param % 3) {
                    case 0:
                        value += 1;
                        break root;

                    case 1:
                        value += 2;
                        break;

                    default:
                        value = 3;
                        break;
                    }
                    value += 4;
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakTripleNest() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 20) int param) {
                int value = 0;
                root: switch (param % 2) {
                case 0:
                    child: switch (param % 3) {
                    case 0:
                        value += 1;
                        break root;

                    case 1:
                        switch (param % 5) { // 6 -> 8def,9,10,11,12
                        case 0:
                            value += 10; // 9 -> 13 -> 8
                            break;

                        case 1:
                            value += 20; // 10 -> 14 -> 2
                            break root;

                        case 2:
                            value += 30; // 11 -> 15 -> 20
                            break child;

                        case 3:
                            value += 40; // 12 -> 8
                            break;
                        }
                        value += 100; // 8 -> 17 -> 20
                        break;

                    default:
                        value = 3;
                        break;
                    }
                    value += 4;
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakFallThrough() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value;
                switch (param) {
                case 0:
                    param += 1;

                case 1:
                    value = param + 2;
                    break;

                case 2:
                    param += 3;

                default:
                    value = param + 4;
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakFallThroughFirstDefault() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value;
                switch (param) {
                default:
                    param += 1;

                case 0:
                    value = param + 2;
                    break;

                case 1:
                    value = param + 3;
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakFallThroughReverse() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value;
                switch (param) {
                case 2:
                    param += 1;

                case 1:
                    value = param + 2;
                    break;

                case 0:
                    param += 3;

                default:
                    value = param + 4;
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakFallThrougMix() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value;
                switch (param) {
                case 1:
                    param += 1;

                case 0:
                    value = param + 2;
                    break;

                case 2:
                    param += 3;

                default:
                    value = param + 4;
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakFallThrougGap() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value;
                switch (param) {
                case 1:
                    param += 1;

                case 3:
                    value = param + 2;
                    break;

                case 5:
                    param += 3;

                default:
                    value = param + 4;
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakFallThrougGapReverse() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value;
                switch (param) {
                case 5:
                    param += 1;

                case 3:
                    value = param + 2;
                    break;

                case 1:
                    param += 3;

                default:
                    value = param + 4;
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakFallThroughSparse() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value;
                switch (param) {
                case 0:
                    param += 1;

                case 100:
                    value = param + 2;

                default:
                    value = param + 4;
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakFallThroughSparseReverse() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value;
                switch (param) {
                case 100:
                    param += 1;

                case 0:
                    value = param + 2;

                default:
                    value = param + 4;
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakFallThroughNoDefault() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                int value = 0;
                switch (param) {
                case 0:
                    value -= 2;
                case 1:
                    value -= 3;
                    break;

                case 2:
                    value += 2;

                case 3:
                    value += 3;
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakFallThroughBlock() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 15) int param) {
                int value = 0;
                switch (param % 2) {
                case 0:
                    if (param % 3 == 0) {
                        value = 3;
                        break;
                    } else {
                        value = 4;
                    }

                default:
                    if (param % 5 == 0) {
                        value = 5;
                    } else {
                        value = 1;
                        break;
                    }
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakFallThroughNest() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                int value = 0;
                switch (param % 3) {
                case 0:
                    switch (param % 2) {
                    case 0:
                        value += 1;
                        break;
                    default:
                        value -= 1;
                        break;
                    }

                case 1:
                    switch (param % 5) {
                    case 0:
                        value += 2;
                    default:
                        value -= 2;
                        break;
                    }
                    break;

                default:
                    switch (param % 7) {
                    case 0:
                        value += 3;
                    default:
                        value -= 3;
                        break;
                    }
                    break;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void expressionInCondition() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                switch (param % 5) {
                case 0:
                case 1:
                    return 10;

                case 3:
                case 4:
                    return 15;

                default:
                    return 25;
                }
            }
        });
    }

    @CrossDecompilerTest
    void expressionInCondition2() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                switch (param % 5) {
                case 0:
                case 1:
                case 2:
                    return 10;

                case 4:
                    return 15;

                default:
                    return 25;
                }
            }
        });
    }

    @CrossDecompilerTest
    void multiple() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                switch (param) {
                case 0:
                case 1:
                case 2:
                    return 10;

                case 3:
                case 4:
                case 5:
                    return 15;

                case 6:
                case 7:
                    return 20;

                default:
                    return 25;
                }
            }
        });
    }

    @CrossDecompilerTest
    void conditionByChar() {
        verify(new TestCode.CharParam() {

            @Override
            public char run(@Param(chars = {'a', 'b', 'c', 'd', 'e'}) char param) {
                switch (param) {
                case 'a':
                    return 'A';

                case 'b':
                    return 'B';

                default:
                    return param;
                }
            }
        });
    }

    @CrossDecompilerTest
    void conditionByCharMultiple() {
        verify(new TestCode.CharParam() {

            @Override
            public char run(@Param(chars = {'a', 'b', 'c', 'd', 'e'}) char param) {
                switch (param) {
                case 'a':
                case 'b':
                    return 'X';

                case 'c':
                case 'd':
                    return 'Y';

                default:
                    return param;
                }
            }
        });
    }

    @CrossDecompilerTest
    void conditionByCharWithMethodCondition() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(@Param(strings = {"aa", "ab", "ac", "ba", "bb"}) String param) {
                switch (param.charAt(0)) {
                case 'a':
                    return "A";

                default:
                    return param;
                }
            }
        });
    }

    @CrossDecompilerTest
    void conditionByEnum() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 3) int param) {
                switch (RetentionPolicy.values()[param]) {
                case CLASS:
                    return 10;

                case RUNTIME:
                    return 20;

                default:
                    return 30;
                }
            }
        });
    }

    @CrossDecompilerTest
    void conditionByEnumMultiple() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 3) int param) {
                switch (RetentionPolicy.values()[param]) {
                case CLASS:
                case RUNTIME:
                    return 20;

                default:
                    return 30;
                }
            }
        });
    }

    @CrossDecompilerTest
    void conditionByString() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(@Param(strings = {"a", "b", "c", "d", "e"}) String param) {
                switch (param) {
                case "a":
                    return "A";

                case "b":
                    return "B";

                default:
                    return param;
                }
            }
        });
    }

    @CrossDecompilerTest
    void conditionByStringMultiple() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(@Param(strings = {"a", "b", "c", "d", "e"}) String param) {
                switch (param) {
                case "a":
                case "b":
                    return "AB";

                case "c":
                case "d":
                    return "CD";

                default:
                    return param;
                }
            }
        });
    }

    @CrossDecompilerTest
    void conditionByStringWithMethodCondition() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(@Param(strings = {"aa", "ab", "ac", "ba", "bb"}) String param) {
                switch (param.substring(0, 1)) {
                case "a":
                    return "A";

                default:
                    return param;
                }
            }
        });
    }

    @CrossDecompilerTest
    void conditionByStringNest() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(@Param(strings = {"aa", "ab", "ac", "ba", "bb"}) String param) {
                switch (param.substring(0, 1)) {
                case "a":
                    switch (param.substring(1, 2)) {
                    case "a":
                        return "AA";

                    case "b":
                        return "AB";

                    default:
                        return "AC";
                    }

                default:
                    return param;
                }
            }
        });
    }
}