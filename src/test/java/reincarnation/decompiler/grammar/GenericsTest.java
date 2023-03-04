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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.TestCode;

@SuppressWarnings("serial")
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

    @Test
    void extendsVariable() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                class Main<A> extends ArrayList<A> {
                }

                Type parent = Main.class.getGenericSuperclass();
                assert parent instanceof ParameterizedType;

                ParameterizedType parameterized = (ParameterizedType) parent;
                Type[] params = parameterized.getActualTypeArguments();
                assert params.length == 1;
                assert params[0] instanceof TypeVariable;
                assert params[0].getTypeName().equals("A");
            }
        });
    }

    @Test
    void extendsVariables() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                class Main<A, B> extends HashMap<A, B> {
                }

                Type parent = Main.class.getGenericSuperclass();
                assert parent instanceof ParameterizedType;

                ParameterizedType parameterized = (ParameterizedType) parent;
                Type[] params = parameterized.getActualTypeArguments();
                assert params.length == 2;
                assert params[0] instanceof TypeVariable;
                assert params[0].getTypeName().equals("A");
                assert params[1] instanceof TypeVariable;
                assert params[1].getTypeName().equals("B");
            }
        });
    }

    @Test
    void extendsParameterized() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                class Main extends HashMap<Integer, String> {
                }

                Type parent = Main.class.getGenericSuperclass();
                assert parent instanceof ParameterizedType;

                ParameterizedType parameterized = (ParameterizedType) parent;
                Type[] params = parameterized.getActualTypeArguments();
                assert params.length == 2;
                assert params[0] == Integer.class;
                assert params[1] == String.class;
            }
        });
    }

    @Test
    void implementsVariable() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                abstract class Main<A> implements List<A> {
                }

                Type parent = Main.class.getGenericInterfaces()[0];
                assert parent instanceof ParameterizedType;

                ParameterizedType parameterized = (ParameterizedType) parent;
                Type[] params = parameterized.getActualTypeArguments();
                assert params.length == 1;
                assert params[0] instanceof TypeVariable;
                assert params[0].getTypeName().equals("A");
            }
        });
    }

    @Test
    void implementsVariables() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                abstract class Main<A, B> implements Map<A, B> {
                }

                Type parent = Main.class.getGenericInterfaces()[0];
                assert parent instanceof ParameterizedType;

                ParameterizedType parameterized = (ParameterizedType) parent;
                Type[] params = parameterized.getActualTypeArguments();
                assert params.length == 2;
                assert params[0] instanceof TypeVariable;
                assert params[0].getTypeName().equals("A");
                assert params[1] instanceof TypeVariable;
                assert params[1].getTypeName().equals("B");
            }
        });
    }

    @Test
    void implementsParameterized() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                abstract class Main implements Map<Integer, String> {
                }

                Type parent = Main.class.getGenericInterfaces()[0];
                assert parent instanceof ParameterizedType;

                ParameterizedType parameterized = (ParameterizedType) parent;
                Type[] params = parameterized.getActualTypeArguments();
                assert params.length == 2;
                assert params[0] == Integer.class;
                assert params[1] == String.class;
            }
        });
    }
}
