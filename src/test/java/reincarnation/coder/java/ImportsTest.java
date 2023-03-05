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

import java.awt.List;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

import psychopath.Directory;
import reincarnation.coder.java.imports.AncestorMemberIsInteger;
import reincarnation.coder.java.imports.InterfaceMemberIsInteger;
import reincarnation.coder.java.imports.ParentIsInteger;
import reincarnation.coder.java.imports.ParentMemberIsInteger;
import reincarnation.coder.java.imports.external.ClassHasIntegerMember;
import reincarnation.coder.java.imports.external.InterfaceHasIntegerMember;

class ImportsTest {

    @Test
    void name() {
        Imports imports = new Imports();
        assert imports.name(ArrayList.class).equals("ArrayList");
        assert imports.name(List.class).equals("List");
    }

    @Test
    void nameSame() {
        Imports imports = new Imports();
        assert imports.name(Date.class).equals("Date");
        assert imports.name(java.sql.Date.class).equals("java.sql.Date");
    }

    @Test
    void namePrimitive() {
        Imports imports = new Imports();
        assert imports.name(int.class).equals("int");
        assert imports.name(float.class).equals("float");
    }

    @Test
    void nameCore() {
        Imports imports = new Imports();
        assert imports.name(String.class).equals("String");
        assert imports.name(Error.class).equals("Error");
    }

    @Test
    void nameArray() {
        Imports imports = new Imports();
        assert imports.name(File[].class).equals("File[]");
        assert imports.name(Directory[].class).equals("Directory[]");
    }

    @Test
    void nameSameArray() {
        Imports imports = new Imports();
        assert imports.name(File[].class).equals("File[]");
        assert imports.name(psychopath.File[].class).equals("psychopath.File[]");
    }

    @Test
    void namePrimitiveArray() {
        Imports imports = new Imports();
        assert imports.name(int[].class).equals("int[]");
        assert imports.name(double[].class).equals("double[]");
    }

    @Test
    void nameMultiDimensionArray() {
        Imports imports = new Imports();
        assert imports.name(File[][].class).equals("File[][]");
        assert imports.name(File[][][][].class).equals("File[][][][]");
    }

    @Test
    void nameMultiDimensionSameArray() {
        Imports imports = new Imports();
        assert imports.name(File[][].class).equals("File[][]");
        assert imports.name(psychopath.File[][].class).equals("psychopath.File[][]");
    }

    @Test
    void coreClass() {
        Imports imports = new Imports();
        imports.setBase(ImportsTest.class);

        assert imports.name(java.lang.Integer.class).equals("Integer");
    }

    @Test
    void coreClassName() {
        Imports imports = new Imports();
        imports.setBase(reincarnation.coder.java.imports.external.Integer.class);

        assert imports.name(reincarnation.coder.java.imports.external.Integer.class).equals("Integer");
        assert imports.name(java.lang.Integer.class).equals("java.lang.Integer");
    }

    @Test
    void parentIsCoreClassName() {
        Imports imports = new Imports();
        imports.setBase(ParentIsInteger.class);

        assert imports.name(reincarnation.coder.java.imports.external.Integer.class).equals("Integer");
        assert imports.name(java.lang.Integer.class).equals("java.lang.Integer");

        imports = new Imports();
        imports.setBase(ParentIsInteger.class);

        assert imports.name(java.lang.Integer.class).equals("Integer");
        assert imports.name(reincarnation.coder.java.imports.external.Integer.class)
                .equals("reincarnation.coder.java.imports.external.Integer");
    }

    @Test
    void memberIsCoreClassName() {
        Imports imports = new Imports();
        imports.setBase(ClassHasIntegerMember.class);

        assert imports.name(ClassHasIntegerMember.Integer.class).equals("Integer");
        assert imports.name(java.lang.Integer.class).equals("java.lang.Integer");
    }

    @Test
    void parentMemberIsCoreClassName() {
        Imports imports = new Imports();
        imports.setBase(ParentMemberIsInteger.class);

        assert imports.name(ClassHasIntegerMember.Integer.class).equals("Integer");
        assert imports.name(java.lang.Integer.class).equals("java.lang.Integer");
    }

    @Test
    void ancestorMemberIsCoreClassName() {
        Imports imports = new Imports();
        imports.setBase(AncestorMemberIsInteger.class);

        assert imports.name(ClassHasIntegerMember.Integer.class).equals("Integer");
        assert imports.name(java.lang.Integer.class).equals("java.lang.Integer");
    }

    @Test
    void interfaceMemberIsCoreClassName() {
        Imports imports = new Imports();
        imports.setBase(InterfaceMemberIsInteger.class);

        assert imports.name(InterfaceHasIntegerMember.Integer.class).equals("Integer");
        assert imports.name(java.lang.Integer.class).equals("java.lang.Integer");
    }
}