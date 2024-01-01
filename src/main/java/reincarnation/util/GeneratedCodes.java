/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.RecordComponent;

import reincarnation.coder.Code;

/**
 * Provide the mock API for compiler generated code.
 */
public class GeneratedCodes {

    /**
     * Check whether the given constructor is defined implicitly or not.
     * 
     * @param constructor
     * @return
     */
    public static boolean isImplicitConstructor(Constructor constructor, Code<Code> code) {
        if (constructor.getDeclaringClass().getDeclaredConstructors().length != 1) {
            return false;
        }

        if (constructor.getParameterCount() != 0) {
            return false;
        }

        if (3 < code.descendent().count().to().exact()) {
            return false;
        }
        return true;
    }

    /**
     * Check whether the given parameter is generated code or not.
     * 
     * @param parameter
     * @return
     */
    public static boolean isLocalParameter(Parameter parameter) {
        Executable exe = parameter.getDeclaringExecutable();
        Class owner = exe.getDeclaringClass();

        // Don't use Parameter#isImplicit.
        //
        // The detailed parameter information should not be used, as it cannot be obtained if
        // debugging information is not provided.
        if (Classes.isMemberLike(owner) && Classes.isNonStatic(owner) && owner.getEnclosingClass() == parameter.getType()) {
            Parameter[] parameters = exe.getParameters();
            if (parameters[0] == parameter) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the given constructor is generated code or not.
     * 
     * @param constructor
     * @return
     */
    public static boolean isEnumConstructor(Constructor constructor, Code<Code> code) {
        if (constructor.getDeclaringClass().isEnum()) {
            if (code.descendent().count().to().exact() <= 5) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the given parameter is generated code or not.
     * 
     * @param parameter
     * @return
     */
    public static boolean isEnumParameter(Parameter parameter) {
        Executable exe = parameter.getDeclaringExecutable();

        // Don't use Parameter#isSynthetic.
        //
        // The detailed parameter information should not be used, as it cannot be obtained if
        // debugging information is not provided.
        if (exe.getDeclaringClass().isEnum() && exe instanceof Constructor) {
            Parameter[] parameters = exe.getParameters();
            for (int i = 0; i < 2; i++) {
                if (parameters[i] == parameter) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check whether the given method is generated code or not.
     * 
     * @param method
     * @return
     */
    public static boolean isEnumMethod(Method method, Code<Code> code) {
        if (!method.getDeclaringClass().isEnum()) {
            return false;
        }

        String name = method.getName();
        Class[] params = method.getParameterTypes();

        if (name.equals("values") && params.length == 0) {
            return true;
        } else if (name.equals("valueOf") && params.length == 1 && params[0] == String.class) {
            return true;
        }
        return false;
    }

    /**
     * Check whether the given field is generated code or not.
     * 
     * @param field
     * @return
     */
    public static boolean isEnumField(Field field) {
        Class owner = field.getDeclaringClass();
        String name = field.getName();

        if (owner.isEnum()) {
            if (name.equals("ENUM$VALUES")) {
                return true;
            }

            for (Object constant : owner.getEnumConstants()) {
                Enum e = (Enum) constant;
                if (e.name().equals(name)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Check whether the given method is generated code or not.
     * 
     * @param method
     * @return
     */
    public static boolean isEnumSwitchMethod(Method method) {
        return method.isSynthetic() && isEnumSwitchName(method.getName());
    }

    /**
     * Check whether the given field is generated code or not.
     * 
     * @param field
     * @return
     */
    public static boolean isEnumSwitchField(Field field) {
        return field.isSynthetic() && isEnumSwitchName(field.getName());
    }

    /**
     * Helper method to detect special enum method.
     * 
     * @param name
     * @return
     */
    public static boolean isEnumSwitchName(String name) {
        // For Eclipse JDT compiler.
        if (name.startsWith("$SWITCH_TABLE$")) {
            return true;
        }

        // For JDK compiler.
        if (name.startsWith("$SwitchMap$")) {
            return true;
        }
        return false;
    }

    /**
     * Check whether the given constructor is generated code or not.
     * 
     * @param constructor
     * @return
     */
    public static boolean isRecordConstructor(Constructor constructor, Code<Code> code) {
        if (constructor.getDeclaringClass().isRecord()) {
            long count = code.descendent().count().to().exact();
            if (3 + constructor.getDeclaringClass().getRecordComponents().length * 4 == count) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the given method is generated code or not.
     * 
     * @param method
     * @return
     */
    public static boolean isRecordMethod(Method method, Code<Code> code) {
        if (!method.getDeclaringClass().isRecord()) {
            return false;
        }

        String name = method.getName();
        Class[] params = method.getParameterTypes();

        if (name.equals("toString") && params.length == 0) {
            return true;
        } else if (name.equals("hashCode") && params.length == 0) {
            return true;
        } else if (name.equals("equals") && params.length == 1 && params[0] == Object.class) {
            return true;
        } else if (params.length == 0) {
            for (RecordComponent component : method.getDeclaringClass().getRecordComponents()) {
                if (component.getName().equals(name)) {
                    long count = code.descendent().count().to().exact();
                    if (count == 5) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check whether the given field is generated code or not.
     * 
     * @param field
     * @return
     */
    public static boolean isRecordField(Field field) {
        Class owner = field.getDeclaringClass();
        String name = field.getName();

        if (!owner.isRecord()) {
            return false;
        }

        for (RecordComponent component : owner.getRecordComponents()) {
            if (component.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Bootstrap methods for state-driven implementations of core methods, including
     * {@link Object#equals(Object)} {@link Object#hashCode()}, and {@link Object#toString()}. These
     * methods may be used, for example, by Java compiler implementations to implement the bodies of
     * Object methods for record classes.
     * 
     * @param o
     * @return
     */
    public static native String recordToString(Object o);

    /**
     * Bootstrap methods for state-driven implementations of core methods, including
     * {@link Object#equals(Object)} {@link Object#hashCode()}, and {@link Object#toString()}. These
     * methods may be used, for example, by Java compiler implementations to implement the bodies of
     * Object methods for record classes.
     * 
     * @param o
     * @return
     */
    public static native boolean recordEquals(Object o, Object other);

    /**
     * Bootstrap methods for state-driven implementations of core methods, including
     * {@link Object#equals(Object)} {@link Object#hashCode()}, and {@link Object#toString()}. These
     * methods may be used, for example, by Java compiler implementations to implement the bodies of
     * Object methods for record classes.
     * 
     * @param o
     * @return
     */
    public static native int recordHashCode(Object o);
}