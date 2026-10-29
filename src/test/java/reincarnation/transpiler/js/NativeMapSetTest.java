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
import js.lang.NativeMap;
import js.lang.NativeSet;
import js.lang.NativeUndefined;
import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript {@code Map} and {@code Set} native bridge.
 */
class NativeMapSetTest extends JavascriptCodeVerifier {

    @Test
    void mapSetGet() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                NativeMap map = new NativeMap();
                map.set("a", 1);
                map.set("b", 2);
                return map.size() * 100 + (int) map.get("a") + (map.has("b") ? 10 : 0);
            }
        });
    }

    @Test
    void mapAbsent() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                NativeMap map = new NativeMap();
                return NativeUndefined.is(map.get("x")) && !map.has("x");
            }
        });
    }

    @Test
    void mapDelete() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                NativeMap map = new NativeMap();
                map.set("a", 1);
                return map.delete("a") && map.size() == 0 && !map.has("a");
            }
        });
    }

    @Test
    void mapKeysValues() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                NativeMap map = new NativeMap();
                map.set("a", 1);
                map.set("b", 2);
                NativeArray keys = map.keys();
                NativeArray values = map.values();
                return keys.length() * 10 + ((String) keys.get(0)).length() + values.length();
            }
        });
    }

    @Test
    void mapEntries() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NativeMap map = new NativeMap();
                map.set("a", 1);
                NativeArray entry = (NativeArray) map.entries().get(0);
                return entry.get(0) + ":" + entry.get(1);
            }
        });
    }

    @Test
    void mapIsMap() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return NativeMap.isMap(new NativeMap()) && !NativeMap.isMap("text");
            }
        });
    }

    @Test
    void setAddHas() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                NativeSet set = new NativeSet();
                set.add("a");
                set.add("b");
                boolean added = set.size() == 2 && set.has("a");
                set.delete("a");
                return added && set.size() == 1 && !set.has("a");
            }
        });
    }

    @Test
    void setValues() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                NativeSet set = new NativeSet();
                set.add("a");
                set.add("b");
                NativeArray values = set.values();
                return values.length() == 2 && values.includes("a");
            }
        });
    }

    @Test
    void setFromArray() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                NativeArray array = new NativeArray();
                array.push("a");
                array.push("b");
                NativeSet set = new NativeSet(array);
                return set.size() == 2 && set.has("a") && set.has("b");
            }
        });
    }

    @Test
    void setIsSet() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return NativeSet.isSet(new NativeSet()) && !NativeSet.isSet("text");
            }
        });
    }
}
