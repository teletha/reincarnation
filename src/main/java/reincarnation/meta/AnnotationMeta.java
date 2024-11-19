/*
 * Copyright (C) 2024 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.meta;

import java.util.LinkedHashMap;
import java.util.Map;

public class AnnotationMeta {

    public final Class clazz;

    public final Map<String, Object> values = new LinkedHashMap();

    public AnnotationMeta(Class clazz) {
        this.clazz = clazz;
    }
}
