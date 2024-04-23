/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.grammar;

import java.util.function.Function;
import java.util.function.IntSupplier;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.Debuggable;
import reincarnation.TestCode;

class EnumTest extends CodeVerifier {

    @CrossDecompilerTest
    void name() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                enum Symbol {
                    A;
                }

                return Symbol.A.name();
            }
        });
    }

    @CrossDecompilerTest
    void ordinal() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                enum Symbol {
                    A;
                }

                return Symbol.A.ordinal();
            }
        });
    }

    @CrossDecompilerTest
    void valueOf() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                enum Symbol {
                    A;
                }

                return Symbol.valueOf("A").ordinal();
            }
        });
    }

    @CrossDecompilerTest
    void values() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                enum Symbol {
                    A;
                }

                return Symbol.values()[0].ordinal();
            }
        });
    }

    @CrossDecompilerTest
    void multiple() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                enum Symbol {
                    A, B, C, D, E;
                }

                return Symbol.values().length;
            }
        });
    }

    @CrossDecompilerTest
    @Debuggable
    void userDefiendConstructor() {
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

    @CrossDecompilerTest
    void userDefiendMultipleConstructors() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                enum Symbol {
                    A(1, 2), B(3), C(4, 5), D;

                    private int value;

                    private Symbol() {
                        this(10);
                        this.value += 20;
                    }

                    private Symbol(int value) {
                        this(value, value);
                        this.value -= 100;
                    }

                    private Symbol(int value, int multiplier) {
                        this.value = value * multiplier;
                    }
                }

                return Symbol.A.value + Symbol.B.value + Symbol.C.value + Symbol.D.value;
            }
        });
    }

    @CrossDecompilerTest
    void userDefinedInitializer() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                enum Symbol {
                    A;

                    private final String version;

                    {
                        version = System.getProperty("java.version");
                    }
                }

                return Symbol.A.version;
            }
        });
    }

    @CrossDecompilerTest
    void userDefinedStaticInitializer() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                enum Symbol {
                    A;

                    private static final String version;

                    static {
                        version = System.getProperty("java.version");
                    }
                }

                return Symbol.version;
            }
        });
    }

    @CrossDecompilerTest
    void userDefinedField() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                enum Symbol {
                    A;

                    public int value = 10;
                }

                return Symbol.A.value;
            }
        });
    }

    @CrossDecompilerTest
    void userDefinedStaticField() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                enum Symbol {
                    A;

                    public static final int count = Symbol.values().length;
                }

                return Symbol.count;
            }
        });
    }

    @CrossDecompilerTest
    void userDefiendMethod() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                enum Symbol {
                    A;

                    public int value() {
                        return 10;
                    }
                }

                return Symbol.A.value();
            }
        });
    }

    @CrossDecompilerTest
    void userDefiendStaticMethod() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                enum Symbol {
                    A;

                    public static int value() {
                        return 20;
                    }
                }

                return Symbol.value();
            }
        });
    }

    @CrossDecompilerTest
    void constantSpecificMethod() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                enum Symbol {
                    A {

                        @Override
                        protected int value() {
                            return 10;
                        }
                    };

                    protected abstract int value();
                }

                return Symbol.A.value();
            }
        });
    }

    @CrossDecompilerTest
    void constantSpecificStaticMethod() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                enum Symbol {
                    A {
                        private static int staticMethod() {
                            return 5;
                        }

                        @Override
                        int value() {
                            return staticMethod();
                        }
                    };

                    abstract int value();
                }

                return Symbol.A.value();
            }
        });
    }

    @CrossDecompilerTest
    void constantSpecificField() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                enum Symbol {
                    A {
                        private int value = 10;

                        @Override
                        protected int value() {
                            return value;
                        }
                    };

                    protected abstract int value();
                }

                return Symbol.A.value();
            }
        });
    }

    @CrossDecompilerTest
    void constantSpecificInitializer() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                enum Symbol {
                    A {
                        private int value;

                        {
                            value = 20;
                        }

                        @Override
                        protected int value() {
                            return value;
                        }
                    };

                    protected abstract int value();
                }

                return Symbol.A.value();
            }
        });
    }

    @CrossDecompilerTest
    void overrideUserDefinedMethodInConstant() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                enum Symbol {
                    A {
                        @Override
                        protected int value() {
                            return -2;
                        }
                    };

                    protected int value() {
                        return 0;
                    }
                }

                return Symbol.A.value();
            }
        });
    }

    @CrossDecompilerTest
    void implementInterface() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                enum Symbol implements IntSupplier {
                    A(20);

                    private final int value;

                    private Symbol(int value) {
                        this.value = value;
                    }

                    @Override
                    public int getAsInt() {
                        return value;
                    }
                }

                return Symbol.A.getAsInt();
            }
        });
    }

    @CrossDecompilerTest
    void implementParameterizedInterface() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                enum Symbol implements Function<Integer, Integer> {
                    A;

                    @Override
                    public Integer apply(Integer value) {
                        return value * 10;
                    }
                }

                return Symbol.A.apply(2);
            }
        });
    }
}