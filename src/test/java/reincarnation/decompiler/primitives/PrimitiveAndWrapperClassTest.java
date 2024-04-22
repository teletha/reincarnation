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
import reincarnation.DecompilableTest;
import reincarnation.TestCode;

class PrimitiveAndWrapperClassTest extends CodeVerifier {

    @DecompilableTest
    void IntegerPrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return int.class;
            }
        });
    }

    @DecompilableTest
    void IntegerWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Integer.class;
            }
        });
    }

    @DecompilableTest
    void IntegerPrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Integer.class != int.class;
                return java.lang.Integer.class == int.class;
            }
        });
    }

    @DecompilableTest
    void LongPrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return long.class;
            }
        });
    }

    @DecompilableTest
    void LongWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Long.class;
            }
        });
    }

    @DecompilableTest
    void LongPrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Long.class != long.class;
                return java.lang.Long.class == long.class;
            }
        });
    }

    @DecompilableTest
    void FloatPrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return float.class;
            }
        });
    }

    @DecompilableTest
    void FloatWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Float.class;
            }
        });
    }

    @DecompilableTest
    void FloatPrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Float.class != float.class;
                return java.lang.Float.class == float.class;
            }
        });
    }

    @DecompilableTest
    void DoublePrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return double.class;
            }
        });
    }

    @DecompilableTest
    void DoubleWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Double.class;
            }
        });
    }

    @DecompilableTest
    void DoublePrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Double.class != double.class;
                return java.lang.Double.class == double.class;
            }
        });
    }

    @DecompilableTest
    void ShortPrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return short.class;
            }
        });
    }

    @DecompilableTest
    void ShortWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Short.class;
            }
        });
    }

    @DecompilableTest
    void ShortPrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Short.class != short.class;
                return java.lang.Short.class == short.class;
            }
        });
    }

    @DecompilableTest
    void BytePrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return byte.class;
            }
        });
    }

    @DecompilableTest
    void ByteWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Byte.class;
            }
        });
    }

    @DecompilableTest
    void BytePrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Byte.class != byte.class;
                return java.lang.Byte.class == byte.class;
            }
        });
    }

    @DecompilableTest
    void BooleanPrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return boolean.class;
            }
        });
    }

    @DecompilableTest
    void BooleanWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Boolean.class;
            }
        });
    }

    @DecompilableTest
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