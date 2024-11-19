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

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import reincarnation.coder.AnnotationLike;

class JavaAnnotationDecompiler extends AnnotationVisitor {

    /** The metadata register. */
    private final BiConsumer<String, Object> register;

    JavaAnnotationDecompiler(AnnotationLike meta) {
        this(meta.values::put);
    }

    JavaAnnotationDecompiler(BiConsumer<String, Object> register) {
        super(Opcodes.ASM9);
        this.register = register;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(String name, Object value) {
        if (value instanceof Type type) {
            value = OperandUtil.load(type);
        }
        register.accept(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnnotationVisitor visitAnnotation(String name, String descriptor) {
        AnnotationLike sub = new AnnotationLike(OperandUtil.load(descriptor));
        register.accept(name, sub);
        return new JavaAnnotationDecompiler(sub);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnnotationVisitor visitArray(String name) {
        List container = new ArrayList();
        register.accept(name, container);

        return new JavaAnnotationDecompiler((k, v) -> container.add(v));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitEnum(String name, String descriptor, String value) {
        Object[] constants = OperandUtil.load(descriptor).getEnumConstants();
        for (Object constant : constants) {
            if (constant instanceof Enum e && e.name().equals(value)) {
                register.accept(name, constant);
            }
        }
    }
}
