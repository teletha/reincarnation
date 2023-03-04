/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.primitives;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.TestCode;

/**
 * @version 2018/10/10 11:02:56
 */
class FloatTest extends CodeVerifier {

    @Test
    void zero() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return 0;
            }
        });
    }

    @Test
    void one() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return 1;
            }
        });
    }

    @Test
    void two() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return 2;
            }
        });
    }

    @Test
    void three() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return 3;
            }
        });
    }

    @Test
    void minus() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return -1;
            }
        });
    }

    @Test
    void max() {
        verify(new TestCode.Float() {

            @Override
            public float run() {
                return java.lang.Float.MAX_VALUE;
            }
        });
    }

    @Test
    void min() {
        verify(new TestCode.Float() {

            @Override
            public float run() {
                return java.lang.Float.MIN_VALUE;
            }
        });
    }

    @Test
    void add() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value + 1;
            }
        });
    }

    @Test
    void addAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value += 2;
            }
        });
    }

    @Test
    void subtract() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value - 1;
            }
        });
    }

    @Test
    void subtractAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value -= 2;
            }
        });
    }

    @Test
    void multiply() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value * 2;
            }
        });
    }

    @Test
    void multiplyAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value *= 2;
            }
        });
    }

    @Test
    void divide() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value / 2;
            }
        });
    }

    @Test
    void divideAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value /= 2;
            }
        });
    }

    @Test
    void modulo() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value % 2;
            }
        });
    }

    @Test
    void moduloAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value %= 2;
            }
        });
    }

    @Test
    void postIncrement() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value++;
            }
        });
    }

    @Test
    void postIncrementValue() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                float next = value++;
                return value + next;
            }
        });
    }

    @Test
    void postIncrementLike() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value + 1;
            }
        });
    }

    @Test
    void preIncrement() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return ++value;
            }
        });
    }

    @Test
    void equal() {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return value == 0;
            }
        });
    }

    @Test
    void notEqual() {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return value != 0;
            }
        });
    }

    @Test
    void less() {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return value < 1;
            }
        });
    }

    @Test
    void lessEqual() {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return value <= 1;
            }
        });
    }

    @Test
    void greater() {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return value > 1;
            }
        });
    }

    @Test
    void greaterEqual() {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return value >= 1;
            }
        });
    }

    @Test
    void incrementStatiFieldInFieldAccess() {
        verify(new IncrementStaticField());
    }

    /**
     * @version 2018/10/10 11:03:49
     */
    private static class IncrementStaticField implements TestCode.Float {

        private static float index = 1;

        private static float count = 2;

        @Override
        public float run() {
            index = count++;

            return count + index * 10;
        }
    }

    @Test
    void decrementStatiFieldInFieldAccess() {
        verify(new DecrementStaticField());
    }

    /**
     * @version 2018/10/10 11:03:41
     */
    private static class DecrementStaticField implements TestCode.Float {

        private static float index = 1;

        private static float count = 2;

        @Override
        public float run() {
            index = count--;

            return count + index * 10;
        }
    }

    @Test
    void preincrementStatiFieldInFieldAccess() {
        verify(new PreincrementStaticField());
    }

    /**
     * @version 2018/10/10 11:03:34
     */
    private static class PreincrementStaticField implements TestCode.Float {

        private static float index = 1;

        private static float count = 2;

        @Override
        public float run() {
            index = ++count;

            return count + index * 10;
        }
    }

    @Test
    void predecrementStatiFieldInFieldAccess() {
        verify(new PredecrementStaticField());
    }

    /**
     * @version 2018/10/10 11:03:14
     */
    private static class PredecrementStaticField implements TestCode.Float {

        private static float index = 1;

        private static float count = 2;

        @Override
        public float run() {
            index = --count;

            return count + index * 10;
        }
    }

    @Test
    void classEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return float.class == float.class;
            }
        });
    }

    @Test
    void arrayClassEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                float[] array = {};
                return float[].class == array.getClass();
            }
        });
    }
}