/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.flow;

import java.io.IOException;

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
     * Maybe throw io exception.
     * 
     * @param value
     * @return
     */
    public static int io(int value) throws IOException {
        if (value % 4 == 0) {
            throw new IOException();
        }
        return value + 300;
    }
}
