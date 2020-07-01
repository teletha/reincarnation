/*
 * Copyright (C) 2020 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder.java;

import org.junit.jupiter.api.Test;

import reincarnation.coder.java.imports.AncestorMemberIsInteger;
import reincarnation.coder.java.imports.InterfaceMemberIsInteger;
import reincarnation.coder.java.imports.ParentIsInteger;
import reincarnation.coder.java.imports.ParentMemberIsInteger;
import reincarnation.coder.java.imports.external.ClassHasIntegerMember;
import reincarnation.coder.java.imports.external.InterfaceHasIntegerMember;

/**
 * @version 2018/10/20 20:29:19
 */
class ImportsTest {

    private static final Class coreInteger = java.lang.Integer.class;

    private static final Class externalInteger = reincarnation.coder.java.imports.external.Integer.class;

    @Test
    void diffName() {
        Imports imports = new Imports();
        imports.add(Member1.class);
        imports.add(Member2.class);

        assert imports.name(Member1.class).equals(Member1.class.getSimpleName());
        assert imports.name(Member2.class).equals(Member2.class.getSimpleName());
    }

    @Test
    void sameName() {
        Imports imports = new Imports();
        imports.add(Member1.SameName.class);
        imports.add(Member2.SameName.class);

        assert imports.name(Member1.SameName.class).equals(Member1.SameName.class.getSimpleName());
        assert imports.name(Member2.SameName.class).equals(Member2.SameName.class.getCanonicalName());
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
    void coreClass() {
        Imports imports = new Imports();
        imports.setBase(ImportsTest.class);

        assert imports.name(coreInteger).equals("Integer");
    }

    @Test
    void coreClassName() {
        Imports imports = new Imports();
        imports.setBase(externalInteger);

        assert imports.name(externalInteger).equals("Integer");
        assert imports.name(coreInteger).equals("java.lang.Integer");
    }

    @Test
    void parentIsCoreClassName() {
        Imports imports = new Imports();
        imports.setBase(ParentIsInteger.class);

        assert imports.name(externalInteger).equals(externalInteger.getCanonicalName());
        assert imports.name(coreInteger).equals("Integer");
    }

    @Test
    void memberIsCoreClassName() {
        Imports imports = new Imports();
        imports.setBase(ClassHasIntegerMember.class);

        assert imports.name(ClassHasIntegerMember.Integer.class).equals("Integer");
        assert imports.name(coreInteger).equals("java.lang.Integer");
    }

    @Test
    void parentMemberIsCoreClassName() {
        Imports imports = new Imports();
        imports.setBase(ParentMemberIsInteger.class);

        assert imports.name(ClassHasIntegerMember.Integer.class).equals("Integer");
        assert imports.name(coreInteger).equals("java.lang.Integer");
    }

    @Test
    void ancestorMemberIsCoreClassName() {
        Imports imports = new Imports();
        imports.setBase(AncestorMemberIsInteger.class);

        assert imports.name(ClassHasIntegerMember.Integer.class).equals("Integer");
        assert imports.name(coreInteger).equals("java.lang.Integer");
    }

    @Test
    void interfaceMemberIsCoreClassName() {
        Imports imports = new Imports();
        imports.setBase(InterfaceMemberIsInteger.class);

        assert imports.name(InterfaceHasIntegerMember.Integer.class).equals("Integer");
        assert imports.name(coreInteger).equals("java.lang.Integer");
    }
}