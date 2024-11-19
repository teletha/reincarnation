/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.method;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class InterfaceMethodTest extends CodeVerifier {

    @CrossDecompilerTest
    void method() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                interface A {
                    String method(); // LLV
                }

                class B implements A {
                    @Override
                    public String method() {
                        return "TEST";
                    };
                }

                return new B().method();
            }
        });
    }

    @CrossDecompilerTest
    void staticMethod() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                interface A {
                    static String method() { // LLV
                        return "Call interface static method";
                    }
                }

                return A.method();
            }
        });

    }

    @CrossDecompilerTest
    void staticPrivateMethod() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                interface A {
                    static String method() { // LLV
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

    @CrossDecompilerTest
    void defaultMethod() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                interface A {
                    default String method() { // LLV
                        return "Call interface default method";
                    }
                }

                class B implements A {
                }

                return new B().method();
            }
        });

    }

    @CrossDecompilerTest
    void privateMethod() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                interface A {
                    default String method() {
                        return privateMethod();
                    }

                    private String privateMethod() { // LLV
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