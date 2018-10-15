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

/**
 * @version 2018/10/16 0:00:39
 */
class JavaReincarnationNamingTest {

    Object anonymous = new Object() {
    };

    class Member {
    }

    @Test
    void simpleName() {
        class Local {
        }

        assert JavaReincarnation.simpleName(String.class).equals("String");
        assert JavaReincarnation.simpleName(anonymous.getClass()).equals("JavaReincarnationNamingTest$1");
        assert JavaReincarnation.simpleName(Local.class).equals("Local");
        assert JavaReincarnation.simpleName(Member.class).equals("Member");
    }

    @Test
    void name() {
        class Local {
        }
        assert JavaReincarnation.name(String.class).equals("java.lang.String");
        assert JavaReincarnation.name(anonymous.getClass())
                .equals("reincarnation.JavaReincarnationNamingTest$JavaReincarnationNamingTest$1");
        assert JavaReincarnation.name(Local.class).equals("reincarnation.JavaReincarnationNamingTest$JavaReincarnationNamingTest$2Local");
        assert JavaReincarnation.name(Member.class).equals("reincarnation.JavaReincarnationNamingTest$Member");
    }
}
