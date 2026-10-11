/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder.js;

/**
 * The JavaScript runtime that emulates the Java standard library APIs which the JavaScript coder
 * maps to JavaScript.
 * <p>
 * Currently only {@code java.util.List} and {@code java.util.Map} (and their common implementations)
 * are supported. The runtime is emitted only when the decompiled code actually references one of
 * them, so the output of the code which does not use collections remains unchanged.
 * </p>
 */
final class JavascriptRuntime {

    /** The standard runtime source code. */
    static final String Standard = """
            class List {
                constructor(...args) {
                    this.elements = [];
                    if (args.length !== 0) {
                        const source = args[0];
                        if (source !== null && source !== undefined && typeof source !== "number") {
                            for (const value of source) {
                                this.elements.push(value);
                            }
                        }
                    }
                }

                add(...args) {
                    if (args.length === 1) {
                        this.elements.push(args[0]);
                        return true;
                    }
                    this.elements.splice(args[0], 0, args[1]);
                }

                get(index) {
                    this.check(index);
                    return this.elements[index];
                }

                set(index, value) {
                    this.check(index);
                    const previous = this.elements[index];
                    this.elements[index] = value;
                    return previous;
                }

                remove(value) {
                    const index = this.indexOf(value);
                    if (index < 0) {
                        return false;
                    }
                    this.elements.splice(index, 1);
                    return true;
                }

                removeAt(index) {
                    this.check(index);
                    return this.elements.splice(index, 1)[0];
                }

                size() {
                    return this.elements.length;
                }

                isEmpty() {
                    return this.elements.length === 0;
                }

                contains(value) {
                    return this.indexOf(value) >= 0;
                }

                indexOf(value) {
                    return this.elements.findIndex(x => List.$equals(x, value));
                }

                lastIndexOf(value) {
                    for (let index = this.elements.length - 1; 0 <= index; index--) {
                        if (List.$equals(this.elements[index], value)) {
                            return index;
                        }
                    }
                    return -1;
                }

                clear() {
                    this.elements.length = 0;
                }

                addAll(...args) {
                    if (args.length === 1) {
                        let changed = false;
                        for (const value of args[0]) {
                            this.elements.push(value);
                            changed = true;
                        }
                        return changed;
                    }
                    let index = args[0];
                    for (const value of args[1]) {
                        this.elements.splice(index++, 0, value);
                    }
                    return true;
                }

                removeAll(collection) {
                    let changed = false;
                    for (const value of collection) {
                        let index;
                        while (0 <= (index = this.indexOf(value))) {
                            this.elements.splice(index, 1);
                            changed = true;
                        }
                    }
                    return changed;
                }

                retainAll(collection) {
                    const retained = [];
                    for (const value of this.elements) {
                        if (List.$contains(collection, value)) {
                            retained.push(value);
                        }
                    }
                    const changed = retained.length !== this.elements.length;
                    this.elements = retained;
                    return changed;
                }

                containsAll(collection) {
                    for (const value of collection) {
                        if (!this.contains(value)) {
                            return false;
                        }
                    }
                    return true;
                }

                toArray(array) {
                    if (array === undefined || array === null) {
                        return this.elements.slice();
                    }
                    for (let index = 0; index < this.elements.length; index++) {
                        array[index] = this.elements[index];
                    }
                    return array;
                }

                iterator() {
                    let index = 0;
                    const elements = this.elements;
                    return {
                        hasNext() { return index < elements.length; },
                        next() { return elements[index++]; },
                        remove() { elements.splice(--index, 1); }
                    };
                }

                forEach(action) {
                    for (const value of this.elements) {
                        action(value);
                    }
                }

                equals(other) {
                    if (other instanceof List) {
                        if (this.elements.length !== other.elements.length) {
                            return false;
                        }
                        for (let index = 0; index < this.elements.length; index++) {
                            if (!List.$equals(this.elements[index], other.elements[index])) {
                                return false;
                            }
                        }
                        return true;
                    }
                    return false;
                }

                hashCode() {
                    let hash = 1;
                    for (const value of this.elements) {
                        hash = (hash * 31 + List.$hash(value)) | 0;
                    }
                    return hash;
                }

                toString() {
                    return "[" + this.elements.map(List.$string).join(", ") + "]";
                }

                [Symbol.iterator]() {
                    return this.elements[Symbol.iterator]();
                }

                check(index) {
                    if (index < 0 || this.elements.length <= index) {
                        throw new Error("IndexOutOfBoundsException: Index " + index);
                    }
                }

                static of(...values) {
                    const list = new List();
                    if (values.length === 1 && Array.isArray(values[0])) {
                        list.elements = values[0].slice();
                    } else {
                        list.elements = values.slice();
                    }
                    return list;
                }

                static copyOf(collection) {
                    return new List(collection);
                }

                static $equals(left, right) {
                    if (left === right) {
                        return true;
                    }
                    if (left === null || left === undefined || right === null || right === undefined) {
                        return false;
                    }
                    return typeof left.equals === "function" && left.equals(right);
                }

                static $contains(collection, value) {
                    for (const element of collection) {
                        if (List.$equals(element, value)) {
                            return true;
                        }
                    }
                    return false;
                }

                static $hash(value) {
                    if (value === null || value === undefined) {
                        return 0;
                    }
                    if (typeof value.hashCode === "function") {
                        return value.hashCode();
                    }
                    if (typeof value === "number") {
                        return value | 0;
                    }
                    const text = String(value);
                    let hash = 0;
                    for (let index = 0; index < text.length; index++) {
                        hash = (hash * 31 + text.charCodeAt(index)) | 0;
                    }
                    return hash;
                }

                static $string(value) {
                    return value === null || value === undefined ? "null" : String(value);
                }
            }

            class ArrayList extends List {}
            class LinkedList extends List {}
            class Vector extends List {}
            class Stack extends List {
                push(value) {
                    this.elements.push(value);
                    return value;
                }
                pop() {
                    return this.elements.pop();
                }
                peek() {
                    return this.elements[this.elements.length - 1];
                }
            }

            class Entry {
                constructor(key, value) {
                    this.key = key;
                    this.value = value;
                }
                getKey() {
                    return this.key;
                }
                getValue() {
                    return this.value;
                }
                setValue(value) {
                    const previous = this.value;
                    this.value = value;
                    return previous;
                }
                equals(other) {
                    return other instanceof Entry && List.$equals(this.key, other.key) && List.$equals(this.value, other.value);
                }
                hashCode() {
                    return List.$hash(this.key) ^ List.$hash(this.value);
                }
                toString() {
                    return List.$string(this.key) + "=" + List.$string(this.value);
                }
            }

            class Map {
                constructor(...args) {
                    this.$keys = [];
                    this.$values = [];
                    if (args.length !== 0) {
                        const source = args[0];
                        if (source instanceof Map) {
                            for (let index = 0; index < source.$keys.length; index++) {
                                this.put(source.$keys[index], source.$values[index]);
                            }
                        } else if (source !== null && source !== undefined && typeof source !== "number") {
                            for (const entry of source) {
                                this.put(entry.getKey(), entry.getValue());
                            }
                        }
                    }
                }

                indexOfKey(key) {
                    return this.$keys.findIndex(x => List.$equals(x, key));
                }

                put(key, value) {
                    const index = this.indexOfKey(key);
                    if (index < 0) {
                        this.$keys.push(key);
                        this.$values.push(value);
                        return null;
                    }
                    const previous = this.$values[index];
                    this.$values[index] = value;
                    return previous;
                }

                get(key) {
                    const index = this.indexOfKey(key);
                    return index < 0 ? null : this.$values[index];
                }

                getOrDefault(key, defaultValue) {
                    const index = this.indexOfKey(key);
                    return index < 0 ? defaultValue : this.$values[index];
                }

                remove(key) {
                    const index = this.indexOfKey(key);
                    if (index < 0) {
                        return null;
                    }
                    this.$keys.splice(index, 1);
                    return this.$values.splice(index, 1)[0];
                }

                containsKey(key) {
                    return this.indexOfKey(key) >= 0;
                }

                containsValue(value) {
                    for (const current of this.$values) {
                        if (List.$equals(current, value)) {
                            return true;
                        }
                    }
                    return false;
                }

                putAll(other) {
                    if (other instanceof Map) {
                        for (let index = 0; index < other.$keys.length; index++) {
                            this.put(other.$keys[index], other.$values[index]);
                        }
                    } else {
                        for (const entry of other) {
                            this.put(entry.getKey(), entry.getValue());
                        }
                    }
                }

                size() {
                    return this.$keys.length;
                }

                isEmpty() {
                    return this.$keys.length === 0;
                }

                clear() {
                    this.$keys.length = 0;
                    this.$values.length = 0;
                }

                keySet() {
                    const list = new List();
                    list.elements = this.$keys.slice();
                    return list;
                }

                values() {
                    const list = new List();
                    list.elements = this.$values.slice();
                    return list;
                }

                entrySet() {
                    const list = new List();
                    for (let index = 0; index < this.$keys.length; index++) {
                        list.elements.push(new Entry(this.$keys[index], this.$values[index]));
                    }
                    return list;
                }

                forEach(action) {
                    for (let index = 0; index < this.$keys.length; index++) {
                        action(this.$keys[index], this.$values[index]);
                    }
                }

                equals(other) {
                    if (other instanceof Map) {
                        if (this.$keys.length !== other.$keys.length) {
                            return false;
                        }
                        for (let index = 0; index < this.$keys.length; index++) {
                            const found = other.indexOfKey(this.$keys[index]);
                            if (found < 0 || !List.$equals(this.$values[index], other.$values[found])) {
                                return false;
                            }
                        }
                        return true;
                    }
                    return false;
                }

                hashCode() {
                    let hash = 0;
                    for (let index = 0; index < this.$keys.length; index++) {
                        hash = (hash + (List.$hash(this.$keys[index]) ^ List.$hash(this.$values[index]))) | 0;
                    }
                    return hash;
                }

                toString() {
                    const parts = [];
                    for (let index = 0; index < this.$keys.length; index++) {
                        parts.push(List.$string(this.$keys[index]) + "=" + List.$string(this.$values[index]));
                    }
                    return "{" + parts.join(", ") + "}";
                }

                static of(...args) {
                    const map = new Map();
                    for (let index = 0; index < args.length; index += 2) {
                        map.put(args[index], args[index + 1]);
                    }
                    return map;
                }

                static ofEntries(...entries) {
                    const map = new Map();
                    for (const entry of entries) {
                        map.put(entry.getKey(), entry.getValue());
                    }
                    return map;
                }

                static copyOf(other) {
                    return new Map(other);
                }
            }

            class HashMap extends Map {}
            class LinkedHashMap extends Map {}
            class TreeMap extends Map {}
            class Hashtable extends Map {}""";

    /**
     * Hide constructor.
     */
    private JavascriptRuntime() {
    }
}
