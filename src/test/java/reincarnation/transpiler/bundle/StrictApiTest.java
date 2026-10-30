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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import reincarnation.coder.js.JavascriptBundle;
import reincarnation.coder.js.JavascriptCodingOption;
import reincarnation.coder.js.TranslationException;

/**
 * Test the strict API mode which rejects the unmapped Java API.
 */
class StrictApiTest {

    @Test
    void rejectsUnmappedApi() {
        JavascriptCodingOption options = new JavascriptCodingOption();
        options.strictApi = true;

        TranslationException error = assertThrows(TranslationException.class, () -> JavascriptBundle.build(Unsupported.class, options));

        assert error.type() == java.util.UUID.class;
        assert error.member().startsWith("<init>");
    }

    @Test
    void acceptsMappedApi() {
        JavascriptCodingOption options = new JavascriptCodingOption();
        options.strictApi = true;

        String javascript = JavascriptBundle.build(Supported.class, options);

        assert javascript.contains("$rt.define");
        assert javascript.contains("Math.abs");
    }

    /** Uses an unmapped API. */
    static class Unsupported {

        static Object run() {
            return new java.util.UUID(0L, 0L);
        }
    }

    /** Uses the mapped APIs only. */
    static class Supported {

        static int run() {
            return Math.abs(-1) + "x".length();
        }
    }
}
