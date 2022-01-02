/*
 * Copyright (C) 2021 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.transpiler.js;

import static viewtify.ui.UIWeb.Operation.script;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.concurrent.ArrayBlockingQueue;

import javafx.application.Platform;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import kiss.I;
import kiss.Variable;
import psychopath.File;
import psychopath.Locator;
import viewtify.Viewtify;
import viewtify.ui.UIWeb;

public class JavascriptTestSupport {

    /** The maximum size of pooled browsers. */
    private static final int MaxPoolSize = 4;

    /** The headless browser pool. */
    private static final ArrayBlockingQueue<Variable<UIWeb>> Browsers = new ArrayBlockingQueue(MaxPoolSize);

    static {
        Viewtify.inHeadless();

        for (int i = 0; i < MaxPoolSize; i++) {
            Variable<UIWeb> browser = Variable.empty();
            Viewtify.browser(browser::set);
            Browsers.add(browser);
        }
    }

    /** The current processing browser holder. */
    private Variable<UIWeb> browser;

    @BeforeEach
    void setup() throws InterruptedException {
        browser = Browsers.take();
    }

    @AfterEach
    void cleanup() throws InterruptedException {
        Browsers.put(browser);
    }

    public static File locate(Class source) {
        Class root = source;
        while (root.isMemberClass()) {
            root = root.getEnclosingClass();
        }

        File file = Locator.file("src/test/java/" + root.getName().replace('.', '/') + ".java");
        System.out.println(file + "   " + file.isPresent());
        return file;
    }

    protected boolean runJS(String code, Object expected) {
        Variable result = Variable.empty();

        Platform.runLater(() -> browser.acquire()
                .load("<!DOCTYPE html><meta charset=utf-8><title></title>")
                .$(script(code))
                .to(result::set));

        Object executionResult = result.acquire();

        assert executionResult.equals(expected);

        return true;
    }

    /**
     * Execute the given code.
     * 
     * @param type An expected result value type.
     * @param code A target code to exeute.
     * @return A execution result.
     */
    protected final <T> T executeAs(Class<T> type, String code) {
        Object result = execute(code);

        if (type == long.class || type == Long.class) {
            System.out.println(result.getClass());
            return (T) (Long) new BigDecimal(result.toString()).longValue();
        } else if (type == double.class || type == double.class) {
            return (T) (Double) new BigDecimal(result.toString()).doubleValue();
        }

        return I.transform(result, type);
    }

    /**
     * Execute the given code.
     * 
     * @param code A target code to exeute.
     * @return A execution result.
     */
    protected final Object execute(String code) {
        Variable result = Variable.empty();

        Platform.runLater(() -> browser.acquire()
                .load("<!DOCTYPE html><meta charset=utf-8><title></title>")
                .$(script(code))
                .to(result::set));

        return result.acquire();
    }

    /**
     * Assert that java object equals to javascript object.
     * 
     * @param java
     * @param js
     */
    private void assertObject(Object java, Object js) {
        if (js.getClass().getSimpleName().equals("NativeError")) {
            // Internal javascript error was thrown (e.g. invalid syntax error, property was not
            // found). So we must report as error for developer's feedback.
            throw new Error(js.toString());
        }

        Class type = java.getClass();

        if (type.isArray()) {
            assertArray(java, js);
        } else if (type == Integer.class) {
            // ========================
            // INT
            // ========================
            int value = ((Integer) java).intValue();

            if (js instanceof Double) {
                assert value == ((Double) js).intValue();
            } else if (js instanceof Long) {
                assert value == ((Long) js).intValue();
            } else {
                assert value == ((Integer) js).intValue();
            }
        } else if (type == Long.class) {
            // ========================
            // LONG
            // ========================
            long value = ((Long) java).longValue();

            long jsValue = createLong(js);
            assert value == jsValue;
        } else if (type == Float.class) {
            // ========================
            // FLOAT
            // ========================
            java = new BigDecimal((Float) java).round(new MathContext(3));
            js = new BigDecimal((Double) js).round(new MathContext(3));

            assert java.equals(js);
        } else if (type == Double.class) {
            // ========================
            // DOUBLE
            // ========================
            java = new BigDecimal((Double) java).round(new MathContext(3));
            js = new BigDecimal((Double) js).round(new MathContext(3));

            assert java.equals(js);
        } else if (type == Short.class) {
            // ========================
            // SHORT
            // ========================
            assert ((Short) java).doubleValue() == ((Double) js).doubleValue();
        } else if (type == Byte.class) {
            // ========================
            // BYTE
            // ========================
            assert ((Byte) java).doubleValue() == ((Double) js).doubleValue();
        } else if (type == Boolean.class) {
            // ========================
            // BOOLEAN
            // ========================
            if (js instanceof Double) {
                js = ((Double) js).intValue() != 0;
            }
            assert java.equals(js);
        } else if (type == String.class) {
            // ========================
            // STRING
            // ========================
            assert js.toString().equals(java);
        } else if (type == Character.class) {
            // ========================
            // CHARACTER
            // ========================
            if (js instanceof Double) {
                // numeric characters (i.e. 0, 1, 2...)
                js = Character.valueOf((char) (((Double) js).intValue() + 48));
            }
            assert ((Character) java).toString().equals(js.toString());
        } else if (Throwable.class.isAssignableFrom(type)) {
            // ========================
            // THROWABLE
            // ========================
            assertException((Throwable) java, js);
        } else if (type == Class.class) {
            // ========================
            // Class
            // ========================
            assertClass((Class) java, js);
        } else {
            // some object

            // If this exception will be thrown, it is bug of this program. So we must rethrow
            // the wrapped error in here.
            throw new Error(js.getClass() + " " + java.getClass() + "  " + java + "  " + js);
        }
    }

    /**
     * <p>
     * Create primitive long from the specified javascript long object.
     * </p>
     * 
     * @param js A primitive long object in javascript runtime.
     * @return A primitive long in java runtime.
     */
    private long createLong(Object js) {
        // long high = Double.valueOf(js.get("h").toString()).longValue();
        // long low = Double.valueOf(js.get("l").toString()).longValue();
        // return high << 32 | (low & 0xffffffffL);
        throw new Error();
    }

    /**
     * Assert each items in array.
     * 
     * @param java
     * @param js
     */
    private void assertArray(Object java, Object js) {
        throw new Error();
    }

    /**
     * Assert the specified javascript object is exception.
     * 
     * @param exception
     * @param js
     */
    private void assertException(Throwable exception, Object js) {
        throw new Error();
    }

    /**
     * Assert the specified javascript object is Class.
     * 
     * @param exception
     * @param js
     */
    private void assertClass(Class clazz, Object js) {
        throw new Error();
    }
}
