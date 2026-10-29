/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.util;

/**
 * The Java implementation of {@link java.util.Set} which is transpiled into JavaScript.
 */
public interface Set<E> extends Collection<E> {

    /**
     * Create the empty set.
     * 
     * @return A set.
     */
    static Set of() {
        return new HashSet();
    }

    /**
     * Create the set.
     * 
     * @param value A value.
     * @return A set.
     */
    static Set of(Object value) {
        return ofArray(new Object[] {value});
    }

    /**
     * Create the set.
     * 
     * @param first A value.
     * @param second A value.
     * @return A set.
     */
    static Set of(Object first, Object second) {
        return ofArray(new Object[] {first, second});
    }

    /**
     * Create the set.
     * 
     * @param first A value.
     * @param second A value.
     * @param third A value.
     * @return A set.
     */
    static Set of(Object first, Object second, Object third) {
        return ofArray(new Object[] {first, second, third});
    }

    /**
     * Create the set.
     * 
     * @param first A value.
     * @param second A value.
     * @param third A value.
     * @param fourth A value.
     * @return A set.
     */
    static Set of(Object first, Object second, Object third, Object fourth) {
        return ofArray(new Object[] {first, second, third, fourth});
    }

    /**
     * Create the set.
     * 
     * @param first A value.
     * @param second A value.
     * @param third A value.
     * @param fourth A value.
     * @param fifth A value.
     * @return A set.
     */
    static Set of(Object first, Object second, Object third, Object fourth, Object fifth) {
        return ofArray(new Object[] {first, second, third, fourth, fifth});
    }

    /**
     * Create the set.
     * 
     * @param first A value.
     * @param second A value.
     * @param third A value.
     * @param fourth A value.
     * @param fifth A value.
     * @param sixth A value.
     * @return A set.
     */
    static Set of(Object first, Object second, Object third, Object fourth, Object fifth, Object sixth) {
        return ofArray(new Object[] {first, second, third, fourth, fifth, sixth});
    }

    /**
     * Create the set.
     * 
     * @param first A value.
     * @param second A value.
     * @param third A value.
     * @param fourth A value.
     * @param fifth A value.
     * @param sixth A value.
     * @param seventh A value.
     * @return A set.
     */
    static Set of(Object first, Object second, Object third, Object fourth, Object fifth, Object sixth,
            Object seventh) {
        return ofArray(new Object[] {first, second, third, fourth, fifth, sixth, seventh});
    }

    /**
     * Create the set.
     * 
     * @param first A value.
     * @param second A value.
     * @param third A value.
     * @param fourth A value.
     * @param fifth A value.
     * @param sixth A value.
     * @param seventh A value.
     * @param eighth A value.
     * @return A set.
     */
    static Set of(Object first, Object second, Object third, Object fourth, Object fifth, Object sixth,
            Object seventh, Object eighth) {
        return ofArray(new Object[] {first, second, third, fourth, fifth, sixth, seventh, eighth});
    }

    /**
     * Create the set.
     * 
     * @param first A value.
     * @param second A value.
     * @param third A value.
     * @param fourth A value.
     * @param fifth A value.
     * @param sixth A value.
     * @param seventh A value.
     * @param eighth A value.
     * @param ninth A value.
     * @return A set.
     */
    static Set of(Object first, Object second, Object third, Object fourth, Object fifth, Object sixth,
            Object seventh, Object eighth, Object ninth) {
        return ofArray(new Object[] {first, second, third, fourth, fifth, sixth, seventh, eighth, ninth});
    }

    /**
     * Create the set.
     * 
     * @param first A value.
     * @param second A value.
     * @param third A value.
     * @param fourth A value.
     * @param fifth A value.
     * @param sixth A value.
     * @param seventh A value.
     * @param eighth A value.
     * @param ninth A value.
     * @param tenth A value.
     * @return A set.
     */
    static Set of(Object first, Object second, Object third, Object fourth, Object fifth, Object sixth,
            Object seventh, Object eighth, Object ninth, Object tenth) {
        return ofArray(new Object[] {first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth});
    }

    /**
     * Create the set.
     * 
     * @param values Values.
     * @return A set.
     */
    static Set of(Object... values) {
        return ofArray(values);
    }

    /**
     * Create the set from the collection.
     * 
     * @param collection A collection.
     * @return A set.
     */
    static Set copyOf(Collection collection) {
        Set set = new HashSet();
        set.addAll(collection);
        return set;
    }

    /**
     * Create the set from the array.
     * 
     * @param values Values.
     * @return A set.
     */
    private static Set ofArray(Object[] values) {
        Set set = new HashSet();
        for (Object value : values) {
            set.add(value);
        }
        return set;
    }
}
