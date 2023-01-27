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

import reincarnation.Reincarnation;
import reincarnation.coder.java.JavaCodingOption;

public class ReincarnationDecompiler implements Decompiler {

    /**
     * {@inheritDoc}
     */
    @Override
    public String decompile(Class target) {
        JavaCodingOption options = new JavaCodingOption();
        options.writeMemberFromTopLevel = true;

        return Reincarnation.rebirth(target, options);
    }
}
