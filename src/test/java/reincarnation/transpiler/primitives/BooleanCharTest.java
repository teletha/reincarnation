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
 * Test the JavaScript output for boolean and char operations.
 * 
 * @version 2026/10/11 11:20:00
 */
class BooleanCharTest extends JavascriptCodeVerifier {

    @Test
    void negation() throws Exception {
        verify(new TestCode.BooleanParam() {

            @Override
            public boolean run(boolean value) {
                return !value;
            }
        });
    }

    @Test
    void charEquality() throws Exception {
        verify(new TestCode.CharParamBoolean() {

            @Override
            public boolean run(char value) {
                return value == 'A';
            }
        });
    }

    @Test
    void charRange() throws Exception {
        verify(new TestCode.CharParamBoolean() {

            @Override
            public boolean run(char value) {
                return 'A' <= value && value <= 'Z';
            }
        });
    }
}