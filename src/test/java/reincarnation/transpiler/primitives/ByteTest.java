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
 * Test the JavaScript output for byte operations.
 * 
 * @version 2026/10/11 13:00:00
 */
class ByteTest extends JavascriptCodeVerifier {

    @Test
    void identityNoParam() throws Exception {
        verify(new TestCode.Byte() {

            @Override
            public byte run() {
                return 3;
            }
        });
    }

    @Test
    void identity() throws Exception {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return value;
            }
        });
    }

    @Test
    void modulo() throws Exception {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return (byte) (value % 3);
            }
        });
    }

    @Test
    void bitwiseAnd() throws Exception {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return (byte) (value & 0x0F);
            }
        });
    }

    @Test
    void comparison() throws Exception {
        verify(new TestCode.ByteParamBoolean() {

            @Override
            public boolean run(byte value) {
                return 0 < value;
            }
        });
    }
}