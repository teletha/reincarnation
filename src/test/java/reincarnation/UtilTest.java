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

import org.junit.jupiter.api.Test;

import com.github.javaparser.JavaParser;

/**
 * @version 2018/10/05 19:28:00
 */
class UtilTest {

    @Test
    void loadJavaParserType() {
        assert Util.load(JavaParser.parseType(String.class.getName())) == String.class;
    }
}
