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
import reincarnation.TestCode.Param;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for String operations.
 * 
 * @version 2026/10/11 11:30:00
 */
class StringTest extends JavascriptCodeVerifier {

    @Test
    void length() throws Exception {
        verify(new TestCode.TextParamInt() {

            @Override
            public int run(@Param(strings = {"", "a", "abc", "some value"}) String value) {
                return value.length();
            }
        });
    }

    @Test
    void isEmpty() throws Exception {
        verify(new TestCode.TextParamBoolean() {

            @Override
            public boolean run(@Param(strings = {"", "a", "abc"}) String value) {
                return value.isEmpty();
            }
        });
    }

    @Test
    void equals() throws Exception {
        verify(new TestCode.TextParamBoolean() {

            @Override
            public boolean run(@Param(strings = {"abc", "xyz"}) String value) {
                return value.equals("abc");
            }
        });
    }

    @Test
    void contains() throws Exception {
        verify(new TestCode.TextParamBoolean() {

            @Override
            public boolean run(@Param(strings = {"abc", "xyz"}) String value) {
                return value.contains("b");
            }
        });
    }

    @Test
    void concat() throws Exception {
        verify(new TestCode.TextParam() {

            @Override
            public String run(@Param(strings = {"a", "b"}) String value) {
                return value.concat("!");
            }
        });
    }

    @Test
    void toUpperCase() throws Exception {
        verify(new TestCode.TextParam() {

            @Override
            public String run(@Param(strings = {"abc", "ABC", "aBc"}) String value) {
                return value.toUpperCase();
            }
        });
    }

    @Test
    void indexOf() throws Exception {
        verify(new TestCode.TextParamInt() {

            @Override
            public int run(@Param(strings = {"abc", "xyz"}) String value) {
                return value.indexOf("b");
            }
        });
    }

    @Test
    void substring() throws Exception {
        verify(new TestCode.TextParam() {

            @Override
            public String run(@Param(strings = {"abcdef", "xyz"}) String value) {
                return value.substring(1, 3);
            }
        });
    }
}