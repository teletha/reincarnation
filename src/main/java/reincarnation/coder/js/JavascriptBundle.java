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

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import reincarnation.Reincarnation;

/**
 * Builds a single JavaScript program which contains the whole dependency closure of the specified
 * class.
 * <p>
 * The classes are written in post-order (super class, interfaces, local classes, anonymous classes,
 * the class itself and its dependencies) so that every class is registered before it is used.
 * </p>
 */
public final class JavascriptBundle {

    /**
     * Hide constructor.
     */
    private JavascriptBundle() {
    }

    /**
     * Build the bundle of the specified class.
     * 
     * @param entry An entry class.
     * @return A JavaScript program.
     */
    public static String build(Class entry) {
        return build(entry, new JavascriptCodingOption());
    }

    /**
     * Build the bundle of the specified class.
     * 
     * @param entry An entry class.
     * @param options A coding options.
     * @return A JavaScript program.
     */
    public static synchronized String build(Class entry, JavascriptCodingOption options) {
        Set<Class> closure = new LinkedHashSet();
        Set<Class> visited = new HashSet();

        if (options.scanNecessary) {
            for (Class type : NecessaryScanner.scan(entry)) {
                collect(type, closure, visited);
            }
        }
        for (Class type : options.necessary) {
            collect(type, closure, visited);
        }
        collect(entry, closure, visited);

        options.bundle = true;
        options.embedRuntime = false;

        NameAllocator names = new NameAllocator();
        Set<Class> defined = new HashSet(closure);

        // The entry class keeps its preferred name even if a dependency declares the same simple
        // name, so the caller can refer to the entry by the simple name.
        names.seed(entry);
        names.seedMembers(entry);

        for (Class type : closure) {
            if (!Classlib.isNative(type)) {
                names.seedMembers(type);
            }
        }

        StringBuilder classes = new StringBuilder();
        boolean runtime = false;

        for (Class type : closure) {
            JavascriptCoder coder = new JavascriptCoder();
            coder.config(options);
            coder.share(names, defined);
            Reincarnation.exhume(type).rebirth(coder);
            classes.append(coder.toString()).append("\r\n");
            runtime |= coder.requiresRuntime();
        }

        StringBuilder bundle = new StringBuilder();
        bundle.append(JavascriptRuntime.Core).append("\r\n\r\n");
        bundle.append(JavascriptRuntime.Intrinsics).append("\r\n\r\n");

        if (runtime) {
            bundle.append(JavascriptRuntime.Standard).append("\r\n\r\n");
        }
        bundle.append(classes);

        // Invoke the main method when the entry class declares it.
        if (options.emitMain) {
            try {
                java.lang.reflect.Method main = entry.getDeclaredMethod("main", String[].class);
                if (java.lang.reflect.Modifier.isStatic(main.getModifiers()) && java.lang.reflect.Modifier.isPublic(main.getModifiers())) {
                    bundle.append("\r\nif (typeof process !== \"undefined\" && process.argv) {\r\n");
                    bundle.append("    $c.").append(names.allocate(entry)).append(".").append(names.allocate(main)).append("(process.argv.slice(2));\r\n");
                    bundle.append("}\r\n");
                }
            } catch (NoSuchMethodException e) {
                // no entry point
            }
        }
        return bundle.toString();
    }

    /**
     * Collect the dependency closure in post-order.
     * 
     * @param type A target class.
     * @param order A collected classes.
     * @param visited A visited classes.
     */
    private static void collect(Class type, Set<Class> order, Set<Class> visited) {
        if (type == null || type.isPrimitive() || type.isArray()) {
            return;
        }

        // Map the standard API to the Java implementation which is transpiled into JavaScript.
        type = Classlib.replace(type);

        // The native bridge is available only at compile time, so it is not emitted.
        if (Classlib.isNative(type)) {
            return;
        }

        if (!visited.add(type)) {
            return;
        }

        // The runtime collections expose the stream API and the map views are returned as the
        // classlib collections.
        if (Classlib.isRuntime(type)) {
            collect(java.util.stream.Stream.class, order, visited);
            collect(java.util.Optional.class, order, visited);
        }
        if (reincarnation.classlib.java.util.Map.class.isAssignableFrom(type)) {
            collect(java.util.List.class, order, visited);
            collect(java.util.Set.class, order, visited);
            collect(java.util.Map.Entry.class, order, visited);
        }

        // The explicitly excluded class is not emitted.
        if (type.isAnnotationPresent(Unnecessary.class)) {
            return;
        }

        // The standard classes are not emitted. They are mapped to the native JavaScript API or
        // the standard runtime.
        if (isStandard(type) || !isAvailable(type)) {
            return;
        }

        Reincarnation reincarnation;
        try {
            reincarnation = Reincarnation.exhume(type);
        } catch (Throwable e) {
            return;
        }

        collect(type.getSuperclass(), order, visited);

        for (Class inf : type.getInterfaces()) {
            collect(inf, order, visited);
        }

        for (Class local : reincarnation.locals) {
            collect(local, order, visited);
        }

        for (Class anonymous : reincarnation.anonymous) {
            collect(anonymous, order, visited);
        }

        order.add(type);

        for (Class dependency : reincarnation.classes) {
            collect(dependency, order, visited);
        }
    }

    /**
     * Check whether the specified class belongs to the standard library.
     * 
     * @param type A target class.
     * @return A result.
     */
    private static boolean isStandard(Class type) {
        String name = type.getName();
        return name.startsWith("java.") || name.startsWith("javax.") //
                || name.startsWith("jdk.") || name.startsWith("sun.") || name.startsWith("com.sun.");
    }

    /**
     * Check whether the bytecode of the specified class is available.
     * 
     * @param type A target class.
     * @return A result.
     */
    private static boolean isAvailable(Class type) {
        ClassLoader loader = type.getClassLoader();
        if (loader == null) {
            loader = ClassLoader.getSystemClassLoader();
        }

        try (InputStream input = loader.getResourceAsStream(type.getName().replace('.', '/') + ".class")) {
            return input != null;
        } catch (IOException e) {
            return false;
        }
    }
}
