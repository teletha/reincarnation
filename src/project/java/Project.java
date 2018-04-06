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

        require("org.ow2.asm", "asm", "6.1.1");
        require("com.github.javaparser", "javaparser-core", "3.5.20");
        require("com.github.teletha", "antibug", "0.6").atTest();
        require("com.github.teletha", "Marionette", "0.1").atTest();
    }
}
