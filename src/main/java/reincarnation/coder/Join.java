/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * @version 2018/10/26 9:41:07
 */
public final class Join<T> implements Code {

    /** The separator. */
    private String separator = "";

    /** The prefix. */
    private String prefix = "";

    /** The suffix. */
    private String suffix = "";

    /** The literalizer. */
    private Function<T, ?> converter = Function.identity();

    /** The flag. */
    private boolean ignoreEmpty = true;

    /** The value manager. */
    private final List<T> values = new ArrayList();

    /**
     * Set separator.
     * 
     * @param separator A separaotr chracter.
     * @return Chainable API.
     */
    public Join<T> separator(String separator) {
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
    public Join<T> prefix(String prefix) {
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
    public Join<T> suffix(String suffix) {
        if (suffix != null) {
            this.suffix = suffix;
        }
        return this;
    }

    /**
     * Set converter.
     * 
     * @param converter A value converter.
     * @return Chainable API.
     */
    public Join<T> converter(Function<T, ?> converter) {
        if (converter != null) {
            this.converter = converter;
        }
        return this;
    }

    /**
     * Set suffix.
     * 
     * @return Chainable API.
     */
    public Join<T> ignoreEmpty(boolean ignore) {
        this.ignoreEmpty = ignore;
        return this;
    }

    /**
     * Add values.
     * 
     * @param values
     * @return
     */
    public Join<T> add(T... values) {
        add(Arrays.asList(values));
        return this;
    }

    /**
     * Add values.
     * 
     * @param values
     * @return
     */
    public Join<T> add(Collection<T> values) {
        this.values.addAll(values);
        return this;
    }

    /**
     * Add values.
     * 
     * @param values
     * @return
     */
    public Join<T> remove(T... values) {
        remove(Arrays.asList(values));
        return this;
    }

    /**
     * Add values.
     * 
     * @param values
     * @return
     */
    public Join<T> remove(Collection<T> values) {
        this.values.removeAll(values);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        if (!ignoreEmpty || values.isEmpty() == false) {
            coder.write(prefix);
            Iterator<T> iterator = values.iterator();
            if (iterator.hasNext()) {
                coder.write(converter.apply(iterator.next()));

                while (iterator.hasNext()) {
                    coder.write(separator, converter.apply(iterator.next()));
                }
            }
            coder.write(suffix);
        }
    }
}
