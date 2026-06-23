/*
 * Copyright (C) 2026 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.transpiler.js;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import com.caoccao.javet.interop.V8Host;
import com.caoccao.javet.interop.V8Runtime;
import com.caoccao.javet.swc4j.Swc4j;
import com.caoccao.javet.swc4j.enums.Swc4jMediaType;
import com.caoccao.javet.swc4j.options.Swc4jTranspileOptions;
import com.caoccao.javet.swc4j.outputs.Swc4jTranspileOutput;

import reincarnation.CompileInfo;
import reincarnation.CompilerType;
import reincarnation.JavaVerifier;
import reincarnation.Reincarnation;
import reincarnation.ShowJavaResultOnly;
import reincarnation.TestCode;
import reincarnation.TestCode.Param;
import reincarnation.coder.ts.TypeScriptCodingOption;

public class JavetTestSupport {

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

    /** The test method. */
    private Method method;

    @BeforeEach
    void enableDebugMode(TestInfo info) {
        method = info.getTestMethod().orElseThrow();
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

        try {
            try {
                // ========================================================
                // Decompile code.
                // ========================================================
                info.decompiled = decompile(target, false);

                System.out.println(info.decompiled);

                // ========================================================
                // Execute recompiled code and compare result with original.
                // ========================================================
                // JavaVerifier java = new JavaVerifier(recompiledClass);
                //
                // for (int i = 0; i < inputs.size(); i++) {
                // java.verify(inputs.get(i), expecteds.get(i));
                // }

                URL specifier = URI.create("file:///abc.ts").toURL();
                Swc4jTranspileOptions options = new Swc4jTranspileOptions().setSpecifier(specifier).setMediaType(Swc4jMediaType.TypeScript);
                // Transpile the code.
                Swc4jTranspileOutput output = new Swc4j().transpile(info.decompiled, options);
                // Print the transpiled code.
                System.out.println(output.getCode());

                // Node.js Mode
                try (V8Runtime runtime = V8Host.getNodeInstance().createV8Runtime()) {
                    System.out.println(runtime.getExecutor(output.getCode() + "\n" + "new JavascriptTestSupportTest$1().run();")
                            .executeString());
                } catch (Exception e) {
                    e.printStackTrace();
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
                // if (info.decompilerDebugLog.isEmpty() && debugged.add(target)) {
                // // decompile with debug mode
                // Reincarnation.CACHE.remove(target);
                // info.decompiled = decompile(target, true);
                // }
                throw e;
            }
        } catch (Throwable e) {
            if (CompilerType.isJavac()) {
                info.asmfier(target, code.getClass());
            }
            throw info.message(e).buildError();
        } finally {
            // if (debuggable != null) {
            // if (CompilerType.isJavac()) {
            // info.asmfier(target, code.getClass());
            // }
            //
            // // compare by another decompiler
            // if (debuggable.vineflower()) {
            // info.decompileByVineFlower();
            // }
            // System.out.println(info.buildMessage(true));
            // }
        }
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

    private String decompile(Class target, boolean debug) {
        TypeScriptCodingOption options = new TypeScriptCodingOption();
        options.writeMemberFromTopLevel = true;

        String decompiled = Reincarnation.rebirth(target, options);

        return decompiled;
    }
}
