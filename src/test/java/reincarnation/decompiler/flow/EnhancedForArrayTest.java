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

class EnhancedForArrayTest extends CodeVerifier {

    @CrossDecompilerTest
    void normal() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                int[] list = {1, 2, 3};
                for (int item : list) {
                    value += item;
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
                int value = 0;
                int[] list = {1, 2, 3};
                for (int item : list) {
                    value += item;
                }
                String.valueOf(value); // noise

                return String.valueOf(value);
            }
        });
    }

    @CrossDecompilerTest
    void breakNoLabel() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 8, to = 10) int value) {
                int[] list = {1, 2, 3};
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

    @CrossDecompilerTest
    void breakInIf() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                int[] list = {1, 2, 3};
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

    @CrossDecompilerTest
    void breakInOneLinerIf() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                int[] list = {1, 2, 3};
                for (int item : list)
                    if (item % 2 == 0) break;

                return value;
            }
        });
    }
}