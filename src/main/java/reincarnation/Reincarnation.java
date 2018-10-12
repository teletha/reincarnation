/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

/**
 * @version 2018/10/10 18:01:27
 */
public class Reincarnation {

    /**
     * Decompile the target {@link Class}.
     * 
     * @param clazz
     */
    public static final JavaSourceCode exhume(Class clazz) {
        return new JavaSourceCode(clazz);
    }
}
