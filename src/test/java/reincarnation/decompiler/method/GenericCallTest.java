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

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class GenericCallTest extends CodeVerifier {

    @CrossDecompilerTest
    public void compare() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                String o = "b";

                assert o.compareTo("b") == 0;
                assert o.compareTo("c") == -1;
                assert o.compareTo("a") == 1;
            }
        });
    }

    @CrossDecompilerTest
    public void compareAsComparable() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                Comparable o = "b";

                assert o.compareTo("b") == 0;
                assert o.compareTo("c") == -1;
                assert o.compareTo("a") == 1;
            }
        });
    }

    @CrossDecompilerTest
    public void compareAsComparableString() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                Comparable<String> o = "b";

                assert o.compareTo("b") == 0;
                assert o.compareTo("c") == -1;
                assert o.compareTo("a") == 1;
            }
        });
    }
}