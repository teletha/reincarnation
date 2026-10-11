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
 * Test the JavaScript output for float operations.
 * 
 * @version 2026/10/11 13:00:00
 */
class FloatTest extends JavascriptCodeVerifier {

    @Test
    void identityNoParam() throws Exception {
        verify(new TestCode.Float() {

            @Override
            public float run() {
                return 1.5F;
            }
        });
    }

    @Test
    void identity() throws Exception {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(@Param(floats = {-2.5F, -1F, 0F, 1F, 2.5F}) float value) {
                return value;
            }
        });
    }

    @Test
    void addition() throws Exception {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(@Param(floats = {-2.5F, -1F, 0F, 1F, 2.5F}) float value) {
                return value + 0.5F;
            }
        });
    }

    @Test
    void multiplication() throws Exception {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(@Param(floats = {-2F, 0F, 1.5F}) float value) {
                return value * 1.5F;
            }
        });
    }

    @Test
    void comparison() throws Exception {
        verify(new TestCode.FloatParamBoolean() {

            @Override
            public boolean run(float value) {
                return 0 < value;
            }
        });
    }
}