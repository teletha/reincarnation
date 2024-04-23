/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.grammar;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

@SuppressWarnings("serial")
class GenericsTest extends CodeVerifier {

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
    void genericArray() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                interface Main<T> extends Map<T[], T[][]> {
                }

                Type parent = Main.class.getGenericInterfaces()[0];
                assert parent instanceof ParameterizedType;

                ParameterizedType parameterized = (ParameterizedType) parent;
                Type[] params = parameterized.getActualTypeArguments();
                assert params.length == 2;
                assert params[0] instanceof GenericArrayType;
                assert params[1] instanceof GenericArrayType;
            }
        });
    }
}