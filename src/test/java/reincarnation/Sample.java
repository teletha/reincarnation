/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import java.lang.invoke.MethodHandle;

import reincarnation.coder.java.JavaCodingOption;

public class Sample {

    public static void main(String[] args) {
        System.out.println(Reincarnation.rebirth(MethodHandle.class, new JavaCodingOption()));
    }
}
