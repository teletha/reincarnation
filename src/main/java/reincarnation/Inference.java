/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import kiss.I;

public class Inference {

    /**
     * Test whether the specified type is instance of the required class or not.
     * 
     * @param target A target type to test.
     * @param required A required type.
     * @return
     */
    static boolean instanceOf(Type target, Class required) {
        if (target == null || required == null) {
            return false;
        }

        if (target instanceof Class clazz) {
            return required.isAssignableFrom(clazz);
        } else if (target instanceof ParameterizedType parameterized) {
            return instanceOf(parameterized.getRawType(), required);
        } else if (target instanceof WildcardType wild) {
            for (Type upper : wild.getUpperBounds()) {
                if (!instanceOf(upper, required)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static ParameterizedType embody(ParameterizedType parameterized, List<Operand> params) {
        return new ParameterizedType() {

            @Override
            public Type getRawType() {
                return parameterized.getRawType();
            }

            @Override
            public Type getOwnerType() {
                return parameterized.getOwnerType();
            }

            @Override
            public Type[] getActualTypeArguments() {
                return params.stream().map(x -> x.type.v).toArray(Type[]::new);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                return getRawType() + "(" + Arrays.toString(getActualTypeArguments()) + ")";
            }
        };
    }

    /**
     * Specialized the return type of the specified method.
     * 
     * @param method
     * @return
     */
    public static Type specialize(Method method, Type owner, List<Operand> parameters) {
        Type definedReturnType = method.getGenericReturnType();

        if (definedReturnType instanceof Class) {
            return definedReturnType;
        }

        if (definedReturnType instanceof TypeVariable variable && owner instanceof ParameterizedType parameterized) {
            GenericDeclaration declaration = variable.getGenericDeclaration();
            TypeVariable<?>[] declaredParameters = declaration.getTypeParameters();

            for (int i = 0; i < declaredParameters.length; i++) {
                if (declaredParameters[i].equals(variable)) {
                    return parameterized.getActualTypeArguments()[i];
                }
            }
        }

        if (definedReturnType instanceof ParameterizedType parameterized) {
            // Type[] args = parameterized.getActualTypeArguments();
            // for (int i = 0; i < args.length; i++) {
            // if (args[i] instanceof TypeVariable variable) {
            // GenericDeclaration declaration = variable.getGenericDeclaration();
            // System.out.println(i + " " + args[i] + " " + parameters
            // .size() + " " + method + " " + declaration + " " + declaration.getClass());
            // args[i] = parameters.get(i).type.v;
            // }
            // }
            // return parameterized;
        }

        return method.getReturnType();
    }

    /**
     * Specialized the given interface type.
     * 
     * @param sam
     * @param returnType
     * @param parameterType
     * @return
     */
    public static SpecializedType specialize(Class sam, Type returnType, Type[] parameterType) {
        if (!sam.isInterface()) {
            throw new Error();
        }

        Method method = findSAM(sam);
        return new SpecializedType(sam).assign(method.getGenericReturnType(), returnType);
    }

    private static Method findSAM(Class sam) {
        return I.signal(sam.getMethods()).skip(m -> m.isDefault() || Modifier.isStatic(m.getModifiers())).first().to().exact();
    }

    /**
     * 
     */
    public static class SpecializedType implements Type {

        /** The raw type. */
        private final Class raw;

        /** The original types. */
        private final Type[] original;

        /** The specialized types. */
        private final Type[] specialized;

        /**
         * @param original
         */
        private SpecializedType(Class type) {
            this.raw = Objects.requireNonNull(type);
            this.original = Objects.requireNonNull(type.getTypeParameters());
            this.specialized = new Type[original.length];

            for (int i = 0; i < original.length; i++) {
                if (original[i] == Class.class) {
                    specialized[i] = original[i];
                }
            }
        }

        /**
         * Check state.
         * 
         * @return
         */
        private boolean isCompleted() {
            for (Type type : specialized) {
                if (type == null) {
                    return false;
                }
            }
            return true;
        }

        private SpecializedType assign(Type original, Type specialized) {
            for (int i = 0; i < this.original.length; i++) {
                if (this.original[i].equals(original)) {
                    this.specialized[i] = specialized;
                }
            }
            return this;
        }

        public Type[] getActualTypeArguments() {
            return specialized;
        }

        public Type getRawType() {
            return raw;
        }
    }
}
