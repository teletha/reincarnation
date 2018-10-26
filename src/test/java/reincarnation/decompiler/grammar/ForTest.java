/*
 * Copyright (C) 2016 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.grammar;

import org.junit.jupiter.api.Test;

import reincarnation.Code;
import reincarnation.CodeVerifier;

/**
 * @version 2018/10/26 16:10:31
 */
class ForTest extends CodeVerifier {

    @Test
    void normal() {
        verify(new Code.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                for (int i = 0; i < 3; i++) {
                    value++;
                }

                return value;
            }
        });
    }

    @Test
    void withoutInitialize() {
        verify(new Code.IntParam() {

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

    @Test
    void withoutUpdate() {
        verify(new Code.IntParam() {

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

    @Test
    void multipleUpdate() throws Exception {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                for (int i = 0; i < 3; i++, value++) {
                    value += 2;
                }
                return value;
            }
        });
    }

    @Test
    void afterProcess() {
        verify(new Code.IntParam() {

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

    @Test
    void noneReturnCodeAfterLoopWillConfuseCompiler() throws Exception {
        verify(new Code.Text() {

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

    @Test
    void breakNoLabel() {
        verify(new Code.IntParam() {

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

    @Test
    void breakInIf() throws Exception {
        verify(new Code.IntParam() {

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

    @Test
    void breakInOneLinerIf() throws Exception {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                for (; value < 5; value++)
                    if (value != 0) break;

                return value;
            }
        });
    }

    @Test
    void continueNoLabel() {
        verify(new Code.IntParam() {

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

    @Test
    void continueNest() throws Exception {
        verify(new Code.IntParam() {

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

    @Test
    void continueMultiple() throws Exception {
        verify(new Code.IntParam() {

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

    @Test
    void continueInIf() throws Exception {
        verify(new Code.IntParam() {

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

    @Test
    void continueInShorthandIf() throws Exception {
        verify(new Code.IntParam() {

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

    @Test
    void continueInLogicalIf() throws Exception {
        verify(new Code.IntParam() {

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

    @Test
    void returnNest() throws Exception {
        verify(new Code.IntParam() {

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

    @Test
    void continueWithLogicalExpressionFail() throws Exception {
        verify(new Code.IntParam() {

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

    @Test
    void continueWithLogicalExpressionAndAfterProcess() throws Exception {
        verify(new Code.IntParam() {

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

    @Test
    void continueWithLogicalExpression() throws Exception {
        verify(new Code.IntParam() {

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

    @Test
    void returnInNest() throws Exception {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                if (value == 0) {
                    value++;
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

    @Test
    void complex() throws Exception {
        verify(new Code.IntParam() {

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

    @Test
    void infinite() throws Exception {
        verify(new Code.IntParam() {

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

    @Test
    void infiniteLikeFor() throws Exception {
        verify(new Code.IntParam() {

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

    @Test
    void infiniteLikeWhile() throws Exception {
        verify(new Code.IntParam() {

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

    @Test
    void infiniteWithWhile() throws Exception {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
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

    @Test
    void infiniteWithWhileNodes() throws Exception {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
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

    @Test
    void infiniteIfReturn() throws Exception {
        verify(new Code.IntParam() {

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

    @Test
    void infiniteIfElse() throws Exception {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
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
