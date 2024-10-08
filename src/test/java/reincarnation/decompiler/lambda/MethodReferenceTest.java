/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.lambda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.IntFunction;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class MethodReferenceTest extends CodeVerifier {

    @CrossDecompilerTest
    void arrayReference() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                IntFunction<String[]> function = String[]::new;
                String[] array = function.apply(4);

                assert array.length == 4;
                assert array instanceof String[];
                assert array.getClass() == String[].class;
            }
        });
    }

    @CrossDecompilerTest
    void constructorReference() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                Supplier<ConstructorReference> function = ConstructorReference::new;
                ConstructorReference instance = function.get();

                assert instance.value == -1;
                assert instance instanceof ConstructorReference;
                assert instance.getClass() == ConstructorReference.class;
                assert ConstructorReference.class.isAssignableFrom(instance.getClass());
            }
        });
    }

    @CrossDecompilerTest
    void constructorReferenceWithParam() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                IntFunction<ConstructorReference> function = ConstructorReference::new;
                ConstructorReference instance = function.apply(10);

                assert instance.value == 10;
                assert instance instanceof ConstructorReference;
                assert instance.getClass() == ConstructorReference.class;
                assert ConstructorReference.class.isAssignableFrom(instance.getClass());
            }
        });
    }

    private static class ConstructorReference {

        private final int value;

        private ConstructorReference() {
            this.value = -1;
        }

        private ConstructorReference(int value) {
            this.value = value;
        }
    }

    @CrossDecompilerTest
    void methodReference() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                MethodReference ref = new MethodReference();
                IntFunction<String> function = ref::intFunction;
                String result = function.apply(10);

                assert result.equals("10");
                assert function instanceof IntFunction;
                assert IntFunction.class.isAssignableFrom(function.getClass());
            }
        });
    }

    @CrossDecompilerTest
    void staticMethodReference() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                LongSupplier supplier = MethodReference::staticLongSupplier;
                long result = supplier.getAsLong();

                assert result == 20L;
                assert supplier instanceof LongSupplier;
                assert LongSupplier.class.isAssignableFrom(supplier.getClass());
            }
        });
    }

    private static class MethodReference {

        String intFunction(int value) {
            return String.valueOf(value);
        }

        static long staticLongSupplier() {
            return 20L;
        }
    }

    @CrossDecompilerTest
    void supplierAsFunctionOnAbstractMethod() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                ToIntFunction<List> function = List::size;

                assert function.applyAsInt(new ArrayList()) == 0;
            }
        });
    }

    @CrossDecompilerTest
    void supplierAsFunctionOnConcreteMethod() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                ToIntFunction<ArrayList> function = ArrayList::size;

                assert function.applyAsInt(new ArrayList()) == 0;
            }
        });
    }

    @CrossDecompilerTest
    void functionAsBiFunction() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                BiFunction<String, String, String> function = String::concat;

                assert function.apply("Hello", "!").equals("Hello!");
            }
        });
    }

    @CrossDecompilerTest
    void functionAsBiFunctionOnAbstractMethod() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                Map<String, String> map = new HashMap();
                map.put("1", "one");

                BiFunction<Map<?, String>, String, String> function = Map::get;
                assert function.apply(map, "1").equals("one");
            }
        });
    }

    @CrossDecompilerTest
    void functionAsBiFunctionOnConcreteMethod() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                HashMap<String, String> map = new HashMap();
                map.put("1", "one");

                BiFunction<Map<?, String>, String, String> function = Map::get;
                assert function.apply(map, "1").equals("one");

                BiPredicate<HashMap, String> x = HashMap::containsKey;
                assert x.test(map, "1") == true;
            }
        });
    }

    @CrossDecompilerTest
    void functionAsBiFunctionWithComplicatedType() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                Map<String, List<? extends Number>> map = new HashMap();
                map.put("1", List.of(1, 1, 1));

                BiFunction<Map<?, List<? extends Number>>, String, List<? extends Number>> function = Map::get;
                assert function.apply(map, "1").equals(List.of(1, 1, 1));
            }
        });
    }

    @CrossDecompilerTest
    void defaultMethod() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                Default instance = new Default() {
                };
                Supplier<String> supplier = instance::value;
                assert supplier.get().equals("test");
            }
        });
    }

    private static interface Default {

        default String value() {
            return "test";
        }
    }

    @CrossDecompilerTest
    void typeVariable() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return length().applyAsInt("test");
            }

            private <T extends CharSequence> ToIntFunction<T> length() {
                return T::length;
            }
        });
    }
}