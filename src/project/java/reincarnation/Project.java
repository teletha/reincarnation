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
        product("com.github.teletha", "Reincarnation", "0.6");

        require("org.ow2.asm", "asm");
        require("com.github.teletha", "sinobu");
        require("com.github.teletha", "psychopath");
        require("com.github.javaparser", "javaparser-core");
        require("org.benf", "cfr");

        // for jetbrains fernflower
        require("org.jetbrains", "annotations").atTest();

        require("com.github.teletha", "icymanipulator").atAnnotation();
        require("com.github.teletha", "bee").atTest();
        require("com.github.teletha", "antibug").atTest();
        require("com.github.teletha", "viewtify").atTest();

        versionControlSystem("https://github.com/teletha/reincarnation");
    }

    public static class Compile extends bee.task.Compile {
        {
            useECJ = true;
        }
    }
}