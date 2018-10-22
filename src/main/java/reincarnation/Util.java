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

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * @version 2018/10/22 18:08:19
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
        case IAND:
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
        case LAND:
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

        case ASTORE:
            return Object.class;

        default:
            // If this exception will be thrown, it is bug of this program. So we must rethrow the
            // wrapped error in here.
            throw new Error(opecode + " is unknow opcode.");
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
     * Load as {@link Class}.
     * 
     * @param types
     * @return
     */
    static Class[] load(Type[] types) {
        Class[] classes = new Class[types.length];

        for (int i = 0; i < classes.length; i++) {
            classes[i] = load(types[i]);
        }
        return classes;
    }

    /**
     * Retrieve the default value operand for the type.
     * 
     * @param type
     * @return
     */
    static Operand defaultValueFor(Class type) {
        if (type == int.class) {
            return new OperandNumber(0);
        }
        if (type == long.class) {
            return new OperandNumber(0L);
        }
        if (type == float.class) {
            return new OperandNumber(0F);
        }
        if (type == double.class) {
            return new OperandNumber(0D);
        }
        if (type == char.class) {
            // If this exception will be thrown, it is bug of this program. So we must rethrow the
            // wrapped error in here.
            throw new Error();
        }
        if (type == byte.class) {
            return new OperandNumber((byte) 0);
        }
        if (type == short.class) {
            return new OperandNumber((short) 0);
        }
        if (type == boolean.class) {
            return new OperandBoolean(false);
        }
        return Operand.Null;
    }
}
