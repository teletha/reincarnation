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
import java.util.concurrent.ConcurrentHashMap;

/**
 * The mapping from the Java standard library to the Java implementation which is transpiled into
 * JavaScript.
 * <p>
 * The primitive operations are provided by {@link JavascriptRuntime}, but the API level
 * classes are implemented in Java and included in the dependency closure so that the transpiler
 * itself verifies their translation.
 * </p>
 */
public final class Classlib {

    /** The classes which are provided as the hand-written JavaScript runtime. */
    private static final Set<Class> runtimes = Set.of(java.lang.Enum.class);

    /** The classes which are provided as the reflection runtime. */
    private static final Set<Class> reflections = Set.of(java.lang.Class.class, java.lang.reflect.Method.class,
            java.lang.reflect.Field.class, java.lang.reflect.Constructor.class, java.lang.reflect.Modifier.class,
            java.lang.reflect.Array.class, java.lang.reflect.Type.class, java.lang.reflect.TypeVariable.class,
            java.lang.reflect.ParameterizedType.class, java.lang.reflect.GenericArrayType.class,
            java.lang.reflect.WildcardType.class);

    /** The replacement table. */
    private static final Map<Class, Class> replacements = new ConcurrentHashMap();

    /** The original JDK classes of the implementations. */
    private static final Map<Class, Class> origins = new ConcurrentHashMap();

    static {
        register(java.lang.StringBuilder.class, reincarnation.classlib.java.lang.StringBuilder.class);
        register(java.lang.StringBuffer.class, reincarnation.classlib.java.lang.StringBuffer.class);
        register(java.lang.System.class, reincarnation.classlib.java.lang.System.class);
        register(java.io.PrintStream.class, reincarnation.classlib.java.io.PrintStream.class);
        register(java.io.OutputStream.class, reincarnation.classlib.java.io.OutputStream.class);
        register(java.io.FilterOutputStream.class, reincarnation.classlib.java.io.FilterOutputStream.class);
        register(java.io.Closeable.class, reincarnation.classlib.java.io.Closeable.class);
        register(java.io.Flushable.class, reincarnation.classlib.java.io.Flushable.class);
        register(java.util.Objects.class, reincarnation.classlib.java.util.Objects.class);
        register(java.util.Date.class, reincarnation.classlib.java.util.Date.class);
        register(java.util.Random.class, reincarnation.classlib.java.util.Random.class);
        register(java.util.BitSet.class, reincarnation.classlib.java.util.BitSet.class);
        register(java.util.Properties.class, reincarnation.classlib.java.util.Properties.class);
        register(java.util.EnumMap.class, reincarnation.classlib.java.util.EnumMap.class);
        register(java.util.EnumSet.class, reincarnation.classlib.java.util.EnumSet.class);
        register(java.util.ResourceBundle.class, reincarnation.classlib.java.util.ResourceBundle.class);
        register(java.util.Enumeration.class, reincarnation.classlib.java.util.Enumeration.class);
        register(java.util.Locale.class, reincarnation.classlib.java.util.Locale.class);
        register(java.util.Locale.Category.class, reincarnation.classlib.java.util.Locale.Category.class);
        register(java.util.Currency.class, reincarnation.classlib.java.util.Currency.class);
        register(java.text.Collator.class, reincarnation.classlib.java.text.Collator.class);
        register(java.text.Format.class, reincarnation.classlib.java.text.Format.class);
        register(java.text.NumberFormat.class, reincarnation.classlib.java.text.NumberFormat.class);
        register(java.text.DateFormat.class, reincarnation.classlib.java.text.DateFormat.class);
        register(java.util.Arrays.class, reincarnation.classlib.java.util.Arrays.class);
        register(java.util.Collections.class, reincarnation.classlib.java.util.Collections.class);
        register(java.lang.Iterable.class, reincarnation.classlib.java.lang.Iterable.class);
        register(java.lang.Appendable.class, reincarnation.classlib.java.lang.Appendable.class);
        register(java.lang.CharSequence.class, reincarnation.classlib.java.lang.CharSequence.class);
        register(java.lang.Comparable.class, reincarnation.classlib.java.lang.Comparable.class);
        register(java.lang.AutoCloseable.class, reincarnation.classlib.java.lang.AutoCloseable.class);
        register(java.lang.invoke.MethodHandle.class, reincarnation.classlib.java.lang.invoke.MethodHandle.class);
        register(java.lang.invoke.MethodHandles.class, reincarnation.classlib.java.lang.invoke.MethodHandles.class);
        register(java.lang.invoke.MethodHandles.Lookup.class, reincarnation.classlib.java.lang.invoke.MethodHandles.Lookup.class);
        register(java.lang.invoke.MethodType.class, reincarnation.classlib.java.lang.invoke.MethodType.class);
        register(java.util.Collection.class, reincarnation.classlib.java.util.Collection.class);
        register(java.util.AbstractCollection.class, reincarnation.classlib.java.util.AbstractCollection.class);
        register(java.util.SequencedCollection.class, reincarnation.classlib.java.util.SequencedCollection.class);
        register(java.util.List.class, reincarnation.classlib.java.util.List.class);
        register(java.util.AbstractList.class, reincarnation.classlib.java.util.AbstractList.class);
        register(java.util.AbstractSequentialList.class, reincarnation.classlib.java.util.AbstractSequentialList.class);
        register(java.util.ArrayList.class, reincarnation.classlib.java.util.ArrayList.class);
        register(java.util.LinkedList.class, reincarnation.classlib.java.util.LinkedList.class);
        register(java.util.Vector.class, reincarnation.classlib.java.util.Vector.class);
        register(java.util.Stack.class, reincarnation.classlib.java.util.Stack.class);
        register(java.util.RandomAccess.class, reincarnation.classlib.java.util.RandomAccess.class);
        register(java.util.Iterator.class, reincarnation.classlib.java.util.Iterator.class);
        register(java.util.ListIterator.class, reincarnation.classlib.java.util.ListIterator.class);
        register(java.util.Set.class, reincarnation.classlib.java.util.Set.class);
        register(java.util.AbstractSet.class, reincarnation.classlib.java.util.AbstractSet.class);
        register(java.util.HashSet.class, reincarnation.classlib.java.util.HashSet.class);
        register(java.util.LinkedHashSet.class, reincarnation.classlib.java.util.LinkedHashSet.class);
        register(java.util.TreeSet.class, reincarnation.classlib.java.util.TreeSet.class);
        register(java.util.SortedSet.class, reincarnation.classlib.java.util.SortedSet.class);
        register(java.util.NavigableSet.class, reincarnation.classlib.java.util.NavigableSet.class);
        register(java.util.SequencedSet.class, reincarnation.classlib.java.util.SequencedSet.class);
        register(java.util.Map.class, reincarnation.classlib.java.util.Map.class);
        register(java.util.AbstractMap.class, reincarnation.classlib.java.util.AbstractMap.class);
        register(java.util.HashMap.class, reincarnation.classlib.java.util.HashMap.class);
        register(java.util.LinkedHashMap.class, reincarnation.classlib.java.util.LinkedHashMap.class);
        register(java.util.TreeMap.class, reincarnation.classlib.java.util.TreeMap.class);
        register(java.util.Hashtable.class, reincarnation.classlib.java.util.Hashtable.class);
        register(java.util.Dictionary.class, reincarnation.classlib.java.util.Dictionary.class);
        register(java.util.SortedMap.class, reincarnation.classlib.java.util.SortedMap.class);
        register(java.util.NavigableMap.class, reincarnation.classlib.java.util.NavigableMap.class);
        register(java.util.SequencedMap.class, reincarnation.classlib.java.util.SequencedMap.class);
        register(java.util.concurrent.ConcurrentMap.class, reincarnation.classlib.java.util.concurrent.ConcurrentMap.class);
        register(java.util.concurrent.ConcurrentHashMap.class, reincarnation.classlib.java.util.concurrent.ConcurrentHashMap.class);
        register(java.util.concurrent.ConcurrentSkipListMap.class, reincarnation.classlib.java.util.concurrent.ConcurrentSkipListMap.class);
        register(java.util.concurrent.ConcurrentSkipListSet.class, reincarnation.classlib.java.util.concurrent.ConcurrentSkipListSet.class);
        register(java.util.concurrent.ConcurrentNavigableMap.class, reincarnation.classlib.java.util.concurrent.ConcurrentNavigableMap.class);
        register(java.util.concurrent.CopyOnWriteArrayList.class, reincarnation.classlib.java.util.concurrent.CopyOnWriteArrayList.class);
        register(java.util.concurrent.CopyOnWriteArraySet.class, reincarnation.classlib.java.util.concurrent.CopyOnWriteArraySet.class);
        register(java.util.concurrent.ConcurrentLinkedQueue.class, reincarnation.classlib.java.util.concurrent.ConcurrentLinkedQueue.class);
        register(java.util.concurrent.ConcurrentLinkedDeque.class, reincarnation.classlib.java.util.concurrent.ConcurrentLinkedDeque.class);
        register(java.util.concurrent.BlockingQueue.class, reincarnation.classlib.java.util.concurrent.BlockingQueue.class);
        register(java.util.concurrent.BlockingDeque.class, reincarnation.classlib.java.util.concurrent.BlockingDeque.class);
        register(java.util.concurrent.TransferQueue.class, reincarnation.classlib.java.util.concurrent.TransferQueue.class);
        register(java.util.concurrent.ArrayBlockingQueue.class, reincarnation.classlib.java.util.concurrent.ArrayBlockingQueue.class);
        register(java.util.concurrent.LinkedBlockingQueue.class, reincarnation.classlib.java.util.concurrent.LinkedBlockingQueue.class);
        register(java.util.concurrent.LinkedBlockingDeque.class, reincarnation.classlib.java.util.concurrent.LinkedBlockingDeque.class);
        register(java.util.concurrent.PriorityBlockingQueue.class, reincarnation.classlib.java.util.concurrent.PriorityBlockingQueue.class);
        register(java.util.concurrent.SynchronousQueue.class, reincarnation.classlib.java.util.concurrent.SynchronousQueue.class);
        register(java.util.concurrent.DelayQueue.class, reincarnation.classlib.java.util.concurrent.DelayQueue.class);
        register(java.util.concurrent.LinkedTransferQueue.class, reincarnation.classlib.java.util.concurrent.LinkedTransferQueue.class);
        register(java.util.concurrent.atomic.AtomicBoolean.class, reincarnation.classlib.java.util.concurrent.atomic.AtomicBoolean.class);
        register(java.util.concurrent.atomic.AtomicInteger.class, reincarnation.classlib.java.util.concurrent.atomic.AtomicInteger.class);
        register(java.util.concurrent.atomic.AtomicLong.class, reincarnation.classlib.java.util.concurrent.atomic.AtomicLong.class);
        register(java.util.concurrent.atomic.AtomicReference.class, reincarnation.classlib.java.util.concurrent.atomic.AtomicReference.class);
        register(java.util.concurrent.atomic.AtomicReferenceArray.class, reincarnation.classlib.java.util.concurrent.atomic.AtomicReferenceArray.class);
        register(java.util.Map.Entry.class, reincarnation.classlib.java.util.Map.Entry.class);
        register(java.lang.reflect.Proxy.class, reincarnation.classlib.java.lang.reflect.Proxy.class);
        register(java.lang.reflect.InvocationHandler.class, reincarnation.classlib.java.lang.reflect.InvocationHandler.class);
        register(java.lang.Cloneable.class, reincarnation.classlib.java.lang.Cloneable.class);
        register(java.io.Serializable.class, reincarnation.classlib.java.io.Serializable.class);
        register(java.util.Queue.class, reincarnation.classlib.java.util.Queue.class);
        register(java.util.Deque.class, reincarnation.classlib.java.util.Deque.class);
        register(java.util.AbstractQueue.class, reincarnation.classlib.java.util.AbstractQueue.class);
        register(java.util.ArrayDeque.class, reincarnation.classlib.java.util.ArrayDeque.class);
        register(java.util.PriorityQueue.class, reincarnation.classlib.java.util.PriorityQueue.class);
        register(java.util.Optional.class, reincarnation.classlib.java.util.Optional.class);
        register(java.util.stream.Stream.class, reincarnation.classlib.java.util.stream.Stream.class);
        register(java.util.stream.BaseStream.class, reincarnation.classlib.java.util.stream.BaseStream.class);
        register(java.util.StringJoiner.class, reincarnation.classlib.java.util.StringJoiner.class);
        register(java.util.regex.Pattern.class, reincarnation.classlib.java.util.regex.Pattern.class);
        register(java.util.regex.Matcher.class, reincarnation.classlib.java.util.regex.Matcher.class);
        register(java.util.regex.MatchResult.class, reincarnation.classlib.java.util.regex.MatchResult.class);
        register(java.time.Instant.class, reincarnation.classlib.java.time.Instant.class);
        register(java.time.LocalDate.class, reincarnation.classlib.java.time.LocalDate.class);
        register(java.time.LocalTime.class, reincarnation.classlib.java.time.LocalTime.class);
        register(java.time.LocalDateTime.class, reincarnation.classlib.java.time.LocalDateTime.class);
        register(java.time.ZonedDateTime.class, reincarnation.classlib.java.time.ZonedDateTime.class);
        register(java.time.Duration.class, reincarnation.classlib.java.time.Duration.class);
        register(java.time.Period.class, reincarnation.classlib.java.time.Period.class);
        register(java.time.ZoneId.class, reincarnation.classlib.java.time.ZoneId.class);
        register(java.time.ZoneOffset.class, reincarnation.classlib.java.time.ZoneOffset.class);
        register(java.time.Month.class, reincarnation.classlib.java.time.Month.class);
        register(java.time.DayOfWeek.class, reincarnation.classlib.java.time.DayOfWeek.class);
        register(java.time.temporal.TemporalAccessor.class, reincarnation.classlib.java.time.temporal.TemporalAccessor.class);
        register(java.time.temporal.Temporal.class, reincarnation.classlib.java.time.temporal.Temporal.class);
        register(java.time.temporal.TemporalAdjuster.class, reincarnation.classlib.java.time.temporal.TemporalAdjuster.class);
        register(java.time.temporal.TemporalAmount.class, reincarnation.classlib.java.time.temporal.TemporalAmount.class);
        register(java.time.temporal.TemporalUnit.class, reincarnation.classlib.java.time.temporal.TemporalUnit.class);
        register(java.time.temporal.TemporalField.class, reincarnation.classlib.java.time.temporal.TemporalField.class);
        register(java.time.temporal.ChronoUnit.class, reincarnation.classlib.java.time.temporal.ChronoUnit.class);
        register(java.time.temporal.ChronoField.class, reincarnation.classlib.java.time.temporal.ChronoField.class);
        register(java.util.stream.Collectors.class, reincarnation.classlib.java.util.stream.Collectors.class);
        origins.put(reincarnation.classlib.java.util.AbstractMap.SimpleEntry.class, java.util.AbstractMap.SimpleEntry.class);
    }


    /** The JDK names of the implementations which are not accessible from the source. */
    private static final Map<Class, String> externalNames = Map
            .of(reincarnation.classlib.java.lang.AbstractStringBuilder.class, "java.lang.AbstractStringBuilder");

    /**
     * Hide constructor.
     */
    private Classlib() {
    }

    /**
     * Register the Java API class and its implementation.
     * 
     * @param target A Java API class.
     * @param implementation An implementation class which is transpiled into JavaScript.
     */
    public static void register(Class target, Class implementation) {
        if (target == null || implementation == null) {
            throw new NullPointerException();
        }
        replacements.put(target, implementation);
        origins.put(implementation, target);
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
     * Get the JDK name of the implementation class which is not accessible from the source.
     * 
     * @param type A target class.
     * @return A JDK name or null.
     */
    static String name(Class type) {
        return externalNames.get(type);
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

        java.lang.reflect.Field resolved = findField(target, field.getName(), new java.util.HashSet());
        return resolved == null ? field : resolved;
    }

    /**
     * Find the field in the class and its super classes and interfaces.
     * 
     * @param type A target class.
     * @param name A field name.
     * @param visited A visited classes.
     * @return A found field or null.
     */
    private static java.lang.reflect.Field findField(Class type, String name, Set<Class> visited) {
        if (type == null || type == Object.class || !visited.add(type)) {
            return null;
        }

        try {
            return type.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            // search the super types
        }

        java.lang.reflect.Field resolved = findField(type.getSuperclass(), name, visited);
        if (resolved != null) {
            return resolved;
        }

        for (Class inf : type.getInterfaces()) {
            resolved = findField(inf, name, visited);
            if (resolved != null) {
                return resolved;
            }
        }
        return null;
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
