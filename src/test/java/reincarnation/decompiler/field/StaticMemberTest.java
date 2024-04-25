/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.field;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class StaticMemberTest extends CodeVerifier {

    @CrossDecompilerTest
    void StringValueOf() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return String.valueOf((Object) null);
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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