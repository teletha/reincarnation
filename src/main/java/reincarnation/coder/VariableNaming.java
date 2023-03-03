/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.coder;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Depth based variable manager.
 */
public class VariableNaming {

    private final ArrayDeque<Map<String, String>> manager = new ArrayDeque();

    /**
     * Start new context.
     */
    public void start() {
        manager.addLast(new HashMap());
    }

    /**
     * End the current context and restore previous one.
     */
    public void end() {
        manager.pollLast();
    }

    /**
     * Declare the variable name.
     * 
     * @param name A variable name.
     */
    public void declare(String name) {
        if (!manager.isEmpty()) {
            manager.peekLast().put(name, isDeclared(name) ? name + "X" : name);
        }
    }

    /**
     * Test whether the specified variablen name is already declared or not.
     * 
     * @param name A variable name.
     * @return
     */
    public boolean isDeclared(String name) {
        Iterator<Map<String, String>> iterator = manager.descendingIterator();
        while (iterator.hasNext()) {
            Map<String, String> set = iterator.next();
            if (set.containsKey(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Resolve the variable name.
     * 
     * @param name
     * @return
     */
    public String name(String name) {
        Iterator<Map<String, String>> iterator = manager.descendingIterator();
        while (iterator.hasNext()) {
            Map<String, String> set = iterator.next();
            String assigned = set.get(name);
            if (assigned != null) {
                return assigned;
            }
        }
        return name;
    }
}
