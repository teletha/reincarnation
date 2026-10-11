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
 * Test the JavaScript output for the interface declarations.
 * 
 * @version 2026/10/11 13:30:00
 */
class InterfaceTest extends JavascriptCodeVerifier {

    @Test
    void name() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                interface Naming {
                    String name();
                }

                class A implements Naming {
                    @Override
                    public String name() {
                        return "A";
                    }
                }

                return new A().name();
            }
        });
    }

    @Test
    void reference() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                interface Naming {
                    int value();
                }

                class A implements Naming {
                    @Override
                    public int value() {
                        return 12;
                    }
                }

                return new A().value();
            }
        });
    }
}