/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.operator;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class NegativeOperatorTest extends CodeVerifier {

    @CrossDecompilerTest
    void shiftInside() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return -value >> 2;
            }
        });
    }

    @CrossDecompilerTest
    void shiftOutside() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return -(value >> 2);
            }
        });
    }
}