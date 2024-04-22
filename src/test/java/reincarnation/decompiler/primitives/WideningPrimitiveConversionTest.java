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

class WideningPrimitiveConversionTest extends CodeVerifier {

    @CompilableTest
    void longToDouble() {
        verify(new TestCode.LongParamDouble() {

            @Override
            public double run(long param) {
                return param / 2;
            }
        });
    }
}