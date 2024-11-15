<p align="center">
    <a href="https://docs.oracle.com/en/java/javase/21/"><img src="https://img.shields.io/badge/Java-Release%2021-green"/></a>
    <span>&nbsp;</span>
    <a href="https://jitpack.io/#teletha/reincarnation"><img src="https://img.shields.io/jitpack/v/github/teletha/reincarnation?label=Repository&color=green"></a>
    <span>&nbsp;</span>
    <a href="https://teletha.github.io/reincarnation"><img src="https://img.shields.io/website.svg?down_color=red&down_message=CLOSE&label=Official%20Site&up_color=green&up_message=OPEN&url=https%3A%2F%2Fteletha.github.io%2Freincarnation"></a>
</p>

## Summary
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
- [x] Constructor
    - [ ] Explicit receiver parameters on inner class
- [x] Field
- [x] Method
    - [x] Variable arity method (a.k.a. "varargs")
    - [ ] Explicit receiver parameters
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
        - [x] expression
            - [x] yield
            - [x] arrow syntax
            - [x] multiple comma-separated labels
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
- [ ] Type interference for generic instance creation (a.k.a. the "diamond operator") (JLS11 15.9.1)
- [x] Lambda expression
- [x] Method reference (e.g. this::method, String::concat, Type::new, int[]::new)
<p align="right"><a href="#top">back to top</a></p>






## Prerequisites
Reincarnation runs on all major operating systems and requires only [Java version 21](https://docs.oracle.com/en/java/javase/21/) or later to run.
To check, please run `java -version` on your terminal.
<p align="right"><a href="#top">back to top</a></p>

## Install
For any code snippet below, please substitute the version given with the version of Reincarnation you wish to use.
#### [Maven](https://maven.apache.org/)
Add JitPack repository at the end of repositories element in your build.xml:
```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```
Add it into in the dependencies element like so:
```xml
<dependency>
    <groupId>com.github.teletha</groupId>
    <artifactId>reincarnation</artifactId>
    <version>1.9.0</version>
</dependency>
```
#### [Gradle](https://gradle.org/)
Add JitPack repository at the end of repositories in your build.gradle:
```gradle
repositories {
    maven { url "https://jitpack.io" }
}
```
Add it into the dependencies section like so:
```gradle
dependencies {
    implementation 'com.github.teletha:reincarnation:1.9.0'
}
```
#### [SBT](https://www.scala-sbt.org/)
Add JitPack repository at the end of resolvers in your build.sbt:
```scala
resolvers += "jitpack" at "https://jitpack.io"
```
Add it into the libraryDependencies section like so:
```scala
libraryDependencies += "com.github.teletha" % "reincarnation" % "1.9.0"
```
#### [Leiningen](https://leiningen.org/)
Add JitPack repository at the end of repositories in your project.clj:
```clj
:repositories [["jitpack" "https://jitpack.io"]]
```
Add it into the dependencies section like so:
```clj
:dependencies [[com.github.teletha/reincarnation "1.9.0"]]
```
#### [Bee](https://teletha.github.io/bee)
Add it into your project definition class like so:
```java
require("com.github.teletha", "reincarnation", "1.9.0");
```
<p align="right"><a href="#top">back to top</a></p>


## Contributing
Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.
If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

The overwhelming majority of changes to this project don't add new features at all. Optimizations, tests, documentation, refactorings -- these are all part of making this product meet the highest standards of code quality and usability.
Contributing improvements in these areas is much easier, and much less of a hassle, than contributing code for new features.

### Bug Reports
If you come across a bug, please file a bug report. Warning us of a bug is possibly the most valuable contribution you can make to Reincarnation.
If you encounter a bug that hasn't already been filed, [please file a report](https://github.com/teletha/reincarnation/issues/new) with an [SSCCE](http://sscce.org/) demonstrating the bug.
If you think something might be a bug, but you're not sure, ask on StackOverflow or on [reincarnation-discuss](https://github.com/teletha/reincarnation/discussions).
<p align="right"><a href="#top">back to top</a></p>


## Dependency
Reincarnation depends on the following products on runtime.
* [asm-9.7.1](https://mvnrepository.com/artifact/org.ow2.asm/asm/9.7.1)
* [sinobu-4.5.0](https://mvnrepository.com/artifact/com.github.teletha/sinobu/4.5.0)
<p align="right"><a href="#top">back to top</a></p>


## License
Copyright (C) 2024 The REINCARNATION Development Team

MIT License

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
<p align="right"><a href="#top">back to top</a></p>