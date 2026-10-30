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

import reincarnation.classlib.java.io.PrintStream;

/**
 * The Java implementation of {@link java.util.Properties} which is transpiled into JavaScript.
 */
public class Properties extends Hashtable<Object, Object> {

    /** The default properties. */
    private final Properties defaults;

    /**
     * Create the properties.
     */
    public Properties() {
        this.defaults = null;
    }

    /**
     * Create the properties.
     * 
     * @param defaults Default properties.
     */
    public Properties(Properties defaults) {
        this.defaults = defaults;
    }

    /**
     * Create the properties.
     * 
     * @param initialCapacity An initial capacity.
     */
    public Properties(int initialCapacity) {
        super(initialCapacity);
        this.defaults = null;
    }

    /**
     * Set the property.
     * 
     * @param key A key.
     * @param value A value.
     * @return A previous value.
     */
    public Object setProperty(String key, String value) {
        return put(key, value);
    }

    /**
     * Get the property.
     * 
     * @param key A key.
     * @return A value or null.
     */
    public String getProperty(String key) {
        Object value = get(key);
        if (value != null) {
            return (String) value;
        }
        return defaults == null ? null : defaults.getProperty(key);
    }

    /**
     * Get the property.
     * 
     * @param key A key.
     * @param defaultValue A default value.
     * @return A value.
     */
    public String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return value == null ? defaultValue : value;
    }

    /**
     * Get the property names.
     * 
     * @return Names.
     */
    public Enumeration<?> propertyNames() {
        int size = size();
        Object[] names = new Object[size];
        int index = 0;
        for (Object key : keySet()) {
            names[index++] = key;
        }
        return new PropertyEnumeration(names);
    }

    /**
     * Get the string property names.
     * 
     * @return Names.
     */
    public Set<String> stringPropertyNames() {
        Set<String> names = new HashSet();
        for (Object key : keySet()) {
            if (key instanceof String) {
                names.add((String) key);
            }
        }
        if (defaults != null) {
            names.addAll(defaults.stringPropertyNames());
        }
        return names;
    }

    /**
     * List the properties.
     * 
     * @param out An output.
     */
    public void list(PrintStream out) {
        for (Object key : keySet()) {
            out.println(key + "=" + get(key));
        }
    }

    /**
     * The property enumeration.
     */
    private static final class PropertyEnumeration implements Enumeration<Object> {

        /** The values. */
        private final Object[] values;

        /** The index. */
        private int index;

        /**
         * Create the enumeration.
         * 
         * @param values Values.
         */
        private PropertyEnumeration(Object[] values) {
            this.values = values;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasMoreElements() {
            return index < values.length;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object nextElement() {
            return values[index++];
        }
    }
}
