/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.js;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the lambda which captures multiple variables.
 */
class LambdaCaptureTest extends JavascriptCodeVerifier {

    @Test
    void staticCapture() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return compose("A", "B");
            }
        });
    }

    @Test
    void instanceCapture() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                String a = "A";
                String b = "B";
                String c = "C";
                Function<String, String> function = value -> a + b + c + value;
                return function.apply("D");
            }
        });
    }

    @Test
    void nestedCapture() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                String a = "A";
                Function<String, String> outer = left -> {
                    String b = "B";
                    Function<String, String> inner = right -> a + b + left + right;
                    return inner.apply("D");
                };
                return outer.apply("C");
            }
        });
    }

    /**
     * Compose the text with the multiple captured variables in the static method.
     * 
     * @param a A text.
     * @param b A text.
     * @return A composed text.
     */
    private static String compose(String a, String b) {
        String c = "C";
        Function<String, String> function = value -> a + b + c + value;
        return function.apply("D");
    }
}
