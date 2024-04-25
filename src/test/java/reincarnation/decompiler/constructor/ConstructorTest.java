/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.constructor;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class ConstructorTest extends CodeVerifier {

    @CrossDecompilerTest
    void NoParameterTopLevelClass() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return new NoParameter().toString();
            }
        });
    }

    @CrossDecompilerTest
    void ImplicitConstructorOfPublicMember() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return new ImplicitConstructorOfPublicMember().toString();
            }
        });
    }

    /**
     * @version 2018/10/10 11:50:52
     */
    public static class ImplicitConstructorOfPublicMember {

        @Override
        public String toString() {
            return "No Param Public Member";
        }
    }

    @CrossDecompilerTest
    void ImplicitConstructorOfProtectedMember() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return new ImplicitConstructorOfProtectedMember().toString();
            }
        });
    }

    /**
     * @version 2018/10/10 11:50:44
     */
    protected static class ImplicitConstructorOfProtectedMember {

        @Override
        public String toString() {
            return "No Param Protected Member";
        }
    }

    @CrossDecompilerTest
    void ImplicitConstructorOfPackageMember() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return new ImplicitConstructorOfPackageMember().toString();
            }
        });
    }

    /**
     * @version 2012/12/01 3:58:43
     */
    static class ImplicitConstructorOfPackageMember {

        @Override
        public String toString() {
            return "No Param Package Member";
        }
    }

    @CrossDecompilerTest
    void ImplicitConstructorOfPrivateMember() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return new ImplicitConstructorOfPrivateMember().toString();
            }
        });
    }

    /**
     * <p>
     * Private class defines <em>private</em> constructor implicitly, so compiler generates
     * <em>package private</em> access constructor.
     * </p>
     * 
     * @version 2018/10/10 11:50:21
     */
    private static class ImplicitConstructorOfPrivateMember {

        @Override
        public String toString() {
            return "No Param Private Member";
        }
    }

    @CrossDecompilerTest
    void ExplicitConstructorOfPrivateMember() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return new ExplicitConstructorOfPrivateMember().toString();
            }
        });
    }

    /**
     * <p>
     * This class defines <em>private</em> constructor explicitly, so compiler generates <em>package
     * private</em> access constructor.
     * </p>
     * 
     * @version 2018/10/10 11:50:26
     */
    private static class ExplicitConstructorOfPrivateMember {

        private ExplicitConstructorOfPrivateMember() {
            // do nothing
        }

        @Override
        public String toString() {
            return "No Param Private Member";
        }
    }

    @CrossDecompilerTest
    void Parameter() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String value) {
                return new Parameter(value).toString();
            }
        });
    }

    /**
     * @version 2018/10/10 11:50:29
     */
    private static class Parameter {

        private final String param;

        public Parameter(String param) {
            this.param = param;
        }

        @Override
        public String toString() {
            return "Parameter [param=" + param + "]";
        }
    }

    @CrossDecompilerTest
    void Parameters() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String value) {
                return new Parameters(value, 1).toString();
            }
        });
    }

    /**
     * @version 2018/10/10 11:50:33
     */
    private static class Parameters {

        private final String param;

        private final int index;

        public Parameters(String param, int index) {
            this.param = param;
            this.index = index;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "Parameters [index=" + index + ", param=" + param + "]";
        }
    }

    @CrossDecompilerTest
    void Overload1() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return new Overload().toString();
            }
        });
    }

    @CrossDecompilerTest
    void Overload2() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String value) {
                return new Overload(value).toString();
            }
        });
    }

    @CrossDecompilerTest
    void Overload3() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String value) {
                return new Overload(value, 1).toString();
            }
        });
    }

    @CrossDecompilerTest
    void Overload4() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String value) {
                return new Overload(value, value).toString();
            }
        });
    }

    /**
     * @version 2018/10/10 11:49:40
     */
    private static class Overload {

        private final String param;

        private final int index;

        public Overload() {
            this("", 0);
        }

        public Overload(String param) {
            this(param, 0);
        }

        public Overload(String param, int index) {
            this.param = param;
            this.index = index;
        }

        public Overload(String param, String index) {
            this.param = param;
            this.index = index.length();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "Parameters [index=" + index + ", param=" + param + "]";
        }
    }

    @CrossDecompilerTest
    void Extend1() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String value) {
                Child external = new Child(value);

                return external.toString();
            }
        });
    }

    @CrossDecompilerTest
    void Extend2() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String value) {
                Child external = new Child(2, value);

                return external.toString();
            }
        });
    }

    /**
     * @version 2018/10/10 11:49:23
     */
    protected static class Base {

        protected final String name;

        public Base(String name) {
            this.name = name;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "ExternalClass [name=" + name + "]";
        }
    }

    /**
     * @version 2018/10/10 11:49:17
     */
    protected static class Child extends Base {

        public Child(String name) {
            super(name);
        }

        public Child(int type, String name) {
            super(name);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "ChildClass [name=" + name + "]";
        }
    }

    @CrossDecompilerTest
    void Inner() {
        verify(new TestCode.TextParam() {

            private String value = "outer";

            @Override
            public String run(String value) {
                return new Inner().toString();
            }

            class Inner {
                @Override
                public String toString() {
                    return value;
                }
            }
        });
    }

    @CrossDecompilerTest
    void Local() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String value) {
                class Local {

                    /**
                     * {@inheritDoc}
                     */
                    @Override
                    public String toString() {
                        return "local";
                    }
                }

                return new Local().toString();
            }
        });
    }

    @CrossDecompilerTest
    void SameNameLocalClassInDifferentMethods() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String value) {
                class Local {
                    @Override
                    public String toString() {
                        return "local";
                    }
                }
                return new Local().toString();
            }

            @Override
            public String toString() {
                class Local {
                    @Override
                    public String toString() {
                        return "other local";
                    }
                }
                return new Local().toString();
            }
        });
    }

    @CrossDecompilerTest
    void Anonymous() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(String value) {
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
    void AnonymousWIthOuterAccess() {
        verify(new TestCode.TextParam() {

            @Override
            public String run(final String value) {
                return new java.lang.Object() {

                    @Override
                    public String toString() {
                        return value;
                    };
                }.toString();
            }
        });
    }

}