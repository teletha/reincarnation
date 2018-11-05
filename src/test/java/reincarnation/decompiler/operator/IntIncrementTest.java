/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.operator;

import org.junit.jupiter.api.Test;

import reincarnation.Code;
import reincarnation.CodeVerifier;

/**
 * @version 2018/10/23 10:11:24
 */
class IntIncrementTest extends CodeVerifier {

    @Test
    void incrementFieldInMethodCall() {
        verify(new Code.Int() {

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

    @Test
    void decrementFieldInMethodCall() {
        verify(new Code.Int() {

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

    @Test
    void preincrementFieldInMethodCall() {
        verify(new Code.Int() {

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

    @Test
    void predecrementFieldInMethodCall() {
        verify(new Code.Int() {

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

    @Test
    void incrementVariableInMethodCall() {
        verify(new Code.IntParam() {

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

    @Test
    void decrementVariableInMethodCall() {
        verify(new Code.IntParam() {

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

    @Test
    void incrementFieldInFieldAccess() {
        verify(new Code.Int() {

            private int index = 1;

            private int count = 2;

            @Override
            public int run() {
                index = count++;

                return count + index * 10;
            }
        });
    }

    @Test
    void decrementFieldInFieldAccess() {
        verify(new Code.Int() {

            private int index = 1;

            private int count = 2;

            @Override
            public int run() {
                index = count--;

                return count + index * 10;
            }
        });
    }

    @Test
    void preincrementFieldInFieldAccess() {
        verify(new Code.Int() {

            private int index = 1;

            private int count = 2;

            @Override
            public int run() {
                index = ++count;

                return count + index * 10;
            }
        });
    }

    @Test
    void predecrementFieldInFieldAccess() {
        verify(new Code.Int() {

            private int index = 1;

            private int count = 2;

            @Override
            public int run() {
                index = --count;

                return count + index * 10;
            }
        });
    }

    @Test
    void postIncrementParentFieldInLocalVariableAccess() {
        verify(new Code.Int() {

            @Override
            public int run() {
                Parent parent = new Parent();
                int old = parent.child.postIncrement();
                return parent.value + old;
            }
        });
    }

    @Test
    void preIncrementParentFieldInLocalVariableAccess() {
        verify(new Code.Int() {

            @Override
            public int run() {
                Parent parent = new Parent();
                int old = parent.child.preIncrement();
                return parent.value + old;
            }
        });
    }

    @Test
    void postDecrementParentFieldInLocalVariableAccess() {
        verify(new Code.Int() {

            @Override
            public int run() {
                Parent parent = new Parent();
                int old = parent.child.postDecrement();
                return parent.value + old;
            }
        });
    }

    @Test
    void preDecrementParentFieldInLocalVariableAccess() {
        verify(new Code.Int() {

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
                int old = value++;

                return old;
            }

            int preIncrement() {
                int old = ++value;

                return old;
            }

            int postDecrement() {
                int old = value--;

                return old;
            }

            int preDecrement() {
                int old = --value;

                return old;
            }
        }
    }
}
