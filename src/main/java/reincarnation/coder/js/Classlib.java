/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder.js;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * The mapping from the Java standard library to the Java implementation which is transpiled into
 * JavaScript.
 * <p>
 * The primitive operations are provided by {@link JavascriptRuntime}, but the API level
 * classes are implemented in Java and included in the dependency closure so that the transpiler
 * itself verifies their translation.
 * </p>
 */
final class Classlib {

    /** The classes which are provided as the hand-written JavaScript runtime. */
    private static final Set<Class> runtimes = Set.of(java.util.Queue.class, java.util.Deque.class,
            java.util.ArrayDeque.class, java.util.PriorityQueue.class, java.util.ListIterator.class,
            java.lang.Enum.class);

    /** The classes which are provided as the reflection runtime. */
    private static final Set<Class> reflections = Set.of(java.lang.Class.class, java.lang.reflect.Method.class,
            java.lang.reflect.Field.class, java.lang.reflect.Constructor.class, java.lang.reflect.Modifier.class,
            java.lang.reflect.Array.class, java.lang.reflect.Type.class, java.lang.reflect.TypeVariable.class,
            java.lang.reflect.ParameterizedType.class, java.lang.reflect.GenericArrayType.class,
            java.lang.reflect.WildcardType.class);

    /** The replacement table. */
    private static final Map<Class, Class> replacements = Map.ofEntries( //
            Map.entry(java.lang.StringBuilder.class, reincarnation.classlib.java.lang.StringBuilder.class), //
            Map.entry(java.lang.StringBuffer.class, reincarnation.classlib.java.lang.StringBuffer.class), //
            Map.entry(java.lang.System.class, reincarnation.classlib.java.lang.System.class), //
            Map.entry(java.io.PrintStream.class, reincarnation.classlib.java.io.PrintStream.class), //
            Map.entry(java.util.Objects.class, reincarnation.classlib.java.util.Objects.class), //
            Map.entry(java.util.Arrays.class, reincarnation.classlib.java.util.Arrays.class), //
            Map.entry(java.util.Collections.class, reincarnation.classlib.java.util.Collections.class), //
            Map.entry(java.util.List.class, reincarnation.classlib.java.util.List.class), //
            Map.entry(java.util.ArrayList.class, reincarnation.classlib.java.util.ArrayList.class), //
            Map.entry(java.util.LinkedList.class, reincarnation.classlib.java.util.LinkedList.class), //
            Map.entry(java.util.Vector.class, reincarnation.classlib.java.util.Vector.class), //
            Map.entry(java.util.Stack.class, reincarnation.classlib.java.util.Stack.class), //
            Map.entry(java.util.AbstractList.class, reincarnation.classlib.java.util.List.class), //
            Map.entry(java.util.Collection.class, reincarnation.classlib.java.util.List.class), //
            Map.entry(java.lang.Iterable.class, reincarnation.classlib.java.util.List.class), //
            Map.entry(java.util.Iterator.class, reincarnation.classlib.java.util.Iterator.class), //
            Map.entry(java.util.Set.class, reincarnation.classlib.java.util.Set.class), //
            Map.entry(java.util.HashSet.class, reincarnation.classlib.java.util.HashSet.class), //
            Map.entry(java.util.LinkedHashSet.class, reincarnation.classlib.java.util.LinkedHashSet.class), //
            Map.entry(java.util.TreeSet.class, reincarnation.classlib.java.util.TreeSet.class), //
            Map.entry(java.util.SortedSet.class, reincarnation.classlib.java.util.Set.class), //
            Map.entry(java.util.NavigableSet.class, reincarnation.classlib.java.util.Set.class), //
            Map.entry(java.util.Map.class, reincarnation.classlib.java.util.Map.class), //
            Map.entry(java.util.HashMap.class, reincarnation.classlib.java.util.HashMap.class), //
            Map.entry(java.util.LinkedHashMap.class, reincarnation.classlib.java.util.LinkedHashMap.class), //
            Map.entry(java.util.TreeMap.class, reincarnation.classlib.java.util.TreeMap.class), //
            Map.entry(java.util.Hashtable.class, reincarnation.classlib.java.util.Hashtable.class), //
            Map.entry(java.util.SortedMap.class, reincarnation.classlib.java.util.Map.class), //
            Map.entry(java.util.NavigableMap.class, reincarnation.classlib.java.util.Map.class), //
            Map.entry(java.util.concurrent.ConcurrentMap.class, reincarnation.classlib.java.util.Map.class), //
            Map.entry(java.util.Map.Entry.class, reincarnation.classlib.java.util.Entry.class), //
            Map.entry(java.util.Optional.class, reincarnation.classlib.java.util.Optional.class), //
            Map.entry(java.util.stream.Stream.class, reincarnation.classlib.java.util.stream.Stream.class), //
            Map.entry(java.util.StringJoiner.class, reincarnation.classlib.java.util.StringJoiner.class), //
            Map.entry(java.util.regex.Pattern.class, reincarnation.classlib.java.util.regex.Pattern.class), //
            Map.entry(java.util.regex.Matcher.class, reincarnation.classlib.java.util.regex.Matcher.class), //
            Map.entry(java.util.stream.Collectors.class, reincarnation.classlib.java.util.stream.Collectors.class));

    /**
     * Hide constructor.
     */
    private Classlib() {
    }

    /**
     * Replace the specified class with the Java implementation which is transpiled into JavaScript.
     * 
     * @param type A target class.
     * @return A replacement class or the given class.
     */
    static Class replace(Class type) {
        return replacements.getOrDefault(type, type);
    }

    /**
     * Resolve the Java API method to the implementation method which is transpiled into
     * JavaScript. The return type is ignored because a covariant override may change it.
     * 
     * @param method A Java API method.
     * @return An implementation method or the given method.
     */
    static Method resolve(Method method) {
        Class target = replace(method.getDeclaringClass());
        if (target == method.getDeclaringClass()) {
            return method;
        }

        String name = method.getName();
        String parameters = parameterDescriptor(method);
        for (Class type = target; type != null && type != Object.class; type = type.getSuperclass()) {
            for (Method candidate : type.getDeclaredMethods()) {
                if (candidate.getName().equals(name) && normalize(parameterDescriptor(candidate)).equals(parameters)) {
                    return candidate;
                }
            }
        }
        return method;
    }

    /**
     * Normalize the classlib types in the descriptor to the Java API types.
     * 
     * @param descriptor A parameter descriptor.
     * @return A normalized descriptor.
     */
    private static String normalize(String descriptor) {
        for (Map.Entry<Class, Class> entry : replacements.entrySet()) {
            String from = "L" + entry.getValue().getName().replace('.', '/') + ";";
            String to = "L" + entry.getKey().getName().replace('.', '/') + ";";
            descriptor = descriptor.replace(from, to);
        }
        return descriptor;
    }

    /**
     * Compute the parameter descriptor of the method.
     * 
     * @param method A target method.
     * @return A parameter descriptor.
     */
    private static String parameterDescriptor(Method method) {
        String descriptor = org.objectweb.asm.Type.getMethodDescriptor(method);
        return descriptor.substring(0, descriptor.indexOf(')') + 1);
    }

    /**
     * Resolve the Java API field to the implementation field which is transpiled into JavaScript.
     * 
     * @param field A Java API field.
     * @return An implementation field or the given field.
     */
    static java.lang.reflect.Field resolve(java.lang.reflect.Field field) {
        Class target = replace(field.getDeclaringClass());
        if (target == field.getDeclaringClass()) {
            return field;
        }
        try {
            return target.getDeclaredField(field.getName());
        } catch (NoSuchFieldException e) {
            return field;
        }
    }

    /**
     * Check whether the specified class has the Java implementation.
     * 
     * @param type A target class.
     * @return A result.
     */
    static boolean isReplaced(Class type) {
        return replacements.containsKey(type);
    }

    /**
     * Check whether the specified class is a JavaScript native type.
     * 
     * @param type A target class.
     * @return A result.
     */
    static boolean isNative(Class type) {
        return type != null && type.isAnnotationPresent(js.lang.Native.class);
    }

    /**
     * Check whether the specified class is provided by the hand-written JavaScript runtime.
     * 
     * @param type A target class.
     * @return A result.
     */
    static boolean isRuntime(Class type) {
        return runtimes.contains(type);
    }

    /**
     * Check whether the specified class is provided by the reflection runtime.
     * 
     * @param type A target class.
     * @return A result.
     */
    static boolean isReflection(Class type) {
        return reflections.contains(type);
    }
}
