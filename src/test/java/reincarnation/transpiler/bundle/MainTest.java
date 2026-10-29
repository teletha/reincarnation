/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.bundle;

import org.junit.jupiter.api.Test;

import reincarnation.coder.js.JavascriptBundle;
import reincarnation.coder.js.JavascriptCodingOption;

/**
 * Test the entry point generation.
 */
class MainTest {

    @Test
    void main() {
        String javascript = JavascriptBundle.build(MainEntry.class);

        assert javascript.contains("$c.MainEntry.main(process.argv.slice(2))");
    }

    @Test
    void noMain() {
        String javascript = JavascriptBundle.build(NoMainEntry.class);

        assert !javascript.contains("process.argv");
    }

    @Test
    void disabled() {
        JavascriptCodingOption options = new JavascriptCodingOption();
        options.emitMain = false;

        String javascript = JavascriptBundle.build(MainEntry.class, options);

        assert !javascript.contains("process.argv");
    }

    /** The entry which has the main method. */
    static class MainEntry {

        public static void main(String[] args) {
        }
    }

    /** The entry which has no main method. */
    static class NoMainEntry {

        static int run() {
            return 0;
        }
    }
}
