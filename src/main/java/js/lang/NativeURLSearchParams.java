/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package js.lang;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The JavaScript {@code URLSearchParams}.
 */
@Native
public final class NativeURLSearchParams {

    /** The values. */
    private final Map<String, List<String>> values;

    /**
     * Create the empty search parameters.
     */
    @Native("new URLSearchParams()")
    public NativeURLSearchParams() {
        this.values = new LinkedHashMap();
    }

    /**
     * Create the search parameters.
     * 
     * @param query A query.
     */
    @Native("new URLSearchParams($0)")
    public NativeURLSearchParams(String query) {
        this.values = parse(query);
    }

    /**
     * Append the value.
     * 
     * @param name A name.
     * @param value A value.
     */
    @Native("$this.append($0, $1)")
    public void append(String name, String value) {
        List<String> list = values.get(name);
        if (list == null) {
            list = new ArrayList();
            values.put(name, list);
        }
        list.add(value);
    }

    /**
     * Delete the values.
     * 
     * @param name A name.
     */
    @Native("$this.delete($0)")
    public void delete(String name) {
        values.remove(name);
    }

    /**
     * Get the value.
     * 
     * @param name A name.
     * @return A value or null.
     */
    @Native("$this.get($0)")
    public String get(String name) {
        List<String> list = values.get(name);
        return list == null || list.isEmpty() ? null : list.get(0);
    }

    /**
     * Get the values.
     * 
     * @param name A name.
     * @return Values.
     */
    @Native("$this.getAll($0)")
    public String[] getAll(String name) {
        List<String> list = values.get(name);
        if (list == null) {
            return new String[0];
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * Check whether the name exists.
     * 
     * @param name A name.
     * @return A result.
     */
    @Native("$this.has($0)")
    public boolean has(String name) {
        return values.containsKey(name);
    }

    /**
     * Set the value.
     * 
     * @param name A name.
     * @param value A value.
     */
    @Native("$this.set($0, $1)")
    public void set(String name, String value) {
        List<String> list = new ArrayList();
        list.add(value);
        values.put(name, list);
    }

    /**
     * Get the size.
     * 
     * @return A size.
     */
    @Native("$this.size")
    public int size() {
        int size = 0;
        for (List<String> list : values.values()) {
            size += list.size();
        }
        return size;
    }

    /**
     * Sort the values.
     */
    @Native("$this.sort()")
    public void sort() {
        // keep the insertion order in the Java fallback
    }

    /**
     * Parse the query.
     * 
     * @param query A query.
     * @return Values.
     */
    private static Map<String, List<String>> parse(String query) {
        Map<String, List<String>> values = new LinkedHashMap();
        if (query == null || query.isEmpty()) {
            return values;
        }
        String text = query.charAt(0) == '?' ? query.substring(1) : query;
        int start = 0;
        for (int i = 0; i <= text.length(); i++) {
            if (i == text.length() || text.charAt(i) == '&') {
                if (i > start) {
                    String pair = text.substring(start, i);
                    int index = pair.indexOf("=");
                    String name = index < 0 ? pair : pair.substring(0, index);
                    String value = index < 0 ? "" : pair.substring(index + 1);
                    List<String> list = values.get(decode(name));
                    if (list == null) {
                        list = new ArrayList();
                        values.put(decode(name), list);
                    }
                    list.add(decode(value));
                }
                start = i + 1;
            }
        }
        return values;
    }

    /**
     * Decode the text.
     * 
     * @param text A text.
     * @return A decoded text.
     */
    private static String decode(String text) {
        try {
            return URLDecoder.decode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return text;
        }
    }

    /**
     * Encode the text.
     * 
     * @param text A text.
     * @return An encoded text.
     */
    private static String encode(String text) {
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return text;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : values.entrySet()) {
            for (String value : entry.getValue()) {
                if (builder.length() > 0) {
                    builder.append('&');
                }
                builder.append(encode(entry.getKey())).append('=').append(encode(value));
            }
        }
        return builder.toString();
    }
}
