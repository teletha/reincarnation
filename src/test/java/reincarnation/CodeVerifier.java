/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import bee.UserInterface;
import bee.api.Command;
import bee.util.JavaCompiler;
import booton.translator.Javascript;
import kiss.I;
import net.sourceforge.htmlunit.corejs.javascript.NativeObject;
import net.sourceforge.htmlunit.corejs.javascript.UniqueTag;
import reincarnation.Code.BooleanParam;
import reincarnation.Code.ByteParam;
import reincarnation.Code.ByteParamBoolean;
import reincarnation.Code.CharParam;
import reincarnation.Code.CharParamBoolean;
import reincarnation.Code.DoubleParam;
import reincarnation.Code.DoubleParamBoolean;
import reincarnation.Code.FloatParam;
import reincarnation.Code.FloatParamBoolean;
import reincarnation.Code.Int;
import reincarnation.Code.IntParam;
import reincarnation.Code.IntParamBoolean;
import reincarnation.Code.LongParam;
import reincarnation.Code.LongParamBoolean;
import reincarnation.Code.Param;
import reincarnation.Code.ShortParam;
import reincarnation.Code.ShortParamBoolean;
import reincarnation.Code.TextParam;
import reincarnation.Code.TextParamBoolean;

/**
 * @version 2018/10/04 14:37:22
 */
public class CodeVerifier {

    /** No parameter. */
    private static final Object NONE = new Object();

    /** The built-in parameters. */
    private static final int[] ints = {Integer.MIN_VALUE, -10, -1, 0, 1, 10, Integer.MAX_VALUE};

    /** The built-in parameters. */
    private static final long[] longs = {Long.MIN_VALUE, -10L, -1L, 0L, 1L, 10L, Long.MAX_VALUE};

    /** The built-in parameters. */
    private static final float[] floats = {Float.MIN_VALUE, -1F, -0.5F, 0F, 0.5F, 1.0F, Float.MAX_VALUE};

    /** The built-in parameters. */
    private static final double[] doubles = {Double.MIN_VALUE, -1D, -0.5D, 0D, 0.5D, 1.0D, Double.MAX_VALUE};

    /** The built-in parameters. */
    private static final byte[] bytes = {Byte.MIN_VALUE, -10, -1, 0, 1, 10, Byte.MAX_VALUE};

    /** The built-in parameters. */
    private static final short[] shorts = {Short.MIN_VALUE, -10, -1, 0, 1, 10, Short.MAX_VALUE};

    /** The built-in parameters. */
    private static final char[] chars = {Character.MIN_VALUE, '0', ' ', 'A', Character.MAX_VALUE};

    /** The built-in parameters. */
    private static final boolean[] booleans = {false, true};

    /** The built-in parameters. */
    private static final String[] texts = {"", " ", "a", "A", "あ", "\\", "\t", "some value"};

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Int code) {
        assert code.run() == recompile(code).run() : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.IntArray code) {
        assert Arrays.equals(code.run(), recompile(code).run()) : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(IntParam code) {
        IntParam recompiled = recompile(code);

        for (int param : ints) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(IntParamBoolean code) {
        IntParamBoolean recompiled = recompile(code);

        for (int param : ints) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Long code) {
        assert code.run() == recompile(code).run() : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.LongArray code) {
        assert Arrays.equals(code.run(), recompile(code).run()) : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(LongParam code) {
        LongParam recompiled = recompile(code);

        for (long param : longs) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(LongParamBoolean code) {
        LongParamBoolean recompiled = recompile(code);

        for (long param : longs) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Float code) {
        assert code.run() == recompile(code).run() : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.FloatArray code) {
        assert Arrays.equals(code.run(), recompile(code).run()) : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(FloatParam code) {
        FloatParam recompiled = recompile(code);

        for (float param : floats) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(FloatParamBoolean code) {
        FloatParamBoolean recompiled = recompile(code);

        for (float param : floats) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Double code) {
        assert code.run() == recompile(code).run() : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.DoubleArray code) {
        assert Arrays.equals(code.run(), recompile(code).run()) : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(DoubleParam code) {
        DoubleParam recompiled = recompile(code);

        for (double param : doubles) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(DoubleParamBoolean code) {
        DoubleParamBoolean recompiled = recompile(code);

        for (double param : doubles) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Byte code) {
        assert code.run() == recompile(code).run() : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.ByteArray code) {
        assert Arrays.equals(code.run(), recompile(code).run()) : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(ByteParam code) {
        ByteParam recompiled = recompile(code);

        for (byte param : bytes) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(ByteParamBoolean code) {
        ByteParamBoolean recompiled = recompile(code);

        for (byte param : bytes) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Short code) {
        assert code.run() == recompile(code).run() : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.ShortArray code) {
        assert Arrays.equals(code.run(), recompile(code).run()) : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(ShortParam code) {
        ShortParam recompiled = recompile(code);

        for (short param : shorts) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(ShortParamBoolean code) {
        ShortParamBoolean recompiled = recompile(code);

        for (short param : shorts) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Char code) {
        assert code.run() == recompile(code).run() : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.CharArray code) {
        assert Arrays.equals(code.run(), recompile(code).run()) : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(CharParam code) {
        CharParam recompiled = recompile(code);

        for (char param : chars) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(CharParamBoolean code) {
        CharParamBoolean recompiled = recompile(code);

        for (char param : chars) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Boolean code) {
        assert code.run() == recompile(code).run() : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.BooleanArray code) {
        assert Arrays.equals(code.run(), recompile(code).run()) : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(BooleanParam code) {
        BooleanParam recompiled = recompile(code);

        for (boolean param : booleans) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Text code) {
        assert code.run() == recompile(code).run() : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.TextArray code) {
        assert Arrays.equals(code.run(), recompile(code).run()) : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(TextParam code) {
        TextParam recompiled = recompile(code);

        for (String param : texts) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(TextParamBoolean code) {
        TextParamBoolean recompiled = recompile(code);

        for (String param : texts) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code code) {
        Supplier<Code> original = instantiator((Class<Code>) code.getClass());
        Method originalMethod = searchInvocation(code.getClass());
        Supplier<Code> recompiled = recompile2(code);
        Method recompiledMethod = searchInvocation(recompiled.get().getClass());

        try {
            for (Object param : prepareInputs(originalMethod)) {
                assert originalMethod.invoke(original.get(), param).equals(recompiledMethod.invoke(recompiled.get(), param));
            }
        } catch (Exception e) {
            throw I.quiet(e);
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
            return Collections.singletonList(NONE);
        }

        Class type = params[0];
        Annotation[] annotations = method.getParameterAnnotations()[0];

        if (annotations.length == 1 && annotations[0] instanceof Param) {
            return prepareInputs(type, (Param) annotations[0]);
        }

        if (type == boolean.class) {
            return Arrays.asList(true, false);
        } else if (type == char.class) {
            return Arrays.asList('2', 'B', 'a', '$', '@', 'c', 'a', 't');
        } else if (type == int.class) {
            return Arrays.asList(0, 1, 2, 3, -1, -2, -3);
        } else if (type == long.class) {
            return Arrays.asList(0L, 1L, 2L, 123456789L, -1L, -2L, -123456789L);
        } else if (type == float.class) {
            return Arrays.asList(0F, 1F, 0.2F, -1.3464F);
        } else if (type == double.class) {
            return Arrays.asList(0D, 1D, -5.4D, 1.239754297642323D, 100.3D);
        } else if (type == short.class) {
            return Arrays.asList((short) 0, (short) 1, (short) 2, (short) -1, (short) -2);
        } else if (type == byte.class) {
            return Arrays.asList((byte) 0, (byte) 1, (byte) 2, (byte) -1, (byte) -2);
        } else if (type == String.class) {
            return Arrays.asList("", "a", "some value");
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
     * Recompile and recompile code.
     * 
     * @param code
     * @return
     */
    private <T extends Code> T recompile(T code) {
        Class<?> originalClass = code.getClass();
        Class<?> enclosingClass = originalClass.getEnclosingClass();
        String fqcn = originalClass.getName();

        CompilationUnit unit = Reincarnation.exhume(code.getClass());

        if (enclosingClass != null) {
            unit = enclose(enclosingClass.getSimpleName(), unit);

            if (originalClass.isAnonymousClass()) {
                fqcn = fqcn.replace("$", "$" + enclosingClass.getSimpleName() + "$");
            }
        }

        String decompiled = unit.toString();
        Silent notifier = new Silent();

        try {
            JavaCompiler compiler = new JavaCompiler(notifier);
            compiler.addSource(unit.getType(0).getNameAsString(), decompiled);
            compiler.addCurrentClassPath();

            ClassLoader loader = compiler.compile();
            Class<?> loadedClass = loader.loadClass(fqcn);
            assert originalClass != loadedClass; // load from different classloader

            Constructor constructor = loadedClass.getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            return (T) constructor.newInstance((Object[]) Array.newInstance(Object.class, constructor.getParameterCount()));
        } catch (Exception e) {
            throw I.quiet(e);
        } catch (Error e) {
            throw Failuer.type("Compile Error")
                    .reason(e)
                    .reason("=================================================")
                    .reason(notifier.message)
                    .reason("-------------------------------------------------")
                    .reason(format(decompiled))
                    .reason("=================================================");
        }
    }

    /**
     * Recompile and recompile code.
     * 
     * @param code
     * @return
     */
    private <T extends Code> Supplier<T> recompile2(T code) {
        Class<?> originalClass = code.getClass();
        Class<?> enclosingClass = originalClass.getEnclosingClass();
        String fqcn = originalClass.getName();

        CompilationUnit unit = Reincarnation.exhume(code.getClass());

        if (enclosingClass != null) {
            unit = enclose(enclosingClass.getSimpleName(), unit);

            if (originalClass.isAnonymousClass()) {
                fqcn = fqcn.replace("$", "$" + enclosingClass.getSimpleName() + "$");
            }
        }

        String decompiled = unit.toString();
        Silent notifier = new Silent();

        try {
            JavaCompiler compiler = new JavaCompiler(notifier);
            compiler.addSource(unit.getType(0).getNameAsString(), decompiled);
            compiler.addCurrentClassPath();

            ClassLoader loader = compiler.compile();
            Class<T> loadedClass = (Class<T>) loader.loadClass(fqcn);
            assert originalClass != loadedClass; // load from different classloader

            return instantiator(loadedClass);
        } catch (Exception e) {
            throw I.quiet(e);
        } catch (Error e) {
            throw Failuer.type("Compile Error")
                    .reason(e)
                    .reason("=================================================")
                    .reason(notifier.message)
                    .reason("-------------------------------------------------")
                    .reason(format(decompiled))
                    .reason("=================================================");
        }
    }

    /**
     * Enclose by the specified class.
     * 
     * @param className A simple class name.
     * @param base A target to enclose.
     * @return An enclosed unit.
     */
    private CompilationUnit enclose(String className, CompilationUnit base) {
        CompilationUnit enclose = new CompilationUnit(base.getPackageDeclaration().orElseThrow(), base.getImports(), new NodeList(), null);

        // create enclosing class
        ClassOrInterfaceDeclaration clazz = enclose.addClass(className);

        base.getTypes().forEach(type -> {
            type.addModifier(Modifier.STATIC);

            clazz.addMember(type);
        });
        return enclose;
    }

    /**
     * Show decompiled formated code.
     * 
     * @param code
     * @return
     */
    private Throwable code(Code code) {
        return Failuer.type("Invalid Decompilation")
                .reason("=================================================")
                .reason(format(Reincarnation.exhume(code.getClass()).toString()))
                .reason("=================================================");
    }

    /**
     * Format as line-numbered code.
     * 
     * @param code
     * @return
     */
    private String[] format(String code) {
        String[] lines = code.split("\r\n");
        int max = (int) (Math.log10(lines.length) + 1);

        for (int i = 0; i < lines.length; i++) {
            int number = i + 1;
            int size = (int) (Math.log10(number) + 1);
            lines[i] = "0".repeat(max - size) + number + "    " + lines[i];
        }
        return lines;
    }

    /**
     * Create instantiator.
     * 
     * @param type
     * @return
     */
    private <T> Supplier<T> instantiator(Class<T> type) {
        Constructor constructor = type.getDeclaredConstructors()[0];
        constructor.setAccessible(true);

        return () -> {
            try {
                return (T) constructor.newInstance((Object[]) Array.newInstance(Object.class, constructor.getParameterCount()));
            } catch (Exception e) {
                throw I.quiet(e);
            }
        };
    }

    /**
     * Assert that java object equals to javascript object.
     * 
     * @param original
     * @param recompiled
     */
    private void assertObject(Object original, Object recompiled) {
        if (original == null) {
            assert recompiled == null;
        } else {
            Class type = original.getClass();

            if (type.isArray()) {
                assertArray(original, recompiled);
            } else if (type == Integer.class) {
                // ========================
                // INT
                // ========================
                int value = ((Integer) original).intValue();

                if (recompiled instanceof Double) {
                    assert value == ((Double) recompiled).intValue();
                } else if (recompiled instanceof Long) {
                    assert value == ((Long) recompiled).intValue();
                } else if (recompiled instanceof UniqueTag) {
                    assert value == 0;
                } else {
                    assert value == ((Integer) recompiled).intValue();
                }
            } else if (type == Long.class) {
                // ========================
                // LONG
                // ========================
                long value = ((Long) original).longValue();

                if (recompiled instanceof UniqueTag) {
                    assert value == 0L;
                } else {
                    long jsValue = createLong((NativeObject) recompiled);
                    assert value == jsValue;
                }
            } else if (type == Float.class) {
                // ========================
                // FLOAT
                // ========================
                original = new BigDecimal((Float) original).round(new MathContext(3));
                recompiled = new BigDecimal((Double) recompiled).round(new MathContext(3));

                assert original.equals(recompiled);
            } else if (type == Double.class) {
                // ========================
                // DOUBLE
                // ========================
                original = new BigDecimal((Double) original).round(new MathContext(3));
                recompiled = new BigDecimal((Double) recompiled).round(new MathContext(3));

                assert original.equals(recompiled);
            } else if (type == Short.class) {
                // ========================
                // SHORT
                // ========================
                assert ((Short) original).doubleValue() == ((Double) recompiled).doubleValue();
            } else if (type == Byte.class) {
                // ========================
                // BYTE
                // ========================
                assert ((Byte) original).doubleValue() == ((Double) recompiled).doubleValue();
            } else if (type == Boolean.class) {
                // ========================
                // BOOLEAN
                // ========================
                if (recompiled instanceof Double) {
                    recompiled = ((Double) recompiled).intValue() != 0;
                }
                assert original.equals(recompiled);
            } else if (type == String.class) {
                // ========================
                // STRING
                // ========================
                assert recompiled.toString().equals(original);
            } else if (type == Character.class) {
                // ========================
                // CHARACTER
                // ========================
                if (recompiled instanceof Double) {
                    // numeric characters (i.e. 0, 1, 2...)
                    recompiled = Character.valueOf((char) (((Double) recompiled).intValue() + 48));
                }
                if (recompiled instanceof NativeObject) {
                    recompiled = NativeObject.callMethod((NativeObject) recompiled, Javascript
                            .computeMethodName(Object.class, "toString", "()Ljava/lang/String;"), new Object[] {});
                }
                assert ((Character) original).toString().equals(recompiled.toString());
            } else if (Throwable.class.isAssignableFrom(type)) {
                // ========================
                // THROWABLE
                // ========================
                assertException((Throwable) original, recompiled);
            } else if (type == Class.class) {
                // ========================
                // Class
                // ========================
                assertClass((Class) original, recompiled);
            } else {
                // some object

                // If this exception will be thrown, it is bug of this program. So we must rethrow
                // the wrapped error in here.
                throw new Error(recompiled.getClass() + " " + original.getClass() + "  " + original + "  " + recompiled);
            }
        }
    }

    /**
     * Assert each items in array.
     * 
     * @param java
     * @param js
     */
    private void assertArray(Object original, Object recompiled) {
        // check array size
        assert Array.getLength(original) == Array.getLength(recompiled);

        // check each items
        int size = Array.getLength(original);

        for (int index = 0; index < size; index++) {
            assertObject(Array.get(original, index), Array.get(recompiled, index));
        }
    }

    /**
     * Assert the specified javascript object is exception.
     * 
     * @param exception
     * @param js
     */
    private void assertException(Throwable exception, Object js) {
        // An expected error (not native Error object) was thrown. This is successful.
        if (js instanceof String) {
            String message = exception.getMessage();

            if (message == null) {
                assert js.equals("");
            } else {
                assert js.equals(message);
            }
        } else {
            // Some error object was thrown certainly, but we cant check in detail.
        }
    }

    /**
     * @version 2018/10/09 12:16:56
     */
    private static class Verifiable<T> {

        /** The target class to verify. */
        private final Class<T> type;

        /** The instant builder. */
        private final Supplier<T> instantiator;

        /** The verifier. */
        private final Method method;

        /**
         * @param type
         */
        private Verifiable(Class<T> type) {
            this.type = type;
            this.instantiator = instantiator();
            this.method = searchInvocation();
        }

        /**
         * Create instantiator.
         * 
         * @param type
         * @return
         */
        private Supplier<T> instantiator() {
            Constructor constructor = type.getDeclaredConstructors()[0];
            constructor.setAccessible(true);

            return () -> {
                try {
                    return (T) constructor.newInstance((Object[]) Array.newInstance(Object.class, constructor.getParameterCount()));
                } catch (Exception e) {
                    throw I.quiet(e);
                }
            };
        }

        /**
         * Search invocation.
         * 
         * @param code
         * @return
         */
        private Method searchInvocation() {
            // search method
            for (Method method : type.getDeclaredMethods()) {
                if (method.getName().equals("run")) {
                    method.setAccessible(true);

                    return method;
                }
            }

            // If this exception will be thrown, it is bug of this program. So we must rethrow the
            // wrapped error in here.
            throw new Error("Verifiable class must implement run method.");
        }

        private Object invoke(Object param) {
            return 
        }
    }

    /**
     * @version 2018/10/04 8:48:47
     */
    private static class Silent extends UserInterface {

        /** The message buffer. */
        private StringBuilder message = new StringBuilder();

        /**
         * {@inheritDoc}
         */
        @Override
        protected void write(String message) {
            this.message.append(message).append("\r\n");
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
}
