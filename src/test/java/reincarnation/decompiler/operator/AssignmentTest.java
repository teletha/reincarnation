/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.operator;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class AssignmentTest extends CodeVerifier {

    @CrossDecompilerTest
    void onelineInExpresion() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                boolean result = (value = value + 3) % 2 == 0;

                return result ? value : value + 1;
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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