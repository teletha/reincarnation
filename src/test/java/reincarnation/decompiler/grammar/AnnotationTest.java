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

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.Debuggable;
import reincarnation.TestCode;
import reincarnation.decompiler.grammar.annotation.Mark;
import reincarnation.decompiler.grammar.annotation.Meta;
import reincarnation.decompiler.grammar.annotation.Symbol;

@Debuggable
class AnnotationTest extends CodeVerifier {

    @Test
    void onClass() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {

                @Mark
                class A {
                }

                return A.class.isAnnotationPresent(Mark.class);
            }
        });
    }

    @Test
    void onMethod() {
        verify(new TestCode.Int() {

            @Override
            public int run() {

                interface A {
                    @Mark(intValue = 20)
                    void test();
                }

                return Meta.findMark(A.class, "test").intValue();
            }
        });
    }

    @Test
    void onField() {
        verify(new TestCode.Int() {

            @Override
            public int run() {

                class A {
                    @Mark(intValue = 20)
                    String test = "ok";
                }

                return Meta.findMark(A.class, "test").intValue();
            }
        });
    }

    @Test
    void onConstructor() {
        verify(new TestCode.Int() {

            @Override
            public int run() {

                class A {
                    @Mark(intValue = 20)
                    A() {
                    }
                }

                return Meta.findConstructorMark(A.class).intValue();
            }
        });
    }

    @Test
    void intValue() {
        verify(new TestCode.Int() {

            @Override
            public int run() {

                @Mark(intValue = 10)
                class A {
                }

                return Meta.findMark(A.class).intValue();
            }
        });
    }

    @Test
    void longValue() {
        verify(new TestCode.Long() {

            @Override
            public long run() {

                @Mark(longValue = 10)
                class A {
                }

                return Meta.findMark(A.class).intValue();
            }
        });
    }

    @Test
    void floatValue() {
        verify(new TestCode.Float() {

            @Override
            public float run() {

                @Mark(floatValue = 10)
                class A {
                }

                return Meta.findMark(A.class).floatValue();
            }
        });
    }

    @Test
    void doubleValue() {
        verify(new TestCode.Double() {

            @Override
            public double run() {

                @Mark(doubleValue = 10)
                class A {
                }

                return Meta.findMark(A.class).doubleValue();
            }
        });
    }

    @Test
    void shortValue() {
        verify(new TestCode.Short() {

            @Override
            public short run() {

                @Mark(shortValue = 10)
                class A {
                }

                return Meta.findMark(A.class).shortValue();
            }
        });
    }

    @Test
    void byteValue() {
        verify(new TestCode.Byte() {

            @Override
            public byte run() {

                @Mark(byteValue = 10)
                class A {
                }

                return Meta.findMark(A.class).byteValue();
            }
        });
    }

    @Test
    void charValue() {
        verify(new TestCode.Char() {

            @Override
            public char run() {

                @Mark(charValue = 'R')
                class A {
                }

                return Meta.findMark(A.class).charValue();
            }
        });
    }

    @Test
    void booleanValue() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {

                @Mark(booleanValue = true)
                class A {
                }

                return Meta.findMark(A.class).booleanValue();
            }
        });
    }

    @Test
    void stringValue() {
        verify(new TestCode.Text() {

            @Override
            public String run() {

                @Mark(stringValue = "test")
                class A {
                }

                return Meta.findMark(A.class).stringValue();
            }
        });
    }

    @Test
    void enumValue() {
        verify(new TestCode.Text() {

            @Override
            public String run() {

                @Mark(enumValue = Symbol.C)
                class A {
                }

                return Meta.findMark(A.class).enumValue().name();
            }
        });
    }

    @Test
    void classValue() {
        verify(new TestCode.Text() {

            @Override
            public String run() {

                @Mark(classValue = List.class)
                class A {
                }

                return Meta.findMark(A.class).classValue().getName();
            }
        });
    }

    @Test
    void annotationValue() {
        verify(new TestCode.Text() {

            @Override
            public String run() {

                @Mark(annotationValue = @Retention(RetentionPolicy.CLASS))
                class A {
                }

                return Meta.findMark(A.class).annotationValue().value().name();
            }
        });
    }

    @Test
    void arrayValue() {
        verify(new TestCode.Int() {

            @Override
            public int run() {

                @Mark(arrayIntValue = {3, 4})
                class A {
                }

                return Meta.findMark(A.class).arrayIntValue()[0];
            }
        });
    }

    @Test
    void multiValues() {
        verify(new TestCode.Long() {

            @Override
            public long run() {

                @Mark(intValue = 10, longValue = 20)
                class A {
                }

                Mark mark = Meta.findMark(A.class);
                return mark.intValue() + mark.longValue();
            }
        });
    }
}