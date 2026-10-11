/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.flow;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.TestCode.Param;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the for statement.
 * 
 * @version 2026/10/11 12:00:00
 */
class ForTest extends JavascriptCodeVerifier {

    @Test
    void normal() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int sum = 0;
                for (int i = 0; i < 5; i++) {
                    sum += i;
                }
                return sum;
            }
        });
    }

    @Test
    void decreasing() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int sum = 0;
                for (int i = 5; 0 < i; i--) {
                    sum += i;
                }
                return sum;
            }
        });
    }

    @Test
    void withBreak() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int sum = 0;
                for (int i = 0; i < 10; i++) {
                    if (i == 3) {
                        break;
                    }
                    sum += i;
                }
                return sum;
            }
        });
    }

    @Test
    void withContinue() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int sum = 0;
                for (int i = 0; i < 5; i++) {
                    if (i == 2) {
                        continue;
                    }
                    sum += i;
                }
                return sum;
            }
        });
    }

    @Test
    void nested() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int sum = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        sum += i * j;
                    }
                }
                return sum;
            }
        });
    }

    @Test
    void parameterBound() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                int sum = 0;
                for (int i = 0; i < value; i++) {
                    sum += i;
                }
                return sum;
            }
        });
    }
}