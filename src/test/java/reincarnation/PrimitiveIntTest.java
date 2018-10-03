/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import org.junit.jupiter.api.Test;

/**
 * @version 2018/10/04 8:36:16
 */
public class PrimitiveIntTest extends CodeVerifier {

    @Test
    void zero() {
        verify(new Code.Int() {

            @Override
            public int run() {
                return 0;
            }
        });
    }

    @Test
    void one() {
        verify(new Code.Int() {

            @Override
            public int run() {
                return 1;
            }
        });
    }

    @Test
    void two() {
        verify(new Code.Int() {

            @Override
            public int run() {
                return 2;
            }
        });
    }

    @Test
    void three() {
        verify(new Code.Int() {

            @Override
            public int run() {
                return 3;
            }
        });
    }

    @Test
    void four() {
        verify(new Code.Int() {

            @Override
            public int run() {
                return 4;
            }
        });
    }

    @Test
    void five() {
        verify(new Code.Int() {

            @Override
            public int run() {
                return 5;
            }
        });
    }

    @Test
    void six() {
        verify(new Code.Int() {

            @Override
            public int run() {
                return 6;
            }
        });
    }

    @Test
    void seven() {
        verify(new Code.Int() {

            @Override
            public int run() {
                return 7;
            }
        });
    }

    @Test
    void minusOne() {
        verify(new Code.Int() {

            @Override
            public int run() {
                return -1;
            }
        });
    }

    @Test
    void minusTwo() {
        verify(new Code.Int() {

            @Override
            public int run() {
                return -2;
            }
        });
    }

    @Test
    void minusThree() {
        verify(new Code.Int() {

            @Override
            public int run() {
                return -3;
            }
        });
    }
}
