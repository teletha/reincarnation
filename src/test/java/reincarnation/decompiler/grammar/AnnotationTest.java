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
import reincarnation.decompiler.grammar.annotation.Marks;
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

                return Meta.findMethodMark(A.class).intValue();
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

                return Meta.findFieldMark(A.class).intValue();
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

    @Mark(intValue = 20)
    @interface AnnotatedAnnotation {
    }

    @Test
    void onAnnotation() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return Meta.findTypeMark(AnnotatedAnnotation.class).intValue();
            }
        });
    }

    @Test
    void onParameter() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                interface A {
                    void test(@Mark(intValue = 3) int value);
                }

                return Meta.findParameterMark(A.class).intValue();
            }
        });
    }

    @Test
    void intValue() {
        verify(new TestCode.Int() {

            @Override
            public int run() {

                @Mark(intValue = 10)
                interface A {
                }

                return Meta.findTypeMark(A.class).intValue();
            }
        });
    }

    @Test
    void longValue() {
        verify(new TestCode.Long() {

            @Override
            public long run() {

                @Mark(longValue = 10)
                interface A {
                }

                return Meta.findTypeMark(A.class).intValue();
            }
        });
    }

    @Test
    void floatValue() {
        verify(new TestCode.Float() {

            @Override
            public float run() {

                @Mark(floatValue = 10)
                interface A {
                }

                return Meta.findTypeMark(A.class).floatValue();
            }
        });
    }

    @Test
    void doubleValue() {
        verify(new TestCode.Double() {

            @Override
            public double run() {

                @Mark(doubleValue = 10)
                interface A {
                }

                return Meta.findTypeMark(A.class).doubleValue();
            }
        });
    }

    @Test
    void shortValue() {
        verify(new TestCode.Short() {

            @Override
            public short run() {

                @Mark(shortValue = 10)
                interface A {
                }

                return Meta.findTypeMark(A.class).shortValue();
            }
        });
    }

    @Test
    void byteValue() {
        verify(new TestCode.Byte() {

            @Override
            public byte run() {

                @Mark(byteValue = 10)
                interface A {
                }

                return Meta.findTypeMark(A.class).byteValue();
            }
        });
    }

    @Test
    void charValue() {
        verify(new TestCode.Char() {

            @Override
            public char run() {

                @Mark(charValue = 'R')
                interface A {
                }

                return Meta.findTypeMark(A.class).charValue();
            }
        });
    }

    @Test
    void booleanValue() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {

                @Mark(booleanValue = true)
                interface A {
                }

                return Meta.findTypeMark(A.class).booleanValue();
            }
        });
    }

    @Test
    void stringValue() {
        verify(new TestCode.Text() {

            @Override
            public String run() {

                @Mark(stringValue = "test")
                interface A {
                }

                return Meta.findTypeMark(A.class).stringValue();
            }
        });
    }

    @Test
    void enumValue() {
        verify(new TestCode.Text() {

            @Override
            public String run() {

                @Mark(enumValue = Symbol.C)
                interface A {
                }

                return Meta.findTypeMark(A.class).enumValue().name();
            }
        });
    }

    @Test
    void classValue() {
        verify(new TestCode.Text() {

            @Override
            public String run() {

                @Mark(classValue = List.class)
                interface A {
                }

                return Meta.findTypeMark(A.class).classValue().getName();
            }
        });
    }

    @Test
    void annotationValue() {
        verify(new TestCode.Text() {

            @Override
            public String run() {

                @Mark(annotationValue = @Retention(RetentionPolicy.CLASS))
                interface A {
                }

                return Meta.findTypeMark(A.class).annotationValue().value().name();
            }
        });
    }

    @Test
    void arrayValue() {
        verify(new TestCode.Int() {

            @Override
            public int run() {

                @Mark(arrayIntValue = {3, 4})
                interface A {
                }

                return Meta.findTypeMark(A.class).arrayIntValue()[0];
            }
        });
    }

    @Test
    void multiValues() {
        verify(new TestCode.Long() {

            @Override
            public long run() {

                @Mark(intValue = 10, longValue = 20)
                interface A {
                }

                Mark mark = Meta.findTypeMark(A.class);
                return mark.intValue() + mark.longValue();
            }
        });
    }

    @Test
    void repeat() {
        verify(new TestCode.Long() {

            @Override
            public long run() {

                @Mark(longValue = 20)
                @Mark(intValue = 10)
                interface A {
                }

                Marks mark = Meta.findType(A.class, Marks.class);
                return mark.value()[0].longValue() + mark.value()[1].intValue();
            }
        });
    }
}