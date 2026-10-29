/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder.js;

import reincarnation.coder.CodingOption;

public class JavascriptCodingOption extends CodingOption {

    /** Whether the runtime is embedded in the output. A bundle embeds it once by itself. */
    public boolean embedRuntime = true;

    /** Whether the output is a bundle of the whole dependency closure. */
    public boolean bundle;

    /**
     * Whether an unmapped Java API is rejected as an error instead of being emitted as an
     * undefined JavaScript call.
     */
    public boolean strictApi;

    /** The representation of the Java {@code long} primitive. */
    public LongMode longMode = LongMode.BIGINT;

    /** Whether the classes annotated by {@link Necessary} are scanned automatically. */
    public boolean scanNecessary;

    /** The additional classes which must be included in the bundle. */
    public java.util.Set<Class> necessary = java.util.Set.of();

    /** Whether the {@code main(String[])} method is invoked at the end of the bundle. */
    public boolean emitMain = true;

    /** Whether the class metadata is emitted for the reflection support. */
    public boolean reflection;
}