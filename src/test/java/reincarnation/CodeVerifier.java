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

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.jar.Manifest;

import org.jetbrains.java.decompiler.main.Fernflower;
import org.jetbrains.java.decompiler.main.extern.IBytecodeProvider;
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
import org.jetbrains.java.decompiler.main.extern.IFernflowerPreferences;
import org.jetbrains.java.decompiler.main.extern.IResultSaver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.TraceClassVisitor;

import bee.UserInterface;
import bee.api.Command;
import bee.util.JavaCompiler;
import kiss.I;
import kiss.WiseSupplier;
import psychopath.Location;
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

    /** The current debbuger. */
    private final Debugger debugger = Debugger.current();

    /** The debug mode. */
    private Debuggable debuggable;

    @BeforeEach
    void enableDebugMode(TestInfo info) {
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
        JavaVerifier base = new JavaVerifier(target, "Execute original test case by java.");
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
        StringBuilder debugLog = debugger.replaceOutput();
        String decompiled = "";

        try {
            // ========================================================
            // Copile code by javac.
            // ========================================================
            if (CompilerType.isJavac()) {
                try {
                    compileAllTestsByJavac();
                    target = JavacClassLoader.loadClass(target.getName());
                } catch (ClassNotFoundException e) {
                    throw I.quiet(e);
                }
            }

            // ========================================================
            // Decompile code.
            // ========================================================
            decompiled = decompile(target, debuggable != null);

            // ========================================================
            // Recompile java code.
            // ========================================================
            Class recompiledClass = null;
            Silent notifier = new Silent();
            try {
                ClassLoader loader = JavaCompiler.with(notifier)
                        .addSource(JavaCoder.computeName(target.getEnclosingClass()), decompiled)
                        .addClassPath(Locator.directory("target/test-classes"))
                        .compile();

                recompiledClass = loader.loadClass(JavaCoder.computeName(target));
                assert target != recompiledClass; // load from different classloader
            } catch (Throwable e) {
                Failuer failuer = Failuer.type("Compile Error")
                        .reason(e)
                        .reason("=================================================")
                        .reason(notifier.message)
                        .reason("-------------------------------------------------")
                        .reason(format(decompiled, true));

                if (CompilerType.isJavac()) {
                    failuer //
                            .reason("-------------------------------------------------")
                            .reason("  Javac version")
                            .reason("-------------------------------------------------")
                            .reason(asmifier(target))
                            .reason("-------------------------------------------------")
                            .reason("  ECJ version")
                            .reason("-------------------------------------------------")
                            .reason(asmifier(code.getClass()));
                }
                throw failuer.reason("=================================================");
            }
            // ========================================================
            // Execute recompiled code and compare result with original.
            // ========================================================
            JavaVerifier java = new JavaVerifier(recompiledClass, decompiled);

            for (int i = 0; i < inputs.size(); i++) {
                java.verify(inputs.get(i), expecteds.get(i));
            }
        } catch (Throwable e) {
            if (debugLog.isEmpty() && debugged.add(target)) {
                // decompile with debug mode
                Reincarnation.cache.remove(target);
                decompiled = decompile(target, true);
            }
            throw I.quiet(e);
        } finally {
            if (!debugLog.isEmpty()) {
                for (String line : format(decompiled, true)) {
                    debugLog.append(line).append("\r\n");
                }
                debugLog.append("\r\n");

                // compare by another decompiler
                if (debuggable != null && debuggable.fernflower()) {
                    debugLog.append("Decompiled by FernFlower\r\n").append(decompileByFernFlower(target)).append("\r\n");
                }

                System.out.println(debugLog);
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

    /**
     * Format as line-numbered code.
     * 
     * @param code
     * @return
     */
    private String[] format(String code, boolean enableLineNumber) {
        String[] lines = code.split("\r\n");

        if (enableLineNumber) {
            int max = (int) (Math.log10(lines.length) + 1);

            for (int i = 0; i < lines.length; i++) {
                int number = i + 1;
                int size = (int) (Math.log10(number) + 1);
                lines[i] = "0".repeat(max - size) + number + "    " + lines[i];
            }
        }
        return lines;
    }

    /**
     * Write ASM code.
     * 
     * @param target
     * @return
     */
    private String[] asmifier(Class target) {
        try {
            StringWriter output = new StringWriter();
            InputStream input = target.getClassLoader().getResourceAsStream(target.getName().replace('.', '/') + ".class");
            new ClassReader(input).accept(new TraceClassVisitor(null, new ASMWriter(), new PrintWriter(output)), ClassReader.EXPAND_FRAMES);

            return I.signal(output.toString().split("\n"))
                    .skip(line -> line.startsWith("import ") || line.startsWith("package "))
                    .skip(line -> line.startsWith("ClassWriter ") || line.startsWith("FieldVisitor ") || line
                            .startsWith("RecordComponentVisitor ") || line
                                    .startsWith("MethodVisitor ") || line.startsWith("AnnotationVisitor "))
                    .skip(line -> line.startsWith("classWriter.visitSource") || line.startsWith("classWriter.visitNestHost") || line
                            .startsWith("classWriter.visitNestMember") || line
                                    .startsWith("classWriter.visitOuterClass") || line.startsWith("classWriter.visitInnerClass"))
                    .skip(line -> line.startsWith("classWriter.visitEnd") || line.startsWith("return classWriter.toByteArray"))
                    .skip("", (prev, next) -> prev.isBlank() && next.isBlank())
                    .toList()
                    .toArray(new String[0]);
        } catch (IOException e) {
            throw I.quiet(e);
        }
    }

    /**
     * Throw the failure of decompilation.
     * 
     * @param message
     * @return
     */
    private static Throwable error(String message) {
        return Failuer.type("Invalid Decompilation")
                .reason("=================================================")
                .reason(message)
                .reason("=================================================");
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
     * @version 2018/10/09 14:36:42
     */
    private static interface Verifier {

        /**
         * Verify the culculation.
         * 
         * @param param An input parameter.
         * @param expectedResult An expected result.
         */
        void verify(Object param, Object expectedResult);
    }

    /**
     * @version 2018/10/09 12:16:56
     */
    private static class JavaVerifier<T> implements Verifier {

        /** The verifier. */
        private final Function verifier;

        /** The actual verifier method. */
        private final Method method;

        /** The detailed error. */
        private final String detailError;

        /**
         * @param type
         * @param detailError
         */
        private JavaVerifier(Class clazz, String detailError) {
            this.detailError = detailError;

            // we smust create new instance for each parameters
            Constructor c = clazz.getDeclaredConstructors()[0];
            c.setAccessible(true);
            WiseSupplier instantiator = () -> c.newInstance((Object[]) Array.newInstance(Object.class, c.getParameterCount()));

            // search verifier method
            method = I.signal(clazz.getDeclaredMethods()).take(m -> m.getName().equals("run")).first().to().v;
            method.setAccessible(true);

            switch (method.getParameterCount()) {
            case 0:
                verifier = param -> {
                    try {
                        return method.invoke(instantiator.get());
                    } catch (InvocationTargetException e) {
                        Throwable cause = e.getCause();
                        if (needRethrow(clazz, cause)) {
                            throw I.quiet(cause);
                        } else {
                            return cause;
                        }
                    } catch (Throwable e) {
                        return e;
                    }
                };
                break;

            case 1:
                verifier = param -> {
                    try {
                        return method.invoke(instantiator.get(), param);
                    } catch (InvocationTargetException e) {
                        Throwable cause = e.getCause();
                        if (needRethrow(clazz, cause)) {
                            throw I.quiet(cause);
                        } else {
                            return cause;
                        }
                    } catch (Throwable e) {
                        return e;
                    }
                };
                break;

            default:
                // If this exception will be thrown, it is bug of this program. So we must rethrow
                // the wrapped error in here.
                throw new Error(getClass().getSimpleName() + " don't support for multiple parameters.");
            }
        }

        private boolean needRethrow(Class clazz, Throwable error) {
            return error instanceof AssertionError && !clazz.getEnclosingClass().getSimpleName().equals("AssertionTest");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void verify(Object param, Object expectedResult) {
            assertObject(expectedResult, verifier.apply(param));
        }

        /**
         * Assert that java object equals to javascript object.
         * 
         * @param expected
         * @param actual
         */
        private void assertObject(Object expected, Object actual) {
            if (expected == null) {
                if (actual instanceof Throwable) {
                    ((Throwable) actual).printStackTrace();
                }
                assert actual == null : error(detailError);
            } else {
                Class type = expected.getClass();

                if (type.isArray()) {
                    assertArray(expected, actual);
                } else if (Throwable.class.isAssignableFrom(type)) {
                    assert type.isInstance(actual);
                    assert Objects.equals(((Throwable) expected).getMessage(), ((Throwable) actual).getMessage());
                } else {
                    assert expected.equals(actual) : error(detailError);
                }
            }
        }

        /**
         * Assert each items in array.
         * 
         * @param expected
         * @param actual
         */
        private void assertArray(Object expected, Object actual) {
            // check array size
            assert Array.getLength(expected) == Array.getLength(actual) : error(detailError);

            // check each items
            int size = Array.getLength(expected);

            for (int index = 0; index < size; index++) {
                assertObject(Array.get(expected, index), Array.get(actual, index));
            }
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
        public void startCommand(String name, Command command) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void endCommand(String name, Command command) {
        }
    }

    /**
     * Decompile by fernflower.
     * 
     * @param target
     * @return
     */
    private static String decompileByFernFlower(Class target) {
        Map<String, Object> options = new HashMap();
        options.put(IFernflowerPreferences.REMOVE_SYNTHETIC, "1");
        options.put(IFernflowerPreferences.INDENT_STRING, "\t");
        options.put(IFernflowerPreferences.LOG_LEVEL, "WARN");

        IBytecodeProvider provider = (externalPath, internalPath) -> {
            return Locator.file(externalPath).bytes();
        };

        String[] decompiled = new String[1];

        IResultSaver saver = new IResultSaver() {

            @Override
            public void saveFolder(String path) {
            }

            @Override
            public void saveDirEntry(String path, String archiveName, String entryName) {
            }

            @Override
            public void saveClassFile(String path, String qualifiedName, String entryName, String content, int[] mapping) {
                decompiled[0] = content;
            }

            @Override
            public void saveClassEntry(String path, String archiveName, String qualifiedName, String entryName, String content) {
            }

            @Override
            public void createArchive(String path, String archiveName, Manifest manifest) {
            }

            @Override
            public void copyFile(String source, String path, String entryName) {
            }

            @Override
            public void copyEntry(String source, String path, String archiveName, String entry) {
            }

            @Override
            public void closeArchive(String path, String archiveName) {
            }
        };

        IFernflowerLogger logger = new IFernflowerLogger() {

            /**
             * {@inheritDoc}
             */
            @Override
            public void writeMessage(String message, Severity severity) {
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void writeMessage(String message, Severity severity, Throwable t) {
            }
        };

        List<Class> classes = new ArrayList();
        classes.add(target);

        Class parent = target.getEnclosingClass();
        while (parent != null) {
            classes.add(parent);
            parent = parent.getEnclosingClass();
        }

        Fernflower fernflower = new Fernflower(provider, saver, options, logger);
        for (Class clazz : classes) {
            Location root = Locator.locate(clazz);
            fernflower.addSource(root.asDirectory().file(clazz.getName().replace('.', '/').concat(".class")).asJavaFile());
        }
        fernflower.decompileContext();

        return decompiled[0];
    }
}