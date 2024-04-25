/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.flow;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class TernaryExpressionTest extends CodeVerifier {

    @CrossDecompilerTest
    void base() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value < 1 ? 10 : 20;
            }
        });
    }

    @CrossDecompilerTest
    void postIncrement() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value++ < 0 ? 10 : value;
            }
        });
    }

    @CrossDecompilerTest
    void preIncrement() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value < 0 ? 10 : ++value;
            }
        });
    }

    @CrossDecompilerTest
    void logicalExpressionInLeft() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value < 3 ? value == 2 : false;
            }
        });
    }

    @CrossDecompilerTest
    void logicalExpressionInRight() {
        verify(new TestCode.IntParamBoolean() {

            private boolean a = true;

            @Override
            public boolean run(int value) {
                return value < 3 ? a : value == 4;
            }
        });
    }

    @CrossDecompilerTest
    void logicalExpressionInBoth() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value < 3 ? value == 2 : value == 4;
            }
        });
    }

    @CrossDecompilerTest
    void logicalCondition() {
        verify(new TestCode.IntParamBoolean() {

            private boolean a = true;

            @Override
            public boolean run(int value) {
                return value < 0 || 2 < value ? a : false;
            }
        });
    }

    @CrossDecompilerTest
    void logicalAll() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return -2 < value && value < 2 ? value != 0 : value == 2;
            }
        });
    }

    @CrossDecompilerTest
    void logicalAllUnformat() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                // @formatter:off
                return -2 < value && value < 2 
                        ? value != 0 
                        : value == 2;
                // @formatter:on
            }
        });
    }

    @CrossDecompilerTest
    void string() {
        verify(new TestCode.IntParamText() {

            @Override
            public String run(int value) {
                return (value < 0 ? "negative" : "positive") + " value";
            }
        });
    }

    @CrossDecompilerTest
    void stringNest() {
        verify(new TestCode.IntParamText() {

            @Override
            public String run(int value) {
                return (value < 0 ? "negative" : value == 1 ? "one" : "positive") + " value";
            }
        });
    }

    @CrossDecompilerTest
    void stringComplex() {
        verify(new TestCode.IntParamText() {

            @Override
            public String run(@Param(ints = {-1, 0, 1, 2, 3}) int value) {
                return value < 0 ? "negative" : value % 2 != 0 && value != 3 ? "one" : value == 0 ? "zero" : "other";
            }
        });
    }

    @CrossDecompilerTest
    void nestRight() {
        verify(new TestCode.IntParamText() {

            @Override
            public String run(int value) {
                return value == 0 ? "zero" : value == 1 ? "one" : "other";
            }
        });
    }

    @CrossDecompilerTest
    void nestLeft() {
        verify(new TestCode.IntParamText() {

            @Override
            public String run(int value) {
                return 0 < value ? value == 1 ? "one" : "other" : "negative";
            }
        });
    }

    @CrossDecompilerTest
    void withExpression() {
        verify(new TestCode.TextParamInt() {

            @Override
            public int run(String value) {
                return 31 + (value == null ? 0 : value.length());
            }
        });
    }

    @CrossDecompilerTest
    void withExpressionThenAssignToVariable() {
        verify(new TestCode.TextParam() {

            boolean flag = true;

            @Override
            public String run(String value) {
                String result = flag ? value.concat("verify") : value.concat("one");

                return result;
            }
        });
    }

    @CrossDecompilerTest
    void withAssignToVariable() {
        verify(new TestCode.BooleanParamInt() {

            @Override
            public int run(boolean flag) {
                int result = flag ? 10 : 12;

                return result;
            }
        });
    }

    @CrossDecompilerTest
    void afterLogicalExpression() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                boolean result = value % 2 == 0;
                return result ? value : value + 1;
            }
        });
    }

    @CrossDecompilerTest
    void inIf() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                if (value % 2 == 0) {
                    return value == 2 ? 0 : 1;
                } else {
                    return value % 3 == 0 ? 0 : 1;
                }
            }
        });
    }

    @CrossDecompilerTest
    void ifConditionLogical() {
        verify(new TestCode.IntParamText() {

            @Override
            public String run(int value) {
                if ((value < 0 && value == -1) || value == 2) {
                    if (value < 0) {
                        return "one";
                    } else {
                        return "two";
                    }
                }
                return "three";
            }
        });
    }

    @CrossDecompilerTest
    void ifConditionTernary() {
        verify(new TestCode.IntParamText() {

            @Override
            public String run(int value) {
                if (value < 0 ? value == -1 : value == 2) {
                    if (value < 0) {
                        return "one";
                    } else {
                        return "two";
                    }
                }
                return "three";
            }
        });
    }

    @CrossDecompilerTest
    void ifConditionTernaryBoolean() {
        verify(new TestCode.IntParamText() {

            @Override
            public String run(int value) {
                if (value < 0 ? true : value == 2) {
                    return "yes";
                }
                return "no";
            }
        });
    }

    @CrossDecompilerTest
    void whileConditionTernary() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                while (value < 0 ? value != -1 : value != 7) {
                    value++;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void withLogical() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 0, to = 10) int value) {
                return value < 8 && (value < 5 ? value == 4 : value == 6);
            }
        });
    }
}