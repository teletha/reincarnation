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
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the initial values of fields.
 * 
 * @version 2026/10/11 12:00:00
 */
class InitialValueTest extends JavascriptCodeVerifier {

    @Test
    void intValue() throws Exception {
        verify(new TestCode.Int() {

            private int count;

            @Override
            public int run() {
                return count;
            }
        });
    }

    @Test
    void longValue() throws Exception {
        verify(new TestCode.Long() {

            private long count;

            @Override
            public long run() {
                return count;
            }
        });
    }

    @Test
    void doubleValue() throws Exception {
        verify(new TestCode.Double() {

            private double ratio;

            @Override
            public double run() {
                return ratio;
            }
        });
    }

    @Test
    void booleanValue() throws Exception {
        verify(new TestCode.Boolean() {

            private boolean flag;

            @Override
            public boolean run() {
                return flag;
            }
        });
    }
}