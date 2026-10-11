/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.operator;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for instanceof operations.
 * 
 * @version 2026/10/11 12:30:00
 */
class InstanceOfTest extends JavascriptCodeVerifier {

    @Test
    void self() throws Exception {
        verify(new Self());
    }

    @Test
    void negate() throws Exception {
        verify(new Negate());
    }

    @Test
    void object() throws Exception {
        verify(new OBJECT());
    }

    @Test
    void arrayObject() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return new int[] {1} instanceof java.lang.Object;
            }
        });
    }

    @Test
    void newObject() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return new java.lang.Object() instanceof java.lang.Object;
            }
        });
    }

    /** Verify <code>this instanceof Self</code>. */
    static class Self implements TestCode.Boolean {

        @Override
        public boolean run() {
            return this instanceof Self;
        }
    }

    /** Verify the negated instanceof. */
    static class Negate implements TestCode.Boolean {

        @Override
        public boolean run() {
            return !(this instanceof Negate);
        }
    }

    /** Verify <code>this instanceof Object</code>. */
    static class OBJECT implements TestCode.Boolean {

        @Override
        public boolean run() {
            return this instanceof java.lang.Object;
        }
    }
}