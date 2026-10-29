/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.js;

import java.util.Objects;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the Java implementation of {@link Objects}.
 */
class ObjectsTest extends JavascriptCodeVerifier {

    @Test
    void equalsAndHash() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return (Objects.equals("a", "a") && !Objects.equals("a", "b") ? 10 : 0)
                        + (Objects.hashCode("a") == Objects.hashCode("a") ? 1 : 0);
            }
        });
    }

    @Test
    void hash() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return Objects.hash("a", 1) == Objects.hash("a", 1);
            }
        });
    }

    @Test
    void deepEquals() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return Objects.deepEquals(new int[] {1, 2}, new int[] {1, 2})
                        && !Objects.deepEquals(new int[] {1, 2}, new int[] {1, 3});
            }
        });
    }

    @Test
    void toStringValue() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return Objects.toString(null, "default") + ":" + Objects.toString("x");
            }
        });
    }

    @Test
    void requireNonNull() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                try {
                    Objects.requireNonNull(null);
                    return "bad";
                } catch (NullPointerException e) {
                    return Objects.requireNonNull("ok");
                }
            }
        });
    }

    @Test
    void requireNonNullElse() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return Objects.requireNonNullElse(null, "default") + ":"
                        + Objects.requireNonNullElseGet(null, () -> "supplied");
            }
        });
    }

    @Test
    void nullability() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return Objects.isNull(null) && Objects.nonNull("x");
            }
        });
    }
}
