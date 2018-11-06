/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.flow;

import org.junit.jupiter.api.Test;

import reincarnation.Code;
import reincarnation.CodeVerifier;

/**
 * @version 2018/11/06 9:10:19
 */
class TernaryExpressionTest extends CodeVerifier {

    @Test
    void base() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value < 1 ? 10 : 20;
            }
        });
    }

    @Test
    void postIncrement() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value++ < 0 ? 10 : value;
            }
        });
    }

    @Test
    void preIncrement() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value < 0 ? 10 : ++value;
            }
        });
    }

    @Test
    void logicalExpressionInLeft() {
        verify(new Code.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value < 3 ? value == 2 : false;
            }
        });
    }

    @Test
    void logicalExpressionInRight() {
        verify(new Code.IntParamBoolean() {

            private boolean a = true;

            @Override
            public boolean run(int value) {
                return value < 3 ? a : value == 4;
            }
        });
    }

    @Test
    void logicalExpressionInBoth() {
        verify(new Code.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value < 3 ? value == 2 : value == 4;
            }
        });
    }

    @Test
    void logicalCondition() {
        verify(new Code.IntParamBoolean() {

            private boolean a = true;

            @Override
            public boolean run(int value) {
                return value < 0 || 2 < value ? a : false;
            }
        });
    }

    @Test
    void logicalAll() {
        verify(new Code.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return -2 < value && value < 2 ? value != 0 : value == 2;
            }
        });
    }

    @Test
    void logicalAllUnformat() {
        verify(new Code.IntParamBoolean() {

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

    @Test
    void string() {
        verify(new Code.IntParamText() {

            @Override
            public String run(int value) {
                return (value < 0 ? "negative" : "positive") + " value";
            }
        });
    }

    @Test
    void stringNest() {
        verify(new Code.IntParamText() {

            @Override
            public String run(int value) {
                return (value < 0 ? "negative" : value == 1 ? "one" : "positive") + " value";
            }
        });
    }

    @Test
    void stringComplex() {
        verify(new Code.IntParamText() {

            @Override
            public String run(@Param(ints = {-1, 0, 1, 2, 3}) int value) {
                return value < 0 ? "negative" : value % 2 != 0 && value != 3 ? "one" : value == 0 ? "zero" : "other";
            }
        });
    }

    @Test
    void nestRight() {
        verify(new Code.IntParamText() {

            @Override
            public String run(int value) {
                return value == 0 ? "zero" : value == 1 ? "one" : "other";
            }
        });
    }

    @Test
    void nestLeft() {
        verify(new Code.IntParamText() {

            @Override
            public String run(int value) {
                return 0 < value ? value == 1 ? "one" : "other" : "negative";
            }
        });
    }

    @Test
    void withExpression() {
        verify(new Code.TextParamInt() {

            @Override
            public int run(String value) {
                return 31 + (value == null ? 0 : value.length());
            }
        });
    }

    @Test
    void withExpressionThenAssignToVariable() {
        verify(new Code.TextParam() {

            boolean flag = true;

            @Override
            public String run(String value) {
                String result = flag ? value.concat("verify") : value.concat("one");

                return result;
            }
        });
    }

    @Test
    void withAssignToVariable() {
        verify(new Code.BooleanParamInt() {

            @Override
            public int run(boolean flag) {
                int result = flag ? 10 : 12;

                return result;
            }
        });
    }

    @Test
    void afterLogicalExpression() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                boolean result = value % 2 == 0;
                return result ? value : value + 1;
            }
        });
    }

    @Test
    void inIf() {
        verify(new Code.IntParam() {

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

    @Test
    void ifConditionLogical() {
        verify(new Code.IntParamText() {

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

    @Test
    void ifConditionTernary() {
        verify(new Code.IntParamText() {

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

    @Test
    void ifConditionTernaryBoolean() {
        verify(new Code.IntParamText() {

            @Override
            public String run(int value) {
                if (value < 0 ? true : value == 2) {
                    return "yes";
                }
                return "no";
            }
        });
    }

    @Test
    void whileConditionTernary() {
        verify(new Code.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                while (value < 0 ? value != -1 : value != 7) {
                    value++;
                }
                return value;
            }
        });
    }

    @Test
    void withLogical() {
        verify(new Code.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 0, to = 10) int value) {
                return value < 8 && (value < 5 ? value == 4 : value == 6);
            }
        });
    }
}
