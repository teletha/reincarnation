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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

import org.jetbrains.java.decompiler.main.Fernflower;
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
import org.jetbrains.java.decompiler.main.extern.IFernflowerPreferences;
import org.jetbrains.java.decompiler.main.extern.IResultSaver;

import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;

import kiss.I;
import psychopath.File;
import psychopath.Location;
import psychopath.Locator;
import reincarnation.Debugger.Printable;

public class CompileInfo {

    /** End of Line */
    private static final String EoL = "\r\n";

    /** Line Separator */
    private static final String SEPARATOR = "============================================================";

    /** The source code cache. */
    private static final Map<String, Map<String, List<String>>> sources = new ConcurrentHashMap();

    /** The test method. */
    private final Method method;

    /** The compiling class. */
    public final Class compilingClass;

    /** The compiler. */
    public CompilerType type;

    /** The decompiled code. */
    public String decompiled;

    /** The decompiled code. */
    private List<String> decompiledByVineFlower;

    /** The error message of compiler. */
    public final List<String> errorMessage = new ArrayList();

    /** The debug log of decompiler. */
    public StringBuilder decompilerDebugLog;

    private final List<ASMified> asmfied = new ArrayList();

    private final StringBuilder builder = new StringBuilder();

    /**
     * @param target
     */
    public CompileInfo(Method method, Class target, CompilerType type) {
        this.method = method;
        this.compilingClass = target;
        this.type = type;
    }

    /**
     * Build the detailed decompiler log.
     * 
     * @return
     */
    public String buildMessage(boolean enableANCI) {
        builder.delete(0, builder.length());

        write("");
        write(type, "compiles", compilingClass.getName());
        write(errorMessage);

        write("Original Code");
        write(extractOriginalTestCode());

        write("Decompiled Code");
        write(format(decompiled, true));

        write("Decompiling Log");
        write(enableANCI ? decompilerDebugLog.toString() : Printable.unstain(decompilerDebugLog.toString()));

        for (ASMified asm : asmfied) {
            write("Bytecode Diff - ", asm.clazz.getName());
            write(asm.diff);
        }

        if (decompiledByVineFlower != null) {
            write("Decompiled by VineFlower");
            write(decompiledByVineFlower);
        }

        return builder.toString();
    }

    /**
     * Build error with detailed message.
     * 
     * @param originalError
     * 
     * @return
     */
    public Error buildError(Throwable originalError) {
        errorMessage.add(exactErrorMessage(originalError));

        Error error = new Error(buildMessage(false));
        error.setStackTrace(new StackTraceElement[0]);
        return error;
    }

    private String exactErrorMessage(Throwable error) {
        StringBuilder builder = new StringBuilder(error.getMessage()).append(EoL);

        I.signal(error.getStackTrace()).take(3).to(trace -> {
            builder.append("    at ").append(trace).append(EoL);
        });

        return builder.toString();
    }

    private List<String> extractOriginalTestCode() {
        return sources.computeIfAbsent(method.getDeclaringClass().getName(), className -> {
            Map<String, List<String>> methods = new HashMap();
            File source = Locator.file("src/test/java/" + method.getDeclaringClass().getName().replace('.', '/') + ".java");
            for (String block : source.text().split("@" + CrossDecompilerTest.class.getSimpleName())) {
                int start = block.indexOf("void");
                int end = block.indexOf("(", start);
                if (start != -1 && end != -1) {
                    String methodName = block.substring(start + 4, end).strip();
                    methods.put(methodName, List.of(block.strip().split(EoL)));
                }
            }
            return methods;
        }).get(method.getName());
    }

    public void asmfier(Class typeForJavac, Class typeForECJ) {
        boolean full = false;
        Debuggable debug = method.getAnnotation(Debuggable.class);
        if (debug != null) {
            full = debug.fullBytecode();
        }

        DiffRowGenerator generator = DiffRowGenerator.create()
                .showInlineDiffs(true)
                .inlineDiffByWord(true)
                .ignoreWhiteSpaces(true)
                .oldTag(f -> "~")
                .newTag(f -> "+")
                .build();

        ASM forJavac = new ASM(full).translate(typeForJavac);
        ASM forECJ = new ASM(full).translate(typeForECJ);

        Iterator<Entry<Class, List<String>>> javac = forJavac.asmifiers.entrySet().iterator();
        Iterator<Entry<Class, List<String>>> ecj = forECJ.asmifiers.entrySet().iterator();
        while (javac.hasNext() && ecj.hasNext()) {
            Entry<Class, List<String>> nextJ = javac.next();
            Entry<Class, List<String>> nextE = ecj.next();

            List<DiffRow> rows = generator.generateDiffRows(nextE.getValue(), nextJ.getValue());
            int maxE = rows.stream().mapToInt(row -> Debugger.calculateLineLength(row.getOldLine())).max().getAsInt();

            List<String> diff = new ArrayList();
            diff.add(Debugger.alignLine("ECJ", maxE) + "Javac");
            for (DiffRow row : rows) {
                diff.add(Debugger.alignLine(row.getOldLine(), maxE) + row.getNewLine());
            }

            asmfied.add(new ASMified(nextJ.getKey(), nextE.getValue(), nextJ.getValue(), diff));
        }
    }

    /**
     * Write line text.
     * 
     * @param text
     */
    private void write(List text) {
        if (text != null && !text.isEmpty()) {
            builder.append(text.stream().map(String::valueOf).collect(Collectors.joining(EoL))).append(EoL).append(SEPARATOR).append(EoL);
        }
    }

    /**
     * Write line text.
     * 
     * @param text
     */
    private void write(Object... text) {
        if (text != null && text.length != 0) {
            builder.append(Arrays.stream(text).map(String::valueOf).collect(Collectors.joining(" ")))
                    .append(EoL)
                    .append(SEPARATOR)
                    .append(EoL);
        }
    }

    /**
     * Format as line-numbered code.
     * 
     * @param code
     * @return
     */
    private List<String> format(String code, boolean enableLineNumber) {
        if (code == null) {
            return List.of();
        }

        String[] lines = code.split(EoL);

        if (enableLineNumber) {
            int max = (int) (Math.log10(lines.length) + 1);

            for (int i = 0; i < lines.length; i++) {
                int number = i + 1;
                int size = (int) (Math.log10(number) + 1);
                lines[i] = "0".repeat(max - size) + number + "    " + lines[i];
            }
        }
        return List.of(lines);
    }

    /**
     * Decompile by external decompiler.
     */
    public void decompileByVineFlower() {
        Map<String, Object> options = new HashMap();
        options.put(IFernflowerPreferences.REMOVE_SYNTHETIC, "1");
        options.put(IFernflowerPreferences.INDENT_STRING, "\t");
        options.put(IFernflowerPreferences.LOG_LEVEL, "WARN");

        String[] decompiled = new String[1];

        IResultSaver saver = new IResultSaver() {

            @Override
            public void saveFolder(String path) {
            }

            @Override
            public void saveDirEntry(String path, String archiveName, String entryName) {
            }

            @Override
            public void saveClassFile(String path, String qualifiedName, String entryName, String content, int[] mapping) {
                decompiled[0] = content;
            }

            @Override
            public void saveClassEntry(String path, String archiveName, String qualifiedName, String entryName, String content) {
            }

            @Override
            public void createArchive(String path, String archiveName, Manifest manifest) {
            }

            @Override
            public void copyFile(String source, String path, String entryName) {
            }

            @Override
            public void copyEntry(String source, String path, String archiveName, String entry) {
            }

            @Override
            public void closeArchive(String path, String archiveName) {
            }
        };

        IFernflowerLogger logger = new IFernflowerLogger() {

            /**
             * {@inheritDoc}
             */
            @Override
            public void writeMessage(String message, Severity severity) {
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void writeMessage(String message, Severity severity, Throwable t) {
            }
        };

        List<Class> classes = new ArrayList();
        classes.add(compilingClass);

        Class parent = compilingClass.getEnclosingClass();
        while (parent != null) {
            classes.add(parent);
            parent = parent.getEnclosingClass();
        }

        Fernflower fernflower = new Fernflower(saver, options, logger);
        for (Class clazz : classes) {
            Location root = Locator.locate(clazz);
            fernflower.addSource(root.asDirectory().file(clazz.getName().replace('.', '/').concat(".class")).asJavaFile());
        }
        fernflower.decompileContext();

        decompiledByVineFlower = I.list(decompiled[0].split("\n"));

        // format
        decompiledByVineFlower.removeIf(line -> {
            return line.startsWith("import ") || line.startsWith("package ");
        });
    }

    private record ASMified(Class clazz, List<String> ecj, List<String> javac, List<String> diff) {
    }
}
