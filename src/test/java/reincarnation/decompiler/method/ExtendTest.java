/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.method;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.TestCode;

class ExtendTest extends CodeVerifier {

    @Test
    void extendClass() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                class Parent {
                    int value() {
                        return 10;
                    }
                }

                class Child extends Parent {
                }

                return new Child().value();
            }
        });
    }

    @Test
    void extendAbstractClass() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                abstract class Parent {
                    int value() {
                        return 10;
                    }
                }

                class Child extends Parent {
                }

                return new Child().value();
            }
        });
    }

    @Test
    void extendObjectClass() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                class Child extends java.lang.Object {
                    int value() {
                        return 2;
                    }
                }

                return new Child().value();
            }
        });
    }

    @Test
    @SuppressWarnings("serial")
    void extendParamiterizedClass() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                class Child extends ArrayList<String> {
                }

                return new Child().size();
            }
        });
    }

    @Test
    void extendDeeply() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                class Parent {
                    int value() {
                        return 10;
                    }
                }

                class Child extends Parent {
                }

                class GrandChild extends Child {
                }

                return new GrandChild().value();
            }
        });
    }

    @Test
    void implementAbstractMethod() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                abstract class Parent {
                    abstract int value();
                }

                class Child extends Parent {

                    @Override
                    int value() {
                        return 10;
                    }
                }

                return new Child().value();
            }
        });
    }

    @Test
    @SuppressWarnings("unused")
    void overrideParentMethod() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                abstract class Parent {
                    int value() {
                        return 0;
                    }
                }

                class Child extends Parent {

                    @Override
                    int value() {
                        return 10;
                    }
                }

                return new Child().value();
            }
        });
    }

    @Test
    void useParentMethodInChild() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                class Parent {
                    int parentCall() {
                        return -10;
                    }
                }

                class Child extends Parent {
                    int childCall() {
                        return parentCall() + 20;
                    }
                }

                return new Child().childCall();
            }
        });
    }

    @Test
    void useOverriddenParentMethodInChild() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                class Parent {
                    int parentCall() {
                        return -10;
                    }
                }

                class Child extends Parent {
                    @Override
                    int parentCall() {
                        return super.parentCall() + 20;
                    }
                }

                return new Child().parentCall();
            }
        });
    }

    @Test
    void useParentFieldInChild() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                class Parent {
                    int onParent = -10;
                }

                class Child extends Parent {
                    int onChild = onParent + 20;
                }

                return new Child().onChild;
            }
        });
    }

    @Test
    @SuppressWarnings("unused")
    void shadowParentFieldInChild() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                class Parent {
                    int onParent = -10;
                }

                class Child extends Parent {
                    int onParent = 20;
                }

                return new Child().onParent;
            }
        });
    }

    @Test
    void useShadowedParentFieldInChild() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                class Parent {
                    int onParent = -10;
                }

                class Child extends Parent {
                    int onParent = super.onParent + 20;
                }

                return new Child().onParent;
            }
        });
    }
}
