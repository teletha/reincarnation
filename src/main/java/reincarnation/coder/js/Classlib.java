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
    private static final Set<Class> runtimes = Set.of(java.util.ListIterator.class, java.lang.Enum.class);

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
            Map.entry(java.util.AbstractMap.class, reincarnation.classlib.java.util.AbstractMap.class), //
            Map.entry(java.util.HashMap.class, reincarnation.classlib.java.util.HashMap.class), //
            Map.entry(java.util.LinkedHashMap.class, reincarnation.classlib.java.util.LinkedHashMap.class), //
            Map.entry(java.util.TreeMap.class, reincarnation.classlib.java.util.TreeMap.class), //
            Map.entry(java.util.Hashtable.class, reincarnation.classlib.java.util.Hashtable.class), //
            Map.entry(java.util.Dictionary.class, reincarnation.classlib.java.util.Dictionary.class), //
            Map.entry(java.util.SortedMap.class, reincarnation.classlib.java.util.SortedMap.class), //
            Map.entry(java.util.NavigableMap.class, reincarnation.classlib.java.util.NavigableMap.class), //
            Map.entry(java.util.SequencedMap.class, reincarnation.classlib.java.util.SequencedMap.class), //
            Map.entry(java.util.concurrent.ConcurrentMap.class,
                    reincarnation.classlib.java.util.concurrent.ConcurrentMap.class), //
            Map.entry(java.util.Map.Entry.class, reincarnation.classlib.java.util.Map.Entry.class), //
            Map.entry(java.lang.Cloneable.class, reincarnation.classlib.java.lang.Cloneable.class), //
            Map.entry(java.io.Serializable.class, reincarnation.classlib.java.io.Serializable.class), //
            Map.entry(java.util.Queue.class, reincarnation.classlib.java.util.Queue.class), //
            Map.entry(java.util.Deque.class, reincarnation.classlib.java.util.Deque.class), //
            Map.entry(java.util.ArrayDeque.class, reincarnation.classlib.java.util.ArrayDeque.class), //
            Map.entry(java.util.PriorityQueue.class, reincarnation.classlib.java.util.PriorityQueue.class), //
            Map.entry(java.util.Optional.class, reincarnation.classlib.java.util.Optional.class), //
            Map.entry(java.util.stream.Stream.class, reincarnation.classlib.java.util.stream.Stream.class), //
            Map.entry(java.util.StringJoiner.class, reincarnation.classlib.java.util.StringJoiner.class), //
            Map.entry(java.util.regex.Pattern.class, reincarnation.classlib.java.util.regex.Pattern.class), //
            Map.entry(java.util.regex.Matcher.class, reincarnation.classlib.java.util.regex.Matcher.class), //
            Map.entry(java.util.stream.Collectors.class, reincarnation.classlib.java.util.stream.Collectors.class));

    /** The original JDK classes of the implementations. */
    private static final Map<Class, Class> origins = Map.ofEntries( //
            Map.entry(reincarnation.classlib.java.util.Map.class, java.util.Map.class), //
            Map.entry(reincarnation.classlib.java.util.AbstractMap.class, java.util.AbstractMap.class), //
            Map.entry(reincarnation.classlib.java.util.HashMap.class, java.util.HashMap.class), //
            Map.entry(reincarnation.classlib.java.util.LinkedHashMap.class, java.util.LinkedHashMap.class), //
            Map.entry(reincarnation.classlib.java.util.TreeMap.class, java.util.TreeMap.class), //
            Map.entry(reincarnation.classlib.java.util.Hashtable.class, java.util.Hashtable.class), //
            Map.entry(reincarnation.classlib.java.util.Dictionary.class, java.util.Dictionary.class), //
            Map.entry(reincarnation.classlib.java.util.SortedMap.class, java.util.SortedMap.class), //
            Map.entry(reincarnation.classlib.java.util.NavigableMap.class, java.util.NavigableMap.class), //
            Map.entry(reincarnation.classlib.java.util.SequencedMap.class, java.util.SequencedMap.class), //
            Map.entry(reincarnation.classlib.java.util.concurrent.ConcurrentMap.class,
                    java.util.concurrent.ConcurrentMap.class), //
            Map.entry(reincarnation.classlib.java.util.Map.Entry.class, java.util.Map.Entry.class), //
            Map.entry(reincarnation.classlib.java.util.AbstractMap.SimpleEntry.class,
                    java.util.AbstractMap.SimpleEntry.class), //
            Map.entry(reincarnation.classlib.java.lang.Cloneable.class, java.lang.Cloneable.class), //
            Map.entry(reincarnation.classlib.java.io.Serializable.class, java.io.Serializable.class));

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
     * Get the original JDK class of the implementation class.
     * 
     * @param type A target class.
     * @return An original class or null.
     */
    static Class origin(Class type) {
        return origins.get(type);
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

        Method resolved = find(target, method.getName(), parameterDescriptor(method), new java.util.HashSet());
        return resolved == null ? method : resolved;
    }

    /**
     * Find the method in the class and its super classes and interfaces.
     * 
     * @param type A target class.
     * @param name A method name.
     * @param parameters A parameter descriptor.
     * @param visited A visited classes.
     * @return A found method or null.
     */
    private static Method find(Class type, String name, String parameters, Set<Class> visited) {
        if (type == null || type == Object.class || !visited.add(type)) {
            return null;
        }

        for (Method candidate : type.getDeclaredMethods()) {
            if (candidate.getName().equals(name) && normalize(parameterDescriptor(candidate)).equals(parameters)) {
                return candidate;
            }
        }

        Method resolved = find(type.getSuperclass(), name, parameters, visited);
        if (resolved != null) {
            return resolved;
        }

        for (Class inf : type.getInterfaces()) {
            resolved = find(inf, name, parameters, visited);
            if (resolved != null) {
                return resolved;
            }
        }
        return null;
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
