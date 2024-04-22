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

public class ByteTest extends CodeVerifier {

    @CompilableTest
    public void zero() {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return 0;
            }
        });
    }

    @CompilableTest
    public void one() {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return 1;
            }
        });
    }

    @CompilableTest
    public void two() {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return 2;
            }
        });
    }

    @CompilableTest
    public void three() {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return 3;
            }
        });
    }

    @CompilableTest
    public void minus() {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return -1;
            }
        });
    }

    @CompilableTest
    public void max() {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return java.lang.Byte.MAX_VALUE;
            }
        });
    }

    @CompilableTest
    public void min() {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return java.lang.Byte.MIN_VALUE;
            }
        });
    }

    @CompilableTest
    void classEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return byte.class == byte.class;
            }
        });
    }

    @CompilableTest
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