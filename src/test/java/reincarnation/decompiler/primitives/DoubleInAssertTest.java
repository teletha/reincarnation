/*
 * Copyright (C) 2020 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.primitives;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.CodeVerifier;

/**
 * @version 2018/10/10 9:54:53
 */
class DoubleInAssertTest extends CodeVerifier {

    private double value = 10D;

    @Test
    void less() throws Exception {
        verify(new TestCode.Run() {

            private double value = 10D;

            @Override
            public void run() {
                assert value < 100D; // CMPG IFLT
            }
        });
    }

    @Test
    void lessEqual() throws Exception {
        assert value <= 100D; // CMPG IFLE
    }

    @Test
    void greater() throws Exception {
        assert value > 0D; // CMPL IFGT
    }

    @Test
    void greaterEqual() throws Exception {
        assert value >= 0D; // CMPL IFGE
    }
}