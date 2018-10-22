/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
public class Project extends bee.api.Project {

    {
        product("com.github.teletha", "Reincarnation", "0.6");

        require("org.ow2.asm", "asm", "7.0-beta");
        require("com.github.javaparser", "javaparser-core", "3.6.25");
        require("com.github.teletha", "antibug", "0.6").atTest();
        require("com.github.teletha", "Marionette", "0.2").atTest();
        require("com.github.teletha", "filer", "0.5").atTest();
    }
}
