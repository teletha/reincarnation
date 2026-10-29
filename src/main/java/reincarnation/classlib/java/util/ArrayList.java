/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.util;

import java.util.Collection;

/**
 * The Java implementation of {@link java.util.ArrayList}.
 */
public final class ArrayList extends List {

    /**
     * Create an empty list.
     */
    public ArrayList() {
        super();
    }

    /**
     * Create an empty list.
     * 
     * @param capacity A capacity.
     */
    public ArrayList(int capacity) {
        super();
    }

    /**
     * Create a list from the collection.
     * 
     * @param collection A collection.
     */
    public ArrayList(Collection collection) {
        super(collection);
    }
}
