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

import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

class SignatureTest {

    @Test
    void testName() {
        Signature signature = Signature.parse("(Ljava/util/function/Supplier<Ljava/lang/String;>;)V");
        assert signature.returnType == void.class;
        assert signature.exceptionType == null;
        assert signature.parameterTypes.size() == 1;
        assert signature.parameterized(0);
    }

    private static void run(Supplier<String> a) {
    }
}
