/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
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
import reincarnation.util.Classes;
import reincarnation.util.GeneratedCodes;

/**
 * {@link Reincarnation} is a unit of decompilation.
 */
public final class Reincarnation {

    /** The class loader to search type. */
    static final ThreadLocal<ClassLoader> LOADER = ThreadLocal.withInitial(ClassLoader::getSystemClassLoader);

    /** The cache. */
    static final Map<Class, Reincarnation> CACHE = new ConcurrentHashMap();

    /** The target class. */
    public final Class clazz;

    /** The initializer manager. */
    public final List<Code> staticInitializer = new ArrayList();

    /** The initializer manager. */
    public final List<Code> initializer = new ArrayList();

    /** The non-static field manager. */
    public final List<Field> fields = new ArrayList();

    /** The static field manager. */
    public final List<Field> staticFields = new ArrayList();

    /** The constructor manager. */
    public final Map<Constructor, Code> constructors = new LinkedHashMap();

    /** The method manager. */
    public final Map<Method, Code> methods = new LinkedHashMap();

    /** The member manager. */
    public final Map<Class, Reincarnation> members = new LinkedHashMap();

    /** The dependency classes. */
    public final Set<Class> classes = new LinkedHashSet();

    /** The dependency member classes. */
    public final Set<Class> anonymous = new LinkedHashSet();

    /** The dependency member classes. */
    public final Set<Class> locals = new LinkedHashSet();

    /**
     * Hide constcutor.
     */
    private Reincarnation(Class clazz) {
        this.clazz = Objects.requireNonNull(clazz);

        // Separate fields into static and non-static
        for (Field field : clazz.getDeclaredFields()) {
            if (Classes.isStatic(field)) {
                if (!GeneratedCodes.isEnumSwitchField(field)) {
                    staticFields.add(field);
                }
            } else {
                fields.add(field);
            }
        }
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

            if (dependency.isLocalClass()) {
                require(dependency.getSuperclass());
                require(dependency.getInterfaces());
                locals.add(dependency);
            } else if (dependency.getName().startsWith(clazz.getName().concat("$"))) {
                anonymous.add(dependency);
            } else {
                classes.add(dependency);
            }
        }
    }

    /**
     * Add dependency type of this source.
     * 
     * @param dependencies
     */
    public void require(Class[] dependencies) {
        if (dependencies != null) {
            for (Class dependency : dependencies) {
                require(dependency);
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
        return CACHE.computeIfAbsent(clazz, key -> {
            ClassLoader loader = key.getClassLoader();
            if (loader == null) {
                loader = ClassLoader.getSystemClassLoader();
            }
            LOADER.set(loader);

            Reincarnation reincarnation = new Reincarnation(key);

            try {
                InputStream input = loader.getResourceAsStream(key.getName().replace('.', '/') + ".class");
                new ClassReader(input).accept(new JavaClassDecompiler(reincarnation), 0);
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
     */
    public static final String rebirth(Class clazz) {
        return rebirth(clazz, null);
    }

    /**
     * Decompile the specified class as Java code.
     * 
     * @param clazz A target class to decompile.
     * @return A decompiled Java source code.
     */
    public static final String rebirth(Class clazz, JavaCodingOption options) {
        JavaCoder coder = new JavaCoder();
        coder.config(options == null ? new JavaCodingOption() : options);

        exhume(clazz).rebirth(coder);
        return coder.toString();
    }
}