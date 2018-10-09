/*
 * Copyright (C) 2016 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.primitives;

import org.junit.jupiter.api.Test;

import reincarnation.Code;
import reincarnation.CodeVerifier;

/**
 * @version 2012/12/01 2:02:40
 */
public class ByteTest extends CodeVerifier {

    @Test
    public void zero() {
        verify(new Code.ByteParam() {

            @Override
            public byte run(byte value) {
                return 0;
            }
        });
    }

    @Test
    public void one() {
        verify(new Code.ByteParam() {

            @Override
            public byte run(byte value) {
                return 1;
            }
        });
    }

    @Test
    public void two() {
        verify(new Code.ByteParam() {

            @Override
            public byte run(byte value) {
                return 2;
            }
        });
    }

    @Test
    public void three() {
        verify(new Code.ByteParam() {

            @Override
            public byte run(byte value) {
                return 3;
            }
        });
    }

    @Test
    public void minus() {
        verify(new Code.ByteParam() {

            @Override
            public byte run(byte value) {
                return -1;
            }
        });
    }

    @Test
    public void max() {
        verify(new Code.ByteParam() {

            @Override
            public byte run(byte value) {
                return java.lang.Byte.MAX_VALUE;
            }
        });
    }

    @Test
    public void min() {
        verify(new Code.ByteParam() {

            @Override
            public byte run(byte value) {
                return java.lang.Byte.MIN_VALUE;
            }
        });
    }
}
