/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.grammar;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.TestCode;

class TextBlockTest extends CodeVerifier {

    @Test
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

    @Test
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

    @Test
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

    @Test
    void empty() {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return """
                        """;
            }
        });
    }

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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
