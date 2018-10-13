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

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.objectweb.asm.ClassReader;

import kiss.I;
import reincarnation.coder.Code;
import reincarnation.coder.Coder;
import reincarnation.coder.JavaCoder;

/**
 * @version 2018/10/10 15:32:50
 */
public final class JavaSourceCode implements Code {

    /** The root class. */
    public final Class root;

    /** The target class. */
    public final Class clazz;

    /** The target class name. */
    public final String className;

    /** The initializer manager. */
    public final List<Code> staticInitializer = new ArrayList();

    /** The initializer manager. */
    public final List<Code> initializer = new ArrayList();

    /** The constructor manager. */
    public final Map<Constructor, Code> constructors = new LinkedHashMap();

    /** The method manager. */
    public final Map<Method, Code> methods = new LinkedHashMap();

    /** The dependency classes. */
    private final Dependency dependency = new Dependency();

    /** The flag. */
    private boolean analyzed;

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
     * {@inheritDoc}
     */
    @Override
    public synchronized void write(Coder coder) {
        if (analyzed == false) {
            analyzed = true;

            try {
                new ClassReader(clazz.getName()).accept(new JavaClassDecompiler(this), 0);
            } catch (IOException e) {
                throw I.quiet(e);
            }
        }

        coder.writePackage(root.getPackage());
        coder.writeImport(dependency.classes);

        if (root == clazz) {
            write(clazz, coder);
        } else {
            coder.writeType(root, () -> {
                write(clazz, coder);
            });
        }
    }

    /**
     * Class declaration.
     * 
     * @param type
     */
    private void write(Class type, Coder coder) {
        coder.writeType(type, () -> {
            List<Field> statics = new ArrayList();
            List<Field> fields = new ArrayList();
            for (Field field : type.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    statics.add(field);
                } else {
                    fields.add(field);
                }
            }

            statics.forEach(coder::writeStaticField);
            staticInitializer.forEach(coder::writeStaticInitializer);
            fields.forEach(coder::writeField);
            initializer.forEach(coder::writeInitializer);
            constructors.entrySet().forEach(e -> coder.writeConstructor(e.getKey(), e.getValue()));
            methods.entrySet().forEach(e -> coder.writeMethod(e.getKey(), e.getValue()));
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        JavaCoder coder = new JavaCoder().addType(root);
        write(coder);
        return coder.toString();
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

                // exclude source
                if (dependency == clazz || dependency == root) {
                    return;
                }

                // exclude primitive
                if (dependency.isPrimitive() || dependency.isLocalClass()) {
                    return;
                }

                // exclude java.lang package
                if (dependency.getPackage().getName().equals("java.lang")) {
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
