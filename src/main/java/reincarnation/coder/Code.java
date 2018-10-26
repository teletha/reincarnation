/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder;

/**
 * @version 2018/10/13 11:34:49
 */
public interface Code {

    /** The empty code. */
    Code Empty = coder -> {
    };

    /**
     * Write source code.
     * 
     * @param coder
     */
    void write(Coder coder);
}
