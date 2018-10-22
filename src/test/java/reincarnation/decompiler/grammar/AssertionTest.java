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
 * @version 2018/10/10 11:20:51
 */
class AssertionTest extends CodeVerifier {

    @Test
    void single() {
        verify(new Code.RunInt() {

            @Override
            public void run(int value) {
                assert value != 10;
            }
        });
    }

    @Test
    void multiple() {
        verify(new Code.RunInt() {

            @Override
            public void run(int value) {
                assert value != 10 || value != 20;
            }
        });
    }
}
