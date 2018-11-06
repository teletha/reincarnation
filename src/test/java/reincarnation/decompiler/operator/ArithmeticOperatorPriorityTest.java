/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.operator;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.CodeVerifier;

/**
 * @version 2018/10/22 17:01:57
 */
class ArithmeticOperatorPriorityTest extends CodeVerifier {

    @Test
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
