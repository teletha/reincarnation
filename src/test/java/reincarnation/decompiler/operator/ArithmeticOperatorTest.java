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
import reincarnation.DecompilableTest;
import reincarnation.TestCode;

class ArithmeticOperatorTest extends CodeVerifier {

    @DecompilableTest
    void AdditionLeft() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return 1 + value;
            }
        });
    }

    @DecompilableTest
    void AdditonRight() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value + 1;
            }
        });
    }

    @DecompilableTest
    void SubtractionLeft() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return 10 - value;
            }
        });
    }

    @DecompilableTest
    void SubtractionRight() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value - 10;
            }
        });
    }

    @DecompilableTest
    void MultiplicationLeft() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value * 3;
            }
        });
    }

    @DecompilableTest
    void MultiplicationRight() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return -3 * value;
            }
        });
    }

    @DecompilableTest
    void DivisionLeft() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value / 2;
            }
        });
    }

    @DecompilableTest
    void DivisionRight() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(@Param(doubles = {1, 3, 4}) double value) {
                return 12 / value;
            }
        });
    }

    @DecompilableTest
    void ReminderLeft() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value % 7;
            }
        });
    }

    @DecompilableTest
    void ReminderRight() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 1, to = 10) int value) {
                return 40 % value;
            }
        });
    }

    @DecompilableTest
    void Complex() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 1, to = 10) int value) {
                return (value + 2) * 2;
            }
        });
    }

    @DecompilableTest
    void Negative() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return -value;
            }
        });
    }
}