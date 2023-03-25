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

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.Debuggable;
import reincarnation.TestCode;

@Debuggable
class SwitchExpressionTest extends CodeVerifier {

    @Test
    void assignToVariable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                int value = switch (param) {
                case 0 -> 10;
                case 1 -> 15;
                default -> param;
                };
                return value;
            }
        });
    }

    @Test
    void methodParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int param) {
                return value(switch (param) {
                case 0 -> 10;
                case 1 -> 15;
                default -> param;
                });
            }

            private int value(int value) {
                return value;
            }
        });
    }
}
