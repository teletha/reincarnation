/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.method;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class MethodTest extends CodeVerifier {

    @CrossDecompilerTest
    void primitiveInt() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return compute();
            }

            private int compute() {
                return -10;
            }
        });
    }

    @CrossDecompilerTest
    void primitiveLong() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return compute();
            }

            private long compute() {
                return -10;
            }
        });
    }

    @CrossDecompilerTest
    void primitiveObject() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return compute();
            }

            private String compute() {
                return "minus";
            }
        });
    }

    @CrossDecompilerTest
    void paramInt() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return compute(value);
            }

            private int compute(int value) {
                return 100 + value;
            }
        });
    }

    @CrossDecompilerTest
    void paramLong() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return compute(value);
            }

            private long compute(long value) {
                return 100 + value;
            }
        });
    }

    @CrossDecompilerTest
    void paramObject() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String value) {
                return compute(value);
            }

            private String compute(String value) {
                return value + "!!!";
            }
        });
    }

    @CrossDecompilerTest
    void params() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return compute(value, value + 1);
            }

            private int compute(int first, int second) {
                return first * second;
            }
        });
    }

    @CrossDecompilerTest
    void paramArray() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                int[] ints = {value, value + 1, value + 2};

                return compute(ints);
            }

            private int compute(int[] values) {
                int i = 0;

                for (int j = 0; j < values.length; j++) {
                    i += values[j];
                }

                return i;
            }
        });
    }

    @CrossDecompilerTest
    void assignLocalInt() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                int local = 0;
                int result = compute(local = value);

                return local + result;
            }

            private int compute(int value) {
                return 100 + value;
            }
        });
    }

    @CrossDecompilerTest
    void assignUninitializedLocalInt() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                int local;
                return compute(local = value) + local;
            }

            private int compute(int value) {
                return 100 + value;
            }
        });
    }

    @CrossDecompilerTest
    void assignParamInt() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return compute(value = 10);
            }

            private int compute(int value) {
                return 100 + value;
            }
        });
    }

    @CrossDecompilerTest
    void assignLocalLong() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                long local = 0;
                long result = compute(local = value);

                return local + result;
            }

            private long compute(long value) {
                return 100 + value;
            }
        });
    }

    @CrossDecompilerTest
    void assignUninitializedLocalLong() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                long local;
                return compute(local = value) + local;
            }

            private long compute(long value) {
                return 100 + value;
            }
        });
    }

    @CrossDecompilerTest
    void assignParamLong() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                return compute(value = 10);
            }

            private long compute(long value) {
                return 100 + value;
            }
        });
    }

    @CrossDecompilerTest
    void assignLocalObject() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String value) {
                String local = "BASE";
                String result = compute(local = value);

                return local + result;
            }

            private String compute(String value) {
                return 100 + value;
            }
        });
    }

    @CrossDecompilerTest
    void assignUninitializedLocalObject() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String value) {
                String local;
                return compute(local = value) + local;
            }

            private String compute(String value) {
                return 100 + value;
            }
        });
    }

    @CrossDecompilerTest
    void assignParamObject() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String value) {
                return compute(value = "OK");
            }

            private String compute(String value) {
                return 100 + value;
            }
        });
    }

    @CrossDecompilerTest
    void AssignFieldParam() {
        verify(new AssignFieldParam());
    }

    /**
     * @version 2018/10/23 15:33:05
     */
    private static class AssignFieldParam implements TestCode.IntParam {

        private int local;

        @Override
        public int run(int value) {
            int result = compute(local = value);

            return local + result;
        }

        private int compute(int value) {
            return 100 + value;
        }
    }

    @CrossDecompilerTest
    void VariableParam() {
        verify(new VariableParam());
    }

    /**
     * @version 2018/10/23 15:33:10
     */
    private static class VariableParam implements TestCode.IntParam {

        @Override
        public int run(int value) {
            return compute(value, value + 1, value + 2);
        }

        private int compute(int... values) {
            int i = 0;

            for (int j = 0; j < values.length; j++) {
                i += values[j];
            }

            return i;
        }
    }

    @CrossDecompilerTest
    void VariableParamWithBase() {
        verify(new VariableParamWithBase());
    }

    /**
     * @version 2018/10/23 15:33:14
     */
    private static class VariableParamWithBase implements TestCode.IntParam {

        @Override
        public int run(int value) {
            return compute(value, value + 1, value + 2);
        }

        private int compute(int base, int... values) {
            int i = base * base;

            for (int j = 0; j < values.length; j++) {
                i += values[j];
            }

            return i;
        }
    }

    @CrossDecompilerTest
    void VariableParamWithBaseOnly() {
        verify(new VariableParamWithBaseOnly());
    }

    /**
     * @version 2018/10/23 15:33:19
     */
    private static class VariableParamWithBaseOnly implements TestCode.IntParam {

        @Override
        public int run(int value) {
            return compute(value);
        }

        private int compute(int base, int... values) {
            int i = base * base;

            for (int j = 0; j < values.length; j++) {
                i += values[j];
            }

            return i;
        }
    }

    @CrossDecompilerTest
    void Nest() {
        verify(new Nest());
    }

    /**
     * @version 2018/10/23 15:33:24
     */
    private static class Nest implements TestCode.IntParam {

        @Override
        public int run(int value) {
            return compute(value, nest(value));
        }

        private int compute(int first, int second) {
            return first * second;
        }

        private int nest(int value) {
            return value + value;
        }
    }

    @CrossDecompilerTest
    void Overload() {
        verify(new Overload());
    }

    /**
     * @version 2018/10/23 15:33:29
     */
    private static class Overload implements TestCode.IntParam {

        @Override
        public int run(int value) {
            return compute(value);
        }

        private int compute(int value) {
            return value * value;
        }

        private String compute(String value) {
            return value.substring(1);
        }
    }

    @CrossDecompilerTest
    void ExtendPublic() {
        verify(new ExtendPublic());
    }

    /**
     * @version 2018/10/23 15:33:39
     */
    private static class BasePublic {

        public int compute() {
            return 10;
        }
    }

    /**
     * @version 2018/10/23 15:33:36
     */
    private static class ExtendPublic extends BasePublic implements TestCode.IntParam {

        @Override
        public int run(int value) {
            return value + compute();
        }
    }

    @CrossDecompilerTest
    void ExtendProtected() {
        verify(new ExtendProtected());
    }

    /**
     * @version 2018/10/23 15:33:47
     */
    private static class BaseProtected {

        protected int compute() {
            return 10;
        }
    }

    /**
     * @version 2018/10/23 15:33:50
     */
    private static class ExtendProtected extends BaseProtected implements TestCode.IntParam {

        @Override
        public int run(int value) {
            return value + compute();
        }
    }

    @CrossDecompilerTest
    void ExtendPackage() {
        verify(new ExtendPackage());
    }

    /**
     * @version 2018/10/23 15:33:54
     */
    private static class BasePackage {

        int compute() {
            return 10;
        }
    }

    /**
     * @version 2018/10/23 15:35:06
     */
    private static class ExtendPackage extends BasePackage implements TestCode.IntParam {

        @Override
        public int run(int value) {
            return value + compute();
        }
    }

    @CrossDecompilerTest
    void Override() {
        verify(new OverrideChild());
    }

    /**
     * @version 2018/10/23 15:35:11
     */
    private static class OverrideBase {

        public int compute(int value) {
            return value + 1;
        }
    }

    /**
     * @version 2018/10/23 15:35:14
     */
    private static class OverrideChild extends OverrideBase implements TestCode.IntParam {

        @Override
        public int run(int value) {
            return compute(value);
        }

        @Override
        public int compute(int value) {
            return value - 1;
        }
    }

    @CrossDecompilerTest
    void callOverriddenMethod() {
        verify(new Child());
    }

    @CrossDecompilerTest
    void callOverriddenMethodFromInstance() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int param) {
                Child child = new Child();
                Parent parent = child;
                Ancestor ancestor = child;

                return child.compute(param) + parent.compute(param) + ancestor.compute(param);
            }
        });
    }

    /**
     * @version 2018/10/26 15:44:41
     */
    static class Ancestor {

        public int compute(int value) {
            return value + 1;
        }
    }

    /**
     * @version 2018/10/26 15:44:41
     */
    static class Parent extends Ancestor {

        @Override
        public int compute(int value) {
            return value + 10;
        }
    }

    /**
     * @version 2018/10/26 15:44:41
     */
    static class Child extends Parent implements TestCode.IntParam {

        @Override
        public int compute(int value) {
            return value + 100;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int run(int param) {
            return compute(param) + super.compute(param) + this.compute(param);
        }
    }

    /**
     * @version 2018/10/23 15:35:40
     */
    static class SuperChild extends Ancestor implements TestCode.IntParam {

        @Override
        public int run(int value) {
            return this.compute(value) + super.compute(value);
        }

        @Override
        public int compute(int value) {
            return value - 1;
        }
    }
}