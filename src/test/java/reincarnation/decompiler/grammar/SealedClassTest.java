/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.grammar;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.Debuggable;
import reincarnation.TestCode;

@Debuggable
class SealedClassTest extends CodeVerifier {

    static sealed class Clazz permits A, B {
    }

    static final class A extends Clazz {
    }

    static non-sealed class B extends Clazz {
    }

    @Test
    void onClass() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                assert Clazz.class.isAssignableFrom(A.class);
                assert Clazz.class.isAssignableFrom(B.class);

                assert Clazz.class.isSealed();

                Class<?>[] permitted = Clazz.class.getPermittedSubclasses();
                assert permitted[0] == A.class;
                assert permitted[1] == B.class;
            }
        });
    }

    static sealed interface Interface permits C, D {
    }

    static final class C implements Interface {
    }

    static non-sealed class D implements Interface {
    }

    @Test
    void onInterface() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                assert Interface.class.isAssignableFrom(C.class);
                assert Interface.class.isAssignableFrom(D.class);

                assert Interface.class.isSealed();

                Class<?>[] permitted = Interface.class.getPermittedSubclasses();
                assert permitted[0] == C.class;
                assert permitted[1] == D.class;
            }
        });
    }

    static sealed interface UseRecord permits E, F {
    }

    record E(int value) implements UseRecord {
    }

    record F(String value) implements UseRecord {
    }

    @Test
    void useRecord() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                assert UseRecord.class.isAssignableFrom(E.class);
                assert UseRecord.class.isAssignableFrom(F.class);

                assert UseRecord.class.isSealed();

                Class<?>[] permitted = UseRecord.class.getPermittedSubclasses();
                assert permitted[0] == E.class;
                assert permitted[1] == F.class;
            }
        });
    }
}