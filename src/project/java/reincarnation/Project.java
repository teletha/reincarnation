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
        require("org.codehaus.janino", "janino");

        // for jetbrains fernflower
        require("org.jetbrains", "annotations").atTest();

        versionControlSystem("https://github.com/teletha/reincarnation");

        describe("""
                Reincarnation aims to be a decompiler for all modern Java grammars.

                #### Java 1.4 language features
                - [x] package declaration, import declaration
                - [x] class declaration
                - [x] interface declaration
                - [x] Inheritance (extends and implements)
                - [x] Static member type declaration
                - [x] Inner (non-static member, local, anonymous) class declaration
                - [x] Class initializer, instance initializer
                - [x] Field declaration, method declaration
                - [x] Local variable declaration
                - [x] Class variable initializer, instance variable initializer
                - [x] Statment
                    - [x] if-else
                    - [x] for
                    - [x] while
                    - [x] do-while
                    - [x] try-catch-finally
                    - [x] throw
                    - [x] return
                    - [x] break
                    - [x] continu
                    - [ ] switch
                    - [ ] synchronized
                    - [x] assert
                - [x] Primitive types (boolean, char, byte, short, int, long, float, double)
                - [x] Assignment operator =
                - [x] Compound assignment operators +=, -=, *=, /=, &=, |=, ^=, %=, <<=, >>=, >>>=
                - [x] Conditional operators ?-:, &&, ||
                - [x] Boolean logical operators &, ^, |
                - [x] Integer bitwise operators &, ^, |
                - [x] Numeric operators *, /, %, +, -, <<, >>, >>>
                - [x] String concatenation operator +
                - [x] Operators ++ and --
                - [x] Type comparison operator instanceof
                - [x] Unary operators +, -, ~, !
                - [x] Parenthesized expression
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
                - [x] Integer literal (decimal, hex and octal)
                - [x] Floating-point literal (decimal)
                - [x] Boolean, character, string literal
                - [x] null literal
                - [x] Numeric conversions: Unary, binary, widening, narrowing
                - [x] Reference conversions: Widening, Narrowing
                - [x] Assignment conversion
                - [x] String conversion (for string concatenation)
                - [x] Cast
                - [x] Constant expression
                - [x] throws clause
                - [x] Array initializer (String[] a = { "x", "y", "z" })
                - [x] Primitive class literals (int.class)
                - [x] Non-primitive class literals (String.class)
                - [x] Local variable information
                - [x] Modifiers (public, protected, package-private, private, final)

                #### Java 5 language features
                - [x] Declaration of parameterized types
                - [x] Type arguments (e.g. List<String>)
                - [x] Enhanced for statement
                - [x] Autoboxing and unboxing
                - [ ] enum declaration
                - [ ] enum switch statement
                - [ ] Annotation type declaration (including method default values)
                - [ ] Variable arity methods (a.k.a. "varargs")
                - [ ] Static imports (single and on-demand; fields, types and methods)
                - [ ] Annotations
                - [x] Covariant return types
                - [ ] Hexadecimal floating point literals

                ### Java 7 language features
                - [ ] Binary integer literal (JLS7 3.10.1)
                - [ ] Underscores in numeric literals (JLS7 3.10.1)
                - [ ] String switch statement (JLS7 14.11)
                - [ ] try-with-resources statement (JLS7 14.20.3)
                - [ ] catching and rethrowing multiple exception types
                - [ ] Type interference for generic instance creation (a.k.a. the "diamond operator") (JLS11 15.9.1)

                ### Java 8 language features
                - [ ] Lambda expressions
                - [ ] Method references
                - [ ] Default methods
                - [ ] Static interface methods

                ### Java 9 language features
                - [ ] Private interface method
                - [ ] Enhanced try-with-resources statement, allowing VariableAccesses as resources (JLS9 14.20.3)

                #### Unsupported
                - annotation
                - generics
                - lambda
                - method reference
                - record
                - record with pattern matching
                - enum
                - sealed class
                - switch with string
                - switch with pattern matching
                - try-with-resources
                - text block
                """);
    }

    public static class Compile extends bee.task.Compile {
        {
            useECJ = true;
        }
    }
}