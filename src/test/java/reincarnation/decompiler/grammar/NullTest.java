/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.grammar;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.CodeVerifier;

/**
 * @version 2018/10/10 11:21:03
 */
class NullTest extends CodeVerifier {

    @Test
    void Null() {
        verify(new TestCode.Object() {

            @Override
            public Object run() {
                return null;
            }
        });
    }
}
