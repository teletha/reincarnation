/*
 * Copyright (C) 2016 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.operator;

import org.junit.jupiter.api.Test;

import reincarnation.Code;
import reincarnation.CodeVerifier;

/**
 * @version 2018/10/23 15:15:59
 */
class NegateOperatorTest extends CodeVerifier {

    @Test
    void shiftInside() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return -value >> 2;
            }
        });
    }

    @Test
    void shiftOutside() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return -(value >> 2);
            }
        });
    }
}
