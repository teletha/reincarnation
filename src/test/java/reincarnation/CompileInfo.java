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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import reincarnation.Debugger.Printable;

public class CompileInfo {

    /** End of Line */
    private static final String EoL = "\r\n";

    /** Line Separator */
    private static final String SEPARATOR = "============================================================";

    /** The compiling class. */
    public final Class compilingClass;

    /** The compiler. */
    public CompilerType type;

    /** The decompiled code. */
    public String decompiled;

    /** The error message of compiler. */
    public final List<String> errorMessage = new ArrayList();

    /** The debug log of decompiler. */
    public StringBuilder decompilerDebugLog;

    public final List<Entry<Class, List<String>>> asmForJava = new ArrayList();

    public final List<Entry<Class, List<String>>> asmForECJ = new ArrayList();

    private final StringBuilder builder = new StringBuilder();

    /**
     * @param target
     */
    public CompileInfo(Class target, CompilerType type) {
        this.compilingClass = target;
        this.type = type;
    }

    /**
     * @param e
     * @return
     */
    public Error buildError(Throwable e) {
        write("");
        write(type, "fails compiling", compilingClass.getName());
        write(errorMessage);
        write(format(decompiled, true));

        write("Decompiling Log");
        write(Printable.unstain(decompilerDebugLog.toString()));

        for (int i = 0; i < asmForJava.size(); i++) {
            Entry<Class, List<String>> javac = asmForJava.get(i);
            Entry<Class, List<String>> ecj = asmForECJ.get(i);

            write("Javac Version Bytecode -", javac.getKey().getName());
            write(javac.getValue());

            write("ECJ Version Bytecode -", ecj.getKey().getName());
            write(ecj.getValue());
        }

        return new Error(builder.toString());
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
}
