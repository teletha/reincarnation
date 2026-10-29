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
import js.lang.NativeUndefined;

/**
 * The Java implementation of {@link java.util.regex.Matcher} which is transpiled into JavaScript.
 */
public final class Matcher implements MatchResult {

    /** The pattern. */
    private final Pattern owner;

    /** The input. */
    private String input;

    /** The matcher for the next match. */
    private final NativeRegExp global;

    /** The matcher for the first match. */
    private final NativeRegExp first;

    /** The matcher for the whole input. */
    private final NativeRegExp whole;

    /** The matcher anchored at the beginning. */
    private final NativeRegExp looking;

    /** The last matched values. */
    private NativeArray match;

    /** The start index. */
    private int startIndex;

    /** The end index. */
    private int endIndex;

    /**
     * Create a matcher.
     * 
     * @param pattern A pattern.
     * @param input An input.
     */
    Matcher(Pattern pattern, String input) {
        this.owner = pattern;
        this.input = input;
        this.global = pattern.create(true);
        this.first = pattern.create(false);
        this.whole = new NativeRegExp("^(?:" + pattern.pattern() + ")$", pattern.flagsText());
        this.looking = new NativeRegExp("^(?:" + pattern.pattern() + ")", pattern.flagsText());
    }

    /**
     * Match the whole input.
     * 
     * @return A result.
     */
    public boolean matches() {
        match = whole.exec(input);
        if (match == null) {
            return false;
        }
        startIndex = 0;
        endIndex = input.length();
        return true;
    }

    /**
     * Find the next match.
     * 
     * @return A result.
     */
    public boolean find() {
        return search();
    }

    /**
     * Find the next match from the index.
     * 
     * @param from A start index.
     * @return A result.
     */
    public boolean find(int from) {
        global.lastIndex(from);
        return search();
    }

    /**
     * Search the next match.
     * 
     * @return A result.
     */
    private boolean search() {
        match = global.exec(input);
        if (match == null) {
            return false;
        }
        String full = String.valueOf(match.get(0));
        endIndex = global.lastIndex();
        startIndex = endIndex - full.length();
        return true;
    }

    /**
     * Match the beginning of the input.
     * 
     * @return A result.
     */
    public boolean lookingAt() {
        match = looking.exec(input);
        if (match == null) {
            return false;
        }
        startIndex = 0;
        endIndex = String.valueOf(match.get(0)).length();
        return true;
    }

    /**
     * Get the group.
     * 
     * @return A group or null.
     */
    public String group() {
        return groupValue(0);
    }

    /**
     * Get the group.
     * 
     * @param index A group index.
     * @return A group or null.
     */
    public String group(int index) {
        return groupValue(index);
    }

    /**
     * Get the group value.
     * 
     * @param index A group index.
     * @return A group or null.
     */
    private String groupValue(int index) {
        if (match == null) {
            throw new IllegalStateException("No match found");
        }
        Object value = match.get(index);
        return NativeUndefined.is(value) ? null : String.valueOf(value);
    }

    /**
     * Get the number of groups.
     * 
     * @return A group count.
     */
    public int groupCount() {
        return match == null ? 0 : match.length() - 1;
    }

    /**
     * Get the start index.
     * 
     * @return A start index.
     */
    public int start() {
        if (match == null) {
            throw new IllegalStateException("No match found");
        }
        return startIndex;
    }

    /**
     * Get the start index of the group.
     * 
     * @param group A group index.
     * @return A start index.
     */
    public int start(int group) {
        if (group == 0) {
            return start();
        }
        String value = group(group);
        return value == null ? -1 : input.indexOf(value, startIndex);
    }

    /**
     * Get the end index.
     * 
     * @return An end index.
     */
    public int end() {
        if (match == null) {
            throw new IllegalStateException("No match found");
        }
        return endIndex;
    }

    /**
     * Get the end index of the group.
     * 
     * @param group A group index.
     * @return An end index.
     */
    public int end(int group) {
        if (group == 0) {
            return end();
        }
        String value = group(group);
        if (value == null) {
            return -1;
        }
        int start = input.indexOf(value, startIndex);
        return start + value.length();
    }

    /**
     * Replace all matches.
     * 
     * @param replacement A replacement.
     * @return A replaced text.
     */
    public String replaceAll(String replacement) {
        return global.replace(input, replacement);
    }

    /**
     * Replace the first match.
     * 
     * @param replacement A replacement.
     * @return A replaced text.
     */
    public String replaceFirst(String replacement) {
        return first.replace(input, replacement);
    }

    /**
     * Reset this matcher.
     * 
     * @return This matcher.
     */
    public Matcher reset() {
        global.lastIndex(0);
        match = null;
        startIndex = 0;
        endIndex = 0;
        return this;
    }

    /**
     * Reset this matcher with the new input.
     * 
     * @param input A new input.
     * @return This matcher.
     */
    public Matcher reset(CharSequence input) {
        this.input = String.valueOf(input);
        return reset();
    }

    /**
     * Get the pattern.
     * 
     * @return A pattern.
     */
    public Pattern pattern() {
        return owner;
    }
}
