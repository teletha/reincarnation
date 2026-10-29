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

import java.util.StringJoiner;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the Java implementation of the string builders and the string joiner.
 */
class ClasslibStringTest extends JavascriptCodeVerifier {

    @Test
    void builder() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                StringBuilder builder = new StringBuilder();
                builder.append("a").append(1).append(true);
                builder.insert(1, "-");
                builder.delete(0, 1);
                builder.setCharAt(0, 'X');
                return builder.toString();
            }
        });
    }

    @Test
    void builderReverse() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                StringBuilder builder = new StringBuilder("abc");
                return builder.reverse().toString();
            }
        });
    }

    @Test
    void builderSubstring() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                StringBuilder builder = new StringBuilder("abcdef");
                builder.setLength(4);
                return builder.substring(1) + ":" + builder.charAt(0) + ":" + builder.length();
            }
        });
    }

    @Test
    void builderReplace() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                StringBuilder builder = new StringBuilder("abcdef");
                builder.replace(1, 4, "X");
                return builder.toString();
            }
        });
    }

    @Test
    void buffer() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                StringBuffer buffer = new StringBuffer("a");
                buffer.append("b").insert(1, "-");
                return buffer.toString();
            }
        });
    }

    @Test
    void joiner() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                StringJoiner joiner = new StringJoiner(",", "[", "]");
                joiner.add("a").add("b");
                return joiner.toString();
            }
        });
    }

    @Test
    void joinerEmpty() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                StringJoiner joiner = new StringJoiner(",");
                return joiner.toString() + ":" + joiner.length();
            }
        });
    }

    @Test
    void joinerMerge() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                StringJoiner left = new StringJoiner("-");
                StringJoiner right = new StringJoiner("-");
                left.add("a");
                right.add("b").add("c");
                left.merge(right);
                return left.toString();
            }
        });
    }
}
