/*
 * Copyright (C) 2021 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.transpiler.js;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
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
