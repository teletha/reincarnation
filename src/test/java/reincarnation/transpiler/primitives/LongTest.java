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
}