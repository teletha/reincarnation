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

import java.util.HashSet;
import java.util.Set;

/**
 * @version 2018/10/19 14:24:55
 */
class Imports {

    /** The imported class. */
    final Set<Class> imported = new HashSet();

    /** The imported simple class name. */
    private final Set<String> importedName = new HashSet();

    /**
     * Try to import all hierarchy classes.
     * 
     * @param clazz
     */
    void addBase(Class clazz) {
        add(clazz);

        Class superclass = clazz.getSuperclass();

        if (superclass != null && superclass != Object.class) {
            addBase(superclass);
        }

        for (Class interfaceClass : clazz.getInterfaces()) {
            addBase(interfaceClass);
        }

        for (Class member : clazz.getClasses()) {
            add(member);
        }
    }

    /**
     * Try to import class.
     * 
     * @param clazz A class to import.
     */
    void add(Class clazz) {
        if (clazz.isAnonymousClass()) {
            return;
        }

        String simple = clazz.getSimpleName();

        if (importedName.add(simple)) {
            imported.add(clazz);
        }
    }

    /**
     * Try to import class.
     * 
     * @param classes Classes to import.
     */
    void add(Set<Class> classes) {
        for (Class clazz : classes) {
            add(clazz);
        }
    }

    /**
     * Check whether the specified class is imported or not.
     * 
     * @param clazz
     * @return
     */
    boolean contains(Class clazz) {
        return imported.contains(clazz);
    }
}
