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

class InitialValueTest extends CodeVerifier {

    @DecompilableTest
    void Interger() {
        verify(new TestCode.Int() {

            private int uninitialized;

            @Override
            public int run() {
                return uninitialized;
            }
        });
    }

    @DecompilableTest
    void Long() {
        verify(new TestCode.Long() {

            private long uninitialized;

            @Override
            public long run() {
                return uninitialized;
            }
        });
    }

    @DecompilableTest
    void Float() {
        verify(new TestCode.Float() {

            private float uninitialized;

            @Override
            public float run() {
                return uninitialized;
            }
        });
    }

    @DecompilableTest
    void Double() {
        verify(new TestCode.Double() {

            private double uninitialized;

            @Override
            public double run() {
                return uninitialized;
            }
        });
    }

    @DecompilableTest
    void Byte() {
        verify(new TestCode.Byte() {

            private byte uninitialized;

            @Override
            public byte run() {
                return uninitialized;
            }
        });
    }

    @DecompilableTest
    void Short() {
        verify(new TestCode.Short() {

            private short uninitialized;

            @Override
            public short run() {
                return uninitialized;
            }
        });
    }

    @DecompilableTest
    void Char() {
        verify(new TestCode.Char() {

            private char uninitialized;

            @Override
            public char run() {
                return uninitialized;
            }
        });
    }

    @DecompilableTest
    void Boolean() {
        verify(new TestCode.Boolean() {

            private boolean uninitialized;

            @Override
            public boolean run() {
                return uninitialized;
            }
        });
    }

    @DecompilableTest
    void Static() {
        verify(new Static());
    }

    /**
     * @version 2018/10/10 11:14:08
     */
    private static class Static implements TestCode.Int {

        private static int uninitialized;

        @Override
        public int run() {
            return uninitialized;
        }
    }
}