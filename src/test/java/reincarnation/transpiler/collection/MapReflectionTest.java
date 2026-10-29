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

import org.junit.jupiter.api.Test;

import reincarnation.transpiler.BundleVerifier;

/**
 * Test the reflection metadata of the classlib collections.
 */
class MapReflectionTest extends BundleVerifier {

    @Test
    void mapType() throws Exception {
        verifyReflection(MapType.class, "run");
    }

    @Test
    void hashMapType() throws Exception {
        verifyReflection(HashMapType.class, "run");
    }

    @Test
    void entryType() throws Exception {
        verifyReflection(EntryType.class, "run");
    }

    @Test
    void forName() throws Exception {
        verifyReflection(ForName.class, "run");
    }

    /** Verify {@link java.util.Map} is an interface. */
    static class MapType {

        static String run() {
            Class<?> type = java.util.Map.class;
            return type.getName() + "|" + type.isInterface() + "|" + type.getSuperclass() + "|"
                    + type.getInterfaces().length;
        }
    }

    /** Verify the hierarchy of {@link java.util.HashMap}. */
    static class HashMapType {

        static String run() {
            Class<?> type = java.util.HashMap.class;
            StringBuilder builder = new StringBuilder(type.getName());
            builder.append("|").append(type.getSuperclass().getName());
            builder.append("|").append(type.getInterfaces().length);
            for (Class<?> inf : type.getInterfaces()) {
                builder.append("|").append(inf.getSimpleName());
            }
            builder.append("|").append(type.isInterface());
            return builder.toString();
        }
    }

    /** Verify {@link java.util.Map.Entry} is an interface. */
    static class EntryType {

        static String run() {
            Class<?> type = java.util.Map.Entry.class;
            return type.getName() + "|" + type.isInterface() + "|" + type.getSimpleName();
        }
    }

    /** Verify the JDK name is resolvable. */
    static class ForName {

        static String run() throws Exception {
            Class<?> type = java.util.HashMap.class;
            Class<?> map = java.util.Map.class;
            return type.getName() + "|" + Class.forName("java.util.HashMap").getName() + "|"
                    + Class.forName("java.util.Map").isInterface() + "|" + map.getName();
        }
    }
}
