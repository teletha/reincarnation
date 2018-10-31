/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.statement;

import reincarnation.coder.Code;
import reincarnation.coder.Coder;

/**
 * @version 2018/10/31 13:47:52
 */
public class Follows extends Statement {

    /** The code. */
    private final Code[] codes;

    /**
     * Statement.
     * 
     * @param codes
     */
    public Follows(Code... codes) {
        this.codes = codes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        for (Code code : codes) {
            if (code != null) {
                code.write(coder);
            }
        }
    }
}
