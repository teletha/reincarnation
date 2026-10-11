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
 * Test the JavaScript output for the while and do-while statements.
 * 
 * @version 2026/10/11 12:00:00
 */
class WhileTest extends JavascriptCodeVerifier {

    @Test
    void normal() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int count = 0;
                while (count < 5) {
                    count++;
                }
                return count;
            }
        });
    }

    @Test
    void withBreak() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int count = 0;
                while (true) {
                    count++;
                    if (3 <= count) {
                        break;
                    }
                }
                return count;
            }
        });
    }

    @Test
    void withContinue() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int sum = 0;
                int count = 0;
                while (count < 5) {
                    count++;
                    if (count % 2 == 0) {
                        continue;
                    }
                    sum += count;
                }
                return sum;
            }
        });
    }

    @Test
    void parameterCondition() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                int count = 0;
                while (count < value) {
                    count++;
                }
                return count;
            }
        });
    }

    @Test
    void doWhile() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int count = 0;
                do {
                    count++;
                } while (count < 5);
                return count;
            }
        });
    }
}