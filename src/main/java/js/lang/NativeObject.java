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
}
