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

/**
 * The Java implementation of {@link java.util.regex.MatchResult} which is transpiled into
 * JavaScript.
 */
public interface MatchResult {

    /**
     * Get the matched text.
     * 
     * @return A text.
     */
    String group();

    /**
     * Get the matched text.
     * 
     * @param group A group index.
     * @return A text.
     */
    String group(int group);

    /**
     * Get the number of groups.
     * 
     * @return A number.
     */
    int groupCount();

    /**
     * Get the start index.
     * 
     * @return An index.
     */
    int start();

    /**
     * Get the start index.
     * 
     * @param group A group index.
     * @return An index.
     */
    int start(int group);

    /**
     * Get the end index.
     * 
     * @return An index.
     */
    int end();

    /**
     * Get the end index.
     * 
     * @param group A group index.
     * @return An index.
     */
    int end(int group);
}
