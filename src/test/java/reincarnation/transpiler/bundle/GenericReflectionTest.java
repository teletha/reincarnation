/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.bundle;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.List;

import org.junit.jupiter.api.Test;

import reincarnation.transpiler.BundleVerifier;

/**
 * Test the generic type reflection.
 */
class GenericReflectionTest extends BundleVerifier {

    @Test
    void typeParameter() throws Exception {
        verifyReflection(TypeParameter.class, "run");
    }

    @Test
    void typeParameterBound() throws Exception {
        verifyReflection(TypeParameterBound.class, "run");
    }

    @Test
    void typeParameterDeclaration() throws Exception {
        verifyReflection(TypeParameterDeclaration.class, "run");
    }

    @Test
    void typeVariableIdentity() throws Exception {
        verifyReflection(TypeVariableIdentity.class, "run");
    }

    @Test
    void genericSuperclass() throws Exception {
        verifyReflection(GenericSuperclass.class, "run");
    }

    @Test
    void genericInterface() throws Exception {
        verifyReflection(GenericInterfaceTest.class, "run");
    }

    @Test
    void genericField() throws Exception {
        verifyReflection(GenericField.class, "run");
    }

    @Test
    void genericArray() throws Exception {
        verifyReflection(GenericArray.class, "run");
    }

    @Test
    void genericReturn() throws Exception {
        verifyReflection(GenericReturn.class, "run");
    }

    @Test
    void genericParameter() throws Exception {
        verifyReflection(GenericParameter.class, "run");
    }

    @Test
    void genericMethodTypeParameter() throws Exception {
        verifyReflection(GenericMethodTypeParameter.class, "run");
    }

    @Test
    void wildcardUpper() throws Exception {
        verifyReflection(WildcardUpper.class, "run");
    }

    @Test
    void wildcardLower() throws Exception {
        verifyReflection(WildcardLower.class, "run");
    }

    @Test
    void wildcardAny() throws Exception {
        verifyReflection(WildcardAny.class, "run");
    }

    @Test
    void instanceofGenericType() throws Exception {
        verifyReflection(InstanceofGenericType.class, "run");
    }

    @Test
    void typeName() throws Exception {
        verifyReflection(TypeName.class, "run");
    }

    @Test
    void noTypeParameter() throws Exception {
        verifyReflection(NoTypeParameter.class, "run");
    }

    @Test
    void objectSuperclass() throws Exception {
        verifyReflection(ObjectSuperclass.class, "run");
    }

    /** The generic class. */
    public static class Generic<T> {

        public T value;

        public T[] array;

        public List<T> list;

        public <R> R identity(R value) {
            return value;
        }

        public List<String> names() {
            return null;
        }

        public void accept(List<? extends Number> values) {
        }
    }

    /** The generic subclass. */
    public static class StringGeneric extends Generic<String> {
    }

    /** The bounded type variable. */
    public static class Bounded<T extends Number & Comparable<T>> {
    }

    /** The generic interface. */
    public interface GenericInterface<T> {
    }

    /** The generic interface implementation. */
    public static class ImplementsGeneric implements GenericInterface<String> {
    }

    /** The wildcard holder. */
    public static class Wildcards {

        public List<? extends Number> upper;

        public List<? super Integer> lower;

        public List<?> any;
    }

    /** Verify the type parameter. */
    static class TypeParameter {

        static int run() {
            TypeVariable<?>[] variables = Generic.class.getTypeParameters();
            return variables.length + (variables[0].getName().equals("T") ? 10 : 0);
        }
    }

    /** Verify the type parameter bounds. */
    static class TypeParameterBound {

        static boolean run() {
            Type[] bounds = Bounded.class.getTypeParameters()[0].getBounds();
            return bounds.length == 2 && ((Class<?>) bounds[0]).getSimpleName().equals("Number") && bounds[1] instanceof ParameterizedType;
        }
    }

    /** Verify the generic declaration. */
    static class TypeParameterDeclaration {

        static boolean run() {
            return Generic.class.getTypeParameters()[0].getGenericDeclaration() == Generic.class;
        }
    }

    /** Verify the type variable identity. */
    static class TypeVariableIdentity {

        static boolean run() throws Exception {
            TypeVariable<?> variable = Generic.class.getTypeParameters()[0];
            Type fieldType = Generic.class.getDeclaredField("value").getGenericType();
            return fieldType == variable;
        }
    }

    /** Verify the generic superclass. */
    static class GenericSuperclass {

        static boolean run() {
            Type type = StringGeneric.class.getGenericSuperclass();
            return type instanceof ParameterizedType //
                    && ((ParameterizedType) type).getRawType() == Generic.class //
                    && ((Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0]).getSimpleName().equals("String");
        }
    }

    /** Verify the generic interfaces. */
    static class GenericInterfaceTest {

        static boolean run() {
            Type type = ImplementsGeneric.class.getGenericInterfaces()[0];
            return type instanceof ParameterizedType //
                    && ((Class<?>) ((ParameterizedType) type).getRawType()).getSimpleName().equals("GenericInterface");
        }
    }

    /** Verify the generic field type. */
    static class GenericField {

        static boolean run() throws Exception {
            Type type = Generic.class.getDeclaredField("list").getGenericType();
            return type instanceof ParameterizedType //
                    && ((TypeVariable<?>) ((ParameterizedType) type).getActualTypeArguments()[0]).getName().equals("T");
        }
    }

    /** Verify the generic array type. */
    static class GenericArray {

        static boolean run() throws Exception {
            Type type = Generic.class.getDeclaredField("array").getGenericType();
            return type instanceof GenericArrayType //
                    && ((TypeVariable<?>) ((GenericArrayType) type).getGenericComponentType()).getName().equals("T");
        }
    }

    /** Verify the generic return type. */
    static class GenericReturn {

        static boolean run() throws Exception {
            Type type = Generic.class.getDeclaredMethod("names").getGenericReturnType();
            return type instanceof ParameterizedType //
                    && ((Class<?>) ((ParameterizedType) type).getRawType()).getSimpleName().equals("List");
        }
    }

    /** Verify the generic parameter type. */
    static class GenericParameter {

        static boolean run() throws Exception {
            Method method = Generic.class.getDeclaredMethod("accept", List.class);
            Type type = method.getGenericParameterTypes()[0];
            return type instanceof ParameterizedType //
                    && ((ParameterizedType) type).getActualTypeArguments()[0] instanceof WildcardType;
        }
    }

    /** Verify the method type parameter. */
    static class GenericMethodTypeParameter {

        static boolean run() throws Exception {
            Method method = Generic.class.getDeclaredMethod("identity", Object.class);
            TypeVariable<?> variable = method.getTypeParameters()[0];
            return variable.getName().equals("R") //
                    && method.getGenericReturnType() == variable //
                    && method.getGenericParameterTypes()[0] == variable;
        }
    }

    /** Verify the upper wildcard. */
    static class WildcardUpper {

        static boolean run() throws Exception {
            ParameterizedType type = (ParameterizedType) Wildcards.class.getDeclaredField("upper").getGenericType();
            WildcardType wild = (WildcardType) type.getActualTypeArguments()[0];
            return ((Class<?>) wild.getUpperBounds()[0]).getSimpleName().equals("Number") && wild.getLowerBounds().length == 0;
        }
    }

    /** Verify the lower wildcard. */
    static class WildcardLower {

        static boolean run() throws Exception {
            ParameterizedType type = (ParameterizedType) Wildcards.class.getDeclaredField("lower").getGenericType();
            WildcardType wild = (WildcardType) type.getActualTypeArguments()[0];
            return ((Class<?>) wild.getLowerBounds()[0]).getSimpleName().equals("Integer") //
                    && ((Class<?>) wild.getUpperBounds()[0]).getSimpleName().equals("Object");
        }
    }

    /** Verify the unbounded wildcard. */
    static class WildcardAny {

        static boolean run() throws Exception {
            ParameterizedType type = (ParameterizedType) Wildcards.class.getDeclaredField("any").getGenericType();
            WildcardType wild = (WildcardType) type.getActualTypeArguments()[0];
            return ((Class<?>) wild.getUpperBounds()[0]).getSimpleName().equals("Object") && wild.getLowerBounds().length == 0;
        }
    }

    /** Verify the reflection type check. */
    static class InstanceofGenericType {

        static boolean run() throws Exception {
            Type type = Generic.class.getDeclaredField("list").getGenericType();
            return type instanceof ParameterizedType && !(type instanceof TypeVariable);
        }
    }

    /** Verify the type name. */
    static class TypeName {

        static String run() throws Exception {
            return Generic.class.getDeclaredField("list").getGenericType().getTypeName();
        }
    }

    /** Verify the class which has no type parameter. */
    static class NoTypeParameter {

        static boolean run() {
            return StringGeneric.class.getTypeParameters().length == 0;
        }
    }

    /** Verify the superclass of Object. */
    static class ObjectSuperclass {

        static boolean run() {
            return Object.class.getGenericSuperclass() == null;
        }
    }
}
