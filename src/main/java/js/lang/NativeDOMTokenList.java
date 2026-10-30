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

import java.util.ArrayList;
import java.util.List;

/**
 * The JavaScript {@code DOMTokenList}.
 */
@Native
public final class NativeDOMTokenList {

    /** The JVM representation. */
    private final List<String> values;

    /**
     * Create the empty token list.
     */
    @Native("typeof document === 'undefined' ? (() => { const values = []; return {get length() { return values.length; }, item: function(index) { return values[index] || null; }, contains: function(token) { return values.indexOf(token) >= 0; }, add: function() { for (let i = 0; i < arguments.length; i++) { if (values.indexOf(arguments[i]) < 0) values.push(arguments[i]); } }, remove: function() { for (let i = 0; i < arguments.length; i++) { const index = values.indexOf(arguments[i]); if (index >= 0) values.splice(index, 1); } }, toggle: function(token, force) { const has = values.indexOf(token) >= 0; const add = force === undefined ? !has : force; if (add && !has) values.push(token); else if (!add && has) values.splice(values.indexOf(token), 1); return add; }, replace: function(oldToken, newToken) { const index = values.indexOf(oldToken); if (index < 0 || values.indexOf(newToken) >= 0) return false; values[index] = newToken; return true; }, get value() { return values.join(' '); }, set value(text) { values.length = 0; const parts = String(text).split(' '); for (const token of parts) { if (token && values.indexOf(token) < 0) values.push(token); } }, toString: function() { return values.join(' '); }}; })() : document.createElement('div').classList")
    public NativeDOMTokenList() {
        this.values = new ArrayList();
    }

    /**
     * Get the length.
     * 
     * @return A length.
     */
    @Native("$this.length")
    public int length() {
        return values.size();
    }

    /**
     * Get the token.
     * 
     * @param index An index.
     * @return A token or null.
     */
    @Native("$this.item($0)")
    public String item(int index) {
        return index < 0 || values.size() <= index ? null : values.get(index);
    }

    /**
     * Check whether the token exists.
     * 
     * @param token A token.
     * @return A result.
     */
    @Native("$this.contains($0)")
    public boolean contains(String token) {
        return values.contains(token);
    }

    /**
     * Add the tokens.
     * 
     * @param tokens Tokens.
     */
    @Native("$this.add(...$0)")
    public void add(String... tokens) {
        for (String token : tokens) {
            if (!values.contains(token)) {
                values.add(token);
            }
        }
    }

    /**
     * Remove the tokens.
     * 
     * @param tokens Tokens.
     */
    @Native("$this.remove(...$0)")
    public void remove(String... tokens) {
        for (String token : tokens) {
            values.remove(token);
        }
    }

    /**
     * Toggle the token.
     * 
     * @param token A token.
     * @return A result.
     */
    @Native("$this.toggle($0)")
    public boolean toggle(String token) {
        if (values.contains(token)) {
            values.remove(token);
            return false;
        }
        values.add(token);
        return true;
    }

    /**
     * Toggle the token.
     * 
     * @param token A token.
     * @param force A result.
     * @return A result.
     */
    @Native("$this.toggle($0, $1)")
    public boolean toggle(String token, boolean force) {
        if (force) {
            if (!values.contains(token)) {
                values.add(token);
            }
            return true;
        }
        values.remove(token);
        return false;
    }

    /**
     * Replace the token.
     * 
     * @param oldToken An old token.
     * @param newToken A new token.
     * @return A result.
     */
    @Native("$this.replace($0, $1)")
    public boolean replace(String oldToken, String newToken) {
        int index = values.indexOf(oldToken);
        if (index < 0 || values.contains(newToken)) {
            return false;
        }
        values.set(index, newToken);
        return true;
    }

    /**
     * Get the value.
     * 
     * @return A value.
     */
    @Native("$this.value")
    public String value() {
        return toString();
    }

    /**
     * Set the value.
     * 
     * @param value A value.
     */
    @Native("$this.value = $0")
    public void value(String value) {
        values.clear();
        String[] tokens = value.split(" ");
        for (String token : tokens) {
            if (!token.isEmpty() && !values.contains(token)) {
                values.add(token);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String value : values) {
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append(value);
        }
        return builder.toString();
    }
}
