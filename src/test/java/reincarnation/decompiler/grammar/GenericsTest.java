/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.grammar;

import java.util.List;

import org.junit.jupiter.api.Test;

import reincarnation.CodeVerifier;
import reincarnation.TestCode;

class GenericsTest extends CodeVerifier {

    @Test
    void parameterizedType() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return size(List.of("1"));
            }

            private int size(List<String> param) {
                return param.get(0).length();
            }
        });
    }

    @Test
    void extendType() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return size(List.of("1"));
            }

            private int size(List<? extends CharSequence> param) {
                return param.get(0).length();
            }
        });
    }

    @Test
    void superType() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return size(List.of("1"));
            }

            private int size(List<? super CharSequence> param) {
                return ((CharSequence) param.get(0)).length();
            }
        });
    }

    @Test
    void wildcard() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return size(List.of("1"));
            }

            private int size(List<?> param) {
                return ((String) param.get(0)).length();
            }
        });
    }
}
