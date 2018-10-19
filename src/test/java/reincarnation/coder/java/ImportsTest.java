/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.coder.java;

import org.junit.jupiter.api.Test;

import reincarnation.Reincarnation;
import reincarnation.coder.java.imports.AncestorHasSameCorePackageClassName;
import reincarnation.coder.java.imports.Integer;
import reincarnation.coder.java.imports.MemberHasSameCorePackageClassName;
import reincarnation.coder.java.imports.ParentHasSameCorePackageClassName;
import reincarnation.coder.java.imports.ParentMemberHasSameCorePackageClassName;

/**
 * @version 2018/10/19 14:24:19
 */
class ImportsTest {

    @Test
    void diffName() {
        Imports imports = new Imports();
        imports.add(Member1.class);
        imports.add(Member2.class);

        assert imports.contains(Member1.class) == true;
        assert imports.contains(Member2.class) == true;
    }

    @Test
    void sameName() {
        Imports imports = new Imports();
        imports.add(Member1.SameName.class);
        imports.add(Member2.SameName.class);

        assert imports.contains(Member1.SameName.class) == true;
        assert imports.contains(Member2.SameName.class) == false;
    }

    private static class Member1 {
        private static class SameName {
        }
    }

    private static class Member2 {
        private static class SameName {
        }
    }

    @Test
    void parentHasSameCorePackageClassName() {
        JavaCoder coder = new JavaCoder();

        Reincarnation reincarnation = Reincarnation.exhume(ParentHasSameCorePackageClassName.class);
        reincarnation.rebirth(coder);

        assert coder.imports.contains(Integer.class) == true;
        assert coder.imports.contains(java.lang.Integer.class) == false;
    }

    @Test
    void parentMemberHasSameCorePackageClassName() {
        JavaCoder coder = new JavaCoder();

        Reincarnation reincarnation = Reincarnation.exhume(ParentMemberHasSameCorePackageClassName.class);
        reincarnation.rebirth(coder);

        assert coder.imports.contains(MemberHasSameCorePackageClassName.Integer.class) == true;
        assert coder.imports.contains(java.lang.Integer.class) == false;
    }

    @Test
    void ancestorHasSameCorePackageClassName() {
        JavaCoder coder = new JavaCoder();

        Reincarnation reincarnation = Reincarnation.exhume(AncestorHasSameCorePackageClassName.class);
        reincarnation.rebirth(coder);

        assert coder.imports.contains(Integer.class) == true;
        assert coder.imports.contains(java.lang.Integer.class) == false;
    }
}
