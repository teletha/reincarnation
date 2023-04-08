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
import reincarnation.TestCode;

class LongTest extends CodeVerifier {

    @Test
    void zero() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return 0L;
            }
        });
    }

    @Test
    void one() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return 1L;
            }
        });
    }

    @Test
    void two() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return 2L;
            }
        });
    }

    @Test
    void three() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return 3L;
            }
        });
    }

    @Test
    void minus() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return -1L;
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
                return param + 1L;
            }
        });
    }

    @Test
    void addAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value += 2L;
            }
        });
    }

    @Test
    void addAssignableOnParameter() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value(value += 2L);
            }

            private long value(long value) {
                return value;
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
                return value - 1L;
            }
        });
    }

    @Test
    void subtractAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value -= 2L;
            }
        });
    }

    @Test
    void subtractAssignableOnParameter() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value(value -= 2L);
            }

            private long value(long value) {
                return value;
            }
        });
    }

    @Test
    void multiply() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value * 2L;
            }
        });
    }

    @Test
    void multiplyAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value *= 2L;
            }
        });
    }

    @Test
    void multipleAssignableOnParameter() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value(value *= 2L);
            }

            private long value(long value) {
                return value;
            }
        });
    }

    @Test
    void divide() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value / 2L;
            }
        });
    }

    @Test
    void divideAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value /= 2L;
            }
        });
    }

    @Test
    void divideAssignableOnParameter() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value(value /= 2L);
            }

            private long value(long value) {
                return value;
            }
        });
    }

    @Test
    void modulo() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value % 2L;
            }
        });
    }

    @Test
    void moduloAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value %= 2L;
            }
        });
    }

    @Test
    void moduloAssignableOnParameter() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value(value %= 2L);
            }

            private long value(long value) {
                return value;
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
    void bitAndAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value &= 0x010101;
            }
        });
    }

    @Test
    void bitAndAssignableOnParameter() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value(value &= 0x010101);
            }

            private long value(long value) {
                return value;
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
    void bitOrAssignableOnParameter() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value(value |= 0x010101);
            }

            private long value(long value) {
                return value;
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
    void bitXorAssignableOnParameter() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value(value ^= 0x010101);
            }

            private long value(long value) {
                return value;
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
    void shiftLeftAssignableOnParameter() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value(value <<= 1);
            }

            private long value(long value) {
                return value;
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
    void shiftRightAssignableOnParameter() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value(value >>= 1);
            }

            private long value(long value) {
                return value;
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
    void unsignedShiftRightAssignableOnParameter() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value(value >>>= 1);
            }

            private long value(long value) {
                return value;
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

    @Test
    void classEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return long.class == long.class;
            }
        });
    }

    @Test
    void arrayClassEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                long[] array = {};
                return long[].class == array.getClass();
            }
        });
    }
}