/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.primitives;

import org.junit.jupiter.api.Test;

import reincarnation.Code.Int;
import reincarnation.CodeVerifier;

/**
 * @version 2018/10/04 8:36:16
 */
class IntConstantTest extends CodeVerifier {

    @Test
    void zero() {
        verify(new Int() {

            @Override
            public int run() {
                return 0;
            }
        });
    }
}
