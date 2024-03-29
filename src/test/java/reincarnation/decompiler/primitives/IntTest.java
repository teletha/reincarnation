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

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.TestCode;
import reincarnation.TestCode.Int;

class IntTest extends CodeVerifier {

    @Test
    void zero() {
        verify(new Int() {

            @Override
            public int run() {
                return 0;
            }
        });
    }

    @Test
    void one() {
        verify(new Int() {

            @Override
            public int run() {
                return 1;
            }
        });
    }

    @Test
    void two() {
        verify(new Int() {

            @Override
            public int run() {
                return 2;
            }
        });
    }

    @Test
    void three() {
        verify(new Int() {

            @Override
            public int run() {
                return 3;
            }
        });
    }

    @Test
    void four() {
        verify(new Int() {

            @Override
            public int run() {
                return 4;
            }
        });
    }

    @Test
    void five() {
        verify(new Int() {

            @Override
            public int run() {
                return 5;
            }
        });
    }

    @Test
    void six() {
        verify(new Int() {

            @Override
            public int run() {
                return 6;
            }
        });
    }

    @Test
    void seven() {
        verify(new Int() {

            @Override
            public int run() {
                return 7;
            }
        });
    }

    @Test
    void minusOne() {
        verify(new Int() {

            @Override
            public int run() {
                return -1;
            }
        });
    }

    @Test
    void minusTwo() {
        verify(new Int() {

            @Override
            public int run() {
                return -2;
            }
        });
    }

    @Test
    void minusThree() {
        verify(new Int() {

            @Override
            public int run() {
                return -3;
            }
        });
    }

    @Test
    void max() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return Integer.MAX_VALUE;
            }
        });
    }

    @Test
    void min() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return Integer.MIN_VALUE;
            }
        });
    }

    @Test
    void add() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value + 1;
            }
        });
    }

    @Test
    void addAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value += 2;
            }
        });
    }

    @Test
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

    @Test
    void subtrrun() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value - 1;
            }
        });
    }

    @Test
    void subtractAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value -= 2;
            }
        });
    }

    @Test
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

    @Test
    void multiply() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value * 2;
            }
        });
    }

    @Test
    void multiplyAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value *= 2;
            }
        });
    }

    @Test
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

    @Test
    void divide() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value / 2;
            }
        });
    }

    @Test
    void divideAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value /= 2;
            }
        });
    }

    @Test
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

    @Test
    void modulo() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value % 2;
            }
        });
    }

    @Test
    void moduloAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value %= 2;
            }
        });
    }

    @Test
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

    @Test
    void bitFlag() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return (value & 1) == 0;
            }
        });
    }

    @Test
    void bitAnd() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value & 0x010101;
            }
        });
    }

    @Test
    void bitAndAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value &= 0x010101;
            }
        });
    }

    @Test
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

    @Test
    void bitOr() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value | 0x010101;
            }
        });
    }

    @Test
    void bitOrAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value |= 0x010101;
            }
        });
    }

    @Test
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

    @Test
    void bitXor() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value ^ 0x010101;
            }
        });
    }

    @Test
    void bitXorAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value ^= 0x010101;
            }
        });
    }

    @Test
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

    @Test
    void bitNot() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return ~value;
            }
        });
    }

    @Test
    void shiftLeft() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value << 1;
            }
        });
    }

    @Test
    void shiftLeftAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value <<= 1;
            }
        });
    }

    @Test
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

    @Test
    void shiftRight() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value >> 1;
            }
        });
    }

    @Test
    void shiftRightAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value >>= 1;
            }
        });
    }

    @Test
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

    @Test
    void unsignedShiftRight() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value >>> 1;
            }
        });
    }

    @Test
    void unsignedShiftRightAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value >>>= 1;
            }
        });
    }

    @Test
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

    @Test
    void equal() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value == 0;
            }
        });
    }

    @Test
    void notEqual() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value != 0;
            }
        });
    }

    @Test
    void less() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value < 1;
            }
        });
    }

    @Test
    void lessEqual() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value <= 1;
            }
        });
    }

    @Test
    void greater() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value > 1;
            }
        });
    }

    @Test
    void greaterEqual() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value >= 1;
            }
        });
    }

    @Test
    void postIncrement() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value++;
            }
        });
    }

    @Test
    void postIncrementValue() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                int next = value++;
                return value + next;
            }
        });
    }

    @Test
    void postIncrementLike() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value + 1;
            }
        });
    }

    @Test
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

    @Test
    void preIncrement() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return ++value;
            }
        });
    }

    @Test
    void preIncrementInStatement() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return 2 * ++value;
            }
        });
    }

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
    void classEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return int.class == int.class;
            }
        });
    }

    @Test
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