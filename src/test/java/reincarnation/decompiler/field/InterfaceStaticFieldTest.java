/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.field;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class InterfaceStaticFieldTest extends CodeVerifier {

    @CrossDecompilerTest
    void interfaceAccess() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                interface Root {
                    String NAME = "OK".trim(); // LLV
                }

                return Root.NAME;
            }
        });
    }

    @CrossDecompilerTest
    @SuppressWarnings({"static-access"})
    void instanceAccess() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                interface Root {
                    String NAME = "OK".trim();
                }
                class Clazz implements Root {
                }

                return new Clazz().NAME;
            }
        });
    }
}