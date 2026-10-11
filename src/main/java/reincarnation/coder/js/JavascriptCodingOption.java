/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder.js;

import reincarnation.coder.CodingOption;

public class JavascriptCodingOption extends CodingOption {

    /** The write member class from top level class, this is mainly used for test. */
    public boolean writeMemberFromTopLevel;

    /**
     * Whether the output targets the TeaVM class library emulation (mangled method names, type
     * substitution and constructor initialization methods) instead of idiomatic JavaScript.
     */
    public boolean classlib;

    /** The reachable method keys ({@code owner#name#desc}) in the class library mode, or null. */
    public java.util.Set<String> methods;
}