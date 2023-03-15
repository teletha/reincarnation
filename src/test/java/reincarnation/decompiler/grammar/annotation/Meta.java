/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.grammar.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import kiss.I;

public class Meta {

    /**
     * Find {@link Mark} annotation.
     * 
     * @param target
     */
    public static Mark findTypeMark(Class<?> target) {
        return findType(target, Mark.class);
    }

    /**
     * Find {@link Mark} annotation.
     * 
     * @param target
     */
    public static <A extends Annotation> A findType(Class<?> target, Class<A> annotationType) {
        return target.getDeclaredAnnotation(annotationType);
    }

    /**
     * Find {@link Mark} annotation.
     * 
     * @param target
     */
    public static Mark findConstructorMark(Class target) {
        for (Constructor constructor : target.getDeclaredConstructors()) {
            Mark mark = constructor.getDeclaredAnnotation(Mark.class);
            if (mark != null) {
                return mark;
            }
        }
        throw new Error("Not found");
    }

    /**
     * Find {@link Mark} annotation.
     * 
     * @param target
     */
    public static Mark findParameterMark(Class target) {
        try {
            for (Method m : target.getDeclaredMethods()) {
                for (Parameter p : m.getParameters()) {
                    Mark mark = p.getDeclaredAnnotation(Mark.class);
                    if (mark != null) {
                        return mark;
                    }
                }
            }
            throw new Error("Not found");
        } catch (Exception e) {
            throw I.quiet(e);
        }
    }

    /**
     * Find {@link Mark} annotation.
     * 
     * @param target
     */
    public static Mark findMethodMark(Class target) {
        try {
            for (Method method : target.getDeclaredMethods()) {
                Mark mark = method.getDeclaredAnnotation(Mark.class);
                if (mark != null) {
                    return mark;
                }
            }
            throw new Error("Not found");
        } catch (Exception e) {
            throw I.quiet(e);
        }
    }

    /**
     * Find {@link Mark} annotation.
     * 
     * @param target
     */
    public static Mark findFieldMark(Class target) {
        try {
            for (Field field : target.getDeclaredFields()) {
                Mark mark = field.getDeclaredAnnotation(Mark.class);
                if (mark != null) {
                    return mark;
                }
            }
            throw new Error("Not found");
        } catch (Exception e) {
            throw I.quiet(e);
        }
    }
}
