/*
 * Copyright (C) 2024 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import bee.UserInterface;
import bee.api.Command;
import bee.util.JavaCompiler;
import psychopath.Locator;

public class JavacEnvironment {

    public static void main(String[] args) {
        // compile source by javac
        Silent silent = new Silent();

        ClassLoader compiled = JavaCompiler.with(silent)
                .addCurrentClassPath()
                .addSourceDirectory(Locator.directory("src/test/java"))
                .compile();

        System.out.println(silent.message);
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
}
