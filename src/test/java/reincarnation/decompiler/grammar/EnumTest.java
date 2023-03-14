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
import reincarnation.Debuggable;
import reincarnation.TestCode;

@Debuggable
class EnumTest extends CodeVerifier {

    @Test
    void basic() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                enum Defined {
                    A, B;
                }

                assert Defined.A != null;
                assert Defined.B != Defined.A;
            }
        });
    }

    @Test
    void customConstructor() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                enum Defined {
                    A(1), B(2);

                    private final int value;

                    Defined(int value) {
                        this.value = value;
                    }
                }

                return Defined.A.value;
            }
        });
    }

    @Test
    void customInitializer() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                enum Defined {
                    A(1), B(2);

                    static final int base;

                    static {
                        base = 10;
                    }

                    private final int value;

                    Defined(int value) {
                        this.value = value;
                    }
                }

                return Defined.A.value + Defined.base;
            }
        });
    }
}