/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.collection;

import java.util.BitSet;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the additional {@code java.util} classes.
 */
class JavaUtilClasslibTest extends JavascriptCodeVerifier {

    @Test
    void random() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Random random = new Random(12345L);
                return random.nextInt() + "|" + random.nextInt(100) + "|" + random.nextLong() + "|" + random.nextDouble() + "|" + (double) random.nextFloat() + "|" + random.nextBoolean();
            }
        });
    }

    @Test
    void bitSet() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                BitSet bits = new BitSet();
                bits.set(1);
                bits.set(3);
                bits.set(1, 5);
                bits.set(10);
                BitSet other = new BitSet();
                other.set(3);
                other.set(7);
                return bits + "|" + bits.cardinality() + "|" + bits.length() + "|" + bits.size() + "|" + bits.get(3) + "|" + bits.nextSetBit(0) + "|" + bits.nextClearBit(0) + "|" + bits.toLongArray()[0] + "|" + bits.intersects(other) + "|" + ((BitSet) bits.clone()).cardinality();
            }
        });
    }

    @Test
    void bitSetOperations() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                BitSet bits = new BitSet();
                bits.set(1);
                bits.set(3);
                BitSet other = new BitSet();
                other.set(3);
                other.set(7);
                BitSet and = (BitSet) bits.clone();
                and.and(other);
                BitSet or = (BitSet) bits.clone();
                or.or(other);
                BitSet xor = (BitSet) bits.clone();
                xor.xor(other);
                BitSet andNot = (BitSet) bits.clone();
                andNot.andNot(other);
                return and + "|" + or + "|" + xor + "|" + andNot;
            }
        });
    }

    @Test
    void properties() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Properties defaults = new Properties();
                defaults.setProperty("b", "2");
                Properties properties = new Properties(defaults);
                properties.setProperty("a", "1");
                properties.setProperty("c", "3");
                return properties.getProperty("a") + "|" + properties.getProperty("b") + "|" + properties.getProperty("c") + "|" + properties.getProperty("d", "x") + "|" + properties.stringPropertyNames().size();
            }
        });
    }

    @Test
    void enumMap() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                EnumMap<Color, Integer> map = new EnumMap<>(Color.class);
                map.put(Color.RED, 1);
                map.put(Color.BLUE, 3);
                return map.get(Color.RED) + "|" + map.size() + "|" + map.containsKey(Color.GREEN) + "|" + map.keySet().size() + "|" + map.clone().get(Color.BLUE);
            }
        });
    }

    @Test
    void enumSet() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                EnumSet<Color> set = EnumSet.of(Color.RED, Color.BLUE);
                return set.size() + "|" + set.contains(Color.RED) + "|" + set.contains(Color.GREEN) + "|" + EnumSet.allOf(Color.class).size() + "|" + EnumSet.range(Color.GREEN, Color.BLUE).size() + "|" + EnumSet.complementOf(set).size() + "|" + set;
            }
        });
    }

    @Test
    void resourceBundle() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                ResourceBundle bundle = new Bundle();
                return bundle.getString("value") + "|" + bundle.containsKey("value") + "|" + bundle.keySet().size();
            }
        });
    }

    /** The test enum. */
    enum Color {

        RED, GREEN, BLUE
    }

    /** The test bundle. */
    static class Bundle extends ResourceBundle {

        @Override
        protected Object handleGetObject(String key) {
            return "value".equals(key) ? "hello" : null;
        }

        @Override
        public Enumeration<String> getKeys() {
            return new Enumeration<String>() {

                private int index;

                @Override
                public boolean hasMoreElements() {
                    return index < 1;
                }

                @Override
                public String nextElement() {
                    index++;
                    return "value";
                }
            };
        }
    }
}
