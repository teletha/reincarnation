/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package js.lang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The JavaScript {@code RegExp}.
 * <p>
 * The Java implementation uses {@link Pattern} and mimics the JavaScript behavior so that the same
 * test can run on the JVM.
 * </p>
 */
@Native
public final class NativeRegExp {

    /** The source pattern. */
    private final String source;

    /** The flags. */
    private final String flags;

    /** The compiled pattern. */
    private final Pattern pattern;

    /** The last index for the stateful matching. */
    private int lastIndex;

    /**
     * Create a regular expression.
     * 
     * @param source A source pattern.
     */
    @Native("new RegExp($0)")
    public NativeRegExp(String source) {
        this(source, "");
    }

    /**
     * Create a regular expression.
     * 
     * @param source A source pattern.
     * @param flags Flags.
     */
    @Native("new RegExp($0, $1)")
    public NativeRegExp(String source, String flags) {
        this.source = source == null ? "" : source;
        this.flags = flags == null ? "" : flags;

        int options = 0;
        if (this.flags.indexOf('i') != -1) {
            options |= Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;
        }
        if (this.flags.indexOf('m') != -1) {
            options |= Pattern.MULTILINE;
        }
        if (this.flags.indexOf('s') != -1) {
            options |= Pattern.DOTALL;
        }
        if (this.flags.indexOf('u') != -1) {
            options |= Pattern.UNICODE_CASE;
        }
        this.pattern = Pattern.compile(this.source, options);
    }

    /**
     * Check whether the input matches this pattern.
     * 
     * @param input An input.
     * @return A result.
     */
    @Native("$this.test($0)")
    public boolean test(String input) {
        Matcher matcher = find(input);
        if (matcher == null) {
            if (global() || sticky()) {
                lastIndex = 0;
            }
            return false;
        }
        if (global() || sticky()) {
            lastIndex = matcher.end();
        }
        return true;
    }

    /**
     * Execute the pattern.
     * 
     * @param input An input.
     * @return A matched values or null.
     */
    @Native("$this.exec($0)")
    public NativeArray exec(String input) {
        Matcher matcher = find(input);
        if (matcher == null) {
            if (global() || sticky()) {
                lastIndex = 0;
            }
            return null;
        }
        if (global() || sticky()) {
            lastIndex = matcher.end();
        }

        NativeArray array = new NativeArray();
        for (int i = 0; i <= matcher.groupCount(); i++) {
            String value = matcher.group(i);
            array.push(value == null ? NativeUndefined.INSTANCE : value);
        }
        return array;
    }

    /**
     * Replace the matched part.
     * 
     * @param input An input.
     * @param replacement A replacement.
     * @return A replaced text.
     */
    @Native("$0.replace($this, $1)")
    public String replace(String input, String replacement) {
        Matcher matcher = pattern.matcher(input);
        return global() ? matcher.replaceAll(replacement) : matcher.replaceFirst(replacement);
    }

    /**
     * Split the input.
     * 
     * @param input An input.
     * @return An array.
     */
    @Native("$0.split($this)")
    public NativeArray split(String input) {
        NativeArray array = new NativeArray();
        for (String part : pattern.split(input, -1)) {
            array.push(part);
        }
        return array;
    }

    /**
     * Get the source pattern.
     * 
     * @return A source pattern.
     */
    @Native("$this.source")
    public String source() {
        return source;
    }

    /**
     * Get the flags.
     * 
     * @return Flags.
     */
    @Native("$this.flags")
    public String flags() {
        return flags;
    }

    /**
     * Check whether this pattern is global.
     * 
     * @return A result.
     */
    @Native("$this.global")
    public boolean global() {
        return flags.indexOf('g') != -1;
    }

    /**
     * Check whether this pattern ignores case.
     * 
     * @return A result.
     */
    @Native("$this.ignoreCase")
    public boolean ignoreCase() {
        return flags.indexOf('i') != -1;
    }

    /**
     * Check whether this pattern is multiline.
     * 
     * @return A result.
     */
    @Native("$this.multiline")
    public boolean multiline() {
        return flags.indexOf('m') != -1;
    }

    /**
     * Check whether this pattern is sticky.
     * 
     * @return A result.
     */
    @Native("$this.sticky")
    public boolean sticky() {
        return flags.indexOf('y') != -1;
    }

    /**
     * Get the last index.
     * 
     * @return A last index.
     */
    @Native("$this.lastIndex")
    public int lastIndex() {
        return lastIndex;
    }

    /**
     * Set the last index.
     * 
     * @param index A last index.
     */
    @Native("$this.lastIndex = $0")
    public void lastIndex(int index) {
        this.lastIndex = index;
    }

    /**
     * Find the matcher.
     * 
     * @param input An input.
     * @return A matcher or null.
     */
    private Matcher find(String input) {
        String text = input == null ? "null" : input;
        Matcher matcher = pattern.matcher(text);

        if (global() || sticky()) {
            if (text.length() < lastIndex) {
                lastIndex = 0;
            }
            if (!matcher.find(lastIndex)) {
                return null;
            }
            if (sticky() && matcher.start() != lastIndex) {
                return null;
            }
        } else if (!matcher.find()) {
            return null;
        }
        return matcher;
    }

    /**
     * {@inheritDoc}
     */
    @Native("$this.toString()")
    @Override
    public String toString() {
        return "/" + source + "/" + flags;
    }
}
