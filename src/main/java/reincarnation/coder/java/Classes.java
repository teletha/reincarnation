/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder.java;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import kiss.model.Model;

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
     * Check member type.
     * 
     * @return
     */
    static boolean isMember(Class encloser, Class target) {
        return enclosings(target).contains(encloser);
    }

    /**
     * Check whether the given class is sealed sub class or not.
     * 
     * @param clazz
     * @return
     */
    static boolean isSealedSubclass(Class clazz) {
        for (Class type : Model.collectTypes(clazz)) {
            if (type.isSealed()) {
                return true;
            }
        }
        return false;
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

    static Set<Class> inner(Class clazz) {
        Set<Class> set = new HashSet();
        inner(clazz, set);
        return set;
    }

    private static void inner(Class clazz, Set<Class> set) {
        set.add(clazz);

        for (Class declared : clazz.getDeclaredClasses()) {
            inner(declared, set);
        }
    }
}