/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.io;

import java.util.Locale;

import js.lang.NativeConsole;
import js.lang.NativeString;

/**
 * The Java implementation of {@link java.io.PrintStream} which is transpiled into JavaScript.
 */
public final class PrintStream {

    /** Whether the error stream is used. */
    private final boolean error;

    /**
     * Create a print stream.
     * 
     * @param error Whether the error stream is used.
     */
    public PrintStream(boolean error) {
        this.error = error;
    }

    /**
     * Print the value.
     * 
     * @param value A value.
     */
    public void print(Object value) {
        writeText(String.valueOf(value));
    }

    /**
     * Print the value.
     * 
     * @param value A value.
     */
    public void print(String value) {
        writeText(value);
    }

    /**
     * Print the value.
     * 
     * @param value A value.
     */
    public void print(boolean value) {
        writeText(String.valueOf(value));
    }

    /**
     * Print the value.
     * 
     * @param value A value.
     */
    public void print(char value) {
        writeText(String.valueOf(value));
    }

    /**
     * Print the value.
     * 
     * @param value A value.
     */
    public void print(int value) {
        writeText(String.valueOf(value));
    }

    /**
     * Print the value.
     * 
     * @param value A value.
     */
    public void print(long value) {
        writeText(String.valueOf(value));
    }

    /**
     * Print the value.
     * 
     * @param value A value.
     */
    public void print(float value) {
        writeText(String.valueOf(value));
    }

    /**
     * Print the value.
     * 
     * @param value A value.
     */
    public void print(double value) {
        writeText(String.valueOf(value));
    }

    /**
     * Print the characters.
     * 
     * @param value Characters.
     */
    public void print(char[] value) {
        String text = "";
        for (char ch : value) {
            text += ch;
        }
        writeText(text);
    }

    /**
     * Print the newline.
     */
    public void println() {
        writeText(NativeString.fromCharCode(10));
    }

    /**
     * Print the value and the newline.
     * 
     * @param value A value.
     */
    public void println(Object value) {
        print(value);
        println();
    }

    /**
     * Print the value and the newline.
     * 
     * @param value A value.
     */
    public void println(String value) {
        print(value);
        println();
    }

    /**
     * Print the value and the newline.
     * 
     * @param value A value.
     */
    public void println(boolean value) {
        print(value);
        println();
    }

    /**
     * Print the value and the newline.
     * 
     * @param value A value.
     */
    public void println(char value) {
        print(value);
        println();
    }

    /**
     * Print the value and the newline.
     * 
     * @param value A value.
     */
    public void println(int value) {
        print(value);
        println();
    }

    /**
     * Print the value and the newline.
     * 
     * @param value A value.
     */
    public void println(long value) {
        print(value);
        println();
    }

    /**
     * Print the value and the newline.
     * 
     * @param value A value.
     */
    public void println(float value) {
        print(value);
        println();
    }

    /**
     * Print the value and the newline.
     * 
     * @param value A value.
     */
    public void println(double value) {
        print(value);
        println();
    }

    /**
     * Print the characters and the newline.
     * 
     * @param value Characters.
     */
    public void println(char[] value) {
        print(value);
        println();
    }

    /**
     * Print the formatted text.
     * 
     * @param format A format.
     * @param args Arguments.
     * @return This stream.
     */
    public PrintStream printf(String format, Object... args) {
        return format(format, args);
    }

    /**
     * Print the formatted text.
     * 
     * @param locale A locale.
     * @param format A format.
     * @param args Arguments.
     * @return This stream.
     */
    public PrintStream printf(Locale locale, String format, Object... args) {
        return format(format, args);
    }

    /**
     * Print the formatted text.
     * 
     * @param format A format.
     * @param args Arguments.
     * @return This stream.
     */
    public PrintStream format(String format, Object... args) {
        writeText(NativeString.format(format, args));
        return this;
    }

    /**
     * Print the formatted text.
     * 
     * @param locale A locale.
     * @param format A format.
     * @param args Arguments.
     * @return This stream.
     */
    public PrintStream format(Locale locale, String format, Object... args) {
        return format(format, args);
    }

    /**
     * Write the character.
     * 
     * @param value A character code.
     */
    public void write(int value) {
        writeText(NativeString.fromCharCode(value));
    }

    /**
     * Write the bytes.
     * 
     * @param values Bytes.
     */
    public void write(byte[] values) {
        write(values, 0, values.length);
    }

    /**
     * Write the bytes.
     * 
     * @param values Bytes.
     * @param offset An offset.
     * @param length A length.
     */
    public void write(byte[] values, int offset, int length) {
        for (int i = offset; i < offset + length; i++) {
            writeText(NativeString.fromCharCode(values[i]));
        }
    }

    /**
     * Append the value.
     * 
     * @param value A value.
     * @return This stream.
     */
    public PrintStream append(CharSequence value) {
        print(value);
        return this;
    }

    /**
     * Append the value.
     * 
     * @param value A value.
     * @return This stream.
     */
    public PrintStream append(char value) {
        print(value);
        return this;
    }

    /**
     * Append the range of the value.
     * 
     * @param value A value.
     * @param start A start index.
     * @param end An end index.
     * @return This stream.
     */
    public PrintStream append(CharSequence value, int start, int end) {
        writeText(String.valueOf(value).substring(start, end));
        return this;
    }

    /**
     * Flush this stream.
     */
    public void flush() {
    }

    /**
     * Close this stream.
     */
    public void close() {
    }

    /**
     * Check the error state.
     * 
     * @return Always false.
     */
    public boolean checkError() {
        return false;
    }

    /**
     * Write the text.
     * 
     * @param text A text.
     */
    private void writeText(String text) {
        NativeConsole.write(error, String.valueOf(text));
    }
}
