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
 * Test the JavaScript output for double operations.
 * 
 * @version 2026/10/11 13:00:00
 */
class DoubleTest extends JavascriptCodeVerifier {

    @Test
    void identityNoParam() throws Exception {
        verify(new TestCode.Double() {

            @Override
            public double run() {
                return 1.5;
            }
        });
    }

    @Test
    void identity() throws Exception {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(@Param(doubles = {-2.5, -1.0, 0.0, 1.0, 2.5}) double value) {
                return value;
            }
        });
    }

    @Test
    void addition() throws Exception {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(@Param(doubles = {-2.5, -1.0, 0.0, 1.0, 2.5}) double value) {
                return value + 0.5;
            }
        });
    }

    @Test
    void multiplication() throws Exception {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(@Param(doubles = {-2.0, 0.0, 1.5}) double value) {
                return value * 1.5;
            }
        });
    }

    @Test
    void division() throws Exception {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(@Param(doubles = {-2.0, 0.0, 3.0}) double value) {
                return value / 2.0;
            }
        });
    }

    @Test
    void absolute() throws Exception {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(@Param(doubles = {-2.5, -1.0, 0.0, 1.0, 2.5}) double value) {
                return Math.abs(value);
            }
        });
    }

    @Test
    void comparison() throws Exception {
        verify(new TestCode.DoubleParamBoolean() {

            @Override
            public boolean run(double value) {
                return 0 < value;
            }
        });
    }
}