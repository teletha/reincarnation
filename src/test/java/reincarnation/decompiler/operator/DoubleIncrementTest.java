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

class DoubleIncrementTest extends CodeVerifier {

    @DecompilableTest
    void sequence() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                value++;
                ++value;
                value++;
                return ++value;
            }
        });
    }

    @DecompilableTest
    void incrementFieldInMethodCall() {
        verify(new TestCode.Double() {

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

    @DecompilableTest
    void decrementFieldInMethodCall() {
        verify(new TestCode.Double() {

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

    @DecompilableTest
    void preincrementFieldInMethodCall() {
        verify(new TestCode.Double() {

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

    @DecompilableTest
    void predecrementFieldInMethodCall() {
        verify(new TestCode.Double() {

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

    @DecompilableTest
    void incrementVariableInMethodCall() {
        verify(new TestCode.DoubleParam() {

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

    @DecompilableTest
    void decrementVariableInMethodCall() {
        verify(new TestCode.DoubleParam() {

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

    @DecompilableTest
    void incrementFieldInFieldAccess() {
        verify(new TestCode.Double() {

            private double index = 1;

            private double count = 2;

            @Override
            public double run() {
                index = count++;

                return count + index * 10;
            }
        });
    }

    @DecompilableTest
    void decrementFieldInFieldAccess() {
        verify(new TestCode.Double() {

            private double index = 1;

            private double count = 2;

            @Override
            public double run() {
                index = count--;

                return count + index * 10;
            }
        });
    }

    @DecompilableTest
    void preincrementFieldInFieldAccess() {
        verify(new TestCode.Double() {

            private double index = 1;

            private double count = 2;

            @Override
            public double run() {
                index = ++count;

                return count + index * 10;
            }
        });
    }

    @DecompilableTest
    void predecrementFieldInFieldAccess() {
        verify(new TestCode.Double() {

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