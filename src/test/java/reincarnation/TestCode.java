/*
 * Copyright (C) 2020 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @version 2018/10/09 21:53:26
 */
public interface TestCode<Self extends TestCode> {

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface Run extends TestCode {

        /**
         * Write testable code.
         */
        void run();
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface RunInt extends TestCode {

        /**
         * Write testable code.
         */
        void run(int value);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface Object<T> extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        T run();
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface ObjectParamBoolean extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        boolean run(Object param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface Int extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        int run();
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface IntArray extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        int[] run();
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface IntParam extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        int run(int param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface IntParamBoolean extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        boolean run(int param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface IntParamText extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        String run(int param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface Long extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        long run();
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface LongArray extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        long[] run();
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface LongParam extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        long run(long param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface LongParamBoolean extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        boolean run(long param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface LongParamInt extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        int run(long param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface LongParamDouble extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        double run(long param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface Float extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        float run();
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface FloatArray extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        float[] run();
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface FloatParam extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        float run(float param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface FloatParamBoolean extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        boolean run(float param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface FloatParamInt extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        int run(float param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface Double extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        double run();
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface DoubleArray extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        double[] run();
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface DoubleParam extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        double run(double param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface DoubleParamBoolean extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        boolean run(double param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface DoubleParamInt extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        int run(double param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface Byte extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        byte run();
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface ByteArray extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        byte[] run();
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface ByteParam extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        byte run(byte param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface ByteParamBoolean extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        boolean run(byte param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface Short extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        short run();
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface ShortArray extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        short[] run();
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface ShortParam extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        short run(short param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface ShortParamBoolean extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        boolean run(short param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface ShortParamInt extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        int run(short param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface Char extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        char run();
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface CharArray extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        char[] run();
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface CharParam extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        char run(char param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface CharParamBoolean extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        boolean run(char param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface CharParamInt extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        int run(char param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface Boolean extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        boolean run();
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface BooleanArray extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        boolean[] run();
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface BooleanParam extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        boolean run(boolean param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface BooleanParamInt extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        int run(boolean param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface Text extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        String run();
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface TextArray extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        String[] run();
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface TextParam extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        String run(String param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface TextParamBoolean extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        boolean run(String param);
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface TextParamInt extends TestCode {

        /**
         * Write testable code.
         * 
         * @return A result.
         */
        int run(String param);
    }

    /**
     * @version 2018/10/09 9:51:30
     */
    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Param {

        /**
         * <p>
         * Provide int values.
         * </p>
         * 
         * @return
         */
        int[] ints() default {};

        /**
         * <p>
         * Provide long values.
         * </p>
         * 
         * @return
         */
        long[] longs() default {};

        /**
         * <p>
         * Provide float values.
         * </p>
         * 
         * @return
         */
        float[] floats() default {};

        /**
         * <p>
         * Provide double values.
         * </p>
         * 
         * @return
         */
        double[] doubles() default {};

        /**
         * <p>
         * Provide byte values.
         * </p>
         * 
         * @return
         */
        byte[] bytes() default {};

        /**
         * <p>
         * Provide short values.
         * </p>
         * 
         * @return
         */
        short[] shorts() default {};

        /**
         * <p>
         * Provide char values.
         * </p>
         * 
         * @return
         */
        char[] chars() default {};

        /**
         * <p>
         * Provide {@link String} values.
         * </p>
         * 
         * @return
         */
        String[] strings() default {};

        /**
         * <p>
         * Provide range.
         * </p>
         * 
         * @return
         */
        int from() default 0;

        /**
         * <p>
         * Provide range.
         * </p>
         * 
         * @return
         */
        int to() default 0;
    }

}