/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.collection;

/**
 * Describes the type hierarchy for the reflection test.
 */
final class TypeDescriber {

    /**
     * Hide constructor.
     */
    private TypeDescriber() {
    }

    /**
     * Describe the class hierarchy.
     * 
     * @param type A target class.
     * @return A description.
     */
    static String describe(Class<?> type) {
        java.lang.StringBuilder builder = new java.lang.StringBuilder(type.getName());
        builder.append("|");
        builder.append(type.getSuperclass() == null ? "null" : type.getSuperclass().getName());
        builder.append("|");
        builder.append(type.isInterface());
        for (Class<?> inf : type.getInterfaces()) {
            builder.append("|").append(inf.getSimpleName());
        }
        return builder.toString();
    }
}
