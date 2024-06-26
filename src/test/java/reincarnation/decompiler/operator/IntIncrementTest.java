/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.operator;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class IntIncrementTest extends CodeVerifier {

    @CrossDecompilerTest
    void sequence() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                value++;
                ++value;
                value++;
                return ++value;
            }
        });
    }

    @CrossDecompilerTest
    void incrementFieldInMethodCall() {
        verify(new TestCode.Int() {

            private int index = 0;

            @Override
            public int run() {
                return call(index++);
            }

            private int call(int value) {
                return index + value * 10;
            }
        });
    }

    @CrossDecompilerTest
    void decrementFieldInMethodCall() {
        verify(new TestCode.Int() {

            private int index = 0;

            @Override
            public int run() {
                return call(index--);
            }

            private int call(int value) {
                return index + value * 10;
            }
        });
    }

    @CrossDecompilerTest
    void preincrementFieldInMethodCall() {
        verify(new TestCode.Int() {

            private int index = 0;

            @Override
            public int run() {
                return call(++index);
            }

            private int call(int value) {
                return index + value * 10;
            }
        });
    }

    @CrossDecompilerTest
    void predecrementFieldInMethodCall() {
        verify(new TestCode.Int() {

            private int index = 0;

            @Override
            public int run() {
                return call(--index);
            }

            private int call(int value) {
                return index + value * 10;
            }
        });
    }

    @CrossDecompilerTest
    void incrementVariableInMethodCall() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                int sum1 = sum(value++, value++, value++);
                int sum2 = sum(++sum1, ++sum1, ++sum1);
                return sum(++sum2, sum2++, ++sum2);
            }

            private int sum(int a, int b, int c) {
                return a + b + c;
            }
        });
    }

    @CrossDecompilerTest
    void decrementVariableInMethodCall() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                int sum1 = sum(value--, value--, value--);
                int sum2 = sum(--sum1, --sum1, --sum1);
                return sum(--sum2, sum2--, --sum2);
            }

            private int sum(int a, int b, int c) {
                return a + b + c;
            }
        });
    }

    @CrossDecompilerTest
    void incrementFieldInFieldAccess() {
        verify(new TestCode.Int() {

            private int index = 1;

            private int count = 2;

            @Override
            public int run() {
                index = count++;

                return count + index * 10;
            }
        });
    }

    @CrossDecompilerTest
    void decrementFieldInFieldAccess() {
        verify(new TestCode.Int() {

            private int index = 1;

            private int count = 2;

            @Override
            public int run() {
                index = count--;

                return count + index * 10;
            }
        });
    }

    @CrossDecompilerTest
    void preincrementFieldInFieldAccess() {
        verify(new TestCode.Int() {

            private int index = 1;

            private int count = 2;

            @Override
            public int run() {
                index = ++count;

                return count + index * 10;
            }
        });
    }

    @CrossDecompilerTest
    void predecrementFieldInFieldAccess() {
        verify(new TestCode.Int() {

            private int index = 1;

            private int count = 2;

            @Override
            public int run() {
                index = --count;

                return count + index * 10;
            }
        });
    }

    @CrossDecompilerTest
    void postIncrementParentFieldInLocalVariableAccess() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Parent parent = new Parent();
                int old = parent.child.postIncrement();
                return parent.value + old;
            }
        });
    }

    @CrossDecompilerTest
    void preIncrementParentFieldInLocalVariableAccess() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Parent parent = new Parent();
                int old = parent.child.preIncrement();
                return parent.value + old;
            }
        });
    }

    @CrossDecompilerTest
    void postDecrementParentFieldInLocalVariableAccess() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Parent parent = new Parent();
                int old = parent.child.postDecrement();
                return parent.value + old;
            }
        });
    }

    @CrossDecompilerTest
    void preDecrementParentFieldInLocalVariableAccess() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Parent parent = new Parent();
                int old = parent.child.preDecrement();
                return parent.value + old;
            }
        });
    }

    /**
     * @version 2014/01/01 20:56:31
     */
    private static class Parent {

        int value = 10;

        private Child child = new Child();

        /**
         * @version 2014/01/01 20:56:39
         */
        private class Child {

            int postIncrement() {
                int old2 = value++;

                return old2;
            }

            int preIncrement() {
                int old3 = ++value;

                return old3;
            }

            int postDecrement() {
                int old4 = value--;

                return old4;
            }

            int preDecrement() {
                int old5 = --value;

                return old5;
            }
        }
    }
}