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
 * Test the JavaScript output for the primitive wrappers.
 */
class WrapperTest extends JavascriptCodeVerifier {

    @Test
    void constants() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return java.lang.Integer.MAX_VALUE + "|" + java.lang.Integer.MIN_VALUE + "|" + java.lang.Integer.SIZE + "|" + java.lang.Integer.BYTES + "|"
                        + java.lang.Long.MAX_VALUE + "|" + java.lang.Long.MIN_VALUE + "|" + java.lang.Double.POSITIVE_INFINITY + "|" + java.lang.Double.NaN + "|"
                        + java.lang.Character.MAX_VALUE;
            }
        });
    }

    @Test
    void instance() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                java.lang.Object integer = java.lang.Integer.valueOf(1);
                java.lang.Object longValue = java.lang.Long.valueOf(1L);
                java.lang.Object character = java.lang.Character.valueOf('a');
                return (integer instanceof java.lang.Integer ? "T" : "F") + (integer instanceof java.lang.Long ? "T" : "F")
                        + (longValue instanceof java.lang.Long ? "T" : "F") + (longValue instanceof java.lang.Integer ? "T" : "F")
                        + (character instanceof java.lang.Character ? "T" : "F");
            }
        });
    }
}
