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
 * Test the JavaScript output for the class inheritance.
 * 
 * @version 2026/10/11 13:45:00
 */
class InheritanceTest extends JavascriptCodeVerifier {

    @Test
    void superMethod() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                class Base {
                    int value() {
                        return 1;
                    }
                }
                class Child extends Base {
                    @Override
                    int value() {
                        return super.value() + 1;
                    }
                }
                return new Child().value();
            }
        });
    }

    @Test
    void inheritedField() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                class Base {
                    int count = 5;

                    int getter() {
                        return count;
                    }
                }
                class Child extends Base {
                    int extra() {
                        return count + 1;
                    }
                }
                return new Child().extra();
            }
        });
    }

    @Test
    void instanceofBase() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                class Base {
                }
                class Child extends Base {
                }
                return new Child() instanceof Base ? 1 : 0;
            }
        });
    }

    @Test
    void constructorChain() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                class Base {
                    int base;

                    Base(int base) {
                        this.base = base;
                    }
                }
                class Child extends Base {
                    Child(int base) {
                        super(base * 2);
                    }

                    int value() {
                        return base;
                    }
                }
                return new Child(3).value();
            }
        });
    }

    @Test
    void override() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                class Base {
                    int value() {
                        return 1;
                    }
                }
                class Child extends Base {
                    @Override
                    int value() {
                        return 2;
                    }
                }
                return new Child().value();
            }
        });
    }

    @Test
    void multiLevel() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                class Base {
                    int value() {
                        return 3;
                    }
                }
                class Middle extends Base {
                }
                class Child extends Middle {
                }
                return new Child().value();
            }
        });
    }
}