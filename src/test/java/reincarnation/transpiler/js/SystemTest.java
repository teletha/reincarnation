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
 * Test the Java implementation of {@link System}.
 */
class SystemTest extends JavascriptCodeVerifier {

    @Test
    void arraycopy() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                int[] source = {1, 2, 3, 4};
                int[] destination = new int[4];
                System.arraycopy(source, 1, destination, 0, 3);
                return destination[0] + ":" + destination[1] + ":" + destination[2] + ":" + destination[3];
            }
        });
    }

    @Test
    void identityHashCode() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                java.lang.Object value = new java.lang.Object();
                return System.identityHashCode(value) == System.identityHashCode(value);
            }
        });
    }

    @Test
    void lineSeparator() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return System.lineSeparator().length() > 0;
            }
        });
    }

    @Test
    void property() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return System.getProperty("missing", "default");
            }
        });
    }

    @Test
    void time() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return System.currentTimeMillis() > 0 && System.nanoTime() > 0;
            }
        });
    }
}
