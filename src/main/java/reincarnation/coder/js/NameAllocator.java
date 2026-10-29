/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder.js;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Allocates a unique and valid JavaScript identifier for every class, method and field.
 * <p>
 * Methods are keyed by their name and parameter descriptor so that an override shares the name of
 * the base method while an overload gets a distinct name. Static and private methods include the
 * declaring class because they are not dynamically dispatched in Java.
 * </p>
 * <p>
 * Fields are keyed by the declaring class and the field name because JavaScript has only one
 * property namespace, while Java allows a subclass to hide a field with the same name.
 * </p>
 */
public final class NameAllocator {

    /** The JavaScript reserved words which can not be used as an identifier. */
    private static final Set<String> reserved = Set.of("await", "break", "case", "catch", "class", "const", "continue",
            "debugger", "default", "delete", "do", "else", "enum", "export", "extends", "false", "finally", "for",
            "function", "if", "implements", "import", "in", "instanceof", "interface", "let", "new", "null", "package",
            "private", "protected", "public", "return", "static", "super", "switch", "this", "throw", "true", "try",
            "typeof", "var", "void", "while", "with", "yield");

    /** The member names which are used by the JavaScript runtime directly. */
    private static final Set<String> fixedNames = Set.of("equals", "hashCode", "toString", "compareTo", "iterator",
            "hasNext", "next", "close");

    /** The allocated class names. */
    private final Map<Class, String> classes = new HashMap();

    /** The allocated method names. */
    private final Map<Method, String> methods = new HashMap();

    /** The allocated field names. */
    private final Map<Field, String> fields = new HashMap();

    /** The allocated names by the logical member key. */
    private final Map<String, String> members = new HashMap();

    /** The used class names. */
    private final Set<String> usedClasses = new HashSet();

    /** The used member names. */
    private final Set<String> usedMembers = new HashSet(fixedNames);

    /**
     * Pre-allocate the name of the specified class to keep the allocation order stable.
     * 
     * @param type A target class.
     */
    public void seed(Class type) {
        allocate(type);
    }

    /**
     * Pre-allocate the names of the declared members to keep the allocation order stable.
     * 
     * @param type A target class.
     */
    public void seedMembers(Class type) {
        for (Method method : type.getDeclaredMethods()) {
            if (!method.isSynthetic() && !method.isBridge()) {
                allocate(method);
            }
        }
        for (Field field : type.getDeclaredFields()) {
            if (!field.isSynthetic()) {
                allocate(field);
            }
        }
    }

    /**
     * Allocate the unique JavaScript name of the specified class.
     * 
     * @param type A target class.
     * @return A JavaScript identifier. The array type is suffixed by {@code []}.
     */
    public String allocate(Class type) {
        if (type == null) {
            return null;
        }

        if (type.isPrimitive()) {
            return type.getName();
        }

        if (type.isArray()) {
            return allocate(type.getComponentType()) + "[]";
        }

        String name = classes.get(type);
        if (name == null) {
            name = compute(baseName(type), usedClasses);
            classes.put(type, name);
        }
        return name;
    }

    /**
     * Compute the preferred class name.
     * 
     * @param type A target class.
     * @return A preferred name.
     */
    private static String baseName(Class type) {
        if (type.isAnonymousClass() || type.isLocalClass()) {
            String binary = type.getName();
            return binary.substring(binary.lastIndexOf('.') + 1);
        }

        String name = type.getSimpleName();
        if (name.isEmpty()) {
            String binary = type.getName();
            name = binary.substring(binary.lastIndexOf('.') + 1);
        }
        return name;
    }

    /**
     * Allocate the unique JavaScript name of the specified method.
     * 
     * @param method A target method.
     * @return A JavaScript identifier.
     */
    public String allocate(Method method) {
        if (method.isAnnotationPresent(js.lang.Native.class) || Classlib.isNative(method.getDeclaringClass())) {
            return method.getName();
        }

        Method resolved = Classlib.resolve(method);
        if (isFixed(resolved)) {
            return resolved.getName();
        }

        String name = methods.get(resolved);
        if (name == null) {
            String key = methodKey(resolved);
            name = members.get(key);
            if (name == null) {
                name = compute(resolved.getName(), usedMembers);
                members.put(key, name);
            }
            methods.put(resolved, name);
        }
        return name;
    }

    /**
     * Allocate the unique JavaScript name of the specified field.
     * 
     * @param field A target field.
     * @return A JavaScript identifier.
     */
    public String allocate(Field field) {
        if (isFixed(field.getDeclaringClass())) {
            return field.getName();
        }
        String name = fields.get(field);
        if (name == null) {
            String key = "F:" + field.getDeclaringClass().getName() + ":" + field.getName();
            name = members.get(key);
            if (name == null) {
                name = compute(field.getName(), usedMembers);
                members.put(key, name);
            }
            fields.put(field, name);
        }
        return name;
    }

    /**
     * Compute the logical key of the method. An override has the same key, while an overload has a
     * different key.
     * 
     * @param method A target method.
     * @return A logical key.
     */
    private static String methodKey(Method method) {
        String descriptor = org.objectweb.asm.Type.getMethodDescriptor(method);
        String parameters = descriptor.substring(0, descriptor.indexOf(')') + 1);
        String name = method.getName() + parameters;

        if (Modifier.isStatic(method.getModifiers()) || Modifier.isPrivate(method.getModifiers())) {
            return "M:" + method.getDeclaringClass().getName() + ":" + name;
        }
        return "M:" + name;
    }

    /**
     * Check whether the method name is fixed by the runtime or the native bridge.
     * 
     * @param method A target method.
     * @return A result.
     */
    private static boolean isFixed(Method method) {
        Class owner = method.getDeclaringClass();
        if (isFixed(owner) || owner.isAnnotation()) {
            return true;
        }
        if (owner.isEnum() && (method.getName().equals("values") || method.getName().equals("valueOf"))) {
            return true;
        }
        String descriptor = org.objectweb.asm.Type.getMethodDescriptor(method);
        return switch (method.getName()) {
        case "equals" -> descriptor.equals("(Ljava/lang/Object;)Z");
        case "hashCode" -> descriptor.equals("()I");
        case "toString" -> descriptor.equals("()Ljava/lang/String;");
        case "compareTo" -> descriptor.equals("(Ljava/lang/Object;)I");
        case "iterator" -> descriptor.equals("()Ljava/util/Iterator;");
        case "hasNext" -> descriptor.equals("()Z");
        case "next" -> descriptor.equals("()Ljava/lang/Object;");
        case "close" -> descriptor.equals("()V");
        default -> false;
        };
    }

    /**
     * Check whether the class is provided outside the user code.
     * 
     * @param type A target class.
     * @return A result.
     */
    private static boolean isFixed(Class type) {
        return type == Object.class || Classlib.isNative(type) || Classlib.isRuntime(type) || Classlib.isReflection(type);
    }

    /**
     * Create a unique name.
     * 
     * @param base A preferred name.
     * @param used A used names.
     * @return A unique name.
     */
    private static String compute(String base, Set<String> used) {
        String name = sanitize(base);
        String candidate = name;

        for (int suffix = 2; used.contains(candidate); suffix++) {
            candidate = name + "$" + suffix;
        }

        used.add(candidate);
        return candidate;
    }

    /**
     * Convert the given name into a valid JavaScript identifier.
     * 
     * @param name A target name.
     * @return A valid identifier.
     */
    private static String sanitize(String name) {
        StringBuilder builder = new StringBuilder(name.length() + 1);

        for (int i = 0; i < name.length(); i++) {
            char code = name.charAt(i);

            if (i == 0) {
                if (Character.isJavaIdentifierStart(code)) {
                    builder.append(code);
                } else {
                    builder.append('_').append(Character.isJavaIdentifierPart(code) ? code : '_');
                }
            } else {
                builder.append(Character.isJavaIdentifierPart(code) ? code : '_');
            }
        }

        String sanitized = builder.toString();
        return reserved.contains(sanitized) ? sanitized + "$" : sanitized;
    }
}
