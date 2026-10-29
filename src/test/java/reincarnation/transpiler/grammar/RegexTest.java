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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the {@link Pattern} and {@link Matcher} API.
 */
class RegexTest extends JavascriptCodeVerifier {

    @Test
    void find() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Matcher matcher = Pattern.compile("a+").matcher("caaab");
                return matcher.find() ? matcher.group().length() : 0;
            }
        });
    }

    @Test
    void matches() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return Pattern.compile("[0-9]+").matcher("12345").matches() //
                        && !Pattern.compile("[0-9]+").matcher("12a45").matches();
            }
        });
    }

    @Test
    void replaceAll() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return Pattern.compile("[0-9]+").matcher("a12b345c").replaceAll("#");
            }
        });
    }

    @Test
    void group() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Matcher matcher = Pattern.compile("(\\w+)-(\\w+)").matcher("abc-def");
                return matcher.find() ? matcher.group(2) : "";
            }
        });
    }

    @Test
    void split() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return Pattern.compile(",").split("a,b,c").length;
            }
        });
    }

    @Test
    void quote() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return Pattern.compile(Pattern.quote("a.b")).matcher("a.b").matches();
            }
        });
    }
}
