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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import reincarnation.Reincarnation;
import reincarnation.util.Classes;

class Hierarchy {

    final Class clazz;

    final List<Hierarchy> children = new ArrayList();

    boolean skelton = false;

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
        hierarchy.skelton = true;

        Set<Class> resolved = new HashSet();
        LinkedList<Class> queue = new LinkedList(reincarnation.classes);
        queue.addAll(reincarnation.anonymous);
        queue.add(reincarnation.clazz);

        while (queue.isEmpty() == false) {
            Class clazz = queue.poll();
            LinkedList<Class> enclosings = Classes.enclosings(clazz);

            if (enclosings.isEmpty() == false && enclosings.removeFirst() == root) {
                if (resolved.add(clazz)) {
                    Hierarchy current = hierarchy;
                    enclosings.add(clazz);

                    for (Class encloser : enclosings) {
                        current = current.child(encloser);
                    }

                    Reincarnation r = Reincarnation.exhume(clazz);
                    queue.addAll(r.classes);
                    queue.addAll(r.anonymous);
                }
            }
        }
        return hierarchy;
    }
}