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

public class CharTest extends CodeVerifier {

    @CrossDecompilerTest
    public void primitive() {
        verify(new TestCode.CharParam() {

            @Override
            public char run(char value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    public void equalToChar() {
        verify(new TestCode.CharParamBoolean() {

            @Override
            public boolean run(char value) {
                return value == 'a';
            }
        });
    }

    @CrossDecompilerTest
    public void equalToNumber1() {
        verify(new TestCode.CharParamBoolean() {

            @Override
            public boolean run(char value) {
                return value == 97; // a
            }
        });
    }

    @CrossDecompilerTest
    public void equalToNumber2() {
        verify(new TestCode.CharParamBoolean() {

            @Override
            public boolean run(char value) {
                return 97 == value; // a
            }
        });
    }

    @CrossDecompilerTest
    public void add() {
        verify(new TestCode.CharParamInt() {

            @Override
            public int run(char value) {
                return value + 1;
            }
        });
    }

    @CrossDecompilerTest
    public void subtract() {
        verify(new TestCode.CharParamInt() {

            @Override
            public int run(char value) {
                return value - 10;
            }
        });
    }

    @CrossDecompilerTest
    public void codition() {
        verify(new TestCode.CharParamBoolean() {

            @Override
            public boolean run(char value) {
                return value < 60;
            }
        });
    }

    @CrossDecompilerTest
    public void cast() {
        verify(new TestCode.CharParam() {

            @Override
            public char run(char value) {
                return (char) (value + 1);
            }
        });
    }

    @CrossDecompilerTest
    void classEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return char.class == char.class;
            }
        });
    }

    @CrossDecompilerTest
    void arrayClassEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                char[] array = {};
                return char[].class == array.getClass();
            }
        });
    }
}