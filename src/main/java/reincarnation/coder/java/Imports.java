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
import java.util.Set;

import kiss.model.Model;

class Imports {

    /** The imported class. */
    final Set<Class> imported = new HashSet();

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

        if (raw.isPrimitive()) {
            return raw.getSimpleName().concat("[]".repeat(depth));
        }

        if (raw.isLocalClass()) {
            return raw.getSimpleName().concat("[]".repeat(depth));
        }

        if (raw.isAnonymousClass()) {
            return clazz.getName().substring(clazz.getPackageName().length() + 1);
        }

        if (imported.contains(raw)) {
            return raw.getSimpleName().concat("[]".repeat(depth));
        } else if (importedImplicitly.contains(raw)) {
            if (!Classes.isMember(root, raw)) {
                imported.add(raw);
                importedName.add(raw.getSimpleName());
            }
            return raw.getSimpleName().concat("[]".repeat(depth));
        } else {
            String name = raw.getSimpleName();
            if (importedName.contains(name) || importedNameImplicitly.contains(name)) {
                return clazz.getCanonicalName();
            }

            imported.add(raw);
            importedName.add(name);

            return raw.getSimpleName().concat("[]".repeat(depth));
        }
    }
}