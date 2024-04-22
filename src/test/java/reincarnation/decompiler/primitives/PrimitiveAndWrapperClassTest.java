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
import reincarnation.CompilableTest;
import reincarnation.TestCode;

class PrimitiveAndWrapperClassTest extends CodeVerifier {

    @CompilableTest
    void IntegerPrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return int.class;
            }
        });
    }

    @CompilableTest
    void IntegerWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Integer.class;
            }
        });
    }

    @CompilableTest
    void IntegerPrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Integer.class != int.class;
                return java.lang.Integer.class == int.class;
            }
        });
    }

    @CompilableTest
    void LongPrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return long.class;
            }
        });
    }

    @CompilableTest
    void LongWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Long.class;
            }
        });
    }

    @CompilableTest
    void LongPrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Long.class != long.class;
                return java.lang.Long.class == long.class;
            }
        });
    }

    @CompilableTest
    void FloatPrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return float.class;
            }
        });
    }

    @CompilableTest
    void FloatWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Float.class;
            }
        });
    }

    @CompilableTest
    void FloatPrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Float.class != float.class;
                return java.lang.Float.class == float.class;
            }
        });
    }

    @CompilableTest
    void DoublePrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return double.class;
            }
        });
    }

    @CompilableTest
    void DoubleWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Double.class;
            }
        });
    }

    @CompilableTest
    void DoublePrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Double.class != double.class;
                return java.lang.Double.class == double.class;
            }
        });
    }

    @CompilableTest
    void ShortPrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return short.class;
            }
        });
    }

    @CompilableTest
    void ShortWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Short.class;
            }
        });
    }

    @CompilableTest
    void ShortPrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Short.class != short.class;
                return java.lang.Short.class == short.class;
            }
        });
    }

    @CompilableTest
    void BytePrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return byte.class;
            }
        });
    }

    @CompilableTest
    void ByteWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Byte.class;
            }
        });
    }

    @CompilableTest
    void BytePrimitiveAndWrapper() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                assert java.lang.Byte.class != byte.class;
                return java.lang.Byte.class == byte.class;
            }
        });
    }

    @CompilableTest
    void BooleanPrimitive() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return boolean.class;
            }
        });
    }

    @CompilableTest
    void BooleanWrapper() {
        verify(new TestCode.Object<Class>() {

            @Override
            public Class run() {
                return java.lang.Boolean.class;
            }
        });
    }

    @CompilableTest
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