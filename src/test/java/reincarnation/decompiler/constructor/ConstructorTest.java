/*
 * Copyright (C) 2016 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.constructor;

import org.junit.jupiter.api.Test;

import reincarnation.Code;
import reincarnation.CodeVerifier;
import reincarnation.Debuggable;

/**
 * @version 2018/10/10 11:47:57
 */
class ConstructorTest extends CodeVerifier {

    @Test
    void NoParameterTopLevelClass() {
        verify(new Code.Text() {

            @Override
            public String run() {
                return new NoParameter().toString();
            }
        });
    }

    @Test
    void ImplicitConstructorOfPublicMember() {
        verify(new Code.Text() {

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

    @Test
    void ImplicitConstructorOfProtectedMember() {
        verify(new Code.Text() {

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

    @Test
    void ImplicitConstructorOfPackageMember() {
        verify(new Code.Text() {

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

    @Test
    void ImplicitConstructorOfPrivateMember() {
        verify(new Code.Text() {

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

    @Test
    void ExplicitConstructorOfPrivateMember() {
        verify(new Code.Text() {

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

    @Test
    void Parameter() {
        verify(new Code.TextParam() {

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

    @Test
    void Parameters() {
        verify(new Code.TextParam() {

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

    @Test
    void Overload1() {
        verify(new Code.Text() {

            @Override
            public String run() {
                return new Overload().toString();
            }
        });
    }

    @Test
    void Overload2() {
        verify(new Code.TextParam() {

            @Override
            public String run(String value) {
                return new Overload(value).toString();
            }
        });
    }

    @Test
    void Overload3() {
        verify(new Code.TextParam() {

            @Override
            public String run(String value) {
                return new Overload(value, 1).toString();
            }
        });
    }

    @Test
    void Overload4() {
        verify(new Code.TextParam() {

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

    @Test
    void Extend1() {
        verify(new Code.TextParam() {

            @Override
            public String run(String value) {
                Child external = new Child(value);

                return external.toString();
            }
        });
    }

    @Test
    void Extend2() {
        verify(new Code.TextParam() {

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

    @Test
    void Local() {
        verify(new Code.TextParam() {

            private String value = "outer";

            @Override
            public String run(String value) {
                return new Inner().toString();
            }

            /**
             * @version 2018/10/10 11:49:11
             */
            class Inner {

                /**
                 * {@inheritDoc}
                 */
                @Override
                public String toString() {
                    return value;
                }
            }
        });
    }

    @Test
    void Anonymous() {
        verify(new Code.TextParam() {

            @Debuggable
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

    @Test
    void AnonymousWIthOuterAccess() {
        verify(new Code.TextParam() {

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
