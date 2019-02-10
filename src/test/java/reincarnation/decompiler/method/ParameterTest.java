/*
 * Copyright (C) 2019 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.method;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode.BooleanParam;
import reincarnation.TestCode.ByteParam;
import reincarnation.TestCode.CharParam;
import reincarnation.TestCode.DoubleParam;
import reincarnation.TestCode.FloatParam;
import reincarnation.TestCode.IntParam;
import reincarnation.TestCode.LongParam;
import reincarnation.TestCode.ShortParam;
import reincarnation.TestCode.TextParam;
import reincarnation.CodeVerifier;

/**
 * @version 2018/10/04 12:00:24
 */
class ParameterTest extends CodeVerifier {

    @Test
    void Int() {
        verify(new IntParam() {

            @Override
            public int run(int param) {
                return param;
            }
        });
    }

    @Test
    void Long() {
        verify(new LongParam() {

            @Override
            public long run(long param) {
                return param;
            }
        });
    }

    @Test
    void Float() {
        verify(new FloatParam() {

            @Override
            public float run(float param) {
                return param;
            }
        });
    }

    @Test
    void Double() {
        verify(new DoubleParam() {

            @Override
            public double run(double param) {
                return param;
            }
        });
    }

    @Test
    void Byte() {
        verify(new ByteParam() {

            @Override
            public byte run(byte param) {
                return param;
            }
        });
    }

    @Test
    void Short() {
        verify(new ShortParam() {

            @Override
            public short run(short param) {
                return param;
            }
        });
    }

    @Test
    void Character() {
        verify(new CharParam() {

            @Override
            public char run(char param) {
                return param;
            }
        });
    }

    @Test
    void Boolean() {
        verify(new BooleanParam() {

            @Override
            public boolean run(boolean param) {
                return param;
            }
        });
    }

    @Test
    void String() {
        verify(new TextParam() {

            @Override
            public String run(String param) {
                return param;
            }
        });
    }
}
