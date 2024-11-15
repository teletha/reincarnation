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

public class DoubleArrayTest extends CodeVerifier {

    @CrossDecompilerTest
    public void base() {
        verify(new TestCode.DoubleArray() {

            @Override
            public double[] run() {
                double[] array = new double[2];
                array[0] = 0;
                array[1] = 1;

                return array;
            }
        });
    }

    @CrossDecompilerTest
    public void multipleAssign() throws Exception {
        verify(new TestCode.DoubleArray() {

            @Override
            public double[] run() {
                double[] array = new double[3];
                array[0] = array[1] = array[2] = 1;

                return array;
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayWithExpression() {
        verify(new TestCode.DoubleArray() {

            private double field = 1;

            @Override
            public double[] run() {
                double[] array = new double[2];
                array[0] = field;
                array[1] = field + 1;

                return array;
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayByShorthand() {
        verify(new TestCode.DoubleArray() {

            @Override

            public double[] run() {
                return new double[] {1, 0};
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayByShorthandWithDeclaration() {
        verify(new TestCode.Double() {

            @Override
            public double run() {
                double[] array = {1.0D, 0.0D}; // LLV
                return array[0] + array[1];
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayByShorthandWithAllFalse() {
        verify(new TestCode.DoubleArray() {

            @Override
            public double[] run() {
                return new double[] {0, 0};
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayWithExpressionByShorthand() {
        verify(new TestCode.DoubleArray() {

            private double field = 1;

            @Override
            public double[] run() {
                return new double[] {field, field + 1};
            }
        });
    }

    @CrossDecompilerTest
    public void ArraySoMany() {
        verify(new TestCode.DoubleArray() {

            @Override
            public double[] run() {
                return new double[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, -1, -2};
            }
        });
    }

    @CrossDecompilerTest
    public void MultiDimensionArray() {
        verify(new TestCode.Object<double[][]>() {

            @Override
            public double[][] run() {
                double[][] array = new double[3][2];
                array[0] = new double[] {1, 2};
                array[1] = new double[] {3, 4};
                array[2] = new double[] {5, 6};

                return array;
            }
        });
    }

    @CrossDecompilerTest
    public void MultiDimensionArrayByShorthand() {
        verify(new TestCode.Object<double[][]>() {

            @Override
            public double[][] run() {
                return new double[][] {{1, 2}, {3, 4}, {5, 6}};
            }
        });
    }

    @CrossDecompilerTest
    public void ThreeDimensionArray() {
        verify(new TestCode.Object<double[][][]>() {

            @Override
            public double[][][] run() {
                double[][][] array = new double[2][3][1];
                array[0] = new double[][] {{1}, {2}, {3}};
                array[1] = new double[][] {{5}, {6}, {7, 8, 9}};

                return array;
            }
        });
    }

    @CrossDecompilerTest
    public void ThreeDimensionArrayWithoutNeedlessDeclaration() {
        verify(new TestCode.Object<double[][][]>() {

            @Override
            public double[][][] run() {
                double[][][] array = new double[2][][];
                array[0] = new double[][] {{1}, {2}, {3}};
                array[1] = new double[][] {{4}, {5}, {6, 7, 8}};

                return array;
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayAccess() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                double[] array = {1, value};

                return array[1];
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayLength() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(@Param(doubles = {0, 1, 10}) double value) {
                return new double[(int) Math.round(value)].length;
            }
        });
    }

    @CrossDecompilerTest
    public void ArrayFor() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                double sum = 0;
                double[] array = {1, 2, 3};

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
        verify(new TestCode.DoubleParam() {
            @Override
            public double run(double value) {
                double sum = 0;
                double[] array = {1, 2, 3};

                for (double item : array) { // LLV
                    if (item == 1) {
                        sum++;
                    }
                }
                return sum;
            }
        });
    }
}