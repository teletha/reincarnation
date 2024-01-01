/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder;

/**
 * Naming strategy.
 */
public interface Naming {

    /**
     * All variable names are incremental suffix number.
     */
    Naming Incremental = name -> {
        return name;
    };

    /**
     * All variable names are obfuscated and minimized.
     */
    Naming Obfuscate = name -> {
        return name;
    };

    /**
     * Rename something.
     * 
     * @param name A target name.
     * @return A renamed.
     */
    String name(String name);

    /**
     * Compose {@link Naming}.
     * 
     * @param next
     * @return
     */
    default Naming then(Naming next) {
        if (next == null) {
            return this;
        }

        return name -> {
            String output = name(name);
            return output != name ? output : next.name(name);
        };
    }
}