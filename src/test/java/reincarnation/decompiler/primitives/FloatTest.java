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

class FloatTest extends CodeVerifier {

    @CompilableTest
    void zero() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return 0F;
            }
        });
    }

    @CompilableTest
    void one() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return 1F;
            }
        });
    }

    @CompilableTest
    void two() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return 2F;
            }
        });
    }

    @CompilableTest
    void three() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return 3F;
            }
        });
    }

    @CompilableTest
    void minus() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return -1F;
            }
        });
    }

    @CompilableTest
    void max() {
        verify(new TestCode.Float() {

            @Override
            public float run() {
                return java.lang.Float.MAX_VALUE;
            }
        });
    }

    @CompilableTest
    void min() {
        verify(new TestCode.Float() {

            @Override
            public float run() {
                return java.lang.Float.MIN_VALUE;
            }
        });
    }

    @CompilableTest
    void add() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value + 1F;
            }
        });
    }

    @CompilableTest
    void addAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value += 2F;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void subtract() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value - 1F;
            }
        });
    }

    @CompilableTest
    void subtractAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value -= 2F;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void multiply() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value * 2F;
            }
        });
    }

    @CompilableTest
    void multiplyAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value *= 2F;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void divide() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value / 2F;
            }
        });
    }

    @CompilableTest
    void divideAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value /= 2F;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void modulo() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value % 2F;
            }
        });
    }

    @CompilableTest
    void moduloAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value %= 2F;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void postIncrement() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value++;
            }
        });
    }

    @CompilableTest
    void postIncrementValue() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                float next = value++;
                return value + next;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void postIncrementLike() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value + 1F;
            }
        });
    }

    @CompilableTest
    void preIncrement() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return ++value;
            }
        });
    }

    @CompilableTest
    void preIncrementValue() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                float next = ++value;
                return value + next;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void equal() {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return value == 0;
            }
        });
    }

    @CompilableTest
    void notEqual() {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return value != 0;
            }
        });
    }

    @CompilableTest
    void less() {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return value < 1;
            }
        });
    }

    @CompilableTest
    void lessEqual() {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return value <= 1;
            }
        });
    }

    @CompilableTest
    void greater() {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return value > 1;
            }
        });
    }

    @CompilableTest
    void greaterEqual() {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return value >= 1;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
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

    @CompilableTest
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

    @CompilableTest
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

    @CompilableTest
    void classEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return float.class == float.class;
            }
        });
    }

    @CompilableTest
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