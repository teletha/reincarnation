/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.lambda;

import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import java.util.function.LongSupplier;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for lambda expressions.
 * <p>
 * The lambda is passed to a helper method and invoked there, so that the invocation is converted to
 * a direct function call in JavaScript (e.g. {@code supplier.getAsInt()} becomes {@code
 * supplier()}).
 * </p>
 * 
 * @version 2026/10/11 11:20:00
 */
class LambdaTest extends JavascriptCodeVerifier {

    @Test
    void noArgLambda() throws Exception {
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

    @Test
    void longNoArgLambda() throws Exception {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                return lambda(() -> 100L);
            }

            private long lambda(LongSupplier supplier) {
                return supplier.getAsLong();
            }
        });
    }

    @Test
    void withArgLambda() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return lambda(x -> x + 10);
            }

            private int lambda(IntUnaryOperator function) {
                return function.applyAsInt(10);
            }
        });
    }

    @Test
    void withArgsLambda() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return lambda((x, y) -> x + y);
            }

            private int lambda(IntBinaryOperator function) {
                return function.applyAsInt(10, 20);
            }
        });
    }

    @Test
    void capturedLocal() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int base = 5;
                return lambda(x -> x + base);
            }

            private int lambda(IntUnaryOperator function) {
                return function.applyAsInt(10);
            }
        });
    }

    @Test
    void objectLambda() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return lambda(x -> x + "!");
            }

            private String lambda(Function<String, String> function) {
                return function.apply("test");
            }
        });
    }
}