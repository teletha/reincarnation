/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.method;

import java.util.ArrayList;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class ExtendTest extends CodeVerifier {

    @CrossDecompilerTest
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

    @CrossDecompilerTest
    void extendClassWithParameter() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                class Parent {

                    int value;

                    Parent(int value) {
                        this.value = value;
                    }

                    int value() {
                        return value;
                    }
                }

                class Child extends Parent {

                    Child(int value) {
                        super(value);
                    }
                }

                return new Child(20).value();
            }
        });
    }

    @CrossDecompilerTest
    void extendClassWithExternalReference() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int value = 20;

                class Parent {
                    int value() {
                        return value;
                    }
                }

                class Child extends Parent {
                }

                return new Child().value();
            }
        });
    }

    @CrossDecompilerTest
    void extendClassWithExternalReferences() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                int a = 20;
                int b = 10;

                class Parent {
                    int value() {
                        return a - b;
                    }
                }

                class Child extends Parent {
                }

                return new Child().value();
            }
        });
    }

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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

    @CrossDecompilerTest
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