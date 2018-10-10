/*
 * Copyright (C) 2016 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.primitives;

import org.junit.jupiter.api.Test;

import reincarnation.Code;
import reincarnation.CodeVerifier;
import reincarnation.Debuggable;

/**
 * @version 2018/10/10 11:14:56
 */
class PrimitiveAndWrapperClassTest extends CodeVerifier {

    @Test
    void IntegerPrimitive() {
        verify(new Code.Object<Class>() {

            @Override
            public Class run() {
                return int.class;
            }
        });
    }

    @Test
    void IntegerWrapper() {
        verify(new Code.Object<Class>() {

            @Override
            public Class run() {
                return Integer.class;
            }
        });
    }

    @Test
    void IntegerPrimitiveAndWrapper() {
        verify(new Code.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Integer.class != int.class;
                return java.lang.Integer.class == int.class;
            }
        });
    }

    @Test
    void LongPrimitive() {
        verify(new Code.Object<Class>() {

            @Override
            public Class run() {
                return long.class;
            }
        });
    }

    @Test
    void LongWrapper() {
        verify(new Code.Object<Class>() {

            @Override
            public Class run() {
                return Long.class;
            }
        });
    }

    @Test
    void LongPrimitiveAndWrapper() {
        verify(new Code.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Long.class != long.class;
                return java.lang.Long.class == long.class;
            }
        });
    }

    @Test
    void FloatPrimitive() {
        verify(new Code.Object<Class>() {

            @Override
            public Class run() {
                return float.class;
            }
        });
    }

    @Test
    void FloatWrapper() {
        verify(new Code.Object<Class>() {

            @Override
            public Class run() {
                return Float.class;
            }
        });
    }

    @Test
    void FloatPrimitiveAndWrapper() {
        verify(new Code.Boolean() {

            @Override
            @Debuggable
            public boolean run() {
                assert java.lang.Float.class != float.class;
                return java.lang.Float.class == float.class;
            }
        });
    }

    @Test
    void DoublePrimitive() {
        verify(new Code.Object<Class>() {

            @Override
            public Class run() {
                return double.class;
            }
        });
    }

    @Test
    void DoubleWrapper() {
        verify(new Code.Object<Class>() {

            @Override
            public Class run() {
                return Double.class;
            }
        });
    }

    @Test
    void DoublePrimitiveAndWrapper() {
        verify(new Code.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Double.class != double.class;
                return java.lang.Double.class == double.class;
            }
        });
    }

    @Test
    void ShortPrimitive() {
        verify(new Code.Object<Class>() {

            @Override
            public Class run() {
                return short.class;
            }
        });
    }

    @Test
    void ShortWrapper() {
        verify(new Code.Object<Class>() {

            @Override
            public Class run() {
                return Short.class;
            }
        });
    }

    @Test
    void ShortPrimitiveAndWrapper() {
        verify(new Code.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Short.class != short.class;
                return java.lang.Short.class == short.class;
            }
        });
    }

    @Test
    void BytePrimitive() {
        verify(new Code.Object<Class>() {

            @Override
            public Class run() {
                return byte.class;
            }
        });
    }

    @Test
    void ByteWrapper() {
        verify(new Code.Object<Class>() {

            @Override
            public Class run() {
                return Byte.class;
            }
        });
    }

    @Test
    void BytePrimitiveAndWrapper() {
        verify(new Code.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Byte.class != byte.class;
                return java.lang.Byte.class == byte.class;
            }
        });
    }

    @Test
    void BooleanPrimitive() {
        verify(new Code.Object<Class>() {

            @Override
            public Class run() {
                return boolean.class;
            }
        });
    }

    @Test
    void BooleanWrapper() {
        verify(new Code.Object<Class>() {

            @Override
            public Class run() {
                return Boolean.class;
            }
        });
    }

    @Test
    void BooleanPrimitiveAndWrapper() {
        verify(new Code.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Boolean.class != boolean.class;
                return java.lang.Boolean.class == boolean.class;
            }
        });
    }

}
