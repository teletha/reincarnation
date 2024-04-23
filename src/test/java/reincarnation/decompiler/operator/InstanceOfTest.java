/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.operator;

import java.util.ArrayList;
import java.util.List;

import reincarnation.CodeVerifier;
import reincarnation.DecompilableTest;
import reincarnation.TestCode;

class InstanceOfTest extends CodeVerifier {

    @DecompilableTest
    void base() {
        verify(new Base());
    }

    private static class Base implements TestCode.Boolean {

        @Override
        public boolean run() {
            return this instanceof Base;
        }
    }

    @DecompilableTest
    void negate() {
        verify(new Negate());
    }

    private static class Negate implements TestCode.Boolean {

        @Override
        public boolean run() {
            return !(this instanceof Negate);
        }
    }

    @DecompilableTest
    void InstanceOfChild() {
        verify(new Child1());
    }

    private static class Child1 extends Base {

        @Override
        public boolean run() {
            return this instanceof Child1;
        }
    }

    @DecompilableTest
    void InstanceOfBase() {
        verify(new Child2());
    }

    private static class Child2 extends Base {

        @Override
        public boolean run() {
            return this instanceof Base;
        }
    }

    @DecompilableTest
    void InstanceOfObject() {
        verify(new OBJECT());
    }

    private static class OBJECT implements TestCode.Boolean {

        @Override
        public boolean run() {
            return this instanceof Object;
        }
    }

    @DecompilableTest
    void InstanceOfInterface() {
        verify(new ValidInterface());
    }

    private static class ValidInterface implements TestCode.Boolean {

        @Override
        public boolean run() {
            return this instanceof TestCode.Boolean;
        }
    }

    @DecompilableTest
    void InstanceOfInvalidInterface() {
        verify(new ValidInterface());
    }

    @DecompilableTest
    void StringLiteral() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return "text" instanceof String;
            }
        });
    }

    @DecompilableTest
    void ArrayPrimitive() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                int[] values = new int[0];
                return values instanceof int[];
            }
        });
    }

    @DecompilableTest
    void ArrayObject() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                String[] values = new String[0];
                return values instanceof String[];
            }
        });
    }

    @DecompilableTest
    void StringNull() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                String value = null;
                return value instanceof String;
            }
        });
    }

    @DecompilableTest
    void ConcreateNull() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                ArrayList value = null;
                return value instanceof ArrayList;
            }
        });
    }

    @DecompilableTest
    void InterfaceNull() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                List value = null;
                return value instanceof List;
            }
        });
    }

    @DecompilableTest
    void withoutCast() {
        verify(new TestCode.Text() {

            CharSequence cs = "test";

            @Override
            public String run() {
                String result = "";
                if (cs instanceof String) {
                    result = (String) cs;
                }
                return result;
            }
        });
    }

    @DecompilableTest
    void withCast() {
        verify(new TestCode.Text() {

            CharSequence cs = "test";

            @Override
            public String run() {
                String result = "";
                if (cs instanceof String text) {
                    result = text;
                }
                return result;
            }
        });
    }
}