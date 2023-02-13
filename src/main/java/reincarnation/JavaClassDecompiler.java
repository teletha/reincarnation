/*
 * Copyright (C) 2023 The REINCARNATION Development Team
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

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import kiss.I;

/**
 * @version 2018/10/13 17:24:11
 */
class JavaClassDecompiler extends ClassVisitor {

    /** The current processing source. */
    private final Reincarnation source;

    /**
     * Java class decompiler
     */
    JavaClassDecompiler(Reincarnation source) {
        super(ASM9);

        this.source = Objects.requireNonNull(source);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        Debugger.current().start(source.clazz);

        source.require(Util.load(superName));
        for (String i : interfaces) {
            source.require(Util.load(i));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        source.require(Util.load(Type.getType(desc)));
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
        JavaMethodDecompiler decompiler;

        try {
            if (name.equals("<init>")) {
                // initializer or constructor
                Constructor constructor = source.clazz.getDeclaredConstructor(load(parameterTypes));
                LocalVariables locals = new LocalVariables(source.clazz, isStatic, parameterTypes, constructor.getParameters());
                decompiler = new JavaMethodDecompiler(source, locals, returnType, constructor);

                source.constructors.put(constructor, decompiler);
            } else if (name.equals("<clinit>")) {
                LocalVariables locals = new LocalVariables(source.clazz, isStatic, parameterTypes, new Parameter[0]);
                decompiler = new JavaMethodDecompiler(source, locals, returnType, null);

                // static initializer
                source.staticInitializer.add(decompiler);
            } else {
                Method method = source.clazz.getDeclaredMethod(name, load(parameterTypes));
                LocalVariables locals = new LocalVariables(source.clazz, isStatic, parameterTypes, method.getParameters());
                decompiler = new JavaMethodDecompiler(source, locals, returnType, method);

                source.methods.put(method, decompiler);
            }
        } catch (Exception e) {
            throw I.quiet(e);
        }

        return decompiler;
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
        Debugger.current().finish(source.clazz);
    }
}