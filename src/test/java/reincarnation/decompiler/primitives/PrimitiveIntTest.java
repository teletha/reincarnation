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

import reincarnation.Code.Int;
import reincarnation.CodeVerifier;

/**
 * @version 2018/10/04 8:36:16
 */
public class PrimitiveIntTest extends CodeVerifier {

    @Test
    void zero() {
        verify(new Int() {

            @Override
            public int run() {
                return 0;
            }
        });
    }

    @Test
    void one() {
        verify(new Int() {

            @Override
            public int run() {
                return 1;
            }
        });
    }

    @Test
    void two() {
        verify(new Int() {

            @Override
            public int run() {
                return 2;
            }
        });
    }

    @Test
    void three() {
        verify(new Int() {

            @Override
            public int run() {
                return 3;
            }
        });
    }

    @Test
    void four() {
        verify(new Int() {

            @Override
            public int run() {
                return 4;
            }
        });
    }

    @Test
    void five() {
        verify(new Int() {

            @Override
            public int run() {
                return 5;
            }
        });
    }

    @Test
    void six() {
        verify(new Int() {

            @Override
            public int run() {
                return 6;
            }
        });
    }

    @Test
    void seven() {
        verify(new Int() {

            @Override
            public int run() {
                return 7;
            }
        });
    }

    @Test
    void minusOne() {
        verify(new Int() {

            @Override
            public int run() {
                return -1;
            }
        });
    }

    @Test
    void minusTwo() {
        verify(new Int() {

            @Override
            public int run() {
                return -2;
            }
        });
    }

    @Test
    void minusThree() {
        verify(new Int() {

            @Override
            public int run() {
                return -3;
            }
        });
    }
}
