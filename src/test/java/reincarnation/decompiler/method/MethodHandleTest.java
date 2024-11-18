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

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class MethodHandleTest extends CodeVerifier {

    @CrossDecompilerTest
    void primitive() {
        verify(new TestCode.TextThrow() {

            @Override
            public String run() throws Throwable {
                MethodHandle handle = MethodHandles.lookup().findGetter(String.class, "toString", String.class);
                return (String) handle.invokeExact("ok");
            }
        });
    }
}