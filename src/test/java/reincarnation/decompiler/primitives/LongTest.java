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
import reincarnation.CompilableTest;
import reincarnation.TestCode;

class LongTest extends CodeVerifier {

    @CompilableTest
    void zero() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return 0L;
            }
        });
    }

    @CompilableTest
    void one() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return 1L;
            }
        });
    }

    @CompilableTest
    void two() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return 2L;
            }
        });
    }

    @CompilableTest
    void three() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return 3L;
            }
        });
    }

    @CompilableTest
    void minus() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return -1L;
            }
        });
    }

    @CompilableTest
    void max() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return java.lang.Long.MAX_VALUE;
            }
        });
    }

    @CompilableTest
    void min() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return java.lang.Long.MIN_VALUE;
            }
        });
    }

    @CompilableTest
    void add() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long param) {
                return param + 1L;
            }
        });
    }

    @CompilableTest
    void addAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value += 2L;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void negate() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return -value;
            }
        });
    }

    @CompilableTest
    void subtract() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value - 1L;
            }
        });
    }

    @CompilableTest
    void subtractAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value -= 2L;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void multiply() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value * 2L;
            }
        });
    }

    @CompilableTest
    void multiplyAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value *= 2L;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void divide() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value / 2L;
            }
        });
    }

    @CompilableTest
    void divideAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value /= 2L;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void modulo() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value % 2L;
            }
        });
    }

    @CompilableTest
    void moduloAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value %= 2L;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void bitFlag() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return (value & 1) == 0;
            }
        });
    }

    @CompilableTest
    void bitAnd() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value & 0x010101;
            }
        });
    }

    @CompilableTest
    void bitAndAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value &= 0x010101;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void bitOr() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value | 0x010101;
            }
        });
    }

    @CompilableTest
    void bitOrAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value |= 0x010101;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void bitXor() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value ^ 0x010101;
            }
        });
    }

    @CompilableTest
    void bitXorAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value ^= 0x010101;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void bitNot() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return ~value;
            }
        });
    }

    @CompilableTest
    void shiftLeft() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value << 1;
            }
        });
    }

    @CompilableTest
    void shiftLeftAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value <<= 1;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void shiftRight() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value >> 1;
            }
        });
    }

    @CompilableTest
    void shiftRightAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value >>= 1;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void unsignedShiftRight() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value >>> 1;
            }
        });
    }

    @CompilableTest
    void unsignedShiftRightAssignable() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value >>>= 1;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
    void equal() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value == 0;
            }
        });
    }

    @CompilableTest
    void notEqual() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value != 0;
            }
        });
    }

    @CompilableTest
    void less() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value < 1;
            }
        });
    }

    @CompilableTest
    void lessEqual() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value <= 1;
            }
        });
    }

    @CompilableTest
    void greater() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value > 1;
            }
        });
    }

    @CompilableTest
    void greaterEqual() {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(long value) {
                return value >= 1;
            }
        });
    }

    @CompilableTest
    void postIncrement() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value++;
            }
        });
    }

    @CompilableTest
    void postIncrementValue() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                long next = value++;
                return value + next;
            }
        });
    }

    @CompilableTest
    void postIncrementLike() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return value + 1;
            }
        });
    }

    @CompilableTest
    void preIncrement() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return ++value;
            }
        });
    }

    @CompilableTest
    void preIncrementInStatement() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return 2 * ++value;
            }
        });
    }

    @CompilableTest
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

    @CompilableTest
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

    @CompilableTest
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

    @CompilableTest
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

    @CompilableTest
    void classEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return long.class == long.class;
            }
        });
    }

    @CompilableTest
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