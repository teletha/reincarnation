/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;

/**
 * Test the JavaScript coder by comparing the invocation results of the original Java method with
 * those of the decompiled JavaScript code executed on a JavaScript runtime.
 * 
 * @version 2026/10/11 10:20:00
 */
class JavascriptCodeVerifierTest extends JavascriptCodeVerifier {

    @Test
    void booleanReturn() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return true;
            }
        });
    }

    @Test
    void intMath() throws Exception {
        // Avoid int overflow: Java int wraps around on overflow while JavaScript does not.
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value % 7;
            }
        });
    }

    @Test
    void booleanFromInt() throws Exception {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return 0 < value;
            }
        });
    }

    @Test
    void intFromText() throws Exception {
        verify(new TestCode.IntParamText() {

            @Override
            public String run(int value) {
                return "value=" + value;
            }
        });
    }

    @Test
    void bracket() throws Exception {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String value) {
                return "[" + value + "]";
            }
        });
    }

    @Test
    void intArray() throws Exception {
        verify(new TestCode.IntArray() {

            @Override
            public int[] run() {
                return new int[] {1, 2, 3};
            }
        });
    }
}