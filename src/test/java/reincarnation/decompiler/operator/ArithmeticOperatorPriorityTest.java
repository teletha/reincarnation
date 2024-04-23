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
import reincarnation.DecompilableTest;
import reincarnation.TestCode;

class ArithmeticOperatorPriorityTest extends CodeVerifier {

    @DecompilableTest
    void shiftWithAdd() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                int value = 128;
                int base = 2;
                assert value >> base + 1 == 16;
                assert (value >> base) + 1 == 33;
            }
        });
    }
}