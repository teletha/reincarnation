/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
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
    void incrementVariableInMethodCall() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                long sum1 = sum(value++, value++, value++);
                long sum2 = sum(++sum1, ++sum1, ++sum1);
                return sum(++sum2, sum2++, ++sum2);
            }

            private long sum(long a, long b, long c) {
                return a + b + c;
            }
        });
    }

    @Test
    void decrementVariableInMethodCall() {
        verify(new Code.LongParam() {

            @Override
            public long run(long value) {
                long sum1 = sum(value--, value--, value--);
                long sum2 = sum(--sum1, --sum1, --sum1);
                return sum(--sum2, sum2--, --sum2);
            }

            private long sum(long a, long b, long c) {
                return a + b + c;
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
