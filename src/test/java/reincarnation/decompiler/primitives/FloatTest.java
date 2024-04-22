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

class FloatTest extends CodeVerifier {

    @DecompilableTest
    void zero() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return 0F;
            }
        });
    }

    @DecompilableTest
    void one() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return 1F;
            }
        });
    }

    @DecompilableTest
    void two() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return 2F;
            }
        });
    }

    @DecompilableTest
    void three() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return 3F;
            }
        });
    }

    @DecompilableTest
    void minus() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return -1F;
            }
        });
    }

    @DecompilableTest
    void max() {
        verify(new TestCode.Float() {

            @Override
            public float run() {
                return java.lang.Float.MAX_VALUE;
            }
        });
    }

    @DecompilableTest
    void min() {
        verify(new TestCode.Float() {

            @Override
            public float run() {
                return java.lang.Float.MIN_VALUE;
            }
        });
    }

    @DecompilableTest
    void add() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value + 1F;
            }
        });
    }

    @DecompilableTest
    void addAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value += 2F;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void subtract() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value - 1F;
            }
        });
    }

    @DecompilableTest
    void subtractAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value -= 2F;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void multiply() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value * 2F;
            }
        });
    }

    @DecompilableTest
    void multiplyAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value *= 2F;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void divide() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value / 2F;
            }
        });
    }

    @DecompilableTest
    void divideAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value /= 2F;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void modulo() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value % 2F;
            }
        });
    }

    @DecompilableTest
    void moduloAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value %= 2F;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void postIncrement() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value++;
            }
        });
    }

    @DecompilableTest
    void postIncrementValue() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                float next = value++;
                return value + next;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void postIncrementLike() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value + 1F;
            }
        });
    }

    @DecompilableTest
    void preIncrement() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return ++value;
            }
        });
    }

    @DecompilableTest
    void preIncrementValue() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                float next = ++value;
                return value + next;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void equal() {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return value == 0;
            }
        });
    }

    @DecompilableTest
    void notEqual() {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return value != 0;
            }
        });
    }

    @DecompilableTest
    void less() {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return value < 1;
            }
        });
    }

    @DecompilableTest
    void lessEqual() {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return value <= 1;
            }
        });
    }

    @DecompilableTest
    void greater() {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return value > 1;
            }
        });
    }

    @DecompilableTest
    void greaterEqual() {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return value >= 1;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
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

    @DecompilableTest
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

    @DecompilableTest
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

    @DecompilableTest
    void classEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return float.class == float.class;
            }
        });
    }

    @DecompilableTest
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