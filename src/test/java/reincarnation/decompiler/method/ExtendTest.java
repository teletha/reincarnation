/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.method;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.TestCode;

class ExtendTest extends CodeVerifier {

    @Test
    @Disabled
    void abstractClassAndMethod() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                abstract class Parent {
                    abstract int value();
                }

                class Child extends Parent {

                    @Override
                    int value() {
                        return 10;
                    }
                }

                return new Child().value();
            }
        });
    }
}
