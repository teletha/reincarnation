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
 * The JavaScript {@code undefined} value.
 * <p>
 * Java has no {@code undefined}, so this singleton represents it on the JVM. It is transpiled to
 * {@code undefined} in JavaScript.
 * </p>
 */
@Native
public final class NativeUndefined {

    /** The singleton. */
    @Native("undefined")
    public static final NativeUndefined INSTANCE = new NativeUndefined();

    /**
     * Hide constructor.
     */
    private NativeUndefined() {
    }

    /**
     * Check whether the specified value is {@code undefined}.
     * 
     * @param value A target value.
     * @return A result.
     */
    @Native("$0 === undefined")
    public static boolean is(Object value) {
        return value == INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "undefined";
    }
}
