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

class LogicalExpressionInControlStructureTest extends CodeVerifier {

    @CrossDecompilerTest
    void Or() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                if (value == 1 || value == 3) {
                    return value;
                } else {
                    return 0;
                }
            }
        });
    }

    @CrossDecompilerTest
    void Complex() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                if ((value == 1 || value == 3) && value == 10) {
                    return value;
                } else {
                    return 0;
                }
            }
        });
    }

    @CrossDecompilerTest
    void Complex2() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                if ((value == 1 || value == 3) && value == 10) {
                    return value;
                } else {
                    return 0;
                }
            }
        });
    }

    @CrossDecompilerTest
    void NotOr() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                if (value != 1 || value == 3) {
                    return value;
                } else {
                    return 0;
                }
            }
        });
    }

    @CrossDecompilerTest
    void OrNot() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                if (value == 1 || value != 3) {
                    return value;
                } else {
                    return 0;
                }
            }
        });
    }

    @CrossDecompilerTest
    void NotOrNot() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                if (value != 1 || value != 3) {
                    return value;
                } else {
                    return 0;
                }
            }
        });
    }

    @CrossDecompilerTest
    void MultipuleOR() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 5) int value) {
                if (value == 1 || value == 3 || value == 5) {
                    return value;
                } else {
                    return 0;
                }
            }
        });
    }

    @CrossDecompilerTest
    void And() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 20) int value) {
                if (value % 2 == 0 && value % 3 == 0) {
                    return value;
                } else {
                    return 0;
                }
            }
        });
    }

    @CrossDecompilerTest
    void NotAnd() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 20) int value) {
                if (value % 2 != 0 && value % 3 == 0) {
                    return value;
                } else {
                    return 0;
                }
            }
        });
    }

    @CrossDecompilerTest
    void AndNot() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 20) int value) {
                if (value % 2 == 0 && value % 3 != 0) {
                    return value;
                } else {
                    return 0;
                }
            }
        });
    }

    @CrossDecompilerTest
    void NotAndNot() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 20) int value) {
                if (value % 2 != 0 && value % 3 != 0) {
                    return value;
                } else {
                    return 0;
                }
            }
        });
    }

    @CrossDecompilerTest
    void MultipleAnd() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 20) int value) {
                if (value % 2 == 0 && value % 3 == 0 && value % 4 == 0) {
                    return value;
                } else {
                    return 0;
                }
            }
        });
    }

    @CrossDecompilerTest
    void nest() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                if (1 < value && value <= 10) {
                    while (value % 2 != 0 || value % 3 != 0) {
                        if (value == 4 || value == 5) {
                            return 100;
                        }
                        value++;
                    }
                }

                return value;
            }
        });
    }
}