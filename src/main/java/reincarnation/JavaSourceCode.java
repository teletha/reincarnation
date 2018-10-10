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

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

/**
 * @version 2018/10/10 15:32:50
 */
public final class JavaSourceCode {

    /** The target class. */
    private final Class clazz;

    /** The dependency classes. */
    private final Set<Class> dependencies = new TreeSet();

    /**
     * Java source code.
     * 
     * @param clazz
     */
    public JavaSourceCode(Class clazz) {
        this.clazz = Objects.requireNonNull(clazz);
    }

    /**
     * Add dependency type of this source.
     * 
     * @param dependency
     * @return
     */
    public JavaSourceCode require(Class dependency) {
        if (dependency != null) {
            if (dependency.isArray()) {
                return require(dependency.getComponentType());
            }

            if (!dependency.isPrimitive() && !dependency.isAnonymousClass() && !dependency.isLocalClass()) {
                dependencies.add(dependency);
            }
        }
        return this;
    }

    /**
     * @return
     */
    public CompilationUnit build() {
        CompilationUnit unit = new CompilationUnit(clazz.getPackageName());
        ClassOrInterfaceDeclaration declaration = unit.addClass(clazz.getSimpleName(), Util.modifiers(clazz.getModifiers()));

        return unit;
    }
}
