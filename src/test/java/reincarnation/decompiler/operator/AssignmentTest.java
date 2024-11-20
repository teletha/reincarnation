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
    void localDoubleChain() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                String loc1 = "A";
                String loc2 = "B";

                loc1 = loc2 = "UPDATE"; // LLV

                return loc1 + loc2;
            }
        });
    }

    @CrossDecompilerTest
    void localDoubleChainOnUninitialized() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                String loc1, loc2;

                loc1 = loc2 = "A";

                return loc1 + loc2;
            }
        });
    }

    @CrossDecompilerTest
    void localTripleChain() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                String loc1 = "1";
                String loc2 = "2";
                String loc3 = "3";

                loc1 = loc2 = loc3 = "T"; // LLV

                return loc1 + loc2 + loc3;
            }
        });
    }

    @CrossDecompilerTest
    void localPentaChain() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                String loc1 = "1";
                String loc2 = "2";
                String loc3 = "3";
                String loc4 = "4";
                String loc5 = "5";

                loc1 = loc2 = loc3 = loc4 = loc5 = "P"; // LLV

                return loc1 + loc2 + loc3 + loc4 + loc5;
            }
        });
    }

    @CrossDecompilerTest
    void localDoubleChainPrimitive() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                int a = 0, b = 0;

                a = b = value + 1; // LLV

                return a * b;
            }
        });
    }

    @CrossDecompilerTest
    void localDoubleChainPrimitiveOnUninitialized() {
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
    void localTripleChainPrimitive() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                int a = 0, b = 0, c = 0;

                a = b = c = value + 1; // LLV

                return a * b * c;
            }
        });
    }

    @CrossDecompilerTest
    void localTripleChainPrimitiveOnUninitialized() {
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
    void fieldDoubleChain() {
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
    void fieldTripleChain() {
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