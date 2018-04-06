/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import org.junit.platform.commons.util.ReflectionUtils;

import marionette.Browser;

/**
 * @version 2018/04/04 16:30:26
 */
public class CodeVerifier {

    /**
     * Verify AST of the specified {@link Code}.
     * 
     * @param code
     */
    protected final void verify(Code code) {
        Executions executionsFromJava = executeJavaCode(code);
        System.out.println(executionsFromJava);

        new Browser().configHeadless(true)
                .load("https://www.google.com/search?q=headless+chrome+java&ie=utf-8&oe=utf-8&client=firefox-b")
                .find("title")
                .to(v -> {
                    System.out.println(v);
                });
    }

    private Executions executeJavaCode(Code code) {
        Executions executions = new Executions();

        ReflectionUtils.findMethod(code.getClass(), "run").ifPresent(method -> {
            // runner needs return value
            Class<?> returnType = method.getReturnType();

            if (returnType == void.class || returnType == Void.class) {
                throw new AssertionError(CodeVerifier.class.getSimpleName() + " requires the method which returns non-void type.");
            }

            Parameter[] parameters = method.getParameters();

            if (parameters.length == 0) {
                // no parameter, execute simply
                executions.output.add(ReflectionUtils.invokeMethod(method, code, executions.params()));
            }
        });
        return executions;
    }

    /**
     * @version 2018/04/04 17:11:58
     */
    private static class Executions {

        /** The input container. */
        private final List<Object[]> input = new ArrayList();

        /** The output container. */
        private final List<Object> output = new ArrayList();

        /**
         * Record inputs.
         * 
         * @param params
         * @return
         */
        private Object[] params(Object... params) {
            this.input.add(params);

            return params;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return output.toString();
        }
    }
}
