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
import js.lang.NativeMath;

/**
 * The Java implementation of {@link java.util.Random} which is transpiled into JavaScript.
 */
public class Random implements Serializable {

    /** The multiplier. */
    private static final long multiplier = 0x5DEECE66DL;

    /** The addend. */
    private static final long addend = 0xBL;

    /** The mask. */
    private static final long mask = (1L << 48) - 1L;

    /** The seed. */
    private long seed;

    /** The next Gaussian value. */
    private double nextNextGaussian;

    /** Whether the next Gaussian value exists. */
    private boolean haveNextNextGaussian;

    /**
     * Create the random generator.
     */
    public Random() {
        setSeed(java.lang.System.nanoTime());
    }

    /**
     * Create the random generator.
     * 
     * @param seed A seed.
     */
    public Random(long seed) {
        setSeed(seed);
    }

    /**
     * Set the seed.
     * 
     * @param seed A seed.
     */
    public synchronized void setSeed(long seed) {
        this.seed = (seed ^ multiplier) & mask;
        this.haveNextNextGaussian = false;
    }

    /**
     * Generate the next value.
     * 
     * @param bits Bits.
     * @return A value.
     */
    protected int next(int bits) {
        seed = (seed * multiplier + addend) & mask;
        return (int) (seed >>> (48 - bits));
    }

    /**
     * Generate the next int.
     * 
     * @return A value.
     */
    public int nextInt() {
        return next(32);
    }

    /**
     * Generate the next int.
     * 
     * @param bound A bound.
     * @return A value.
     */
    public int nextInt(int bound) {
        if (bound <= 0) {
            throw new IllegalArgumentException("bound must be positive");
        }
        int r = next(31);
        int m = bound - 1;
        if ((bound & m) == 0) {
            r = (int) ((bound * (long) r) >> 31);
        } else {
            for (int u = r; u - (r = u % bound) + m < 0; u = next(31)) {
                // retry
            }
        }
        return r;
    }

    /**
     * Generate the next long.
     * 
     * @return A value.
     */
    public long nextLong() {
        return ((long) next(32) << 32) + next(32);
    }

    /**
     * Generate the next boolean.
     * 
     * @return A value.
     */
    public boolean nextBoolean() {
        return next(1) != 0;
    }

    /**
     * Generate the next float.
     * 
     * @return A value.
     */
    public float nextFloat() {
        return NativeMath.fround(next(24) / ((float) (1 << 24)));
    }

    /**
     * Generate the next double.
     * 
     * @return A value.
     */
    public double nextDouble() {
        return (((long) next(26) << 27) + next(27)) / 9007199254740992.0;
    }

    /**
     * Generate the next bytes.
     * 
     * @param bytes Bytes.
     */
    public void nextBytes(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) next(8);
        }
    }

    /**
     * Generate the next Gaussian.
     * 
     * @return A value.
     */
    public synchronized double nextGaussian() {
        if (haveNextNextGaussian) {
            haveNextNextGaussian = false;
            return nextNextGaussian;
        }
        double v1 = 0;
        double v2 = 0;
        double s = 0;
        do {
            v1 = 2 * nextDouble() - 1;
            v2 = 2 * nextDouble() - 1;
            s = v1 * v1 + v2 * v2;
        } while (s >= 1 || s == 0);
        double multiplier = StrictMath.sqrt(-2 * StrictMath.log(s) / s);
        nextNextGaussian = v2 * multiplier;
        haveNextNextGaussian = true;
        return v1 * multiplier;
    }
}
