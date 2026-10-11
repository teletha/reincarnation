/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.flow;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.TestCode.Param;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the if statement.
 * 
 * @version 2026/10/11 12:00:00
 */
class IfTest extends JavascriptCodeVerifier {

    @Test
    void normal() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                if (value < 3) {
                    return 2;
                }
                return 1;
            }
        });
    }

    @Test
    void then() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                if (value < 3) {
                    value++;
                }
                return value;
            }
        });
    }

    @Test
    void thenNest() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                if (value < 5) {
                    value += 1;

                    if (value < 6) {
                        value += 2;
                    }
                }
                return value;
            }
        });
    }

    @Test
    void nestReturn() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                if (value < 3) {
                    if (1 < value) {
                        return 0;
                    }
                    return 2;
                }
                return 1;
            }
        });
    }

    @Test
    void nestComplexLogicalExpression() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                if (value < 3 && 1 < value || value % 2 == 0) {
                    if (1 < value && value < 2) {
                        return 0;
                    } else {
                        return 11;
                    }
                }
                return 1;
            }
        });
    }

    @Test
    void integer() throws Exception {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                if (value == 1) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    @Test
    void ifelse() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                if (value == 2) {
                    return value + 3;
                } else {
                    return value;
                }
            }
        });
    }

    @Test
    void multiple() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                if (value == 2) {
                    return value + 3;
                } else if (value == -2) {
                    return value;
                } else {
                    return value - 1;
                }
            }
        });
    }

    @Test
    void sequence() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                if (value == 2) {
                    value = value + 1;
                }

                if (value == 3) {
                    value = value + 2;
                }

                if (value == 3) {
                    value = value + 3;
                }
                return value;
            }
        });
    }

    @Test
    void noElse() throws Exception {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                int result = value;

                if (value == 1 || value == 3) {
                    result = -10;
                }
                return result;
            }
        });
    }

    @Test
    void denyInElse() throws Exception {
        verify(new TestCode.TextParamBoolean() {

            @Override
            public boolean run(String value) {
                if (value == null) {
                    return true;
                } else {
                    return !value.equals("");
                }
            }
        });
    }
}