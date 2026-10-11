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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utilities for the TeaVM class library based JavaScript output.
 */
public final class Classlib {

    /** The cache of substituted types. */
    private static final Map<Class, Class> substitutions = new ConcurrentHashMap();

    /**
     * Hide constructor.
     */
    private Classlib() {
    }

    /**
     * Substitute the given standard class with the corresponding TeaVM class library class if it
     * exists.
     * 
     * @param type A target type.
     * @return A substituted type.
     */
    public static Class substitute(Class type) {
        if (type == null || type.isPrimitive() || type.isArray() || type.isAnonymousClass() || type.isLocalClass()) {
            return type;
        }

        if (type.getName().startsWith("org.teavm.classlib.")) {
            return type;
        }

        // The throwable classes are not emulated by the class library (TThrowable extends the host
        // java.lang.RuntimeException), so they must not be substituted.
        if (Throwable.class.isAssignableFrom(type)) {
            return type;
        }

        return substitutions.computeIfAbsent(type, key -> {
            String name = key.getName();
            if (name.startsWith("java.") || name.startsWith("javax.")) {
                int index = name.lastIndexOf('.');
                String candidate = "org.teavm.classlib." + name.substring(0, index) + ".T" + name.substring(index + 1);
                try {
                    // The bootstrap class loader of java.* classes can not find the class library,
                    // so the loader of this class is used instead.
                    return Class.forName(candidate, false, Classlib.class.getClassLoader());
                } catch (ClassNotFoundException e) {
                    return key;
                }
            }
            return key;
        });
    }

    /**
     * Mangle the method name to make the overloaded methods unique in JavaScript.
     * 
     * @param name A method name.
     * @param params Parameter types.
     * @return A mangled name.
     */
    public static String mangle(String name, Class[] params) {
        StringBuilder builder = new StringBuilder(name).append('$');
        for (Class param : params) {
            mangle(param, builder);
        }
        return builder.toString();
    }

    /**
     * Write the type code for the mangled name.
     * 
     * @param type A parameter type.
     * @param builder A target builder.
     */
    private static void mangle(Class type, StringBuilder builder) {
        if (type.isPrimitive()) {
            builder.append(type.getName());
        } else if (type.isArray()) {
            builder.append('A');
            mangle(type.getComponentType(), builder);
        } else {
            builder.append('L').append(type.getName().replace('.', '_').replace('$', '_')).append('_');
        }
    }

    /**
     * Extract the parameter part of the method descriptor (the Java method identity ignores the
     * return type).
     * 
     * @param descriptor A method descriptor.
     * @return A parameter descriptor (e.g. {@code (I)V}).
     */
    public static String params(String descriptor) {
        int index = descriptor.indexOf(')');
        return index < 0 ? descriptor : descriptor.substring(0, index + 1);
    }

    /**
     * Check whether the given type belongs to the TeaVM class library.
     * 
     * @param type A target type.
     * @return A result.
     */
    public static boolean isClasslib(Class type) {
        return type != null && type.getName().startsWith("org.teavm.classlib.");
    }

    /**
     * Compute the unique JavaScript class name for the class library mode. The simple names collide
     * across the packages, so the fully qualified name is sanitized.
     * 
     * @param type A target type.
     * @return A unique class name.
     */
    public static String uniqueName(Class type) {
        String name = type.getName();
        if (name.startsWith("org.teavm.classlib.")) {
            name = name.substring("org.teavm.classlib.".length());
        }
        return name.replace('.', '_').replace('$', '_');
    }
}
