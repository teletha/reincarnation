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
    private static final Set<Class> runtimes = Set.of(java.util.List.class, java.util.ArrayList.class,
            java.util.LinkedList.class, java.util.Vector.class, java.util.Stack.class, java.util.AbstractList.class,
            java.util.Set.class, java.util.HashSet.class, java.util.LinkedHashSet.class, java.util.TreeSet.class,
            java.util.SortedSet.class, java.util.NavigableSet.class, java.util.Queue.class, java.util.Deque.class,
            java.util.ArrayDeque.class, java.util.PriorityQueue.class, java.util.Map.class, java.util.HashMap.class,
            java.util.LinkedHashMap.class, java.util.TreeMap.class, java.util.Hashtable.class,
            java.util.SortedMap.class, java.util.NavigableMap.class, java.util.concurrent.ConcurrentMap.class,
            java.util.Map.Entry.class, java.util.Collection.class, java.util.Iterator.class,
            java.util.ListIterator.class, java.lang.Iterable.class, java.util.Optional.class,
            java.util.stream.Stream.class, java.lang.Enum.class);

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
