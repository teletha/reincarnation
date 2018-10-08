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

import static org.objectweb.asm.Opcodes.*;
import static reincarnation.Util.*;

import java.util.Objects;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;

/**
 * @version 2018/10/03 11:50:57
 */
class JavaClassDecompiler extends ClassVisitor {

    /** The target class. */
    private final Class clazz;

    private final ClassOrInterfaceDeclaration root;

    /**
     * Java class decompiler
     * 
     * @param clazz A target class.
     * @param root AST root.
     */
    JavaClassDecompiler(Class clazz, ClassOrInterfaceDeclaration root) {
        super(ASM7);

        this.clazz = Objects.requireNonNull(clazz);
        this.root = Objects.requireNonNull(root);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        Debugger.start(clazz);

        for (String interfaceName : interfaces) {
            root.addImplementedType(load(interfaceName));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        root.addField(load(Type.getType(desc)), name, modifiers(access));

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        Type type = Type.getType(desc);
        Type returnType = type.getReturnType();
        Type[] parameterTypes = type.getArgumentTypes();
        boolean isStatic = (access & ACC_STATIC) != 0;
        LocalVariables locals = new LocalVariables(clazz, isStatic, parameterTypes);

        BlockStmt block;

        if (name.equals("<init>")) {
            // initializer or constructor
            name = clazz.getSimpleName();

            ConstructorDeclaration constructor = root.addConstructor(modifiers(access));
            constructor.setParameters(locals.parameters);

            block = constructor.getBody();
        } else if (name.equals("<clinit>")) {
            // static initializer
            InitializerDeclaration initializer = new InitializerDeclaration(true, new BlockStmt());
            root.addMember(initializer);

            block = initializer.getBody();
        } else {
            MethodDeclaration method = root.addMethod(name, modifiers(access)).setType(load(returnType));
            method.setParameters(locals.parameters);

            block = method.getBody().get();
        }

        return new JavaMethodDecompiler(clazz, block, locals, name, desc, isStatic);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitEnd() {
        Debugger.finish(clazz);
    }
}
