/*
 * Copyright (C) 2020 Reincarnation Development Team
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import bee.UserInterface;
import bee.api.Command;
import bee.util.JavaCompiler;
import kiss.I;
import kiss.WiseSupplier;
import kiss.Ⅱ;
import psychopath.Locator;
import reincarnation.TestCode.Param;
import reincarnation.coder.java.JavaCoder;
import reincarnation.coder.java.JavaCodingOption;

/**
 * @version 2018/10/09 16:01:55
 */
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
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(TestCode code) {
        JavaVerifier base = new JavaVerifier<>(I.pair(code.getClass(), IllegalCallerException::new));
        List inputs = prepareInputs(base.method);
        List expecteds = new ArrayList();

        for (Object input : inputs) {
            expecteds.add(base.verifier.apply(input));
        }

        // java decompiler
        JavaVerifier java = new JavaVerifier(recompile(code));

        for (int i = 0; i < inputs.size(); i++) {
            java.verify(inputs.get(i), expecteds.get(i));
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

    /**
     * Decompile and recompile code.
     * 
     * @param code
     * @return
     */
    private <T extends TestCode> Ⅱ<Class<T>, Supplier<Throwable>> recompile(T code) {
        Class target = code.getClass();

        JavaCodingOption options = new JavaCodingOption();
        options.writeMemberFromTopLevel = true;

        String decompiled = Reincarnation.rebirth(target, options);
        Silent notifier = new Silent();

        if (Debugger.isEnable()) {
            for (String line : format(decompiled)) {
                System.out.println(line);
            }
        }

        try {
            JavaCompiler compiler = new JavaCompiler(notifier);
            compiler.addSource(JavaCoder.computeName(target.getEnclosingClass()), decompiled);
            compiler.addClassPath(Locator.directory("target/test-classes"));

            ClassLoader loader = compiler.compile();
            Class<T> loadedClass = (Class<T>) loader.loadClass(JavaCoder.computeName(target));
            assert target != loadedClass; // load from different classloader

            return I.pair(loadedClass, () -> code(decompiled));
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
     * Show decompiled formated code.
     * 
     * @param code
     * @return
     */
    private Throwable code(String code) {
        return Failuer.type("Invalid Decompilation")
                .reason("=================================================")
                .reason(code)
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

        /** The error notifier. */
        private final Supplier<Throwable> detailError;

        /**
         * @param type
         * @param detailError
         */
        private JavaVerifier(Ⅱ<Class, Supplier<Throwable>> context) {
            this.detailError = context.ⅱ;

            // we smust create new instance for each parameters
            Constructor c = context.ⅰ.getDeclaredConstructors()[0];
            c.setAccessible(true);
            WiseSupplier instantiator = () -> c.newInstance((Object[]) Array.newInstance(Object.class, c.getParameterCount()));

            // search verifier method
            method = I.signal(context.ⅰ.getDeclaredMethods()).take(m -> m.getName().equals("run")).first().to().v;
            method.setAccessible(true);

            switch (method.getParameterCount()) {
            case 0:
                verifier = param -> {
                    try {
                        return method.invoke(instantiator.get());
                    } catch (InvocationTargetException e) {
                        return e.getCause();
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
                        return e.getCause();
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
                assert actual == null : detailError.get();
            } else {
                Class type = expected.getClass();

                if (type.isArray()) {
                    assertArray(expected, actual);
                } else if (Throwable.class.isAssignableFrom(type)) {
                    assert type.isInstance(actual);
                    assert Objects.equals(((Throwable) expected).getMessage(), ((Throwable) actual).getMessage());
                } else {
                    assert expected.equals(actual) : detailError.get();
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
            assert Array.getLength(expected) == Array.getLength(actual) : detailError.get();

            // check each items
            int size = Array.getLength(expected);

            for (int index = 0; index < size; index++) {
                assertObject(Array.get(expected, index), Array.get(actual, index));
            }
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