/*
 * Copyright (C) 2016 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.method;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.TestCode;

class ThrowTest extends CodeVerifier {

    @Test
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

    @Test
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

    @Test
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

    @Test
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
