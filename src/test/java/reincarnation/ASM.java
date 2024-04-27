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

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.TraceClassVisitor;

import kiss.I;

public class ASM {

    /** The translated codes. */
    public final Map<Class, String[]> asmifiers = new LinkedHashMap();

    /**
     * Traslate codes recursively.
     * 
     * @param target
     * @return
     */
    public ASM translate(Class target) {
        try {
            InputStream input = target.getClassLoader().getResourceAsStream(target.getName().replace('.', '/') + ".class");
            Translator translator = new Translator(target);
            new ClassReader(input).accept(new TraceClassVisitor(null, translator, new PrintWriter(translator.writer)), 0);

            asmifiers.put(target, translator.format());
        } catch (IOException e) {
            throw I.quiet(e);
        }

        return this;
    }

    /**
     * Actual code translator.
     * 
     */
    private class Translator extends ASMifier {

        private final StringWriter writer = new StringWriter();

        private final Class target;

        public Translator(Class target) {
            this(Opcodes.ASM9, "ASMWriter", 0, target);
        }

        /**
         * @param api
         * @param visitorVariableName
         * @param annotationVisitorId
         */
        private Translator(int api, String visitorVariableName, int annotationVisitorId, Class target) {
            super(api, visitorVariableName, annotationVisitorId);
            this.target = target;
        }

        /**
         * Format code.
         * 
         * @return
         */
        private String[] format() {
            return I.signal(writer.toString().split("\n"))
                    .skip(line -> line.startsWith("import ") || line.startsWith("package "))
                    .skip(line -> line.startsWith("ClassWriter ") || line.startsWith("FieldVisitor ") || line
                            .startsWith("RecordComponentVisitor ") || line
                                    .startsWith("MethodVisitor ") || line.startsWith("AnnotationVisitor "))
                    .skip(line -> line.startsWith("classWriter.visitSource") || line.startsWith("classWriter.visitNestHost") || line
                            .startsWith("classWriter.visitNestMember") || line
                                    .startsWith("classWriter.visitOuterClass") || line.startsWith("classWriter.visitInnerClass"))
                    .skip(line -> line.startsWith("classWriter.visitEnd") || line.startsWith("return classWriter.toByteArray"))
                    .skip("", (prev, next) -> prev.isBlank() && next.isBlank())
                    .toList()
                    .toArray(new String[0]);
        }

        /**
         * Constructs a new {@link ASMifier}.
         *
         * @param visitorVariableName the name of the visitor variable in the produced code.
         * @param annotationVisitorId identifier of the annotation visitor variable in the produced
         *            code.
         * @return a new {@link ASMifier}.
         */
        @Override
        protected Translator createASMifier(final String visitorVariableName, final int annotationVisitorId) {
            return new Translator(api, visitorVariableName, annotationVisitorId, target);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitInnerClass(String name, String outerName, String innerName, int access) {
            super.visitInnerClass(name, outerName, innerName, access);

            if (outerName == null && innerName != null) {
                name = name.replace('/', '.');
                if (!name.equals(target.getName())) {
                    try {
                        translate(Class.forName(name, false, target.getClassLoader()));
                    } catch (ClassNotFoundException e) {
                        throw I.quiet(e);
                    }
                }
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitParameter(String parameterName, int access) {
            super.visitParameter(Objects.requireNonNullElse(parameterName, "NoParameterName"), access);
        }
    }
}
