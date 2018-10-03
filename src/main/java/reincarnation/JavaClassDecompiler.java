/*
 * Copyright (C) 2016 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import static org.objectweb.asm.Opcodes.*;
import static reincarnation.Util.*;

import java.util.Objects;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

/**
 * @version 2018/10/03 11:50:57
 */
class JavaClassDecompiler extends ClassVisitor {

    private ClassOrInterfaceDeclaration root;

    /**
     * Java class decompiler
     */
    JavaClassDecompiler(ClassOrInterfaceDeclaration root) {
        super(ASM7);

        this.root = Objects.requireNonNull(root);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        for (String interfaceName : interfaces) {
            root.addImplementedType(load(interfaceName));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        CallableDeclaration declaration;

        if (name.equals("<init>")) {
            // initializer or constructor
            name = root.getNameAsString();

            declaration = root.addConstructor(modifiers(access));
        } else {
            declaration = root.addMethod(name, modifiers(access)).setType(load(Type.getType(desc).getReturnType()));
        }

        // static modifier
        boolean isStatic = (access & ACC_STATIC) != 0;

        return new JavaMethodDecompiler(declaration, name, desc, isStatic);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitEnd() {
    }
}
