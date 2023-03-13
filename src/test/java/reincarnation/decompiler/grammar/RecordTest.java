/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.grammar;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.TestCode;

class RecordTest extends CodeVerifier {

    record A(int age, String name) {
    }

    @Test
    void base() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return new A(3, "test").age;
            }
        });
    }
}