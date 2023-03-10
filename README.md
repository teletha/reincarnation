<p align="center">
    <a href="https://docs.oracle.com/en/java/javase/19/"><img src="https://img.shields.io/badge/Java-Release%2019-green"/></a>
    <span>&nbsp;</span>
    <a href="https://jitpack.io/#teletha/reincarnation"><img src="https://img.shields.io/jitpack/v/github/teletha/reincarnation?label=Repository&color=green"></a>
    <span>&nbsp;</span>
    <a href="https://teletha.github.io/reincarnation"><img src="https://img.shields.io/website.svg?down_color=red&down_message=CLOSE&label=Official%20Site&up_color=green&up_message=OPEN&url=https%3A%2F%2Fteletha.github.io%2Freincarnation"></a>
</p>


## Summary
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
- [x] Static member type declaration
- [x] Inner (non-static member, local, anonymous) class declaration
- [x] Class initializer, instance initializer
- [x] Field declaration, method declaration
- [x] Local variable declaration
- [x] Class variable initializer, instance variable initializer
- [x] Statment
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
    - [x] auto-boxing and unboxing
- [x] Array initializer (String[] a = { "x", "y", "z" })
- [x] Local variable information
- [x] Modifiers
    - [x] public
    - [x] protected
    - [x] private
    - [x] final
    - [x] synchronized
    - [x] abstract
    - [x] native
    - [x] static
- [x] Generics
    - [x] Parameterized types (e.g. Foo<X>)
    - [x] Type arguments (e.g. List<String>)
    - [x]
- [ ] Annotation
    - [ ] Type
    - [ ] Constructor
    - [ ] Method
    - [ ] Field
    - [ ] Parameter
    - [ ]
- [ ] Variable arity methods (a.k.a. "varargs")
- [ ] Type interference for generic instance creation (a.k.a. the "diamond operator") (JLS11 15.9.1)
- [ ] Lambda expressions
- [ ] Constructor references
- [ ] Method references
- [ ] Default methods
- [ ] Static interface methods
- [ ] Private interface methods
- [ ] Text block
<p align="right"><a href="#top">back to top</a></p>


## Usage

<p align="right"><a href="#top">back to top</a></p>


## Prerequisites
Reincarnation runs on all major operating systems and requires only [Java version 19](https://docs.oracle.com/en/java/javase/19/) or later to run.
To check, please run `java -version` from the command line interface. You should see something like this:
```
> java -version
openjdk version "16" 2021-03-16
OpenJDK Runtime Environment (build 16+36-2231)
OpenJDK 64-Bit Server VM (build 16+36-2231, mixed mode, sharing)
```
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
    <artifactId>Reincarnation</artifactId>
    <version>1.1.0</version>
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
    implementation 'com.github.teletha:Reincarnation:1.1.0'
}
```
#### [SBT](https://www.scala-sbt.org/)
Add JitPack repository at the end of resolvers in your build.sbt:
```scala
resolvers += "jitpack" at "https://jitpack.io"
```
Add it into the libraryDependencies section like so:
```scala
libraryDependencies += "com.github.teletha" % "Reincarnation" % "1.1.0"
```
#### [Leiningen](https://leiningen.org/)
Add JitPack repository at the end of repositories in your project.clj:
```clj
:repositories [["jitpack" "https://jitpack.io"]]
```
Add it into the dependencies section like so:
```clj
:dependencies [[com.github.teletha/Reincarnation "1.1.0"]]
```
#### [Bee](https://teletha.github.io/bee)
Add it into your project definition class like so:
```java
require("com.github.teletha", "Reincarnation", "1.1.0");
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
If you think something might be a bug, but you're not sure, ask on StackOverflow or on [Reincarnation-discuss](https://github.com/teletha/reincarnation/discussions).
<p align="right"><a href="#top">back to top</a></p>


## Dependency
Reincarnation depends on the following products on runtime.
* [asm-9.4](https://mvnrepository.com/artifact/org.ow2.asm/asm/9.4)
* [commons-compiler-3.1.9](https://mvnrepository.com/artifact/org.codehaus.janino/commons-compiler/3.1.9)
* [janino-3.1.9](https://mvnrepository.com/artifact/org.codehaus.janino/janino/3.1.9)
* [psychopath-1.7.1](https://mvnrepository.com/artifact/com.github.teletha/psychopath/1.7.1)
* [sinobu-3.2.0](https://mvnrepository.com/artifact/com.github.teletha/sinobu/3.2.0)
<p align="right"><a href="#top">back to top</a></p>


## License
Copyright (C) 2023 The REINCARNATION Development Team

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