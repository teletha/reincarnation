/*
 * Copyright (C) 2019 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.array;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.TestCode;

/**
 * @version 2018/10/09 9:25:34
 */
public class BooleanArrayTest extends CodeVerifier {

    @Test
    public void base() {
        verify(new TestCode.BooleanArray() {

            @Override
            public boolean[] run() {
                boolean[] array = new boolean[2];
                array[0] = true;
                array[1] = false;

                return array;
            }
        });
    }

    @Test
    public void multipleAssign() throws Exception {
        verify(new TestCode.BooleanArray() {

            @Override
            public boolean[] run() {
                boolean[] array = new boolean[3];
                array[0] = array[1] = array[2] = true;

                return array;
            }
        });
    }

    @Test
    public void ArrayWithExpression() {
        verify(new TestCode.BooleanArray() {

            private boolean field = true;

            @Override
            public boolean[] run() {
                boolean[] array = new boolean[2];
                array[0] = field;
                array[1] = !field;

                return array;
            }
        });
    }

    @Test
    public void ArrayByShorthand() {
        verify(new TestCode.BooleanArray() {

            @Override

            public boolean[] run() {
                return new boolean[] {true, false};
            }
        });
    }

    @Test
    public void ArrayByShorthandWithAllFalse() {
        verify(new TestCode.BooleanArray() {

            @Override
            public boolean[] run() {
                return new boolean[] {false, false};
            }
        });
    }

    @Test
    public void ArrayWithExpressionByShorthand() {
        verify(new TestCode.BooleanArray() {

            private boolean field = true;

            @Override
            public boolean[] run() {
                return new boolean[] {field, !field};
            }
        });
    }

    @Test
    public void ArraySoMany() {
        verify(new TestCode.BooleanArray() {

            @Override
            public boolean[] run() {
                return new boolean[] {true, true, true, true, true, true, true, true, true, true, true, true};
            }
        });
    }

    @Test
    public void MultiDimensionArray() {
        verify(new TestCode.Object<boolean[][]>() {

            @Override
            public boolean[][] run() {
                boolean[][] array = new boolean[3][2];
                array[0] = new boolean[] {true, true};
                array[1] = new boolean[] {true, false};
                array[2] = new boolean[] {false, true};

                return array;
            }
        });
    }

    @Test
    public void MultiDimensionArrayByShorthand() {
        verify(new TestCode.Object<boolean[][]>() {

            @Override
            public boolean[][] run() {
                return new boolean[][] {{true, true}, {true, false}, {false, true}};
            }
        });
    }

    @Test
    public void ThreeDimensionArray() {
        verify(new TestCode.Object<boolean[][][]>() {

            @Override
            public boolean[][][] run() {
                boolean[][][] array = new boolean[2][3][1];
                array[0] = new boolean[][] {{true}, {false}, {true}};
                array[1] = new boolean[][] {{true}, {false}, {true, false, false}};

                return array;
            }
        });
    }

    @Test
    public void ThreeDimensionArrayWithoutNeedlessDeclaration() {
        verify(new TestCode.Object<boolean[][][]>() {

            @Override
            public boolean[][][] run() {
                boolean[][][] array = new boolean[2][][];
                array[0] = new boolean[][] {{true}, {false}, {true}};
                array[1] = new boolean[][] {{true}, {false}, {true, false, false}};

                return array;
            }
        });
    }

    @Test
    public void ArrayAccess() {
        verify(new TestCode.BooleanParam() {

            @Override
            public boolean run(boolean value) {
                boolean[] array = {false, value};

                return array[1];
            }
        });
    }

    @Test
    public void ArrayLength() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(ints = {0, 1, 10}) int value) {
                return new boolean[value].length;
            }
        });
    }

    @Test
    public void ArrayFor() {
        verify(new TestCode.BooleanParamInt() {

            @Override
            public int run(boolean value) {
                int sum = 0;
                boolean[] array = {true, false, true};

                for (int i = 0; i < array.length; i++) {
                    if (array[i]) {
                        sum++;
                    }
                }
                return sum;
            }
        });
    }

    @Test
    public void ArrayForEach() {
        verify(new TestCode.BooleanParamInt() {
            @Override
            public int run(boolean value) {
                int sum = 0;
                boolean[] array = {true, false, true};

                for (boolean i : array) {
                    if (i) {
                        sum++;
                    }
                }
                return sum;
            }
        });
    }
}
