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

class NullTest extends CodeVerifier {

    @CrossDecompilerTest
    void Null() {
        verify(new TestCode.Object() {

            @Override
            public Object run() {
                return null;
            }
        });
    }
}