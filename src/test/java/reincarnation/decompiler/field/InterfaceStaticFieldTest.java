/*
 * Copyright (C) 2016 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.field;

import org.junit.jupiter.api.Test;

import reincarnation.Code;
import reincarnation.CodeVerifier;
import reincarnation.Debuggable;

/**
 * @version 2018/10/23 15:49:59
 */
class InterfaceStaticFieldTest extends CodeVerifier {

    @Test
    void interfaceAccess() {
        verify(new Code.Text() {

            @Debuggable
            @Override
            public String run() {
                return Interface.NAME;
            }
        });
    }

    @Test
    @SuppressWarnings("static-access")
    void instanceAccess() {
        verify(new Code.Text() {

            @Override
            public String run() {
                Clazz clazz = new Clazz();
                return clazz.NAME;
            }
        });
    }

    /**
     * @version 2018/10/23 15:51:48
     */
    private static interface Interface {
        String NAME = "TEST".substring(1);
    }

    /**
     * @version 2018/10/23 15:51:51
     */
    private static class Clazz implements Interface {
    }
}
