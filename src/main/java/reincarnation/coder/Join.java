/*
 * Copyright (C) 2023 The REINCARNATION Development Team
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
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import kiss.I;
import kiss.Ⅱ;

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
    private BiFunction<Integer, T, ?> converter = (i, v) -> v;

    /** The filter. */
    private BiPredicate<Long, T> take;

    /** The flag. */
    private boolean ignoreEmpty;

    /** The flag. */
    private boolean ignoreSingle;

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
     * Set filter.
     * 
     * @param condition
     * @return
     */
    public Join<T> skip(Predicate<T> condition) {
        return take(condition.negate());
    }

    /**
     * Set filter.
     * 
     * @param condition
     * @return
     */
    public Join<T> skip(BiPredicate<Long, T> condition) {
        return take(condition.negate());
    }

    /**
     * Set filter.
     * 
     * @param condition
     * @return
     */
    public Join<T> take(Predicate<T> condition) {
        this.take = (index, item) -> condition.test(item);
        return this;
    }

    /**
     * Set filter.
     * 
     * @param condition
     * @return
     */
    public Join<T> take(BiPredicate<Long, T> condition) {
        this.take = condition;
        return this;
    }

    /**
     * Set converter.
     * 
     * @param converter A value converter.
     * @return Chainable API.
     */
    public Join<T> converter(Function<T, ?> converter) {
        return converter((i, v) -> converter.apply(v));
    }

    /**
     * Set converter for last value.
     * 
     * @param converter A value converter.
     * @return Chainable API.
     */
    public Join<T> converter(BiFunction<Integer, T, ?> converter) {
        if (converter != null) {
            this.converter = converter;
        }
        return this;
    }

    /**
     * Ignore prefix and suffix if container is empty.
     * 
     * @return Chainable API.
     */
    public Join<T> ignoreEmpty() {
        this.ignoreEmpty = true;
        return this;
    }

    /**
     * Ignore prefix and suffix if container has single item.
     * 
     * @return Chainable API.
     */
    public Join<T> ignoreSingle() {
        this.ignoreSingle = true;
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
     * Write out as {@link String}.
     * 
     * @return
     */
    public String write() {
        StringBuilder builder = new StringBuilder();
        write(builder);
        return builder.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        write(coder, I.NoOP);
    }

    /**
     * Write out or process if no item.
     * 
     * @param coder
     * @param empty
     */
    public void write(Coder coder, Runnable empty) {
        List<T> values = take == null ? this.values : I.signal(this.values).index(0).take(x -> take.test(x.ⅱ, x.ⅰ)).map(Ⅱ::ⅰ).toList();
        int size = values.size();

        if (!ignoreEmpty || size != 0) {
            if (!ignoreSingle || size != 1) coder.write(prefix);
            int index = 0;
            Iterator<T> iterator = values.iterator();
            if (iterator.hasNext()) {
                coder.write(converter.apply(index++, iterator.next()));

                while (iterator.hasNext()) {
                    coder.write(separator, converter.apply(index++, iterator.next()));
                }
            }
            if (!ignoreSingle || size != 1) coder.write(suffix);
        } else if (empty != null) {
            empty.run();
        }
    }

    /**
     * Write to {@link StringBuilder}.
     * 
     * @param builder
     */
    public void write(StringBuilder builder) {
        write(builder, I.NoOP);
    }

    /**
     * Write to {@link StringBuilder}.
     * 
     * @param builder
     */
    public void write(StringBuilder builder, Runnable empty) {
        List<T> values = take == null ? this.values : I.signal(this.values).index(0).take(x -> take.test(x.ⅱ, x.ⅰ)).map(Ⅱ::ⅰ).toList();
        int size = values.size();

        if (!ignoreEmpty || size != 0) {
            if (!ignoreSingle || size != 1) builder.append(prefix);
            int index = 0;
            Iterator<T> iterator = values.iterator();
            if (iterator.hasNext()) {
                builder.append(converter.apply(index++, iterator.next()));

                while (iterator.hasNext()) {
                    builder.append(separator).append(converter.apply(index++, iterator.next()));
                }
            }
            if (!ignoreSingle || size != 1) builder.append(suffix);
        } else if (empty != null) {
            empty.run();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return write();
    }

    /**
     * Create joinable code.
     * 
     * @param values The values to join.
     * @return A joinable code.
     */
    public static final <T> Join<T> of(T... values) {
        return new Join<T>().add(values);
    }

    /**
     * Create joinable code.
     * 
     * @param values The values to join.
     * @return A joinable code.
     */
    public static final <T> Join<T> of(Collection<T> values) {
        return new Join<T>().add(values);
    }

    /**
     * Create joinable code.
     * 
     * @param prefix The prefix.
     * @return A joinable code.
     */
    public static final <T> Join<T> of(String prefix, Collection<T> values, String separator, String suffix) {
        return new Join().prefix(prefix).add(values).separator(separator).suffix(suffix);
    }
}