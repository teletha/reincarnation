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

import org.objectweb.asm.ClassReader;

import com.github.javaparser.ast.CompilationUnit;

import kiss.I;

/**
 * @version 2018/10/03 8:20:01
 */
public class Reincarnation {

    /**
     * Decompile the target {@link Class}.
     * 
     * @param clazz
     */
    public static final CompilationUnit exhume(Class clazz) {
        try {
            String name = clazz.getName();
            CompilationUnit root = new CompilationUnit(clazz.getPackageName());

            if (clazz.isAnonymousClass()) {
                name = name.substring(name.lastIndexOf(".") + 1);
            }

            ClassReader reader = new ClassReader(clazz.getName());
            reader.accept(new JavaClassDecompiler(root.addClass(name)), ClassReader.SKIP_DEBUG);

            return root;
        } catch (IOException e) {
            throw I.quiet(e);
        }
    }
}
