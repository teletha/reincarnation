/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.text;

import reincarnation.classlib.java.io.Serializable;
import reincarnation.classlib.java.lang.Cloneable;

/**
 * The Java implementation of {@link java.text.Format} which is transpiled into JavaScript.
 */
public abstract class Format implements Serializable, Cloneable {

    /**
     * Create the format.
     */
    protected Format() {
    }

    /**
     * Format the value.
     * 
     * @param value A value.
     * @return A formatted text.
     */
    public abstract String format(Object value);
}
