/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.primitives;

import org.junit.jupiter.api.Test;

import reincarnation.Code;
import reincarnation.CodeVerifier;

/**
 * @version 2018/10/10 9:05:14
 */
public class CharTest extends CodeVerifier {

    @Test
    public void primitive() {
        verify(new Code.CharParam() {

            @Override
            public char run(char value) {
                return value;
            }
        });
    }

    @Test
    public void equalToChar() {
        verify(new Code.CharParamBoolean() {

            @Override
            public boolean run(char value) {
                return value == 'a';
            }
        });
    }

    @Test
    public void equalToNumber1() {
        verify(new Code.CharParamBoolean() {

            @Override
            public boolean run(char value) {
                return value == 97; // a
            }
        });
    }

    @Test
    public void equalToNumber2() {
        verify(new Code.CharParamBoolean() {

            @Override
            public boolean run(char value) {
                return 97 == value; // a
            }
        });
    }

    @Test
    public void add() {
        verify(new Code.CharParamInt() {

            @Override
            public int run(char value) {
                return value + 1;
            }
        });
    }

    @Test
    public void subtract() {
        verify(new Code.CharParamInt() {

            @Override
            public int run(char value) {
                return value - 10;
            }
        });
    }

    @Test
    public void codition() {
        verify(new Code.CharParamBoolean() {

            @Override
            public boolean run(char value) {
                return value < 60;
            }
        });
    }

    @Test
    public void cast() {
        verify(new Code.CharParam() {

            @Override
            public char run(char value) {
                return (char) (value + 1);
            }
        });
    }
}
