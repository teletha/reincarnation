/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.primitives;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class DoubleTest extends CodeVerifier {

    @CrossDecompilerTest
    void zero() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return 0d;
            }
        });
    }

    @CrossDecompilerTest
    void one() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return 1d;
            }
        });
    }

    @CrossDecompilerTest
    void two() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return 2d;
            }
        });
    }

    @CrossDecompilerTest
    void three() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return 3d;
            }
        });
    }

    @CrossDecompilerTest
    void minus() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return -1d;
            }
        });
    }

    @CrossDecompilerTest
    void max() {
        verify(new TestCode.Double() {

            @Override
            public double run() {
                return java.lang.Double.MAX_VALUE;
            }
        });
    }

    @CrossDecompilerTest
    void min() {
        verify(new TestCode.Double() {

            @Override
            public double run() {
                return java.lang.Double.MIN_VALUE;
            }
        });
    }

    @CrossDecompilerTest
    void add() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value + 1d;
            }
        });
    }

    @CrossDecompilerTest
    void addAssignable() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value += 2d;
            }
        });
    }

    @CrossDecompilerTest
    void addAssignableOnParameter() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value(value += 2d);
            }

            private double value(double value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void subtract() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value - 1d;
            }
        });
    }

    @CrossDecompilerTest
    void subtractAssignable() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value -= 2d;
            }
        });
    }

    @CrossDecompilerTest
    void subtractAssignableOnParameter() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value(value -= 2d);
            }

            private double value(double value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void multiply() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value * 2d;
            }
        });
    }

    @CrossDecompilerTest
    void multiplyAssignable() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value *= 2d;
            }
        });
    }

    @CrossDecompilerTest
    void multipleAssignableOnParameter() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value(value *= 2d);
            }

            private double value(double value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void divide() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value / 2d;
            }
        });
    }

    @CrossDecompilerTest
    void divideAssignable() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value /= 2d;
            }
        });
    }

    @CrossDecompilerTest
    void divideAssignableOnParameter() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value(value /= 2d);
            }

            private double value(double value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void modulo() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value % 2d;
            }
        });
    }

    @CrossDecompilerTest
    void moduloAssignable() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value %= 2d;
            }
        });
    }

    @CrossDecompilerTest
    void moduloAssignableOnParameter() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value(value %= 2d);
            }

            private double value(double value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void postIncrement() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value++;
            }
        });
    }

    @CrossDecompilerTest
    void postIncrementValue() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                double next = value++;
                return value + next;
            }
        });
    }

    @CrossDecompilerTest
    void postIncrementLike() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value + 1;
            }
        });
    }

    @CrossDecompilerTest
    void postIncrementOnParameter() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value(value++);
            }

            private double value(double value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void preIncrement() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return ++value;
            }
        });
    }

    @CrossDecompilerTest
    void preIncrementValue() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                double next = ++value;
                return value + next;
            }
        });
    }

    @CrossDecompilerTest
    void preIncrementOnParameter() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value(++value);
            }

            private double value(double value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void equal() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return value == 0;
            }
        });
    }

    @CrossDecompilerTest
    void notEqual() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return value != 0; // CMPL IFEQ
            }
        });
    }

    @CrossDecompilerTest
    void less() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return value < 1; // CMPG IFGE
            }
        });
    }

    @CrossDecompilerTest
    void lessEqual() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return value <= 1; // CMPG IFGT
            }
        });
    }

    @CrossDecompilerTest
    void lessEqual2() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return 1 >= value; // CMPL IFLT
            }
        });
    }

    @CrossDecompilerTest
    void greater() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return value > 1; // CMPL IFLE
            }
        });
    }

    @CrossDecompilerTest
    void greater2() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return 1 < value; // CMPG IFGE
            }
        });
    }

    @CrossDecompilerTest
    void greaterEqual() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return value >= 1; // CMPL IFLT
            }
        });
    }

    @CrossDecompilerTest
    void greaterEqual2() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return 1 <= value; // CMPG IFGT
            }
        });
    }

    @CrossDecompilerTest
    void incrementStatiFieldInFieldAccess() throws Exception {
        verify(new IncrementStaticField());
    }

    /**
     * @version 2018/10/10 9:38:09
     */
    private static class IncrementStaticField implements TestCode.Double {

        private static double index = 1;

        private static double count = 2;

        @Override
        public double run() {
            index = count++;

            return count + index * 10;
        }
    }

    @CrossDecompilerTest
    void decrementStatiFieldInFieldAccess() throws Exception {
        verify(new DecrementStaticField());
    }

    /**
     * @version 2018/10/10 9:37:58
     */
    private static class DecrementStaticField implements TestCode.Double {

        private static double index = 1;

        private static double count = 2;

        @Override
        public double run() {
            index = count--;

            return count + index * 10;
        }
    }

    @CrossDecompilerTest
    void preincrementStatiFieldInFieldAccess() throws Exception {
        verify(new PreincrementStaticField());
    }

    /**
     * @version 2018/10/10 9:38:01
     */
    private static class PreincrementStaticField implements TestCode.Double {

        private static double index = 1;

        private static double count = 2;

        @Override
        public double run() {
            index = ++count;

            return count + index * 10;
        }
    }

    @CrossDecompilerTest
    void predecrementStatiFieldInFieldAccess() throws Exception {
        verify(new PredecrementStaticField());
    }

    /**
     * @version 2018/10/10 9:38:04
     */
    private static class PredecrementStaticField implements TestCode.Double {

        private static double index = 1;

        private static double count = 2;

        @Override
        public double run() {
            index = --count;

            return count + index * 10;
        }
    }

    @CrossDecompilerTest
    void classEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return double.class == double.class;
            }
        });
    }

    @CrossDecompilerTest
    void arrayClassEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                double[] array = {};
                return double[].class == array.getClass();
            }
        });
    }
}