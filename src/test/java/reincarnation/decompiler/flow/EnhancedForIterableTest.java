/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.flow;

import java.util.List;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.TestCode;

class EnhancedForIterableTest extends CodeVerifier {

    @Test
    void normal() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                List<Integer> list = List.of(1, 2, 3);
                for (int item : list) {
                    value += item;
                }
                return value;
            }
        });
    }

    @Test
    void noneReturnCodeAfterLoopWillConfuseCompiler() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                int value = 0;
                List<Integer> list = List.of(1, 2, 3);
                for (int item : list) {
                    value += item;
                }
                String.valueOf(value); // noise

                return String.valueOf(value);
            }
        });
    }

    @Test
    void breakNoLabel() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 8, to = 10) int value) {
                List<Integer> list = List.of(1, 2, 3);
                for (int item : list) {
                    if (item == 2) {
                        break;
                    }
                    value += item;
                }
                return value;
            }
        });
    }

    @Test
    void breakInIf() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                List<Integer> list = List.of(1, 2, 3);
                for (int item : list)
                    if (item == 2) {
                        break;
                    } else {
                        value += item;
                    }

                return value;
            }
        });
    }

    @Test
    void breakInOneLinerIf() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                List<Integer> list = List.of(1, 2, 3);
                for (int item : list)
                    if (item % 2 == 0) break;

                return value;
            }
        });
    }
}