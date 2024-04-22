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

class LongTest extends CodeVerifier {

    @DecompilableTest
    void zero() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return 0L;
            }
        });
    }

    @DecompilableTest
    void one() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return 1L;
            }
        });
    }

    @DecompilableTest
    void two() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return 2L;
            }
        });
    }

    @DecompilableTest
    void three() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return 3L;
            }
        });
    }

    @DecompilableTest
    void minus() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return -1L;
            }
        });
    }

    @DecompilableTest
    void max() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return java.lang.Long.MAX_VALUE;
            }
        });
    }

    @DecompilableTest
    void min() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return java.lang.Long.MIN_VALUE;
            }
        });
    }

    @DecompilableTest
    void add() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long param) {
                return param + 1L;
            }
        });
    }

    @DecompilableTest
    void addAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value += 2L;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void negate() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return -value;
            }
        });
    }

    @DecompilableTest
    void subtract() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value - 1L;
            }
        });
    }

    @DecompilableTest
    void subtractAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value -= 2L;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void multiply() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value * 2L;
            }
        });
    }

    @DecompilableTest
    void multiplyAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value *= 2L;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void divide() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value / 2L;
            }
        });
    }

    @DecompilableTest
    void divideAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value /= 2L;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void modulo() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value % 2L;
            }
        });
    }

    @DecompilableTest
    void moduloAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value %= 2L;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void bitFlag() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return (value & 1) == 0;
            }
        });
    }

    @DecompilableTest
    void bitAnd() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value & 0x010101;
            }
        });
    }

    @DecompilableTest
    void bitAndAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value &= 0x010101;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void bitOr() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value | 0x010101;
            }
        });
    }

    @DecompilableTest
    void bitOrAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value |= 0x010101;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void bitXor() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value ^ 0x010101;
            }
        });
    }

    @DecompilableTest
    void bitXorAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value ^= 0x010101;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void bitNot() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return ~value;
            }
        });
    }

    @DecompilableTest
    void shiftLeft() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value << 1;
            }
        });
    }

    @DecompilableTest
    void shiftLeftAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value <<= 1;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void shiftRight() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value >> 1;
            }
        });
    }

    @DecompilableTest
    void shiftRightAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value >>= 1;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void unsignedShiftRight() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value >>> 1;
            }
        });
    }

    @DecompilableTest
    void unsignedShiftRightAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value >>>= 1;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
    void equal() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value == 0;
            }
        });
    }

    @DecompilableTest
    void notEqual() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value != 0;
            }
        });
    }

    @DecompilableTest
    void less() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value < 1;
            }
        });
    }

    @DecompilableTest
    void lessEqual() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value <= 1;
            }
        });
    }

    @DecompilableTest
    void greater() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value > 1;
            }
        });
    }

    @DecompilableTest
    void greaterEqual() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value >= 1;
            }
        });
    }

    @DecompilableTest
    void postIncrement() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value++;
            }
        });
    }

    @DecompilableTest
    void postIncrementValue() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                long next = value++;
                return value + next;
            }
        });
    }

    @DecompilableTest
    void postIncrementLike() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value + 1;
            }
        });
    }

    @DecompilableTest
    void preIncrement() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return ++value;
            }
        });
    }

    @DecompilableTest
    void preIncrementInStatement() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return 2 * ++value;
            }
        });
    }

    @DecompilableTest
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

    @DecompilableTest
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

    @DecompilableTest
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

    @DecompilableTest
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

    @DecompilableTest
    void classEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return long.class == long.class;
            }
        });
    }

    @DecompilableTest
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