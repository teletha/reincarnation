/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.lang.invoke;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import js.lang.NativeObject;

/**
 * The Java implementation of {@link java.lang.invoke.MethodHandle} which is transpiled into
 * JavaScript.
 */
public class MethodHandle {

    /** The declaring class. */
    private final Class owner;

    /** The method name. */
    private final String name;

    /** The method or null. */
    private final Method method;

    /** The constructor or null. */
    private final Constructor constructor;

    /** Whether the method is virtual. */
    private final boolean virtual;

    /**
     * Create the handle.
     * 
     * @param owner A declaring class.
     * @param name A method name.
     * @param method A method or null.
     * @param constructor A constructor or null.
     * @param virtual Whether the method is virtual.
     */
    MethodHandle(Class owner, String name, Method method, Constructor constructor, boolean virtual) {
        this.owner = owner;
        this.name = name;
        this.method = method;
        this.constructor = constructor;
        this.virtual = virtual;
    }

    /**
     * Invoke the handle.
     * 
     * @param args Arguments.
     * @return A result.
     * @throws Throwable If the invocation fails.
     */
    public Object invoke(Object... args) throws Throwable {
        return invokeArray(args);
    }

    /**
     * Invoke the handle exactly.
     * 
     * @param args Arguments.
     * @return A result.
     * @throws Throwable If the invocation fails.
     */
    public Object invokeExact(Object... args) throws Throwable {
        return invokeArray(args);
    }

    /**
     * Invoke the handle with the argument list.
     * 
     * @param args Arguments.
     * @return A result.
     * @throws Throwable If the invocation fails.
     */
    public Object invokeWithArguments(java.util.List args) throws Throwable {
        return invokeWithArgumentsArray(args.toArray());
    }

    /**
     * Invoke the handle with the argument array.
     * 
     * @param args Arguments.
     * @return A result.
     * @throws Throwable If the invocation fails.
     */
    public Object invokeWithArgumentsArray(Object[] args) throws Throwable {
        if (args.length == 1 && args[0] instanceof java.util.List) {
            return invokeArray(((java.util.List) args[0]).toArray());
        }
        return invokeArray(args);
    }

    /**
     * Invoke the handle with the argument array.
     * 
     * @param args Arguments.
     * @return A result.
     * @throws Throwable If the invocation fails.
     */
    public Object invokeArray(Object[] args) throws Throwable {
        if (constructor != null) {
            return constructor.newInstance(args);
        }
        if (method != null) {
            if (virtual) {
                return method.invoke(args[0], tail(args));
            }
            return method.invoke(null, args);
        }
        if (constructor == null && method == null && !virtual && name == null) {
            return NativeObject.construct(NativeObject.classObject(owner), args);
        }
        if (virtual) {
            return NativeObject.invoke(args[0], name, tail(args));
        }
        return NativeObject.invoke(NativeObject.classObject(owner), name, args);
    }

    /**
     * Get the arguments except the receiver.
     * 
     * @param args Arguments.
     * @return Arguments.
     */
    private static Object[] tail(Object[] args) {
        Object[] rest = new Object[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            rest[i - 1] = args[i];
        }
        return rest;
    }
}
