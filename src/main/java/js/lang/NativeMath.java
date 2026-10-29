/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package js.lang;

/**
 * The JavaScript {@code Math}.
 * <p>
 * The Java implementation mimics the JavaScript behavior so that the same test can run on the JVM.
 * </p>
 */
@Native
public final class NativeMath {

    /**
     * Hide constructor.
     */
    private NativeMath() {
    }

    /**
     * Compute the absolute value.
     * 
     * @param value A value.
     * @return A result.
     */
    @Native("Math.abs($0)")
    public static double abs(double value) {
        return Math.abs(value);
    }

    /**
     * Compute the smallest integer greater than or equal to the value.
     * 
     * @param value A value.
     * @return A result.
     */
    @Native("Math.ceil($0)")
    public static double ceil(double value) {
        return Math.ceil(value);
    }

    /**
     * Compute the largest integer less than or equal to the value.
     * 
     * @param value A value.
     * @return A result.
     */
    @Native("Math.floor($0)")
    public static double floor(double value) {
        return Math.floor(value);
    }

    /**
     * Truncate the fractional part.
     * 
     * @param value A value.
     * @return A result.
     */
    @Native("Math.trunc($0)")
    public static double trunc(double value) {
        return value < 0 ? Math.ceil(value) : Math.floor(value);
    }

    /**
     * Round the value.
     * 
     * @param value A value.
     * @return A result.
     */
    @Native("Math.round($0)")
    public static double round(double value) {
        double rounded = Math.floor(value + 0.5);
        return rounded == 0 && value < 0 ? -0.0 : rounded;
    }

    /**
     * Compute the square root.
     * 
     * @param value A value.
     * @return A result.
     */
    @Native("Math.sqrt($0)")
    public static double sqrt(double value) {
        return Math.sqrt(value);
    }

    /**
     * Compute the cube root.
     * 
     * @param value A value.
     * @return A result.
     */
    @Native("Math.cbrt($0)")
    public static double cbrt(double value) {
        return Math.cbrt(value);
    }

    /**
     * Compute the power.
     * 
     * @param value A value.
     * @param exponent An exponent.
     * @return A result.
     */
    @Native("Math.pow($0, $1)")
    public static double pow(double value, double exponent) {
        return Math.pow(value, exponent);
    }

    /**
     * Compute the minimum.
     * 
     * @param left A value.
     * @param right A value.
     * @return A result.
     */
    @Native("Math.min($0, $1)")
    public static double min(double left, double right) {
        return Math.min(left, right);
    }

    /**
     * Compute the maximum.
     * 
     * @param left A value.
     * @param right A value.
     * @return A result.
     */
    @Native("Math.max($0, $1)")
    public static double max(double left, double right) {
        return Math.max(left, right);
    }

    /**
     * Generate a random number.
     * 
     * @return A random number.
     */
    @Native("Math.random()")
    public static double random() {
        return Math.random();
    }

    /**
     * Compute the sign.
     * 
     * @param value A value.
     * @return A result.
     */
    @Native("Math.sign($0)")
    public static double sign(double value) {
        return value > 0 ? 1 : value < 0 ? -1 : value;
    }

    /**
     * Check whether the value is NaN.
     * 
     * @param value A value.
     * @return A result.
     */
    @Native("Number.isNaN($0)")
    public static boolean isNaN(double value) {
        return Double.isNaN(value);
    }

    /**
     * Check whether the value is finite.
     * 
     * @param value A value.
     * @return A result.
     */
    @Native("Number.isFinite($0)")
    public static boolean isFinite(double value) {
        return Double.isFinite(value);
    }
}
