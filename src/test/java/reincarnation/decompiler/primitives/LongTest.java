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

class LongTest extends CodeVerifier {

    @CrossDecompilerTest
    void zero() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return 0L;
            }
        });
    }

    @CrossDecompilerTest
    void one() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return 1L;
            }
        });
    }

    @CrossDecompilerTest
    void two() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return 2L;
            }
        });
    }

    @CrossDecompilerTest
    void three() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return 3L;
            }
        });
    }

    @CrossDecompilerTest
    void minus() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return -1L;
            }
        });
    }

    @CrossDecompilerTest
    void max() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return java.lang.Long.MAX_VALUE;
            }
        });
    }

    @CrossDecompilerTest
    void min() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return java.lang.Long.MIN_VALUE;
            }
        });
    }

    @CrossDecompilerTest
    void add() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long param) {
                return param + 1L;
            }
        });
    }

    @CrossDecompilerTest
    void addAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value += 2L;
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
    void negate() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return -value;
            }
        });
    }

    @CrossDecompilerTest
    void subtract() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value - 1L;
            }
        });
    }

    @CrossDecompilerTest
    void subtractAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value -= 2L;
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
    void multiply() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value * 2L;
            }
        });
    }

    @CrossDecompilerTest
    void multiplyAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value *= 2L;
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
    void divide() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value / 2L;
            }
        });
    }

    @CrossDecompilerTest
    void divideAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value /= 2L;
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
    void modulo() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value % 2L;
            }
        });
    }

    @CrossDecompilerTest
    void moduloAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value %= 2L;
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
    void bitFlag() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return (value & 1) == 0;
            }
        });
    }

    @CrossDecompilerTest
    void bitAnd() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value & 0x010101;
            }
        });
    }

    @CrossDecompilerTest
    void bitAndAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value &= 0x010101;
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
    void bitOr() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value | 0x010101;
            }
        });
    }

    @CrossDecompilerTest
    void bitOrAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value |= 0x010101;
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
    void bitXor() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value ^ 0x010101;
            }
        });
    }

    @CrossDecompilerTest
    void bitXorAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value ^= 0x010101;
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
    void bitNot() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return ~value;
            }
        });
    }

    @CrossDecompilerTest
    void shiftLeft() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value << 1;
            }
        });
    }

    @CrossDecompilerTest
    void shiftLeftAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value <<= 1;
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
    void shiftRight() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value >> 1;
            }
        });
    }

    @CrossDecompilerTest
    void shiftRightAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value >>= 1;
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
    void unsignedShiftRight() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value >>> 1;
            }
        });
    }

    @CrossDecompilerTest
    void unsignedShiftRightAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value >>>= 1;
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
    void equal() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value == 0;
            }
        });
    }

    @CrossDecompilerTest
    void notEqual() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value != 0;
            }
        });
    }

    @CrossDecompilerTest
    void less() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value < 1;
            }
        });
    }

    @CrossDecompilerTest
    void lessEqual() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value <= 1;
            }
        });
    }

    @CrossDecompilerTest
    void greater() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value > 1;
            }
        });
    }

    @CrossDecompilerTest
    void greaterEqual() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value >= 1;
            }
        });
    }

    @CrossDecompilerTest
    void postIncrement() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value++;
            }
        });
    }

    @CrossDecompilerTest
    void postIncrementValue() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                long next = value++;
                return value + next;
            }
        });
    }

    @CrossDecompilerTest
    void postIncrementLike() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value + 1;
            }
        });
    }

    @CrossDecompilerTest
    void preIncrement() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return ++value;
            }
        });
    }

    @CrossDecompilerTest
    void preIncrementInStatement() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return 2 * ++value;
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
    void classEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return long.class == long.class;
            }
        });
    }

    @CrossDecompilerTest
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