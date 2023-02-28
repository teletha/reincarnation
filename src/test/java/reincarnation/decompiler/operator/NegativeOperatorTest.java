/*
 * Copyright (C) 2023 The REINCARNATION Development Team
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
 * @version 2018/10/23 15:15:59
 */
class NegativeOperatorTest extends CodeVerifier {

    @Test
    void shiftInside() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return -value >> 2;
            }
        });
    }

    @Test
    void shiftOutside() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return -(value >> 2);
            }
        });
    }
}