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
 * Test the JavaScript output for short operations.
 * 
 * @version 2026/10/11 13:00:00
 */
class ShortTest extends JavascriptCodeVerifier {

    @Test
    void identityNoParam() throws Exception {
        verify(new TestCode.Short() {

            @Override
            public short run() {
                return 3;
            }
        });
    }

    @Test
    void identity() throws Exception {
        verify(new TestCode.ShortParam() {

            @Override
            public short run(short value) {
                return value;
            }
        });
    }

    @Test
    void modulo() throws Exception {
        verify(new TestCode.ShortParam() {

            @Override
            public short run(short value) {
                return (short) (value % 5);
            }
        });
    }

    @Test
    void addition() throws Exception {
        verify(new TestCode.ShortParam() {

            @Override
            public short run(@reincarnation.TestCode.Param(shorts = {-5, 0, 3}) short value) {
                return (short) (value + 1);
            }
        });
    }

    @Test
    void comparison() throws Exception {
        verify(new TestCode.ShortParamBoolean() {

            @Override
            public boolean run(short value) {
                return 0 < value;
            }
        });
    }
}