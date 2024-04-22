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
import reincarnation.DecompilableTest;
import reincarnation.TestCode;

class DoubleTest extends CodeVerifier {

    @DecompilableTest
    void zero() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return 0d;
            }
        });
    }

    @DecompilableTest
    void one() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return 1d;
            }
        });
    }

    @DecompilableTest
    void two() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return 2d;
            }
        });
    }

    @DecompilableTest
    void three() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return 3d;
            }
        });
    }

    @DecompilableTest
    void minus() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return -1d;
            }
        });
    }

    @DecompilableTest
    void max() {
        verify(new TestCode.Double() {

            @Override
            public double run() {
                return java.lang.Double.MAX_VALUE;
            }
        });
    }

    @DecompilableTest
    void min() {
        verify(new TestCode.Double() {

            @Override
            public double run() {
                return java.lang.Double.MIN_VALUE;
            }
        });
    }

    @DecompilableTest
    void add() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value + 1d;
            }
        });
    }

    @DecompilableTest
    void addAssignable() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value += 2d;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void subtract() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value - 1d;
            }
        });
    }

    @DecompilableTest
    void subtractAssignable() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value -= 2d;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void multiply() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value * 2d;
            }
        });
    }

    @DecompilableTest
    void multiplyAssignable() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value *= 2d;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void divide() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value / 2d;
            }
        });
    }

    @DecompilableTest
    void divideAssignable() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value /= 2d;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void modulo() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value % 2d;
            }
        });
    }

    @DecompilableTest
    void moduloAssignable() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value %= 2d;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void postIncrement() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value++;
            }
        });
    }

    @DecompilableTest
    void postIncrementValue() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                double next = value++;
                return value + next;
            }
        });
    }

    @DecompilableTest
    void postIncrementLike() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value + 1;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void preIncrement() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return ++value;
            }
        });
    }

    @DecompilableTest
    void preIncrementValue() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                double next = ++value;
                return value + next;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void equal() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return value == 0;
            }
        });
    }

    @DecompilableTest
    void notEqual() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return value != 0; // CMPL IFEQ
            }
        });
    }

    @DecompilableTest
    void less() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return value < 1; // CMPG IFGE
            }
        });
    }

    @DecompilableTest
    void lessEqual() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return value <= 1; // CMPG IFGT
            }
        });
    }

    @DecompilableTest
    void lessEqual2() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return 1 >= value; // CMPL IFLT
            }
        });
    }

    @DecompilableTest
    void greater() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return value > 1; // CMPL IFLE
            }
        });
    }

    @DecompilableTest
    void greater2() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return 1 < value; // CMPG IFGE
            }
        });
    }

    @DecompilableTest
    void greaterEqual() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return value >= 1; // CMPL IFLT
            }
        });
    }

    @DecompilableTest
    void greaterEqual2() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return 1 <= value; // CMPG IFGT
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
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

    @DecompilableTest
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

    @DecompilableTest
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

    @DecompilableTest
    void classEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return double.class == double.class;
            }
        });
    }

    @DecompilableTest
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