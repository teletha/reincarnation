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

import org.junit.platform.commons.util.ReflectionUtils;

import bee.UserInterface;
import bee.api.Command;
import bee.util.JavaCompiler;
import kiss.I;
import reincarnation.Code.Int;

/**
 * @version 2018/10/04 8:49:25
 */
public class CodeVerifier {

    /**
     * Verify decompiled code.
     * 
     * @param code
     */
    protected final void verify(Int code) {
        assert code.run() == recompile(code).run();
    }

    /**
     * Recompile and recompile code.
     * 
     * @param code
     * @return
     */
    private <T extends Code> T recompile(T code) {
        try {
            String decompiled = Reincarnation.exhume(code.getClass()).toString();

            JavaCompiler compiler = new JavaCompiler(new NoOP());
            compiler.addSource(code.getSimpleName(), decompiled);
            compiler.addCurrentClassPath();

            ClassLoader loader = compiler.compile();
            Class<?> loadedClass = loader.loadClass(code.getName());
            assert loadedClass != code.getClass(); // load from different class loaders

            return (T) ReflectionUtils.newInstance(loadedClass);
        } catch (Exception e) {
            throw I.quiet(e);
        }
    }

    /**
     * @version 2018/10/04 8:48:47
     */
    private static class NoOP extends UserInterface {

        /**
         * {@inheritDoc}
         */
        @Override
        protected void write(String message) {
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
