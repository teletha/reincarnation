/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.flow;

public class MaybeThrow {

    /**
     * Maybe throw error.
     * 
     * @param value
     * @return
     */
    public static int error(int value) {
        if (value % 2 == 0) {
            throw new Error();
        }
        return value + 100;
    }

    /**
     * Maybe throw exception.
     * 
     * @param value
     * @return
     */
    public static int exception(int value) throws Exception {
        if (value % 3 == 0) {
            throw new Exception();
        }
        return value + 200;
    }

    /**
     * Maybe throw runtime exception.
     * 
     * @param value
     * @return
     */
    public static int runtime(int value) {
        if (value % 4 == 0) {
            throw new RuntimeException();
        }
        return value + 300;
    }
}