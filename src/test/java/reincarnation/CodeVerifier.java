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

import java.lang.reflect.Constructor;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import bee.UserInterface;
import bee.api.Command;
import bee.util.JavaCompiler;
import kiss.I;
import reincarnation.Code.FloatParam;
import reincarnation.Code.Int;
import reincarnation.Code.IntParam;
import reincarnation.Code.LongParam;

/**
 * @version 2018/10/04 14:37:22
 */
public class CodeVerifier {

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Int code) {
        assert code.run() == recompile(code).run();
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(IntParam code) {
        int[] params = IntStream.range(-5, 5).toArray();
        IntParam recompiled = recompile(code);

        for (int param : params) {
            assert code.run(param) == recompiled.run(param);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Long code) {
        assert code.run() == recompile(code).run();
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.LongParam code) {
        long[] params = LongStream.range(-5, 5).toArray();
        LongParam recompiled = recompile(code);

        for (long param : params) {
            assert code.run(param) == recompiled.run(param);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Float code) {
        assert code.run() == recompile(code).run();
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.FloatParam code) {
        long[] params = LongStream.range(-5, 5).toArray();
        FloatParam recompiled = recompile(code);

        for (long param : params) {
            assert code.run(param) == recompiled.run(param);
        }
    }

    /**
     * Recompile and recompile code.
     * 
     * @param code
     * @return
     */
    private <T extends Code> T recompile(T code) {
        Silent notifier = new Silent();
        String decompiled = Reincarnation.exhume(code.getClass()).toString();

        try {
            JavaCompiler compiler = new JavaCompiler(notifier);
            compiler.addSource(code.getSimpleName(), decompiled);
            compiler.addCurrentClassPath();

            ClassLoader loader = compiler.compile();
            Class<?> loadedClass = loader.loadClass(code.getName());
            assert loadedClass != code.getClass(); // load from different class loaders

            Constructor<?> constructor = loadedClass.getDeclaredConstructor(getClass());
            constructor.setAccessible(true);
            return (T) constructor.newInstance(this);
        } catch (Exception e) {
            throw I.quiet(e);
        } catch (Error e) {
            throw Failuer.type("Compile Error")
                    .reason("=================================================")
                    .reason(notifier.message)
                    .reason("-------------------------------------------------")
                    .reason(format(decompiled))
                    .reason("=================================================");
        }
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
            this.message.append(message);
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
