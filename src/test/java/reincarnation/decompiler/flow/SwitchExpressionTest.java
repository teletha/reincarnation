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

import java.lang.annotation.RetentionPolicy;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.Debuggable;
import reincarnation.TestCode;

class SwitchExpressionTest extends CodeVerifier {

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
    @Debuggable
    void nest() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                return switch (param % 2) {
                case 0 -> switch (param) {
                case 2 -> 20;
                default -> param;
                };
                default -> switch (param) {
                case 1 -> -10;
                default -> -param;
                };
                };
            }
        });
    }

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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
}
