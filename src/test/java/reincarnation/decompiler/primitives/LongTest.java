/*
 * Copyright (C) 2019 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.primitives;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.CodeVerifier;

/**
 * @version 2018/10/09 15:53:39
 */
class LongTest extends CodeVerifier {

    @Test
    void zero() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return 0;
            }
        });
    }

    @Test
    void one() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return 1;
            }
        });
    }

    @Test
    void two() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return 2;
            }
        });
    }

    @Test
    void three() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return 3;
            }
        });
    }

    @Test
    void minus() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return -1;
            }
        });
    }

    @Test
    void max() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return java.lang.Long.MAX_VALUE;
            }
        });
    }

    @Test
    void min() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return java.lang.Long.MIN_VALUE;
            }
        });
    }

    @Test
    void add() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long param) {
                return param + 1;
            }
        });
    }

    @Test
    void addAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value += 2;
            }
        });
    }

    @Test
    void negate() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return -value;
            }
        });
    }

    @Test
    void subtract() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value - 1;
            }
        });
    }

    @Test
    void subtractAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value -= 2;
            }
        });
    }

    @Test
    void multiply() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value * 2;
            }
        });
    }

    @Test
    void multiplyAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value *= 2;
            }
        });
    }

    @Test
    void divide() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value / 2;
            }
        });
    }

    @Test
    void divideAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value /= 2;
            }
        });
    }

    @Test
    void modulo() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value % 2;
            }
        });
    }

    @Test
    void moduloAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value %= 2;
            }
        });
    }

    @Test
    void bitFlag() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return (value & 1) == 0;
            }
        });
    }

    @Test
    void bitAnd() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value & 0x010101;
            }
        });
    }

    @Test
    void bitOr() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value | 0x010101;
            }
        });
    }

    @Test
    void bitOrAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value |= 0x010101;
            }
        });
    }

    @Test
    void bitXor() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value ^ 0x010101;
            }
        });
    }

    @Test
    void bitXorAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value ^= 0x010101;
            }
        });
    }

    @Test
    void bitNot() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return ~value;
            }
        });
    }

    @Test
    void shiftLeft() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value << 1;
            }
        });
    }

    @Test
    void shiftLeftAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value <<= 1;
            }
        });
    }

    @Test
    void shiftRight() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value >> 1;
            }
        });
    }

    @Test
    void shiftRightAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value >>= 1;
            }
        });
    }

    @Test
    void unsignedShiftRight() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value >>> 1;
            }
        });
    }

    @Test
    void unsignedShiftRightAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value >>>= 1;
            }
        });
    }

    @Test
    void equal() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value == 0;
            }
        });
    }

    @Test
    void notEqual() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value != 0;
            }
        });
    }

    @Test
    void less() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value < 1;
            }
        });
    }

    @Test
    void lessEqual() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value <= 1;
            }
        });
    }

    @Test
    void greater() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value > 1;
            }
        });
    }

    @Test
    void greaterEqual() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value >= 1;
            }
        });
    }

    @Test
    void postIncrement() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value++;
            }
        });
    }

    @Test
    void postIncrementValue() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                long next = value++;
                return value + next;
            }
        });
    }

    @Test
    void postIncrementLike() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value + 1;
            }
        });
    }

    @Test
    void preIncrement() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return ++value;
            }
        });
    }

    @Test
    void preIncrementInStatement() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return 2 * ++value;
            }
        });
    }

    @Test
    void incrementStaticFieldInFieldAccess() throws Exception {
        verify(new IncrementStaticField());
    }

    /**
     * @version 2018/10/05 15:21:36
     */
    private static class IncrementStaticField implements TestCode.Long {

        private static long index = 1;

        private static long count = 2;

        @Override
        public long run() {
            index = count++;

            return count + index * 10;
        }
    }

    @Test
    void decrementStaticFieldInFieldAccess() throws Exception {
        verify(new DecrementStaticField());
    }

    /**
     * @version 2018/10/05 15:21:40
     */
    private static class DecrementStaticField implements TestCode.Long {

        private static long index = 1;

        private static long count = 2;

        @Override
        public long run() {
            index = count--;

            return count + index * 10;
        }
    }

    @Test
    void preincrementStaticFieldInFieldAccess() throws Exception {
        verify(new PreincrementStaticField());
    }

    /**
     * @version 2018/10/05 15:21:43
     */
    private static class PreincrementStaticField implements TestCode.Long {

        private static long index = 1;

        private static long count = 2;

        @Override
        public long run() {
            index = ++count;

            return count + index * 10;
        }
    }

    @Test
    void predecrementStaticFieldInFieldAccess() throws Exception {
        verify(new PredecrementStaticField());
    }

    /**
     * @version 2018/10/05 15:21:46
     */
    private static class PredecrementStaticField implements TestCode.Long {

        private static long index = 1;

        private static long count = 2;

        @Override
        public long run() {
            index = --count;

            return count + index * 10;
        }
    }
}
