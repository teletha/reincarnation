/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler;

import static org.junit.jupiter.api.Assumptions.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.caoccao.javet.interop.V8Runtime;
import com.caoccao.javet.values.V8Value;
import com.caoccao.javet.values.primitive.V8ValueBigInteger;
import com.caoccao.javet.values.primitive.V8ValueBoolean;
import com.caoccao.javet.values.primitive.V8ValueDouble;
import com.caoccao.javet.values.primitive.V8ValueInteger;
import com.caoccao.javet.values.primitive.V8ValueLong;
import com.caoccao.javet.values.primitive.V8ValueNull;
import com.caoccao.javet.values.primitive.V8ValueString;
import com.caoccao.javet.values.reference.V8ValueArray;

import reincarnation.JavaVerifier;
import reincarnation.Reincarnation;
import reincarnation.ShowJavaResultOnly;
import reincarnation.TestCode;
import reincarnation.TestCode.Param;
import reincarnation.coder.js.JavascriptCoder;
import reincarnation.coder.js.JavascriptCodingOption;

/**
 * <p>
 * Verify the decomposed JavaScript code by comparing the invocation results of the original Java
 * method with those of the decompiled JavaScript code executed on a JavaScript runtime.
 * </p>
 * <p>
 * This is the JavaScript counterpart of {@link reincarnation.CodeVerifier}.
 * </p>
 */
public class JavascriptCodeVerifier {

    /** No parameter. */
    private static final Object NoParam = new Object();

    /** The built-in parameters. */
    private static final List<Integer> ints = List.of(Integer.MIN_VALUE, -10, -1, 0, 1, 10, Integer.MAX_VALUE);

    /** The built-in parameters. */
    private static final List<Long> longs = List.of(Long.MIN_VALUE, -10L, -1L, 0L, 1L, 10L, Long.MAX_VALUE);

    /** The built-in parameters. */
    private static final List<Float> floats = List.of(Float.MIN_VALUE, -1F, -0.5F, 0F, 0.5F, 1.0F, Float.MAX_VALUE);

    /** The built-in parameters. */
    private static final List<Double> doubles = List.of(Double.MIN_VALUE, -1D, -0.5D, 0D, 0.5D, 1.0D, Double.MAX_VALUE);

    /** The built-in parameters. */
    private static final List<Byte> bytes = List.of(Byte.MIN_VALUE, (byte) -10, (byte) -1, (byte) 0, (byte) 1, (byte) 10, Byte.MAX_VALUE);

    /** The built-in parameters. */
    private static final List<Short> shorts = List
            .of(Short.MIN_VALUE, (short) -10, (short) -1, (short) 0, (short) 1, (short) 10, Short.MAX_VALUE);

    /** The built-in parameters. */
    private static final List<Character> chars = List.of(Character.MIN_VALUE, '0', ' ', 'A', Character.MAX_VALUE);

    /** The built-in parameters. */
    private static final List<Boolean> booleans = List.of(false, true);

    /** The built-in parameters. */
    private static final List<String> texts = List.of("", " ", "a", "A", "あ", "\\", "\t", "some value");

    /** The result cache. */
    private static final Map<Class, List> cache = new ConcurrentHashMap();

    /** The availability of the V8 runtime. */
    private static final boolean V8Available = JavetSupport.available();

    /**
     * Verify the decompiled JavaScript code.
     * 
     * @param code A target code to verify.
     * @throws Exception If the JavaScript runtime fails.
     */
    protected final void verify(TestCode code) throws Exception {
        Class target = code.getClass();

        // ========================================================
        // Execute the original test code by Java and store the results.
        // ========================================================
        JavaVerifier base = new JavaVerifier(target);
        List inputs = prepareInputs(base.method);
        List expecteds = cache.computeIfAbsent(target, key -> {
            List result = new ArrayList();
            for (Object input : inputs) {
                result.add(base.verifier.apply(input));
            }
            return result;
        });

        if (base.method.isAnnotationPresent(ShowJavaResultOnly.class)) {
            System.out.println("Show Java result : " + base.method);
            for (int i = 0; i < inputs.size(); i++) {
                System.out.println(inputs.get(i) + " : " + expecteds.get(i));
            }
            return;
        }

        // Skip the tests if the V8 runtime is not available on this platform.
        assumeTrue(V8Available, "The V8 runtime is not available on this platform.");

        // ========================================================
        // Decompile the bytecode into JavaScript.
        // ========================================================
        String javascript = Reincarnation.rebirth(target, new JavascriptCodingOption());
        String className = JavascriptCoder.computeSimpleName(target);
        String methodName = base.method.getName();
        Class[] parameterTypes = base.method.getParameterTypes();

        // ========================================================
        // Execute the decompiled JavaScript and compare the results.
        // ========================================================
        try (V8Runtime runtime = JavetSupport.create()) {
            for (int i = 0; i < inputs.size(); i++) {
                Object input = inputs.get(i);
                String arguments = parameterTypes.length == 0 ? "" : toJSLiteral(input, parameterTypes[0]);

                // The decompiled code is wrapped in a fresh function scope so the same class
                // (i.e. the anonymous inner class) is never declared twice in the shared runtime.
                String script = "(function () {\r\n" + javascript + "\r\n" + " const instance = new $c." + className + "();\r\n" + " return instance." + methodName + "(" + arguments + ");\r\n" + "})()";

                try (V8Value value = runtime.getExecutor(script).execute()) {
                    Object actual = toJava(value);
                    assertObject(expecteds.get(i), actual);
                }
            }
        }
    }

    /**
     * Convert the given V8 value into a plain Java object.
     * 
     * @param value A V8 value.
     * @return A converted Java object.
     * @throws Exception If the given V8 value cannot be converted.
     */
    private Object toJava(V8Value value) throws Exception {
        if (value instanceof V8ValueBoolean v) {
            return v.toPrimitive();
        } else if (value instanceof V8ValueLong v) {
            return v.toPrimitive();
        } else if (value instanceof V8ValueBigInteger v) {
            return v.toPrimitive().longValue();
        } else if (value instanceof V8ValueInteger v) {
            return v.toPrimitive();
        } else if (value instanceof V8ValueDouble v) {
            return v.toPrimitive();
        } else if (value instanceof V8ValueString v) {
            return v.toPrimitive();
        } else if (value instanceof V8ValueNull) {
            return null;
        } else if (value instanceof V8ValueArray array) {
            List<Object> list = new ArrayList();
            array.forEach(value2 -> list.add(toJava(value2)));
            return list;
        } else {
            return String.valueOf(value);
        }
    }

    /**
     * Assert that the invocation result of the decompiled JavaScript equals to the original Java
     * result.
     * 
     * @param expected A Java result.
     * @param actual A JavaScript result.
     */
    private void assertObject(Object expected, Object actual) {
        if (expected == null) {
            assert actual == null : "Expected null, but actual is " + actual;
        } else if (expected instanceof Boolean expectedValue) {
            assert expectedValue.equals(actual) : expected + " != " + actual;
        } else if (expected instanceof Character expectedValue) {
            assert String.valueOf(expectedValue).equals(String.valueOf(actual)) : expected + " != " + actual;
        } else if (expected instanceof String expectedValue) {
            assert expectedValue.equals(String.valueOf(actual)) : expected + " != " + actual;
        } else if (expected instanceof Number) {
            assert actual instanceof Number : expected + " != " + actual;
            Number expectedNumber = (Number) expected;
            Number actualNumber = (Number) actual;
            assert expectedNumber.doubleValue() == actualNumber.doubleValue() : expected + " != " + actual;
        } else if (expected.getClass().isArray()) {
            assert actual instanceof List : expected + " != " + actual;
            List result = (List) actual;
            assert Array.getLength(expected) == result.size() : expected + " != " + actual;
            for (int i = 0; i < result.size(); i++) {
                assertObject(Array.get(expected, i), result.get(i));
            }
        } else {
            assert expected.toString().equals(String.valueOf(actual)) : expected + " != " + actual;
        }
    }

    /**
     * Convert the given Java value into a JavaScript literal.
     * 
     * @param value A Java value.
     * @param type A parameter type.
     * @return A JavaScript literal.
     */
    private String toJSLiteral(Object value, Class type) {
        if (value == NoParam) {
            return "";
        } else if (type == boolean.class) {
            return value.toString();
        } else if (type == char.class) {
            return "\"" + value + "\"";
        } else if (type == long.class) {
            return ((Number) value).longValue() + "n";
        } else if (type == int.class || type == short.class || type == byte.class) {
            return String.valueOf(((Number) value).longValue());
        } else if (type == float.class || type == double.class) {
            return String.valueOf(((Number) value).doubleValue());
        } else if (type == String.class) {
            return "\"" + String.valueOf(value).replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
        } else if (value == null) {
            return "null";
        } else if (value instanceof String) {
            return "\"" + String.valueOf(value).replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
        } else if (value instanceof Number) {
            return String.valueOf(value);
        } else {
            return String.valueOf(value);
        }
    }

    /**
     * Prepare input values for test automatically.
     * 
     * @param method A target method.
     * @return A built-in values.
     */
    private List prepareInputs(Method method) {
        Class[] params = method.getParameterTypes();

        if (params.length == 0) {
            return Collections.singletonList(NoParam);
        }

        Class type = params[0];
        Annotation[] annotations = method.getParameterAnnotations()[0];

        if (annotations.length == 1 && annotations[0] instanceof Param) {
            return prepareInputs(type, (Param) annotations[0]);
        }

        if (type == boolean.class) {
            return booleans;
        } else if (type == char.class) {
            return chars;
        } else if (type == int.class) {
            return ints;
        } else if (type == long.class) {
            return longs;
        } else if (type == float.class) {
            return floats;
        } else if (type == double.class) {
            return doubles;
        } else if (type == short.class) {
            return shorts;
        } else if (type == byte.class) {
            return bytes;
        } else if (type == String.class) {
            return texts;
        } else if (type == Class.class) {
            return Arrays.asList(method.getDeclaringClass(), int.class);
        } else {
            return Arrays.asList(null, "String", 1);
        }
    }

    /**
     * Prepare user specified input values for test.
     * 
     * @param type A parameter type.
     * @param param User specified parameters.
     * @return A user specified values.
     */
    private List prepareInputs(Class type, Param param) {
        if (type == int.class) {
            int from = param.from();
            int to = param.to();

            if (from != to) {
                List inputs = new ArrayList();

                for (int i = from; i <= to; i++) {
                    inputs.add(i);
                }
                return inputs;
            }

            return asList(param.ints());
        } else if (type == long.class) {
            return asList(param.longs());
        } else if (type == float.class) {
            return asList(param.floats());
        } else if (type == double.class) {
            return asList(param.doubles());
        } else if (type == short.class) {
            return asList(param.shorts());
        } else if (type == byte.class) {
            return asList(param.bytes());
        } else if (type == char.class) {
            return asList(param.chars());
        } else if (type == String.class) {
            return asList(param.strings());
        } else {
            throw new Error();
        }
    }

    /**
     * Helper method to prepare user specified inputs.
     * 
     * @param array
     * @return
     */
    private List asList(Object array) {
        List inputs = new ArrayList();

        for (int i = 0; i < Array.getLength(array); i++) {
            inputs.add(Array.get(array, i));
        }
        return inputs;
    }
}