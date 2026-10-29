/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.grammar;

import java.lang.reflect.Proxy;

import org.junit.jupiter.api.Test;

import reincarnation.transpiler.BundleVerifier;

/**
 * Test the JavaScript output for {@link Proxy}.
 */
class ProxyTest extends BundleVerifier {

    @Test
    void method() throws Exception {
        verifyReflection(ProxyMethod.class, "run");
    }

    @Test
    void invocationHandler() throws Exception {
        verifyReflection(InvocationHandlerMethod.class, "run");
    }

    @Test
    void proxyClass() throws Exception {
        verifyReflection(ProxyClass.class, "run");
    }

    /** The proxy interface. */
    interface Greeting {

        String hello(String name);

        int value();

        default String defaultHello() {
            return "default";
        }
    }

    /** Invoke the proxy methods. */
    static class ProxyMethod {

        static String run() {
            Greeting greeting = (Greeting) Proxy.newProxyInstance(Greeting.class.getClassLoader(),
                    new Class[] {Greeting.class}, (proxy, method, args) -> {
                        if (method.getName().equals("hello")) {
                            return "Hi " + args[0];
                        }
                        if (method.getName().equals("value")) {
                            return 7;
                        }
                        return "default";
                    });
            return greeting.hello("A") + "|" + greeting.value() + "|" + greeting.defaultHello() + "|"
                    + (greeting instanceof Greeting ? "true" : "false");
        }
    }

    /** Get the invocation handler. */
    static class InvocationHandlerMethod {

        static String run() {
            Greeting greeting = (Greeting) Proxy.newProxyInstance(Greeting.class.getClassLoader(),
                    new Class[] {Greeting.class}, (proxy, method, args) -> "ok");
            return Proxy.getInvocationHandler(greeting) != null ? "true" : "false";
        }
    }

    /** Check the proxy class. */
    static class ProxyClass {

        static String run() {
            Class<?> type = Proxy.getProxyClass(Greeting.class.getClassLoader(), Greeting.class);
            return Proxy.isProxyClass(type) ? "true" : "false";
        }
    }
}
