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

import java.util.EnumSet;
import java.util.Objects;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;

/**
 * @version 2018/10/03 11:50:57
 */
class JavaClassDecompiler extends ClassVisitor {

    /** The current processing source. */
    private final JavaSourceCode source;

    /** The compilation unit. */
    private final CompilationUnit unit;

    /** The top level class. */
    private final ClassOrInterfaceDeclaration root;

    /** The target class to decompile. */
    private final ClassOrInterfaceDeclaration decompile;

    /**
     * Java class decompiler
     * 
     * @param clazz A target class.
     * @param unit The compilation unit.
     */
    JavaClassDecompiler(JavaSourceCode source, CompilationUnit unit) {
        super(ASM7);

        this.source = Objects.requireNonNull(source);
        this.unit = Objects.requireNonNull(unit);

        if (source.clazz != source.root) {
            // member, local or anonymous class must be written in top level class
            root = unit.addClass(source.root.getSimpleName());

            String name = source.clazz.getName();

            if (source.clazz.isAnonymousClass()) {
                name = name.substring(name.lastIndexOf(".") + 1);
            } else {
                name = source.clazz.getSimpleName();
            }

            decompile = new ClassOrInterfaceDeclaration();
            decompile.setName(name);

            root.addMember(decompile);
        } else {
            decompile = root = unit.addClass(source.clazz.getSimpleName());
        }
        decompile.setModifiers(modifier(source.clazz.getModifiers()));
        decompile.setInterface(source.clazz.isInterface());
    }

    /**
     * Build modifiers from Java's modifier.
     * 
     * @param modifier
     * @return
     */
    private EnumSet<Modifier> modifier(int modifier) {
        EnumSet<Modifier> set = EnumSet.noneOf(Modifier.class);

        if (java.lang.reflect.Modifier.isAbstract(modifier)) {
            set.add(Modifier.ABSTRACT);
        }
        if (java.lang.reflect.Modifier.isFinal(modifier)) {
            set.add(Modifier.FINAL);
        }
        if (java.lang.reflect.Modifier.isNative(modifier)) {
            set.add(Modifier.NATIVE);
        }
        if (java.lang.reflect.Modifier.isPrivate(modifier)) {
            set.add(Modifier.PRIVATE);
        }
        if (java.lang.reflect.Modifier.isProtected(modifier)) {
            set.add(Modifier.PROTECTED);
        }
        if (java.lang.reflect.Modifier.isPublic(modifier)) {
            set.add(Modifier.PUBLIC);
        }
        if (java.lang.reflect.Modifier.isStatic(modifier)) {
            set.add(Modifier.STATIC);
        }
        return set;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        Debugger.start(source.clazz);

        for (String interfaceName : interfaces) {
            decompile.addImplementedType(load(interfaceName));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        decompile.addField(load(Type.getType(desc)), name, modifiers(access));

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        // ignore compiler generated method (e.g. generics)
        if ((access & (ACC_BRIDGE)) != 0) {
            return null;
        }

        Type type = Type.getType(desc);
        Type returnType = type.getReturnType();
        Type[] parameterTypes = type.getArgumentTypes();
        boolean isStatic = (access & ACC_STATIC) != 0;
        LocalVariables locals = new LocalVariables(source.clazz, isStatic, parameterTypes);

        BlockStmt block;

        if (name.equals("<init>")) {
            // initializer or constructor
            name = source.clazz.getSimpleName();

            ConstructorDeclaration constructor = decompile.addConstructor(modifiers(access));
            constructor.setParameters(locals.parameters);

            block = constructor.getBody();
        } else if (name.equals("<clinit>")) {
            // static initializer
            InitializerDeclaration initializer = new InitializerDeclaration(true, new BlockStmt());
            decompile.addMember(initializer);

            block = initializer.getBody();
        } else {
            MethodDeclaration method = decompile.addMethod(name, modifiers(access)).setType(load(returnType));
            method.setParameters(locals.parameters);

            block = method.getBody().get();
        }

        return new JavaMethodDecompiler(source, block, locals, returnType);
    }

    // /**
    // * {@inheritDoc}
    // */
    // @Override
    // public void visitInnerClass(String name, String outerName, String innerName, int access) {
    // if (outerName != null) {
    // Class outer = load(outerName);
    // if (outer == clazz.getEnclosingClass()) {
    // Class inner = load(name);
    //
    // if (inner != clazz) {
    // JavaSourceCode source = Reincarnation.exhume(inner);
    // System.out.println(inner.getName());
    // System.out.println(source.build().);
    // root.addMember(source.build().getClassByName(inner.getSimpleName()).get());
    // }
    // }
    // }
    // super.visitInnerClass(name, outerName, innerName, access);
    // }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitEnd() {
        Debugger.finish(source.clazz);
    }
}
