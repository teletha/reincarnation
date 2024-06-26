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

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class DoWhileTest extends CodeVerifier {

    @CrossDecompilerTest
    void normal() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                do {
                    value++;
                } while (value < 3);

                return value;
            }
        });
    }

    @CrossDecompilerTest
    void equivalent() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
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

    @CrossDecompilerTest
    void complexCondition() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                do {
                    value += 2;
                } while (++value < 4 || ++value % 3 == 0);

                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakNoLabel() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
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

    @CrossDecompilerTest
    void breakInfinite() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
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

    @CrossDecompilerTest
    void continueNoLabel() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
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

    @CrossDecompilerTest
    void continueAndBreak() {
        verify(new TestCode.IntParam() {

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

    @CrossDecompilerTest
    void oneLiner() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                // @formatter:off
                do {value+= 2;} while (value < 3);
                // @formatter:on
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void oneLinerComplexCondition() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                // @formatter:off
                do {value += 2;} while (++value < 10 && value % 2 == 0);
                // @formatter:on
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void inIf() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                if (value != 3) {
                    do {
                        value += 2;
                    } while (value < 5);
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void inIfOneLiner() {
        verify(new TestCode.IntParam() {

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

    @CrossDecompilerTest
    void inOneLinerIf() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                // @formatter:off
                if (value != 0) do {value += 2;} while (value < 3);
                // @formatter:on
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void continueThenFollow() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 10) int value) {
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

    @CrossDecompilerTest
    void withWhile() {
        verify(new TestCode.IntParam() {

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

    @CrossDecompilerTest
    void nestContinueJump() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 10) int value) {
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

    @CrossDecompilerTest
    void nestBreakJump() {
        verify(new TestCode.IntParam() {

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