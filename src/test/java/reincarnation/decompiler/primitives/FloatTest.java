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
import reincarnation.Debuggable;
import reincarnation.TestCode;

class FloatTest extends CodeVerifier {

    @Test
    void zero() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return 0F;
            }
        });
    }

    @Test
    void one() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return 1F;
            }
        });
    }

    @Test
    void two() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return 2F;
            }
        });
    }

    @Test
    void three() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return 3F;
            }
        });
    }

    @Test
    void minus() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return -1F;
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
                return value + 1F;
            }
        });
    }

    @Test
    void addAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value += 2F;
            }
        });
    }

    @Test
    void addAssignableOnParameter() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value(value += 2F);
            }

            private float value(float value) {
                return value;
            }
        });
    }

    @Test
    void subtract() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value - 1F;
            }
        });
    }

    @Test
    void subtractAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value -= 2F;
            }
        });
    }

    @Test
    @Debuggable
    void subtractAssignableOnParameter() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value(value -= 2.2F);
            }

            private float value(float value) {
                return value;
            }
        });
    }

    @Test
    void multiply() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value * 2F;
            }
        });
    }

    @Test
    void multiplyAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value *= 2F;
            }
        });
    }

    @Test
    void multipleAssignableOnParameter() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value(value *= 2.2F);
            }

            private float value(float value) {
                return value;
            }
        });
    }

    @Test
    void divide() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value / 2F;
            }
        });
    }

    @Test
    void divideAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value /= 2F;
            }
        });
    }

    @Test
    void divideAssignableOnParameter() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value(value /= 2F);
            }

            private float value(float value) {
                return value;
            }
        });
    }

    @Test
    void modulo() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value % 2F;
            }
        });
    }

    @Test
    void moduloAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value %= 2F;
            }
        });
    }

    @Test
    void moduloAssignableOnParameter() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value(value %= 2F);
            }

            private float value(float value) {
                return value;
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
    void postIncrementOnParameter() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value(value++);
            }

            private float value(float value) {
                return value;
            }
        });
    }

    @Test
    void postIncrementLike() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value + 1F;
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
    void preIncrementValue() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                float next = ++value;
                return value + next;
            }
        });
    }

    @Test
    void preIncrementOnParameter() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value(++value);
            }

            private float value(float value) {
                return value;
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