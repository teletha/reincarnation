/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

public class MavenEnvironmentCondition implements ExecutionCondition {

    /**
     * {@inheritDoc}
     */
    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        boolean isMaven = System.getProperty("maven.home") != null || System.getProperty("maven.multiModuleProjectDirectory") != null;

        if (isMaven) {
            return ConditionEvaluationResult.disabled("Not executable in Maven");
        } else {
            return ConditionEvaluationResult.enabled("Executable in Maven");
        }
    }
}
