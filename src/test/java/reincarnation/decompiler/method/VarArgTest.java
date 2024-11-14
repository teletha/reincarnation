/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.method;

import org.junit.jupiter.api.Disabled;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class VarArgTest extends CodeVerifier {

    @CrossDecompilerTest
    void primitive() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return total(1, 2, 3);
            }

            private int total(int... values) {
                int total = 0;
                for (int i = 0; i < values.length; i++) {
                    total += values[i];
                }
                return total;
            }
        });
    }

    @CrossDecompilerTest
    void wrapper() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return total(1, 2, 3);
            }

            private int total(Integer... values) {
                int total = 0;
                for (int i = 0; i < values.length; i++) {
                    total += values[i];
                }
                return total;
            }
        });
    }

    @CrossDecompilerTest
    void withHeaderParam() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return total(20, 1, 2, 3);
            }

            private int total(int base, int... values) {
                int total = base;
                for (int i = 0; i < values.length; i++) {
                    total += values[i];
                }
                return total;
            }
        });
    }

    @CrossDecompilerTest
    void string() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return concat("a", "b", "c");
            }

            private String concat(String... text) {
                return String.join("", text);
            }
        });
    }

    @Disabled
    @CrossDecompilerTest
    void object() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return concat("a", "b", "c");
            }

            private String concat(java.lang.Object... text) {
                StringBuilder builder = new StringBuilder();
                for (java.lang.Object object : text) {
                    builder.append(object);
                }
                return builder.toString();
            }
        });
    }
}