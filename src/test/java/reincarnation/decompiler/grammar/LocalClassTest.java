/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.grammar;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.Debuggable;
import reincarnation.TestCode;

class LocalClassTest extends CodeVerifier {

    @Test
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

    @Test
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

    @Test
    @Debuggable
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

    @Test
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
