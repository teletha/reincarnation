/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import kiss.I;
import reincarnation.JavaSourceCode.JavaMemberSourceCode;

/**
 * {@link Reincarnation} is a unit of decompilation.
 * 
 * @version 2018/10/14 17:15:33
 */
public abstract class Reincarnation<Self extends Reincarnation> {

    /** The cache. */
    private static final Map<Class, JavaSourceCode> cache = new ConcurrentHashMap();

    /** The required codes. */
    private final Set<JavaSourceCode> codes = new HashSet();

    /**
     * Hide constcutor.
     */
    protected Reincarnation() {
    }

    /**
     * Decompile the target {@link Class}.
     * 
     * @param clazz A class to decompile.
     * @return Chainable API.
     */
    public final Self exhume(Class clazz) {
        unearth(clazz);
        return (Self) this;
    }

    /**
     * Decompile the target {@link Class}. (Internal API)
     * 
     * @param clazz A class to decompile.
     * @return Chainable API.
     */
    static final JavaSourceCode unearth(Class clazz) {
        JavaSourceCode code = cache.get(clazz);

        if (code == null) {
            if (clazz.isAnonymousClass() || clazz.isLocalClass() || clazz.isMemberClass()) {
                code = new JavaMemberSourceCode(clazz);
            } else {
                code = new JavaSourceCode(clazz);
            }
            cache.put(clazz, code);
        }
        return code;
    }

    /**
     * Compile the specified class.
     * 
     * @param clazz A target class to compile.
     * @param output An output.
     */
    protected abstract void reborn(JavaSourceCode code, Appendable output);

    /**
     * Decompile all classes.
     * 
     * @param directory A root directory which contains all decompiled sources.
     */
    public void rebirth(Path directory) {
        Objects.requireNonNull(directory);

        try {
            if (Files.isDirectory(directory) == false) {
                Files.createDirectories(directory);
            }
        } catch (IOException e) {
            throw I.quiet(e);
        }
    }

    /**
     * Compile the specified class.
     * 
     * @param clazz A target class to compile.
     * @param output An output.
     */
    public final void rebirth(Class clazz, Appendable output) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(output);

        reborn(unearth(clazz), output);
    }

    /**
     * Compile the specified class.
     * 
     * @param clazz A target class to compile.
     * @param output An output.
     */
    public final void rebirth(Class clazz, Path output) {
        Objects.requireNonNull(output);

        try {
            rebirth(clazz, Files.newBufferedWriter(output));
        } catch (IOException e) {
            throw I.quiet(e);
        }
    }

    /**
     * Compile the specified class.
     * 
     * @param clazz A target class to compile.
     * @return An output as text code.
     */
    public final String rebirth(Class clazz) {
        StringBuilder builder = new StringBuilder();
        rebirth(clazz, builder);
        return builder.toString();
    }
}
