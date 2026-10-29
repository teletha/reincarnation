/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.collection;

import org.junit.jupiter.api.Test;

import reincarnation.transpiler.BundleVerifier;

/**
 * Test the JDK type hierarchy which the classlib reproduces.
 */
class ClasslibReflectionTest extends BundleVerifier {

    @Test
    void arrayList() throws Exception {
        verifyReflection(ArrayListType.class, "run");
    }

    @Test
    void list() throws Exception {
        verifyReflection(ListType.class, "run");
    }

    @Test
    void stringBuilder() throws Exception {
        verifyReflection(StringBuilderType.class, "run");
    }

    @Test
    void stream() throws Exception {
        verifyReflection(StreamType.class, "run");
    }

    @Test
    void printStream() throws Exception {
        verifyReflection(PrintStreamType.class, "run");
    }

    @Test
    void pattern() throws Exception {
        verifyReflection(PatternType.class, "run");
    }

    @Test
    void matcher() throws Exception {
        verifyReflection(MatcherType.class, "run");
    }

    @Test
    void instantHierarchy() throws Exception {
        verifyReflection(InstantType.class, "run");
    }

    @Test
    void durationHierarchy() throws Exception {
        verifyReflection(DurationType.class, "run");
    }

    @Test
    void declaredMethod() throws Exception {
        verifyReflection(DeclaredMethod.class, "run");
    }

    @Test
    void declaredField() throws Exception {
        verifyReflection(DeclaredField.class, "run");
    }

    @Test
    void invokeMethod() throws Exception {
        verifyReflection(InvokeMethod.class, "run");
    }

    @Test
    void invokeConstructor() throws Exception {
        verifyReflection(InvokeConstructor.class, "run");
    }

    @Test
    void fieldAccess() throws Exception {
        verifyReflection(FieldAccess.class, "run");
    }

    /** Verify the hierarchy of {@link java.util.ArrayList}. */
    static class ArrayListType {

        static String run() {
            return TypeDescriber.describe(java.util.ArrayList.class);
        }
    }

    /** Verify {@link java.util.List} is an interface. */
    static class ListType {

        static String run() {
            return TypeDescriber.describe(java.util.List.class);
        }
    }

    /** Verify the hierarchy of {@link java.lang.StringBuilder}. */
    static class StringBuilderType {

        static String run() {
            return TypeDescriber.describe(java.lang.StringBuilder.class);
        }
    }

    /** Verify {@link java.util.stream.Stream} is an interface. */
    static class StreamType {

        static String run() {
            return TypeDescriber.describe(java.util.stream.Stream.class);
        }
    }

    /** Verify the hierarchy of {@link java.io.PrintStream}. */
    static class PrintStreamType {

        static String run() {
            return TypeDescriber.describe(java.io.PrintStream.class);
        }
    }

    /** Verify {@link java.util.regex.Pattern}. */
    static class PatternType {

        static String run() {
            return TypeDescriber.describe(java.util.regex.Pattern.class);
        }
    }

    /** Verify {@link java.util.regex.Matcher}. */
    static class MatcherType {

        static String run() {
            return TypeDescriber.describe(java.util.regex.Matcher.class);
        }
    }

    /** Verify the hierarchy of {@link java.time.Instant}. */
    static class InstantType {

        static String run() {
            return TypeDescriber.describe(java.time.Instant.class);
        }
    }

    /** Verify the hierarchy of {@link java.time.Duration}. */
    static class DurationType {

        static String run() {
            return TypeDescriber.describe(java.time.Duration.class);
        }
    }

    /** Verify the declared method of the classlib type. */
    static class DeclaredMethod {

        static String run() throws Exception {
            java.lang.reflect.Method method = java.util.ArrayList.class.getDeclaredMethod("add", java.lang.Object.class);
            return method.getName() + "|" + method.getParameterCount() + "|" + method.getDeclaringClass().getName();
        }
    }

    /** Verify the declared field of the classlib type. */
    static class DeclaredField {

        static String run() throws Exception {
            java.lang.reflect.Field field = java.lang.System.class.getDeclaredField("out");
            return field.getName() + "|" + field.getType().getName();
        }
    }

    /** Verify the method invocation of the classlib type. */
    static class InvokeMethod {

        static int run() throws Exception {
            java.util.List<Object> list = new java.util.ArrayList<>();
            java.lang.reflect.Method add = java.util.ArrayList.class.getDeclaredMethod("add", Object.class);
            add.invoke(list, "a");
            add.invoke(list, "b");

            java.util.Map<String, Integer> map = new java.util.LinkedHashMap<>();
            java.lang.reflect.Method put = java.util.HashMap.class.getDeclaredMethod("put", Object.class, Object.class);
            put.invoke(map, "a", 1);
            return list.size() * 10 + map.get("a");
        }
    }

    /** Verify the constructor invocation of the classlib type. */
    static class InvokeConstructor {

        static int run() throws Exception {
            java.util.List<Object> list = (java.util.List<Object>) java.util.ArrayList.class.getDeclaredConstructor(int.class)
                    .newInstance(10);
            list.add("a");
            list.add("b");

            java.lang.StringBuilder builder = (java.lang.StringBuilder) java.lang.StringBuilder.class
                    .getDeclaredConstructor().newInstance();
            java.lang.reflect.Method append = java.lang.StringBuilder.class.getDeclaredMethod("append", String.class);
            append.invoke(builder, "abc");
            return list.size() * 10 + builder.toString().length();
        }
    }

    /** Verify the field access of the classlib type. */
    static class FieldAccess {

        static String run() throws Exception {
            java.lang.reflect.Field field = java.lang.System.class.getDeclaredField("out");
            java.lang.Object value = field.get(null);
            return value == null ? "null" : "present";
        }
    }
}
