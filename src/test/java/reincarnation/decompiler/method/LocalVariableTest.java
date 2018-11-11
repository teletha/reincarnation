/*
 * Copyright (C) 2018 Reincarnation Development Team
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
import reincarnation.Debuggable;
import reincarnation.TestCode;

/**
 * @version 2018/11/05 11:45:32
 */
class LocalVariableTest extends CodeVerifier {

    @Test
    void parallel() {
        verify(new TestCode.IntParam() {

            @Debuggable
            @Override
            public int run(int value) {
                if (value < 1) {
                    int x = value;

                    value = value - 1;
                    value = x * value;
                } else {
                    int x = value;

                    value = value + 1;
                    value = x * value;
                }
                return value;
            }
        });
    }

    @Test
    void parallel2() {
        verify(new TestCode.IntParam() {

            @Debuggable
            @Override
            public int run(int value) {
                int x;

                if (value < 1) {
                    x = value;

                    value = value - 1;
                    value = x * value;
                } else {
                    x = value;

                    value = value + 1;
                    value = x * value;
                }
                return value + x;
            }
        });
    }

    @Test
    void PrimitiveLongAndDoubleUses2Stacks() {
        verify(new TestCode.DoubleParam() {

            @Override
            public double run(double value) {
                return calc(20, value, 10);
            }

            double calc(double one, double two, long three) {
                return one * two + three;
            }
        });
    }

    @Test
    void CompilerGeneratedCodeDoesntProduceLocalVariableOperand() {
        verify(new CompilerGenerator());
    }

    /**
     * @version 2018/11/05 11:47:41
     */
    private static class CompilerGenerator implements TestCode.Double {

        @Override
        public double run() {
            return new CompilerGeneratedCode().calc(10, 10);
        }

        /**
         * @version 2018/11/05 11:47:38
         */
        private static class CompilerGeneratedCode {

            private double calc(double one, double two) {
                return one * two;
            }
        }
    }
}