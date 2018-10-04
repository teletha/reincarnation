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
 * @version 2018/10/02 19:57:24
 */
public interface Code {

    /**
     * Estimate the fully qualified class name.
     * 
     * @return
     */
    default String getName() {
        return getClass().getName();
    }

    /**
     * Estimate the simple class name.
     */
    default String getSimpleName() {
        Class clazz = getClass();

        if (clazz.isAnonymousClass()) {
            String name = clazz.getName();
            int index = name.lastIndexOf(".");

            if (index == -1) {
                return name;
            } else {
                return name.substring(index + 1);
            }
        } else {
            return clazz.getSimpleName();
        }
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface Int extends Code {

        /**
         * Write testable code.
         * 
         * @return A int result.
         */
        int run();
    }

    /**
     * @version 2018/04/04 16:29:00
     */
    public interface IntParam extends Code {

        /**
         * Write testable code.
         * 
         * @param A int parameter.
         * @return A int result.
         */
        int run(int param);
    }
}
