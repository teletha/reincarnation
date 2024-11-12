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

import org.junit.jupiter.api.Disabled;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.DisabledOnMaven;
import reincarnation.TestCode;

class SwitchExpressionTest extends CodeVerifier {

    @CrossDecompilerTest
    void assignToVariable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value = switch (param) {
                case 0 -> 10;
                case 1 -> 15;
                default -> param;
                };
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void methodParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                return value(switch (param) {
                case 0 -> 10;
                case 1 -> 15;
                default -> param;
                });
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void methodCondition() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                return switch (increment(param)) {
                case 1 -> 10;
                case 2 -> 20;
                default -> 30;
                };
            }

            private int increment(int value) {
                return value + 1;
            }
        });
    }

    @CrossDecompilerTest
    void withAssign() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value = switch (param) {
                case 0 -> param = 10;
                default -> param;
                };
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void withUnaryOperator() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value = switch (param) {
                case 0 -> --param;
                default -> param;
                };
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void withAssignOperator() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value = switch (param) {
                case 0 -> param += 10;
                default -> param;
                };
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void sparse() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                return switch (param) {
                case 5 -> 10;
                case 100 -> 15;
                default -> 25;
                };
            }
        });
    }

    @CrossDecompilerTest
    void block() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                return switch (param) {
                case 0 -> {
                    int value = param;
                    yield value + 1;
                }
                default -> param;
                };
            }
        });
    }

    @CrossDecompilerTest
    void blockInMultipleCases() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                return switch (param) {
                case 0 -> {
                    int value = param;
                    yield value + 1;
                }
                case 1 -> {
                    int value = param;
                    yield value + 2;
                }
                default -> param;
                };
            }
        });
    }

    @CrossDecompilerTest
    void blockInDefault() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                return switch (param) {
                case 0 -> {
                    int value = param;
                    yield value + 1;
                }
                case 1 -> {
                    int value = param;
                    yield value + 2;
                }
                default -> {
                    int value = param;
                    yield value + 3;
                }
                };
            }
        });
    }

    @CrossDecompilerTest
    void blockIf() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                return switch (param) {
                case 0, 1, 2 -> {
                    if (param % 2 == 0) {
                        if (param == 2) {
                            yield 1;
                        } else {
                            yield 10;
                        }
                    } else {
                        yield param - 20;
                    }
                }
                default -> param;
                };
            }
        });
    }

    @CrossDecompilerTest
    void blockFor() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                return switch (param) {
                case 0, 1, 2 -> {
                    int value = 0;
                    for (int i = 0; i < param; i++) {
                        value += 2;
                    }
                    yield value;
                }
                default -> param;
                };
            }
        });
    }

    @CrossDecompilerTest
    void conditional() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                return switch (param) {
                case 0, 1, 2 -> {
                    if (param < 2) {
                        yield 10;
                    } else {
                        yield -20;
                    }
                }
                default -> param;
                };
            }
        });
    }

    @CrossDecompilerTest
    void loop() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                return switch (param) {
                case 0, 1, 2 -> {
                    int value = 0;
                    for (int i = 0; i < param; i++) {
                        value += 2;
                    }
                    yield value;
                }
                default -> param;
                };
            }
        });
    }

    @CrossDecompilerTest
    void infiniteLoop() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                return switch (param) {
                case 0, 1, 2 -> {
                    while (true) {
                        param += 4;
                        if (10 < param) {
                            yield param;
                        }
                    }
                }
                default -> param;
                };
            }
        });
    }

    @CrossDecompilerTest
    void tryCatch() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                return switch (param) {
                case 0, 1, 2 -> {
                    try {
                        param = MaybeThrow.error(param);
                    } catch (Error e) {
                        param = param + 1;
                    }
                    yield param;
                }
                default -> param;
                };
            }
        });
    }

    @CrossDecompilerTest
    void tryCatchFinally() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                return switch (param) {
                case 0, 1, 2 -> {
                    try {
                        param = MaybeThrow.error(param);
                    } catch (Error e) {
                        param = param + 1;
                    } finally {
                        param += 2;
                    }
                    yield param;
                }
                default -> param;
                };
            }
        });
    }

    @CrossDecompilerTest
    void withThrow() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                try {
                    return value(switch (param) {
                    case 0 -> 10;
                    case 1 -> 15;
                    default -> throw new Error();
                    });
                } catch (Error e) {
                    return 30;
                }
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void nest() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                return switch (param % 2) {
                case 0 -> switch (param) {
                case 2 -> 20;
                default -> param;
                };
                default -> 30;
                };
            }
        });
    }

    @CrossDecompilerTest
    void inSwitchStatement() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                switch (param % 2) {
                case 0:
                    return switch (param) {
                    case 2 -> 20;
                    default -> param;
                    };
                default:
                    return 30;
                }
            }
        });
    }

    @CrossDecompilerTest
    void onSwitchStatement() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                return switch (param % 2) {
                case 0 -> {
                    switch (param) {
                    case 2:
                        yield 20;
                    default:
                        yield param;
                    }
                }
                default -> 30;
                };
            }
        });

    }

    @CrossDecompilerTest
    void conditionByChar() {
        verify(new TestCode.CharParam() {

            @Override
            public char run(@Param(chars = {'a', 'b', 'c', 'd', 'e'}) char param) {
                return switch (param) {
                case 'a' -> 'A';
                case 'b' -> 'B';
                default -> param;
                };
            }
        });
    }

    @CrossDecompilerTest
    void conditionByCharMultiple() {
        verify(new TestCode.CharParam() {

            @Override
            public char run(@Param(chars = {'a', 'b', 'c', 'd', 'e'}) char param) {
                return switch (param) {
                case 'a', 'b' -> 'X';
                case 'c', 'd' -> 'Y';
                default -> param;
                };
            }
        });
    }

    @CrossDecompilerTest
    void conditionByCharWithMethodCondition() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(@Param(strings = {"aa", "ab", "ac", "ba", "bb"}) String param) {
                return switch (param.charAt(0)) {
                case 'a' -> "A";
                default -> param;
                };
            }
        });
    }

    @DisabledOnMaven
    @CrossDecompilerTest
    void conditionByEnum() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 3) int param) {
                return switch (RetentionPolicy.values()[param]) {
                case CLASS -> 10;
                case RUNTIME -> 20;
                default -> 30;
                };
            }
        });
    }

    @DisabledOnMaven
    @CrossDecompilerTest
    void conditionByEnumMultiple() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 3) int param) {
                return switch (RetentionPolicy.values()[param]) {
                case CLASS, RUNTIME -> 10;
                default -> 20;
                };
            }
        });
    }

    @CrossDecompilerTest
    void conditionByString() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(@Param(strings = {"a", "b", "c", "d", "e"}) String param) {
                return switch (param) {
                case "a" -> "A";
                case "b" -> "B";
                default -> param;
                };
            }
        });
    }

    @CrossDecompilerTest
    void conditionByStringMultiple() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(@Param(strings = {"a", "b", "c", "d", "e"}) String param) {
                return switch (param) {
                case "a", "b" -> "AB";
                case "c", "d" -> "CD";
                default -> param;
                };
            }
        });
    }

    @CrossDecompilerTest
    void conditionByStringWithMethodCondition() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(@Param(strings = {"aa", "ab", "ac", "ba", "bb"}) String param) {
                return switch (param.substring(0, 1)) {
                case "a" -> "A";
                default -> param;
                };
            }
        });
    }

    @CrossDecompilerTest
    @Disabled
    void conditionByStringNest() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(@Param(strings = {"aa", "ab", "ac", "ba", "bb"}) String param) {
                return switch (param.substring(0, 1)) {
                case "a" -> switch (param.substring(1, 2)) {
                case "a" -> "AA";
                case "b" -> "AB";
                default -> "AC";
                };

                default -> param;
                };
            }
        });
    }
}