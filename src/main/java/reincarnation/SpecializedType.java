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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;

import kiss.I;

public final class SpecializedType implements Type {

    /** The raw type. */
    private final Class raw;

    /** The SAM of the raw type. */
    private final Method sam;

    /** The original types. */
    private final Type[] original;

    /** The specialized types. */
    private final Type[] specialized;

    /**
     * Create {@link SpecializedType}.
     * 
     * @param samInterface A interface which has a single abstract method.
     */
    public SpecializedType(Class samInterface) {
        if (samInterface == null || !samInterface.isInterface()) {
            throw new Error(samInterface + " is not SAM interface.");
        }

        this.raw = samInterface;
        this.sam = I.signal(samInterface.getMethods()).skip(m -> m.isDefault() || Modifier.isStatic(m.getModifiers())).first().to().exact();
        this.original = samInterface.getTypeParameters();
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
            if (type == null || (type instanceof Class clazz && clazz.getTypeParameters().length != 0)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Specialize the {@link TypeVariable}.
     * 
     * @param original
     * @param specialized
     * @return Chainable API.
     */
    private SpecializedType specialize(Type original, Type specialized) {
        for (int i = 0; i < this.original.length; i++) {
            if (this.original[i].equals(original)) {
                this.specialized[i] = specialized;
            }
        }
        return this;
    }

    /**
     * Specialize the {@link TypeVariable}.
     * 
     * @param original
     * @param specialized
     * @return Chainable API.
     */
    private SpecializedType specialize(Type[] original, Type[] specialized) {
        for (int i = 0; i < original.length && i < specialized.length; i++) {
            specialize(original[i], specialized[i]);
        }
        return this;
    }

    /**
     * Specialize by the method.
     * 
     * @param specialized
     * @return Chainable API.
     */
    SpecializedType specializeByReturnAndParamTypes(org.objectweb.asm.Type specialized) {
        return specializeByReturnType(specialized.getReturnType()).specializeByParamTypes(specialized.getArgumentTypes());
    }

    /**
     * Specialize by the method.
     * 
     * @param specialized
     * @return Chainable API.
     */
    SpecializedType specializeByReturnAndParameterTypes(Method specialized) {
        return specializeByReturnType(specialized.getGenericReturnType()).specializeByParamTypes(specialized.getGenericParameterTypes());
    }

    /**
     * Specialize by the return type.
     * 
     * @param specialized
     * @return Chainable API.
     */
    SpecializedType specializeByReturnType(Type specialized) {
        return specialize(sam.getGenericReturnType(), specialized);
    }

    /**
     * Specialize by the parameter type.
     * 
     * @param specialized
     * @return Chainable API.
     */
    SpecializedType specializeByReturnType(org.objectweb.asm.Type specialized) {
        return specializeByReturnType(OperandUtil.load(specialized));
    }

    /**
     * Specialize by the parameter type.
     * 
     * @param specialized
     * @return Chainable API.
     */
    SpecializedType specializeByParamTypes(Type[] specialized) {
        return specialize(sam.getGenericParameterTypes(), specialized);
    }

    /**
     * Specialize by the return type.
     * 
     * @param specialized
     * @return Chainable API.
     */
    SpecializedType specializeByParamTypes(org.objectweb.asm.Type[] specialized) {
        return specializeByParamTypes(OperandUtil.load(specialized));
    }

    /**
     * Specialize by SAM.
     * 
     * @param specialized
     * @return Chainable API.
     */
    SpecializedType specializeBySAM(Method specialized) {
        System.out.println(specialized.getGenericReturnType() + "   " + Arrays.toString(specialized.getParameterTypes()));
        System.out.println(specialized + "   " + raw + "   " + Arrays.toString(this.specialized));
        return this;
    }

    /**
     * Returns an array of Type objects representing the actual type arguments to this type. Note
     * that in some cases, the returned array be empty. This can occur if this type represents a
     * non-parameterized type nested within a parameterized type.
     * 
     * @return An array of Type objects representing the actual type arguments to this type.
     */
    public Type[] getActualTypeArguments() {
        return specialized;
    }

    /**
     * Returns the Type object representing the class or interface that declared this type.
     * 
     * @return Type object representing the class or interface that declared this type
     */
    public Type getRawType() {
        return raw;
    }
}