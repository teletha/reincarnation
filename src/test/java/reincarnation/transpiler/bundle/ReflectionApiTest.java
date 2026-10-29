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
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

import reincarnation.coder.js.Necessary;
import reincarnation.transpiler.BundleVerifier;
import reincarnation.transpiler.bundle.reflection.Mark;

/**
 * Test the reflection API exhaustively.
 */
class ReflectionApiTest extends BundleVerifier {

    @Test
    void className() throws Exception {
        verifyReflection(ClassName.class, "run");
    }

    @Test
    void canonicalName() throws Exception {
        verifyReflection(CanonicalName.class, "run");
    }

    @Test
    void typeName() throws Exception {
        verifyReflection(TypeName.class, "run");
    }

    @Test
    void simpleName() throws Exception {
        verifyReflection(SimpleName.class, "run");
    }

    @Test
    void standardClassName() throws Exception {
        verifyReflection(StandardClassName.class, "run");
    }

    @Test
    void primitiveClassName() throws Exception {
        verifyReflection(PrimitiveClassName.class, "run");
    }

    @Test
    void arrayFlag() throws Exception {
        verifyReflection(ArrayFlag.class, "run");
    }

    @Test
    void arrayComponent() throws Exception {
        verifyReflection(ArrayComponent.class, "run");
    }

    @Test
    void objectArrayComponent() throws Exception {
        verifyReflection(ObjectArrayComponent.class, "run");
    }

    @Test
    void nestedArrayComponent() throws Exception {
        verifyReflection(NestedArrayComponent.class, "run");
    }

    @Test
    void arrayComponentNone() throws Exception {
        verifyReflection(ArrayComponentNone.class, "run");
    }

    @Test
    void primitiveFlag() throws Exception {
        verifyReflection(PrimitiveFlag.class, "run");
    }

    @Test
    void classModifiers() throws Exception {
        verifyReflection(ClassModifiers.class, "run");
    }

    @Test
    void finalModifiers() throws Exception {
        verifyReflection(FinalModifiers.class, "run");
    }

    @Test
    void abstractModifiers() throws Exception {
        verifyReflection(AbstractModifiers.class, "run");
    }

    @Test
    void interfaceModifiers() throws Exception {
        verifyReflection(InterfaceModifiers.class, "run");
    }

    @Test
    void isInterface() throws Exception {
        verifyReflection(IsInterface.class, "run");
    }

    @Test
    void isAnnotation() throws Exception {
        verifyReflection(IsAnnotation.class, "run");
    }

    @Test
    void isEnum() throws Exception {
        verifyReflection(IsEnum.class, "run");
    }

    @Test
    void superclass() throws Exception {
        verifyReflection(Superclass.class, "run");
    }

    @Test
    void superclassObject() throws Exception {
        verifyReflection(SuperclassObject.class, "run");
    }

    @Test
    void superclassInterface() throws Exception {
        verifyReflection(SuperclassInterface.class, "run");
    }

    @Test
    void superclassPrimitive() throws Exception {
        verifyReflection(SuperclassPrimitive.class, "run");
    }

    @Test
    void interfaces() throws Exception {
        verifyReflection(Interfaces.class, "run");
    }

    @Test
    void assignable() throws Exception {
        verifyReflection(Assignable.class, "run");
    }

    @Test
    void notAssignable() throws Exception {
        verifyReflection(NotAssignable.class, "run");
    }

    @Test
    void isInstance() throws Exception {
        verifyReflection(IsInstance.class, "run");
    }

    @Test
    void isNotInstance() throws Exception {
        verifyReflection(IsNotInstance.class, "run");
    }

    @Test
    void cast() throws Exception {
        verifyReflection(Cast.class, "run");
    }

    @Test
    void classToString() throws Exception {
        verifyReflection(ClassToString.class, "run");
    }

    @Test
    void interfaceToString() throws Exception {
        verifyReflection(InterfaceToString.class, "run");
    }

    @Test
    void forName() throws Exception {
        verifyReflection(ForName.class, "run");
    }

    @Test
    void forNameMissing() throws Exception {
        verifyReflection(ForNameMissing.class, "run");
    }

    @Test
    void declaredMethodCount() throws Exception {
        verifyReflection(DeclaredMethodCount.class, "run");
    }

    @Test
    void methodParameterTypes() throws Exception {
        verifyReflection(MethodParameterTypes.class, "run");
    }

    @Test
    void methodReturnType() throws Exception {
        verifyReflection(MethodReturnType.class, "run");
    }

    @Test
    void methodVoidReturn() throws Exception {
        verifyReflection(MethodVoidReturn.class, "run");
    }

    @Test
    void methodArrayReturn() throws Exception {
        verifyReflection(MethodArrayReturn.class, "run");
    }

    @Test
    void methodExceptionTypes() throws Exception {
        verifyReflection(MethodExceptionTypes.class, "run");
    }

    @Test
    void methodNoException() throws Exception {
        verifyReflection(MethodNoException.class, "run");
    }

    @Test
    void methodStaticParameterTypes() throws Exception {
        verifyReflection(MethodStaticParameterTypes.class, "run");
    }

    @Test
    void methodModifiers() throws Exception {
        verifyReflection(MethodModifiers.class, "run");
    }

    @Test
    void methodParameterCount() throws Exception {
        verifyReflection(MethodParameterCount.class, "run");
    }

    @Test
    void methodName() throws Exception {
        verifyReflection(MethodName.class, "run");
    }

    @Test
    void methodMissing() throws Exception {
        verifyReflection(MethodMissing.class, "run");
    }

    @Test
    void methodInherited() throws Exception {
        verifyReflection(MethodInherited.class, "run");
    }

    @Test
    void methodsContainsInherited() throws Exception {
        verifyReflection(MethodsContainsInherited.class, "run");
    }

    @Test
    void fieldCount() throws Exception {
        verifyReflection(FieldCount.class, "run");
    }

    @Test
    void fieldTypePrimitive() throws Exception {
        verifyReflection(FieldTypePrimitive.class, "run");
    }

    @Test
    void fieldTypeString() throws Exception {
        verifyReflection(FieldTypeString.class, "run");
    }

    @Test
    void fieldTypeArray() throws Exception {
        verifyReflection(FieldTypeArray.class, "run");
    }

    @Test
    void fieldModifiers() throws Exception {
        verifyReflection(FieldModifiers.class, "run");
    }

    @Test
    void fieldGetSet() throws Exception {
        verifyReflection(FieldGetSet.class, "run");
    }

    @Test
    void fieldMissing() throws Exception {
        verifyReflection(FieldMissing.class, "run");
    }

    @Test
    void fieldInherited() throws Exception {
        verifyReflection(FieldInherited.class, "run");
    }

    @Test
    void fieldsContainsInherited() throws Exception {
        verifyReflection(FieldsContainsInherited.class, "run");
    }

    @Test
    void constructorCount() throws Exception {
        verifyReflection(ConstructorCount.class, "run");
    }

    @Test
    void constructorParameterTypes() throws Exception {
        verifyReflection(ConstructorParameterTypes.class, "run");
    }

    @Test
    void constructorExceptionTypes() throws Exception {
        verifyReflection(ConstructorExceptionTypes.class, "run");
    }

    @Test
    void constructorModifiers() throws Exception {
        verifyReflection(ConstructorModifiers.class, "run");
    }

    @Test
    void constructorNewInstance() throws Exception {
        verifyReflection(ConstructorNewInstance.class, "run");
    }

    @Test
    void constructorNoArgs() throws Exception {
        verifyReflection(ConstructorNoArgs.class, "run");
    }

    @Test
    void constructorsCount() throws Exception {
        verifyReflection(ConstructorsCount.class, "run");
    }

    @Test
    void modifierCombination() throws Exception {
        verifyReflection(ModifierCombination.class, "run");
    }

    @Test
    void modifierFieldFlags() throws Exception {
        verifyReflection(ModifierFieldFlags.class, "run");
    }

    @Test
    void arrayNewObject() throws Exception {
        verifyReflection(ArrayNewObject.class, "run");
    }

    @Test
    void arrayNewBoolean() throws Exception {
        verifyReflection(ArrayNewBoolean.class, "run");
    }

    @Test
    void arrayNewInt() throws Exception {
        verifyReflection(ArrayNewInt.class, "run");
    }

    @Test
    void arrayGetSet() throws Exception {
        verifyReflection(ArrayGetSet.class, "run");
    }

    @Test
    void genericExceptionVariable() throws Exception {
        verifyReflection(GenericExceptionVariable.class, "run");
    }

    /** The base class. */
    public static class ApiBase {

        public int inherited = 3;

        public void inheritedMethod() {
        }
    }

    /** The interface. */
    public interface Api {
    }

    /** The final class. */
    public static final class ApiFinal {
    }

    /** The abstract class. */
    public abstract static class ApiAbstract {
    }

    /** The enum. */
    public enum ApiEnum {
        ONE, TWO
    }

    /** The reflection target. */
    @Necessary
    @Mark(value = 11)
    public static class ApiTarget extends ApiBase implements Api {

        public static final int CONSTANT = 7;

        protected String text = "t";

        private int[][] matrix;

        public int number;

        public ApiTarget() {
        }

        public ApiTarget(int value) throws java.io.IOException {
            this.number = value;
        }

        public void noop() {
        }

        public int add(int a, String b) throws Exception {
            return a + b.length();
        }

        public String[] names() {
            return null;
        }

        public static <T> T pick(T value) {
            return value;
        }
    }

    /** The generic target. */
    public static class ApiGenericTarget {

        public <T extends Exception> void risky() throws T {
        }
    }

    /** Verify Class.getName. */
    static class ClassName {

        static String run() {
            return ApiTarget.class.getName();
        }
    }

    /** Verify Class.getCanonicalName. */
    static class CanonicalName {

        static String run() {
            return ApiTarget.class.getCanonicalName();
        }
    }

    /** Verify Class.getTypeName. */
    static class TypeName {

        static String run() {
            return ApiTarget.class.getTypeName();
        }
    }

    /** Verify Class.getSimpleName. */
    static class SimpleName {

        static String run() {
            return ApiTarget.class.getSimpleName();
        }
    }

    /** Verify the standard class name. */
    static class StandardClassName {

        static String run() {
            return String.class.getName() + "," + Object.class.getName();
        }
    }

    /** Verify the primitive class name. */
    static class PrimitiveClassName {

        static String run() {
            return int.class.getName() + "," + void.class.getName();
        }
    }

    /** Verify Class.isArray. */
    static class ArrayFlag {

        static boolean run() {
            return int[].class.isArray() && !int.class.isArray() && !ApiTarget.class.isArray();
        }
    }

    /** Verify the primitive array component. */
    static class ArrayComponent {

        static boolean run() {
            return int[].class.getComponentType() == int.class;
        }
    }

    /** Verify the object array component. */
    static class ObjectArrayComponent {

        static boolean run() {
            return String[].class.getComponentType() == String.class;
        }
    }

    /** Verify the nested array component. */
    static class NestedArrayComponent {

        static boolean run() {
            return int[][].class.getComponentType().isArray();
        }
    }

    /** Verify the non array component. */
    static class ArrayComponentNone {

        static boolean run() {
            return ApiTarget.class.getComponentType() == null;
        }
    }

    /** Verify Class.isPrimitive. */
    static class PrimitiveFlag {

        static boolean run() {
            return int.class.isPrimitive() && !ApiTarget.class.isPrimitive();
        }
    }

    /** Verify the class modifiers. */
    static class ClassModifiers {

        static boolean run() {
            int modifiers = ApiTarget.class.getModifiers();
            return Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers);
        }
    }

    /** Verify the final modifiers. */
    static class FinalModifiers {

        static boolean run() {
            int modifiers = ApiFinal.class.getModifiers();
            return Modifier.isFinal(modifiers) && Modifier.isPublic(modifiers);
        }
    }

    /** Verify the abstract modifiers. */
    static class AbstractModifiers {

        static boolean run() {
            return Modifier.isAbstract(ApiAbstract.class.getModifiers());
        }
    }

    /** Verify the interface modifiers. */
    static class InterfaceModifiers {

        static boolean run() {
            int modifiers = Api.class.getModifiers();
            return Modifier.isInterface(modifiers) && Modifier.isAbstract(modifiers);
        }
    }

    /** Verify Class.isInterface. */
    static class IsInterface {

        static boolean run() {
            return Api.class.isInterface() && !ApiTarget.class.isInterface();
        }
    }

    /** Verify Class.isAnnotation. */
    static class IsAnnotation {

        static boolean run() {
            return Mark.class.isAnnotation() && !Api.class.isAnnotation();
        }
    }

    /** Verify Class.isEnum. */
    static class IsEnum {

        static boolean run() {
            return ApiEnum.class.isEnum() && !ApiTarget.class.isEnum();
        }
    }

    /** Verify Class.getSuperclass. */
    static class Superclass {

        static boolean run() {
            return ApiTarget.class.getSuperclass() == ApiBase.class;
        }
    }

    /** Verify the superclass of Object. */
    static class SuperclassObject {

        static boolean run() {
            return Object.class.getSuperclass() == null;
        }
    }

    /** Verify the superclass of an interface. */
    static class SuperclassInterface {

        static boolean run() {
            return Api.class.getSuperclass() == null;
        }
    }

    /** Verify the superclass of a primitive. */
    static class SuperclassPrimitive {

        static boolean run() {
            return int.class.getSuperclass() == null;
        }
    }

    /** Verify Class.getInterfaces. */
    static class Interfaces {

        static boolean run() {
            Class<?>[] interfaces = ApiTarget.class.getInterfaces();
            return interfaces.length == 1 && interfaces[0] == Api.class;
        }
    }

    /** Verify Class.isAssignableFrom. */
    static class Assignable {

        static boolean run() {
            return ApiBase.class.isAssignableFrom(ApiTarget.class) //
                    && Api.class.isAssignableFrom(ApiTarget.class);
        }
    }

    /** Verify the negative isAssignableFrom. */
    static class NotAssignable {

        static boolean run() {
            return !ApiTarget.class.isAssignableFrom(ApiBase.class);
        }
    }

    /** Verify Class.isInstance. */
    static class IsInstance {

        static boolean run() {
            return ApiTarget.class.isInstance(new ApiTarget());
        }
    }

    /** Verify the negative isInstance. */
    static class IsNotInstance {

        static boolean run() {
            return !ApiTarget.class.isInstance(new ApiBase());
        }
    }

    /** Verify Class.cast. */
    static class Cast {

        static boolean run() {
            return ((ApiTarget) ApiTarget.class.cast(new ApiTarget())).number == 0;
        }
    }

    /** Verify Class.toString. */
    static class ClassToString {

        static String run() {
            return ApiTarget.class.toString();
        }
    }

    /** Verify the interface toString. */
    static class InterfaceToString {

        static String run() {
            return Api.class.toString();
        }
    }

    /** Verify Class.forName. */
    static class ForName {

        static String run() throws Exception {
            return Class.forName("reincarnation.transpiler.bundle.ReflectionApiTest$ApiTarget").getName();
        }
    }

    /** Verify the missing Class.forName. */
    static class ForNameMissing {

        static boolean run() {
            try {
                Class.forName("no.such.Type");
                return false;
            } catch (Throwable e) {
                return true;
            }
        }
    }

    /** Verify the declared method count. */
    static class DeclaredMethodCount {

        static int run() {
            return ApiTarget.class.getDeclaredMethods().length;
        }
    }

    /** Verify Method.getParameterTypes. */
    static class MethodParameterTypes {

        static boolean run() throws Exception {
            Class<?>[] types = ApiTarget.class.getDeclaredMethod("add", int.class, String.class).getParameterTypes();
            return types.length == 2 && types[0] == int.class && types[1] == String.class;
        }
    }

    /** Verify Method.getReturnType. */
    static class MethodReturnType {

        static boolean run() throws Exception {
            return ApiTarget.class.getDeclaredMethod("add", int.class, String.class).getReturnType() == int.class;
        }
    }

    /** Verify the void return type. */
    static class MethodVoidReturn {

        static boolean run() throws Exception {
            return ApiTarget.class.getDeclaredMethod("noop").getReturnType() == void.class;
        }
    }

    /** Verify the array return type. */
    static class MethodArrayReturn {

        static boolean run() throws Exception {
            Class<?> type = ApiTarget.class.getDeclaredMethod("names").getReturnType();
            return type.isArray() && type.getComponentType() == String.class;
        }
    }

    /** Verify Method.getExceptionTypes. */
    static class MethodExceptionTypes {

        static String run() throws Exception {
            return ApiTarget.class.getDeclaredMethod("add", int.class, String.class).getExceptionTypes()[0].getName();
        }
    }

    /** Verify the empty exception types. */
    static class MethodNoException {

        static int run() throws Exception {
            return ApiTarget.class.getDeclaredMethod("noop").getExceptionTypes().length;
        }
    }

    /** Verify the erased parameter type. */
    static class MethodStaticParameterTypes {

        static boolean run() throws Exception {
            return ApiTarget.class.getDeclaredMethod("pick", Object.class).getParameterTypes()[0] == Object.class;
        }
    }

    /** Verify Method.getModifiers. */
    static class MethodModifiers {

        static boolean run() throws Exception {
            int modifiers = ApiTarget.class.getDeclaredMethod("noop").getModifiers();
            return Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers);
        }
    }

    /** Verify Method.getParameterCount. */
    static class MethodParameterCount {

        static int run() throws Exception {
            return ApiTarget.class.getDeclaredMethod("add", int.class, String.class).getParameterCount();
        }
    }

    /** Verify Method.getName. */
    static class MethodName {

        static String run() throws Exception {
            return ApiTarget.class.getDeclaredMethod("noop").getName();
        }
    }

    /** Verify the missing method. */
    static class MethodMissing {

        static boolean run() {
            try {
                ApiTarget.class.getDeclaredMethod("missing");
                return false;
            } catch (NoSuchMethodException e) {
                return true;
            }
        }
    }

    /** Verify the inherited method lookup. */
    static class MethodInherited {

        static String run() throws Exception {
            return ApiTarget.class.getMethod("inheritedMethod").getName();
        }
    }

    /** Verify that getMethods contains the inherited method. */
    static class MethodsContainsInherited {

        static boolean run() {
            for (Method method : ApiTarget.class.getMethods()) {
                if (method.getName().equals("inheritedMethod")) {
                    return true;
                }
            }
            return false;
        }
    }

    /** Verify the declared field count. */
    static class FieldCount {

        static int run() {
            return ApiTarget.class.getDeclaredFields().length;
        }
    }

    /** Verify the primitive field type. */
    static class FieldTypePrimitive {

        static boolean run() throws Exception {
            return ApiTarget.class.getDeclaredField("CONSTANT").getType() == int.class;
        }
    }

    /** Verify the object field type. */
    static class FieldTypeString {

        static boolean run() throws Exception {
            return ApiTarget.class.getDeclaredField("text").getType() == String.class;
        }
    }

    /** Verify the array field type. */
    static class FieldTypeArray {

        static boolean run() throws Exception {
            return ApiTarget.class.getDeclaredField("matrix").getType().isArray();
        }
    }

    /** Verify Field.getModifiers. */
    static class FieldModifiers {

        static boolean run() throws Exception {
            int modifiers = ApiTarget.class.getDeclaredField("text").getModifiers();
            return Modifier.isProtected(modifiers) && !Modifier.isPublic(modifiers);
        }
    }

    /** Verify Field.get and Field.set. */
    static class FieldGetSet {

        static String run() throws Exception {
            Field field = ApiTarget.class.getDeclaredField("text");
            ApiTarget target = new ApiTarget();
            String before = (String) field.get(target);
            field.set(target, before + "!");
            return (String) field.get(target);
        }
    }

    /** Verify the missing field. */
    static class FieldMissing {

        static boolean run() {
            try {
                ApiTarget.class.getDeclaredField("missing");
                return false;
            } catch (NoSuchFieldException e) {
                return true;
            }
        }
    }

    /** Verify the inherited field lookup. */
    static class FieldInherited {

        static String run() throws Exception {
            return ApiTarget.class.getField("inherited").getName();
        }
    }

    /** Verify that getFields contains the inherited field. */
    static class FieldsContainsInherited {

        static boolean run() {
            for (Field field : ApiTarget.class.getFields()) {
                if (field.getName().equals("inherited")) {
                    return true;
                }
            }
            return false;
        }
    }

    /** Verify the declared constructor count. */
    static class ConstructorCount {

        static int run() {
            return ApiTarget.class.getDeclaredConstructors().length;
        }
    }

    /** Verify Constructor.getParameterTypes. */
    static class ConstructorParameterTypes {

        static boolean run() throws Exception {
            Class<?>[] types = ApiTarget.class.getDeclaredConstructor(int.class).getParameterTypes();
            return types.length == 1 && types[0] == int.class;
        }
    }

    /** Verify Constructor.getExceptionTypes. */
    static class ConstructorExceptionTypes {

        static String run() throws Exception {
            return ApiTarget.class.getDeclaredConstructor(int.class).getExceptionTypes()[0].getName();
        }
    }

    /** Verify Constructor.getModifiers. */
    static class ConstructorModifiers {

        static boolean run() throws Exception {
            return Modifier.isPublic(ApiTarget.class.getDeclaredConstructor(int.class).getModifiers());
        }
    }

    /** Verify Constructor.newInstance. */
    static class ConstructorNewInstance {

        static int run() throws Exception {
            Constructor<ApiTarget> constructor = ApiTarget.class.getDeclaredConstructor(int.class);
            return constructor.newInstance(9).number;
        }
    }

    /** Verify the default constructor. */
    static class ConstructorNoArgs {

        static int run() throws Exception {
            return ApiTarget.class.getDeclaredConstructor().newInstance().number;
        }
    }

    /** Verify Class.getConstructors. */
    static class ConstructorsCount {

        static int run() {
            return ApiTarget.class.getConstructors().length;
        }
    }

    /** Verify Modifier combination. */
    static class ModifierCombination {

        static boolean run() {
            int flags = Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL;
            return Modifier.isPrivate(flags) && Modifier.isStatic(flags) && Modifier.isFinal(flags) //
                    && !Modifier.isPublic(flags) && !Modifier.isProtected(flags);
        }
    }

    /** Verify the field modifier flags. */
    static class ModifierFieldFlags {

        static boolean run() throws Exception {
            int constant = ApiTarget.class.getDeclaredField("CONSTANT").getModifiers();
            int matrix = ApiTarget.class.getDeclaredField("matrix").getModifiers();
            return Modifier.isPublic(constant) && Modifier.isStatic(constant) && Modifier.isFinal(constant) //
                    && Modifier.isPrivate(matrix);
        }
    }

    /** Verify Array.newInstance for the object type. */
    static class ArrayNewObject {

        static int run() {
            String[] array = (String[]) Array.newInstance(String.class, 3);
            return array.length;
        }
    }

    /** Verify Array.newInstance for boolean. */
    static class ArrayNewBoolean {

        static boolean run() {
            boolean[] array = (boolean[]) Array.newInstance(boolean.class, 2);
            return array[0] || array[1];
        }
    }

    /** Verify Array.newInstance for int. */
    static class ArrayNewInt {

        static int run() {
            int[] array = (int[]) Array.newInstance(int.class, 2);
            return array[0] + array[1];
        }
    }

    /** Verify Array.get and Array.set for the object type. */
    static class ArrayGetSet {

        static String run() {
            String[] array = new String[2];
            Array.set(array, 0, "a");
            Array.set(array, 1, (String) Array.get(array, 0) + "b");
            return array[1];
        }
    }

    /** Verify the generic exception type identity. */
    static class GenericExceptionVariable {

        static boolean run() throws Exception {
            Method method = ApiGenericTarget.class.getDeclaredMethod("risky");
            return method.getGenericExceptionTypes()[0] == method.getTypeParameters()[0];
        }
    }
}
