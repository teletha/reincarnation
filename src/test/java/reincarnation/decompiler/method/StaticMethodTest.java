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

class StaticMethodTest extends CodeVerifier {

    @CrossDecompilerTest
    void callStaticMethodFromChildClass() {
        verify(new CallFromChild());
    }

    private static class MethodDefinedInParent {

        public static int call() {
            return 10;
        }
    }

    private static class CallFromChild extends MethodDefinedInParent implements TestCode.Int {

        @Override
        public int run() {
            return call();
        }
    }
}