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
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.TraceClassVisitor;

import kiss.I;

public class ASM {

    /** The translated codes. */
    public final Map<Class, List<String>> asmifiers = new LinkedHashMap();

    private boolean full;

    /**
     * @param full
     */
    public ASM(boolean full) {
        this.full = full;
    }

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
            new ClassReader(input).accept(new Filter(new TraceClassVisitor(null, translator, new PrintWriter(translator.writer))), 0);

            asmifiers.put(target, translator.format());
        } catch (IOException e) {
            throw I.quiet(e);
        }

        return this;
    }

    private class Filter extends ClassVisitor {

        /**
         * @param classVisitor
         */
        public Filter(ClassVisitor classVisitor) {
            super(Opcodes.ASM9, classVisitor);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            if (!full && (name.equals("<init>") || name.equals("<cinit>"))) {
                return null;
            } else {
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        }
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
        private List<String> format() {
            return I.signal(writer.toString().split("\n"))
                    .take(line -> line.startsWith("methodVisitor"))
                    .flatMap(line -> line.startsWith("methodVisitor.") ? I.signal(line) : I.signal("", line))
                    .skip(1)
                    .skip(line -> line.startsWith("methodVisitor.visitCode()") || line.startsWith("methodVisitor.visitEnd()") || line
                            .startsWith("methodVisitor.visitMax") || line.startsWith("methodVisitor.visitLineNumber"))
                    .map(line -> line.replace("methodVisitor.visit", "").replace("methodVisitor = classWriter.", ""))
                    .map(line -> line.replaceAll("reincarnation/decompiler/.+/", "").replaceAll("java/lang/", "j.l."))
                    .toList();
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
