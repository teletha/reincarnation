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

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

import reincarnation.coder.js.Necessary;
import reincarnation.transpiler.BundleVerifier;
import reincarnation.transpiler.bundle.reflection.Mark;
import reincarnation.transpiler.bundle.reflection.Nested;

/**
 * Test the reflection support.
 */
class ReflectionTest extends BundleVerifier {

    @Test
    void forName() throws Exception {
        verifyReflection(ForName.class, "run");
    }

    @Test
    void simpleName() throws Exception {
        verifyReflection(SimpleName.class, "run");
    }

    @Test
    void method() throws Exception {
        verifyReflection(MethodTest.class, "run");
    }

    @Test
    void staticMethod() throws Exception {
        verifyReflection(StaticMethod.class, "run");
    }

    @Test
    void field() throws Exception {
        verifyReflection(FieldTest.class, "run");
    }

    @Test
    void constructor() throws Exception {
        verifyReflection(ConstructorTest.class, "run");
    }

    @Test
    void modifier() throws Exception {
        verifyReflection(ModifierTest.class, "run");
    }

    @Test
    void isInstance() throws Exception {
        verifyReflection(IsInstance.class, "run");
    }

    @Test
    void getClassMethod() throws Exception {
        verifyReflection(GetClassTest.class, "run");
    }

    @Test
    void declaredMethods() throws Exception {
        verifyReflection(DeclaredMethods.class, "run");
    }

    @Test
    void declaredFields() throws Exception {
        verifyReflection(DeclaredFields.class, "run");
    }

    @Test
    void declaredConstructors() throws Exception {
        verifyReflection(DeclaredConstructors.class, "run");
    }

    @Test
    void superclass() throws Exception {
        verifyReflection(Superclass.class, "run");
    }

    @Test
    void interfaces() throws Exception {
        verifyReflection(Interfaces.class, "run");
    }

    @Test
    void isInterface() throws Exception {
        verifyReflection(IsInterface.class, "run");
    }

    @Test
    void isAssignableFrom() throws Exception {
        verifyReflection(IsAssignableFrom.class, "run");
    }

    @Test
    void arrayGet() throws Exception {
        verifyReflection(ArrayGet.class, "run");
    }

    @Test
    void arraySet() throws Exception {
        verifyReflection(ArraySet.class, "run");
    }

    @Test
    void arrayNewInstance() throws Exception {
        verifyReflection(ArrayNew.class, "run");
    }

    @Test
    void isEnum() throws Exception {
        verifyReflection(IsEnum.class, "run");
    }

    @Test
    void isArray() throws Exception {
        verifyReflection(IsArray.class, "run");
    }

    @Test
    void isPrimitive() throws Exception {
        verifyReflection(IsPrimitive.class, "run");
    }

    @Test
    void cast() throws Exception {
        verifyReflection(Cast.class, "run");
    }

    @Test
    void classAnnotation() throws Exception {
        verifyReflection(ClassAnnotation.class, "run");
    }

    @Test
    void methodAnnotation() throws Exception {
        verifyReflection(MethodAnnotation.class, "run");
    }

    @Test
    void fieldAnnotation() throws Exception {
        verifyReflection(FieldAnnotation.class, "run");
    }

    @Test
    void constructorAnnotation() throws Exception {
        verifyReflection(ConstructorAnnotation.class, "run");
    }

    @Test
    void annotationPresent() throws Exception {
        verifyReflection(AnnotationPresent.class, "run");
    }

    @Test
    void annotationArray() throws Exception {
        verifyReflection(AnnotationArray.class, "run");
    }

    @Test
    void annotationClassValue() throws Exception {
        verifyReflection(AnnotationClassValue.class, "run");
    }

    @Test
    void nestedAnnotation() throws Exception {
        verifyReflection(NestedAnnotation.class, "run");
    }

    @Test
    void annotationCount() throws Exception {
        verifyReflection(AnnotationCount.class, "run");
    }

    /** The reflection target. */
    @Necessary
    @Mark(value = 5, name = "target", type = Target.class, tags = {"a", "b"})
    public static class Target {

        @Mark(name = "field")
        public int value = 5;

        @Mark(value = 3)
        public Target() {
        }

        @Mark(value = 4)
        public Target(int value) {
            this.value = value;
        }

        @Mark(value = 7)
        public int add(int other) {
            return value + other;
        }

        public static int twice(int value) {
            return value * 2;
        }
    }

    /** The base class. */
    public static class Base {
    }

    /** The user interface. */
    public interface Named {
    }

    /** The derived class. */
    public static class Derived extends Base implements Named {
    }

    /** The enum. */
    public enum Color {
        RED, GREEN
    }

    /** Verify Class.forName. */
    static class ForName {

        static String run() throws Exception {
            return Class.forName("reincarnation.transpiler.bundle.ReflectionTest$Target").getName();
        }
    }

    /** Verify Class.getSimpleName. */
    static class SimpleName {

        static String run() throws Exception {
            return Class.forName("reincarnation.transpiler.bundle.ReflectionTest$Target").getSimpleName();
        }
    }

    /** Verify Method.invoke. */
    static class MethodTest {

        static int run() throws Exception {
            Class<?> type = Class.forName("reincarnation.transpiler.bundle.ReflectionTest$Target");
            Object instance = type.getDeclaredConstructor().newInstance();
            Method method = type.getDeclaredMethod("add", int.class);
            return (int) method.invoke(instance, 3);
        }
    }

    /** Verify the static method invocation. */
    static class StaticMethod {

        static int run() throws Exception {
            Method method = Target.class.getDeclaredMethod("twice", int.class);
            return (int) method.invoke(null, 3);
        }
    }

    /** Verify Field.get and Field.set. */
    static class FieldTest {

        static int run() throws Exception {
            Class<?> type = Class.forName("reincarnation.transpiler.bundle.ReflectionTest$Target");
            Object instance = type.getDeclaredConstructor(int.class).newInstance(10);
            Field field = type.getDeclaredField("value");
            int before = (int) field.get(instance);
            field.set(instance, before + 1);
            return (int) field.get(instance);
        }
    }

    /** Verify Constructor.newInstance. */
    static class ConstructorTest {

        static int run() throws Exception {
            Class<?> type = Class.forName("reincarnation.transpiler.bundle.ReflectionTest$Target");
            Target instance = (Target) type.getDeclaredConstructor(int.class).newInstance(7);
            return instance.value;
        }
    }

    /** Verify Modifier. */
    static class ModifierTest {

        static int run() throws Exception {
            int modifiers = Class.forName("reincarnation.transpiler.bundle.ReflectionTest$Target").getModifiers();
            return (Modifier.isPublic(modifiers) ? 1 : 0) + (Modifier.isStatic(modifiers) ? 10 : 0);
        }
    }

    /** Verify Class.isInstance. */
    static class IsInstance {

        static boolean run() throws Exception {
            Class<?> type = Class.forName("reincarnation.transpiler.bundle.ReflectionTest$Target");
            return type.isInstance(new Target()) && !type.isInstance("text");
        }
    }

    /** Verify Object.getClass. */
    static class GetClassTest {

        static String run() {
            return new Target().getClass().getSimpleName();
        }
    }

    /** Verify getDeclaredMethods. */
    static class DeclaredMethods {

        static int run() {
            return Target.class.getDeclaredMethods().length;
        }
    }

    /** Verify getDeclaredFields. */
    static class DeclaredFields {

        static int run() {
            return Target.class.getDeclaredFields().length;
        }
    }

    /** Verify getDeclaredConstructors. */
    static class DeclaredConstructors {

        static int run() {
            return Target.class.getDeclaredConstructors().length;
        }
    }

    /** Verify getSuperclass. */
    static class Superclass {

        static String run() {
            return Derived.class.getSuperclass().getSimpleName();
        }
    }

    /** Verify getInterfaces. */
    static class Interfaces {

        static boolean run() {
            Class<?>[] interfaces = Derived.class.getInterfaces();
            return interfaces.length == 1 && interfaces[0].getSimpleName().equals("Named");
        }
    }

    /** Verify isInterface. */
    static class IsInterface {

        static boolean run() {
            return Named.class.isInterface() && !Base.class.isInterface();
        }
    }

    /** Verify isAssignableFrom. */
    static class IsAssignableFrom {

        static boolean run() {
            return Base.class.isAssignableFrom(Derived.class) && !Derived.class.isAssignableFrom(Base.class);
        }
    }

    /** Verify Array.get. */
    static class ArrayGet {

        static int run() {
            int[] array = {1, 2, 3};
            return Array.getLength(array) + (int) Array.get(array, 1);
        }
    }

    /** Verify Array.set. */
    static class ArraySet {

        static int run() {
            int[] array = new int[2];
            Array.set(array, 0, 7);
            return array[0];
        }
    }

    /** Verify Array.newInstance. */
    static class ArrayNew {

        static int run() {
            int[] array = (int[]) Array.newInstance(int.class, 3);
            return array.length + array[2];
        }
    }

    /** Verify isEnum. */
    static class IsEnum {

        static boolean run() {
            return Color.class.isEnum() && !Target.class.isEnum();
        }
    }

    /** Verify isArray. */
    static class IsArray {

        static boolean run() {
            return int[].class.isArray() && !int.class.isArray();
        }
    }

    /** Verify isPrimitive. */
    static class IsPrimitive {

        static boolean run() {
            return int.class.isPrimitive() && !Target.class.isPrimitive();
        }
    }

    /** Verify cast. */
    static class Cast {

        static int run() {
            return ((String) String.class.cast("abc")).length();
        }
    }

    /** Verify the class annotation. */
    static class ClassAnnotation {

        static int run() {
            Mark mark = Target.class.getAnnotation(Mark.class);
            return mark.value() + mark.name().length() + mark.tags().length;
        }
    }

    /** Verify the method annotation. */
    static class MethodAnnotation {

        static int run() throws Exception {
            Method method = Target.class.getDeclaredMethod("add", int.class);
            return method.getAnnotation(Mark.class).value();
        }
    }

    /** Verify the field annotation. */
    static class FieldAnnotation {

        static String run() throws Exception {
            Field field = Target.class.getDeclaredField("value");
            return field.getAnnotation(Mark.class).name();
        }
    }

    /** Verify the constructor annotation. */
    static class ConstructorAnnotation {

        static int run() throws Exception {
            return Target.class.getDeclaredConstructor(int.class).getAnnotation(Mark.class).value();
        }
    }

    /** Verify isAnnotationPresent. */
    static class AnnotationPresent {

        static boolean run() {
            return Target.class.isAnnotationPresent(Mark.class) && !Target.class.isAnnotationPresent(Nested.class);
        }
    }

    /** Verify the annotation array value. */
    static class AnnotationArray {

        static String run() {
            return String.join(",", Target.class.getAnnotation(Mark.class).tags());
        }
    }

    /** Verify the annotation class value. */
    static class AnnotationClassValue {

        static boolean run() {
            return Target.class.getAnnotation(Mark.class).type().getSimpleName().equals("Target");
        }
    }

    /** Verify the nested annotation value. */
    static class NestedAnnotation {

        static String run() {
            return Target.class.getAnnotation(Mark.class).nested().value();
        }
    }

    /** Verify getAnnotationsByType. */
    static class AnnotationCount {

        static int run() {
            return Target.class.getAnnotationsByType(Mark.class).length;
        }
    }
}
