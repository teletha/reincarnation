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

/**
 * @version 2018/11/05 14:22:48
 */
class DoubleIncrementTest extends CodeVerifier {

    @Test
    void sequence() {
        verify(new Code.DoubleParam() {

            @Override
            public double run(double value) {
                value++;
                ++value;
                value++;
                return ++value;
            }
        });
    }

    @Test
    void incrementFieldInMethodCall() {
        verify(new Code.Double() {

            private double index = 0;

            @Override
            public double run() {
                return call(index++);
            }

            private double call(double value) {
                return index + value * 10;
            }
        });
    }

    @Test
    void decrementFieldInMethodCall() {
        verify(new Code.Double() {

            private double index = 0;

            @Override
            public double run() {
                return call(index--);
            }

            private double call(double value) {
                return index + value * 10;
            }
        });
    }

    @Test
    void preincrementFieldInMethodCall() {
        verify(new Code.Double() {

            private double index = 0;

            @Override
            public double run() {
                return call(++index);
            }

            private double call(double value) {
                return index + value * 10;
            }
        });
    }

    @Test
    void predecrementFieldInMethodCall() {
        verify(new Code.Double() {

            private double index = 0;

            @Override
            public double run() {
                return call(--index);
            }

            private double call(double value) {
                return index + value * 10;
            }
        });
    }

    @Test
    void incrementVariableInMethodCall() {
        verify(new Code.DoubleParam() {

            @Override
            public double run(double value) {
                double sum1 = sum(value++, value++, value++);
                double sum2 = sum(++sum1, ++sum1, ++sum1);
                return sum(++sum2, sum2++, ++sum2);
            }

            private double sum(double a, double b, double c) {
                return a + b + c;
            }
        });
    }

    @Test
    void decrementVariableInMethodCall() {
        verify(new Code.DoubleParam() {

            @Override
            public double run(double value) {
                double sum1 = sum(value--, value--, value--);
                double sum2 = sum(--sum1, --sum1, --sum1);
                return sum(--sum2, sum2--, --sum2);
            }

            private double sum(double a, double b, double c) {
                return a + b + c;
            }
        });
    }

    @Test
    void incrementFieldInFieldAccess() {
        verify(new Code.Double() {

            private double index = 1;

            private double count = 2;

            @Override
            public double run() {
                index = count++;

                return count + index * 10;
            }
        });
    }

    @Test
    void decrementFieldInFieldAccess() {
        verify(new Code.Double() {

            private double index = 1;

            private double count = 2;

            @Override
            public double run() {
                index = count--;

                return count + index * 10;
            }
        });
    }

    @Test
    void preincrementFieldInFieldAccess() {
        verify(new Code.Double() {

            private double index = 1;

            private double count = 2;

            @Override
            public double run() {
                index = ++count;

                return count + index * 10;
            }
        });
    }

    @Test
    void predecrementFieldInFieldAccess() {
        verify(new Code.Double() {

            private double index = 1;

            private double count = 2;

            @Override
            public double run() {
                index = --count;

                return count + index * 10;
            }
        });
    }
}
