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
 * @version 2018/10/10 11:07:32
 */
class ShortTest extends CodeVerifier {

    @Test
    void zero() {
        verify(new TestCode.Short() {

            @Override
            public short run() {
                return 0;
            }
        });
    }

    @Test
    void one() {
        verify(new TestCode.Short() {

            @Override
            public short run() {
                return 1;
            }
        });
    }

    @Test
    void two() {
        verify(new TestCode.Short() {

            @Override
            public short run() {
                return 2;
            }
        });
    }

    @Test
    void three() {
        verify(new TestCode.Short() {

            @Override
            public short run() {
                return 3;
            }
        });
    }

    @Test
    void minus() {
        verify(new TestCode.Short() {

            @Override
            public short run() {
                return -1;
            }
        });
    }

    @Test
    void max() {
        verify(new TestCode.Short() {

            @Override
            public short run() {
                return java.lang.Short.MAX_VALUE;
            }
        });
    }

    @Test
    void min() {
        verify(new TestCode.Short() {

            @Override
            public short run() {
                return java.lang.Short.MIN_VALUE;
            }
        });
    }
}