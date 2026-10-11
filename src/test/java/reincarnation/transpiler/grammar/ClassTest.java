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
 * Test the JavaScript output for the class declarations.
 * 
 * @version 2026/10/11 13:30:00
 */
class ClassTest extends JavascriptCodeVerifier {

    @Test
    void localClass() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                class Counter {
                    int count;

                    Counter() {
                        count = 2;
                    }

                    int add(int value) {
                        count += value;
                        return count;
                    }
                }
                return new Counter().add(3);
            }
        });
    }

    @Test
    void localClassWithMethod() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                class Calculator {
                    int base() {
                        return 10;
                    }

                    int twice() {
                        return base() * 2;
                    }
                }
                return new Calculator().twice();
            }
        });
    }

    @Test
    void nestedClass() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                class Point {
                    int x;
                    int y;

                    Point(int x, int y) {
                        this.x = x;
                        this.y = y;
                    }
                }
                return new Point(3, 4).x + new Point(1, 2).y;
            }
        });
    }
}