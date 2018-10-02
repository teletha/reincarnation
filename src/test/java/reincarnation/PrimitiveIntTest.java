/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import org.junit.jupiter.api.Test;

/**
 * @version 2018/04/04 16:25:10
 */
public class PrimitiveIntTest extends CodeVerifier {

    @Test
    void field() {
        verify(new CodeInt() {
            private int value;

            @Override
            public int run() {
                return value;
            }
        });
    }
}
