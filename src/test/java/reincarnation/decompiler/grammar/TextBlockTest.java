/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.grammar;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class TextBlockTest extends CodeVerifier {

    @CrossDecompilerTest
    void textBlock() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return """
                        block
                        """;
            }
        });
    }

    @CrossDecompilerTest
    void multiLines() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return """
                        This
                        Is
                        Text
                        Block
                        """;
            }
        });
    }

    @CrossDecompilerTest
    void noTailBreak() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return """
                        This
                        Is
                        Text
                        Block""";
            }
        });
    }

    @CrossDecompilerTest
    void empty() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return """
                        """;
            }
        });
    }

    @CrossDecompilerTest
    void doubleQuote() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return """
                        "quoted"
                        """;
            }
        });
    }

    @CrossDecompilerTest
    void lineBreak() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return """
                        explicite\r\n
                        line break
                        """;
            }
        });
    }

    @CrossDecompilerTest
    void noLineBreak() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return """
                        no\
                        line break
                        """;
            }
        });
    }

    @CrossDecompilerTest
    void lineHeadSpace() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return """
                         Head
                        """;
            }
        });
    }

    @CrossDecompilerTest
    void lineHeadSpace2() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return """
                         Space
                           Multi
                        \sEscaped
                        \tTab
                        """;
            }
        });
    }

    @CrossDecompilerTest
    void lineTailSpace() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return """
                        Tail\s
                        """;
            }
        });
    }

    @CrossDecompilerTest
    void lineTailSpace2() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return """
                        Space \s
                        Multi    \s
                        Escape\s\s
                        Tab\t\s
                        """;
            }
        });
    }
}