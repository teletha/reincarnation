/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.method;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.TestCode;

class VarArgTest extends CodeVerifier {

    @Test
    void primitive() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return total(1, 2, 3);
            }

            private int total(int... values) {
                int total = 0;
                for (int i = 0; i < values.length; i++) {
                    total += values[i];
                }
                return total;
            }
        });
    }

    @Test
    void wrapper() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return total(1, 2, 3);
            }

            private int total(Integer... values) {
                int total = 0;
                for (int i = 0; i < values.length; i++) {
                    total += values[i];
                }
                return total;
            }
        });
    }

    @Test
    void withHeaderParam() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return total(20, 1, 2, 3);
            }

            private int total(int base, int... values) {
                int total = base;
                for (int i = 0; i < values.length; i++) {
                    total += values[i];
                }
                return total;
            }
        });
    }
}