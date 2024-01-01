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

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.TestCode;

/**
 * @version 2018/10/10 8:46:48
 */
public class ByteTest extends CodeVerifier {

    @Test
    public void zero() {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return 0;
            }
        });
    }

    @Test
    public void one() {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return 1;
            }
        });
    }

    @Test
    public void two() {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return 2;
            }
        });
    }

    @Test
    public void three() {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return 3;
            }
        });
    }

    @Test
    public void minus() {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return -1;
            }
        });
    }

    @Test
    public void max() {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return java.lang.Byte.MAX_VALUE;
            }
        });
    }

    @Test
    public void min() {
        verify(new TestCode.ByteParam() {

            @Override
            public byte run(byte value) {
                return java.lang.Byte.MIN_VALUE;
            }
        });
    }

    @Test
    void classEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return byte.class == byte.class;
            }
        });
    }

    @Test
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