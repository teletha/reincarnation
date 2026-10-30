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

import reincarnation.classlib.java.io.Serializable;
import reincarnation.classlib.java.util.HashMap;
import reincarnation.classlib.java.util.HashSet;
import reincarnation.classlib.java.util.Locale;
import reincarnation.classlib.java.util.Map;

/**
 * The Java implementation of {@link java.util.ResourceBundle} which is transpiled into JavaScript.
 */
public abstract class ResourceBundle implements Serializable {

    /** The registered bundles. */
    private static final Map bundles = new HashMap();

    /** The parent bundle. */
    private ResourceBundle parent;

    /** The locale. */
    private Locale locale;

    /**
     * Create the resource bundle.
     */
    protected ResourceBundle() {
    }

    /**
     * Get the object.
     * 
     * @param key A key.
     * @return A value or null.
     */
    public final Object getObject(String key) {
        Object value = handleGetObject(key);
        if (value == null && parent != null) {
            value = parent.getObject(key);
        }
        return value;
    }

    /**
     * Get the string.
     * 
     * @param key A key.
     * @return A value or null.
     */
    public final String getString(String key) {
        Object value = getObject(key);
        return value == null ? null : (String) value;
    }

    /**
     * Get the string array.
     * 
     * @param key A key.
     * @return Values or null.
     */
    public final String[] getStringArray(String key) {
        Object value = getObject(key);
        return value == null ? null : (String[]) value;
    }

    /**
     * Check whether the key exists.
     * 
     * @param key A key.
     * @return A result.
     */
    public boolean containsKey(String key) {
        if (handleGetObject(key) != null) {
            return true;
        }
        return parent != null && parent.containsKey(key);
    }

    /**
     * Get the keys.
     * 
     * @return Keys.
     */
    public Set<String> keySet() {
        Set<String> keys = new HashSet();
        Enumeration<String> enumeration = getKeys();
        while (enumeration.hasMoreElements()) {
            keys.add(enumeration.nextElement());
        }
        return keys;
    }

    /**
     * Get the keys.
     * 
     * @return Keys.
     */
    public abstract Enumeration<String> getKeys();

    /**
     * Get the object of this bundle.
     * 
     * @param key A key.
     * @return A value or null.
     */
    protected abstract Object handleGetObject(String key);

    /**
     * Get the parent.
     * 
     * @return A parent or null.
     */
    public ResourceBundle getParent() {
        return parent;
    }

    /**
     * Set the parent.
     * 
     * @param parent A parent.
     */
    public void setParent(ResourceBundle parent) {
        this.parent = parent;
    }

    /**
     * Get the locale.
     * 
     * @return A locale.
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Set the locale.
     * 
     * @param locale A locale.
     */
    protected void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Get the base bundle name.
     * 
     * @return A base bundle name or null.
     */
    public String getBaseBundleName() {
        return null;
    }

    /**
     * Register the bundle.
     * 
     * @param baseName A base name.
     * @param bundle A bundle.
     */
    public static void register(String baseName, ResourceBundle bundle) {
        bundles.put(baseName, bundle);
    }

    /**
     * Get the bundle.
     * 
     * @param baseName A base name.
     * @return A bundle.
     */
    public static ResourceBundle getBundle(String baseName) {
        ResourceBundle bundle = (ResourceBundle) bundles.get(baseName);
        if (bundle == null) {
            throw new IllegalArgumentException("Can't find bundle for base name " + baseName);
        }
        return bundle;
    }

    /**
     * Get the bundle.
     * 
     * @param baseName A base name.
     * @param locale A locale.
     * @return A bundle.
     */
    public static ResourceBundle getBundle(String baseName, Locale locale) {
        return getBundle(baseName);
    }

    /**
     * Clear the cache.
     */
    public static void clearCache() {
        bundles.clear();
    }
}
