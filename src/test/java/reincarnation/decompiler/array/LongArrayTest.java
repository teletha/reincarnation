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

public class LongArrayTest extends CodeVerifier {

    @CrossDecompilerTest
    public void base() {
        verify(new TestCode.LongArray() {

            @Override
            public long[] run() {
                long[] array = new long[2];
                array[0] = 0;
                array[1] = 1;

                return array;
            }
        });
    }

    @CrossDecompilerTest
    public void multipleAssign() throws Exception {
        verify(new TestCode.LongArray() {

            @Override
            public long[] run() {
                long[] array = new long[3];
                array[0] = array[1] = array[2] = 1;

                return array;
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayWithExpression() {
        verify(new TestCode.LongArray() {

            private long field = 1;

            @Override
            public long[] run() {
                long[] array = new long[2];
                array[0] = field;
                array[1] = field + 1;

                return array;
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayByShorthand() {
        verify(new TestCode.LongArray() {

            @Override

            public long[] run() {
                return new long[] {1, 0};
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayByShorthandWithAllFalse() {
        verify(new TestCode.LongArray() {

            @Override
            public long[] run() {
                return new long[] {0, 0};
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayWithExpressionByShorthand() {
        verify(new TestCode.LongArray() {

            private long field = 1;

            @Override
            public long[] run() {
                return new long[] {field, field + 1};
            }
        });
    }

    @CrossDecompilerTest
    public void ArraySoMany() {
        verify(new TestCode.LongArray() {

            @Override
            public long[] run() {
                return new long[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, -1, -2};
            }
        });
    }

    @CrossDecompilerTest
    public void MultiDimensionArray() {
        verify(new TestCode.Object<long[][]>() {

            @Override
            public long[][] run() {
                long[][] array = new long[3][2];
                array[0] = new long[] {1, 2};
                array[1] = new long[] {3, 4};
                array[2] = new long[] {5, 6};

                return array;
            }
        });
    }

    @CrossDecompilerTest
    public void MultiDimensionArrayByShorthand() {
        verify(new TestCode.Object<long[][]>() {

            @Override
            public long[][] run() {
                return new long[][] {{1, 2}, {3, 4}, {5, 6}};
            }
        });
    }

    @CrossDecompilerTest
    public void ThreeDimensionArray() {
        verify(new TestCode.Object<long[][][]>() {

            @Override
            public long[][][] run() {
                long[][][] array = new long[2][3][1];
                array[0] = new long[][] {{1}, {2}, {3}};
                array[1] = new long[][] {{5}, {6}, {7, 8, 9}};

                return array;
            }
        });
    }

    @CrossDecompilerTest
    public void ThreeDimensionArrayWithoutNeedlessDeclaration() {
        verify(new TestCode.Object<long[][][]>() {

            @Override
            public long[][][] run() {
                long[][][] array = new long[2][][];
                array[0] = new long[][] {{1}, {2}, {3}};
                array[1] = new long[][] {{4}, {5}, {6, 7, 8}};

                return array;
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayAccess() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                long[] array = {1, value};

                return array[1];
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayLength() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(@Param(longs = {0, 1, 10}) long value) {
                return new long[(int) value].length;
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayFor() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                long sum = 0;
                long[] array = {1, 2, 3};

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
        verify(new TestCode.LongParam() {
            @Override
            public long run(long value) {
                long sum = 0;
                long[] array = {1, 2, 3};

                for (long i : array) {
                    if (i == 1) {
                        sum++;
                    }
                }
                return sum;
            }
        });
    }
}