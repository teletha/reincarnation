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

import org.junit.jupiter.api.Disabled;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.Debuggable;
import reincarnation.TestCode;

class WhileTest extends CodeVerifier {

    @CrossDecompilerTest
    void normal() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                while (value < 3) {
                    value++;
                }

                return value;
            }
        });
    }

    @CrossDecompilerTest
    void withBreak() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                while (value < 3) {
                    value++;

                    if (value == 1) {
                        break;
                    }
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void multipuleBreaks() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                while (value < 5) {
                    value++;

                    if (value == 1) {
                        break;
                    }

                    if (value == 0) {
                        break;
                    }
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void withContinue() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                while (value < 3) {
                    value += 2;

                    if (value == 3) {
                        continue;
                    }
                    value += 2;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void withReturn() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                while (value < 3) {
                    value++;

                    if (value == 3) {
                        return value;
                    }
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void mix() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                while (value < 5) {
                    value++;

                    if (value == 3)
                        return value;
                    else if (value == 2)
                        break;
                    else
                        value++;
                }
                return value + 1;
            }
        });
    }

    @CrossDecompilerTest
    void nest() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                while (value < 30) {
                    value += 10;

                    while (30 < value) {
                        value--;
                    }
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void labeledBreak() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 1, to = 10) int value) {
                root: while (value < 100) {
                    value *= 2;

                    while (50 < value) {
                        value--;

                        if (value == 70) {
                            break root;
                        }
                    }
                    value *= 2;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void inIf() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                while (value < 3) {
                    value++;
                }

                return value;
            }
        });
    }

    @CrossDecompilerTest
    void afterIf() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                if (value % 2 == 0) {
                    value += 100;
                }

                while (value < 3) {
                    value++;
                }

                return value;
            }
        });
    }

    @CrossDecompilerTest
    void logical() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                while (value % 2 != 0 && value != 1) {
                    value++;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void assignWithMethod() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                while ((value = verify(value)) < 10) {
                    value++;
                }
                return value;
            }

            private int verify(int value) {
                return value + 2;
            }
        });
    }

    @CrossDecompilerTest
    void oneLiner() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                // @formatter:off
                while (value < 0) {value+=2;}
                // @formatter:on

                return value;
            }
        });
    }

    @CrossDecompilerTest
    @Debuggable
    void oneLinerNest() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                // @formatter:off
                while (value < 0) {if (value % 2==0) {value +=3;} else {value+= 5;}};
                // @formatter:on

                return value;
            }
        });
    }

    @CrossDecompilerTest
    void sequentialWithComplexCondition() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                while (0 < value && value < 5) {
                    value++;
                }

                while (value < 1) {
                    value++;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void infiniteBreak() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                while (true) {
                    value++;

                    if (value == 10) {
                        break;
                    }
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void infiniteFisrtBreak() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                while (true) {
                    value++;

                    if (value == 5 || value == 6) {
                        break;
                    }

                    value++;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void infiniteStatementBreakWithFollow() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                while (true) {
                    value++;

                    if (value == 10) {
                        value += 2;
                        break;
                    }
                }
                value += 3;
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void infiniteMultipleStatementBreak() {
        verify(new TestCode.IntParam() {
            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                while (true) {
                    value++;

                    if (value == 0) {
                        break;
                    }

                    if (value % 2 == 0) {
                        value += 2;
                        break;
                    }
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void infiniteMultipleStatementBreaks() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                while (true) {
                    value++;

                    if (value == 0) {
                        value += 2;
                        break;
                    }

                    if (value % 2 == 0) {
                        value += 3;
                        break;
                    }
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void infiniteNonConditionalSingleBackedge() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                while (true) {
                    value++;

                    if (value == 0) {
                        value += 2;
                        break;
                    }

                    if (value % 2 == 0) {
                        value += 3;
                        break;
                    }

                    value += 2;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void infiniteBreakInNestedIf() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                while (true) {
                    value++;

                    if (value == 0) {
                        break;
                    }

                    if (value % 2 == 0) {
                        value++;

                        if (value % 3 == 0) {
                            break;
                        }
                    }
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void infiniteBreakAndContinue() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                while (true) {
                    value++;

                    if (value % 5 == 0) {
                        value += 5;
                        break;
                    }

                    if (value % 3 == 0) {
                        continue;
                    }

                    if (value % 2 == 0) {
                        value += 2;
                        break;
                    }
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void inifinitContinue() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 10) int value) {
                while (true) {
                    value++;

                    if (value % 6 == 0) {
                        continue;
                    }

                    if (value % 3 == 0) {
                        return value;
                    }
                }
            }
        });
    }

    @CrossDecompilerTest
    void inifinitContinueWithShorthandIf() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 10) int value) {
                while (true) {
                    value++;

                    if (value % 6 == 0) continue;

                    if (value % 3 == 0) {
                        return value;
                    }
                }
            }
        });
    }

    @CrossDecompilerTest
    void inifinitContinueWithShorthandIfInAnotherIf() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = -5, to = 20) int value) {
                while (true) {
                    value++;

                    if (value % 2 == 0) {
                        value++;

                        if (value % 3 == 0) continue;
                    }

                    if (value % 7 == 0) {
                        return value;
                    }
                }
            }
        });
    }

    @CrossDecompilerTest
    void infiniteNest() {
        verify(new TestCode.IntParam() {
            @Override
            public int run(@Param(from = -5, to = 5) int value) {
                while (true) {
                    while (true) {
                        value++;

                        if (value % 3 == 0) {
                            break;
                        }

                        if (value % 5 == 0) {
                            break;
                        }
                    }

                    value++;

                    if (value % 2 == 0) {
                        return value;
                    }
                }
            }
        });
    }

    @CrossDecompilerTest
    @Disabled
    void infiniteNestBreakAndContinue() {
        verify(new TestCode.IntParam() {
            @Override
            public int run(@Param(from = -5, to = 10) int value) {
                int a = 0;

                while (true) {
                    while (true) {
                        value++;

                        if (value % 3 == 0) {
                            break;
                        }

                        if (value % 4 == 0) {
                            a++;
                            continue;
                        }

                        if (value % 5 == 0) {
                            break;
                        }
                    }

                    a++;

                    if (value % 2 == 0) {
                        break;
                    }
                }
                return a;
            }
        });
    }
}