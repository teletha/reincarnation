/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

public class Project extends bee.api.Project {

    {
        product("com.github.teletha", "Reincarnation", ref("version.txt"));

        require("org.ow2.asm", "asm");
        require("com.github.teletha", "sinobu");
        require("com.github.teletha", "psychopath");

        require("com.github.teletha", "icymanipulator").atAnnotation();
        require("com.github.teletha", "bee").atTest();
        require("com.github.teletha", "antibug").atTest();
        require("com.github.teletha", "viewtify").atTest();
        require("com.github.javaparser", "javaparser-core").atTest();

        // for jetbrains fernflower
        require("org.jetbrains", "annotations").atTest();

        versionControlSystem("https://github.com/teletha/reincarnation");

        describe("""
                Reincarnation aims to be a decompiler for all modern Java grammars.

                #### Supported
                - basic grammar (keyword, operator, variable declaration, cast, type definition, field and method definition)
                - control syntax (if, switch, while, do-while, for, break, continue, try-catch-finally)
                - auto boxing and unboxing
                - assertion (with message)
                - varargs
                - enhanced for-loop
                - multi catches
                - var
                - string literal
                - local class
                - instanceof (with pattern matching)

                #### Unsupported
                - annotation
                - generics
                - lambda
                - method reference
                - record
                - record with pattern matching
                - enum
                - sealed class
                - switch with string
                - switch with pattern matching
                - try-with-resources
                - text block
                """);
    }

    public static class Compile extends bee.task.Compile {
        {
            useECJ = true;
        }
    }
}