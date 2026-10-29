/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.primitives;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.TestCode.Param;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for long operations.
 * 
 * @version 2026/10/11 13:00:00
 */
class LongTest extends JavascriptCodeVerifier {

    @Test
    void identityNoParam() throws Exception {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return 100L + 2L;
            }
        });
    }

    @Test
    void identity() throws Exception {
        verify(new TestCode.LongParam() {

            @Override
            public long run(@Param(longs = {-100L, 0L, 100L}) long value) {
                return value;
            }
        });
    }

    @Test
    void addition() throws Exception {
        verify(new TestCode.LongParam() {

            @Override
            public long run(@Param(longs = {-100L, 0L, 100L}) long value) {
                return value + 1;
            }
        });
    }

    @Test
    void subtraction() throws Exception {
        verify(new TestCode.LongParam() {

            @Override
            public long run(@Param(longs = {-100L, 0L, 100L}) long value) {
                return value - 1;
            }
        });
    }

    @Test
    void modulo() throws Exception {
        verify(new TestCode.LongParam() {

            @Override
            public long run(@Param(longs = {-100L, -7L, 0L, 7L, 100L}) long value) {
                return value % 7;
            }
        });
    }

    @Test
    void comparison() throws Exception {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(@Param(longs = {-100L, 0L, 100L}) long value) {
                return 0 < value;
            }
        });
    }

    @Test
    void overflow() throws Exception {
        verify(new TestCode.LongParam() {

            @Override
            public long run(@Param(longs = {java.lang.Long.MAX_VALUE, java.lang.Long.MIN_VALUE}) long value) {
                return value + 1;
            }
        });
    }

    @Test
    void underflow() throws Exception {
        verify(new TestCode.LongParam() {

            @Override
            public long run(@Param(longs = {java.lang.Long.MAX_VALUE, java.lang.Long.MIN_VALUE}) long value) {
                return value - 1;
            }
        });
    }

    @Test
    void multiplication() throws Exception {
        verify(new TestCode.LongParam() {

            @Override
            public long run(@Param(longs = {java.lang.Long.MAX_VALUE, java.lang.Long.MIN_VALUE}) long value) {
                return value * 3;
            }
        });
    }

    @Test
    void division() throws Exception {
        verify(new TestCode.LongParam() {

            @Override
            public long run(@Param(longs = {java.lang.Long.MAX_VALUE, java.lang.Long.MIN_VALUE}) long value) {
                return value / -1;
            }
        });
    }

    @Test
    void unsignedShift() throws Exception {
        verify(new TestCode.LongParam() {

            @Override
            public long run(@Param(longs = {-1L, java.lang.Long.MIN_VALUE}) long value) {
                return value >>> 1;
            }
        });
    }

    @Test
    void shift() throws Exception {
        verify(new TestCode.LongParam() {

            @Override
            public long run(@Param(longs = {-1L, java.lang.Long.MIN_VALUE}) long value) {
                return value << 4 >> 4;
            }
        });
    }

    @Test
    void bitwise() throws Exception {
        verify(new TestCode.LongParam() {

            @Override
            public long run(@Param(longs = {java.lang.Long.MAX_VALUE, java.lang.Long.MIN_VALUE}) long value) {
                return (value & 0x00FF00FF00FF00FFL) ^ 0x0F0F0F0F0F0F0F0FL;
            }
        });
    }

    @Test
    void parseLong() throws Exception {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return java.lang.Long.parseLong("9223372036854775807") + java.lang.Long.parseLong("-9223372036854775808");
            }
        });
    }

    @Test
    void parseLongRadix() throws Exception {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return java.lang.Long.parseLong("7fffffffffffffff", 16);
            }
        });
    }

    @Test
    void toStringLong() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return java.lang.Long.toString(java.lang.Long.MIN_VALUE).length();
            }
        });
    }

    @Test
    void toUnsignedString() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return java.lang.Long.toUnsignedString(-1L);
            }
        });
    }

    @Test
    void toHexString() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return java.lang.Long.toHexString(java.lang.Long.MIN_VALUE);
            }
        });
    }

    @Test
    void hashCodeLong() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return java.lang.Long.hashCode(0x123456789ABCDEF0L);
            }
        });
    }

    @Test
    void compareLong() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return java.lang.Long.compare(java.lang.Long.MIN_VALUE, java.lang.Long.MAX_VALUE);
            }
        });
    }

    @Test
    void bitCountLong() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return java.lang.Long.bitCount(-1L);
            }
        });
    }

    @Test
    void widening() throws Exception {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                long a = 1;
                int b = 2;
                long c = b;
                return a + c;
            }
        });
    }

    @Test
    void concatenation() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return "x" + 1234567890123456789L;
            }
        });
    }

    @Test
    void longField() throws Exception {
        verify(new LongField());
    }

    @Test
    void mathAbs() throws Exception {
        verify(new TestCode.LongParam() {

            @Override
            public long run(@Param(longs = {java.lang.Long.MIN_VALUE, -100L, 0L, 100L, java.lang.Long.MAX_VALUE}) long value) {
                return Math.abs(value);
            }
        });
    }

    @Test
    void mathFloorDiv() throws Exception {
        verify(new TestCode.LongParam() {

            @Override
            public long run(@Param(longs = {-7L, -1L, 0L, 1L, 7L}) long value) {
                return Math.floorDiv(value, -3L) + Math.floorMod(value, -3L);
            }
        });
    }

    /** Verify the long instance field. */
    static class LongField implements TestCode.Long {

        long value = 1234567890123456789L;

        @Override
        public long run() {
            return value * 2;
        }
    }
}

