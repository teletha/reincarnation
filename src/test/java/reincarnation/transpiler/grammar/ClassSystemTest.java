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
 * Test the class registration based JavaScript output.
 */
class ClassSystemTest extends JavascriptCodeVerifier {

    @Test
    void instanceFieldDefault() throws Exception {
        verify(new Defaults());
    }

    @Test
    void overloadedConstructor() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                class Value {

                    int number;

                    Value(int number) {
                        this.number = number;
                    }

                    Value(String number) {
                        this.number = number.length();
                    }
                }
                return new Value(3).number + new Value("4").number;
            }
        });
    }

    @Test
    void superConstructor() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                class Base {

                    int value;

                    Base(int value) {
                        this.value = value;
                    }
                }
                class Derived extends Base {

                    Derived(int value) {
                        super(value + 1);
                    }
                }
                return new Derived(3).value;
            }
        });
    }

    @Test
    void staticInitializer() throws Exception {
        verify(new StaticField());
    }

    @Test
    void inheritedField() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                class Base {

                    int value = 7;
                }
                class Derived extends Base {

                    int read() {
                        return value;
                    }
                }
                return new Derived().read();
            }
        });
    }

    @Test
    void enumNameAndOrdinal() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                enum Symbol {
                    A, B, C
                }
                return Symbol.B.name().length() + Symbol.C.ordinal();
            }
        });
    }

    @Test
    void enumValues() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                enum Symbol {
                    A, B, C
                }
                int count = 0;
                for (Symbol symbol : Symbol.values()) {
                    if (symbol != null) {
                        count++;
                    }
                }
                return count;
            }
        });
    }

    @Test
    void enumValueOf() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                enum Symbol {
                    A, B, C
                }
                return Symbol.valueOf("B").ordinal() + Enum.valueOf(Symbol.class, "C").ordinal();
            }
        });
    }

    @Test
    void enumWithField() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                enum Symbol {
                    A(1), B(2), C(3);

                    private final int value;

                    Symbol(int value) {
                        this.value = value;
                    }
                }
                return Symbol.A.value + Symbol.C.value;
            }
        });
    }

    /** Verify the default value of the instance fields. */
    static class Defaults implements TestCode.Int {

        int number;

        boolean flag;

        String text;

        Object object;

        @Override
        public int run() {
            return number + (flag ? 1 : 0) + (text == null ? 1 : 0) + (object == null ? 1 : 0);
        }
    }

    /** Verify the deferred static initializer. */
    static class StaticField implements TestCode.Int {

        static int value;

        static {
            value = 5;
        }

        @Override
        public int run() {
            return value;
        }
    }
}
