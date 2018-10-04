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

import java.util.stream.IntStream;

import org.junit.platform.commons.util.ReflectionUtils;

import bee.UserInterface;
import bee.api.Command;
import bee.util.JavaCompiler;
import kiss.I;
import reincarnation.Code.Int;
import reincarnation.Code.IntParam;

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

        for (int param : params) {
            assert code.run(param) == recompile(code).run(param);
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

            return (T) ReflectionUtils.newInstance(loadedClass);
        } catch (Exception e) {
            throw I.quiet(e);
        } catch (Error e) {
            throw Failuer.type("Compile Error")
                    .reason("=================================================")
                    .reason(notifier.buffer)
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
        private StringBuilder buffer = new StringBuilder();

        /**
         * {@inheritDoc}
         */
        @Override
        protected void write(String message) {
            buffer.append(message);
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
