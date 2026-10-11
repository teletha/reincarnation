/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.grammar;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the sealed class declarations.
 * 
 * @version 2026/10/11 13:30:00
 */
class SealedClassTest extends JavascriptCodeVerifier {

    @Test
    void sealed() throws Exception {
        verify(new Sealed());
    }

    @Test
    void negate() throws Exception {
        verify(new Negate());
    }

    /** The sealed class. */
    private static sealed class Sealed implements TestCode.Boolean permits SealedChild {

        @Override
        public boolean run() {
            return this instanceof Sealed;
        }
    }

    /** The permitted subclass. */
    private static final class SealedChild extends Sealed {
    }

    /** The sealed class with negated instanceof. */
    private static sealed class Negate implements TestCode.Boolean permits NegateChild {

        @Override
        public boolean run() {
            return !(this instanceof Negate);
        }
    }

    /** The permitted subclass. */
    private static final class NegateChild extends Negate {
    }
}