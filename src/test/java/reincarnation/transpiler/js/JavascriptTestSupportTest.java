/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.js;

import org.junit.jupiter.api.Test;

class JavascriptTestSupportTest extends JavascriptTestSupport {

    @Test
    void primitiveInt() {
        assert executeAs(int.class, "return 3") == 3;
    }

    @Test
    void primitiveLong() {
        assert executeAs(long.class, "return 123456789012") == 123456789012L;
    }
}