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

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the Java implementation of the regular expression API.
 */
class ClasslibRegexTest extends JavascriptCodeVerifier {

    @Test
    void groupAndRange() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("\\d+").matcher("a12b");
                if (!matcher.find()) {
                    return "";
                }
                return matcher.group() + ":" + matcher.start() + ":" + matcher.end();
            }
        });
    }

    @Test
    void capture() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("(a)(b)").matcher("ab");
                return matcher.matches() ? matcher.group(1) + matcher.group(2) : "";
            }
        });
    }

    @Test
    void flags() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return java.util.regex.Pattern.compile("abc", java.util.regex.Pattern.CASE_INSENSITIVE).matcher("ABC").matches();
            }
        });
    }

    @Test
    void replaceAllAndFirst() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\d");
                return pattern.matcher("a1b2c3").replaceAll("#") + ":" + pattern.matcher("a1b2c3").replaceFirst("#");
            }
        });
    }

    @Test
    void reset() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("a").matcher("a");
                boolean first = matcher.matches();
                matcher.reset("b");
                boolean second = matcher.matches();
                return first && !second;
            }
        });
    }

    @Test
    void splitLimit() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                String[] parts = java.util.regex.Pattern.compile(",").split("a,b,c", 2);
                return parts.length * 10 + parts[1].length();
            }
        });
    }
}
