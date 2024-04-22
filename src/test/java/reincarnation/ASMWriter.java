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

import java.util.Objects;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.ASMifier;

public class ASMWriter extends ASMifier {

    public ASMWriter() {
        this(Opcodes.ASM9, "ASMWriter", 0);
    }

    /**
     * @param api
     * @param visitorVariableName
     * @param annotationVisitorId
     */
    private ASMWriter(int api, String visitorVariableName, int annotationVisitorId) {
        super(api, visitorVariableName, annotationVisitorId);
    }

    /**
     * Constructs a new {@link ASMifier}.
     *
     * @param visitorVariableName the name of the visitor variable in the produced code.
     * @param annotationVisitorId identifier of the annotation visitor variable in the produced
     *            code.
     * @return a new {@link ASMifier}.
     */
    // DontCheck(AbbreviationAsWordInName): can't be renamed (for backward binary compatibility).
    @Override
    protected ASMWriter createASMifier(final String visitorVariableName, final int annotationVisitorId) {
        return new ASMWriter(api, visitorVariableName, annotationVisitorId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitParameter(String parameterName, int access) {
        super.visitParameter(Objects.requireNonNullElse(parameterName, "NoParameterName"), access);
    }
}
