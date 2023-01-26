/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler;

import java.util.Objects;

import psychopath.Directory;
import psychopath.File;
import psychopath.Locator;

public abstract class Decompiler {

    /**
     * Decompiler the target class file.
     * 
     * @param input
     */
    public final String decompile(Class input) {
        return decompile(Locator.locate(input).asDirectory().file(input.getName().replace('.', '/').concat(".class")));
    }

    /**
     * Decompiler the target class file.
     * 
     * @param input
     */
    public final String decompile(File input) {
        File output = Locator.temporaryFile();
        decompile(input, output);
        return output.text();
    }

    /**
     * Decompiler the target class file.
     * 
     * @param input
     * @param output
     */
    public final void decompile(File input, File output) {
        Objects.requireNonNull(input);
        Objects.requireNonNull(output);

        execute(input, output);
    }

    /**
     * Decompiler the target class file.
     * 
     * @param input
     * @param output
     */
    public final void decompile(Directory input, Directory output) {
        input.walkFile("**.class").to(file -> {
            decompile(file, output.file(input.relativize(file)).extension("java"));
        });
    }

    /**
     * Decompiler the target class file.
     * 
     * @param input
     * @param output
     */
    protected abstract void execute(File input, File output);
}
