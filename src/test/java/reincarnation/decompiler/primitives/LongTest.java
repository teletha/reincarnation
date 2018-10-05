/*
 * Copyright (C) 2016 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.primitives;

import org.junit.jupiter.api.Test;

import reincarnation.Code;
import reincarnation.CodeVerifier;

/**
 * @version 2018/10/05 15:12:53
 */
class LongTest extends CodeVerifier {

    @Test
    void zero() {
        verify(new Code.Long() {

            @Override
            public long run() {
                return 0;
            }
        });
    }

    @Test
    void one() {
        verify(new Code.Long() {

            @Override
            public long run() {
                return 1;
            }
        });
    }

    @Test
    void two() {
        verify(new Code.Long() {

            @Override
            public long run() {
                return 2;
            }
        });
    }

    @Test
    void three() {
        verify(new Code.Long() {

            @Override
            public long run() {
                return 3;
            }
        });
    }

    @Test
    void minus() {
        verify(new Code.Long() {

            @Override
            public long run() {
                return -1;
            }
        });
    }

    @Test
    void max() {
        verify(new Code.Long() {

            @Override
            public long run() {
                return java.lang.Long.MAX_VALUE;
            }
        });
    }

    @Test
    void min() {
        verify(new Code.Long() {

            @Override
            public long run() {
                return java.lang.Long.MIN_VALUE;
            }
        });
    }

    @Test
    void add() {
        verify(new Code.LongParam() {

            @Override
            public long run(long param) {
                return param + 1;
            }
        });
    }

    @Test
    void addAssignable() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return value += 2;
            }
        });
    }

    @Test
    void negate() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return -value;
            }
        });
    }

    @Test
    void subtract() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return value - 1;
            }
        });
    }

    @Test
    void subtractAssignable() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return value -= 2;
            }
        });
    }

    @Test
    void multiply() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return value * 2;
            }
        });
    }

    @Test
    void multiplyAssignable() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return value *= 2;
            }
        });
    }

    @Test
    void divide() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return value / 2;
            }
        });
    }

    @Test
    void divideAssignable() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return value /= 2;
            }
        });
    }

    @Test
    void modulo() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return value % 2;
            }
        });
    }

    @Test
    void moduloAssignable() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return value %= 2;
            }
        });
    }

    @Test
    void bitFlag() {
        verify(new Code.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return (value & 1) == 0;
            }
        });
    }

    @Test
    void bitAnd() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return value & 0x010101;
            }
        });
    }

    @Test
    void bitOr() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return value | 0x010101;
            }
        });
    }

    @Test
    void bitOrAssignable() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return value |= 0x010101;
            }
        });
    }

    @Test
    void bitXor() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return value ^ 0x010101;
            }
        });
    }

    @Test
    void bitXorAssignable() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return value ^= 0x010101;
            }
        });
    }

    @Test
    void bitNot() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return ~value;
            }
        });
    }

    @Test
    void shiftLeft() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return value << 1;
            }
        });
    }

    @Test
    void shiftLeftAssignable() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return value <<= 1;
            }
        });
    }

    @Test
    void shiftRight() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return value >> 1;
            }
        });
    }

    @Test
    void shiftRightAssignable() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return value >>= 1;
            }
        });
    }

    @Test
    void unsignedShiftRight() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return value >>> 1;
            }
        });
    }

    @Test
    void unsignedShiftRightAssignable() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return value >>>= 1;
            }
        });
    }

    @Test
    void postIncrement() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return value++;
            }
        });
    }

    @Test
    void postIncrementValue() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                long next = value++;
                return value + next;
            }
        });
    }

    @Test
    void postIncrementLike() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return value + 1;
            }
        });
    }

    @Test
    void preIncrement() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return ++value;
            }
        });
    }

    @Test
    void preIncrementInStatement() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                return 2 * ++value;
            }
        });
    }

    @Test
    void equal() {
        verify(new Code.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value == 0;
            }
        });
    }

    @Test
    void notEqual() {
        verify(new Code.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value != 0;
            }
        });
    }

    @Test
    void less() {
        verify(new Code.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value < 1;
            }
        });
    }

    @Test
    void lessEqual() {
        verify(new Code.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value <= 1;
            }
        });
    }

    @Test
    void greater() {
        verify(new Code.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value > 1;
            }
        });
    }

    @Test
    void greaterEqual() {
        verify(new Code.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value >= 1;
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
    private static class IncrementStaticField implements Code.Long {

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
    private static class DecrementStaticField implements Code.Long {

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
    private static class PreincrementStaticField implements Code.Long {

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
    private static class PredecrementStaticField implements Code.Long {

        private static long index = 1;

        private static long count = 2;

        @Override
        public long run() {
            index = --count;

            return count + index * 10;
        }
    }
}
