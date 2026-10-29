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

/**
 * The Java implementation of {@link java.lang.reflect.InvocationHandler} which is transpiled into
 * JavaScript.
 */
public interface InvocationHandler {

    /**
     * Invoke the method.
     * 
     * @param proxy A proxy instance.
     * @param method A method.
     * @param args Arguments.
     * @return A result.
     * @throws Throwable If the invocation fails.
     */
    Object invoke(Object proxy, java.lang.reflect.Method method, Object[] args) throws Throwable;
}
