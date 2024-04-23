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
import reincarnation.CrossDecompilerTest;

public class FloatInAssertTest extends CodeVerifier {

    private float value = 10F;

    @CrossDecompilerTest
    public void less() throws Exception {
        assert value < 100F; // CMPG IFLT
    }

    @CrossDecompilerTest
    public void lessEqual() throws Exception {
        assert value <= 100F; // CMPG IFLE
    }

    @CrossDecompilerTest
    public void greater() throws Exception {
        assert value > 0F; // CMPL IFGT
    }

    @CrossDecompilerTest
    public void greaterEqual() throws Exception {
        assert value >= 0F; // CMPL IFGE
    }
}