/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.flow;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.TestCode;

/**
 * @version 2018/10/29 14:41:58
 */
class LogicalExpressionTest extends CodeVerifier {

    @Test
    void True() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return true;
            }
        });
    }

    @Test
    void False() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return false;
            }
        });
    }

    @Test
    void Equal() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value == 1;
            }
        });
    }

    @Test
    void Not() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value != 1;
            }
        });
    }

    @Test
    void Or() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value == 1 || value == -1;
            }
        });
    }

    @Test
    void NotOr() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value != 1 || value == -1;
            }
        });
    }

    @Test
    void OrNot() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value == 1 || value != -1;
            }
        });
    }

    @Test
    void NotOrNot() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value != 1 || value != -1;
            }
        });
    }

    @Test
    void MultipleOr() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 2 == 0 || value % 3 == 0 || value % 5 == 0;
            }
        });
    }

    @Test
    void OrWithOtherStatement() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                int i = value;

                return i == 1 || i != -1;
            }
        });
    }

    @Test
    void And() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 2 == 0 && value % 3 == 0;
            }
        });
    }

    @Test
    void NotAnd() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 2 != 0 && value % 3 == 0;
            }
        });
    }

    @Test
    void AndNot() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 2 == 0 && value % 3 != 0;
            }
        });
    }

    @Test
    void NotAndNot() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 2 != 0 && value % 3 != 0;
            }
        });
    }

    @Test
    void MultipleAnd() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 2 == 0 && value % 3 == 0 && value % 4 == 0;
            }
        });
    }

    @Test
    void postIncrement() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 10) int value) {
                return value++ % 2 == 0 || value++ % 5 == 0;
            }
        });
    }

    @Test
    void preIncrement() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 10) int value) {
                return ++value % 2 == 0 || ++value % 5 == 0;
            }
        });
    }

    @Test
    void postDecrement() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 10) int value) {
                return value-- % 2 == 0 || value-- % 5 == 0;
            }
        });
    }

    @Test
    void preDecrement() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 10) int value) {
                return --value % 2 == 0 || --value % 5 == 0;
            }
        });
    }

    @Test
    void Complex1() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 5 == 0 || value % 3 == 0 && value % 4 == 0;
            }
        });
    }

    @Test
    void Complex2() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 5 == 0 || (value % 3 == 0 && value % 4 == 0);
            }
        });
    }

    @Test
    void Complex3() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return (value % 5 == 0 || value % 3 == 0) && value % 2 == 0;
            }
        });
    }

    @Test
    void Complex4() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 2 == 0 && value % 3 == 0 || value % 4 == 0;
            }
        });
    }

    @Test
    void Complex5() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return (value % 2 == 0 && value % 3 == 0) || value % 4 == 0;
            }
        });
    }

    @Test
    void Complex6() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 3 == 0 && (value % 2 == 0 || value % 5 == 0);
            }
        });
    }

    @Test
    void Complex10() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 5 == 0 && value % 3 == 0 || value % 2 == 0 || value % 7 == 0;
            }
        });
    }

    @Test
    void Complex11() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 5 == 0 || value % 3 == 0 && value % 2 == 0 || value % 7 == 0;
            }
        });
    }

    @Test
    void Complex12() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return value % 5 == 0 || value % 3 == 0 || value % 2 == 0 && value % 7 == 0;
            }
        });
    }

    @Test
    void Complex13() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return (value % 5 == 0 || value % 3 == 0 || value % 7 == 0) && value % 2 == 0;
            }
        });
    }

    @Test
    void Complex14() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 24) int value) {
                return (value % 5 == 0 && value % 3 == 0) || (value % 7 == 0 && value % 2 == 0);
            }
        });
    }

    @Test
    void Complex20() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 48) int value) {
                return (value % 3 == 0 && value % 4 == 0) || (value % 7 == 0 || value % 6 == 0) && value % 5 == 0;
            }
        });
    }

    @Test
    void Complex21() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 48) int value) {
                return ((value % 3 == 0 && value % 4 == 0) || (value % 7 == 0 || value % 6 == 0)) && value % 5 == 0;
            }
        });
    }

    @Test
    void Complex22() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 48) int value) {
                return (value % 3 == 0 || value % 4 == 0) && (value % 5 == 0 || value % 6 == 0) && value % 2 == 0;
            }
        });
    }

    @Test
    void Complex23() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 48) int value) {
                return ((value % 3 == 0 || value % 4 == 0) && (value % 7 == 0 || value % 11 == 0)) || value % 5 == 0;
            }
        });
    }

    @Test
    void Complex24() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 128) int value) {
                return value % 5 == 0 && ((value % 3 == 0 && value % 4 == 0) || (value % 7 == 0 || value % 6 == 0));
            }
        });
    }

    @Test
    void Complex25() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 128) int value) {
                return ((value % 3 == 0 && value % 4 == 0) || (value % 7 == 0 && value % 6 == 0)) && value % 5 == 0;
            }
        });
    }

    @Test
    void Complex26() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 128) int value) {
                return (value % 3 == 0 && value % 4 == 0) || (value % 7 == 0 && value % 6 == 0) && value % 5 == 0;
            }
        });
    }

    @Test
    void Complex27() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 128) int value) {
                return value % 3 == 0 || value % 4 == 0 || value % 7 == 0 && (value % 6 == 0 || value % 5 == 0);
            }
        });
    }

    @Test
    void Complex28() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 128) int value) {
                return (value % 11 == 0 || value % 7 == 0) && ((value % 3 == 0 && value % 2 == 0) || value % 5 == 0);
            }
        });
    }

    @Test
    void Complex29() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 128) int value) {
                return value % 7 == 0 || (value % 3 == 0 || value % 2 == 0) && value % 5 == 0;
            }
        });
    }

    @Test
    void Complex30() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 128) int value) {
                return value % 7 == 0 && (value % 3 == 0 && value % 2 == 0 || value % 5 == 0);
            }
        });
    }

    @Test
    void Complex31() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 128) int value) {
                return value % 7 == 0 && (value % 5 == 0 || value % 3 == 0) && value % 2 == 0;
            }
        });
    }

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
    void VariableOr() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 1, to = 5) int value) {
                boolean v = value == 1 || value == 2;

                return v;
            }
        });
    }

    @Test
    void VariableAnd() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 0, to = 24) int value) {
                boolean v = value % 2 == 0 || value % 3 == 0;

                return v;
            }
        });
    }

    @Test
    void VariableComplex1() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 0, to = 24) int value) {
                boolean v = (value % 3 == 0 || value % 4 == 0) && value % 2 == 0;

                return v;
            }
        });
    }

    @Test
    void VariableComplex2() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(@Param(from = 0, to = 48) int value) {
                boolean v = (value % 3 == 0 || value % 4 == 0) && value % 2 == 0 || value % 5 == 0 && (value % 2 == 0 || value % 3 == 0);

                return v;
            }
        });
    }

    @Test
    void ConditionalOperator() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                return value == 1 ? 20 : value;
            }
        });
    }

    @Test
    void ConditionalOperatorLogicalSum() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                return value == 1 || value == 2 ? 20 : value;
            }
        });
    }

    @Test
    void ConditionalOperatorComplex() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                return (value % 2 == 0 || value % 3 == 0) && value % 5 == 0 ? value + 1 : value + 2;
            }
        });
    }

    @Test
    void ConditionalOperatorNest1() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                return value % 2 == 0 ? value == 2 ? 20 : 10 : value;
            }
        });
    }

    @Test
    void ConditionalOperatorNest2() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                return value == 1 || value == 2 ? value == 1 ? 20 : 10 : value;
            }
        });
    }

    @Test
    void ConditionalOperatorNest3() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                return value % 2 == 0 ? value == 2 || value == 4 ? 20 : 10 : value;
            }
        });
    }

    @Test
    void ConditionalOperatorNest4() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                return value == 0 ? 20 : value == 1 ? 10 : value;
            }
        });
    }

    @Test
    void ConditionalOperatorNest5() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 100) int value) {
                return value % 2 == 0 ? value % 3 == 0 || value % 4 == 0 && (value % 5 == 0 || value % 7 == 0) ? 20 : 10 : value;
            }
        });
    }

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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