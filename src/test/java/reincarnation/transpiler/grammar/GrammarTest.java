/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.grammar;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for miscellaneous grammar constructs.
 * 
 * @version 2026/10/11 13:30:00
 */
class GrammarTest extends JavascriptCodeVerifier {

    @Test
    void textBlock() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return """
                        line1
                        line2""";
            }
        });
    }

    @Test
    void nullReturn() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return null;
            }
        });
    }

    @Test
    void nullComparison() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return null == null;
            }
        });
    }
}