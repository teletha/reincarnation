/*
 * Copyright (C) 2016 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.lambda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.Debuggable;
import reincarnation.TestCode;

@Debuggable
class LambdaTest extends CodeVerifier {

    @Test
    void inlineWithoutArg() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return lambda(() -> 10);
            }

            private int lambda(IntSupplier supplier) {
                return supplier.getAsInt();
            }
        });
    }

    @Test
    void inlineWithArg() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return lambda(x -> x + 10);
            }

            private int lambda(IntUnaryOperator supplier) {
                return supplier.applyAsInt(10);
            }
        });
    }

    @Test
    void inlineWithArgs() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return lambda((x, y) -> x + y);
            }

            private int lambda(IntBinaryOperator supplier) {
                return supplier.applyAsInt(10, 20);
            }
        });
    }

    @Test
    void inlineWithObject() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return lambda(x -> x.toUpperCase());
            }

            private String lambda(Function<String, String> function) {
                return function.apply("test");
            }
        });
    }

    @Test
    void inlineWithParamiterizedType() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return lambda(x -> x.subList(2, 3)).get(0);
            }

            private List<String> lambda(Function<List<String>, List<String>> function) {
                return function.apply(List.of("1", "2", "3"));
            }
        });
    }

    @Test
    void useLocalVariable() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int local = 10;

                return lambda(() -> local);
            }

            private int lambda(IntSupplier supplier) {
                return supplier.getAsInt();
            }
        });
    }

    @Test
    void useLocalVariables() {
        verify(new TestCode.Long() {

            @Override
            public long run() {
                int local1 = 10;
                long local2 = 20;

                return lambda(() -> local1 + local2);
            }

            private long lambda(LongSupplier supplier) {
                return supplier.getAsLong();
            }
        });
    }

    @Test
    void useLocalVariableWithArg() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                String local = "!!";

                return lambda(x -> x.concat(local));
            }

            private String lambda(Function<String, String> function) {
                return function.apply("test");
            }
        });
    }

    @Test
    void useLocalVariablesWithArg() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                String local1 = "Hello ";
                String local2 = "!!";

                return lambda(x -> local1 + x + local2);
            }

            private String lambda(Function<String, String> function) {
                return function.apply("test");
            }
        });
    }

    @Test
    void useLocalVariablesWithArgs() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                String local2 = "!!";
                String local1 = "Hello ";

                return lambda((x, y) -> local1 + x + y + local2);
            }

            private String lambda(BiFunction<String, Integer, String> function) {
                return function.apply("test", 1);
            }
        });
    }

    @Test
    void useLocalVariableWithArgNest() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int local = 10;
                int deep = 20;

                return lambda1(x -> local - x * lambda2(y -> y + deep));
            }

            private int lambda1(IntUnaryOperator function) {
                return function.applyAsInt(3);
            }

            private int lambda2(IntUnaryOperator function) {
                return function.applyAsInt(-5);
            }
        });
    }

    @Test
    void arrayReference() {
        IntFunction<String[]> function = String[]::new;
        String[] array = function.apply(4);

        assert array.length == 4;
        assert array instanceof String[];
        assert array.getClass() == String[].class;
    }

    @Test
    void constructorReference() {
        Supplier<ConstructorReference> function = ConstructorReference::new;
        ConstructorReference instance = function.get();

        assert instance.value == -1;
        assert instance instanceof ConstructorReference;
        assert instance.getClass() == ConstructorReference.class;
        assert ConstructorReference.class.isAssignableFrom(instance.getClass());
    }

    @Test
    void constructorReferenceWithParam() {
        IntFunction<ConstructorReference> function = ConstructorReference::new;
        ConstructorReference instance = function.apply(10);

        assert instance.value == 10;
        assert instance instanceof ConstructorReference;
        assert instance.getClass() == ConstructorReference.class;
        assert ConstructorReference.class.isAssignableFrom(instance.getClass());
    }

    /**
     * @version 2015/02/26 11:34:52
     */
    private static class ConstructorReference {

        private final int value;

        private ConstructorReference() {
            this.value = -1;
        }

        /**
         * @param value
         */
        private ConstructorReference(int value) {
            this.value = value;
        }
    }

    @Test
    void methodReference() {
        MethodReference ref = new MethodReference();
        IntFunction<String> function = ref::intFunction;
        String result = function.apply(10);

        assert result.equals("10");
        assert function instanceof IntFunction;
        assert IntFunction.class.isAssignableFrom(function.getClass());
    }

    @Test
    void staticMethodReference() {
        LongSupplier supplier = MethodReference::staticLongSupplier;
        long result = supplier.getAsLong();

        assert result == 20L;
        assert supplier instanceof LongSupplier;
        assert LongSupplier.class.isAssignableFrom(supplier.getClass());
    }

    /**
     * @version 2015/02/26 13:42:06
     */
    private static class MethodReference {

        String intFunction(int value) {
            return String.valueOf(value);
        }

        static long staticLongSupplier() {
            return 20L;
        }
    }

    @Test
    void supplierAsFunctionOnAbstractMethod() throws Exception {
        ToIntFunction<List> function = List::size;

        assert function.applyAsInt(new ArrayList()) == 0;
    }

    @Test
    void supplierAsFunctionOnConcreteMethod() throws Exception {
        ToIntFunction<ArrayList> function = ArrayList::size;

        assert function.applyAsInt(new ArrayList()) == 0;
    }

    @Test
    void functionAsBiFunction() throws Exception {
        BiFunction<String, String, String> function = String::concat;

        assert function.apply("Hello", "!").equals("Hello!");
    }

    @Test
    void functionAsBiFunctionOnAbstractMethod() throws Exception {
        Map<String, String> map = new HashMap();
        map.put("1", "one");

        BiFunction<Map<String, String>, String, String> function = Map::get;
        assert function.apply(map, "1").equals("one");
    }

    @Test
    void functionAsBiFunctionOnConcreteMethod() throws Exception {
        HashMap<String, String> map = new HashMap();
        map.put("1", "one");

        BiFunction<HashMap<String, String>, String, String> function = HashMap::get;
        assert function.apply(map, "1").equals("one");
    }

    @Test
    void defaultMethod() throws Exception {
        Default instance = new Default() {
        };
        Supplier<String> supplier = instance::value;
        assert supplier.get().equals("test");
    }

    /**
     * @version 2015/03/01 0:41:00
     */
    private static interface Default {

        default String value() {
            return "test";
        }
    }
}
