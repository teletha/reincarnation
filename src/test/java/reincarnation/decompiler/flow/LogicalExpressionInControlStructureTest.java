/*
 * Copyright (C) 2016 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.flow;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.TestCode;

class LogicalExpressionInControlStructureTest extends CodeVerifier {

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
    void nest() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(@Param(from = 0, to = 10) int value) {
                if (1 < value && value <= 10) {
                    while (value % 3 != 0 || value % 2 != 0) {
                        if (value == 5 || value == 7) {
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
