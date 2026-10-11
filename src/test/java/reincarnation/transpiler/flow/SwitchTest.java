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
 * Test the JavaScript output for the switch statement and switch expression.
 * 
 * @version 2026/10/11 12:00:00
 */
class SwitchTest extends JavascriptCodeVerifier {

    @Test
    void switchStatement() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                switch (value) {
                case 0, 1, 2:
                    return 1;
                case 3:
                    return 2;
                default:
                    return 3;
                }
            }
        });
    }

    @Test
    void switchWithBreak() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                switch (value) {
                case 1:
                    value += 10;
                    break;
                default:
                    value += 100;
                    break;
                }
                return value;
            }
        });
    }

    @Test
    void switchExpression() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 3) int value) {
                return switch (value) {
                case 0 -> 10;
                case 1 -> 20;
                default -> 30;
                };
            }
        });
    }
}