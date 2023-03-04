/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.grammar;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.TestCode;

class GenericsTest extends CodeVerifier {

    @Test
    void parameterizedType() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return size(List.of("1"));
            }

            private int size(List<String> param) {
                return param.get(0).length();
            }
        });
    }

    @Test
    void extendType() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return size(List.of("1"));
            }

            private int size(List<? extends CharSequence> param) {
                return param.get(0).length();
            }
        });
    }

    @Test
    void superType() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return size(List.of("1"));
            }

            private int size(List<? super CharSequence> param) {
                return ((CharSequence) param.get(0)).length();
            }
        });
    }

    @Test
    void wildcard() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return size(List.of("1"));
            }

            private int size(List<?> param) {
                return ((String) param.get(0)).length();
            }
        });
    }

    @Test
    void classVariable() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                class Main<A> {
                }

                TypeVariable[] params = Main.class.getTypeParameters();
                assert params.length == 1;
                assert params[0].getName().equals("A");
            }
        });
    }

    @Test
    void classVariables() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                class Main<A, B> {
                }

                TypeVariable[] params = Main.class.getTypeParameters();
                assert params.length == 2;
                assert params[0].getName().equals("A");
                assert params[1].getName().equals("B");
            }
        });
    }

    @Test
    void classBoundedVariable() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                class Main<A extends CharSequence> {
                }

                TypeVariable[] types = Main.class.getTypeParameters();
                assert types.length == 1;

                Type[] bounds = types[0].getBounds();
                assert bounds.length == 1;
                assert bounds[0] == CharSequence.class;
            }
        });
    }

    @Test
    void classParameterized() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                class Main<A extends Map<String, Date>> {
                }

                TypeVariable[] types = Main.class.getTypeParameters();
                assert types.length == 1;

                Type[] bounds = types[0].getBounds();
                assert bounds.length == 1;
                assert bounds[0] instanceof ParameterizedType;

                ParameterizedType parameterized = (ParameterizedType) bounds[0];
                Type[] params = parameterized.getActualTypeArguments();
                assert params[0] == String.class;
                assert params[1] == Date.class;
            }
        });
    }
}
