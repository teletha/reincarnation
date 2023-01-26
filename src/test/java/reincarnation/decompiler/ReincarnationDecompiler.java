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

import psychopath.File;
import psychopath.Location;
import reincarnation.Reincarnation;

public class ReincarnationDecompiler extends Decompiler {

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(File input, File output) {
        Location dir = input;
        String fqcn = input.name().replace(".class", "");

        while (true) {
            try {
                output.text(Reincarnation.rebirth(Class.forName(fqcn)));
                break;
            } catch (ClassNotFoundException e) {
                // try next
                dir = dir.parent();
                fqcn = dir.name() + "." + fqcn;
            }
        }
    }
}
