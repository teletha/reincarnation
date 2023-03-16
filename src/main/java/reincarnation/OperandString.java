/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import java.util.List;

import reincarnation.coder.Coder;

class OperandString extends Operand {

    /** The actual string expression of this operand. */
    final String expression;

    /** The text type. */
    private final boolean textBlock;

    /**
     * Create String operand.
     */
    OperandString(String text) {
        fix(String.class);

        this.textBlock = speculateTextBlock(text);

        if (textBlock) {
            this.expression = text.replaceAll("\r\n", "\\\\r\\\\n");
        } else {
            this.expression = text.replaceAll("\\\\", "\\\\\\\\")
                    .replaceAll("\"", "\\\\\"")
                    .replaceAll("\r", "\\\\r")
                    .replaceAll("\n", "\\\\n");
        }
    }

    /**
     * Test whether the given value is text block or not.
     * 
     * @param text
     * @return
     */
    private boolean speculateTextBlock(String text) {
        return text.endsWith("\n");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        if (textBlock) {
            coder.writeTextBlock(List.of(expression.split("\n")));
        } else {
            coder.writeString(expression);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String info() {
        return "String";
    }
}