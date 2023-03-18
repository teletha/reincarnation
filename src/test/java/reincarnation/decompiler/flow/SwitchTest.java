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
class SwitchTest extends CodeVerifier {

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
    void nest() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int param) {
                switch (param) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    switch (param + 1) {
                    case 3:
                        return 30;
                    case 4:
                        return 40;
                    }
                    return 10;

                case 6:
                case 7:
                case 8:
                case 9:
                    switch (param + 1) {
                    case 8:
                        return 80;
                    case 10:
                        return 100;
                    }
                    return -1;
                }

                return 30;
            }
        });
    }

    @Test
    @Disabled
    void switchExpression() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                return switch (param) {
                case 0 -> 10;
                case 1 -> 15;
                default -> {
                    param += -2;
                    yield 20 + param;
                }
                };
            }
        });
    }

    @Test
    @Disabled
    void blockInDefault() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                return switch (param) {
                case 0 -> 20;
                default -> {
                    for (int i = 0; i < 2; i++) {
                        param++;
                    }
                    yield param;
                }
                };
            }
        });
    }
}
