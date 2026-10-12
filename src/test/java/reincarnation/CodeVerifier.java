/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.lang.model.SourceVersion;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import bee.UserInterface;
import bee.util.JavaCompiler;
import kiss.I;
import psychopath.Locator;
import reincarnation.TestCode.Param;
import reincarnation.coder.java.JavaCoder;
import reincarnation.coder.java.JavaCodingOption;

public class CodeVerifier {

    /** No parameter. */
    private static final Object NoParam = new Object();

    /** The built-in parameters. */
    private static final List<Integer> ints = List.of(Integer.MIN_VALUE, -10, -1, 0, 1, 10, Integer.MAX_VALUE);

    /** The built-in parameters. */
    private static final List<Long> longs = List.of(Long.MIN_VALUE, -10L, -1L, 0L, 1L, 10L, Long.MAX_VALUE);

    /** The built-in parameters. */
    private static final List<Float> floats = List.of(Float.MIN_VALUE, -1F, -0.5F, 0F, 0.5F, 1.0F, Float.MAX_VALUE);

    /** The built-in parameters. */
    private static final List<Double> doubles = List.of(Double.MIN_VALUE, -1D, -0.5D, 0D, 0.5D, 1.0D, Double.MAX_VALUE);

    /** The built-in parameters. */
    private static final List<Byte> bytes = List.of(Byte.MIN_VALUE, (byte) -10, (byte) -1, (byte) 0, (byte) 1, (byte) 10, Byte.MAX_VALUE);

    /** The built-in parameters. */
    private static final List<Short> shorts = List
            .of(Short.MIN_VALUE, (short) -10, (short) -1, (short) 0, (short) 1, (short) 10, Short.MAX_VALUE);

    /** The built-in parameters. */
    private static final List<Character> chars = List.of(Character.MIN_VALUE, '0', ' ', 'A', Character.MAX_VALUE);

    /** The built-in parameters. */
    private static final List<Boolean> booleans = List.of(false, true);

    /** The built-in parameters. */
    private static final List<String> texts = List.of("", " ", "a", "A", "あ", "\\", "\t", "some value");

    /**
     * Cache the original expected value. If a test containing static references is run multiple
     * times, the expected value may be different each time.
     */
    private static final Map<Class, List> cache = new ConcurrentHashMap();

    /** The debugged class manager. */
    private static final Set<Class> debugged = ConcurrentHashMap.newKeySet();

    /** The required code fragments which must appear in the decompiled output. */
    private List<String> requiredFragments;

    /** The current debbuger. */
    private final Debugger debugger = Debugger.current();

    /** The debug mode. */
    private Debuggable debuggable;

    /** The test method. */
    private Method method;

    @BeforeEach
    void enableDebugMode(TestInfo info) {
        method = info.getTestMethod().orElseThrow();
        debuggable = info.getTestMethod().get().getAnnotation(Debuggable.class);
        if (debuggable == null) {
            debuggable = info.getTestClass().get().getAnnotation(Debuggable.class);
        }
    }

    @AfterEach
    void disableDebugMode(TestInfo info) {
        debuggable = null;
    }

    /**
     * Verify decompiled code and check that all required code fragments appear in the decompiled
     * output.
     * 
     * @param code A target code to verify.
     * @param fragments Required code fragments (e.g. the expected catch clause of a multi-catch).
     */
    protected final void verify(TestCode code, String... fragments) {
        requiredFragments = List.of(fragments);
        try {
            verify(code);
        } finally {
            requiredFragments = null;
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(TestCode code) {
        Class target = code.getClass();

        // ========================================================
        // Execute test code and store results by java.
        //
        // Cache the original expected value. If a test containing static references is run multiple
        // times, the expected value may be different each time.
        // ========================================================
        JavaVerifier base = new JavaVerifier(target);
        List inputs = prepareInputs(base.method);
        List expecteds = cache.computeIfAbsent(target, key -> {
            List list = new ArrayList();
            for (Object input : inputs) {
                list.add(base.verifier.apply(input));
            }
            return list;
        });

        if (base.method.isAnnotationPresent(ShowJavaResultOnly.class)) {
            System.out.println("Show Java result : " + base.method);
            for (int i = 0; i < inputs.size(); i++) {
                System.out.println(inputs.get(i) + " : " + expecteds.get(i));
            }
            return;
        }

        // ========================================================
        // Prepare recompile.
        // ========================================================
        CompileInfo info = new CompileInfo(method, target, CompilerType.current());
        info.decompilerDebugLog = debugger.replaceOutput();

        try {
            try {
                // ========================================================
                // Compile code by javac.
                // ========================================================
                if (CompilerType.isJavac()) {
                    try {
                        compileAllTestsByJavac();
                        target = JavacClassLoader.loadClass(target.getName());
                    } catch (ClassNotFoundException e) {
                        throw I.quiet(e);
                    } catch (LinkageError e) {
                        e.printStackTrace();
                    }
                }

                // ========================================================
                // Decompile code.
                // ========================================================
                info.decompiled = decompile(target, debuggable != null);

                // ========================================================
                // Check the required code fragments.
                // ========================================================
                if (requiredFragments != null) {
                    for (String fragment : requiredFragments) {
                        assert info.decompiled
                                .contains(fragment) : "The required code fragment is not found. \n" + fragment + "\n\n" + info.decompiled;
                    }
                }

                // ========================================================
                // Recompile java code.
                // ========================================================
                Class recompiledClass = null;
                Silent notifier = new Silent();
                try {
                    // The JDK 25 compiler inserts a null check for the immediately enclosing
                    // instance into inner class constructors when targeting release 25.
                    // However, this verifier instantiates the recompiled inner classes with a null
                    // enclosing instance via reflection, so such a null check throws an NPE.
                    // Therefore, we compile the decompiled code with the latest release that does
                    // not emit the enclosing instance null check. (See JDK-8351274)
                    ClassLoader loader = JavaCompiler.with(notifier)
                            .addSource(JavaCoder.computeName(target.getEnclosingClass()), info.decompiled)
                            .addClassPath(Locator.directory("target/test-classes"))
                            .setVersion(SourceVersion.RELEASE_24)
                            .compile();

                    recompiledClass = loader.loadClass(JavaCoder.computeName(target));
                    assert target != recompiledClass; // load from different classloader
                } catch (Throwable e) {
                    info.messages.add(notifier.message.toString());
                    throw e;
                }
                // ========================================================
                // Execute recompiled code and compare result with original.
                // ========================================================
                JavaVerifier java = new JavaVerifier(recompiledClass);

                for (int i = 0; i < inputs.size(); i++) {
                    java.verify(inputs.get(i), expecteds.get(i));
                }

                // ========================================================
                // Low Line Verification
                // ========================================================
                List<String> lines = info.lowLevelVerifier();
                if (!lines.isEmpty()) {
                    List<String> decompiled = Arrays.stream(info.decompiled.split("\n")).map(String::trim).toList();

                    for (String line : lines) {
                        assert decompiled.stream().anyMatch(line::equals) : "The required code fragment is not found. \n" + line + "\n";
                    }
                }
            } catch (Throwable e) {
                if (info.decompilerDebugLog.isEmpty() && debugged.add(target)) {
                    // decompile with debug mode
                    Reincarnation.CACHE.remove(target);
                    info.decompiled = decompile(target, true);
                }
                throw e;
            }
        } catch (Throwable e) {
            if (CompilerType.isJavac()) {
                info.asmfier(target, code.getClass());
            }
            throw info.message(e).buildError();
        } finally {
            if (debuggable != null) {
                if (CompilerType.isJavac()) {
                    info.asmfier(target, code.getClass());
                }

                // compare by another decompiler
                if (debuggable.vineflower()) {
                    info.decompileByVineFlower();
                }
                System.out.println(info.buildMessage(true));
            }
        }
    }

    private String decompile(Class target, boolean debug) {
        JavaCodingOption options = new JavaCodingOption();
        options.writeMemberFromTopLevel = true;

        debugger.enableForcibly = debug;
        String decompiled = Reincarnation.rebirth(target, options);
        debugger.enableForcibly = false;

        return decompiled;
    }

    /**
     * Prepare input values for test automatically.
     * 
     * @param method A target method.
     * @return A built-in values.
     */
    private List prepareInputs(Method method) {
        Class[] params = method.getParameterTypes();

        if (params.length == 0) {
            return Collections.singletonList(NoParam);
        }

        Class type = params[0];
        Annotation[] annotations = method.getParameterAnnotations()[0];

        if (annotations.length == 1 && annotations[0] instanceof Param) {
            return prepareInputs(type, (Param) annotations[0]);
        }

        if (type == boolean.class) {
            return booleans;
        } else if (type == char.class) {
            return chars;
        } else if (type == int.class) {
            return ints;
        } else if (type == long.class) {
            return longs;
        } else if (type == float.class) {
            return floats;
        } else if (type == double.class) {
            return doubles;
        } else if (type == short.class) {
            return shorts;
        } else if (type == byte.class) {
            return bytes;
        } else if (type == String.class) {
            return texts;
        } else if (type == Class.class) {
            return Arrays.asList(method.getDeclaringClass(), int.class);
        } else {
            return Arrays.asList(null, "String", 1);
        }
    }

    /**
     * Prepare user specified input values for test.
     * 
     * @param type A parameter type.
     * @param method A target method.
     * @return A user specified values.
     */
    private List prepareInputs(Class type, Param param) {
        if (type == boolean.class) {
            // If this exception will be thrown, it is bug of this program. So we must rethrow the
            // wrapped error in here.
            throw new Error();
        } else if (type == char.class) {
            return asList(param.chars());
        } else if (type == int.class) {
            int from = param.from();
            int to = param.to();

            if (from != to) {
                List inputs = new ArrayList();

                for (int i = from; i <= to; i++) {
                    inputs.add(i);
                }
                return inputs;
            }

            return asList(param.ints());
        } else if (type == long.class) {
            return asList(param.longs());
        } else if (type == float.class) {
            return asList(param.floats());
        } else if (type == double.class) {
            return asList(param.doubles());
        } else if (type == short.class) {
            return asList(param.shorts());
        } else if (type == byte.class) {
            return asList(param.bytes());
        } else if (type == String.class) {
            return asList(param.strings());
        } else {
            // If this exception will be thrown, it is bug of this program. So we must rethrow the
            // wrapped error in here.
            throw new Error();
        }
    }

    /**
     * Helper method to prepare user specified inputs.
     * 
     * @param array
     * @return
     */
    private List asList(Object array) {
        List inputs = new ArrayList();

        for (int i = 0; i < Array.getLength(array); i++) {
            inputs.add(Array.get(array, i));
        }
        return inputs;
    }

    /** The compiled class loader for Javac. */
    private static ClassLoader JavacClassLoader;

    /**
     * Compile all test sources by Javac.
     */
    private static synchronized void compileAllTestsByJavac() {
        if (JavacClassLoader == null) {
            JavacClassLoader = JavaCompiler.with(new Silent())
                    .addCurrentClassPath()
                    .addSourceDirectory(Locator.directory("src/test/java"))
                    .compile();
        }
    }

    /**
     * 
     */
    private static class Silent extends UserInterface {

        /** The message buffer. */
        private StringBuilder message = new StringBuilder();

        /**
         * {@inheritDoc}
         */
        @Override
        protected void write(int type, String message) {
            this.message.append(message).append("\r\n");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void write(Throwable error) {
            error.printStackTrace();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Appendable getInterface() {
            return System.out;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected InputStream getSink() {
            return System.in;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void startCommand(String name) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void endCommand(String name) {
        }
    }
}