/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.lang.reflect;

import js.lang.NativeProxy;

/**
 * The Java implementation of {@link java.lang.reflect.Proxy} which is transpiled into JavaScript.
 */
public class Proxy {

    /**
     * Create the proxy instance.
     * 
     * @param loader A class loader.
     * @param interfaces Interface classes.
     * @param handler An invocation handler.
     * @return A proxy instance.
     */
    public static Object newProxyInstance(ClassLoader loader, Class[] interfaces, InvocationHandler handler) {
        return NativeProxy.create(interfaces, handler);
    }

    /**
     * Get the invocation handler.
     * 
     * @param proxy A proxy instance.
     * @return An invocation handler.
     */
    public static InvocationHandler getInvocationHandler(Object proxy) {
        return (InvocationHandler) NativeProxy.handler(proxy);
    }

    /**
     * Check whether the class is a proxy class.
     * 
     * @param type A reflection class.
     * @return A result.
     */
    public static boolean isProxyClass(Class type) {
        return NativeProxy.isProxy(type);
    }

    /**
     * Create the proxy class.
     * 
     * @param loader A class loader.
     * @param interfaces Interface classes.
     * @return A reflection class.
     */
    public static Class getProxyClass(ClassLoader loader, Class... interfaces) {
        return (Class) NativeProxy.proxyClass(interfaces);
    }
}
