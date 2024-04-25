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

class ForTest extends CodeVerifier {

    @CrossDecompilerTest
    void normal() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                for (int i = 0; i < 3; i++) {
                    value++;
                }

                return value;
            }
        });
    }

    @CrossDecompilerTest
    void multipleInitializer() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                for (int i = 0, j = 10; i < j; i++) {
                    value++;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void withoutInitialize() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                int i = 0;
                value++;

                for (; i < 3; i++) {
                    value++;
                }

                return value;
            }
        });
    }

    @CrossDecompilerTest
    void withoutUpdate() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                for (int i = 0; i < 8;) {
                    i = value;
                    value += 2;

                    if (value == 5) {
                        break;
                    }
                }

                return value;
            }
        });
    }

    @CrossDecompilerTest
    void multipleUpdate() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                for (int i = 0; i < 3; i++, value++) {
                    value += 2;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void afterProcess() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                String name = "act";

                for (int i = 0; i < name.length(); i++) {
                    value++;
                }

                return value;
            }
        });
    }

    @CrossDecompilerTest
    void noneReturnCodeAfterLoopWillConfuseCompiler() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                int m = 0;

                for (int i = 0; i < 3; i++) {
                    m++;
                }
                String.valueOf(m); // noise

                return String.valueOf(m);
            }
        });
    }

    @CrossDecompilerTest
    void breakNoLabel() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 8, to = 10) int value) {
                for (int i = 0; i < 3; i++) {
                    value++;

                    if (value == 10) {
                        value += 10;
                        break;
                    }
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakInIf() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                for (; value < 5; value++)
                    if (value != 0) {
                        break;
                    }

                return value;
            }
        });
    }

    @CrossDecompilerTest
    void breakInOneLinerIf() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                for (; value < 5; value++)
                    if (value != 0) break;

                return value;
            }
        });
    }

    @CrossDecompilerTest
    void continueNoLabel() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 1, to = 10) int value) {
                for (int i = 0; i < 3; i++) {
                    value++;

                    if (value % 2 == 0) {
                        continue;
                    }
                    value += 3;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void continueNest() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                root: for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (i == 1) {
                            continue root;
                        }
                        value += 2;
                    }
                    value += 3;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void continueMultiple() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                root: for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (i == 1) {
                            continue root;
                        }
                        value++;

                        if (i == 2) {
                            continue root;
                        }
                        value++;
                    }
                    value++;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void continueInIf() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                for (; value < 2; value++) {
                    if (value % 2 == 0) {
                        continue;
                    }
                    value += 3;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void continueInShorthandIf() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                for (; value < 2; value++) {
                    if (value % 2 == 0) continue;
                    value += 3;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void continueInLogicalIf() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                for (; value < 2; value++) {
                    if (value == 0 || value == -1) {
                        continue;
                    }
                    value += 3;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void returnNest() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (i == 1) {
                            return 100;
                        }
                        value++;
                    }
                    value++;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void continueWithLogicalExpressionFail() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                root: for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 4; j++) {
                        value++;

                        if ((i + j) % 3 == 0) {
                            continue root;
                        }
                    }
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void continueWithLogicalExpressionAndAfterProcess() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                root: for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 4; j++) {
                        if ((i + j) % 3 == 0) {
                            continue root;
                        }
                        value++;
                    }
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void continueWithLogicalExpression() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                for (int i = 0; i < 3; i++) {
                    value++;

                    if (i == -1 || value % 5 == 0) {
                        continue;
                    }
                    value++;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void returnInNest() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                if (value == 0) {
                    value += 2;
                } else {
                    for (int i = 0; i < 3; i++) {
                        value++;

                        if (i == -1 || value % 5 == 0) {
                            return 1000;
                        }
                    }
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void complex() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                for (int j = 0; j < 2; j++) {
                    if (value % 2 == 0) {
                        if (value % 3 == 0) {
                            value += 100;
                        } else if (value % 4 == 0) {
                            value += 200;
                        }
                    }
                    value += 2;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void infinite() {
        verify(new TestCode.IntParam() {

            @Override

            public int run(int value) {
                for (;;) {
                    value++;

                    if (value % 5 == 0) {
                        return value;
                    } else {
                        if (value % 9 == 0) {
                            return value;
                        } else {
                            value++;
                        }
                    }
                }
            }
        });
    }

    @CrossDecompilerTest
    void infiniteLikeFor() {
        verify(new TestCode.IntParam() {

            @Override

            public int run(int value) {
                for (;;) {
                    if (value < 5) {
                        value += 3;
                    }

                    if (value % 7 == 0) {
                        return value;
                    }
                    value++;
                }
            }
        });
    }

    @CrossDecompilerTest
    void infiniteLikeWhile() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                for (;;) {
                    if (value < 5) {
                        value += 3;
                    }

                    value++;

                    if (value % 7 == 0) {
                        return value;
                    }
                }
            }
        });
    }

    @CrossDecompilerTest
    void infiniteWithWhile() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -10, to = 10) int value) {
                int counter = 1;

                for (;;) {
                    while (value < 2) {
                        value++;
                        counter++;
                    }
                    value += counter;

                    if (10 < value) {
                        return value;
                    }
                }
            }
        });
    }

    @CrossDecompilerTest
    void infiniteWithWhileNodes() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -10, to = 10) int value) {
                for (;;) {
                    while (value < 5) {
                        if (value % 2 == 0) {
                            return 100;
                        }
                        value += 3;
                    }
                    value++;

                    if (value % 7 == 0) {
                        return value;
                    }
                }
            }
        });
    }

    @CrossDecompilerTest
    void infiniteIfReturn() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                for (;;) {
                    if (value % 7 == 0) {
                        return value;
                    }
                    value++;
                    if (value % 3 == 0) {
                        return value;
                    }
                    value++;
                }
            }
        });
    }

    @CrossDecompilerTest
    void infiniteIfElse() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -10, to = 10) int value) {
                for (;;) {
                    if (0 < value) {
                        value++;
                    } else {
                        value--;
                    }
                    if (value % 3 == 0) {
                        return value;
                    }
                }
            }
        });
    }
}