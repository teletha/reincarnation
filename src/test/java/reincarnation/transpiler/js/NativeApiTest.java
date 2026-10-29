/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.js;

import org.junit.jupiter.api.Test;

import js.lang.NativeArray;
import js.lang.NativeMath;
import js.lang.NativeString;
import js.lang.NativeUndefined;
import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript native bridge. The same test runs on the JVM through the Java fallback and on
 * the JavaScript runtime through the native mapping.
 */
class NativeApiTest extends JavascriptCodeVerifier {

    @Test
    void arrayPush() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                NativeArray array = new NativeArray();
                array.push("a");
                array.push("bb");
                return array.length() * 10 + ((String) array.get(1)).length();
            }
        });
    }

    @Test
    void arraySet() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                NativeArray array = new NativeArray(2);
                array.set(0, 7);
                array.set(4, 9);
                return array.length() * 100 + (int) array.get(4);
            }
        });
    }

    @Test
    void arrayPopShift() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                NativeArray array = new NativeArray();
                array.push(1);
                array.push(2);
                array.push(3);
                return (int) array.pop() * 10 + (int) array.shift();
            }
        });
    }

    @Test
    void arraySlice() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                NativeArray array = new NativeArray();
                array.push("a");
                array.push("b");
                array.push("c");
                NativeArray sliced = array.slice(1);
                return sliced.length() * 10 + ((String) sliced.get(0)).length();
            }
        });
    }

    @Test
    void arrayJoin() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NativeArray array = new NativeArray();
                array.push("a");
                array.push("b");
                return array.join("-");
            }
        });
    }

    @Test
    void arrayMap() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NativeArray array = new NativeArray();
                array.push("a");
                array.push("b");
                return (String) array.map(value -> ((String) value).toUpperCase()).get(1);
            }
        });
    }

    @Test
    void arrayIsArray() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return NativeArray.isArray(new NativeArray()) && !NativeArray.isArray("text");
            }
        });
    }

    @Test
    void arrayUndefined() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                NativeArray array = new NativeArray();
                return NativeUndefined.is(array.get(0)) && NativeUndefined.is(array.pop());
            }
        });
    }

    @Test
    void arrayLengthConstructor() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                NativeArray array = new NativeArray(3);
                return array.length() + (NativeUndefined.is(array.get(2)) ? 10 : 0);
            }
        });
    }

    @Test
    void mathFloorCeil() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return (int) NativeMath.floor(2.9) * 100 + (int) NativeMath.ceil(2.1) * 10 + (int) NativeMath.trunc(-2.7);
            }
        });
    }

    @Test
    void mathRound() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return (int) NativeMath.round(2.5) * 10 + (int) NativeMath.round(-1.5);
            }
        });
    }

    @Test
    void mathMinMaxPow() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return (int) NativeMath.max(3, 7) + (int) NativeMath.min(3, 7) * 10 + (int) NativeMath.pow(2, 3) * 100;
            }
        });
    }

    @Test
    void mathSign() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return (int) NativeMath.sign(-9) + (int) NativeMath.sign(9) * 10;
            }
        });
    }

    @Test
    void mathClassification() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return NativeMath.isNaN(0.0 / 0.0) && NativeMath.isFinite(1.0) && !NativeMath.isFinite(java.lang.Double.POSITIVE_INFINITY);
            }
        });
    }

    @Test
    void stringFromCharCode() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return NativeString.fromCharCode(65) + NativeString.fromCharCode(66);
            }
        });
    }

    @Test
    void stringCharCodeAt() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return NativeString.charCodeAt("ABC", 1) * 10 + NativeString.length("abcd");
            }
        });
    }

    @Test
    void stringSubstring() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return NativeString.substring("abcdef", 1, 4) + NativeString.toUpperCase("x");
            }
        });
    }

    @Test
    void stringIncludesIndex() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return NativeString.includes("hello", "ell") && NativeString.indexOf("hello", "l") == 2;
            }
        });
    }

    @Test
    void stringSplit() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                NativeArray parts = NativeString.split("a,b,c", ",");
                return parts.length() * 10 + ((String) parts.get(1)).length();
            }
        });
    }

    @Test
    void stringTrim() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return NativeString.trim("  abc  ");
            }
        });
    }

    @Test
    void undefinedValue() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return NativeUndefined.is(NativeUndefined.INSTANCE);
            }
        });
    }
}
