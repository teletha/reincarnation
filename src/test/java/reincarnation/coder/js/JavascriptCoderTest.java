/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder.js;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.net.URI;

import org.junit.jupiter.api.Test;

import com.caoccao.javet.swc4j.Swc4j;
import com.caoccao.javet.swc4j.enums.Swc4jMediaType;
import com.caoccao.javet.swc4j.options.Swc4jTranspileOptions;

import reincarnation.Reincarnation;

/**
 * Test the JavaScript coder.
 * 
 * @version 2026/10/10 16:20:00
 */
class JavascriptCoderTest {

    /** The availability of the swc4j native library. */
    private static final boolean Swc4jAvailable = loadSwc4j();

    /**
     * Check whether the swc4j native library can be loaded on the current platform.
     * 
     * @return The availability of the swc4j native library.
     */
    private static boolean loadSwc4j() {
        try {
            new Swc4j().transpile("var x = 1;", new Swc4jTranspileOptions());
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    /** The sample class to decompile. */
    static class Sample {
        private int count;

        Sample() {
            count = 0;
        }

        void add(int value) {
            count += value;
        }

        int sum(int[] values) {
            int total = 0;
            for (int v : values) {
                total += v;
            }
            return total;
        }

        String greeting() {
            return "Hello " + count;
        }

        static int twice(int value) {
            return value * 2;
        }
    }

    @Test
    void jscoder() throws Exception {
        String js = Reincarnation.rebirth(Sample.class, new JavascriptCodingOption());

        // The output must be actual JavaScript syntax.
        assertTrue(js.contains("class "));
        assertTrue(js.contains("constructor"));
        assertFalse(js.contains("::"), "method reference is not JavaScript: " + js);
        assertFalse(js.contains(".class"), "class literal is not JavaScript: " + js);
        assertFalse(js.contains(": boolean"), "type annotation is not JavaScript: " + js);
        assertFalse(js.contains(" -> "), "Java lambda is not JavaScript: " + js);

        // Check the generated code is valid JavaScript syntax.
        assumeTrue(Swc4jAvailable, "The swc4j native library is not available on this platform.");
        Swc4jTranspileOptions options = new Swc4jTranspileOptions()
                .setSpecifier(URI.create("file:///sample.js").toURL())
                .setMediaType(Swc4jMediaType.JavaScript);
        new Swc4j().transpile(js, options);
    }
}