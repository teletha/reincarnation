/*
 * Copyright (C) 2023 The REINCARNATION Development Team
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

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.Debuggable;
import reincarnation.TestCode;

class InstanceOfTest extends CodeVerifier {

    @Test
    void InstanceOf() {
        verify(new Base());
    }

    private static class Base implements TestCode.Boolean {

        @Override
        public boolean run() {
            return this instanceof Base;
        }
    }

    @Test
    void InstanceOfChild() {
        verify(new Child1());
    }

    private static class Child1 extends Base {

        @Override
        public boolean run() {
            return this instanceof Child1;
        }
    }

    @Test
    void InstanceOfBase() {
        verify(new Child2());
    }

    private static class Child2 extends Base {

        @Override
        public boolean run() {
            return this instanceof Base;
        }
    }

    @Test
    void InstanceOfObject() {
        verify(new OBJECT());
    }

    private static class OBJECT implements TestCode.Boolean {

        @Override
        public boolean run() {
            return this instanceof Object;
        }
    }

    @Test
    void InstanceOfInterface() {
        verify(new ValidInterface());
    }

    private static class ValidInterface implements TestCode.Boolean {

        @Override
        public boolean run() {
            return this instanceof TestCode.Boolean;
        }
    }

    @Test
    void InstanceOfInvalidInterface() {
        verify(new ValidInterface());
    }

    @Test
    void StringLiteral() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return "text" instanceof String;
            }
        });
    }

    @Test
    void ArrayPrimitive() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                int[] values = new int[0];
                return values instanceof int[];
            }
        });
    }

    @Test
    void ArrayObject() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                String[] values = new String[0];
                return values instanceof String[];
            }
        });
    }

    @Test
    void StringNull() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                String value = null;
                return value instanceof String;
            }
        });
    }

    @Test
    void ConcreateNull() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                ArrayList value = null;
                return value instanceof ArrayList;
            }
        });
    }

    @Test
    void InterfaceNull() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                List value = null;
                return value instanceof List;
            }
        });
    }

    @Test
    @Debuggable
    void withoutCast() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                String buffer = "";
                List list = List.of("1", "2", "3");
                for (java.lang.Object value : list) {
                    if (value instanceof String) {
                        buffer += (String) value;
                    }
                }
                return buffer;
            }
        });
    }

    @Test
    @Debuggable
    void withCast() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                String buffer = "";
                List list = List.of("1", "2", "3");
                for (java.lang.Object value : list) {
                    if (value instanceof String text) {
                        buffer += text;
                    }
                }
                return buffer;
            }
        });
    }
}