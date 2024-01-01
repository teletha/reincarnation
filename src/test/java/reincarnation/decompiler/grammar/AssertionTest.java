/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.grammar;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.TestCode;

class AssertionTest extends CodeVerifier {

    @Test
    void single() {
        verify(new TestCode.RunInt() {

            @Override
            public void run(int value) {
                assert value != 10;
            }
        });
    }

    @Test
    void multiple() {
        verify(new TestCode.RunInt() {

            @Override
            public void run(int value) {
                assert value != 10 || value < 3;
            }
        });
    }

    @Test
    void messageText() {
        verify(new TestCode.RunInt() {

            @Override
            public void run(int value) {
                assert value != 10 : "fail";
            }
        });
    }

    @Test
    void messageExpression() {
        verify(new TestCode.RunInt() {

            @Override
            public void run(int value) {
                assert value != 10 || value < 3 : "fail on " + value;
            }
        });
    }

    @Test
    void multi() {
        verify(new TestCode.RunInt() {

            @Override
            public void run(int value) {
                assert value != 0;
                assert value != 10;
            }
        });
    }

    @Test
    void multiMessage() {
        verify(new TestCode.RunInt() {

            @Override
            public void run(int value) {
                assert value != 0 : "fail on " + value;
                assert value != 10 : "fail on " + value;
            }
        });
    }
}