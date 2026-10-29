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

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The Java implementation of {@link java.util.Collections} which is transpiled into JavaScript.
 */
public final class Collections {

    /**
     * Hide constructor.
     */
    private Collections() {
    }

    /**
     * Sort the list.
     * 
     * @param list A list.
     */
    public static void sort(List list) {
        list.sort(null);
    }

    /**
     * Sort the list.
     * 
     * @param list A list.
     * @param comparator A comparator.
     */
    public static void sort(List list, Comparator comparator) {
        list.sort(comparator);
    }

    /**
     * Reverse the list.
     * 
     * @param list A list.
     */
    public static void reverse(List list) {
        for (int left = 0, right = list.size() - 1; left < right; left++, right--) {
            Object value = list.get(left);
            list.set(left, list.get(right));
            list.set(right, value);
        }
    }

    /**
     * Swap the values.
     * 
     * @param list A list.
     * @param left A left index.
     * @param right A right index.
     */
    public static void swap(List list, int left, int right) {
        Object value = list.get(left);
        list.set(left, list.get(right));
        list.set(right, value);
    }

    /**
     * Fill the list.
     * 
     * @param list A list.
     * @param value A value.
     */
    public static void fill(List list, Object value) {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, value);
        }
    }

    /**
     * Copy the list.
     * 
     * @param destination A destination list.
     * @param source A source list.
     */
    public static void copy(List destination, List source) {
        for (int i = 0; i < source.size(); i++) {
            destination.set(i, source.get(i));
        }
    }

    /**
     * Get the maximum value.
     * 
     * @param collection A collection.
     * @return A maximum value.
     */
    public static Object max(Collection collection) {
        Object result = null;
        boolean first = true;
        for (Object value : collection) {
            if (first || ((Comparable) value).compareTo(result) > 0) {
                result = value;
                first = false;
            }
        }
        return result;
    }

    /**
     * Get the maximum value.
     * 
     * @param collection A collection.
     * @param comparator A comparator.
     * @return A maximum value.
     */
    public static Object max(Collection collection, Comparator comparator) {
        Object result = null;
        boolean first = true;
        for (Object value : collection) {
            if (first || comparator.compare(value, result) > 0) {
                result = value;
                first = false;
            }
        }
        return result;
    }

    /**
     * Get the minimum value.
     * 
     * @param collection A collection.
     * @return A minimum value.
     */
    public static Object min(Collection collection) {
        Object result = null;
        boolean first = true;
        for (Object value : collection) {
            if (first || ((Comparable) value).compareTo(result) < 0) {
                result = value;
                first = false;
            }
        }
        return result;
    }

    /**
     * Get the minimum value.
     * 
     * @param collection A collection.
     * @param comparator A comparator.
     * @return A minimum value.
     */
    public static Object min(Collection collection, Comparator comparator) {
        Object result = null;
        boolean first = true;
        for (Object value : collection) {
            if (first || comparator.compare(value, result) < 0) {
                result = value;
                first = false;
            }
        }
        return result;
    }

    /**
     * Search the key.
     * 
     * @param list A list.
     * @param key A key.
     * @return An index.
     */
    public static int binarySearch(List list, Object key) {
        int low = 0;
        int high = list.size() - 1;
        while (low <= high) {
            int middle = (low + high) >>> 1;
            int compared = ((Comparable) list.get(middle)).compareTo(key);
            if (compared < 0) {
                low = middle + 1;
            } else if (compared > 0) {
                high = middle - 1;
            } else {
                return middle;
            }
        }
        return -(low + 1);
    }

    /**
     * Search the key.
     * 
     * @param list A list.
     * @param key A key.
     * @param comparator A comparator.
     * @return An index.
     */
    public static int binarySearch(List list, Object key, Comparator comparator) {
        int low = 0;
        int high = list.size() - 1;
        while (low <= high) {
            int middle = (low + high) >>> 1;
            int compared = comparator.compare(list.get(middle), key);
            if (compared < 0) {
                low = middle + 1;
            } else if (compared > 0) {
                high = middle - 1;
            } else {
                return middle;
            }
        }
        return -(low + 1);
    }

    /**
     * Get the reverse comparator.
     * 
     * @return A comparator.
     */
    public static Comparator reverseOrder() {
        return (left, right) -> ((Comparable) right).compareTo(left);
    }

    /**
     * Get the reverse comparator.
     * 
     * @param comparator A comparator.
     * @return A comparator.
     */
    public static Comparator reverseOrder(Comparator comparator) {
        return (left, right) -> comparator.compare(right, left);
    }

    /**
     * Create the empty list.
     * 
     * @return A list.
     */
    public static List emptyList() {
        return List.of();
    }

    /**
     * Create the empty set.
     * 
     * @return A set.
     */
    public static Set emptySet() {
        return Set.of();
    }

    /**
     * Create the empty map.
     * 
     * @return A map.
     */
    public static Map emptyMap() {
        return Map.of();
    }

    /**
     * Create the singleton list.
     * 
     * @param value A value.
     * @return A list.
     */
    public static List singletonList(Object value) {
        return List.of(value);
    }

    /**
     * Create the singleton set.
     * 
     * @param value A value.
     * @return A set.
     */
    public static Set singleton(Object value) {
        return Set.of(value);
    }

    /**
     * Create the singleton map.
     * 
     * @param key A key.
     * @param value A value.
     * @return A map.
     */
    public static Map singletonMap(Object key, Object value) {
        return Map.of(key, value);
    }

    /**
     * Create the list with the repeated value.
     * 
     * @param count A count.
     * @param value A value.
     * @return A list.
     */
    public static List nCopies(int count, Object value) {
        List list = List.of();
        for (int i = 0; i < count; i++) {
            list.add(value);
        }
        return list;
    }

    /**
     * Get the unmodifiable list.
     * 
     * @param list A list.
     * @return A list.
     */
    public static List unmodifiableList(List list) {
        return list;
    }

    /**
     * Get the unmodifiable set.
     * 
     * @param set A set.
     * @return A set.
     */
    public static Set unmodifiableSet(Set set) {
        return set;
    }

    /**
     * Get the unmodifiable map.
     * 
     * @param map A map.
     * @return A map.
     */
    public static Map unmodifiableMap(Map map) {
        return map;
    }

    /**
     * Get the unmodifiable collection.
     * 
     * @param collection A collection.
     * @return A collection.
     */
    public static Collection unmodifiableCollection(Collection collection) {
        return collection;
    }

    /**
     * Get the synchronized list.
     * 
     * @param list A list.
     * @return A list.
     */
    public static List synchronizedList(List list) {
        return list;
    }

    /**
     * Get the synchronized set.
     * 
     * @param set A set.
     * @return A set.
     */
    public static Set synchronizedSet(Set set) {
        return set;
    }

    /**
     * Get the synchronized map.
     * 
     * @param map A map.
     * @return A map.
     */
    public static Map synchronizedMap(Map map) {
        return map;
    }

    /**
     * Get the synchronized collection.
     * 
     * @param collection A collection.
     * @return A collection.
     */
    public static Collection synchronizedCollection(Collection collection) {
        return collection;
    }

    /**
     * Get the checked list.
     * 
     * @param list A list.
     * @param type A type.
     * @return A list.
     */
    public static List checkedList(List list, Class type) {
        return list;
    }

    /**
     * Get the checked set.
     * 
     * @param set A set.
     * @param type A type.
     * @return A set.
     */
    public static Set checkedSet(Set set, Class type) {
        return set;
    }

    /**
     * Get the checked map.
     * 
     * @param map A map.
     * @param keyType A key type.
     * @param valueType A value type.
     * @return A map.
     */
    public static Map checkedMap(Map map, Class keyType, Class valueType) {
        return map;
    }

    /**
     * Get the checked collection.
     * 
     * @param collection A collection.
     * @param type A type.
     * @return A collection.
     */
    public static Collection checkedCollection(Collection collection, Class type) {
        return collection;
    }
}
