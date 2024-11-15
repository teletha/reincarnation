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

public class FloatArrayTest extends CodeVerifier {

    @CrossDecompilerTest
    public void base() {
        verify(new TestCode.FloatArray() {

            @Override
            public float[] run() {
                float[] array = new float[2];
                array[0] = 0;
                array[1] = 1;

                return array;
            }
        });
    }

    @CrossDecompilerTest
    public void multipleAssign() throws Exception {
        verify(new TestCode.FloatArray() {

            @Override
            public float[] run() {
                float[] array = new float[3];
                array[0] = array[1] = array[2] = 1;

                return array;
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayWithExpression() {
        verify(new TestCode.FloatArray() {

            private float field = 1;

            @Override
            public float[] run() {
                float[] array = new float[2];
                array[0] = field;
                array[1] = field + 1;

                return array;
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayByShorthand() {
        verify(new TestCode.FloatArray() {

            @Override

            public float[] run() {
                return new float[] {1, 0};
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayByShorthandWithDeclaration() {
        verify(new TestCode.Float() {

            @Override
            public float run() {
                float[] array = {1.0F, 0.0F}; // LLV
                return array[0] + array[1];
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayByShorthandWithAllFalse() {
        verify(new TestCode.FloatArray() {

            @Override
            public float[] run() {
                return new float[] {0, 0};
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayWithExpressionByShorthand() {
        verify(new TestCode.FloatArray() {

            private float field = 1;

            @Override
            public float[] run() {
                return new float[] {field, field + 1};
            }
        });
    }

    @CrossDecompilerTest
    public void ArraySoMany() {
        verify(new TestCode.FloatArray() {

            @Override
            public float[] run() {
                return new float[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, -1, -2};
            }
        });
    }

    @CrossDecompilerTest
    public void MultiDimensionArray() {
        verify(new TestCode.Object<float[][]>() {

            @Override
            public float[][] run() {
                float[][] array = new float[3][2];
                array[0] = new float[] {1, 2};
                array[1] = new float[] {3, 4};
                array[2] = new float[] {5, 6};

                return array;
            }
        });
    }

    @CrossDecompilerTest
    public void MultiDimensionArrayByShorthand() {
        verify(new TestCode.Object<float[][]>() {

            @Override
            public float[][] run() {
                return new float[][] {{1, 2}, {3, 4}, {5, 6}};
            }
        });
    }

    @CrossDecompilerTest
    public void ThreeDimensionArray() {
        verify(new TestCode.Object<float[][][]>() {

            @Override
            public float[][][] run() {
                float[][][] array = new float[2][3][1];
                array[0] = new float[][] {{1}, {2}, {3}};
                array[1] = new float[][] {{5}, {6}, {7, 8, 9}};

                return array;
            }
        });
    }

    @CrossDecompilerTest
    public void ThreeDimensionArrayWithoutNeedlessDeclaration() {
        verify(new TestCode.Object<float[][][]>() {

            @Override
            public float[][][] run() {
                float[][][] array = new float[2][][];
                array[0] = new float[][] {{1}, {2}, {3}};
                array[1] = new float[][] {{4}, {5}, {6, 7, 8}};

                return array;
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayAccess() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                float[] array = {1, value};

                return array[1];
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayLength() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(@Param(floats = {0, 1, 10}) float value) {
                return new float[Math.round(value)].length;
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayFor() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                float sum = 0;
                float[] array = {1, 2, 3};

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
        verify(new TestCode.FloatParam() {
            @Override
            public float run(float value) {
                float sum = 0;
                float[] array = {1, 2, 3};

                for (float i : array) { // LLV
                    if (i == 1) {
                        sum++;
                    }
                }
                return sum;
            }
        });
    }
}