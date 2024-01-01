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

import java.util.Optional;

import kiss.I;
import kiss.Signal;
import kiss.Variable;

public interface Code<C extends Code> {

    /** The empty code. */
    Code Empty = coder -> {
    };

    /**
     * Write source code.
     * 
     * @param coder
     */
    void write(Coder coder);

    /**
     * Comment for {@link Code}.
     * 
     * @return
     */
    default Optional<String> comment() {
        return Optional.empty();
    }

    /**
     * Check this code is empty or not.
     * 
     * @return A result.
     */
    default boolean isEmpty() {
        return false;
    }

    /**
     * Check this code is empty or not.
     * 
     * @return A result.
     */
    default boolean isNotEmpty() {
        return !isEmpty();
    }

    /**
     * Find the first child {@link Code} fragment.
     * 
     * @return
     */
    default Variable<C> child() {
        return children().first().to();
    }

    /**
     * Find the first child {@link Code} fragment.
     * 
     * @param <O>
     * @param type
     * @return
     */
    default <O extends C> Variable<O> child(Class<O> type) {
        return children(type).first().to();
    }

    /**
     * Find all children {@link Code} fragments.
     * 
     * @return
     */
    default Signal<C> children() {
        return I.signal();
    }

    /**
     * Find all children {@link Code} fragments.
     * 
     * @return
     */
    default <O extends C> Signal<O> children(Class<O> type) {
        return children().as(type);
    }

    /**
     * Find all children {@link Code} fragments.
     * 
     * @return
     */
    default <O extends C, P extends C> Signal<P> children(Class<O> type1, Class<P> type2) {
        return children(type1).flatMap(C::children).as(type2);
    }

    /**
     * Find all children {@link Code} fragments.
     * 
     * @return
     */
    default <O extends C, P extends C, Q extends C> Signal<Q> children(Class<O> type1, Class<P> type2, Class<Q> type3) {
        return children(type1, type2).flatMap(C::children).as(type3);
    }

    /**
     * Collect all descendent {@link Code} fragments.
     * 
     * @return
     */
    default Signal<C> descendent() {
        return I.signal(this).merge(children().skipNull().flatMap(c -> c.descendent()));
    }
}