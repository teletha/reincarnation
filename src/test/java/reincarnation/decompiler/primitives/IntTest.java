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
import reincarnation.TestCode.Int;

class IntTest extends CodeVerifier {

    @DecompilableTest
    void zero() {
        verify(new Int() {

            @Override
            public int run() {
                return 0;
            }
        });
    }

    @DecompilableTest
    void one() {
        verify(new Int() {

            @Override
            public int run() {
                return 1;
            }
        });
    }

    @DecompilableTest
    void two() {
        verify(new Int() {

            @Override
            public int run() {
                return 2;
            }
        });
    }

    @DecompilableTest
    void three() {
        verify(new Int() {

            @Override
            public int run() {
                return 3;
            }
        });
    }

    @DecompilableTest
    void four() {
        verify(new Int() {

            @Override
            public int run() {
                return 4;
            }
        });
    }

    @DecompilableTest
    void five() {
        verify(new Int() {

            @Override
            public int run() {
                return 5;
            }
        });
    }

    @DecompilableTest
    void six() {
        verify(new Int() {

            @Override
            public int run() {
                return 6;
            }
        });
    }

    @DecompilableTest
    void seven() {
        verify(new Int() {

            @Override
            public int run() {
                return 7;
            }
        });
    }

    @DecompilableTest
    void minusOne() {
        verify(new Int() {

            @Override
            public int run() {
                return -1;
            }
        });
    }

    @DecompilableTest
    void minusTwo() {
        verify(new Int() {

            @Override
            public int run() {
                return -2;
            }
        });
    }

    @DecompilableTest
    void minusThree() {
        verify(new Int() {

            @Override
            public int run() {
                return -3;
            }
        });
    }

    @DecompilableTest
    void max() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return Integer.MAX_VALUE;
            }
        });
    }

    @DecompilableTest
    void min() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return Integer.MIN_VALUE;
            }
        });
    }

    @DecompilableTest
    void add() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value + 1;
            }
        });
    }

    @DecompilableTest
    void addAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value += 2;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void subtrrun() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value - 1;
            }
        });
    }

    @DecompilableTest
    void subtractAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value -= 2;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void multiply() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value * 2;
            }
        });
    }

    @DecompilableTest
    void multiplyAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value *= 2;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void divide() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value / 2;
            }
        });
    }

    @DecompilableTest
    void divideAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value /= 2;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void modulo() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value % 2;
            }
        });
    }

    @DecompilableTest
    void moduloAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value %= 2;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void bitFlag() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return (value & 1) == 0;
            }
        });
    }

    @DecompilableTest
    void bitAnd() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value & 0x010101;
            }
        });
    }

    @DecompilableTest
    void bitAndAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value &= 0x010101;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void bitOr() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value | 0x010101;
            }
        });
    }

    @DecompilableTest
    void bitOrAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value |= 0x010101;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void bitXor() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value ^ 0x010101;
            }
        });
    }

    @DecompilableTest
    void bitXorAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value ^= 0x010101;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void bitNot() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return ~value;
            }
        });
    }

    @DecompilableTest
    void shiftLeft() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value << 1;
            }
        });
    }

    @DecompilableTest
    void shiftLeftAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value <<= 1;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void shiftRight() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value >> 1;
            }
        });
    }

    @DecompilableTest
    void shiftRightAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value >>= 1;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void unsignedShiftRight() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value >>> 1;
            }
        });
    }

    @DecompilableTest
    void unsignedShiftRightAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value >>>= 1;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void equal() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value == 0;
            }
        });
    }

    @DecompilableTest
    void notEqual() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value != 0;
            }
        });
    }

    @DecompilableTest
    void less() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value < 1;
            }
        });
    }

    @DecompilableTest
    void lessEqual() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value <= 1;
            }
        });
    }

    @DecompilableTest
    void greater() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value > 1;
            }
        });
    }

    @DecompilableTest
    void greaterEqual() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value >= 1;
            }
        });
    }

    @DecompilableTest
    void postIncrement() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value++;
            }
        });
    }

    @DecompilableTest
    void postIncrementValue() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                int next = value++;
                return value + next;
            }
        });
    }

    @DecompilableTest
    void postIncrementLike() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value + 1;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void preIncrement() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return ++value;
            }
        });
    }

    @DecompilableTest
    void preIncrementInStatement() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return 2 * ++value;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
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

    @DecompilableTest
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

    @DecompilableTest
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

    @DecompilableTest
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

    @DecompilableTest
    void classEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return int.class == int.class;
            }
        });
    }

    @DecompilableTest
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