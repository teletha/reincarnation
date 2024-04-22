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
import reincarnation.DecompilableTest;
import reincarnation.TestCode;

class BooleanTest extends CodeVerifier {

    @DecompilableTest
    void value() {
        verify(new TestCode.BooleanParam() {

            @Override
            public boolean run(boolean value) {
                return value;
            }
        });
    }

    @DecompilableTest
    void negate() {
        verify(new TestCode.BooleanParam() {

            @Override
            public boolean run(boolean value) {
                return !value;
            }
        });
    }

    @DecompilableTest
    void negateTwice() {
        verify(new TestCode.BooleanParam() {

            @Override
            public boolean run(boolean value) {
                return !!value;
            }
        });
    }

    @DecompilableTest
    void negateInVariable() {
        verify(new TestCode.BooleanParam() {

            @Override
            public boolean run(boolean value) {
                value = !value;

                return value;
            }
        });
    }

    @DecompilableTest
    void negateTwiceInVariable() {
        verify(new TestCode.BooleanParam() {

            @Override
            public boolean run(boolean value) {
                value = !!value;

                return value;
            }
        });
    }

    @DecompilableTest
    void classEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return boolean.class == boolean.class;
            }
        });
    }

    @DecompilableTest
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