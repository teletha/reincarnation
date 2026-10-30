/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder.js;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

/**
 * Test the external registration of the class library.
 */
class ClasslibRegistrationTest {

    @Test
    void register() throws Exception {
        Classlib.register(Source.class, Implementation.class);
        assertSame(Implementation.class, Classlib.replace(Source.class));
        assertSame(Source.class, Classlib.origin(Implementation.class));
        assertTrue(Classlib.isReplaced(Source.class));

        Method method = Classlib.resolve(Source.class.getDeclaredMethod("value"));
        assertEquals(Implementation.class, method.getDeclaringClass());
        assertEquals("value", method.getName());

        Field field = Classlib.resolve(Source.class.getDeclaredField("value"));
        assertEquals(Implementation.class, field.getDeclaringClass());
        assertEquals("value", field.getName());
    }

    /** The source type. */
    static class Source {

        /** The value. */
        int value;

        /**
         * Get the value.
         * 
         * @return A value.
         */
        int value() {
            return value;
        }
    }

    /** The implementation type. */
    static class Implementation {

        /** The value. */
        int value;

        /**
         * Get the value.
         * 
         * @return A value.
         */
        int value() {
            return value;
        }
    }
}
