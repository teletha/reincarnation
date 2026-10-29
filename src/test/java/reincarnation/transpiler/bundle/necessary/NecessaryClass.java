/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.bundle.necessary;

import reincarnation.coder.js.Necessary;

/**
 * A class which must be included even if it is not referenced from the bytecode.
 */
@Necessary
public class NecessaryClass {

    /**
     * @return A value.
     */
    public static int value() {
        return 7;
    }
}
