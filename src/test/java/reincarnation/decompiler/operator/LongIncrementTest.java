/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.operator;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class LongIncrementTest extends CodeVerifier {

    @CrossDecompilerTest
    void sequence() {
        verify(new TestCode.LongParam() {

            @Override
            public long run(long value) {
                value++;
                ++value;
                value++;
                return ++value;
            }
        });
    }

    @CrossDecompilerTest
    void incrementFieldInMethodCall() {
        verify(new TestCode.Long() {

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

    @CrossDecompilerTest
    void decrementFieldInMethodCall() {
        verify(new TestCode.Long() {

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

    @CrossDecompilerTest
    void preincrementFieldInMethodCall() {
        verify(new TestCode.Long() {

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

    @CrossDecompilerTest
    void predecrementFieldInMethodCall() {
        verify(new TestCode.Long() {

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

    @CrossDecompilerTest
    void incrementVariableInMethodCall() {
        verify(new TestCode.LongParam() {

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

    @CrossDecompilerTest
    void decrementVariableInMethodCall() {
        verify(new TestCode.LongParam() {

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

    @CrossDecompilerTest
    void incrementFieldInFieldAccess() {
        verify(new TestCode.Long() {

            private long index = 1;

            private long count = 2;

            @Override
            public long run() {
                index = count++;

                return count + index * 10;
            }
        });
    }

    @CrossDecompilerTest
    void decrementFieldInFieldAccess() {
        verify(new TestCode.Long() {

            private long index = 1;

            private long count = 2;

            @Override
            public long run() {
                index = count--;

                return count + index * 10;
            }
        });
    }

    @CrossDecompilerTest
    void preincrementFieldInFieldAccess() {
        verify(new TestCode.Long() {

            private long index = 1;

            private long count = 2;

            @Override
            public long run() {
                index = ++count;

                return count + index * 10;
            }
        });
    }

    @CrossDecompilerTest
    void predecrementFieldInFieldAccess() {
        verify(new TestCode.Long() {

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