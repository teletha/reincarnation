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

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Signals that the bytecode can not be translated into JavaScript.
 * <p>
 * Unlike the previous behavior which silently dropped the unsupported class, this exception carries
 * the class and the member which caused the failure so that the caller can fix the mapping.
 * </p>
 */
public class TranslationException extends RuntimeException {

    /** The target class. */
    private final Class type;

    /** The target member name. */
    private final String member;

    /**
     * Create an exception for the class.
     * 
     * @param type A target class.
     * @param cause A cause.
     */
    public TranslationException(Class type, Throwable cause) {
        this(type, null, cause);
    }

    /**
     * Create an exception for the method.
     * 
     * @param method A target method.
     */
    public TranslationException(Method method) {
        this(method.getDeclaringClass(), method.getName() + descriptor(method), null);
    }

    /**
     * Create an exception for the constructor.
     * 
     * @param constructor A target constructor.
     */
    public TranslationException(Constructor constructor) {
        this(constructor.getDeclaringClass(), "<init>" + descriptor(constructor), null);
    }

    /**
     * Create an exception.
     * 
     * @param type A target class.
     * @param member A target member.
     * @param cause A cause.
     */
    public TranslationException(Class type, String member, Throwable cause) {
        super(message(type, member), cause);

        this.type = type;
        this.member = member;
    }

    /**
     * The target class.
     * 
     * @return A target class.
     */
    public Class type() {
        return type;
    }

    /**
     * The target member.
     * 
     * @return A target member.
     */
    public String member() {
        return member;
    }

    /**
     * Build the error message.
     * 
     * @param type A target class.
     * @param member A target member.
     * @return A message.
     */
    private static String message(Class type, String member) {
        StringBuilder message = new StringBuilder("Can't translate ");

        if (type != null) {
            message.append(type.getName());
        }
        if (member != null) {
            message.append("#").append(member);
        }
        message.append(" into JavaScript. Add the mapping to the JavaScript runtime.");
        return message.toString();
    }

    /**
     * Compute the method descriptor.
     * 
     * @param method A target method.
     * @return A descriptor.
     */
    private static String descriptor(Method method) {
        return org.objectweb.asm.Type.getMethodDescriptor(method);
    }

    /**
     * Compute the constructor descriptor.
     * 
     * @param constructor A target constructor.
     * @return A descriptor.
     */
    private static String descriptor(Constructor constructor) {
        return org.objectweb.asm.Type.getConstructorDescriptor(constructor);
    }
}
