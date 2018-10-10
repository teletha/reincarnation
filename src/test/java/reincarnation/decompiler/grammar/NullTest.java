/*
 * Copyright (C) 2016 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.grammar;

import org.junit.jupiter.api.Test;

import reincarnation.Code;
import reincarnation.CodeVerifier;

/**
 * @version 2018/10/10 11:21:03
 */
class NullTest extends CodeVerifier {

    @Test
    void Null() {
        verify(new Code.Object() {

            @Override
            public Object run() {
                return null;
            }
        });
    }
}
