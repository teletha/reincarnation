/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import org.objectweb.asm.ClassReader;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

import kiss.I;

/**
 * @version 2018/10/10 15:32:50
 */
public final class JavaSourceCode {

    /** The root class. */
    public final Class root;

    /** The target class. */
    public final Class clazz;

    /** The target class name. */
    public final String className;

    /** The dependency classes. */
    private final Dependency dependency = new Dependency();

    /** The compilation unit. */
    private CompilationUnit unit;

    /**
     * Java source code.
     * 
     * @param clazz
     */
    public JavaSourceCode(Class clazz) {
        this.clazz = Objects.requireNonNull(clazz);
        this.root = findRootClass(clazz);

        String fqcn = clazz.getName();

        if (clazz.isAnonymousClass()) {
            fqcn = fqcn.replace("$", "$" + root.getSimpleName() + "$");
        }
        this.className = fqcn;
    }

    /**
     * Find the top level class.
     * 
     * @param clazz A target class.
     * @return A top level class.
     */
    private static Class findRootClass(Class clazz) {
        while (clazz.getEnclosingClass() != null) {
            clazz = clazz.getEnclosingClass();
        }
        return clazz;
    }

    /**
     * Add dependency type of this source.
     * 
     * @param dependency
     */
    public void require(Class dependency) {
        this.dependency.require(dependency);
    }

    /**
     * Build source code.
     * 
     * @return
     */
    public synchronized CompilationUnit build() {
        if (unit == null) {
            unit = new CompilationUnit(clazz.getPackageName());

            try {
                new ClassReader(clazz.getName()).accept(new JavaClassDecompiler(this, unit), 0);
            } catch (Exception e) {
                throw I.quiet(e);
            }

            // member classes
            for (Class member : dependency.members) {
                merge(unit, new JavaSourceCode(member).build());
            }

            // clear up
            removeMemberImport();
        }
        return unit;
    }

    /**
     * Merge two {@link CompilationUnit}s by {@link TypeDeclaration}.
     * 
     * @param one
     * @param other
     */
    private static void merge(CompilationUnit one, CompilationUnit other) {
        NodeList<TypeDeclaration<?>> ones = one.getTypes();
        NodeList<TypeDeclaration<?>> others = other.getTypes();

        if (ones.size() == 1 && others.size() == 1) {
            TypeDeclaration<?> type = ones.get(0);
            TypeDeclaration<?> oType = others.get(0);

            if (type.isClassOrInterfaceDeclaration() && oType.isClassOrInterfaceDeclaration() && type.getName().equals(oType.getName())) {
                for (BodyDeclaration<?> member : oType.getMembers()) {
                    type.addMember(member);
                }
            }
        }
    }

    /**
     * Remove member classes from import declarations.
     */
    private void removeMemberImport() {
        Iterator<ImportDeclaration> iterator = unit.getImports().iterator();

        while (iterator.hasNext()) {
            ImportDeclaration next = iterator.next();

            if (next.getNameAsString().startsWith(root.getName() + ".")) {
                iterator.remove();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return build().toString();
    }

    /**
     * @version 2018/10/11 21:48:55
     */
    private class Dependency {

        /** The dependency classes. */
        private final Set<Class> classes = new HashSet();

        /** The dependency member classes. */
        private final Set<Class> members = new HashSet();

        /**
         * Depends on the specified class.
         * 
         * @param dependency
         */
        private void require(Class dependency) {
            if (dependency != null) {
                if (dependency.isArray()) {
                    require(dependency.getComponentType());
                    return;
                }

                if (dependency == clazz || dependency == root) {
                    return;
                }

                if (dependency.isPrimitive() || dependency.isLocalClass()) {
                    return;
                }

                if (dependency.isAnonymousClass() || dependency.getCanonicalName().startsWith(root.getCanonicalName() + ".")) {
                    members.add(dependency);
                } else {
                    classes.add(dependency);
                }
            }
        }
    }
}
