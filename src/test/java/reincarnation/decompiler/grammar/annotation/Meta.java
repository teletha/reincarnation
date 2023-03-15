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

import kiss.I;

public class Meta {

    /**
     * Find annotation by the given type.
     * 
     * @param <A>
     * @param target
     * @param type
     */
    public static <A extends Annotation> A find(Class target, Class<A> type) {
        return (A) target.getDeclaredAnnotation(type);
    }

    /**
     * Find {@link Mark} annotation.
     * 
     * @param target
     */
    public static Mark findMark(Class target) {
        return find(target, Mark.class);
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
    public static Mark findMark(Class target, String memberName) {
        try {
            Method method = target.getDeclaredMethod(memberName);
            Mark mark = method.getDeclaredAnnotation(Mark.class);
            if (mark != null) {
                return mark;
            }

            throw new Error("Not found");
        } catch (Exception e) {
            try {
                Field field = target.getDeclaredField(memberName);
                Mark mark = field.getDeclaredAnnotation(Mark.class);
                if (mark != null) {
                    return mark;
                }

                throw new Error("Not found");
            } catch (Exception x) {
                throw I.quiet(x);
            }
        }
    }
}
