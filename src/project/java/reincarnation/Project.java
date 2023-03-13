/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

public class Project extends bee.api.Project {

    {
        product("com.github.teletha", "Reincarnation", ref("version.txt"));

        require("org.ow2.asm", "asm");
        require("com.github.teletha", "sinobu");
        require("com.github.teletha", "psychopath");

        require("com.github.teletha", "icymanipulator").atAnnotation();
        require("com.github.teletha", "bee").atTest();
        require("com.github.teletha", "antibug").atTest();
        require("com.github.teletha", "viewtify").atTest();
        require("com.github.javaparser", "javaparser-core").atTest();

        // for jetbrains fernflower
        require("org.jetbrains", "annotations").atTest();

        versionControlSystem("https://github.com/teletha/reincarnation");

        describe("""
                Reincarnation aims to be a decompiler for all modern Java grammars.

                #### Java Language Features
                - [x] Declarations
                    - [x] package
                    - [x] import
                    - [ ] static import
                    - [x] class
                    - [x] interface
                    - [ ] annotation
                    - [ ] enum
                    - [ ] record
                - [x] Inheritance
                    - [x] extends
                    - [x] implements
                    - [ ] sealed and permits
                - [x] Modifiers
                    - [x] public
                    - [x] protected
                    - [x] private
                    - [x] static
                    - [x] final
                    - [x] synchronized
                    - [x] abstract
                    - [x] native
                    - [x] transient
                    - [x] volatile
                    - [x] strictfp
                - [x] Static member type declaration
                - [x] Inner (non-static member, local, anonymous) class declaration
                - [x] Class initializer, instance initializer
                - [x] Field declaration, method declaration
                - [x] Local variable declaration
                - [x] Class variable initializer, instance variable initializer
                - [x] Statement
                    - [x] if-else
                    - [x] for
                    - [x] enhanced for by iterator
                    - [ ] enhanced for by array
                    - [x] while
                    - [x] do-while
                    - [x] try-catch-finally (including multiple catches)
                    - [ ] try-with-resources
                    - [x] throw
                    - [x] return
                    - [x] break
                    - [x] continue
                    - [ ] switch
                    - [ ] switch by enum
                    - [ ] switch by String
                    - [ ] switch with pattern matching
                    - [ ] synchronized
                    - [x] assert
                - [x] Primitive types (boolean, char, byte, short, int, long, float, double)
                - [x] Assignment operators
                    - [x] =
                    - [x] +=
                    - [x] -=
                    - [x] *=
                    - [x] /=
                    - [x] &=
                    - [x] |=
                    - [x] ^=
                    - [x] <<=
                    - [x] >>=
                    - [x] >>>=
                - [x] Conditional operators
                    - [x] ? ... : ...
                    - [x] &&
                    - [x] ||
                - [x] Logical operators and Integer bitwise operators
                    - [x] &
                    - [x] ^
                    - [x] |
                    - [x] !
                    - [x] ~
                - [x] Numeric operators
                    - [x] +
                    - [x] -
                    - [x] *
                    - [x] /
                    - [x] %
                    - [x] <<
                    - [x] >>
                    - [x] >>>
                    - [x] ++
                    - [x] --
                - [x] String concatenation operator +
                - [x] Field access (like System.out)
                - [x] Superclass member access (super.meth(), super.field)
                - [x] this (reference to current instance)
                - [x] Alternate constructor invocation (this(a, b, c);)
                - [x] Superclass constructor invocation (super(a, b, c);)
                - [x] Method invocation (System.out.println("Hello"))
                - [x] Class instance creation (new Foo())
                - [x] Primitive array creation (new int[10][5][])
                - [x] Class or interface array creation (new Foo[10][5][])
                - [x] Array access (args[0])
                - [x] Local variable access
                - [x] Local variable type inference (a.k.a. "var")
                - [x] Literal
                    - [x] integer
                    - [x] floating-point
                    - [x] boolean
                    - [x] character
                    - [x] string
                    - [x] null
                    - [x] class (e.g. int.class, String.class)
                - [x] Cast
                    - [x] instanceof
                    - [x] instanceof with pattern matching
                    - [x] wideining
                    - [x] narrowing
                    - [x] auto-boxing and auto-unboxing
                - [x] Array initializer (String[] a = { "x", "y", "z" })
                - [x] Local variable information
                - [x] Generics
                    - [x] Type variable (e.g. <S, T, R>)
                    - [x] Parameterized type (e.g. Foo<X>)
                    - [x] Wildcard type (e.g. ? extends Closeable)
                    - [ ] Generic array type (e.g. T[])
                - [ ] Annotation
                    - [ ] Type
                    - [ ] Constructor
                    - [ ] Method
                    - [ ] Field
                    - [ ] Parameter
                    - [ ] Local variable
                - [ ] Variable arity method (a.k.a. "varargs")
                - [ ] Type interference for generic instance creation (a.k.a. the "diamond operator") (JLS11 15.9.1)
                - [x] Lambda expression
                - [x] Constructor reference
                - [x] Method reference
                - [x] Default method
                - [x] Static interface method
                - [x] Private interface method
                - [ ] Text block
                """);
    }

    public static class Compile extends bee.task.Compile {
        {
            useECJ = true;
        }
    }
}