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

import java.util.function.IntUnaryOperator;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for method references.
 * 
 * @version 2026/10/11 12:00:00
 */
class MethodReferenceTest extends JavascriptCodeVerifier {

    @Test
    void instanceMethodReference() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return lambda(this::calc);
            }

            private int lambda(IntUnaryOperator function) {
                return function.applyAsInt(10);
            }

            private int calc(int value) {
                return value + 5;
            }
        });
    }
}