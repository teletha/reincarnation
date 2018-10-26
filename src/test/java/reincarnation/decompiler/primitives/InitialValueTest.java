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
 * @version 2018/10/10 11:14:35
 */
class InitialValueTest extends CodeVerifier {

    @Test
    void Interger() {
        verify(new Code.Int() {

            private int uninitialized;

            @Override
            public int run() {
                return uninitialized;
            }
        });
    }

    @Test
    void Long() {
        verify(new Code.Long() {

            private long uninitialized;

            @Override
            public long run() {
                return uninitialized;
            }
        });
    }

    @Test
    void Float() {
        verify(new Code.Float() {

            private float uninitialized;

            @Override
            public float run() {
                return uninitialized;
            }
        });
    }

    @Test
    void Double() {
        verify(new Code.Double() {

            private double uninitialized;

            @Override
            public double run() {
                return uninitialized;
            }
        });
    }

    @Test
    void Byte() {
        verify(new Code.Byte() {

            private byte uninitialized;

            @Override
            public byte run() {
                return uninitialized;
            }
        });
    }

    @Test
    void Short() {
        verify(new Code.Short() {

            private short uninitialized;

            @Override
            public short run() {
                return uninitialized;
            }
        });
    }

    @Test
    void Char() {
        verify(new Code.Char() {

            private char uninitialized;

            @Override
            public char run() {
                return uninitialized;
            }
        });
    }

    @Test
    void Boolean() {
        verify(new Code.Boolean() {

            private boolean uninitialized;

            @Override
            public boolean run() {
                return uninitialized;
            }
        });
    }

    @Test
    void Static() {
        verify(new Static());
    }

    /**
     * @version 2018/10/10 11:14:08
     */
    private static class Static implements Code.Int {

        private static int uninitialized;

        @Override
        public int run() {
            return uninitialized;
        }
    }
}
