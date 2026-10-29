/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.collection;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the {@link Optional} API.
 */
class OptionalTest extends JavascriptCodeVerifier {

    @Test
    void of() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return Optional.of("abc").map(value -> value.length()).orElse(0);
            }
        });
    }

    @Test
    void empty() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return Optional.empty().map(value -> 1).orElse(7);
            }
        });
    }

    @Test
    void ofNullable() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return !Optional.ofNullable(null).isPresent() && Optional.ofNullable("x").isPresent();
            }
        });
    }

    @Test
    void filter() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return Optional.of(10).filter(value -> value > 5).orElse(0) //
                        + Optional.of(3).filter(value -> value > 5).orElse(100);
            }
        });
    }

    @Test
    void flatMap() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return Optional.of("abc").flatMap(value -> Optional.of(value.length())).orElse(0);
            }
        });
    }

    @Test
    void orElseGet() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return Optional.<Integer>empty().orElseGet(() -> 42);
            }
        });
    }
}
