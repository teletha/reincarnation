/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;

import reincarnation.coder.Code;

/**
 * Provide the mock API for compiler generated code.
 */
public class GeneratedRecordCodes {

    /**
     * Check whether the given constructor is generated code or not.
     * 
     * @param constructor
     * @return
     */
    public static boolean isGenerated(Constructor constructor, Code<Code> code) {
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
    public static boolean isGenerated(Method method, Code<Code> code) {
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
    public static boolean isGenerated(Field field) {
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
