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
import reincarnation.TestCode;

class RecordTest extends CodeVerifier {

    @Test
    void primitiveInt() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                record A(int value) {
                }

                return new A(3).value;
            }
        });
    }

    @Test
    void primitiveFloat() {
        verify(new TestCode.Float() {

            @Override
            public float run() {
                record A(float value) {
                }

                return new A(-1.4f).value;
            }
        });
    }

    @Test
    void string() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                record A(String value) {
                }

                return new A("text").value;
            }
        });
    }

    @Test
    void item2() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                record A(String value, long num) {
                }

                return new A("text", 10).value;
            }
        });
    }

    @Test
    void item3() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                record A(String value, long num, double floating) {
                }

                return new A("text", 10, 0.2).value;
            }
        });
    }

    @Test
    void item4() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                record A(String value, long num, double floating, Object item) {
                }

                return new A("text", 10, 0.2, null).value;
            }
        });
    }

    @Test
    void array() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                record A(int[] value) {
                }

                return new A(new int[] {1, 2, 3}).value[2];
            }
        });
    }

    @Test
    void vararg() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                record A(int... value) {
                }

                return new A(1, 2, 3).value[2];
            }
        });
    }

    @Test
    void varargWithBase() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                record A(long base, int... value) {
                }

                return new A(10L, 1, 2, 3).value[2];
            }
        });
    }

    @Test
    void varargArray() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                record A(int[]... value) {
                }

                int[] a = {1, 2, 3};
                int[] b = {4, 5, 6};

                return new A(a, b).value[1][2];
            }
        });
    }

    @Test
    void empty() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                record A() {
                }

                assert new A() != null;
            }
        });
    }

    @Test
    void additionalConstructor() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                record A(int value) {
                    A() {
                        this(100);
                    }
                }

                return new A().value;
            }
        });
    }

    @Test
    void additionalMethod() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                record A(int value) {
                    int calc() {
                        return value * 10;
                    }
                }

                return new A(3).calc();
            }
        });
    }

    @Test
    void additionalStaticMethod() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                record A(int value) {
                    static int calc(int x, int y) {
                        return x + y;
                    }
                }

                return A.calc(10, 3);
            }
        });
    }

    @Test
    void additionalCodeInConstructor() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                record A(int value) {

                    A {
                        value *= 10;
                    }
                }

                return new A(3).value;
            }
        });
    }

    @Test
    void additionalCodeInAccessor() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                record A(int value) {
                    public int value() {
                        return value * 10;
                    }
                }

                return new A(3).value();
            }
        });
    }

    @Test
    void implementInterface() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                interface X {
                    default int calc() {
                        return 10;
                    }
                }

                record A(int value) implements X {
                }

                return new A(3).calc();
            }
        });
    }
}