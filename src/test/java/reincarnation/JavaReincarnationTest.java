/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import org.junit.jupiter.api.Test;

import antibug.powerassert.PowerAssertOff;

/**
 * @version 2018/10/16 13:06:56
 */
class JavaReincarnationTest {

    @PowerAssertOff
    @Test
    void testName() {
        JavaReincarnation reincarnation = new JavaReincarnation();
        String rebirth1 = reincarnation.rebirth(Main.class);
        String rebirth2 = reincarnation.rebirth(Main.class);

        System.out.println(rebirth1);

        System.out.println(rebirth2);

        assert rebirth1.equals(rebirth2);

    }

    static class Main {
        public int vvv() {
            return 1;
        }
    }
}
