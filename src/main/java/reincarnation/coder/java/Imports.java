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

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import kiss.I;
import kiss.Model;
import reincarnation.util.Classes;

class Imports {

    /** The core package. */
    private static final Package Core = Object.class.getPackage();

    /** The imported class. */
    private final Set<Class> imported = new TreeSet<>(Comparator.comparing(Class::getName));

    /** The imported simple class name. */
    private final Set<String> importedName = new HashSet();

    /** The Implicitly imported class. */
    private final Set<Class> importedImplicitly = new HashSet();

    /** The Implicitly imported class. */
    private final Set<String> importedNameImplicitly = new HashSet();

    /** The root class. */
    private Class root;

    /**
     * Set base class.
     * 
     * @param base
     */
    void setBase(Class base) {
        this.root = Classes.enclosingRoot(base);

        for (Class inner : Classes.inner(root)) {
            addImplicitly(inner);
        }

        for (Class clazz : Model.collectTypes(base)) {
            for (Class member : clazz.getDeclaredClasses()) {
                addImplicitly(member);
            }
        }
    }

    private void addImplicitly(Class clazz) {
        String simple = clazz.getSimpleName();

        if (importedNameImplicitly.add(simple)) {
            importedImplicitly.add(clazz);
        }
    }

    /**
     * Compute fully qualified class name.
     * 
     * @param clazz A target class.
     * @return A class name.
     */
    String name(Class clazz) {
        int depth = 0;
        Class raw = clazz;
        while (raw.isArray()) {
            depth++;
            raw = raw.getComponentType();
        }

        String name;

        if (raw.isPrimitive()) {
            name = raw.getSimpleName();
        } else if (raw.isLocalClass()) {
            name = raw.getSimpleName();
        } else if (raw.isAnonymousClass()) {
            name = clazz.getName().substring(clazz.getPackageName().length() + 1);
        } else if (imported.contains(raw)) {
            name = raw.getSimpleName();
        } else if (importedImplicitly.contains(raw)) {
            name = raw.getSimpleName();
            if (!Classes.isMember(root, raw)) {
                imported.add(raw);
                importedName.add(name);
            }
        } else {
            name = raw.getSimpleName();
            if (importedName.contains(name) || importedNameImplicitly.contains(name)) {
                name = raw.getCanonicalName();
            } else {
                imported.add(raw);
                importedName.add(name);
            }
        }

        return name.concat("[]".repeat(depth));
    }

    /**
     * Export classes.
     * 
     * @return
     */
    Map<String, List<Class>> export() {
        return I.signal(imported).skip(x -> x.getPackage() == Core).toGroup(x -> {
            String name = x.getPackage().getName();
            int index = name.indexOf(".");
            if (index == -1) {
                return name;
            } else {
                return name.substring(0, index);
            }
        });
    }
}