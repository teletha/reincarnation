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

class LogicalExpressionTest extends CodeVerifier {

    @CrossDecompilerTest
    void True() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return true;
            }
        });
    }

    @CrossDecompilerTest
    void False() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return false;
            }
        });
    }

    @CrossDecompilerTest
    void Equal() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value == 1;
            }
        });
    }

    @CrossDecompilerTest
    void Not() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value != 1;
            }
        });
    }

    @CrossDecompilerTest
    void Or() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value == 1 || value == -1;
            }
        });
    }

    @CrossDecompilerTest
    void NotOr() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value != 1 || value == -1;
            }
        });
    }

    @CrossDecompilerTest
    void OrNot() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value == 1 || value != -1;
            }
        });
    }

    @CrossDecompilerTest
    void NotOrNot() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value != 1 || value != -1;
            }
        });
    }

    @CrossDecompilerTest
    void MultipleOr() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 2 == 0 || value % 3 == 0 || value % 5 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void OrWithOtherStatement() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                int i = value;

                return i == 1 || i != -1;
            }
        });
    }

    @CrossDecompilerTest
    void And() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 2 == 0 && value % 3 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void NotAnd() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 2 != 0 && value % 3 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void AndNot() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 2 == 0 && value % 3 != 0;
            }
        });
    }

    @CrossDecompilerTest
    void NotAndNot() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 2 != 0 && value % 3 != 0;
            }
        });
    }

    @CrossDecompilerTest
    void MultipleAnd() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 2 == 0 && value % 3 == 0 && value % 4 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void postIncrement() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 10) int value) {
                return value++ % 2 == 0 || value++ % 5 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void preIncrement() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 10) int value) {
                return ++value % 2 == 0 || ++value % 5 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void postDecrement() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 10) int value) {
                return value-- % 2 == 0 || value-- % 5 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void preDecrement() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 10) int value) {
                return --value % 2 == 0 || --value % 5 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void Complex1() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 5 == 0 || value % 3 == 0 && value % 4 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void Complex2() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 5 == 0 || (value % 3 == 0 && value % 4 == 0);
            }
        });
    }

    @CrossDecompilerTest
    void Complex3() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return (value % 5 == 0 || value % 3 == 0) && value % 2 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void Complex4() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 2 == 0 && value % 3 == 0 || value % 4 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void Complex5() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return (value % 2 == 0 && value % 3 == 0) || value % 4 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void Complex6() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 3 == 0 && (value % 2 == 0 || value % 5 == 0);
            }
        });
    }

    @CrossDecompilerTest
    void Complex10() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 5 == 0 && value % 3 == 0 || value % 2 == 0 || value % 7 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void Complex11() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 5 == 0 || value % 3 == 0 && value % 2 == 0 || value % 7 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void Complex12() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 5 == 0 || value % 3 == 0 || value % 2 == 0 && value % 7 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void Complex13() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return (value % 5 == 0 || value % 3 == 0 || value % 7 == 0) && value % 2 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void Complex14() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return (value % 5 == 0 && value % 3 == 0) || (value % 7 == 0 && value % 2 == 0);
            }
        });
    }

    @CrossDecompilerTest
    void Complex20() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 48) int value) {
                return (value % 3 == 0 && value % 4 == 0) || (value % 7 == 0 || value % 6 == 0) && value % 5 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void Complex21() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 48) int value) {
                return ((value % 3 == 0 && value % 4 == 0) || (value % 7 == 0 || value % 6 == 0)) && value % 5 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void Complex22() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 48) int value) {
                return (value % 3 == 0 || value % 4 == 0) && (value % 5 == 0 || value % 6 == 0) && value % 2 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void Complex23() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 48) int value) {
                return ((value % 3 == 0 || value % 4 == 0) && (value % 7 == 0 || value % 11 == 0)) || value % 5 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void Complex24() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 128) int value) {
                return value % 5 == 0 && ((value % 3 == 0 && value % 4 == 0) || (value % 7 == 0 || value % 6 == 0));
            }
        });
    }

    @CrossDecompilerTest
    void Complex25() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 128) int value) {
                return ((value % 3 == 0 && value % 4 == 0) || (value % 7 == 0 && value % 6 == 0)) && value % 5 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void Complex26() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 128) int value) {
                return (value % 3 == 0 && value % 4 == 0) || (value % 7 == 0 && value % 6 == 0) && value % 5 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void Complex27() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 128) int value) {
                return value % 3 == 0 || value % 4 == 0 || value % 7 == 0 && (value % 6 == 0 || value % 5 == 0);
            }
        });
    }

    @CrossDecompilerTest
    void Complex28() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 128) int value) {
                return (value % 11 == 0 || value % 7 == 0) && ((value % 3 == 0 && value % 2 == 0) || value % 5 == 0);
            }
        });
    }

    @CrossDecompilerTest
    void Complex29() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 128) int value) {
                return value % 7 == 0 || (value % 3 == 0 || value % 2 == 0) && value % 5 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void Complex30() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 128) int value) {
                return value % 7 == 0 && (value % 3 == 0 && value % 2 == 0 || value % 5 == 0);
            }
        });
    }

    @CrossDecompilerTest
    void Complex31() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 128) int value) {
                return value % 7 == 0 && (value % 5 == 0 || value % 3 == 0) && value % 2 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void IfOr() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                if (value == 1 || value == 3) {
                    return 10;
                } else {
                    return 20;
                }
            }
        });
    }

    @CrossDecompilerTest
    void IfAnd() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 12) int value) {
                if (value % 2 == 0 && value % 3 == 0) {
                    return 10;
                } else {
                    return 20;
                }
            }
        });
    }

    @CrossDecompilerTest
    void IfComplex1() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                if ((value == 3 || value == 4) && value % 2 == 0) {
                    return value;
                } else {
                    return 0;
                }
            }
        });
    }

    @CrossDecompilerTest
    void IfComplex2() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 48) int value) {
                if ((value % 3 == 0 && value % 4 == 0) || (value % 7 == 0 || value % 6 == 0) && value % 5 == 0) {
                    return value;
                } else {
                    return 0;
                }
            }
        });
    }

    @CrossDecompilerTest
    void MethodOr() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 5) int value) {
                return method(value == 1 || value == 2);
            }

            private boolean method(boolean value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void MethodAnd() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 0, to = 48) int value) {
                return method(value % 2 == 0 && value % 3 == 0);
            }

            private boolean method(boolean value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void MethodComplex1() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 0, to = 48) int value) {
                return method((value % 4 == 0 || value % 3 == 0) && value % 2 == 0);
            }

            private boolean method(boolean value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void MethodComplex2() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 0, to = 48) int value) {
                return method((value % 4 == 0 || value % 3 == 0) && value % 2 == 0 || (value % 5 == 0 || value % 7 == 0) && value % 3 == 0);
            }

            private boolean method(boolean value) {
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void WithMethodCall() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 0, to = 12) int value) {
                return (value % 4 == 0 || method3(value)) && value % 2 == 0 || method5(value);
            }

            private boolean method3(int value) {
                return value % 3 == 0;
            }

            private boolean method5(int value) {
                return value % 5 == 0;
            }
        });
    }

    @CrossDecompilerTest
    void VariableOr() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 5) int value) {
                boolean v = value == 1 || value == 2;

                return v;
            }
        });
    }

    @CrossDecompilerTest
    void VariableAnd() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 0, to = 24) int value) {
                boolean v = value % 2 == 0 || value % 3 == 0;

                return v;
            }
        });
    }

    @CrossDecompilerTest
    void VariableComplex1() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 0, to = 24) int value) {
                boolean v = (value % 3 == 0 || value % 4 == 0) && value % 2 == 0;

                return v;
            }
        });
    }

    @CrossDecompilerTest
    void VariableComplex2() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 0, to = 48) int value) {
                boolean v = (value % 3 == 0 || value % 4 == 0) && value % 2 == 0 || value % 5 == 0 && (value % 2 == 0 || value % 3 == 0);

                return v;
            }
        });
    }

    @CrossDecompilerTest
    void ConditionalOperator() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                return value == 1 ? 20 : value;
            }
        });
    }

    @CrossDecompilerTest
    void ConditionalOperatorLogicalSum() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                return value == 1 || value == 2 ? 20 : value;
            }
        });
    }

    @CrossDecompilerTest
    void ConditionalOperatorComplex() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                return (value % 2 == 0 || value % 3 == 0) && value % 5 == 0 ? value + 1 : value + 2;
            }
        });
    }

    @CrossDecompilerTest
    void ConditionalOperatorNest1() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                return value % 2 == 0 ? value == 2 ? 20 : 10 : value;
            }
        });
    }

    @CrossDecompilerTest
    void ConditionalOperatorNest2() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                return value == 1 || value == 2 ? value == 1 ? 20 : 10 : value;
            }
        });
    }

    @CrossDecompilerTest
    void ConditionalOperatorNest3() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                return value % 2 == 0 ? value == 2 || value == 4 ? 20 : 10 : value;
            }
        });
    }

    @CrossDecompilerTest
    void ConditionalOperatorNest4() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                return value == 0 ? 20 : value == 1 ? 10 : value;
            }
        });
    }

    @CrossDecompilerTest
    void ConditionalOperatorNest5() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 100) int value) {
                return value % 2 == 0 ? value % 3 == 0 || value % 4 == 0 && (value % 5 == 0 || value % 7 == 0) ? 20 : 10 : value;
            }
        });
    }

    @CrossDecompilerTest
    void ConditionalOperatorWithIf() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                if (value % 2 == 0 || value % 3 == 0) {
                    return value == 4 ? 20 : 10;
                } else {
                    return value;
                }
            }
        });
    }

    @CrossDecompilerTest
    void ConditionalOperatorInMethod() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                return method(value == 1 ? value + 1 : value + 2);
            }

            private int method(int test) {
                return test + 1;
            }
        });
    }

    @CrossDecompilerTest
    void ConditionalOperatorWithLogicalSumInMethod() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                return method(value == 1 || value == 2 ? value + 1 : value + 2);
            }

            private int method(int test) {
                return test + 1;
            }
        });
    }

    @CrossDecompilerTest
    void ConditionalOperatorComplexInMethod() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 48) int value) {
                return method((value % 2 == 0 || value % 3 == 0) && value % 4 == 0 ? value + 1 : value + 2);
            }

            private int method(int test) {
                return test + 1;
            }
        });
    }

    @CrossDecompilerTest
    void Anonymous() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return new java.lang.Object() {

                    @Override
                    public String toString() {
                        return "Anonymous";
                    };
                }.toString();
            }
        });
    }

    @CrossDecompilerTest
    void IntArrayForEach() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 1, to = 10) int value) {
                int sum = 0;
                int[] array = {0, 1, 2};

                for (int i : array) {
                    sum += i;
                }
                return sum;
            }
        });
    }

    @CrossDecompilerTest
    void If() {
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
    void IfElse() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                if (value < 3) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
    }

    @CrossDecompilerTest
    void lfElseAfter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                if (value < 3) {
                    value = 2;
                } else {
                    value = 0;
                }
                return value;
            }
        });
    }

    @CrossDecompilerTest
    void IfNest() {
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
    void IfNestComplex() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                if (value < 3 && 1 < value || value % 2 == 0) {
                    if (1 < value && value < 4) {
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
    void DoWhile() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                do {
                    value++;
                } while (value < 3);

                return value;
            }
        });
    }

    @CrossDecompilerTest
    void DoWhileEquivalent() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                while (true) {
                    value++;

                    if (3 <= value) {
                        break;
                    }
                }

                return value;
            }
        });
    }

    void DoWhileBreak() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                do {
                    value++;

                    if (value == 2) {
                        break;
                    }
                } while (value < 4);

                return value;
            }
        });
    }

    @CrossDecompilerTest
    void DoWhileInfiniteBreak() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                do {
                    value++;

                    if (value == 10) {
                        break;
                    }
                } while (true);

                return value;
            }
        });
    }

    @CrossDecompilerTest
    void DoWhileContinue() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                do {
                    value += 2;

                    if (value == 2) {
                        continue;
                    }

                    value += 3;
                } while (value < 4);

                return value;
            }
        });
    }

    @CrossDecompilerTest
    void For() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                for (int i = 0; i < 3; i++) {
                    value++;
                }

                return value;
            }
        });
    }

    @CrossDecompilerTest
    void ForWithoutInitialize() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                int i = 0;
                value++;

                for (; i < 3; i++) {
                    value++;
                }

                return value;
            }
        });
    }

    @CrossDecompilerTest
    void ForWithoutUpdate() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                for (int i = 0; i < 8;) {
                    i = value;
                    value += 2;

                    if (value == 5) {
                        break;
                    }
                }

                return value;
            }
        });
    }

    @CrossDecompilerTest
    void ForBreak() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                for (int i = 0; i < 3; i++) {
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
    void ForContinue() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                for (int i = 0; i < 3; i++) {
                    value++;

                    if (value == 10) {
                        continue;
                    }

                    value++;
                }

                return value;
            }
        });
    }
}