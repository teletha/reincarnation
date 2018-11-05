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
import reincarnation.Debuggable;

/**
 * @version 2018/10/28 9:15:52
 */
public class IfTest extends CodeVerifier {

    @Test
    void normal() {
        verify(new Code.IntParam() {

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
    void then() {
        verify(new Code.IntParam() {

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
    void thenNest() {
        verify(new Code.IntParam() {

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
    void thenNestImmidiately() {
        verify(new Code.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                if (value < 5) {
                    if (value < 3) {
                        value += 1;
                    }
                    value += 2;
                }
                return value;
            }
        });
    }

    @Test
    void nestReturn() {
        verify(new Code.IntParam() {

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
    void nestComplexLogicalExpression() {
        verify(new Code.IntParam() {

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
    void integer() {
        verify(new Code.IntParamBoolean() {

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
    void object() {
        verify(new Code.ObjectParamBoolean() {

            @Override
            public boolean run(Object o) {
                if (o == null) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    @Test
    void object2() {
        verify(new Code.ObjectParamBoolean() {

            @Override
            public boolean run(Object o) {
                if (o != null) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    @Test
    void ifelse() {
        verify(new Code.IntParam() {

            @Override
            public int run(int o) {
                if (o == 2) {
                    return o + 3;
                } else {
                    return o;
                }
            }
        });
    }

    @Test
    void multiple() {
        verify(new Code.IntParam() {

            @Override
            public int run(int o) {
                if (o == 2) {
                    return o + 3;
                } else if (o == -2) {
                    return o;
                } else {
                    return o - 1;
                }
            }
        });
    }

    @Test
    void sequence() {
        verify(new Code.IntParam() {

            @Override
            public int run(int o) {
                if (o == 2) {
                    o = o + 1;
                }

                if (o == 3) {
                    o = o + 2;
                }

                if (o == 3) {
                    o = o + 3;
                }
                return o;
            }
        });
    }

    @Test
    void ifelseWithFollowing() {
        verify(new Code.IntParam() {

            @Override
            public int run(int o) {
                if (o == 2) {
                    o += 10;
                } else {
                    o += 2;
                }
                return o;
            }
        });
    }

    @Test
    void shorthand() {
        verify(new Code.IntParam() {

            @Override
            public int run(int o) {
                if (o == 2) return o + 3;
                return o;
            }
        });
    }

    @Test
    void shorthandLine() {
        verify(new Code.IntParam() {

            @Override
            public int run(int o) {
                if (o == 2) // need line
                    return o + 3;

                return o;
            }
        });
    }

    @Test
    void shorthandElse() {
        verify(new Code.IntParam() {

            @Override
            public int run(int o) {
                if (o == 2)
                    return o + 3;
                else
                    return o;
            }
        });
    }

    @Test
    void shorthandMultiple() {
        verify(new Code.IntParam() {

            @Override
            public int run(int o) {
                if (o == 2)
                    return o + 3;
                else if (o == 1)
                    return -10;
                else
                    return o;
            }
        });
    }

    @Test
    void shorthandWithExpression() {
        verify(new Code.IntParam() {

            @Override
            public int run(int o) {
                if (o == 2) o = o + 3;

                return o;
            }
        });
    }

    @Test
    void shorthandElseWithExpression() {
        verify(new Code.IntParam() {

            @Override
            public int run(int o) {
                if (o == 2)
                    o = o + 3;
                else
                    o = o + 10;
                return o;
            }
        });
    }

    @Test
    void shorthandInFlow() {
        verify(new Code.IntParamBoolean() {

            @Override
            public boolean run(int o) {
                while (o < 4) {
                    o = o + 2;
                    if (o == 2) return true;
                }
                return false;
            }
        });
    }

    @Test
    void shorthandInNest() {
        verify(new Code.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int o) {
                while (o < 10) {
                    o++;
                    if (o % 2 == 0) {
                        if (o % 3 == 0) return -100;
                    } else if (o % 5 == 0) return -10;
                }
                return o;
            }
        });
    }

    @Test
    void noElse() {
        verify(new Code.IntParam() {

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
    void ternaryLike() {
        verify(new Code.IntParam() {

            @Debuggable
            @Override
            public int run(int value) {
                if (0 < value) {
                    value++;
                } else {
                    value--;
                }
                return value;
            }
        });
    }

    @Test
    void oneLiner() {
        verify(new Code.IntParam() {

            @Override
            public int run(int o) {
                // @formatter:off
                if (o == 2) {return o + 3;} else {return o;}
                // @formatter:on
            }
        });
    }

    @Test
    void denyInElse() {
        verify(new Code.TextParamBoolean() {

            @Override
            public boolean run(String param) {
                if (param == null) {
                    return true;
                } else {
                    return !param.equals("");
                }
            }
        });
    }

    @Test
    void whileInElse() {
        verify(new Code.TextParam() {

            private boolean flag;

            @Override
            public String run(String attrs) {
                if (attrs == null) {
                    attrs = "huu";
                } else {
                    while (flag) {
                    }
                    attrs = "ok";
                }
                return attrs;
            }
        });
    }

    @Test
    void multipleInWhile() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                while (value++ < 5) {
                    if (value != 0 && value != 1 && value != 2) {
                        value += 4;
                    }
                }
                return value;
            }

        });
    }
}
