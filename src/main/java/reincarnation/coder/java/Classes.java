/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.coder.java;

/**
 * @version 2018/10/20 9:36:18
 */
class Classes {

    /**
     * Helper to check member-like type.
     * 
     * @param clazz A target to check.
     * @return A result.
     */
    static boolean isMemberLike(Class clazz) {
        return clazz.isAnonymousClass() || clazz.isLocalClass() || clazz.isMemberClass();
    }
}
