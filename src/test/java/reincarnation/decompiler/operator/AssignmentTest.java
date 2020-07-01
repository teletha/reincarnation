/*
 * Copyright (C) 2020 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.operator;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.TestCode;

class AssignmentTest extends CodeVerifier {

    @Test
    void onelineInExpresion() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                boolean result = (value = value + 3) % 2 == 0;

                return result ? value : value + 1;
            }
        });
    }

    @Test
    void dual() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                int a, b;

                a = b = value + 1;

                return a * b;
            }
        });
    }

    @Test
    void multiple() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                int a, b, c;

                a = b = c = value + 1;

                return a * b * c;
            }
        });
    }

    @Test
    void fieldDual() {
        verify(new TestCode.IntParam() {

            private int a;

            private int b;

            @Override
            public int run(int value) {
                a = b = value;

                return a + b;
            }
        });
    }

    @Test
    void fieldMultiple() {
        verify(new TestCode.DoubleParam() {

            private double a;

            private double b;

            private double c;

            @Override
            public double run(double value) {
                a = b = c = value;

                return a + b + c;
            }
        });
    }

    @Test
    void fieldMix() {
        verify(new TestCode.LongParam() {

            private long a;

            @Override
            public long run(long value) {
                long a = this.a = value;

                return a + this.a;
            }
        });
    }
}