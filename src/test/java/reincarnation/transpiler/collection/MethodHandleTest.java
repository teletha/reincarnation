/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.collection;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import reincarnation.transpiler.BundleVerifier;

/**
 * Test the JavaScript output for {@link MethodHandle}.
 */
class MethodHandleTest extends BundleVerifier {

    @Test
    void virtualNative() throws Exception {
        verifyReflection(VirtualNative.class, "run");
    }

    @Test
    void virtualClasslib() throws Exception {
        verifyReflection(VirtualClasslib.class, "run");
    }

    @Test
    void staticMethod() throws Exception {
        verifyReflection(StaticMethod.class, "run");
    }

    @Test
    void constructor() throws Exception {
        verifyReflection(Constructor.class, "run");
    }

    @Test
    void invokeWithArguments() throws Exception {
        verifyReflection(InvokeWithArguments.class, "run");
    }

    /** Invoke the native method. */
    static class VirtualNative {

        static String run() throws Throwable {
            MethodHandle handle = MethodHandles.lookup().findVirtual(String.class, "toString", MethodType.methodType(String.class));
            return (String) handle.invoke("ok");
        }
    }

    /** Invoke the classlib method. */
    static class VirtualClasslib {

        static int run() throws Throwable {
            List<String> list = new ArrayList<>();
            list.add("a");
            MethodHandle handle = MethodHandles.lookup().findVirtual(ArrayList.class, "size", MethodType.methodType(int.class));
            return (int) handle.invoke(list);
        }
    }

    /** Invoke the static classlib method. */
    static class StaticMethod {

        static int run() throws Throwable {
            MethodHandle handle = MethodHandles.lookup().findStatic(List.class, "of", MethodType.methodType(List.class, Object.class));
            return ((List) handle.invoke("a")).size();
        }
    }

    /** Invoke the classlib constructor. */
    static class Constructor {

        static int run() throws Throwable {
            MethodHandle handle = MethodHandles.lookup().findConstructor(ArrayList.class, MethodType.methodType(void.class, int.class));
            List<String> list = (List<String>) handle.invoke(10);
            list.add("a");
            return list.size();
        }
    }

    /** Invoke with the argument list. */
    static class InvokeWithArguments {

        static String run() throws Throwable {
            MethodHandle handle = MethodHandles.lookup().findVirtual(String.class, "toString", MethodType.methodType(String.class));
            return (String) handle.invokeWithArguments(List.of("ok"));
        }
    }
}
