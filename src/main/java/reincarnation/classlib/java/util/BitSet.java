/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.util;

import reincarnation.classlib.java.io.Serializable;
import reincarnation.classlib.java.lang.Cloneable;

/**
 * The Java implementation of {@link java.util.BitSet} which is transpiled into JavaScript.
 */
public class BitSet implements Cloneable, Serializable {

    /** The bits. */
    private boolean[] bits;

    /**
     * Create the empty bit set.
     */
    public BitSet() {
        this.bits = new boolean[64];
    }

    /**
     * Create the empty bit set.
     * 
     * @param nbits A bit size.
     */
    public BitSet(int nbits) {
        if (nbits < 0) {
            throw new NegativeArraySizeException("nbits < 0: " + nbits);
        }
        this.bits = new boolean[capacity(nbits)];
    }

    /**
     * Create the bit set.
     * 
     * @param bits Bits.
     */
    private BitSet(boolean[] bits) {
        this.bits = bits;
    }

    /**
     * Create the bit set from the long values.
     * 
     * @param words Words.
     * @return A bit set.
     */
    public static BitSet valueOf(long[] words) {
        if (words == null) {
            throw new NullPointerException("longs");
        }
        int length = words.length * 64;
        BitSet bitset = new BitSet(length);
        for (int i = 0; i < words.length; i++) {
            long word = words[i];
            for (int bit = 0; bit < 64; bit++) {
                if ((word & (1L << bit)) != 0L) {
                    bitset.bits[i * 64 + bit] = true;
                }
            }
        }
        bitset.trim();
        return bitset;
    }

    /**
     * Create the bit set from the byte values.
     * 
     * @param bytes Bytes.
     * @return A bit set.
     */
    public static BitSet valueOf(byte[] bytes) {
        if (bytes == null) {
            throw new NullPointerException("bytes");
        }
        BitSet bitset = new BitSet(bytes.length * 8);
        for (int i = 0; i < bytes.length; i++) {
            int value = bytes[i] & 0xFF;
            for (int bit = 0; bit < 8; bit++) {
                if ((value & (1 << bit)) != 0) {
                    bitset.bits[i * 8 + bit] = true;
                }
            }
        }
        bitset.trim();
        return bitset;
    }

    /**
     * Compute the capacity.
     * 
     * @param bits Bits.
     * @return A capacity.
     */
    private static int capacity(int bits) {
        return bits == 0 ? 0 : (int) (((long) bits + 63L) / 64L) * 64;
    }

    /**
     * Ensure the capacity.
     * 
     * @param index An index.
     */
    private void ensure(int index) {
        int required = capacity(index + 1);
        if (bits.length < required) {
            boolean[] array = new boolean[required];
            for (int i = 0; i < bits.length; i++) {
                array[i] = bits[i];
            }
            bits = array;
        }
    }

    /**
     * Trim the trailing words.
     */
    private void trim() {
        int length = length();
        int required = capacity(length);
        if (bits.length != required) {
            boolean[] array = new boolean[required];
            for (int i = 0; i < required; i++) {
                array[i] = bits[i];
            }
            bits = array;
        }
    }

    /**
     * Set the bit.
     * 
     * @param index An index.
     */
    public void set(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("bitIndex < 0: " + index);
        }
        ensure(index);
        bits[index] = true;
    }

    /**
     * Set the bit.
     * 
     * @param index An index.
     * @param value A value.
     */
    public void set(int index, boolean value) {
        if (value) {
            set(index);
        } else {
            clear(index);
        }
    }

    /**
     * Set the bits.
     * 
     * @param from A start index.
     * @param to An end index.
     */
    public void set(int from, int to) {
        set(from, to, true);
    }

    /**
     * Set the bits.
     * 
     * @param from A start index.
     * @param to An end index.
     * @param value A value.
     */
    public void set(int from, int to, boolean value) {
        if (from < 0 || to < 0) {
            throw new IndexOutOfBoundsException("fromIndex or toIndex < 0");
        }
        if (from > to) {
            throw new IndexOutOfBoundsException("fromIndex > toIndex");
        }
        if (value) {
            ensure(to - 1);
            for (int i = from; i < to; i++) {
                bits[i] = true;
            }
        } else {
            for (int i = from; i < to && i < bits.length; i++) {
                bits[i] = false;
            }
        }
    }

    /**
     * Clear the bit.
     * 
     * @param index An index.
     */
    public void clear(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("bitIndex < 0: " + index);
        }
        if (index < bits.length) {
            bits[index] = false;
        }
    }

    /**
     * Clear the bits.
     * 
     * @param from A start index.
     * @param to An end index.
     */
    public void clear(int from, int to) {
        if (from < 0 || to < 0) {
            throw new IndexOutOfBoundsException("fromIndex or toIndex < 0");
        }
        if (from > to) {
            throw new IndexOutOfBoundsException("fromIndex > toIndex");
        }
        for (int i = from; i < to && i < bits.length; i++) {
            bits[i] = false;
        }
    }

    /**
     * Clear all bits.
     */
    public void clear() {
        for (int i = 0; i < bits.length; i++) {
            bits[i] = false;
        }
    }

    /**
     * Get the bit.
     * 
     * @param index An index.
     * @return A result.
     */
    public boolean get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("bitIndex < 0: " + index);
        }
        if (index >= bits.length) {
            return false;
        }
        return bits[index];
    }

    /**
     * Get the bits.
     * 
     * @param from A start index.
     * @param to An end index.
     * @return A bit set.
     */
    public BitSet get(int from, int to) {
        if (from < 0 || to < 0) {
            throw new IndexOutOfBoundsException("fromIndex or toIndex < 0");
        }
        if (from > to) {
            throw new IndexOutOfBoundsException("fromIndex > toIndex");
        }
        BitSet result = new BitSet(to - from);
        for (int i = from; i < to && i < bits.length; i++) {
            if (bits[i]) {
                result.set(i - from);
            }
        }
        return result;
    }

    /**
     * Flip the bit.
     * 
     * @param index An index.
     */
    public void flip(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("bitIndex < 0: " + index);
        }
        ensure(index);
        bits[index] = !bits[index];
    }

    /**
     * Flip the bits.
     * 
     * @param from A start index.
     * @param to An end index.
     */
    public void flip(int from, int to) {
        if (from < 0 || to < 0) {
            throw new IndexOutOfBoundsException("fromIndex or toIndex < 0");
        }
        if (from > to) {
            throw new IndexOutOfBoundsException("fromIndex > toIndex");
        }
        if (to > 0) {
            ensure(to - 1);
            for (int i = from; i < to; i++) {
                bits[i] = !bits[i];
            }
        }
    }

    /**
     * Get the cardinality.
     * 
     * @return A cardinality.
     */
    public int cardinality() {
        int count = 0;
        for (int i = 0; i < bits.length; i++) {
            if (bits[i]) {
                count++;
            }
        }
        return count;
    }

    /**
     * Get the length.
     * 
     * @return A length.
     */
    public int length() {
        int length = 0;
        for (int i = 0; i < bits.length; i++) {
            if (bits[i]) {
                length = i + 1;
            }
        }
        return length;
    }

    /**
     * Get the size.
     * 
     * @return A size.
     */
    public int size() {
        return bits.length;
    }

    /**
     * Check whether this bit set is empty.
     * 
     * @return A result.
     */
    public boolean isEmpty() {
        return length() == 0;
    }

    /**
     * Check whether this bit set intersects the other.
     * 
     * @param other A bit set.
     * @return A result.
     */
    public boolean intersects(BitSet other) {
        int length = bits.length < other.bits.length ? bits.length : other.bits.length;
        boolean found = false;
        for (int i = 0; i < length; i++) {
            if (bits[i] && other.bits[i]) {
                found = true;
            }
        }
        return found;
    }

    /**
     * Compute the intersection.
     * 
     * @param other A bit set.
     */
    public void and(BitSet other) {
        int length = bits.length < other.bits.length ? bits.length : other.bits.length;
        for (int i = 0; i < bits.length; i++) {
            bits[i] = i < length && bits[i] && other.bits[i];
        }
    }

    /**
     * Compute the union.
     * 
     * @param other A bit set.
     */
    public void or(BitSet other) {
        ensure(other.bits.length - 1);
        for (int i = 0; i < other.bits.length; i++) {
            bits[i] = bits[i] || other.bits[i];
        }
    }

    /**
     * Compute the symmetric difference.
     * 
     * @param other A bit set.
     */
    public void xor(BitSet other) {
        ensure(other.bits.length - 1);
        for (int i = 0; i < other.bits.length; i++) {
            bits[i] = bits[i] != other.bits[i];
        }
    }

    /**
     * Remove the bits of the other.
     * 
     * @param other A bit set.
     */
    public void andNot(BitSet other) {
        int length = bits.length < other.bits.length ? bits.length : other.bits.length;
        for (int i = 0; i < length; i++) {
            if (other.bits[i]) {
                bits[i] = false;
            }
        }
    }

    /**
     * Get the next set bit.
     * 
     * @param from A start index.
     * @return An index or -1.
     */
    public int nextSetBit(int from) {
        if (from < 0) {
            throw new IndexOutOfBoundsException("fromIndex < 0: " + from);
        }
        int result = -1;
        for (int i = from; i < bits.length; i++) {
            if (bits[i] && result < 0) {
                result = i;
            }
        }
        return result;
    }

    /**
     * Get the next clear bit.
     * 
     * @param from A start index.
     * @return An index.
     */
    public int nextClearBit(int from) {
        if (from < 0) {
            throw new IndexOutOfBoundsException("fromIndex < 0: " + from);
        }
        if (from >= bits.length) {
            return from;
        }
        int i = from;
        while (i < bits.length && bits[i]) {
            i++;
        }
        return i;
    }

    /**
     * Get the previous set bit.
     * 
     * @param from A start index.
     * @return An index or -1.
     */
    public int previousSetBit(int from) {
        if (from < 0) {
            return -1;
        }
        int result = -1;
        for (int i = from < bits.length ? from : bits.length - 1; i >= 0; i--) {
            if (bits[i] && result < 0) {
                result = i;
            }
        }
        return result;
    }

    /**
     * Get the previous clear bit.
     * 
     * @param from A start index.
     * @return An index or -1.
     */
    public int previousClearBit(int from) {
        if (from < 0) {
            return -1;
        }
        if (from >= bits.length) {
            return from;
        }
        int i = from;
        while (i >= 0 && bits[i]) {
            i--;
        }
        return i;
    }

    /**
     * Get the long values.
     * 
     * @return Words.
     */
    public long[] toLongArray() {
        int length = length();
        long[] words = new long[capacity(length) / 64];
        for (int i = 0; i < length; i++) {
            if (bits[i]) {
                words[(int) ((long) i / 64L)] |= 1L << (i % 64);
            }
        }
        return words;
    }

    /**
     * Get the byte values.
     * 
     * @return Bytes.
     */
    public byte[] toByteArray() {
        int length = (int) (((long) length() + 7L) / 8L);
        byte[] bytes = new byte[length];
        for (int i = 0; i < length * 8; i++) {
            if (bits[i]) {
                int index = (int) ((long) i / 8L);
                bytes[index] = (byte) (bytes[index] | (1 << (i % 8)));
            }
        }
        return bytes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BitSet clone() {
        boolean[] array = new boolean[bits.length];
        for (int i = 0; i < bits.length; i++) {
            array[i] = bits[i];
        }
        return new BitSet(array);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof BitSet)) {
            return false;
        }
        BitSet bitset = (BitSet) other;
        int length = bits.length > bitset.bits.length ? bits.length : bitset.bits.length;
        for (int i = 0; i < length; i++) {
            boolean left = i < bits.length && bits[i];
            boolean right = i < bitset.bits.length && bitset.bits[i];
            if (left != right) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        long hash = 1234L;
        for (int i = bits.length - 1; i >= 0; i--) {
            hash ^= (bits[i] ? 1L : 0L) * (i + 1);
        }
        return (int) ((hash >> 32) ^ hash);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{");
        for (int i = 0; i < bits.length; i++) {
            if (bits[i]) {
                if (builder.length() > 1) {
                    builder.append(", ");
                }
                builder.append(i);
            }
        }
        return builder.append('}').toString();
    }
}
