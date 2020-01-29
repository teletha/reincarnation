/*
 * Copyright (C) 2019 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.operator;

import org.junit.jupiter.api.Test;

import antibug.powerassert.PowerAssertOff;
import reincarnation.CodeVerifier;
import reincarnation.TestCode;

@PowerAssertOff
class IntIncrementTest2 extends CodeVerifier {

    @Test
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

    @Test
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

    @Test
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

    @Test
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
