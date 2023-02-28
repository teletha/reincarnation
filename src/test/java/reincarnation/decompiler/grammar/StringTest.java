/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.grammar;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.TestCode;

class StringTest extends CodeVerifier {

    @Test
    void literal() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return "literal";
            }
        });
    }

    @Test
    void concatString() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                return param + "!";
            }
        });
    }

    @Test
    void concatStringFirst() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                return "#" + param;
            }
        });
    }

    @Test
    void concatInt() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                return param + 10;
            }
        });
    }

    @Test
    void concatLong() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                return param + 10L;
            }
        });
    }

    @Test
    void concatFloat() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                return param + 10f;
            }
        });
    }

    @Test
    void concatDouble() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                return param + 10d;
            }
        });
    }

    @Test
    void concatBoolean() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                return param + true;
            }
        });
    }

    @Test
    void concatShort() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                return param + (short) 10;
            }
        });
    }

    @Test
    void concatByte() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                return param + (byte) 10;
            }
        });
    }

    @Test
    void concatEnclosed() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                return param + (param.length() - 3) + param;
            }
        });
    }

    @Test
    void concatTernary() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                return param + (param.length() == 0 ? "ZERO" : "POSITIVE");
            }
        });
    }

    @Test
    void builder() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                StringBuilder builder = new StringBuilder(param).append("by builder");

                return builder.toString();
            }
        });
    }

    @Test
    void builder2() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                StringBuilder builder = new StringBuilder(param).append("by builder");

                return builder.append(" divided append").toString();
            }
        });
    }

    @Test
    void inParam() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                return concat(param + 1, param + "2");
            }

            private String concat(String a, String b) {
                return a + b;
            }
        });
    }

    @Test
    void assignLocalVariable() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                String value = param + param;

                return value + param + "!";
            }
        });
    }
}