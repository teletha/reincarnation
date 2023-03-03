/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder.java;

import org.junit.jupiter.api.Test;

class JavaCoderNamingTest {

    Object anonymous = new Object() {
    };

    class Member {
    }

    @Test
    void simpleName() {
        class Local {
        }

        assert JavaCoder.computeSimpleName(String.class).equals("String");
        assert JavaCoder.computeSimpleName(anonymous.getClass()).equals("JavaCoderNamingTest$1");
        assert JavaCoder.computeSimpleName(Local.class).equals("Local");
        assert JavaCoder.computeSimpleName(Member.class).equals("Member");
    }

    @Test
    void name() {
        class Local {
        }
        assert JavaCoder.computeName(String.class).equals("java.lang.String");
        assert JavaCoder.computeName(anonymous.getClass()).equals("reincarnation.coder.java.JavaCoderNamingTest$JavaCoderNamingTest$1");
        assert JavaCoder.computeName(Local.class).equals("reincarnation.coder.java.JavaCoderNamingTest$JavaCoderNamingTest$2Local");
        assert JavaCoder.computeName(Member.class).equals("reincarnation.coder.java.JavaCoderNamingTest$Member");
    }
}