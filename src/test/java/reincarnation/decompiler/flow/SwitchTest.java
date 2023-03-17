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

                case 3:
                    return 15 + param;

                default:
                    return 25;
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

                case 0:
                    return 15 + param;

                default:
                    return 25;
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
    @Disabled
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
