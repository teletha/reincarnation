/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.js;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;

class JavascriptTestSupportTest extends JavetTestSupport {

    @Test
    void primitiveInt() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return true;
            }
        });
    }
}