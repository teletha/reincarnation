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
    void declaredMethod() throws Exception {
        verifyReflection(DeclaredMethod.class, "run");
    }

    @Test
    void declaredField() throws Exception {
        verifyReflection(DeclaredField.class, "run");
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
}
