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

import org.junit.jupiter.api.Test;

import reincarnation.transpiler.BundleVerifier;

/**
 * Test the bundled JavaScript program which contains the whole dependency closure.
 */
class BundleTest extends BundleVerifier {

    @Test
    void nameCollision() throws Exception {
        verify(Collision.class, "run");
    }

    @Test
    void interfaceInstanceof() throws Exception {
        verify(InterfaceInstanceof.class, "run");
    }

    @Test
    void inheritance() throws Exception {
        verify(Inheritance.class, "run");
    }

    @Test
    void superMethod() throws Exception {
        verify(SuperMethod.class, "run");
    }

    @Test
    void staticInitializationOrder() throws Exception {
        verify(StaticOrder.class, "run");
    }

    @Test
    void enumBundle() throws Exception {
        verify(EnumBundle.class, "run");
    }

    @Test
    void anonymousClass() throws Exception {
        verify(AnonymousClass.class, "run");
    }

    @Test
    void parseInt() throws Exception {
        verify(ParseInt.class, "run");
    }

    @Test
    void stringBuilder() throws Exception {
        verify(StringBuilderTest.class, "run");
    }

    @Test
    void longBundle() throws Exception {
        verify(LongBundle.class, "run");
    }

    /** Verify the classes which have the same simple name in the different packages. */
    static class Collision {

        static int run() {
            return new reincarnation.transpiler.bundle.a.Twin().value() + new reincarnation.transpiler.bundle.b.Twin().value();
        }
    }

    /** The shape interface. */
    interface Shape {
    }

    /** The circle. */
    static class Circle implements Shape {
    }

    /** The square which does not implement the shape. */
    static class Square {
    }

    /** Verify the interface based instanceof. */
    static class InterfaceInstanceof {

        static int run() {
            Object circle = new Circle();
            Object square = new Square();

            int count = 0;
            if (circle instanceof Shape) {
                count++;
            }
            if (square instanceof Shape) {
                count++;
            }
            if (circle instanceof Object) {
                count++;
            }
            if (circle instanceof Circle) {
                count++;
            }
            if (square instanceof Circle) {
                count++;
            }
            return count;
        }
    }

    /** The animal. */
    abstract static class Animal {

        abstract String name();

        String greet() {
            return "hi " + name();
        }
    }

    /** The dog. */
    static class Dog extends Animal {

        @Override
        String name() {
            return "dog";
        }
    }

    /** Verify the inheritance and the virtual dispatch. */
    static class Inheritance {

        static int run() {
            return new Dog().greet().length();
        }
    }

    /** The first parent. */
    static class FirstParent {

        int value() {
            return 1;
        }
    }

    /** The child which calls the super method. */
    static class SecondChild extends FirstParent {

        @Override
        int value() {
            return super.value() + 1;
        }
    }

    /** Verify the super method invocation. */
    static class SuperMethod {

        static int run() {
            return new SecondChild().value();
        }
    }

    /** The first class which refers to the second class in its static initializer. */
    static class First {

        static int value = Second.value + 1;
    }

    /** The second class. */
    static class Second {

        static int value = 10;
    }

    /** Verify the class initialization order. */
    static class StaticOrder {

        static int run() {
            return First.value;
        }
    }

    /** The color. */
    enum Color {
        RED, GREEN, BLUE
    }

    /** Verify the enum in the bundle. */
    static class EnumBundle {

        static int run() {
            return Color.values().length + Color.valueOf("GREEN").ordinal();
        }
    }

    /** Verify the anonymous class in the static initializer. */
    static class AnonymousClass {

        static Runnable task = new Runnable() {

            @Override
            public void run() {
            }
        };

        static int run() {
            return task == null ? 0 : 1;
        }
    }

    /** Verify the number parsing runtime. */
    static class ParseInt {

        static int run() {
            return Integer.parseInt("42") + Integer.parseInt("-7") + Integer.parseInt("ff", 16);
        }
    }

    /** Verify the string builder runtime. */
    static class StringBuilderTest {

        static int run() {
            StringBuilder builder = new StringBuilder();
            builder.append("ab").append(12).append('c');
            builder.insert(1, "X");
            builder.reverse();
            return builder.toString().length() + builder.length();
        }
    }

    /** Verify the 64 bit long value. */
    static class LongBundle {

        static long value = Long.MIN_VALUE;

        static long run() {
            return value + Long.MAX_VALUE;
        }
    }
}
