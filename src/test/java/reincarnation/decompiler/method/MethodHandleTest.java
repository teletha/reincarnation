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

import static java.lang.invoke.MethodType.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.List;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class MethodHandleTest extends CodeVerifier {

    @CrossDecompilerTest
    void invoke() {
        verify(new TestCode.TextThrow() {

            @Override
            public String run() throws Throwable {
                MethodHandle handle = MethodHandles.lookup().findVirtual(String.class, "toString", methodType(String.class));
                return (String) handle.invoke("ok");
            }
        });
    }

    @CrossDecompilerTest
    void invokeExact() {
        verify(new TestCode.TextThrow() {

            @Override
            public String run() throws Throwable {
                MethodHandle handle = MethodHandles.lookup().findVirtual(String.class, "toString", methodType(String.class));
                return (String) handle.invokeExact("ok");
            }
        });
    }

    @CrossDecompilerTest
    void invokeWithArguments() {
        verify(new TestCode.TextThrow() {

            @Override
            public String run() throws Throwable {
                MethodHandle handle = MethodHandles.lookup().findVirtual(String.class, "toString", methodType(String.class));
                return (String) handle.invokeWithArguments("ok");
            }
        });
    }

    @CrossDecompilerTest
    void invokeWithArgumentsList() {
        verify(new TestCode.TextThrow() {

            @Override
            public String run() throws Throwable {
                MethodHandle handle = MethodHandles.lookup().findVirtual(String.class, "toString", methodType(String.class));
                return (String) handle.invokeWithArguments(List.of("ok"));
            }
        });
    }
}