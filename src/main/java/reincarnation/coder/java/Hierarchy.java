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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import reincarnation.Reincarnation;

/**
 * @version 2018/10/21 16:45:39
 */
class Hierarchy {

    final Class clazz;

    final List<Hierarchy> children = new ArrayList();

    /**
     * @param clazz
     */
    Hierarchy(Class clazz) {
        this.clazz = clazz;
    }

    private Hierarchy child(Class member) {
        for (Hierarchy child : children) {
            if (child.clazz == member) {
                return child;
            }
        }

        Hierarchy child = new Hierarchy(member);
        children.add(child);
        return child;
    }

    static Hierarchy calculate(Reincarnation reincarnation) {
        Class root = Classes.enclosingRoot(reincarnation.clazz);
        Hierarchy hierarchy = new Hierarchy(root);

        Set<Class> resolved = new HashSet();
        LinkedList<Class> queue = new LinkedList(reincarnation.dependency.classes);
        while (queue.isEmpty() == false) {
            Class clazz = queue.poll();
            LinkedList<Class> enclosings = Classes.enclosings(clazz);

            if (enclosings.removeFirst() == root) {
                if (resolved.add(clazz)) {
                    Hierarchy current = hierarchy;

                    for (Class encloser : enclosings) {
                        current = current.child(encloser);
                    }
                    queue.addAll(Reincarnation.exhume(clazz).dependency.classes);
                }
            }
        }

        return null;
    }
}