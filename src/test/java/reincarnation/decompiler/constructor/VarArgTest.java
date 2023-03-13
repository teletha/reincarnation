/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.constructor;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.TestCode;

class VarArgTest extends CodeVerifier {

    private static class Total {
        int sum;

        private Total(int... values) {
            int total = 0;
            for (int i = 0; i < values.length; i++) {
                total += values[i];
            }
            this.sum = total;
        }

        private Total(long base, int... values) {
            int total = (int) base;
            for (int i = 0; i < values.length; i++) {
                total += values[i];
            }
            this.sum = total;
        }
    }

    @Test
    void primitive() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return new Total(1, 2, 3).sum;
            }
        });
    }

    @Test
    void withHeaderParam() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return new Total(10L, 1, 2, 3).sum;
            }
        });
    }
}