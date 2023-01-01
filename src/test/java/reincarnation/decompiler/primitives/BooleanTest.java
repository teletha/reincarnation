/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.primitives;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.CodeVerifier;

/**
 * @version 2018/10/06 1:06:47
 */
class BooleanTest extends CodeVerifier {

    @Test
    void value() {
        verify(new TestCode.BooleanParam() {

            @Override
            public boolean run(boolean value) {
                return value;
            }
        });
    }

    @Test
    void negate() {
        verify(new TestCode.BooleanParam() {

            @Override
            public boolean run(boolean value) {
                return !value;
            }
        });
    }

    @Test
    void negateTwice() {
        verify(new TestCode.BooleanParam() {

            @Override
            public boolean run(boolean value) {
                return !!value;
            }
        });
    }

    @Test
    void negateInVariable() {
        verify(new TestCode.BooleanParam() {

            @Override
            public boolean run(boolean value) {
                value = !value;

                return value;
            }
        });
    }

    @Test
    void negateTwiceInVariable() {
        verify(new TestCode.BooleanParam() {

            @Override
            public boolean run(boolean value) {
                value = !!value;

                return value;
            }
        });
    }

    @Test
    void classEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return boolean.class == boolean.class;
            }
        });
    }

    @Test
    void arrayClassEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                boolean[] array = {};
                return boolean[].class == array.getClass();
            }
        });
    }
}