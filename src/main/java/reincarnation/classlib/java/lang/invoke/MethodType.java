/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.lang.invoke;

import java.util.List;

/**
 * The Java implementation of {@link java.lang.invoke.MethodType} which is transpiled into
 * JavaScript.
 */
public final class MethodType implements reincarnation.classlib.java.io.Serializable {

    /** The return type. */
    private final Class returnType;

    /** The parameter types. */
    private final Class[] parameterTypes;

    /**
     * Create the method type.
     * 
     * @param returnType A return type.
     * @param parameterTypes Parameter types.
     */
    MethodType(Class returnType, Class[] parameterTypes) {
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
    }

    /**
     * Create the method type.
     * 
     * @param returnType A return type.
     * @return A method type.
     */
    public static MethodType methodType(Class returnType) {
        return new MethodType(returnType, new Class[0]);
    }

    /**
     * Create the method type.
     * 
     * @param returnType A return type.
     * @param parameterType A parameter type.
     * @return A method type.
     */
    public static MethodType methodType(Class returnType, Class parameterType) {
        return new MethodType(returnType, new Class[] {parameterType});
    }

    /**
     * Create the method type.
     * 
     * @param returnType A return type.
     * @param parameterTypes Parameter types.
     * @return A method type.
     */
    public static MethodType methodType(Class returnType, Class... parameterTypes) {
        return new MethodType(returnType, parameterTypes);
    }

    /**
     * Create the method type.
     * 
     * @param returnType A return type.
     * @param parameters A parameter type.
     * @return A method type.
     */
    public static MethodType methodType(Class returnType, MethodType parameters) {
        return new MethodType(returnType, parameters.parameterArray());
    }

    /**
     * Create the method type.
     * 
     * @param returnType A return type.
     * @param parameterTypes Parameter types.
     * @return A method type.
     */
    public static MethodType methodType(Class returnType, List parameterTypes) {
        return new MethodType(returnType, (Class[]) parameterTypes.toArray(new Class[parameterTypes.size()]));
    }

    /**
     * Get the return type.
     * 
     * @return A return type.
     */
    public Class returnType() {
        return returnType;
    }

    /**
     * Get the parameter types.
     * 
     * @return Parameter types.
     */
    public Class[] parameterArray() {
        return (Class[]) reincarnation.classlib.java.util.Arrays.copyOf(parameterTypes, parameterTypes.length);
    }

    /**
     * Get the number of parameters.
     * 
     * @return A number.
     */
    public int parameterCount() {
        return parameterTypes.length;
    }

    /**
     * Get the parameter type.
     * 
     * @param index An index.
     * @return A parameter type.
     */
    public Class parameterType(int index) {
        return parameterTypes[index];
    }

    /**
     * Get the parameter types.
     * 
     * @return Parameter types.
     */
    public reincarnation.classlib.java.util.List parameterList() {
        return reincarnation.classlib.java.util.List.of((Object[]) parameterTypes);
    }
}
