/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.method;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.TestCode;

class GenericCallTest extends CodeVerifier {

    @Test
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

    @Test
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

    @Test
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