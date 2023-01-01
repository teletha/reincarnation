/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.primitives;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.CodeVerifier;

/**
 * @version 2018/10/10 11:14:56
 */
class PrimitiveAndWrapperClassTest extends CodeVerifier {

    @Test
    void IntegerPrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return int.class;
            }
        });
    }

    @Test
    void IntegerWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Integer.class;
            }
        });
    }

    @Test
    void IntegerPrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Integer.class != int.class;
                return java.lang.Integer.class == int.class;
            }
        });
    }

    @Test
    void LongPrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return long.class;
            }
        });
    }

    @Test
    void LongWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Long.class;
            }
        });
    }

    @Test
    void LongPrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Long.class != long.class;
                return java.lang.Long.class == long.class;
            }
        });
    }

    @Test
    void FloatPrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return float.class;
            }
        });
    }

    @Test
    void FloatWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Float.class;
            }
        });
    }

    @Test
    void FloatPrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Float.class != float.class;
                return java.lang.Float.class == float.class;
            }
        });
    }

    @Test
    void DoublePrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return double.class;
            }
        });
    }

    @Test
    void DoubleWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Double.class;
            }
        });
    }

    @Test
    void DoublePrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Double.class != double.class;
                return java.lang.Double.class == double.class;
            }
        });
    }

    @Test
    void ShortPrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return short.class;
            }
        });
    }

    @Test
    void ShortWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Short.class;
            }
        });
    }

    @Test
    void ShortPrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Short.class != short.class;
                return java.lang.Short.class == short.class;
            }
        });
    }

    @Test
    void BytePrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return byte.class;
            }
        });
    }

    @Test
    void ByteWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Byte.class;
            }
        });
    }

    @Test
    void BytePrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Byte.class != byte.class;
                return java.lang.Byte.class == byte.class;
            }
        });
    }

    @Test
    void BooleanPrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return boolean.class;
            }
        });
    }

    @Test
    void BooleanWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Boolean.class;
            }
        });
    }

    @Test
    void BooleanPrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Boolean.class != boolean.class;
                return java.lang.Boolean.class == boolean.class;
            }
        });
    }

}