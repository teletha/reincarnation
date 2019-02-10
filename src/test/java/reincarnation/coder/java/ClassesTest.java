/*
 * Copyright (C) 2019 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder.java;

import org.junit.jupiter.api.Test;

/**
 * @version 2018/10/21 17:11:54
 */
class ClassesTest {

    @Test
    void enclosingRoot() {
        assert Classes.enclosingRoot(Character.class) == Character.class;
        assert Classes.enclosingRoot(Character.Subset.class) == Character.class;
    }

    @Test
    void enclosings() {
        assert Classes.enclosings(Character.class).isEmpty();
        assert Classes.enclosings(Character.Subset.class).size() == 1;
    }
}
