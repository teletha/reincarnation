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
import reincarnation.transpiler.bundle.necessary.UnnecessaryClass;

/**
 * Test the explicit class inclusion control by {@code @Necessary} and {@code @Unnecessary}.
 */
class NecessaryTest {

    @Test
    void necessary() {
        JavascriptCodingOption options = new JavascriptCodingOption();
        options.scanNecessary = true;

        String javascript = JavascriptBundle.build(Entry.class, options);

        assert javascript.contains("$rt.define(\"NecessaryClass\"");
    }

    @Test
    void unnecessary() {
        String javascript = JavascriptBundle.build(UnnecessaryEntry.class);

        assert !javascript.contains("$rt.define(\"UnnecessaryClass\"");
    }

    /** The plain entry. */
    static class Entry {

        static int run() {
            return 1;
        }
    }

    /** The entry which refers to the unnecessary class. */
    static class UnnecessaryEntry {

        static int run() {
            return UnnecessaryClass.value();
        }
    }
}
