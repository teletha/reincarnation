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

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.signature.SignatureVisitor;

class Signature {

    final Type returnType;

    final Type exceptionType;

    final List<Type> parameterTypes;

    /**
     * @param returnType
     * @param exceptionType
     */
    private Signature(Type returnType, Type exceptionType, List<Type> parameterTypes) {
        this.returnType = returnType;
        this.exceptionType = exceptionType;
        this.parameterTypes = parameterTypes;
    }

    Type param(int index) {
        return parameterTypes.get(index);
    }

    /**
     * Parse the given signature.
     * 
     * @param signature
     * @return
     */
    static Signature parse(String signature) {
        Signature sig = new Signature();

        if (signature == null) {
            return sig;
        }

        SignatureReader reader = new SignatureReader(signature);
        reader.accept(sig);

        return sig;
    }

    /**
     * Root parser.
     */
    private static class Parser extends SignatureVisitor {

        private Type type;

        private Parser() {
            super(Opcodes.ASM9);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public SignatureVisitor visitParameterType() {
            return (SignatureVisitor) (type = new ParameterizedTypeVisitor());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public SignatureVisitor visitArrayType() {
            return (SignatureVisitor) (type = new GenericArrayTypeVisitor());
        }
    }

    /**
     * 
     */
    private static class ParameterizedTypeVisitor extends Parser implements ParameterizedType {

        /**
         * {@inheritDoc}
         */
        @Override
        public Type[] getActualTypeArguments() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Type getRawType() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Type getOwnerType() {
            return null;
        }
    }

    /**
     * 
     */
    private static class GenericArrayTypeVisitor extends Parser implements GenericArrayType {

        /**
         * {@inheritDoc}
         */
        @Override
        public Type getGenericComponentType() {
            return null;
        }
    }

    /**
     * 
     */
    private static class WildcardTypeVisitor extends Parser implements WildcardType {

        /**
         * {@inheritDoc}
         */
        @Override
        public Type[] getUpperBounds() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Type[] getLowerBounds() {
            return null;
        }
    }

    /**
     * 
     */
    private static class TypeVariableVisitor extends Parser implements TypeVariable {

        /**
         * {@inheritDoc}
         */
        @Override
        public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
            // If this exception will be thrown, it is bug of this program. So we must rethrow the
            // wrapped error in here.
            throw new Error();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Annotation[] getAnnotations() {
            // If this exception will be thrown, it is bug of this program. So we must rethrow the
            // wrapped error in here.
            throw new Error();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Annotation[] getDeclaredAnnotations() {
            // If this exception will be thrown, it is bug of this program. So we must rethrow the
            // wrapped error in here.
            throw new Error();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Type[] getBounds() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public GenericDeclaration getGenericDeclaration() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getName() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public AnnotatedType[] getAnnotatedBounds() {
            // If this exception will be thrown, it is bug of this program. So we must rethrow the
            // wrapped error in here.
            throw new Error();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public SignatureVisitor visitTypeArgument(char wildcard) {
            switch (wildcard) {
            case SignatureVisitor.EXTENDS:
                System.out.println("extend ");
                break;

            case SignatureVisitor.INSTANCEOF:
                System.out.println("instanceof");
                break;

            case SignatureVisitor.SUPER:
                System.out.println("super");
                break;
            }
            return super.visitTypeArgument(wildcard);
        }
    }
}
