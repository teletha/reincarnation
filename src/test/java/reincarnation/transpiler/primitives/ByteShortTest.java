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
 * Test the JavaScript output for byte, short, long, float and double operations.
 * 
 * @version 2026/10/11 11:20:00
 */
class ByteShortTest extends JavascriptCodeVerifier {

    @Test
    void byteModulo() throws Exception {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return (byte) (value % 3);
            }
        });
    }

    @Test
    void shortModulo() throws Exception {
        verify(new TestCode.ShortParam() {

            @Override
            public short run(short value) {
                return (short) (value % 5);
            }
        });
    }

    @Test
    void longModulo() throws Exception {
        verify(new TestCode.LongParam() {

            @Override
            public long run(@Param(longs = {-100000L, -7L, 0L, 7L, 100000L}) long value) {
                return value % 7;
            }
        });
    }

    @Test
    void longComparison() throws Exception {
        verify(new TestCode.LongParamBoolean() {

            @Override
            public boolean run(@Param(longs = {-100L, -1L, 0L, 1L, 100L}) long value) {
                return 0 <= value;
            }
        });
    }

    @Test
    void doubleAddition() throws Exception {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(@Param(doubles = {-2.5, -1.0, 0.0, 1.0, 2.5}) double value) {
                return value + 0.5;
            }
        });
    }

    @Test
    void floatAddition() throws Exception {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(@Param(floats = {-2.5F, -1F, 0F, 1F, 2.5F}) float value) {
                return value + 0.5F;
            }
        });
    }
}