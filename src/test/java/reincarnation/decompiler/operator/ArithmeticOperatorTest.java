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

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.CodeVerifier;

/**
 * @version 2018/10/22 16:58:22
 */
class ArithmeticOperatorTest extends CodeVerifier {

    @Test
    void AdditionLeft() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return 1 + value;
            }
        });
    }

    @Test
    void AdditonRight() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value + 1;
            }
        });
    }

    @Test
    void SubtractionLeft() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return 10 - value;
            }
        });
    }

    @Test
    void SubtractionRight() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value - 10;
            }
        });
    }

    @Test
    void MultiplicationLeft() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value * 3;
            }
        });
    }

    @Test
    void MultiplicationRight() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return -3 * value;
            }
        });
    }

    @Test
    void DivisionLeft() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return value / 2;
            }
        });
    }

    @Test
    void DivisionRight() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(@Param(doubles = {1, 3, 4}) double value) {
                return 12 / value;
            }
        });
    }

    @Test
    void ReminderLeft() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value % 7;
            }
        });
    }

    @Test
    void ReminderRight() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 1, to = 10) int value) {
                return 40 % value;
            }
        });
    }

    @Test
    void Complex() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 1, to = 10) int value) {
                return (value + 2) * 2;
            }
        });
    }

    @Test
    void Negative() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return -value;
            }
        });
    }
}