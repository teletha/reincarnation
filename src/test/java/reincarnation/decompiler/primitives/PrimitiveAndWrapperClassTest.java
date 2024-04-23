/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.primitives;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class PrimitiveAndWrapperClassTest extends CodeVerifier {

    @CrossDecompilerTest
    void IntegerPrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return int.class;
            }
        });
    }

    @CrossDecompilerTest
    void IntegerWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Integer.class;
            }
        });
    }

    @CrossDecompilerTest
    void IntegerPrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Integer.class != int.class;
                return java.lang.Integer.class == int.class;
            }
        });
    }

    @CrossDecompilerTest
    void LongPrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return long.class;
            }
        });
    }

    @CrossDecompilerTest
    void LongWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Long.class;
            }
        });
    }

    @CrossDecompilerTest
    void LongPrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Long.class != long.class;
                return java.lang.Long.class == long.class;
            }
        });
    }

    @CrossDecompilerTest
    void FloatPrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return float.class;
            }
        });
    }

    @CrossDecompilerTest
    void FloatWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Float.class;
            }
        });
    }

    @CrossDecompilerTest
    void FloatPrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Float.class != float.class;
                return java.lang.Float.class == float.class;
            }
        });
    }

    @CrossDecompilerTest
    void DoublePrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return double.class;
            }
        });
    }

    @CrossDecompilerTest
    void DoubleWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Double.class;
            }
        });
    }

    @CrossDecompilerTest
    void DoublePrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Double.class != double.class;
                return java.lang.Double.class == double.class;
            }
        });
    }

    @CrossDecompilerTest
    void ShortPrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return short.class;
            }
        });
    }

    @CrossDecompilerTest
    void ShortWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Short.class;
            }
        });
    }

    @CrossDecompilerTest
    void ShortPrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Short.class != short.class;
                return java.lang.Short.class == short.class;
            }
        });
    }

    @CrossDecompilerTest
    void BytePrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return byte.class;
            }
        });
    }

    @CrossDecompilerTest
    void ByteWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Byte.class;
            }
        });
    }

    @CrossDecompilerTest
    void BytePrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Byte.class != byte.class;
                return java.lang.Byte.class == byte.class;
            }
        });
    }

    @CrossDecompilerTest
    void BooleanPrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return boolean.class;
            }
        });
    }

    @CrossDecompilerTest
    void BooleanWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Boolean.class;
            }
        });
    }

    @CrossDecompilerTest
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