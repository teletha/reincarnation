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

import org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler;

import psychopath.File;
import psychopath.Locator;

public class FernFlowerDecompiler extends Decompiler {

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(File input, File output) {
        ConsoleDecompiler decompiler = new ConsoleDecompiler(Locator.temporaryDirectory().asJavaFile(), null, null);
    }
}
