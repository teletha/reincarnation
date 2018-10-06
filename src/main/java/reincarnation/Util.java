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
import static org.objectweb.asm.Type.*;

import java.lang.reflect.Array;
import java.util.EnumSet;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

/**
 * @version 2018/10/04 8:31:09
 */
public class Util {

    /**
     * Load {@link Class} by internal name.
     * 
     * @param internalName
     * @return
     */
    static Class load(String internalName) {
        return load(Type.getObjectType(internalName));
    }

    /**
     * Load {@link Class} by {@link Opcodes}.
     * 
     * @param internalName
     * @return
     */
    static Class load(int opecode) {
        switch (opecode) {
        case IADD:
        case ICONST_0:
        case ICONST_1:
        case ICONST_2:
        case ICONST_3:
        case ICONST_4:
        case ICONST_5:
        case ICONST_M1:
        case IDIV:
        case ILOAD:
        case IMUL:
        case INEG:
        case IOR:
        case IREM:
        case IRETURN:
        case ISHL:
        case ISHR:
        case ISTORE:
        case ISUB:
        case IUSHR:
        case IXOR:
            return int.class;

        case LADD:
        case LCONST_0:
        case LCONST_1:
        case LDIV:
        case LLOAD:
        case LMUL:
        case LNEG:
        case LOR:
        case LREM:
        case LRETURN:
        case LSHL:
        case LSHR:
        case LSTORE:
        case LSUB:
        case LUSHR:
        case LXOR:
            return long.class;

        case FADD:
        case FCONST_0:
        case FCONST_1:
        case FCONST_2:
        case FDIV:
        case FLOAD:
        case FMUL:
        case FNEG:
        case FREM:
        case FRETURN:
        case FSTORE:
        case FSUB:
            return float.class;

        case DADD:
        case DCONST_0:
        case DCONST_1:
        case DDIV:
        case DLOAD:
        case DMUL:
        case DNEG:
        case DREM:
        case DRETURN:
        case DSTORE:
        case DSUB:
            return double.class;

        default:
            // If this exception will be thrown, it is bug of this program. So we must rethrow the
            // wrapped error in here.
            throw new Error();
        }
    }

    /**
     * Load {@link Class} by internal type.
     * 
     * @param internalName
     * @return
     */
    static Class load(Type internalType) {
        switch (internalType.getSort()) {
        case INT:
            return int.class;

        case Type.LONG:
            return long.class;

        case Type.FLOAT:
            return float.class;

        case Type.DOUBLE:
            return double.class;

        case CHAR:
            return char.class;

        case BYTE:
            return byte.class;

        case SHORT:
            return short.class;

        case BOOLEAN:
            return boolean.class;

        case VOID:
            return void.class;

        case ARRAY:
            return Array.newInstance(load(internalType.getElementType()), new int[internalType.getDimensions()]).getClass();

        default:
            try {
                return Class.forName(internalType.getClassName(), false, ClassLoader.getSystemClassLoader());
            } catch (ClassNotFoundException e) {
                // If this exception will be thrown, it is bug of this program. So we must
                // rethrow the wrapped error in here.
                throw new Error(e);
            }
        }
    }

    /**
     * Load {@link Class} by parser type.
     * 
     * @param type
     * @return
     */
    static Class load(com.github.javaparser.ast.type.Type type) {
        if (type.isPrimitiveType()) {
            switch (type.asPrimitiveType().getType()) {
            case BOOLEAN:
                return boolean.class;
            case BYTE:
                return byte.class;
            case CHAR:
                return char.class;
            case DOUBLE:
                return double.class;
            case FLOAT:
                return float.class;
            case INT:
                return int.class;
            case LONG:
                return long.class;
            case SHORT:
                return short.class;
            }
        }

        try {
            return Class.forName(type.toString(), false, ClassLoader.getSystemClassLoader());
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Load {@link ClassOrInterfaceType} by internal name.
     * 
     * @param internalName
     * @return
     */
    static ClassOrInterfaceType loadType(String internalName) {
        return loadType(Type.getObjectType(internalName));
    }

    /**
     * Load {@link ClassOrInterfaceType} by internal name.
     * 
     * @param internalName
     * @return
     */
    static ClassOrInterfaceType loadType(Class type) {
        return JavaParser.parseClassOrInterfaceType(type.getCanonicalName());
    }

    /**
     * Load {@link ClassOrInterfaceType} by internal type.
     * 
     * @param internalName
     * @return
     */
    static ClassOrInterfaceType loadType(Type internalType) {
        return JavaParser.parseClassOrInterfaceType(internalType.getClassName());
    }

    /**
     * Compute modifiers from ASM access code.
     * 
     * @param access
     * @return
     */
    static Modifier[] modifiers(int access) {
        EnumSet<Modifier> set = EnumSet.noneOf(Modifier.class);

        if ((access & ACC_ABSTRACT) != 0) {
            set.add(Modifier.ABSTRACT);
        }
        if ((access & ACC_FINAL) != 0) {
            set.add(Modifier.FINAL);
        }
        if ((access & ACC_NATIVE) != 0) {
            set.add(Modifier.NATIVE);
        }
        if ((access & ACC_PRIVATE) != 0) {
            set.add(Modifier.PRIVATE);
        }
        if ((access & ACC_PROTECTED) != 0) {
            set.add(Modifier.PROTECTED);
        }
        if ((access & ACC_PUBLIC) != 0) {
            set.add(Modifier.PUBLIC);
        }
        if ((access & ACC_STATIC) != 0) {
            set.add(Modifier.STATIC);
        }
        if ((access & ACC_STRICT) != 0) {
            set.add(Modifier.STRICTFP);
        }
        if ((access & ACC_SYNCHRONIZED) != 0) {
            set.add(Modifier.SYNCHRONIZED);
        }
        if ((access & ACC_TRANSIENT) != 0) {
            set.add(Modifier.TRANSIENT);
        }
        if ((access & ACC_TRANSITIVE) != 0) {
            set.add(Modifier.TRANSITIVE);
        }
        if ((access & ACC_VOLATILE) != 0) {
            set.add(Modifier.VOLATILE);
        }
        return set.toArray(Modifier[]::new);
    }
}