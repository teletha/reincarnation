/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.lambda;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import java.util.function.LongSupplier;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class LambdaTest extends CodeVerifier {

    @CrossDecompilerTest
    void inlineWithoutArg() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return lambda(() -> 10);
            }

            private int lambda(IntSupplier supplier) {
                return supplier.getAsInt();
            }
        });
    }

    @CrossDecompilerTest
    void inlineWithArg() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return lambda(x -> x + 10);
            }

            private int lambda(IntUnaryOperator supplier) {
                return supplier.applyAsInt(10);
            }
        });
    }

    @CrossDecompilerTest
    void inlineWithArgs() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return lambda((x, y) -> x + y);
            }

            private int lambda(IntBinaryOperator supplier) {
                return supplier.applyAsInt(10, 20);
            }
        });
    }

    @CrossDecompilerTest
    void inlineWithObject() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return lambda(x -> x.toUpperCase());
            }

            private String lambda(Function<String, String> function) {
                return function.apply("test");
            }
        });
    }

    @CrossDecompilerTest
    void inlineWithParamiterizedType() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return lambda(x -> x.subList(2, 3)).get(0);
            }

            private List<String> lambda(Function<List<String>, List<String>> function) {
                return function.apply(List.of("1", "2", "3"));
            }
        });
    }

    @CrossDecompilerTest
    void useLocalVariable() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int local = 10;

                return lambda(() -> local);
            }

            private int lambda(IntSupplier supplier) {
                return supplier.getAsInt();
            }
        });
    }

    @CrossDecompilerTest
    void useLocalVariables() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                int local1 = 10;
                long local2 = 20;

                return lambda(() -> local1 + local2);
            }

            private long lambda(LongSupplier supplier) {
                return supplier.getAsLong();
            }
        });
    }

    @CrossDecompilerTest
    void useLocalVariableWithArg() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                String local = "!!";

                return lambda(x -> x.concat(local));
            }

            private String lambda(Function<String, String> function) {
                return function.apply("test");
            }
        });
    }

    @CrossDecompilerTest
    void useLocalVariablesWithArg() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                String local1 = "Hello ";
                String local2 = "!!";

                return lambda(x -> local1 + x + local2);
            }

            private String lambda(Function<String, String> function) {
                return function.apply("test");
            }
        });
    }

    @CrossDecompilerTest
    void useLocalVariablesWithArgs() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                String local2 = "!!";
                String local1 = "Hello ";

                return lambda((x, y) -> local1 + x + y + local2);
            }

            private String lambda(BiFunction<String, Integer, String> function) {
                return function.apply("test", 1);
            }
        });
    }

    @CrossDecompilerTest
    void useLocalVariableWithArgNest() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int local = 10;
                int deep = 20;

                return lambda1(x -> local - x * lambda2(y -> y + deep));
            }

            private int lambda1(IntUnaryOperator function) {
                return function.applyAsInt(3);
            }

            private int lambda2(IntUnaryOperator function) {
                return function.applyAsInt(-5);
            }
        });
    }
}