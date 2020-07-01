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

import reincarnation.CodeVerifier;

/**
 * @version 2018/10/10 11:06:22
 */
public class FloatInAssertTest extends CodeVerifier {

    private float value = 10F;

    @Test
    public void less() throws Exception {
        assert value < 100F; // CMPG IFLT
    }

    @Test
    public void lessEqual() throws Exception {
        assert value <= 100F; // CMPG IFLE
    }

    @Test
    public void greater() throws Exception {
        assert value > 0F; // CMPL IFGT
    }

    @Test
    public void greaterEqual() throws Exception {
        assert value >= 0F; // CMPL IFGE
    }
}