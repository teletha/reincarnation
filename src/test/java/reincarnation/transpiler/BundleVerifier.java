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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.caoccao.javet.interop.V8Host;
import com.caoccao.javet.interop.V8Runtime;
import com.caoccao.javet.values.V8Value;
import com.caoccao.javet.values.primitive.V8ValueBigInteger;
import com.caoccao.javet.values.primitive.V8ValueBoolean;
import com.caoccao.javet.values.primitive.V8ValueDouble;
import com.caoccao.javet.values.primitive.V8ValueInteger;
import com.caoccao.javet.values.primitive.V8ValueLong;
import com.caoccao.javet.values.primitive.V8ValueNull;
import com.caoccao.javet.values.primitive.V8ValueString;

import reincarnation.coder.js.JavascriptBundle;
import reincarnation.coder.js.JavascriptCoder;
import reincarnation.coder.js.JavascriptCodingOption;

/**
 * Verifies the bundled JavaScript program which contains the whole dependency closure.
 * <p>
 * The entry class must declare a static method.
 * </p>
 */
public class BundleVerifier {

    /** The availability of the V8 runtime. */
    private static final boolean V8Available = loadV8();

    /**
     * Check whether the V8 runtime is available.
     * 
     * @return A result.
     */
    private static boolean loadV8() {
        try {
            V8Host.getNodeInstance();
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * Verify the bundled JavaScript program.
     * 
     * @param target An entry class.
     * @param method A static method name.
     * @param args Arguments.
     * @throws Exception If the verification fails.
     */
    protected final void verify(Class<?> target, String method, Object... args) throws Exception {
        verify(target, method, new JavascriptCodingOption(), args);
    }

    /**
     * Verify the bundled JavaScript program with the reflection support.
     * 
     * @param target An entry class.
     * @param method A static method name.
     * @param args Arguments.
     * @throws Exception If the verification fails.
     */
    protected final void verifyReflection(Class<?> target, String method, Object... args) throws Exception {
        JavascriptCodingOption options = new JavascriptCodingOption();
        options.reflection = true;
        options.scanNecessary = true;
        verify(target, method, options, args);
    }

    /**
     * Verify the bundled JavaScript program.
     * 
     * @param target An entry class.
     * @param method A static method name.
     * @param options A coding options.
     * @param args Arguments.
     * @throws Exception If the verification fails.
     */
    private void verify(Class<?> target, String method, JavascriptCodingOption options, Object... args) throws Exception {
        Method entry = find(target, method, args.length);
        entry.setAccessible(true);

        Object expected = entry.invoke(null, args);

        assumeTrue(V8Available, "The V8 runtime is not available on this platform.");

        String javascript = JavascriptBundle.build(target, options);
        String className = JavascriptCoder.computeSimpleName(target);

        StringBuilder literal = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (i != 0) {
                literal.append(", ");
            }
            literal.append(toJSLiteral(args[i], entry.getParameterTypes()[i]));
        }

        String script = "(function () {\r\n" + javascript + "\r\n return $c." + className + "." + method + "(" + literal + ");\r\n})()";

        try (V8Runtime runtime = V8Host.getNodeInstance().createV8Runtime()) {
            try (V8Value value = runtime.getExecutor(script).execute()) {
                assertObject(expected, toJava(value));
            }
        }
    }

    /**
     * Find the static entry method.
     * 
     * @param target A target class.
     * @param name A method name.
     * @param arity The number of parameters.
     * @return A method.
     * @throws NoSuchMethodException If not found.
     */
    private Method find(Class<?> target, String name, int arity) throws NoSuchMethodException {
        for (Method method : target.getDeclaredMethods()) {
            if (method.getName().equals(name) && method.getParameterCount() == arity && Modifier.isStatic(method.getModifiers())) {
                return method;
            }
        }
        throw new NoSuchMethodException(target + "#" + name);
    }

    /**
     * Convert the given Java value into a JavaScript literal.
     * 
     * @param value A Java value.
     * @param type A parameter type.
     * @return A JavaScript literal.
     */
    private String toJSLiteral(Object value, Class type) {
        if (value == null) {
            return "null";
        } else if (type == boolean.class || type == int.class || type == short.class || type == byte.class) {
            return String.valueOf(value);
        } else if (type == char.class) {
            return "\"" + value + "\"";
        } else if (type == long.class) {
            return ((Number) value).longValue() + "n";
        } else if (type == float.class || type == double.class) {
            return String.valueOf(value);
        } else {
            return "\"" + String.valueOf(value).replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
        }
    }

    /**
     * Convert the given V8 value into a plain Java object.
     * 
     * @param value A V8 value.
     * @return A converted value.
     * @throws Exception If the conversion fails.
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
        } else {
            return String.valueOf(value);
        }
    }

    /**
     * Assert that the JavaScript result equals to the Java result.
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
        } else if (expected instanceof Number expectedValue) {
            assert actual instanceof Number : expected + " != " + actual;
            assert expectedValue.doubleValue() == ((Number) actual).doubleValue() : expected + " != " + actual;
        } else {
            assert expected.toString().equals(String.valueOf(actual)) : expected + " != " + actual;
        }
    }
}
