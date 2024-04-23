/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.grammar;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class LocalClassTest extends CodeVerifier {

    @CrossDecompilerTest
    void field() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                class Local {
                    String name = "in local";
                }
                return new Local().name;
            }
        });
    }

    @CrossDecompilerTest
    void method() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                class Local {
                    String text() {
                        return "local";
                    }
                }
                return new Local().text();
            }
        });
    }

    @CrossDecompilerTest
    void outerReference() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                String outer = "from outer";

                class Local {
                    String text() {
                        return outer;
                    }
                }
                return new Local().text();
            }
        });
    }

    @CrossDecompilerTest
    void metadata() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                class Local {
                }
                return Local.class.getSimpleName();
            }
        });
    }
}