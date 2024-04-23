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
import reincarnation.TestCode.Int;

class IntTest extends CodeVerifier {

    @CrossDecompilerTest
    void zero() {
        verify(new Int() {

            @Override
            public int run() {
                return 0;
            }
        });
    }

    @CrossDecompilerTest
    void one() {
        verify(new Int() {

            @Override
            public int run() {
                return 1;
            }
        });
    }

    @CrossDecompilerTest
    void two() {
        verify(new Int() {

            @Override
            public int run() {
                return 2;
            }
        });
    }

    @CrossDecompilerTest
    void three() {
        verify(new Int() {

            @Override
            public int run() {
                return 3;
            }
        });
    }

    @CrossDecompilerTest
    void four() {
        verify(new Int() {

            @Override
            public int run() {
                return 4;
            }
        });
    }

    @CrossDecompilerTest
    void five() {
        verify(new Int() {

            @Override
            public int run() {
                return 5;
            }
        });
    }

    @CrossDecompilerTest
    void six() {
        verify(new Int() {

            @Override
            public int run() {
                return 6;
            }
        });
    }

    @CrossDecompilerTest
    void seven() {
        verify(new Int() {

            @Override
            public int run() {
                return 7;
            }
        });
    }

    @CrossDecompilerTest
    void minusOne() {
        verify(new Int() {

            @Override
            public int run() {
                return -1;
            }
        });
    }

    @CrossDecompilerTest
    void minusTwo() {
        verify(new Int() {

            @Override
            public int run() {
                return -2;
            }
        });
    }

    @CrossDecompilerTest
    void minusThree() {
        verify(new Int() {

            @Override
            public int run() {
                return -3;
            }
        });
    }

    @CrossDecompilerTest
    void max() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return Integer.MAX_VALUE;
            }
        });
    }

    @CrossDecompilerTest
    void min() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return Integer.MIN_VALUE;
            }
        });
    }

    @CrossDecompilerTest
    void add() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value + 1;
            }
        });
    }

    @CrossDecompilerTest
    void addAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value += 2;
            }
        });
    }

    @CrossDecompilerTest
    void addAssignableOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(value += 2);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void subtrrun() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value - 1;
            }
        });
    }

    @CrossDecompilerTest
    void subtractAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value -= 2;
            }
        });
    }

    @CrossDecompilerTest
    void subtractAssignableOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(value -= 2);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void multiply() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value * 2;
            }
        });
    }

    @CrossDecompilerTest
    void multiplyAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value *= 2;
            }
        });
    }

    @CrossDecompilerTest
    void multipleAssignableOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(value *= 20000);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void divide() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value / 2;
            }
        });
    }

    @CrossDecompilerTest
    void divideAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value /= 2;
            }
        });
    }

    @CrossDecompilerTest
    void divideAssignableOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(value /= 2);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void modulo() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value % 2;
            }
        });
    }

    @CrossDecompilerTest
    void moduloAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value %= 2;
            }
        });
    }

    @CrossDecompilerTest
    void moduloAssignableOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(value %= 2);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void bitFlag() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return (value & 1) == 0;
            }
        });
    }

    @CrossDecompilerTest
    void bitAnd() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value & 0x010101;
            }
        });
    }

    @CrossDecompilerTest
    void bitAndAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value &= 0x010101;
            }
        });
    }

    @CrossDecompilerTest
    void bitAndAssignableOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(value &= 0x010101);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void bitOr() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value | 0x010101;
            }
        });
    }

    @CrossDecompilerTest
    void bitOrAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value |= 0x010101;
            }
        });
    }

    @CrossDecompilerTest
    void bitOrAssignableOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(value |= 0x010101);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void bitXor() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value ^ 0x010101;
            }
        });
    }

    @CrossDecompilerTest
    void bitXorAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value ^= 0x010101;
            }
        });
    }

    @CrossDecompilerTest
    void bitXorAssignableOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(value ^= 0x010101);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void bitNot() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return ~value;
            }
        });
    }

    @CrossDecompilerTest
    void shiftLeft() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value << 1;
            }
        });
    }

    @CrossDecompilerTest
    void shiftLeftAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value <<= 1;
            }
        });
    }

    @CrossDecompilerTest
    void shiftLeftAssignableOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(value <<= 1);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void shiftRight() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value >> 1;
            }
        });
    }

    @CrossDecompilerTest
    void shiftRightAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value >>= 1;
            }
        });
    }

    @CrossDecompilerTest
    void shiftRightAssignableOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(value >>= 1);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void unsignedShiftRight() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value >>> 1;
            }
        });
    }

    @CrossDecompilerTest
    void unsignedShiftRightAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value >>>= 1;
            }
        });
    }

    @CrossDecompilerTest
    void unsignedShiftRightAssignableOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(value >>>= 1);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void equal() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value == 0;
            }
        });
    }

    @CrossDecompilerTest
    void notEqual() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value != 0;
            }
        });
    }

    @CrossDecompilerTest
    void less() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value < 1;
            }
        });
    }

    @CrossDecompilerTest
    void lessEqual() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value <= 1;
            }
        });
    }

    @CrossDecompilerTest
    void greater() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value > 1;
            }
        });
    }

    @CrossDecompilerTest
    void greaterEqual() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value >= 1;
            }
        });
    }

    @CrossDecompilerTest
    void postIncrement() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value++;
            }
        });
    }

    @CrossDecompilerTest
    void postIncrementValue() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                int next = value++;
                return value + next;
            }
        });
    }

    @CrossDecompilerTest
    void postIncrementLike() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value + 1;
            }
        });
    }

    @CrossDecompilerTest
    void postIncrementOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(value++);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void preIncrement() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return ++value;
            }
        });
    }

    @CrossDecompilerTest
    void preIncrementInStatement() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return 2 * ++value;
            }
        });
    }

    @CrossDecompilerTest
    void preIncrementOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(++value);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void incrementStatiFieldInFieldAccess() {
        verify(new IncrementStaticField());
    }

    /**
     * @version 2018/10/05 0:16:26
     */
    private static class IncrementStaticField implements TestCode.Int {

        private static int index = 1;

        private static int count = 2;

        @Override
        public int run() {
            index = count++;
            return count + index * 10;
        }
    }

    @CrossDecompilerTest
    void decrementStatiFieldInFieldAccess() {
        verify(new DecrementStaticField());
    }

    /**
     * @version 2018/10/05 0:16:12
     */
    private static class DecrementStaticField implements TestCode.Int {

        private static int index = 1;

        private static int count = 2;

        @Override
        public int run() {
            index = count--;

            return count + index * 10;
        }
    }

    @CrossDecompilerTest
    void preincrementStatiFieldInFieldAccess() {
        verify(new PreincrementStaticField());
    }

    /**
     * @version 2018/10/05 0:16:08
     */
    private static class PreincrementStaticField implements TestCode.Int {

        private static int index = 1;

        private static int count = 2;

        @Override
        public int run() {
            index = ++count;

            return count + index * 10;
        }
    }

    @CrossDecompilerTest
    void predecrementStatiFieldInFieldAccess() {
        verify(new PredecrementStaticField());
    }

    /**
     * @version 2018/10/05 0:16:17
     */
    private static class PredecrementStaticField implements TestCode.Int {

        private static int index = 1;

        private static int count = 2;

        @Override
        public int run() {
            index = --count;

            return count + index * 10;
        }
    }

    @CrossDecompilerTest
    void classEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return int.class == int.class;
            }
        });
    }

    @CrossDecompilerTest
    void arrayClassEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                int[] array = {};
                return int[].class == array.getClass();
            }
        });
    }
}