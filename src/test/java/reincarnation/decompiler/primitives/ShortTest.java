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

class ShortTest extends CodeVerifier {

    @CrossDecompilerTest
    void zero() {
        verify(new TestCode.Short() {

            @Override
            public short run() {
                return 0;
            }
        });
    }

    @CrossDecompilerTest
    void one() {
        verify(new TestCode.Short() {

            @Override
            public short run() {
                return 1;
            }
        });
    }

    @CrossDecompilerTest
    void two() {
        verify(new TestCode.Short() {

            @Override
            public short run() {
                return 2;
            }
        });
    }

    @CrossDecompilerTest
    void three() {
        verify(new TestCode.Short() {

            @Override
            public short run() {
                return 3;
            }
        });
    }

    @CrossDecompilerTest
    void minus() {
        verify(new TestCode.Short() {

            @Override
            public short run() {
                return -1;
            }
        });
    }

    @CrossDecompilerTest
    void max() {
        verify(new TestCode.Short() {

            @Override
            public short run() {
                return java.lang.Short.MAX_VALUE;
            }
        });
    }

    @CrossDecompilerTest
    void min() {
        verify(new TestCode.Short() {

            @Override
            public short run() {
                return java.lang.Short.MIN_VALUE;
            }
        });
    }

    @CrossDecompilerTest
    void classEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return short.class == short.class;
            }
        });
    }

    @CrossDecompilerTest
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