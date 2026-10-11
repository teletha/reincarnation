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

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the record declarations.
 * 
 * @version 2026/10/11 13:30:00
 */
class RecordTest extends JavascriptCodeVerifier {

    @Test
    void accessor() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                record Point(int x, int y) {
                }
                return new Point(3, 4).x() + new Point(3, 4).y();
            }
        });
    }

    @Test
    void componentMethod() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                record Point(int x, int y) {

                    int sum() {
                        return x + y;
                    }
                }
                return new Point(3, 4).sum();
            }
        });
    }

    @Test
    void equality() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                record Box(int size) {
                }
                return new Box(10).size() == 10;
            }
        });
    }
}