/*
 * Copyright (C) 2016 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.method;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.TestCode;

class MethodChainTest extends CodeVerifier {

    @Test
    void chainInMultiLines() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                String result = "long".replace("looooooooooooooooooooooooooooooooooooooooong", "short")
                        .replace("loooooooooooooooooooooooooooooooooooooooooooooooooooooooooong", "short")
                        .replace("long", "replaced");

                return result;
            }
        });
    }
}
