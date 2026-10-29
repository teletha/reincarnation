/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.js;

import org.junit.jupiter.api.Test;

import js.lang.NativeArray;
import js.lang.NativeRegExp;
import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript {@code RegExp} native bridge.
 */
class NativeRegExpTest extends JavascriptCodeVerifier {

    @Test
    void test() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                NativeRegExp regex = new NativeRegExp("a+");
                return regex.test("baa") && !regex.test("bbb");
            }
        });
    }

    @Test
    void exec() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NativeRegExp regex = new NativeRegExp("(\\d+)-(\\d+)");
                NativeArray result = regex.exec("abc 12-34 xyz");
                return result.get(0) + ":" + result.get(1) + ":" + result.get(2);
            }
        });
    }

    @Test
    void execUnmatched() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return new NativeRegExp("z").exec("abc") == null;
            }
        });
    }

    @Test
    void flags() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                NativeRegExp regex = new NativeRegExp("a", "i");
                return regex.test("A") && regex.ignoreCase() && !regex.global() && regex.source().equals("a");
            }
        });
    }

    @Test
    void replace() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return new NativeRegExp("a").replace("banana", "x");
            }
        });
    }

    @Test
    void replaceGlobal() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return new NativeRegExp("a", "g").replace("banana", "x");
            }
        });
    }

    @Test
    void split() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                NativeArray parts = new NativeRegExp(",").split("a,b,c");
                return parts.length() * 10 + ((String) parts.get(1)).length();
            }
        });
    }

    @Test
    void lastIndex() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                NativeRegExp regex = new NativeRegExp("a", "g");
                boolean first = regex.test("a");
                int index = regex.lastIndex();
                boolean second = regex.test("a");
                return first && index == 1 && !second;
            }
        });
    }

    @Test
    void toStringText() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return new NativeRegExp("a", "gi").toString();
            }
        });
    }
}
