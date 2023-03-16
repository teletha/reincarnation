/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.flow;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.TestCode;

@Disabled
class SwitchTest extends CodeVerifier {

    @Test
    void switchStatement() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                switch (param) {
                case 0:
                    return 10;

                case 1:
                    return 15;

                default:
                    return 20;
                }
            }
        });
    }

    @Test
    void switchStatementNoReturn() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value;

                switch (param) {
                case 0:
                    value = 10;
                    break;

                case 1:
                    value = 15;
                    break;

                default:
                    value = 20;
                    break;
                }

                return value;
            }
        });
    }

    @Test
    void switchExpression() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                return switch (param) {
                case 0 -> 10;
                case 1 -> 15;
                default -> {
                    param += -2;
                    yield 20 + param;
                }
                };
            }
        });
    }
}
