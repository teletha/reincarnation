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
import java.util.List;
import java.util.Map.Entry;

import kiss.Variable;
import reincarnation.Debugger.Printable;

public class CompileInfo {

    private static final String EOL = "\r\n";

    /** The compiling class. */
    public final Class compilingClass;

    /** The compiler. */
    public CompilerType type;

    /** The decompiled code. */
    public String decompiled;

    /** The error message of compiler. */
    public final Variable<String> compilerErrorMessage = Variable.empty();

    /** The error message of compiler. */
    public final Variable<String> verificationErrorMessage = Variable.empty();

    /** The debug log of decompiler. */
    public StringBuilder decompilerDebugLog;

    public final List<Entry<Class, String[]>> asmForJava = new ArrayList();

    public final List<Entry<Class, String[]>> asmForECJ = new ArrayList();

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
        String separator = "============================================================";

        StringBuilder builder = new StringBuilder().append(EOL);
        builder.append(separator).append(EOL);
        builder.append("Test Class \t: ").append("XXXX").append(EOL);
        builder.append("Test Method \t: ").append("YYY").append(EOL);
        builder.append("Failed Compiler \t: " + type).append(EOL);
        builder.append("Compiled Type \t: " + compilingClass.getName()).append(EOL);
        if (compilerErrorMessage.isPresent()) {
            builder.append("Reason \t\t: Failed to compile the following decompiled code.").append(EOL);
            builder.append(compilerErrorMessage.get());
        } else {
            builder.append("Reason \t\t: There are different results between original code and the following decompiled code.").append(EOL);
        }
        builder.append(separator).append(EOL);
        for (String line : format(decompiled, true)) {
            builder.append(line).append(EOL);
        }
        builder.append(EOL).append(separator).append(EOL);
        builder.append("Decompiling Log").append(EOL);
        builder.append(separator).append(EOL);
        builder.append(Printable.unstain(decompilerDebugLog.toString())).append(EOL);
        builder.append(separator).append(EOL);

        for (int i = 0; i < asmForJava.size(); i++) {
            Entry<Class, String[]> javac = asmForJava.get(i);
            Entry<Class, String[]> ecj = asmForECJ.get(i);
            builder.append("Javac Version Bytecode - ").append(javac.getKey().getName()).append(EOL);
            builder.append(separator).append(EOL);
            for (String line : javac.getValue()) {
                builder.append(line).append(EOL);
            }
            builder.append(EOL).append(separator).append(EOL);
            builder.append("ECJ Version Bytecode - ").append(ecj.getKey().getName()).append(EOL);
            builder.append(separator).append(EOL);
            for (String line : ecj.getValue()) {
                builder.append(line).append(EOL);
            }
            builder.append(EOL).append(separator).append(EOL);
        }

        return new Error(builder.toString());
    }

    /**
     * Format as line-numbered code.
     * 
     * @param code
     * @return
     */
    private String[] format(String code, boolean enableLineNumber) {
        String[] lines = code.split(EOL);

        if (enableLineNumber) {
            int max = (int) (Math.log10(lines.length) + 1);

            for (int i = 0; i < lines.length; i++) {
                int number = i + 1;
                int size = (int) (Math.log10(number) + 1);
                lines[i] = "0".repeat(max - size) + number + "    " + lines[i];
            }
        }
        return lines;
    }
}
