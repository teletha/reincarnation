/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.primitives;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import reincarnation.Code;
import reincarnation.CodeVerifier;

/**
 * @version 2018/10/04 16:55:00
 */
public class IntTest extends CodeVerifier {

    @Test
    public void max() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return Integer.MAX_VALUE;
            }
        });
    }

    @Test
    public void min() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return Integer.MIN_VALUE;
            }
        });
    }

    @Test
    public void add() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value + 1;
            }
        });
    }

    @Test
    public void addAssignable() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value += 2;
            }
        });
    }

    @Test
    public void subtrrun() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value - 1;
            }
        });
    }

    @Test
    public void subtractAssignable() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value -= 2;
            }
        });
    }

    @Test
    public void multiply() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value * 2;
            }
        });
    }

    @Test
    public void multiplyAssignable() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value *= 2;
            }
        });
    }

    @Test
    public void divide() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value / 2;
            }
        });
    }

    @Test
    public void divideAssignable() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value /= 2;
            }
        });
    }

    @Test
    public void modulo() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value % 2;
            }
        });
    }

    @Test
    public void moduloAssignable() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value %= 2;
            }
        });
    }

    @Test
    @Disabled
    public void bitFlag() {
        // verify(new Code.IntParam() {
        //
        // @Override
        // boolean run(int value) {
        // return (value & 1) == 0;
        // }
        // });
    }

    @Test
    public void bitAnd() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value & 0x010101;
            }
        });
    }

    @Test
    public void bitOr() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value | 0x010101;
            }
        });
    }

    @Test
    public void bitOrAssignable() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value |= 0x010101;
            }
        });
    }

    @Test
    public void bitXor() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value ^ 0x010101;
            }
        });
    }

    @Test
    public void bitXorAssignable() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value ^= 0x010101;
            }
        });
    }

    @Test
    public void bitNot() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return ~value;
            }
        });
    }

    @Test
    public void shiftLeft() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value << 1;
            }
        });
    }

    @Test
    public void shiftLeftAssignable() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value <<= 1;
            }
        });
    }

    @Test
    public void shiftRight() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value >> 1;
            }
        });
    }

    @Test
    public void shiftRightAssignable() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value >>= 1;
            }
        });
    }

    @Test
    public void unsignedShiftRight() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value >>> 1;
            }
        });
    }

    @Test
    public void unsignedShiftRightAssignable() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value >>>= 1;
            }
        });
    }

    @Test
    public void postIncrement() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value++;
            }
        });
    }

    @Test
    public void postIncrementValue() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                int next = value++;
                return value + next;
            }
        });
    }

    @Test
    public void postIncrementLike() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return value + 1;
            }
        });
    }

    @Test
    public void preIncrement() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return ++value;
            }
        });
    }

    @Test
    public void preIncrementInStatement() {
        verify(new Code.IntParam() {

            @Override
            public int run(int value) {
                return 2 * ++value;
            }
        });
    }

    @Test
    public void incrementStatiFieldInFieldAccess() throws Exception {
        verify(new IncrementStaticField());
    }

    /**
     * @version 2018/10/04 16:54:17
     */
    private static class IncrementStaticField implements Code.Int {

        private static int index = 1;

        private static int count = 2;

        @Override
        public int run() {
            index = count++;

            return count + index * 10;
        }
    }

    @Test
    public void decrementStatiFieldInFieldAccess() throws Exception {
        verify(new DecrementStaticField());
    }

    /**
     * @version 2013/03/13 17:21:44
     */
    private static class DecrementStaticField implements Code.Int {

        private static int index = 1;

        private static int count = 2;

        @Override
        public int run() {
            index = count--;

            return count + index * 10;
        }
    }

    @Test
    public void preincrementStatiFieldInFieldAccess() throws Exception {
        verify(new PreincrementStaticField());
    }

    /**
     * @version 2013/03/13 17:21:44
     */
    private static class PreincrementStaticField implements Code.Int {

        private static int index = 1;

        private static int count = 2;

        @Override
        public int run() {
            index = ++count;

            return count + index * 10;
        }
    }

    @Test
    public void predecrementStatiFieldInFieldAccess() throws Exception {
        verify(new PredecrementStaticField());
    }

    /**
     * @version 2013/03/13 17:21:44
     */
    private static class PredecrementStaticField implements Code.Int {

        private static int index = 1;

        private static int count = 2;

        @Override
        public int run() {
            index = --count;

            return count + index * 10;
        }
    }
}
