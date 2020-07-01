/*
 * Copyright (C) 2020 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.primitives;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.CodeVerifier;

/**
 * @version 2018/10/22 16:04:57
 */
class TypeConversionTest extends CodeVerifier {

    @Test
    void longToInt() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                long value = 123L;
                assert ((int) value) == 123;

                value = Integer.MAX_VALUE;
                assert ((int) value) == 2147483647;

                value = Integer.MAX_VALUE + 1;
                assert ((int) value) == -2147483648;

                value = Integer.MIN_VALUE;
                assert ((int) value) == -2147483648;

                value = Integer.MIN_VALUE - 1;
                assert ((int) value) == 2147483647;

                value = 123456789012L;
                assert ((int) value) == -1097262572;

                value = -9876543210987654L;
                assert ((int) value) == 374136698;
            }
        });
    }

    @Test
    void intToLong() {
        verify(new TestCode.Run() {

            @Override
            public void run() {
                int value = 123;
                assert (value) == 123L;

                value = Integer.MAX_VALUE;
                assert (value) == 2147483647L;

                value = Integer.MAX_VALUE + 1;
                assert (value) == -2147483648L;

                value = Integer.MIN_VALUE;
                assert (value) == -2147483648L;

                value = Integer.MIN_VALUE - 1;
                assert (value) == 2147483647L;
            }
        });
    }
}