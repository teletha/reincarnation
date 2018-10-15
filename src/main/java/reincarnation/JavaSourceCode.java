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
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
 * @version 2018/10/14 16:52:15
 */
public class JavaSourceCode implements Code {

    /** The target class. */
    public final Class clazz;

    /** The initializer manager. */
    public final List<Code> staticInitializer = new ArrayList();

    /** The initializer manager. */
    public final List<Code> initializer = new ArrayList();

    /** The constructor manager. */
    public final Map<Constructor, Code> constructors = new LinkedHashMap();

    /** The method manager. */
    public final Map<Method, Code> methods = new LinkedHashMap();

    /** The member manager. */
    public final Map<Class, JavaSourceCode> members = new LinkedHashMap();

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
     * Retrieve the enclosing source code.
     * 
     * @return A enclosing source code.
     */
    public JavaSourceCode enclosing() {
        return this;
    }

    /**
     * Retrieve the root enclosing source code.
     * 
     * @return A root enclosing source code.
     */
    public final JavaSourceCode enclosingRoot() {
        JavaSourceCode parent = enclosing();

        return parent == this ? this : parent.enclosingRoot();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        analyze();

        coder.writePackage(clazz.getPackage());
        coder.writeImport(dependency.classes);

        writeType(clazz, coder);
    }

    /**
     * Write the target member class only.
     * 
     * @param coder
     * @param target
     */
    public void write(Coder coder, Class target) {
        coder.writePackage(clazz.getPackage());
        coder.writeImport(dependency.classes);

        write(coder, computeHierarchy(target));
    }

    private void write(Coder coder, Deque<JavaSourceCode> hierarchy) {
        JavaSourceCode current = hierarchy.poll();

        if (hierarchy.isEmpty()) {
            current.write(coder);
        } else {
            coder.writeType(current.clazz, () -> {
                write(coder, hierarchy);
            });
        }
    }

    /**
     * Compute class hierarchy tree.
     * 
     * @param current A target type.
     * @return
     */
    private Deque<JavaSourceCode> computeHierarchy(Class current) {
        LinkedList<JavaSourceCode> hierarchy = new LinkedList();

        while (current != null) {
            hierarchy.addFirst(Reincarnation.unearth(current));
            current = current.getEnclosingClass();
        }

        return hierarchy;
    }

    /**
     * Analyze byte code.
     */
    protected synchronized void analyze() {
        if (analyzed == false) {
            analyzed = true;

            try {
                new ClassReader(clazz.getName()).accept(new JavaClassDecompiler(this), 0);
            } catch (IOException e) {
                throw I.quiet(e);
            }
        }
    }

    /**
     * Class declaration.
     * 
     * @param type
     */
    protected void writeType(Class type, Coder coder) {
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
        JavaCoder coder = new JavaCoder().addType(clazz);
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
                if (dependency == clazz) {
                    return;
                }

                // exclude primitive
                if (dependency.isPrimitive()) {
                    return;
                }

                if (dependency.getName().startsWith(clazz.getName().concat("$"))) {
                    members.add(dependency);
                } else {
                    classes.add(dependency);
                }
            }
        }
    }

    /**
     * @version 2018/10/15 20:54:46
     */
    static class JavaMemberSourceCode extends JavaSourceCode {

        /** The enclosing code. */
        private final JavaSourceCode enclosing;

        /**
         * Specialized for member class.
         * 
         * @param clazz A member class.
         */
        JavaMemberSourceCode(Class clazz) {
            super(clazz);

            this.enclosing = Reincarnation.unearth(clazz.getEnclosingClass());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void require(Class dependency) {
            enclosing.require(dependency);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public JavaSourceCode enclosing() {
            return enclosing;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void write(Coder coder) {
            analyze();

            writeType(clazz, coder);
        }
    }
}
