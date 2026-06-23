/*
 * Copyright (C) 2026 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Function;

import kiss.I;
import kiss.WiseSupplier;

/**
 * @version 2018/10/09 12:16:56
 */
public class JavaVerifier<T> implements Verifier {

    /** The verifier. */
    public final Function verifier;

    /** The actual verifier method. */
    public final Method method;

    public JavaVerifier(Class clazz) {
        // we smust create new instance for each parameters
        Constructor c = clazz.getDeclaredConstructors()[0];
        c.setAccessible(true);
        WiseSupplier instantiator = () -> c.newInstance((Object[]) Array.newInstance(Object.class, c.getParameterCount()));

        // search verifier method
        method = I.signal(clazz.getDeclaredMethods()).take(m -> m.getName().equals("run")).first().to().v;
        method.setAccessible(true);

        switch (method.getParameterCount()) {
        case 0:
            verifier = param -> {
                try {
                    return method.invoke(instantiator.get());
                } catch (InvocationTargetException e) {
                    Throwable cause = e.getCause();
                    if (needRethrow(clazz, cause)) {
                        throw I.quiet(cause);
                    } else {
                        return cause;
                    }
                } catch (Throwable e) {
                    return e;
                }
            };
            break;

        case 1:
            verifier = param -> {
                try {
                    return method.invoke(instantiator.get(), param);
                } catch (InvocationTargetException e) {
                    Throwable cause = e.getCause();
                    if (needRethrow(clazz, cause)) {
                        throw I.quiet(cause);
                    } else {
                        return cause;
                    }
                } catch (Throwable e) {
                    return e;
                }
            };
            break;

        default:
            // If this exception will be thrown, it is bug of this program. So we must rethrow
            // the wrapped error in here.
            throw new Error(getClass().getSimpleName() + " don't support for multiple parameters.");
        }
    }

    private boolean needRethrow(Class clazz, Throwable error) {
        return error instanceof AssertionError && !clazz.getEnclosingClass().getSimpleName().equals("AssertionTest");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void verify(Object param, Object expectedResult) {
        assertObject(expectedResult, verifier.apply(param));
    }

    /**
     * Assert that java object equals to javascript object.
     * 
     * @param expected
     * @param actual
     */
    private void assertObject(Object expected, Object actual) {
        if (expected == null) {
            if (actual instanceof Throwable) {
                ((Throwable) actual).printStackTrace();
            }
            assert actual == null;
        } else if (actual == null) {
            throw new AssertionError("Expected value is " + expected + ", but actual is " + actual);
        } else {
            Class type = expected.getClass();

            if (type.isArray()) {
                assertArray(expected, actual);
            } else if (Throwable.class.isAssignableFrom(type)) {
                assert type.isInstance(actual);
                assert Objects.equals(((Throwable) expected).getMessage(), ((Throwable) actual).getMessage());
            } else {
                assert expected.equals(actual);
            }
        }
    }

    /**
     * Assert each items in array.
     * 
     * @param expected
     * @param actual
     */
    private void assertArray(Object expected, Object actual) {
        // check array size
        assert Array.getLength(expected) == Array.getLength(actual);

        // check each items
        int size = Array.getLength(expected);

        for (int index = 0; index < size; index++) {
            assertObject(Array.get(expected, index), Array.get(actual, index));
        }
    }
}