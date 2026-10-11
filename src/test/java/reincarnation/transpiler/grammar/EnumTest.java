/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.grammar;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the enum declarations.
 * <p>
 * The enum is written as a class whose constants are static fields.
 * </p>
 * 
 * @version 2026/10/11 13:30:00
 */
class EnumTest extends JavascriptCodeVerifier {

    @Test
    void simple() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                enum Symbol {
                    A, B;
                }
                return Symbol.A == Symbol.A;
            }
        });
    }

    @Test
    void equality() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                enum Symbol {
                    A, B;
                }
                return Symbol.A == Symbol.B;
            }
        });
    }

    @Test
    void userDefinedConstructor() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                enum Symbol {
                    A(1, 2);

                    private final int value;

                    private Symbol(int value, int multiplier) {
                        this.value = value * multiplier;
                    }
                }
                return Symbol.A.value;
            }
        });
    }

    @Test
    void multipleConstants() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                enum Symbol {
                    A(1), B(2), C(3);

                    private final int value;

                    private Symbol(int value) {
                        this.value = value;
                    }
                }
                return Symbol.A.value + Symbol.B.value + Symbol.C.value;
            }
        });
    }
}