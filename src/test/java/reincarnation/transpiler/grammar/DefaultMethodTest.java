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

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import reincarnation.transpiler.BundleVerifier;

/**
 * Test the default method and its override with the direct and reflective invocation.
 */
class DefaultMethodTest extends BundleVerifier {

    @Test
    void defaultDirect() throws Exception {
        verify(DefaultDirect.class, "run");
    }

    @Test
    void overrideDirect() throws Exception {
        verify(OverrideDirect.class, "run");
    }

    @Test
    void defaultReflection() throws Exception {
        verifyReflection(DefaultReflection.class, "run");
    }

    @Test
    void overrideReflection() throws Exception {
        verifyReflection(OverrideReflection.class, "run");
    }

    @Test
    void defaultGetMethod() throws Exception {
        verifyReflection(DefaultGetMethod.class, "run");
    }

    @Test
    void isDefault() throws Exception {
        verifyReflection(IsDefault.class, "run");
    }

    /** The default method. */
    interface Greeting {

        default String hello() {
            return "hello";
        }
    }

    /** The implementation. */
    static class Greeter implements Greeting {
    }

    /** The override. */
    static class Loud implements Greeting {

        @Override
        public String hello() {
            return "HELLO";
        }
    }

    /** Invoke the default method directly. */
    static class DefaultDirect {

        static String run() {
            return new Greeter().hello();
        }
    }

    /** Invoke the overridden method directly. */
    static class OverrideDirect {

        static String run() {
            return new Loud().hello();
        }
    }

    /** Invoke the default method reflectively. */
    static class DefaultReflection {

        static String run() throws Exception {
            Method method = Greeting.class.getDeclaredMethod("hello");
            return (String) method.invoke(new Greeter());
        }
    }

    /** Invoke the overridden method reflectively. */
    static class OverrideReflection {

        static String run() throws Exception {
            Method method = Greeting.class.getDeclaredMethod("hello");
            return (String) method.invoke(new Loud());
        }
    }

    /** Find the default method from the implementation class. */
    static class DefaultGetMethod {

        static String run() throws Exception {
            Method method = Greeter.class.getMethod("hello");
            return (String) method.invoke(new Greeter());
        }
    }

    /** Check the default method flag. */
    static class IsDefault {

        static String run() throws Exception {
            Method defaultMethod = Greeting.class.getDeclaredMethod("hello");
            Method overrideMethod = Loud.class.getDeclaredMethod("hello");
            return (defaultMethod.isDefault() ? "true" : "false") + "|"
                    + (overrideMethod.isDefault() ? "true" : "false");
        }
    }
}
