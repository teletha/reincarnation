/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.grammar;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class StringTest extends CodeVerifier {

    @CrossDecompilerTest
    void literal() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return "literal";
            }
        });
    }

    @CrossDecompilerTest
    void escape() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return "\b\f\n\r\t\\\'\"";
            }
        });
    }

    @CrossDecompilerTest
    void escapeUnicode() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return "\u0052";
            }
        });
    }

    @CrossDecompilerTest
    void concatString() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                return param + "!";
            }
        });
    }

    @CrossDecompilerTest
    void concatStringFirst() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                return "#" + param;
            }
        });
    }

    @CrossDecompilerTest
    void concatMultipleStrings() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                String before = "A";
                String after = "B";
                return before + param + after;
            }
        });
    }

    /**
     * There is a JVM limit (classfile structural constraint): no method can call with more than 255
     * slots. This limits the number of static and dynamic arguments one can pass to bootstrap
     * method. Since there are potential concatenation strategies that use MethodHandle combinators,
     * we need to reserve a few empty slots on the parameter lists to capture the temporal results.
     * This is why bootstrap methods in this factory do not accept more than 200 argument slots.
     * Users requiring more than 200 argument slots in concatenation are expected to split the large
     * concatenation in smaller expressions.
     */
    @CrossDecompilerTest
    void concat200OverStrings() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String p) {
                // row 12 column 20 total 240
                return p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + 1 //
                        + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + 2 //
                        + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + 3 //
                        + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + 4 //
                        + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + 5 //
                        + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + 6 //
                        + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + 7 //
                        + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + 8 //
                        + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + 9 //
                        + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + 10 //
                        + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + 11 //
                        + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + p + 12;//
            }
        });
    }

    @CrossDecompilerTest
    void concatSpecialRecipeString() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                return /* \u0001 */ "[" + param + "]\u0001";
            }
        });
    }

    @CrossDecompilerTest
    void concatInt() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                return param + 10;
            }
        });
    }

    @CrossDecompilerTest
    void concatLong() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                return param + 10L;
            }
        });
    }

    @CrossDecompilerTest
    void concatFloat() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                return param + 10f;
            }
        });
    }

    @CrossDecompilerTest
    void concatDouble() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                return param + 10d;
            }
        });
    }

    @CrossDecompilerTest
    void concatBoolean() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                return param + true;
            }
        });
    }

    @CrossDecompilerTest
    void concatShort() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                return param + (short) 10;
            }
        });
    }

    @CrossDecompilerTest
    void concatByte() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                return param + (byte) 10;
            }
        });
    }

    @CrossDecompilerTest
    void concatEnclosed() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                return param + (param.length() - 3) + param;
            }
        });
    }

    @CrossDecompilerTest
    void concatTernary() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                return param + (param.length() == 0 ? "ZERO" : "POSITIVE");
            }
        });
    }

    @CrossDecompilerTest
    void builder() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                StringBuilder builder = new StringBuilder(param).append("by builder");

                return builder.toString();
            }
        });
    }

    @CrossDecompilerTest
    void builder2() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String param) {
                StringBuilder builder = new StringBuilder(param).append("by builder");

                return builder.append(" divided append").toString();
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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