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
import reincarnation.CompilableTest;
import reincarnation.TestCode;

class DoubleTest extends CodeVerifier {

    @CompilableTest
    void zero() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return 0d;
            }
        });
    }

    @CompilableTest
    void one() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return 1d;
            }
        });
    }

    @CompilableTest
    void two() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return 2d;
            }
        });
    }

    @CompilableTest
    void three() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return 3d;
            }
        });
    }

    @CompilableTest
    void minus() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return -1d;
            }
        });
    }

    @CompilableTest
    void max() {
        verify(new TestCode.Double() {

            @Override
            public double run() {
                return java.lang.Double.MAX_VALUE;
            }
        });
    }

    @CompilableTest
    void min() {
        verify(new TestCode.Double() {

            @Override
            public double run() {
                return java.lang.Double.MIN_VALUE;
            }
        });
    }

    @CompilableTest
    void add() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value + 1d;
            }
        });
    }

    @CompilableTest
    void addAssignable() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value += 2d;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void subtract() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value - 1d;
            }
        });
    }

    @CompilableTest
    void subtractAssignable() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value -= 2d;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void multiply() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value * 2d;
            }
        });
    }

    @CompilableTest
    void multiplyAssignable() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value *= 2d;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void divide() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value / 2d;
            }
        });
    }

    @CompilableTest
    void divideAssignable() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value /= 2d;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void modulo() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value % 2d;
            }
        });
    }

    @CompilableTest
    void moduloAssignable() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value %= 2d;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void postIncrement() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value++;
            }
        });
    }

    @CompilableTest
    void postIncrementValue() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                double next = value++;
                return value + next;
            }
        });
    }

    @CompilableTest
    void postIncrementLike() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value + 1;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void preIncrement() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return ++value;
            }
        });
    }

    @CompilableTest
    void preIncrementValue() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                double next = ++value;
                return value + next;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void equal() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return value == 0;
            }
        });
    }

    @CompilableTest
    void notEqual() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return value != 0; // CMPL IFEQ
            }
        });
    }

    @CompilableTest
    void less() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return value < 1; // CMPG IFGE
            }
        });
    }

    @CompilableTest
    void lessEqual() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return value <= 1; // CMPG IFGT
            }
        });
    }

    @CompilableTest
    void lessEqual2() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return 1 >= value; // CMPL IFLT
            }
        });
    }

    @CompilableTest
    void greater() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return value > 1; // CMPL IFLE
            }
        });
    }

    @CompilableTest
    void greater2() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return 1 < value; // CMPG IFGE
            }
        });
    }

    @CompilableTest
    void greaterEqual() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return value >= 1; // CMPL IFLT
            }
        });
    }

    @CompilableTest
    void greaterEqual2() {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return 1 <= value; // CMPG IFGT
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
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

    @CompilableTest
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

    @CompilableTest
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

    @CompilableTest
    void classEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return double.class == double.class;
            }
        });
    }

    @CompilableTest
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