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

    /**
     * All variable names are incremental suffix number.
     */
    public static final Strategy Incremental = name -> {
        return name;
    };

    /**
     * Reuse the original name as far as possible.
     */
    public static final Strategy Original = name -> {
        return name;
    };

    /**
     * All variable names are obfuscated and minimized.
     */
    public static final Strategy Obfuscate = name -> {
        return name;
    };

    private final ArrayDeque<Map<String, String>> manager = new ArrayDeque();

    public void start() {
        manager.addLast(new HashMap());
    }

    public void end() {
        manager.pollLast();
    }

    public void declare(String name) {
        if (!manager.isEmpty()) {
            manager.peekLast().put(name, isDeclared(name) ? name + "X" : name);
        }
    }

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

    /**
     * Naming strategy.
     */
    public interface Strategy {
        String name(String name);
    }
}
