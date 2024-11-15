/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.array;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

public class IntArrayTest extends CodeVerifier {

    @CrossDecompilerTest
    public void base() {
        verify(new TestCode.IntArray() {

            @Override
            public int[] run() {
                int[] array = new int[2];
                array[0] = 0;
                array[1] = 1;

                return array;
            }
        });
    }

    @CrossDecompilerTest
    public void multipleAssign() throws Exception {
        verify(new TestCode.IntArray() {

            @Override
            public int[] run() {
                int[] array = new int[3];
                array[0] = array[1] = array[2] = 1;

                return array;
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayWithExpression() {
        verify(new TestCode.IntArray() {

            private int field = 1;

            @Override
            public int[] run() {
                int[] array = new int[2];
                array[0] = field;
                array[1] = field + 1;

                return array;
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayByShorthand() {
        verify(new TestCode.IntArray() {

            @Override

            public int[] run() {
                return new int[] {1, 0};
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayByShorthandWithAllFalse() {
        verify(new TestCode.IntArray() {

            @Override
            public int[] run() {
                return new int[] {0, 0};
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayWithExpressionByShorthand() {
        verify(new TestCode.IntArray() {

            private int field = 1;

            @Override
            public int[] run() {
                return new int[] {field, field + 1};
            }
        });
    }

    @CrossDecompilerTest
    public void ArraySoMany() {
        verify(new TestCode.IntArray() {

            @Override
            public int[] run() {
                return new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, -1, -2};
            }
        });
    }

    @CrossDecompilerTest
    public void MultiDimensionArray() {
        verify(new TestCode.Object<int[][]>() {

            @Override
            public int[][] run() {
                int[][] array = new int[3][2];
                array[0] = new int[] {1, 2};
                array[1] = new int[] {3, 4};
                array[2] = new int[] {5, 6};

                return array;
            }
        });
    }

    @CrossDecompilerTest
    public void MultiDimensionArrayByShorthand() {
        verify(new TestCode.Object<int[][]>() {

            @Override
            public int[][] run() {
                return new int[][] {{1, 2}, {3, 4}, {5, 6}};
            }
        });
    }

    @CrossDecompilerTest
    public void ThreeDimensionArray() {
        verify(new TestCode.Object<int[][][]>() {

            @Override
            public int[][][] run() {
                int[][][] array = new int[2][3][1];
                array[0] = new int[][] {{1}, {2}, {3}};
                array[1] = new int[][] {{5}, {6}, {7, 8, 9}};

                return array;
            }
        });
    }

    @CrossDecompilerTest
    public void ThreeDimensionArrayWithoutNeedlessDeclaration() {
        verify(new TestCode.Object<int[][][]>() {

            @Override
            public int[][][] run() {
                int[][][] array = new int[2][][];
                array[0] = new int[][] {{1}, {2}, {3}};
                array[1] = new int[][] {{4}, {5}, {6, 7, 8}};

                return array;
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayAccess() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                int[] array = {1, value};

                return array[1];
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayLength() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(ints = {0, 1, 10}) int value) {
                return new int[value].length;
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayFor() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                int sum = 0;
                int[] array = {1, 2, 3};

                for (int i = 0; i < array.length; i++) {
                    if (array[i] == 1) {
                        sum++;
                    }
                }
                return sum;
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayForEach() {
        verify(new TestCode.IntParam() {
            @Override
            public int run(int value) {
                int sum = 0;
                int[] array = {1, 2, 3};

                for (int i : array) { // LLV
                    if (i == 1) {
                        sum++;
                    }
                }
                return sum;
            }
        });
    }
}