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

import reincarnation.coder.js.Unnecessary;

/**
 * A class which must not be included even if it is referenced from the bytecode.
 */
@Unnecessary
public class UnnecessaryClass {

    /**
     * @return A value.
     */
    public static int value() {
        return 9;
    }
}
