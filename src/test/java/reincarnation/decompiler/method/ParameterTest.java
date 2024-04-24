/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.method;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode.BooleanParam;
import reincarnation.TestCode.ByteParam;
import reincarnation.TestCode.CharParam;
import reincarnation.TestCode.DoubleParam;
import reincarnation.TestCode.FloatParam;
import reincarnation.TestCode.IntParam;
import reincarnation.TestCode.LongParam;
import reincarnation.TestCode.ShortParam;
import reincarnation.TestCode.TextParam;

class ParameterTest extends CodeVerifier {

    @CrossDecompilerTest
    void Int() {
        verify(new IntParam() {

            @Override
            public int run(int param) {
                return param;
            }
        });
    }

    @CrossDecompilerTest
    void Long() {
        verify(new LongParam() {

            @Override
            public long run(long param) {
                return param;
            }
        });
    }

    @CrossDecompilerTest
    void Float() {
        verify(new FloatParam() {

            @Override
            public float run(float param) {
                return param;
            }
        });
    }

    @CrossDecompilerTest
    void Double() {
        verify(new DoubleParam() {

            @Override
            public double run(double param) {
                return param;
            }
        });
    }

    @CrossDecompilerTest
    void Byte() {
        verify(new ByteParam() {

            @Override
            public byte run(byte param) {
                return param;
            }
        });
    }

    @CrossDecompilerTest
    void Short() {
        verify(new ShortParam() {

            @Override
            public short run(short param) {
                return param;
            }
        });
    }

    @CrossDecompilerTest
    void Character() {
        verify(new CharParam() {

            @Override
            public char run(char param) {
                return param;
            }
        });
    }

    @CrossDecompilerTest
    void Boolean() {
        verify(new BooleanParam() {

            @Override
            public boolean run(boolean param) {
                return param;
            }
        });
    }

    @CrossDecompilerTest
    void String() {
        verify(new TextParam() {

            @Override
            public String run(String param) {
                return param;
            }
        });
    }
}