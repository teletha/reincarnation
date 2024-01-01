/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.List;
import java.util.Objects;

import kiss.I;
import reincarnation.coder.Join;

/**
 * {@link SpecializedType} represents a parameterized type that can be specialized with actual type
 * arguments.
 */
final class SpecializedType implements ParameterizedType {

    /** The reusable type for <?>. */
    private static final WildcardType WILD = new WildcardType() {

        private static final Type[] Φ = {};

        @Override
        public Type[] getUpperBounds() {
            return Φ;
        }

        @Override
        public Type[] getLowerBounds() {
            return Φ;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "?";
        }
    };

    /** The raw type. */
    private final Class raw;

    /** The original types. */
    private final Type[] original;

    /** The specialized types. */
    private final Type[] specialized;

    /** The SAM of the raw type. */
    private Method sam;

    /**
     * Create {@link SpecializedType}.
     * 
     * @param raw A base type.
     */
    SpecializedType(Class raw) {
        this.raw = Objects.requireNonNull(raw);
        this.original = raw.getTypeParameters();
        this.specialized = new Type[original.length];

        for (int i = 0; i < original.length; i++) {
            if (original[i] == Class.class) {
                specialized[i] = original[i];
            }
        }
    }

    /**
     * Find the single abstract method.
     * 
     * @return
     */
    private Method sam() {
        if (sam == null) {
            List<Method> methods = I.signal(raw.getMethods()).skip(m -> m.isDefault() || Modifier.isStatic(m.getModifiers())).toList();

            if (methods.size() != 1 && !raw.isInterface()) {
                throw new Error(raw + " is not SAM interface.");
            }
            sam = methods.get(0);
        }
        return sam;
    }

    /**
     * Set the single abstract method.
     * 
     * @param method
     * @return
     */
    private SpecializedType sam(Method method) {
        if (method != null) {
            sam = method;
        }
        return this;
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
     * Find the specialized type corresponding to the given original type.
     * 
     * @param originalType
     * @return
     */
    private Type findSpecialized(Type originalType) {
        for (int i = 0; i < this.original.length; i++) {
            if (this.original[i].equals(originalType)) {
                return specialized[i];
            }
        }
        return null;
    }

    /**
     * Find the specialized type corresponding to the given original type.
     * 
     * @param originalType
     * @return
     */
    private Type[] findSpecialized(Type[] originalTypes) {
        Type[] converted = new Type[originalTypes.length];
        for (int i = 0; i < originalTypes.length; i++) {
            converted[i] = findSpecialized(originalTypes[i]);
        }
        return converted;
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
        return specialize(sam().getGenericReturnType(), specialized);
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
        return specialize(sam().getGenericParameterTypes(), specialized);
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
        int samSize = countParameterAndReturn(sam());
        int specializedSize = countParameterAndReturn(specialized);
        if (samSize == specializedSize + 1) {
            // The first type argument of SAM must be the actual method owner class.
            Class<?> owner = specialized.getDeclaringClass();
            if (this.specialized[0] == owner) {
                // Check to see if the owner class needs to be type inferred.
                if (owner.getTypeParameters().length != 0) {
                    this.specialized[0] = new SpecializedType(owner).sam(specialized)
                            .specializeByReturnType(findSpecialized(sam().getGenericReturnType()))
                            .specializeByParamTypes(findSpecialized(sam().getGenericParameterTypes()))
                            .fillBy(WILD);
                }
            }
        }
        return this;
    }

    /**
     * Count parameter and return types.
     * 
     * @param method
     * @return
     */
    private int countParameterAndReturn(Method method) {
        int params = method.getParameterCount();
        int returns = method.getReturnType() == void.class ? 0 : 1;
        return params + returns;
    }

    /**
     * Fill all empty slot by the given type.
     * 
     * @param type
     * @return
     */
    SpecializedType fillBy(Type type) {
        for (int i = 0; i < specialized.length; i++) {
            if (specialized[i] == null) {
                specialized[i] = type;
            }
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Type getOwnerType() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Type[] getActualTypeArguments() {
        return specialized;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Type getRawType() {
        return raw;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return raw.getSimpleName() + Join.of(specialized)
                .ignoreEmpty()
                .prefix("<")
                .separator(", ")
                .suffix(">")
                .converter(Type::getTypeName)
                .write();
    }
}