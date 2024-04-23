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

public class ByteTest extends CodeVerifier {

    @CrossDecompilerTest
    public void zero() {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return 0;
            }
        });
    }

    @CrossDecompilerTest
    public void one() {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return 1;
            }
        });
    }

    @CrossDecompilerTest
    public void two() {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return 2;
            }
        });
    }

    @CrossDecompilerTest
    public void three() {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return 3;
            }
        });
    }

    @CrossDecompilerTest
    public void minus() {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return -1;
            }
        });
    }

    @CrossDecompilerTest
    public void max() {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return java.lang.Byte.MAX_VALUE;
            }
        });
    }

    @CrossDecompilerTest
    public void min() {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return java.lang.Byte.MIN_VALUE;
            }
        });
    }

    @CrossDecompilerTest
    void classEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return byte.class == byte.class;
            }
        });
    }

    @CrossDecompilerTest
    void arrayClassEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                byte[] array = {};
                return byte[].class == array.getClass();
            }
        });
    }
}