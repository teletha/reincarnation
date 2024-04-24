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

class MethodChainTest extends CodeVerifier {

    @CrossDecompilerTest
    void chainInMultiLines() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                String result = "long".replace("looooooooooooooooooooooooooooooooooooooooong", "short")
                        .replace("loooooooooooooooooooooooooooooooooooooooooooooooooooooooooong", "short")
                        .replace("long", "replaced");

                return result;
            }
        });
    }
}