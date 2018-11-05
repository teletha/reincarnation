/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.operator;

import org.junit.jupiter.api.Test;

import reincarnation.Code;
import reincarnation.CodeVerifier;
import reincarnation.Debuggable;

/**
 * @version 2018/10/22 19:17:57
 */
class AssignmentTest extends CodeVerifier {

    @Test
    void onelineInExpresion() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                boolean result = (value = value + 3) % 2 == 0;

                return result ? value : value + 1;
            }
        });
    }

    @Test
    void dual() {
        verify(new Code.IntParam() {

            @Debuggable
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
        verify(new Code.IntParam() {

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
        verify(new Code.IntParam() {

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
        verify(new Code.DoubleParam() {

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
        verify(new Code.LongParam() {

            private long a;

            @Override
            public long run(long value) {
                long a = this.a = value;

                return a + this.a;
            }
        });
    }
}
