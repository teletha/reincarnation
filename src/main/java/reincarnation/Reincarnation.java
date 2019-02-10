/*
 * Copyright (C) 2019 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.objectweb.asm.ClassReader;

import kiss.I;
import reincarnation.coder.Code;
import reincarnation.coder.Coder;
import reincarnation.coder.java.JavaCoder;
import reincarnation.coder.java.JavaCodingOption;

/**
 * {@link Reincarnation} is a unit of decompilation.
 * 
 * @version 2018/10/19 12:36:08
 */
public final class Reincarnation {

    /** The cache. */
    private static final Map<Class, Reincarnation> cache = new ConcurrentHashMap();

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
    public final Map<Class, Reincarnation> members = new LinkedHashMap();

    /** The dependency classes. */
    public final Set<Class> classes = new HashSet();

    /** The dependency member classes. */
    public final Set<Class> anonymous = new HashSet();

    /**
     * Hide constcutor.
     */
    private Reincarnation(Class clazz) {
        this.clazz = Objects.requireNonNull(clazz);
    }

    /**
     * Compile the specified class.
     * 
     * @param coder A target class to compile.
     */
    public void rebirth(Coder coder) {
        coder.write(this);
    }

    /**
     * Add dependency type of this source.
     * 
     * @param dependency
     */
    public void require(Class dependency) {
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
                anonymous.add(dependency);
            } else {
                classes.add(dependency);
            }
        }
    }

    /**
     * Decompile the target {@link Class}.
     * 
     * @param clazz A class to decompile.
     * @return Chainable API.
     */
    public static final synchronized Reincarnation exhume(Class clazz) {
        return cache.computeIfAbsent(clazz, key -> {
            Reincarnation reincarnation = new Reincarnation(clazz);

            try {
                new ClassReader(clazz.getName()).accept(new JavaClassDecompiler(reincarnation), 0);
            } catch (IOException e) {
                throw I.quiet(e);
            }

            return reincarnation;
        });
    }

    /**
     * Decompile the specified class as Java code.
     * 
     * @param clazz A target class to decompile.
     * @return A decompiled Java source code.
     */
    public static final String rebirth(Class clazz) {
        JavaCoder coder = new JavaCoder();
        exhume(clazz).rebirth(coder);
        return coder.toString();
    }

    /**
     * Decompile the specified class as Java code.
     * 
     * @param clazz A target class to decompile.
     * @return A decompiled Java source code.
     */
    public static final String rebirth(Class clazz, JavaCodingOption options) {
        JavaCoder coder = new JavaCoder();
        coder.config(options);

        exhume(clazz).rebirth(coder);
        return coder.toString();
    }
}
