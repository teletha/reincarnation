/*
 * Copyright (C) 2016 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.flow;

import org.junit.jupiter.api.Test;

import reincarnation.Code;
import reincarnation.CodeVerifier;
import reincarnation.Debuggable;

/**
 * @version 2018/10/29 15:01:13
 */
class DoWhileTest extends CodeVerifier {

    @Test
    void normal() {
        verify(new Code.IntParam() {

            @Debuggable
            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                do {
                    value++;
                } while (value < 3);

                return value;
            }
        });
    }

    @Test
    void equivalent() {
        verify(new Code.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                while (true) {
                    value++;

                    if (3 <= value) {
                        break;
                    }
                }

                return value;
            }
        });
    }

    @Test
    void complexCondition() {
        verify(new Code.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                do {
                    value += 2;
                } while (++value < 4 || ++value % 3 == 0);

                return value;
            }
        });
    }

    @Test
    void breakNoLabel() {
        verify(new Code.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                do {
                    value++;

                    if (value == 2) {
                        break;
                    }
                    value++;
                } while (value < 4);

                return value;
            }
        });
    }

    @Test
    void breakInfinite() {
        verify(new Code.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                do {
                    value++;

                    if (value == 10) {
                        break;
                    }
                } while (true);

                return value;
            }
        });
    }

    @Test
    void continueNoLabel() {
        verify(new Code.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                do {
                    value += 2;

                    if (value == 2) {
                        continue;
                    }

                    value += 3;
                } while (value < 4);

                return value;
            }
        });
    }

    @Test
    void continueAndBreak() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                do {
                    value++;

                    if (value % 2 == 0) {
                        continue;
                    }

                    if (value < 0) {
                        break;
                    }
                    value++;
                } while (value < 4);

                return value;
            }
        });
    }

    @Test
    void oneLiner() {
        verify(new Code.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                // @formatter:off
                do {value+= 2;} while (value < 3);
                // @formatter:on
                return value;
            }
        });
    }

    @Test
    void oneLinerComplexCondition() throws Exception {
        verify(new Code.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                // @formatter:off
                do {value += 2;} while (++value < 10 && value % 2 == 0);
                // @formatter:on
                return value;
            }
        });
    }

    @Test
    void inIf() throws Exception {
        verify(new Code.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                if (value != 3) {
                    do {
                        value += 2;
                    } while (value < 5);
                }
                return value;
            }
        });
    }

    @Test
    void inIfOneLiner() throws Exception {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                if (value != 0) {
                    // @formatter:off
                    do {value += 2;} while (value < 3);           
                    // @formatter:on
                }
                return value;
            }
        });
    }

    @Test
    void inOneLinerIf() throws Exception {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                // @formatter:off
                if (value != 0) do {value += 2;} while (value < 3);
                // @formatter:on
                return value;
            }
        });
    }

    @Test
    void continueThenFollow() {
        verify(new Code.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                do {
                    value += 5;

                    if (value % 2 == 0) {
                        if (value % 3 == 0) {
                            continue;
                        }
                        value++;
                    }
                    value += 3;
                } while (value < 10);

                return value;
            }
        });
    }

    @Test
    void withWhile() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                do {
                    value++;

                    if (value % 2 == 0) {
                        value += 5;
                    }

                    while (value < 7) {
                        value += 3;
                    }
                } while (value < 10);

                return value;
            }
        });
    }

    @Test
    void nestContinueJump() {
        verify(new Code.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                root: do {
                    value += 2;

                    if (value % 2 == 0) {
                        if (value % 3 == 0) {
                            continue;
                        }
                        value++;
                    }

                    do {
                        value += 10;

                        if (value % 3 == 0) {
                            continue root;
                        }
                    } while (value < 7);
                } while (value < 10);

                return value;
            }
        });
    }

    @Test
    void nestBreakJump() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                root: do {
                    value++;

                    do {
                        value += 2;

                        if (value % 3 == 0) {
                            break root;
                        }
                    } while (value < 5);
                } while (value < 7);

                return value;
            }
        });
    }
}
