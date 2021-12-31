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

import org.junit.jupiter.api.Test;

class JSTest extends JavascriptTestSupport {

    @Test
    void fail9() {
        assert execute("return 2;").equals(3);
    }

    @Test
    void fail1() {
        assert execute("return 2;").equals(3);
    }

    @Test
    void testName22() {
        assert execute("return 3;").equals(3);
    }

    @Test
    void testName23() {
        assert execute("return 5;").equals(5);
    }

    @Test
    void fail8() {
        assert execute("return 2;").equals(3);
    }

    @Test
    void testName253() {
        assert execute("return 5;").equals(5);
    }

    @Test
    void fail7() {
        assert execute("return 2;").equals(31);
    }

    @Test
    void testName353() {
        assert execute("return 5;").equals(5);
    }

    @Test
    void fail6() {
        assert execute("return 2;").equals(31);
    }

    @Test
    void testName453() {
        assert execute("return 5;").equals(5);
    }

    @Test
    void fail5() {
        assert execute("return 2;").equals(31);
    }

    @Test
    void testName553() {
        assert execute("return 5;").equals(5);
    }

    @Test
    void fail4() {
        assert execute("return 2;").equals(31);
    }

    @Test
    void testName653() {
        assert execute("return 5;").equals(5);
    }

    @Test
    void fail3() {
        assert execute("return 2;").equals(31);
    }

    @Test
    void intTest() {
        assert executeAs(int.class, "return 3") == 3;
    }
}
