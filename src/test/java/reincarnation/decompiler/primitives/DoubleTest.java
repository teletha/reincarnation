/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.primitives;

import org.junit.jupiter.api.Test;

import reincarnation.Code;
import reincarnation.CodeVerifier;

/**
 * @version 2018/10/10 9:35:58
 */
class DoubleTest extends CodeVerifier {

    @Test
    void zero() {
        verify(new Code.DoubleParam() {

            @Override
            public double run(double value) {
                return 0;
            }
        });
    }

    @Test
    void one() {
        verify(new Code.DoubleParam() {

            @Override
            public double run(double value) {
                return 1;
            }
        });
    }

    @Test
    void two() {
        verify(new Code.DoubleParam() {

            @Override
            public double run(double value) {
                return 2;
            }
        });
    }

    @Test
    void three() {
        verify(new Code.DoubleParam() {

            @Override
            public double run(double value) {
                return 3;
            }
        });
    }

    @Test
    void minus() {
        verify(new Code.DoubleParam() {

            @Override
            public double run(double value) {
                return -1;
            }
        });
    }

    @Test
    void max() {
        verify(new Code.Double() {

            @Override
            public double run() {
                return java.lang.Double.MAX_VALUE;
            }
        });
    }

    @Test
    void min() {
        verify(new Code.Double() {

            @Override
            public double run() {
                return java.lang.Double.MIN_VALUE;
            }
        });
    }

    @Test
    void add() {
        verify(new Code.DoubleParam() {

            @Override
            public double run(double value) {
                return value + 1;
            }
        });
    }

    @Test
    void addAssignable() {
        verify(new Code.DoubleParam() {

            @Override
            public double run(double value) {
                return value += 2;
            }
        });
    }

    @Test
    void subtract() {
        verify(new Code.DoubleParam() {

            @Override
            public double run(double value) {
                return value - 1;
            }
        });
    }

    @Test
    void subtractAssignable() {
        verify(new Code.DoubleParam() {

            @Override
            public double run(double value) {
                return value -= 2;
            }
        });
    }

    @Test
    void multiply() {
        verify(new Code.DoubleParam() {

            @Override
            public double run(double value) {
                return value * 2;
            }
        });
    }

    @Test
    void multiplyAssignable() {
        verify(new Code.DoubleParam() {

            @Override
            public double run(double value) {
                return value *= 2;
            }
        });
    }

    @Test
    void divide() {
        verify(new Code.DoubleParam() {

            @Override
            public double run(double value) {
                return value / 2;
            }
        });
    }

    @Test
    void divideAssignable() {
        verify(new Code.DoubleParam() {

            @Override
            public double run(double value) {
                return value /= 2;
            }
        });
    }

    @Test
    void modulo() {
        verify(new Code.DoubleParam() {

            @Override
            public double run(double value) {
                return value % 2;
            }
        });
    }

    @Test
    void moduloAssignable() {
        verify(new Code.DoubleParam() {

            @Override
            public double run(double value) {
                return value %= 2;
            }
        });
    }

    @Test
    void postIncrement() {
        verify(new Code.DoubleParam() {

            @Override
            public double run(double value) {
                return value++;
            }
        });
    }

    @Test
    void postIncrementValue() {
        verify(new Code.DoubleParam() {

            @Override
            public double run(double value) {
                double next = value++;
                return value + next;
            }
        });
    }

    @Test
    void postIncrementLike() {
        verify(new Code.DoubleParam() {

            @Override
            public double run(double value) {
                return value + 1;
            }
        });
    }

    @Test
    void preIncrement() {
        verify(new Code.DoubleParam() {

            @Override
            public double run(double value) {
                return ++value;
            }
        });
    }

    @Test
    void equal() {
        verify(new Code.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return value == 0;
            }
        });
    }

    @Test
    void notEqual() {
        verify(new Code.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return value != 0; // CMPL IFEQ
            }
        });
    }

    @Test
    void less() {
        verify(new Code.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return value < 1; // CMPG IFGE
            }
        });
    }

    @Test
    void lessEqual() {
        verify(new Code.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return value <= 1; // CMPG IFGT
            }
        });
    }

    @Test
    void lessEqual2() {
        verify(new Code.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return 1 >= value; // CMPL IFLT
            }
        });
    }

    @Test
    void greater() {
        verify(new Code.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return value > 1; // CMPL IFLE
            }
        });
    }

    @Test
    void greater2() {
        verify(new Code.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return 1 < value; // CMPG IFGE
            }
        });
    }

    @Test
    void greaterEqual() {
        verify(new Code.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return value >= 1; // CMPL IFLT
            }
        });
    }

    @Test
    void greaterEqual2() {
        verify(new Code.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return 1 <= value; // CMPG IFGT
            }
        });
    }

    @Test
    void incrementStatiFieldInFieldAccess() throws Exception {
        verify(new IncrementStaticField());
    }

    /**
     * @version 2018/10/10 9:38:09
     */
    private static class IncrementStaticField implements Code.Double {

        private static double index = 1;

        private static double count = 2;

        @Override
        public double run() {
            index = count++;

            return count + index * 10;
        }
    }

    @Test
    void decrementStatiFieldInFieldAccess() throws Exception {
        verify(new DecrementStaticField());
    }

    /**
     * @version 2018/10/10 9:37:58
     */
    private static class DecrementStaticField implements Code.Double {

        private static double index = 1;

        private static double count = 2;

        @Override
        public double run() {
            index = count--;

            return count + index * 10;
        }
    }

    @Test
    void preincrementStatiFieldInFieldAccess() throws Exception {
        verify(new PreincrementStaticField());
    }

    /**
     * @version 2018/10/10 9:38:01
     */
    private static class PreincrementStaticField implements Code.Double {

        private static double index = 1;

        private static double count = 2;

        @Override
        public double run() {
            index = ++count;

            return count + index * 10;
        }
    }

    @Test
    void predecrementStatiFieldInFieldAccess() throws Exception {
        verify(new PredecrementStaticField());
    }

    /**
     * @version 2018/10/10 9:38:04
     */
    private static class PredecrementStaticField implements Code.Double {

        private static double index = 1;

        private static double count = 2;

        @Override
        public double run() {
            index = --count;

            return count + index * 10;
        }
    }
}
