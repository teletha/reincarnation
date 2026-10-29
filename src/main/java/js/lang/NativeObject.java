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
 * The JavaScript object functions.
 */
@Native
public final class NativeObject {

    /**
     * Hide constructor.
     */
    private NativeObject() {
    }

    /**
     * Get the identity hash code.
     * 
     * @param value A value.
     * @return An identity hash code.
     */
    @Native("$rt_identityHashCode($0)")
    public static int identityHashCode(Object value) {
        return java.lang.System.identityHashCode(value);
    }

    /**
     * Invoke the named function of the receiver.
     * 
     * @param receiver A receiver.
     * @param name A function name.
     * @param args Arguments.
     * @return A result.
     */
    @Native("$0[$1](...$2)")
    public static Object invoke(Object receiver, String name, Object[] args) {
        return null;
    }

    /**
     * Get the JavaScript class of the reflection class.
     * 
     * @param type A reflection class.
     * @return A JavaScript class.
     */
    @Native("$0.jsClass")
    public static Object classObject(Object type) {
        return null;
    }

    /**
     * Create the instance of the JavaScript class.
     * 
     * @param type A JavaScript class.
     * @param args Arguments.
     * @return An instance.
     */
    @Native("new $0(...$1)")
    public static Object construct(Object type, Object[] args) {
        return null;
    }
}
