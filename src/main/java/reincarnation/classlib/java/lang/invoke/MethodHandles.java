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

/**
 * The Java implementation of {@link java.lang.invoke.MethodHandles} which is transpiled into
 * JavaScript.
 */
public class MethodHandles {

    /**
     * Hide constructor.
     */
    private MethodHandles() {
    }

    /**
     * Get the lookup.
     * 
     * @return A lookup.
     */
    public static Lookup lookup() {
        return new Lookup();
    }

    /**
     * The Java implementation of {@link java.lang.invoke.MethodHandles.Lookup} which is transpiled
     * into JavaScript.
     */
    public static final class Lookup {

        /**
         * Create the lookup.
         */
        Lookup() {
        }

        /**
         * Find the virtual method.
         * 
         * @param owner A declaring class.
         * @param name A method name.
         * @param type A method type.
         * @return A method handle.
         */
        public MethodHandle findVirtual(Class owner, String name, MethodType type) {
            Method method = find(owner, name, type);
            return new MethodHandle(owner, name, method, null, true);
        }

        /**
         * Find the special method.
         * 
         * @param owner A declaring class.
         * @param name A method name.
         * @param type A method type.
         * @param special A special caller.
         * @return A method handle.
         */
        public MethodHandle findSpecial(Class owner, String name, MethodType type, Class special) {
            return findVirtual(owner, name, type);
        }

        /**
         * Find the static method.
         * 
         * @param owner A declaring class.
         * @param name A method name.
         * @param type A method type.
         * @return A method handle.
         */
        public MethodHandle findStatic(Class owner, String name, MethodType type) {
            Method method = find(owner, name, type);
            return new MethodHandle(owner, name, method, null, false);
        }

        /**
         * Find the constructor.
         * 
         * @param owner A declaring class.
         * @param type A method type.
         * @return A method handle.
         */
        public MethodHandle findConstructor(Class owner, MethodType type) {
            Constructor constructor = null;
            try {
                constructor = owner.getDeclaredConstructor(type.parameterArray());
            } catch (Throwable e) {
                // the native constructor is used instead
            }
            return new MethodHandle(owner, null, null, constructor, false);
        }

        /**
         * Find the method.
         * 
         * @param owner A declaring class.
         * @param name A method name.
         * @param type A method type.
         * @return A method or null.
         */
        private static Method find(Class owner, String name, MethodType type) {
            try {
                return owner.getMethod(name, type.parameterArray());
            } catch (Throwable e) {
                return null;
            }
        }
    }
}
