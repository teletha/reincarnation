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

class ShortTest extends CodeVerifier {

    @DecompilableTest
    void zero() {
        verify(new TestCode.Short() {

            @Override
            public short run() {
                return 0;
            }
        });
    }

    @DecompilableTest
    void one() {
        verify(new TestCode.Short() {

            @Override
            public short run() {
                return 1;
            }
        });
    }

    @DecompilableTest
    void two() {
        verify(new TestCode.Short() {

            @Override
            public short run() {
                return 2;
            }
        });
    }

    @DecompilableTest
    void three() {
        verify(new TestCode.Short() {

            @Override
            public short run() {
                return 3;
            }
        });
    }

    @DecompilableTest
    void minus() {
        verify(new TestCode.Short() {

            @Override
            public short run() {
                return -1;
            }
        });
    }

    @DecompilableTest
    void max() {
        verify(new TestCode.Short() {

            @Override
            public short run() {
                return java.lang.Short.MAX_VALUE;
            }
        });
    }

    @DecompilableTest
    void min() {
        verify(new TestCode.Short() {

            @Override
            public short run() {
                return java.lang.Short.MIN_VALUE;
            }
        });
    }

    @DecompilableTest
    void classEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return short.class == short.class;
            }
        });
    }

    @DecompilableTest
    void arrayClassEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                short[] array = {};
                return short[].class == array.getClass();
            }
        });
    }
}