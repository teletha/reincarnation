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

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.DisabledOnMaven;
import reincarnation.TestCode;

@DisabledOnMaven
class AssertionTest extends CodeVerifier {

    @CrossDecompilerTest
    void single() {
        verify(new TestCode.RunInt() {

            @Override
            public void run(int value) {
                assert value != 10;
            }
        });
    }

    @CrossDecompilerTest
    void multiple() {
        verify(new TestCode.RunInt() {

            @Override
            public void run(int value) {
                assert value != 10 || value < 3;
            }
        });
    }

    @CrossDecompilerTest
    void messageText() {
        verify(new TestCode.RunInt() {

            @Override
            public void run(int value) {
                assert value != 10 : "fail";
            }
        });
    }

    @CrossDecompilerTest
    void messageExpression() {
        verify(new TestCode.RunInt() {

            @Override
            public void run(int value) {
                assert value != 10 || value < 3 : "fail on " + value;
            }
        });
    }

    @CrossDecompilerTest
    void multi() {
        verify(new TestCode.RunInt() {

            @Override
            public void run(int value) {
                assert value != 0;
                assert value != 10;
            }
        });
    }

    @CrossDecompilerTest
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