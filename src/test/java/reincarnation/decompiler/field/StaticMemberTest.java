/*
 * Copyright (C) 2020 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.field;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.CodeVerifier;

/**
 * @version 2018/10/26 12:29:11
 */
class StaticMemberTest extends CodeVerifier {

    @Test
    void StringValueOf() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return String.valueOf((Object) null);
            }
        });
    }

    @Test
    void StaticMethod() {
        verify(new StaticMethod());
    }

    /**
     * @version 2018/10/26 12:30:37
     */
    private static class StaticMethod implements TestCode.Int {

        @Override
        public int run() {
            return compute();
        }

        private static int compute() {
            return 1;
        }
    }

    @Test
    void StaticMethodWithParam() {
        verify(new StaticMethodWithParam());
    }

    /**
     * @version 2018/10/26 12:30:48
     */
    private static class StaticMethodWithParam implements TestCode.IntParam {

        @Override
        public int run(int value) {
            return compute(value);
        }

        private static int compute(int value) {
            return value;
        }
    }

    @Test
    void GetStaticField() {
        verify(new GetStaticField());
    }

    /**
     * @version 2018/10/26 12:30:58
     */
    private static class GetStaticField implements TestCode.Int {

        private static int field = 10;

        @Override
        public int run() {
            return field;
        }
    }

    @Test
    void GetStaticFieldFromStaticMethod() {
        verify(new GetStaticFieldFromStaticMethod());
    }

    /**
     * @version 2018/10/26 12:31:04
     */
    private static class GetStaticFieldFromStaticMethod implements TestCode.Int {

        private static int field = 10;

        @Override
        public int run() {
            return compute();
        }

        private static int compute() {
            return field;
        }
    }

    @Test
    void GetStaticFieldFromSubClass() {
        verify(new GetStaticFieldFromSubClass());
    }

    /**
     * @version 2018/10/26 12:31:14
     */
    private static class StaticFieldParent {

        protected static final int superField = "ok".length();
    }

    /**
     * @version 2018/10/26 12:31:17
     */
    private static class GetStaticFieldFromSubClass extends StaticFieldParent implements TestCode.Int {

        @Override
        public int run() {
            return superField;
        }
    }

    @Test
    void SetStaticField() {
        verify(new SetStaticField());
    }

    /**
     * @version 2018/10/26 12:31:25
     */
    private static class SetStaticField implements TestCode.IntParam {

        private static int field;

        @Override
        public int run(int value) {
            field = value;

            return field;
        }
    }

    @Test
    void SetStaticFieldFromSubClass() {
        verify(new SetStaticFieldFromSubClass());
    }

    /**
     * @version 2018/10/26 15:31:23
     */
    private static class SetStaticFieldParent {
        protected static int superField = 10;
    }

    /**
     * @version 2018/10/26 12:31:33
     */
    private static class SetStaticFieldFromSubClass extends SetStaticFieldParent implements TestCode.IntParam {

        @Override
        public int run(int value) {
            superField = value;

            return superField;
        }
    }

    @Test
    void StaticInitialization() {
        verify(new StaticInitialization());
    }

    /**
     * @version 2018/10/26 12:31:40
     */
    private static class StaticInitialization implements TestCode.Int {

        private static final int field;

        static {
            field = 10;
        }

        @Override
        public int run() {
            return field;
        }
    }

    @Test
    void Lazy() {
        verify(new Lazy());
    }

    /**
     * @version 2018/10/26 12:31:51
     */
    private static class Lazy implements TestCode.Text {

        private static String value;

        @Override
        public String run() {
            if (value != null) {
                return value;
            }

            value = "test";
            return value;
        }
    }
}