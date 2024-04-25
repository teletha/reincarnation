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

public class IfTest extends CodeVerifier {

    @CrossDecompilerTest
    void normal() {
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

    @CrossDecompilerTest
    void then() {
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

    @CrossDecompilerTest
    void thenNest() {
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

    @CrossDecompilerTest
    void thenNestImmidiately() {
        verify(new TestCode.IntParam() {

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

    @CrossDecompilerTest
    void nestReturn() {
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

    @CrossDecompilerTest
    void nestComplexLogicalExpression() {
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

    @CrossDecompilerTest
    void integer() {
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

    @CrossDecompilerTest
    void object() {
        verify(new TestCode.ObjectParamBoolean() {

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

    @CrossDecompilerTest
    void object2() {
        verify(new TestCode.ObjectParamBoolean() {

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

    @CrossDecompilerTest
    void ifelse() {
        verify(new TestCode.IntParam() {

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

    @CrossDecompilerTest
    void multiple() {
        verify(new TestCode.IntParam() {

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

    @CrossDecompilerTest
    void sequence() {
        verify(new TestCode.IntParam() {

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

    @CrossDecompilerTest
    void ifelseWithFollowing() {
        verify(new TestCode.IntParam() {

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

    @CrossDecompilerTest
    void shorthand() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int o) {
                if (o == 2) return o + 3;
                return o;
            }
        });
    }

    @CrossDecompilerTest
    void shorthandLine() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int o) {
                if (o == 2) // need line
                    return o + 3;

                return o;
            }
        });
    }

    @CrossDecompilerTest
    void shorthandElse() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int o) {
                if (o == 2)
                    return o + 3;
                else
                    return o;
            }
        });
    }

    @CrossDecompilerTest
    void shorthandMultiple() {
        verify(new TestCode.IntParam() {

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

    @CrossDecompilerTest
    void shorthandWithExpression() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int o) {
                if (o == 2) o = o + 3;

                return o;
            }
        });
    }

    @CrossDecompilerTest
    void shorthandElseWithExpression() {
        verify(new TestCode.IntParam() {

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

    @CrossDecompilerTest
    void shorthandInFlow() {
        verify(new TestCode.IntParamBoolean() {

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

    @CrossDecompilerTest
    void shorthandInNest() {
        verify(new TestCode.IntParam() {

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

    @CrossDecompilerTest
    void noElse() {
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

    @CrossDecompilerTest
    void ternaryLike() {
        verify(new TestCode.IntParam() {

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

    @CrossDecompilerTest
    void oneLiner() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int o) {
                // @formatter:off
                if (o == 2) {return o + 3;} else {return o;}
                // @formatter:on
            }
        });
    }

    @CrossDecompilerTest
    void denyInElse() {
        verify(new TestCode.TextParamBoolean() {

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

    @CrossDecompilerTest
    void whileInElse() {
        verify(new TestCode.TextParam() {

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

    @CrossDecompilerTest
    void multipleInWhile() {
        verify(new TestCode.IntParam() {

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