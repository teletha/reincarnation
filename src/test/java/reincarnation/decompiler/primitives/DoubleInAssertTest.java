/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.primitives;

import reincarnation.CodeVerifier;
import reincarnation.CompilableTest;
import reincarnation.TestCode;

class DoubleInAssertTest extends CodeVerifier {

    private double value = 10D;

    @CompilableTest
    void less() throws Exception {
        verify(new TestCode.Run() {

            private double value = 10D;

            @Override
            public void run() {
                assert value < 100D; // CMPG IFLT
            }
        });
    }

    @CompilableTest
    void lessEqual() throws Exception {
        assert value <= 100D; // CMPG IFLE
    }

    @CompilableTest
    void greater() throws Exception {
        assert value > 0D; // CMPL IFGT
    }

    @CompilableTest
    void greaterEqual() throws Exception {
        assert value >= 0D; // CMPL IFGE
    }
}