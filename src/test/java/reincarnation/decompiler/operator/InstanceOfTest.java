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
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class InstanceOfTest extends CodeVerifier {

    @CrossDecompilerTest
    void base() {
        verify(new Base());
    }

    private static class Base implements TestCode.Boolean {

        @Override
        public boolean run() {
            return this instanceof Base;
        }
    }

    @CrossDecompilerTest
    void negate() {
        verify(new Negate());
    }

    private static class Negate implements TestCode.Boolean {

        @Override
        public boolean run() {
            return !(this instanceof Negate);
        }
    }

    @CrossDecompilerTest
    void InstanceOfChild() {
        verify(new Child1());
    }

    private static class Child1 extends Base {

        @Override
        public boolean run() {
            return this instanceof Child1;
        }
    }

    @CrossDecompilerTest
    void InstanceOfBase() {
        verify(new Child2());
    }

    private static class Child2 extends Base {

        @Override
        public boolean run() {
            return this instanceof Base;
        }
    }

    @CrossDecompilerTest
    void InstanceOfObject() {
        verify(new OBJECT());
    }

    private static class OBJECT implements TestCode.Boolean {

        @Override
        public boolean run() {
            return this instanceof Object;
        }
    }

    @CrossDecompilerTest
    void InstanceOfInterface() {
        verify(new ValidInterface());
    }

    private static class ValidInterface implements TestCode.Boolean {

        @Override
        public boolean run() {
            return this instanceof TestCode.Boolean;
        }
    }

    @CrossDecompilerTest
    void InstanceOfInvalidInterface() {
        verify(new ValidInterface());
    }

    @CrossDecompilerTest
    void StringLiteral() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return "text" instanceof String;
            }
        });
    }

    @CrossDecompilerTest
    void ArrayPrimitive() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                int[] values = new int[0];
                return values instanceof int[];
            }
        });
    }

    @CrossDecompilerTest
    void ArrayObject() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                String[] values = new String[0];
                return values instanceof String[];
            }
        });
    }

    @CrossDecompilerTest
    void StringNull() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                String value = null;
                return value instanceof String;
            }
        });
    }

    @CrossDecompilerTest
    void ConcreateNull() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                ArrayList value = null;
                return value instanceof ArrayList;
            }
        });
    }

    @CrossDecompilerTest
    void InterfaceNull() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                List value = null;
                return value instanceof List;
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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