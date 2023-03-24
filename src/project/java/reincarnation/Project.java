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
                Reincarnation is designed as a decompiler intended to support modern Java syntax.
                Eventually, it should be able to understand all of the syntax and grammars listed below.
                However, some information that is completely removed by the compiler and cannot be accessed from the bytecode cannot be restored,
                but we believe that the restored code will not interfere with its operation.

                The output method of the analyzed code is not limited to Java, but is designed to be convertible to a variety of languages.

                #### Java Language Features
                - [x] Declarations
                    - [x] package
                    - [x] import
                    - [ ] static import
                    - [x] class
                    - [x] interface
                        - [x] Default method
                        - [x] Static method
                        - [x] Private method
                    - [x] annotation
                    - [x] enum
                    - [x] record
                - [x] Inheritance
                    - [x] extends
                    - [x] implements
                    - [x] sealed and permits
                - [x] Modifiers (public protected private static abstract final synchronized native  transient volatile strictfp)
                - [x] Inner class declaration
                    - [x] Member class (static or non-static)
                    - [x] Local class
                    - [ ] Anonymous class
                - [x] Class initializer, instance initializer
                - [x] Constructor, field and method
                - [x] Statement
                    - [x] if-else
                    - [x] for
                        - [x] enhanced for by array
                        - [x] enhanced for by Iterable
                    - [x] while
                    - [x] do-while
                    - [x] try-catch-finally
                        - [x] multi catches
                    - [ ] try-with-resources
                    - [x] throw
                    - [x] return
                    - [x] break
                    - [x] continue
                    - [x] switch
                        - [x] fall through
                        - [x] by char
                        - [x] by enum
                        - [x] by String
                        - [ ] pattern matching
                        - [ ] expression
                            - [ ] yield
                            - [ ] arrow syntax
                            - [ ] multiple comma-separated labels
                    - [ ] synchronized
                    - [x] assert
                    - [x] labeled statement
                - [x] Types, Values, and Variables
                    - [x] Primitive Types (boolean, char, byte, short, int, long, float, double)
                    - [x] Reference Types (class, interface, array)
                - [x] Operators
                    - [x] Unary operators ( +  -  ++  --  ~  ! )
                    - [x] Multiplicative operators ( *  /  % )
                    - [x] Additive operators ( +  - )
                    - [x] Shift operators ( <<  >>  >>> )
                    - [x] Relational operators ( <  >  <=  >=  instanceof )
                    - [x] Equality operators ( ==  != )
                    - [x] Logical operators and Integer bitwise operators ( &  ^  !  |  ~ )
                    - [x] Conditional operator ( ||  &&  ? ... : ... )
                    - [x] Assignment operators ( =  *=  /=  %=  +=  -=  <<=  >>=  >>>=  &=  ^=  |= )
                    - [x] String concatenation operator ( + )
                - [x] Superclass member access (e.g. super.meth(), super.field)
                - [x] this (reference to current instance)
                - [x] Constructor invocation (e.g. this(a, b),  super(a)
                - [x] Method invocation (e.g. System.out.println("Hello"))
                - [x] Class Instance Creation Expression (e.g. new Foo())
                - [x] Array Creation and Access Expression (e.g. new int[],  new Object[10][5][],  args[1])
                - [x] Array initializer (e.g. String[] array = { "x", "y", "z" })
                - [x] Field Access Expression (e.g. System.out)
                - [x] Local variable access
                - [x] Local variable type inference (a.k.a. "var")
                - [x] Literal
                    - [x] integer
                    - [x] floating-point
                    - [x] boolean
                    - [x] character
                    - [x] string
                        - [x] text block
                        - [x] escape sequence
                    - [x] null
                    - [x] class (e.g. int.class, String.class)
                - [x] Cast
                    - [x] instanceof
                    - [x] instanceof with pattern matching
                    - [x] wideining
                    - [x] narrowing
                    - [x] auto-boxing and auto-unboxing
                - [x] Generics
                    - [x] Type variable (e.g. <S, T, R>)
                    - [x] Parameterized type (e.g. Foo<X>)
                    - [x] Wildcard type (e.g. ? extends Closeable)
                    - [x] Generic array type (e.g. T[])
                    - [x] Intersection type (e.g. X extends List & Serializable)
                - [x] Annotation
                    - [x] on Type
                    - [x] on Constructor
                    - [x] on Method
                    - [x] on Field
                    - [x] on Parameter
                    - [x] on Annotation
                    - [ ] on Local Variable
                    - [ ] on Package
                    - [ ] on Type Parameter
                    - [ ] on Type Use
                    - [x] Repeatable declaration
                - [x] Variable arity method (a.k.a. "varargs")
                - [ ] Type interference for generic instance creation (a.k.a. the "diamond operator") (JLS11 15.9.1)
                - [x] Lambda expression
                - [x] Method reference (e.g. this::method, String::concat, Type::new, int[]::new)
                """);
    }

    public static class Compile extends bee.task.Compile {
        {
            useECJ = true;
        }
    }
}