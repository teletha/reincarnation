/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.method;

import org.junit.jupiter.api.Test;

import reincarnation.Code.IntParam;
import reincarnation.Code.LongParam;
import reincarnation.CodeVerifier;

/**
 * @version 2018/10/04 12:00:24
 */
class ParameterTest extends CodeVerifier {

    @Test
    void Int() {
        verify(new IntParam() {

            @Override
            public int run(int param) {
                return param;
            }
        });
    }

    @Test
    void Long() {
        verify(new LongParam() {

            @Override
            public long run(long param) {
                return param;
            }
        });
    }
}
