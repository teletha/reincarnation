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

import js.lang.NativeArray;
import js.lang.NativeObject;
import js.lang.PropertyDescriptor;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for {@link NativeObject}.
 */
class NativeObjectTest extends JavascriptCodeVerifier {

    @Test
    void properties() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NativeObject object = new NativeObject();
                object.setProperty("a", 1);
                object.setProperty(1, "one");
                object.setInt("b", 2);
                object.setLong("c", 3L);
                object.setDouble("d", 1.5);
                object.setBoolean("e", true);
                object.setChar("f", 'x');
                return object.getProperty("a") + "|" + object.getProperty(1) + "|" + object.getInt("b") + "|" + object.getLong("c") + "|" + object.getDouble("d") + "|" + (object.getBoolean("e") ? "true" : "false") + "|" + object.getChar("f") + "|" + (object.hasProperty("a") ? "true" : "false") + "|" + (object.hasOwnProperty("b") ? "true" : "false") + "|" + (object.hasProperty("z") ? "true" : "false");
            }
        });
    }

    @Test
    void defaults() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NativeObject object = new NativeObject();
                object.setProperty("a", 1);
                return object.getProperty("a", 9) + "|" + object.getProperty("b", 9) + "|" + object.getProperty(0, 7);
            }
        });
    }

    @Test
    void numericProperties() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NativeObject object = new NativeObject();
                object.setByte("b", (byte) 1);
                object.setShort("s", (short) 2);
                object.setFloat("f", 1.5F);
                object.setInt(2, 3);
                return object.getByte("b") + "|" + object.getShort("s") + "|" + object.getFloat("f") + "|" + object.getInt(2);
            }
        });
    }

    @Test
    void keysValuesEntries() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NativeObject object = new NativeObject();
                object.setProperty("a", 1);
                object.setProperty("b", 2);
                return object.keys().join(",") + "|" + object.values().join(",") + "|" + object.entries().length() + "|" + object.getOwnPropertyNames().length();
            }
        });
    }

    @Test
    void deleteProperty() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NativeObject object = new NativeObject();
                object.setProperty("a", 1);
                object.setProperty("b", 2);
                boolean deleted = object.deleteProperty("a");
                return (deleted ? "true" : "false") + "|" + (object.hasProperty("a") ? "true" : "false") + "|" + object.keys().length();
            }
        });
    }

    @Test
    void propertyDescriptor() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NativeObject object = new NativeObject();
                NativeObject.defineProperty(object, "x", new PropertyDescriptor<>(42, false, true, false));
                PropertyDescriptor descriptor = NativeObject.getOwnPropertyDescriptor(object, "x");
                return object.getInt("x") + "|" + descriptor.value + "|" + (descriptor.writable ? "true" : "false") + "|" + (descriptor.enumerable ? "true" : "false") + "|" + (descriptor.configurable ? "true" : "false");
            }
        });
    }

    @Test
    void staticProperties() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NativeObject source = new NativeObject();
                source.setProperty("a", 1);
                NativeObject target = new NativeObject();
                NativeObject.assign(target, source);
                return (NativeObject.hasProperty(target, "a") ? "true" : "false") + "|" + (NativeObject.hasOwn(target, "a") ? "true" : "false") + "|" + (NativeObject.is(target, target) ? "true" : "false") + "|" + NativeObject.keys(target).length();
            }
        });
    }

    @Test
    void fromEntries() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                NativeArray entries = new NativeArray();
                NativeArray pair = new NativeArray();
                pair.push("a");
                pair.push(1);
                entries.push(pair);
                return NativeObject.fromEntries(entries).getInt("a");
            }
        });
    }
}
