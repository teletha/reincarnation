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

class FloatTest extends CodeVerifier {

    @CrossDecompilerTest
    void zero() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return 0F;
            }
        });
    }

    @CrossDecompilerTest
    void one() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return 1F;
            }
        });
    }

    @CrossDecompilerTest
    void two() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return 2F;
            }
        });
    }

    @CrossDecompilerTest
    void three() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return 3F;
            }
        });
    }

    @CrossDecompilerTest
    void minus() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return -1F;
            }
        });
    }

    @CrossDecompilerTest
    void max() {
        verify(new TestCode.Float() {

            @Override
            public float run() {
                return java.lang.Float.MAX_VALUE;
            }
        });
    }

    @CrossDecompilerTest
    void min() {
        verify(new TestCode.Float() {

            @Override
            public float run() {
                return java.lang.Float.MIN_VALUE;
            }
        });
    }

    @CrossDecompilerTest
    void add() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value + 1F;
            }
        });
    }

    @CrossDecompilerTest
    void addAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value += 2F;
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
    void subtract() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value - 1F;
            }
        });
    }

    @CrossDecompilerTest
    void subtractAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value -= 2F;
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
    void multiply() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value * 2F;
            }
        });
    }

    @CrossDecompilerTest
    void multiplyAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value *= 2F;
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
    void divide() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value / 2F;
            }
        });
    }

    @CrossDecompilerTest
    void divideAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value /= 2F;
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
    void modulo() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value % 2F;
            }
        });
    }

    @CrossDecompilerTest
    void moduloAssignable() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value %= 2F;
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
    void postIncrement() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value++;
            }
        });
    }

    @CrossDecompilerTest
    void postIncrementValue() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                float next = value++;
                return value + next;
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
    void postIncrementLike() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return value + 1F;
            }
        });
    }

    @CrossDecompilerTest
    void preIncrement() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                return ++value;
            }
        });
    }

    @CrossDecompilerTest
    void preIncrementValue() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                float next = ++value;
                return value + next;
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
    void equal() {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return value == 0;
            }
        });
    }

    @CrossDecompilerTest
    void notEqual() {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return value != 0;
            }
        });
    }

    @CrossDecompilerTest
    void less() {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return value < 1;
            }
        });
    }

    @CrossDecompilerTest
    void lessEqual() {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return value <= 1;
            }
        });
    }

    @CrossDecompilerTest
    void greater() {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return value > 1;
            }
        });
    }

    @CrossDecompilerTest
    void greaterEqual() {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return value >= 1;
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
    void classEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return float.class == float.class;
            }
        });
    }

    @CrossDecompilerTest
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