/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder.js;

/**
 * The representation of the Java {@code long} primitive in the generated JavaScript.
 */
public enum LongMode {

    /** The JavaScript number. It is exact only up to 2^53. */
    NUMBER,

    /** The JavaScript BigInt. It is exact over the whole 64 bit range. */
    BIGINT
}
