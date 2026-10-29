/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package js.lang;

/**
 * The JavaScript dynamic proxy functions.
 */
@Native
public final class NativeProxy {

    /**
     * Hide constructor.
     */
    private NativeProxy() {
    }

    /**
     * Create the proxy instance.
     * 
     * @param interfaces Interface classes.
     * @param handler An invocation handler.
     * @return A proxy instance.
     */
    @Native("$rt_proxy($0, $1)")
    public static Object create(Object[] interfaces, Object handler) {
        return null;
    }

    /**
     * Get the invocation handler.
     * 
     * @param proxy A proxy instance.
     * @return An invocation handler.
     */
    @Native("$rt_proxyHandlers.get($0)")
    public static Object handler(Object proxy) {
        return null;
    }

    /**
     * Check whether the class is a proxy class.
     * 
     * @param type A reflection class.
     * @return A result.
     */
    @Native("$rt_proxyClasses.has($0.jsClass)")
    public static boolean isProxy(Object type) {
        return false;
    }

    /**
     * Create the proxy class.
     * 
     * @param interfaces Interface classes.
     * @return A reflection class.
     */
    @Native("$rt_proxyClass($0)")
    public static Object proxyClass(Object[] interfaces) {
        return null;
    }
}
