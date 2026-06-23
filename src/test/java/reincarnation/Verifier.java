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

/**
 * @version 2018/10/09 14:36:42
 */
public interface Verifier {

    /**
     * Verify the culculation.
     * 
     * @param param An input parameter.
     * @param expectedResult An expected result.
     */
    void verify(Object param, Object expectedResult);
}