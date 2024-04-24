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

class ThrowTest extends CodeVerifier {

    @CrossDecompilerTest
    void exception() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                try {
                    throw new Exception();
                } catch (Exception e) {
                    return 10;
                }
            }
        });
    }

    @CrossDecompilerTest
    void exceptionWithParam() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                try {
                    throw new Exception("test");
                } catch (Exception e) {
                    return e.getMessage();
                }
            }
        });
    }

    @CrossDecompilerTest
    void runtimeException() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                try {
                    throw new RuntimeException("test");
                } catch (RuntimeException e) {
                    return e.getMessage();
                }
            }
        });
    }

    @CrossDecompilerTest
    void error() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                try {
                    throw new Error("test");
                } catch (Error e) {
                    return e.getMessage();
                }
            }
        });
    }
}