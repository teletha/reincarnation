/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import kiss.I;
import kiss.Model;

public class Classes {

    /**
     * Check whether the given member is abstract or not.
     * 
     * @param member A target member to test.
     * @return A result.
     */
    public static boolean isAbstract(Member member) {
        return member != null && Modifier.isAbstract(member.getModifiers());
    }

    /**
     * Check whether the given member is native or not.
     * 
     * @param member A target member to test.
     * @return A result.
     */
    public static boolean isNative(Member member) {
        return member != null && Modifier.isNative(member.getModifiers());
    }

    /**
     * Check whether the given member is static or not.
     * 
     * @param member A target member to test.
     * @return A result.
     */
    public static boolean isStatic(Member member) {
        return member != null && Modifier.isStatic(member.getModifiers());
    }

    /**
     * Check whether the given class is static or not.
     * 
     * @param clazz A target class to test.
     * @return A result.
     */
    public static boolean isStatic(Class clazz) {
        return clazz != null && Modifier.isStatic(clazz.getModifiers());
    }

    /**
     * Check whether the given member is NOT static or not.
     * 
     * @param member A target member to test.
     * @return A result.
     */
    public static boolean isNonStatic(Member member) {
        return !isStatic(member);
    }

    /**
     * Check whether the given class is NOT static or not.
     * 
     * @param clazz A target class to test.
     * @return A result.
     */
    public static boolean isNonStatic(Class clazz) {
        return !isStatic(clazz);
    }

    /**
     * Helper to check member-like type.
     * 
     * @param clazz A target to check.
     * @return A result.
     */
    public static boolean isMemberLike(Class clazz) {
        return clazz.isAnonymousClass() || clazz.isLocalClass() || clazz.isMemberClass();
    }

    /**
     * Check member type.
     * 
     * @return
     */
    public static boolean isMember(Class encloser, Class target) {
        return enclosings(target).contains(encloser);
    }

    /**
     * Check whether the given class is sealed sub class or not.
     * 
     * @param clazz
     * @return
     */
    public static boolean isSealedSubclass(Class clazz) {
        for (Class type : Model.collectTypes(clazz)) {
            if (type.isSealed()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculate root of enclosing class.
     * 
     * @param clazz A target class.
     * @return A root of enclosing class.
     */
    public static Class enclosingRoot(Class clazz) {
        LinkedList<Class> list = enclosings(clazz);

        return list.isEmpty() ? clazz : list.getFirst();
    }

    /**
     * Collect enclosing classes.
     * 
     * @param clazz A target class.
     * @return A list of enclosing classes.
     */
    public static LinkedList<Class> enclosings(Class clazz) {
        LinkedList<Class> list = new LinkedList();

        while (clazz.getEnclosingClass() != null) {
            clazz = clazz.getEnclosingClass();
            list.addFirst(clazz);
        }
        return list;
    }

    public static Set<Class> inner(Class clazz) {
        Set<Class> set = new HashSet();
        inner(clazz, set);
        return set;
    }

    private static void inner(Class clazz, Set<Class> set) {
        set.add(clazz);

        for (Class declared : clazz.getDeclaredClasses()) {
            inner(declared, set);
        }
    }

    /**
     * Check whether the specified method is unwrapper for primitive or not.
     * 
     * @param method
     * @return
     */
    public static boolean isUnwrapper(Method method) {
        Class type = method.getDeclaringClass();
        String name = method.getName();

        // check parameter
        Parameter[] params = method.getParameters();
        if (params.length != 0) {
            return false;
        }

        // check owner and method name
        return (type == Integer.class && name.equals("intValue")) // for int
                || (type == Long.class && name.equals("longValue")) // for long
                || (type == Float.class && name.equals("floatValue")) // for float
                || (type == Double.class && name.equals("doubleValue")) // for double
                || (type == Boolean.class && name.equals("booleanValue")) // for boolean
                || (type == Byte.class && name.equals("byteValue")) // for byte
                || (type == Short.class && name.equals("shortValue")) // for short
                || (type == Character.class && name.equals("intValue")); // for char
    }

    /**
     * Converts a wrapper class to its corresponding primitive class.
     * <p>
     * This method checks if the given {@code Class} object represents a wrapper type
     * (e.g., {@link Integer}, {@link Long}, {@link Double}, etc.) and returns the
     * corresponding primitive type (e.g., {@code int.class}, {@code long.class},
     * {@code double.class}, etc.).
     * If the provided type is not a wrapper class, it is returned unchanged.
     * </p>
     *
     * @param type the {@code Class} object to be unwrapped
     * @return the corresponding primitive {@code Class} object if the input is a wrapper type;
     *         otherwise, the input {@code Class} object itself
     * @throws NullPointerException if {@code type} is {@code null}
     */
    public static Class unwrap(Class type) {
        if (type == Integer.class) {
            return int.class;
        } else if (type == Long.class) {
            return long.class;
        } else if (type == Float.class) {
            return float.class;
        } else if (type == Double.class) {
            return double.class;
        } else if (type == Short.class) {
            return short.class;
        } else if (type == Byte.class) {
            return byte.class;
        } else if (type == Boolean.class) {
            return boolean.class;
        } else if (type == Character.class) {
            return char.class;
        } else {
            return type;
        }
    }

    /**
     * Check whether the specified method is wrapper for primitive or not.
     * 
     * @param method
     * @return
     */
    public static boolean isWrapper(Method method) {
        Class type = method.getDeclaringClass();
        String name = method.getName();

        // check parameter
        Parameter[] params = method.getParameters();
        if (params.length != 1 || I.wrap(params[0].getType()) != type) {
            return false;
        }

        // check owner and method name
        return (type == Integer.class && name.equals("valueOf")) // for int
                || (type == Long.class && name.equals("valueOf")) // for long
                || (type == Float.class && name.equals("valueOf")) // for float
                || (type == Double.class && name.equals("valueOf")) // for double
                || (type == Boolean.class && name.equals("valueOf")) // for boolean
                || (type == Byte.class && name.equals("valueOf")) // for byte
                || (type == Short.class && name.equals("valueOf")) // for short
                || (type == Character.class && name.equals("valueOf")); // for char
    }

    /**
     * Calling Executable#getGenericParameterTypes in class files compiled with Javac and ECJ
     * returns different results for some APIs. (Enum constructors only?).
     * 
     * It seems to be caused by whether the parameter qualifier is Synthetic or Mandated, but I
     * don't know the details.
     * 
     * @param executable
     * @return
     */
    public static Type[] fixGenericParameterTypes(Executable executable) {
        Class clazz = executable.getDeclaringClass();
        Type[] types = executable.getGenericParameterTypes();
        Class[] params = executable.getParameterTypes();

        if (types.length < params.length) {
            if (clazz.isEnum() && Constructor.class.isInstance(executable)) {
                Type[] fixed = new Type[params.length];
                System.arraycopy(types, 0, fixed, params.length - types.length, types.length);
                fixed[0] = String.class; // enum name
                fixed[1] = int.class; // enum ordinal
                return fixed;
            }

            if (clazz.isLocalClass() && Constructor.class.isInstance(executable)) {
                Type[] fixed = new Type[params.length];
                System.arraycopy(params, 0, fixed, 0, params.length);
                return fixed;
            }
        }

        return executable.getGenericParameterTypes();
    }
}