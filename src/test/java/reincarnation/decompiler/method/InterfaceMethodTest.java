/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.method;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.TestCode;

class InterfaceMethodTest extends CodeVerifier {

    @Test
    void staticMethod() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                interface A {
                    static String method() {
                        return "Call interface static method";
                    }
                }

                return A.method();
            }
        });
    }

    @Test
    void staticPrivateMethod() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                interface A {
                    static String method() {
                        return privateMethod();
                    }

                    private static String privateMethod() {
                        return "Call interface static private method";
                    }
                }

                return A.method();
            }
        });
    }

    @Test
    void defaultMethod() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                interface A {
                    default String method() {
                        return "Call interface default method";
                    }
                }

                class B implements A {
                }

                return new B().method();
            }
        });
    }

    @Test
    void privateMethod() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                interface A {
                    default String method() {
                        return privateMethod();
                    }

                    private String privateMethod() {
                        return "Call interface private method";
                    }
                }

                class B implements A {
                }

                return new B().method();
            }
        });
    }
}
