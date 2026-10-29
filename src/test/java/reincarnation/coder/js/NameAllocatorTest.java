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

import org.junit.jupiter.api.Test;

/**
 * Test for {@link NameAllocator}.
 */
class NameAllocatorTest {

    @Test
    void preferred() {
        NameAllocator names = new NameAllocator();

        assert names.allocate(String.class).equals("String");
        assert names.allocate(int.class).equals("int");
        assert names.allocate(void.class).equals("void");
    }

    @Test
    void stable() {
        NameAllocator names = new NameAllocator();

        String first = names.allocate(First.Twin.class);
        String second = names.allocate(First.Twin.class);

        assert first.equals("Twin");
        assert first.equals(second);
    }

    @Test
    void collision() {
        NameAllocator names = new NameAllocator();

        String first = names.allocate(First.Twin.class);
        String second = names.allocate(Second.Twin.class);

        assert first.equals("Twin");
        assert !first.equals(second);
        assert isValid(second);
    }

    @Test
    void collisionIsDeterministic() {
        NameAllocator first = new NameAllocator();
        NameAllocator second = new NameAllocator();

        assert first.allocate(First.Twin.class).equals(second.allocate(First.Twin.class));
        assert first.allocate(Second.Twin.class).equals(second.allocate(Second.Twin.class));
    }

    @Test
    void seedKeepsPreferredName() {
        NameAllocator names = new NameAllocator();

        names.seed(Second.Twin.class);

        assert names.allocate(Second.Twin.class).equals("Twin");
        assert !names.allocate(First.Twin.class).equals("Twin");
    }

    @Test
    void array() {
        NameAllocator names = new NameAllocator();

        assert names.allocate(String[].class).equals("String[]");
        assert names.allocate(int[][].class).equals("int[][]");
    }

    @Test
    void anonymous() {
        NameAllocator names = new NameAllocator();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
            }
        };

        String name = names.allocate(runnable.getClass());

        assert isValid(name);
        assert name.startsWith("NameAllocatorTest$");
    }

    @Test
    void methodOverride() throws Exception {
        NameAllocator names = new NameAllocator();

        Method base = MethodBase.class.getDeclaredMethod("f", int.class);
        Method override = MethodSub.class.getDeclaredMethod("f", int.class);
        Method overload = MethodBase.class.getDeclaredMethod("f", String.class);

        assert names.allocate(base).equals(names.allocate(override));
        assert !names.allocate(base).equals(names.allocate(overload));
    }

    @Test
    void staticMethodIsUnique() throws Exception {
        NameAllocator names = new NameAllocator();

        Method first = StaticMethodFirst.class.getDeclaredMethod("g", int.class);
        Method second = StaticMethodSecond.class.getDeclaredMethod("g", int.class);

        assert !names.allocate(first).equals(names.allocate(second));
    }

    @Test
    void hiddenField() throws Exception {
        NameAllocator names = new NameAllocator();

        Field base = FieldBase.class.getDeclaredField("x");
        Field sub = FieldSub.class.getDeclaredField("x");

        assert !names.allocate(base).equals(names.allocate(sub));
        assert names.allocate(base).equals(names.allocate(base));
    }

    /**
     * Check whether the given name is a valid JavaScript identifier.
     * 
     * @param name A target name.
     * @return A result.
     */
    private boolean isValid(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        if (!Character.isJavaIdentifierStart(name.charAt(0))) {
            return false;
        }
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isJavaIdentifierPart(name.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /** The first class which declares {@code Twin}. */
    static class First {

        /** A twin. */
        static class Twin {
        }
    }

    /** The second class which declares {@code Twin}. */
    static class Second {

        /** A twin. */
        static class Twin {
        }
    }

    /** The base method. */
    static class MethodBase {

        void f(int value) {
        }

        void f(String value) {
        }
    }

    /** The derived method. */
    static class MethodSub extends MethodBase {

        @Override
        void f(int value) {
        }
    }

    /** The first static method. */
    static class StaticMethodFirst {

        static void g(int value) {
        }
    }

    /** The second static method. */
    static class StaticMethodSecond {

        static void g(int value) {
        }
    }

    /** The base field. */
    static class FieldBase {

        int x;
    }

    /** The derived field. */
    static class FieldSub extends FieldBase {

        int x;
    }
}
