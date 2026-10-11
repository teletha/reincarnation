/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.primitives;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for char operations.
 * 
 * @version 2026/10/11 13:00:00
 */
class CharTest extends JavascriptCodeVerifier {

    @Test
    void identityNoParam() throws Exception {
        verify(new TestCode.Char() {

            @Override
            public char run() {
                return 'A';
            }
        });
    }

    @Test
    void identity() throws Exception {
        verify(new TestCode.CharParam() {

            @Override
            public char run(char value) {
                return value;
            }
        });
    }

    @Test
    void equality() throws Exception {
        verify(new TestCode.CharParamBoolean() {

            @Override
            public boolean run(char value) {
                return value == 'A';
            }
        });
    }

    @Test
    void range() throws Exception {
        verify(new TestCode.CharParamBoolean() {

            @Override
            public boolean run(char value) {
                return 'A' <= value && value <= 'Z';
            }
        });
    }
}