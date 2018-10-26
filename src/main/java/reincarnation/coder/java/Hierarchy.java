/*
 * Copyright (C) 2018 Reincarnation Development Team
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

/**
 * @version 2018/10/21 16:45:39
 */
class Hierarchy {

    final Class clazz;

    final List<Hierarchy> children = new ArrayList();

    boolean skelton = false;

    final Set<Class> classes = new HashSet();

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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return dump(1);
    }

    private String dump(int indent) {
        StringBuilder builder = new StringBuilder();
        builder.append(clazz.getName());
        for (Hierarchy hierarchy : children) {
            builder.append("\r\n").append("\t".repeat(indent)).append(hierarchy.dump(indent + 1));
        }
        return builder.toString();
    }

    static Hierarchy calculate(Reincarnation reincarnation) {
        Class root = Classes.enclosingRoot(reincarnation.clazz);
        Hierarchy hierarchy = new Hierarchy(root);
        hierarchy.classes.addAll(reincarnation.classes);
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
                    hierarchy.classes.addAll(r.classes);
                }
            }
        }
        return hierarchy;
    }
}
