/*
 * Copyright (C) 2020 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder.java;

import java.util.LinkedList;

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

    /**
     * Calculate root of enclosing class.
     * 
     * @param clazz A target class.
     * @return A root of enclosing class.
     */
    static Class enclosingRoot(Class clazz) {
        LinkedList<Class> list = enclosings(clazz);

        return list.isEmpty() ? clazz : list.getFirst();
    }

    /**
     * Collect enclosing classes.
     * 
     * @param clazz A target class.
     * @return A list of enclosing classes.
     */
    static LinkedList<Class> enclosings(Class clazz) {
        LinkedList<Class> list = new LinkedList();

        while (clazz.getEnclosingClass() != null) {
            clazz = clazz.getEnclosingClass();
            list.addFirst(clazz);
        }
        return list;
    }

    /**
     * Check member type.
     * 
     * @return
     */
    static boolean isMember(Class encloser, Class target) {
        return enclosings(target).contains(encloser);
    }
}