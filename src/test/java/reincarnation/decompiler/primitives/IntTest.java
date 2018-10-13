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
import reincarnation.Code.Int;
import reincarnation.CodeVerifier;
import reincarnation.Debuggable;

/**
 * @version 2018/10/05 0:17:07
 */
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
        verify(new Code.Int() {

            @Override
            public int run() {
                return Integer.MAX_VALUE;
            }
        });
    }

    @Test
    void min() {
        verify(new Code.Int() {

            @Override
            public int run() {
                return Integer.MIN_VALUE;
            }
        });
    }

    @Test
    void add() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value + 1;
            }
        });
    }

    @Test
    void addAssignable() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value += 2;
            }
        });
    }

    @Test
    void subtrrun() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value - 1;
            }
        });
    }

    @Test
    void subtractAssignable() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value -= 2;
            }
        });
    }

    @Test
    void multiply() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value * 2;
            }
        });
    }

    @Test
    void multiplyAssignable() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value *= 2;
            }
        });
    }

    @Test
    void divide() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value / 2;
            }
        });
    }

    @Test
    void divideAssignable() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value /= 2;
            }
        });
    }

    @Test
    void modulo() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value % 2;
            }
        });
    }

    @Test
    void moduloAssignable() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value %= 2;
            }
        });
    }

    @Test
    void bitFlag() {
        verify(new Code.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return (value & 1) == 0;
            }
        });
    }

    @Test
    void bitAnd() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value & 0x010101;
            }
        });
    }

    @Test
    void bitOr() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value | 0x010101;
            }
        });
    }

    @Test
    void bitOrAssignable() {
        verify(new Code() {

            public int run(int value) {
                return value |= 0x010101;
            }
        });
    }

    @Test
    void bitXor() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value ^ 0x010101;
            }
        });
    }

    @Test
    void bitXorAssignable() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value ^= 0x010101;
            }
        });
    }

    @Test
    void bitNot() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return ~value;
            }
        });
    }

    @Test
    void shiftLeft() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value << 1;
            }
        });
    }

    @Test
    void shiftLeftAssignable() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value <<= 1;
            }
        });
    }

    @Test
    void shiftRight() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value >> 1;
            }
        });
    }

    @Test
    void shiftRightAssignable() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value >>= 1;
            }
        });
    }

    @Test
    void unsignedShiftRight() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value >>> 1;
            }
        });
    }

    @Test
    void unsignedShiftRightAssignable() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value >>>= 1;
            }
        });
    }

    @Test
    void equal() {
        verify(new Code.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value == 0;
            }
        });
    }

    @Test
    void notEqual() {
        verify(new Code.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value != 0;
            }
        });
    }

    @Test
    void less() {
        verify(new Code.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value < 1;
            }
        });
    }

    @Test
    void lessEqual() {
        verify(new Code.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value <= 1;
            }
        });
    }

    @Test
    void greater() {
        verify(new Code.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value > 1;
            }
        });
    }

    @Test
    void greaterEqual() {
        verify(new Code.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value >= 1;
            }
        });
    }

    @Test
    void postIncrement() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value++;
            }
        });
    }

    @Test
    void postIncrementValue() {
        verify(new Code.IntParam() {

            @Debuggable
            @Override
            public int run(int value) {
                int next = value++;
                return value + next;
            }
        });
    }

    @Test
    void postIncrementLike() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value + 1;
            }
        });
    }

    @Test
    void preIncrement() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return ++value;
            }
        });
    }

    @Test
    void preIncrementInStatement() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return 2 * ++value;
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
    private static class IncrementStaticField implements Code.Int {

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
    private static class DecrementStaticField implements Code.Int {

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
    private static class PreincrementStaticField implements Code.Int {

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
    private static class PredecrementStaticField implements Code.Int {

        private static int index = 1;

        private static int count = 2;

        @Override
        public int run() {
            index = --count;

            return count + index * 10;
        }
    }
}
