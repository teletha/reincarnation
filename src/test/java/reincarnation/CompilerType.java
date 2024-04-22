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

import java.util.List;

import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;

public enum CompilerType implements TestTemplateInvocationContext {

    Javac, ECJ;

    static final ThreadLocal<CompilerType> current = ThreadLocal.withInitial(() -> ECJ);

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayName(int invocationIndex) {
        return name();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Extension> getAdditionalExtensions() {
        return List.of(new BeforeTestExecutionCallback() {

            @Override
            public void beforeTestExecution(ExtensionContext context) throws Exception {
                current.set(CompilerType.this);
            }
        });
    }
}
