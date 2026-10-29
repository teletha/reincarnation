/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.util.regex;

import js.lang.NativeArray;
import js.lang.NativeRegExp;

/**
 * The Java implementation of {@link java.util.regex.Pattern} which is transpiled into JavaScript.
 */
public final class Pattern implements reincarnation.classlib.java.io.Serializable {

    /** The flag. */
    public static final int UNIX_LINES = 0x01;

    /** The flag. */
    public static final int CASE_INSENSITIVE = 0x02;

    /** The flag. */
    public static final int COMMENTS = 0x04;

    /** The flag. */
    public static final int MULTILINE = 0x08;

    /** The flag. */
    public static final int LITERAL = 0x10;

    /** The flag. */
    public static final int DOTALL = 0x20;

    /** The flag. */
    public static final int UNICODE_CASE = 0x40;

    /** The flag. */
    public static final int CANON_EQ = 0x80;

    /** The flag. */
    public static final int UNICODE_CHARACTER_CLASS = 0x100;

    /** The source pattern. */
    private final String regexText;

    /** The flags. */
    private final int flagValue;

    /** The JavaScript flags. */
    private final String flagText;

    /**
     * Create a pattern.
     * 
     * @param regex A source pattern.
     * @param flags Flags.
     */
    private Pattern(String regex, int flags) {
        this.regexText = regex;
        this.flagValue = flags;
        this.flagText = flagText(flags);
    }

    /**
     * Compile the pattern.
     * 
     * @param regex A source pattern.
     * @return A pattern.
     */
    public static Pattern compile(String regex) {
        return compile(regex, 0);
    }

    /**
     * Compile the pattern.
     * 
     * @param regex A source pattern.
     * @param flags Flags.
     * @return A pattern.
     */
    public static Pattern compile(String regex, int flags) {
        String source = regex == null ? "null" : regex;
        if ((flags & LITERAL) != 0) {
            source = quote(source);
        }
        return new Pattern(source, flags);
    }

    /**
     * Get the source pattern.
     * 
     * @return A source pattern.
     */
    public String pattern() {
        return regexText;
    }

    /**
     * Get the flags.
     * 
     * @return Flags.
     */
    public int flags() {
        return flagValue;
    }

    /**
     * Create a matcher.
     * 
     * @param input An input.
     * @return A matcher.
     */
    public Matcher matcher(CharSequence input) {
        return new Matcher(this, String.valueOf(input));
    }

    /**
     * Split the input.
     * 
     * @param input An input.
     * @return An array.
     */
    public String[] split(CharSequence input) {
        return split(input, 0);
    }

    /**
     * Split the input.
     * 
     * @param input An input.
     * @param limit A limit.
     * @return An array.
     */
    public String[] split(CharSequence input, int limit) {
        String text = String.valueOf(input);
        NativeRegExp regexp = create(true);
        NativeArray parts = new NativeArray();
        int index = 0;
        int count = 0;

        NativeArray match = regexp.exec(text);
        while (match != null) {
            int end = regexp.lastIndex();
            int start = end - String.valueOf(match.get(0)).length();
            parts.push(text.substring(index, start));
            index = end;
            count++;
            if (start == end) {
                regexp.lastIndex(end + 1);
            }

            if (0 < limit && limit - 1 <= count) {
                match = null;
            } else {
                match = regexp.exec(text);
            }
        }
        parts.push(text.substring(index));

        int size = parts.length();
        if (limit == 0) {
            while (0 < size && String.valueOf(parts.get(size - 1)).isEmpty()) {
                size--;
            }
        }

        String[] result = new String[size];
        for (int i = 0; i < size; i++) {
            result[i] = String.valueOf(parts.get(i));
        }
        return result;
    }

    /**
     * Quote the text.
     * 
     * @param value A text.
     * @return A quoted text.
     */
    public static String quote(String value) {
        String special = "\\.[]{}()<>*+-=!?^$|";
        String result = "";
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (special.indexOf(ch) != -1) {
                result += '\\';
            }
            result += ch;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return regexText;
    }

    /**
     * Create the native regular expression.
     * 
     * @param global Whether the expression is global.
     * @return A native regular expression.
     */
    NativeRegExp create(boolean global) {
        return new NativeRegExp(regexText, global ? flagText + "g" : flagText);
    }

    /**
     * Get the JavaScript flags.
     * 
     * @return A flags text.
     */
    String flagsText() {
        return flagText;
    }

    /**
     * Convert the flags.
     * 
     * @param flags Flags.
     * @return A JavaScript flags text.
     */
    private static String flagText(int flags) {
        String text = "";
        if ((flags & CASE_INSENSITIVE) != 0) {
            text += "i";
        }
        if ((flags & MULTILINE) != 0) {
            text += "m";
        }
        if ((flags & DOTALL) != 0) {
            text += "s";
        }
        if ((flags & UNICODE_CASE) != 0) {
            text += "u";
        }
        return text;
    }
}
