/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.coder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @version 2018/10/14 9:14:32
 */
public final class Joiner implements Code {

    /** The separator. */
    private String separator = "";

    /** The prefix. */
    private String prefix = "";

    /** The suffix. */
    private String suffix = "";

    /** The value manager. */
    private final List values = new ArrayList();

    /**
     * Set separator.
     * 
     * @param separator A separaotr chracter.
     * @return Chainable API.
     */
    public Joiner separator(String separator) {
        if (separator != null) {
            this.separator = separator;
        }
        return this;
    }

    /**
     * Set prefix.
     * 
     * @param prefix A prefix chracter.
     * @return Chainable API.
     */
    public Joiner prefix(String prefix) {
        if (prefix != null) {
            this.prefix = prefix;
        }
        return this;
    }

    /**
     * Set suffix.
     * 
     * @param suffix A suffix chracter.
     * @return Chainable API.
     */
    public Joiner suffix(String suffix) {
        if (suffix != null) {
            this.suffix = suffix;
        }
        return this;
    }

    /**
     * Add values.
     * 
     * @param values
     * @return
     */
    public Joiner add(Object... values) {
        values(Arrays.asList(values));
        return this;
    }

    /**
     * Add values.
     * 
     * @param values
     * @return
     */
    public Joiner values(Collection values) {
        this.values.addAll(values);

        return this;
    }

    public Joiner values(Collection values, String separator) {
        this.values.addAll(values);
        this.separator = separator;
        return this;
    }

    /**
     * Add values.
     * 
     * @param values
     * @return
     */
    public Joiner remove(Object... values) {
        remove(Arrays.asList(values));
        return this;
    }

    /**
     * Add values.
     * 
     * @param values
     * @return
     */
    public Joiner remove(Collection values) {
        this.values.removeAll(values);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        coder.write(prefix);
        Iterator iterator = values.iterator();
        if (iterator.hasNext()) {
            coder.write(iterator.next());

            while (iterator.hasNext()) {
                coder.write(separator, iterator.next());
            }
        }
        coder.write(suffix);
    }
}
