/*
 * Copyright (C) 2024 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.coder;

import java.util.LinkedHashMap;
import java.util.Map;

public class AnnotationLike {

    /** The actual annotation type. */
    public final Class clazz;

    /** The key-value store. */
    public final Map<String, Object> values = new LinkedHashMap();

    /**
     * Create an annotation data holder.
     * 
     * @param clazz
     */
    public AnnotationLike(Class clazz) {
        this.clazz = clazz;
    }
}
