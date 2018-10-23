/*
 * Copyright (C) 2016 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.operator;

import org.junit.jupiter.api.Test;

import reincarnation.Code;
import reincarnation.CodeVerifier;

/**
 * @version 2018/10/23 10:12:59
 */
class LongIncrementTest extends CodeVerifier {

    @Test
    void incrementFieldInMethodCall() {
        verify(new Code.Long() {

            private long index = 0;

            @Override
            public long run() {
                return call(index++);
            }

            private long call(long value) {
                return index + value * 10;
            }
        });
    }

    @Test
    void decrementFieldInMethodCall() {
        verify(new Code.Long() {

            private long index = 0;

            @Override
            public long run() {
                return call(index--);
            }

            private long call(long value) {
                return index + value * 10;
            }
        });
    }

    @Test
    void preincrementFieldInMethodCall() {
        verify(new Code.Long() {

            private long index = 0;

            @Override
            public long run() {
                return call(++index);
            }

            private long call(long value) {
                return index + value * 10;
            }
        });
    }

    @Test
    void predecrementFieldInMethodCall() {
        verify(new Code.Long() {

            private long index = 0;

            @Override
            public long run() {
                return call(--index);
            }

            private long call(long value) {
                return index + value * 10;
            }
        });
    }

    @Test
    void incrementFieldInFieldAccess() {
        verify(new Code.Long() {

            private long index = 1;

            private long count = 2;

            @Override
            public long run() {
                index = count++;

                return count + index * 10;
            }
        });
    }

    @Test
    void decrementFieldInFieldAccess() {
        verify(new Code.Long() {

            private long index = 1;

            private long count = 2;

            @Override
            public long run() {
                index = count--;

                return count + index * 10;
            }
        });
    }

    @Test
    void preincrementFieldInFieldAccess() {
        verify(new Code.Long() {

            private long index = 1;

            private long count = 2;

            @Override
            public long run() {
                index = ++count;

                return count + index * 10;
            }
        });
    }

    @Test
    void predecrementFieldInFieldAccess() {
        verify(new Code.Long() {

            private long index = 1;

            private long count = 2;

            @Override
            public long run() {
                index = --count;

                return count + index * 10;
            }
        });
    }
}
