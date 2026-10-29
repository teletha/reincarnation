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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Allocates a unique and valid JavaScript identifier for every Java class.
 * <p>
 * The JavaScript coder used to resolve the declared class name and the referenced class name by
 * different rules, so a class whose simple name collided with another class was declared by its
 * simple name but referenced by its canonical name (which is not a valid JavaScript identifier).
 * This allocator is the single source of truth for both sides.
 * </p>
 * <p>
 * The name of a class is allocated lazily and cached. The first class which claims a name keeps it,
 * so seeding the root class before its dependencies makes the output stable.
 * </p>
 */
public final class NameAllocator {

    /** The JavaScript reserved words which can not be used as an identifier. */
    private static final Set<String> reserved = Set.of("await", "break", "case", "catch", "class", "const", "continue",
            "debugger", "default", "delete", "do", "else", "enum", "export", "extends", "false", "finally", "for",
            "function", "if", "implements", "import", "in", "instanceof", "interface", "let", "new", "null", "package",
            "private", "protected", "public", "return", "static", "super", "switch", "this", "throw", "true", "try",
            "typeof", "var", "void", "while", "with", "yield");

    /** The allocated names. */
    private final Map<Class, String> allocated = new HashMap();

    /** The used names. */
    private final Set<String> used = new HashSet();

    /**
     * Pre-allocate the name of the specified class to keep the allocation order stable.
     * 
     * @param type A target class.
     */
    public void seed(Class type) {
        allocate(type);
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

        if (type.isArray()) {
            return allocate(type.getComponentType()) + "[]";
        }

        String name = allocated.get(type);
        if (name == null) {
            name = compute(type);
            allocated.put(type, name);
        }
        return name;
    }

    /**
     * Compute a new name.
     * 
     * @param type A target class.
     * @return A unique name.
     */
    private String compute(Class type) {
        String base = baseName(type);
        String candidate = base;

        for (int suffix = 2; used.contains(candidate); suffix++) {
            candidate = base + "$" + suffix;
        }

        used.add(candidate);
        return candidate;
    }

    /**
     * Compute the preferred name of the specified class.
     * 
     * @param type A target class.
     * @return A preferred name.
     */
    private String baseName(Class type) {
        if (type.isPrimitive()) {
            return type.getName();
        }

        String name;
        if (type.isAnonymousClass() || type.isLocalClass()) {
            String binary = type.getName();
            name = binary.substring(binary.lastIndexOf('.') + 1);
        } else {
            name = type.getSimpleName();
            if (name.isEmpty()) {
                String binary = type.getName();
                name = binary.substring(binary.lastIndexOf('.') + 1);
            }
        }

        return sanitize(name);
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
