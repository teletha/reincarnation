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

import org.objectweb.asm.Type;

import com.github.javaparser.ast.Modifier;

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
