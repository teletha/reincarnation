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

public class CharTest extends CodeVerifier {

    @CompilableTest
    public void primitive() {
        verify(new TestCode.CharParam() {

            @Override
            public char run(char value) {
                return value;
            }
        });
    }

    @CompilableTest
    public void equalToChar() {
        verify(new TestCode.CharParamBoolean() {

            @Override
            public boolean run(char value) {
                return value == 'a';
            }
        });
    }

    @CompilableTest
    public void equalToNumber1() {
        verify(new TestCode.CharParamBoolean() {

            @Override
            public boolean run(char value) {
                return value == 97; // a
            }
        });
    }

    @CompilableTest
    public void equalToNumber2() {
        verify(new TestCode.CharParamBoolean() {

            @Override
            public boolean run(char value) {
                return 97 == value; // a
            }
        });
    }

    @CompilableTest
    public void add() {
        verify(new TestCode.CharParamInt() {

            @Override
            public int run(char value) {
                return value + 1;
            }
        });
    }

    @CompilableTest
    public void subtract() {
        verify(new TestCode.CharParamInt() {

            @Override
            public int run(char value) {
                return value - 10;
            }
        });
    }

    @CompilableTest
    public void codition() {
        verify(new TestCode.CharParamBoolean() {

            @Override
            public boolean run(char value) {
                return value < 60;
            }
        });
    }

    @CompilableTest
    public void cast() {
        verify(new TestCode.CharParam() {

            @Override
            public char run(char value) {
                return (char) (value + 1);
            }
        });
    }

    @CompilableTest
    void classEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return char.class == char.class;
            }
        });
    }

    @CompilableTest
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