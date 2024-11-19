/*
 * Copyright (C) 2024 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;

import reincarnation.meta.AnnotationMeta;
import reincarnation.meta.AnnotationsMeta;

public class JavaAnnotationDecompiler extends AnnotationVisitor {

    /** The metadata holder. */
    private final AnnotationMeta meta;

    public JavaAnnotationDecompiler(Class clazz, AnnotationsMeta meta) {
        super(Opcodes.ASM9);

        System.out.println(clazz);
        this.meta = new AnnotationMeta(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(String name, Object value) {
        super.visit(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnnotationVisitor visitAnnotation(String name, String descriptor) {
        return super.visitAnnotation(name, descriptor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnnotationVisitor visitArray(String name) {
        return super.visitArray(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitEnum(String name, String descriptor, String value) {
        super.visitEnum(name, descriptor, value);
    }
}
