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
 * {@link #Intrinsics} provides the primitive operations (string, number, character, long and so on)
 * and is always embedded. {@link #Standard} provides the collection classes and is embedded only
 * when the decompiled code actually references one of them.
 * </p>
 */
public final class JavascriptRuntime {

    /** The intrinsic runtime source code. */
    public static final String Intrinsics = """
            const $rt_equals = (a, b) => a === b || (a !== null && a !== undefined && (
                typeof a.equals === "function" ? a.equals(b) :
                typeof a.equals$Ljava_lang_Object_ === "function" ? a.equals$Ljava_lang_Object_(b) : false));
            const $rt_hashCode = (a) => {
                if (a === null || a === undefined) return 0;
                if (typeof a === "number") return a | 0;
                if (typeof a === "string") {
                    let hash = 0;
                    for (let index = 0; index < a.length; index++) hash = (hash * 31 + a.charCodeAt(index)) | 0;
                    return hash;
                }
                if (typeof a.hashCode === "function") return a.hashCode();
                if (typeof a.hashCode$ === "function") return a.hashCode$();
                return $rt_identityHashCode(a);
            };
            const $rt_toString = (a) => a === null || a === undefined ? "null" : (typeof a.toString$ === "function" ? a.toString$() : typeof a.toString === "function" ? a.toString() : String(a));
            const $rt_sb = () => ({ $value: "" });
            const $rt_sbWith = (value) => ({ $value: value === null || value === undefined ? "null" : String(value) });
            const $rt_sbAppend = (builder, value) => {
                builder.$value += $rt_toString(value);
                return builder;
            };
            const $rt_sbValue = (builder) => builder.$value;
            const $rt_iterator = (o) => typeof o.iterator === "function" ? o.iterator() : typeof o.iterator$ === "function" ? o.iterator$() : null;
            const $rt_hasNext = (iterator) => typeof iterator.hasNext === "function" ? iterator.hasNext() : iterator.hasNext$();
            const $rt_next = (iterator) => typeof iterator.next === "function" ? iterator.next() : iterator.next$();
            const $rt_iterable = (o) => {
                if (o === null || o === undefined) return [];
                if (Array.isArray(o) || typeof o[Symbol.iterator] === "function") return o;
                const list = [];
                const iterator = $rt_iterator(o);
                if (iterator) {
                    while ($rt_hasNext(iterator)) list.push($rt_next(iterator));
                }
                return list;
            };
            const $rt_compareString = (a, b) => {
                a = String(a);
                b = String(b);
                const length = Math.min(a.length, b.length);
                for (let i = 0; i < length; i++) {
                    const diff = a.charCodeAt(i) - b.charCodeAt(i);
                    if (diff !== 0) return diff;
                }
                return a.length - b.length;
            };
            const $rt_compare = (a, b) => {
                if (a === b) return 0;
                if (a === null || a === undefined) return -1;
                if (b === null || b === undefined) return 1;
                if (typeof a === "number" && typeof b === "number") return a < b ? -1 : a > b ? 1 : 0;
                if (typeof a === "string" && typeof b === "string") return $rt_compareString(a, b);
                if (typeof a.compareTo$ === "function") return a.compareTo$(b);
                if (typeof a.compareTo$Ljava_lang_Object_ === "function") return a.compareTo$Ljava_lang_Object_(b);
                if (typeof a.compareTo === "function") return a.compareTo(b);
                const left = String(a);
                const right = String(b);
                return left < right ? -1 : left > right ? 1 : 0;
            };
            const $rt_compareBoolean = (a, b) => a === b ? 0 : a ? 1 : -1;
            const $rt_compareUnsigned = (a, b) => (a >>> 0) < (b >>> 0) ? -1 : (a >>> 0) > (b >>> 0) ? 1 : 0;
            const $rt_doubleCompare = (a, b) => {
                if (Number.isNaN(a)) return Number.isNaN(b) ? 0 : 1;
                if (Number.isNaN(b)) return -1;
                if (a === b) return 0;
                return a < b ? -1 : 1;
            };
            const $rt_char = (c) => typeof c === "number" ? String.fromCharCode(c) : String(c);
            const $rt_charCode = (c) => typeof c === "number" ? c : (c === null || c === undefined ? 0 : String(c).charCodeAt(0));
            const $rt_chars = (text) => String(text).split("");
            const $rt_charsToString = (array, from, to) => (array || []).slice(from === undefined ? 0 : from, to === undefined ? undefined : to).join("");
            const $rt_parseInt = (text, radix) => {
                const value = String(text).trim();
                const base = radix === undefined ? 10 : radix;
                if (value.length === 0 || base < 2 || base > 36 || !/^[+-]?[0-9a-zA-Z]+$/.test(value)) throw new Error("NumberFormatException");
                const number = parseInt(value, base);
                if (Number.isNaN(number) || number < -2147483648 || number > 2147483647) throw new Error("NumberFormatException");
                return number;
            };
            const $rt_parseLong = (text, radix) => {
                const value = String(text).trim();
                const base = radix === undefined ? 10 : radix;
                if (value.length === 0 || base < 2 || base > 36 || !/^[+-]?[0-9a-zA-Z]+$/.test(value)) throw new Error("NumberFormatException");
                const number = parseInt(value, base);
                if (Number.isNaN(number)) throw new Error("NumberFormatException");
                return number;
            };
            const $rt_parseIntRange = (text, begin, end, radix) => $rt_parseInt(String(text).substring(begin, end), radix);
            const $rt_parseLongRange = (text, begin, end, radix) => $rt_parseLong(String(text).substring(begin, end), radix);
            const $rt_parseDouble = (text) => {
                const value = String(text).trim();
                if (value.length === 0) throw new Error("NumberFormatException");
                const number = Number(value);
                if (Number.isNaN(number) && !/^[+-]?NaN$/i.test(value)) throw new Error("NumberFormatException");
                return number;
            };
            const $rt_parseBoolean = (text) => text !== null && text !== undefined && String(text).toLowerCase() === "true";
            const $rt_toRadixString = (value, radix) => Math.trunc(Number(value)).toString(radix);
            const $rt_unsignedString = (value, radix, bits) => {
                let number = Number(value);
                if (bits === 32) {
                    number = number >>> 0;
                } else if (number < 0) {
                    number += 18446744073709551616;
                }
                return Math.trunc(number).toString(radix);
            };
            const $rt_doubleToString = (value) => {
                if (Number.isNaN(value)) return "NaN";
                if (value === Infinity) return "Infinity";
                if (value === -Infinity) return "-Infinity";
                if (Number.isInteger(value) && Math.abs(value) < 1e21) return value.toFixed(1);
                return String(value);
            };
            const $rt_numberHash = (value) => typeof value === "boolean" ? (value ? 1231 : 1237) : (Math.trunc(Number(value)) | 0);
            const $rt_bitCount = (value) => {
                let n = value | 0;
                n = n - ((n >> 1) & 0x55555555);
                n = (n & 0x33333333) + ((n >> 2) & 0x33333333);
                return (((n + (n >> 4)) & 0x0F0F0F0F) * 0x01010101) >> 24;
            };
            const $rt_highestOneBit = (value) => {
                const n = value | 0;
                return n === 0 ? 0 : 1 << (31 - Math.clz32(n));
            };
            const $rt_lowestOneBit = (value) => (value | 0) & -(value | 0);
            const $rt_numberOfLeadingZeros = (value) => Math.clz32(value | 0);
            const $rt_numberOfTrailingZeros = (value) => {
                const n = value | 0;
                return n === 0 ? 32 : 31 - Math.clz32(n & -n);
            };
            const $rt_reverseBits = (value) => {
                let n = value | 0;
                n = ((n >>> 1) & 0x55555555) | ((n & 0x55555555) << 1);
                n = ((n >>> 2) & 0x33333333) | ((n & 0x33333333) << 2);
                n = ((n >>> 4) & 0x0F0F0F0F) | ((n & 0x0F0F0F0F) << 4);
                n = ((n >>> 8) & 0x00FF00FF) | ((n & 0x00FF00FF) << 8);
                return (n >>> 16) | (n << 16);
            };
            const $rt_reverseBytes = (value) => {
                const n = value | 0;
                return ((n & 0xFF) << 24) | ((n & 0xFF00) << 8) | ((n >>> 8) & 0xFF00) | ((n >>> 24) & 0xFF);
            };
            const $rt_rotateLeft = (value, distance) => {
                const n = value | 0;
                const d = distance & 31;
                return (n << d) | (n >>> (32 - d));
            };
            const $rt_rotateRight = (value, distance) => {
                const n = value | 0;
                const d = distance & 31;
                return (n >>> d) | (n << (32 - d));
            };
            const $rt_divideUnsigned = (a, b) => ((a >>> 0) / (b >>> 0)) | 0;
            const $rt_remainderUnsigned = (a, b) => ((a >>> 0) % (b >>> 0)) | 0;
            const $rt_doubleToLongBits = (value) => {
                const buffer = new ArrayBuffer(8);
                const view = new DataView(buffer);
                view.setFloat64(0, value);
                return Number(view.getBigInt64(0));
            };
            const $rt_longBitsToDouble = (value) => {
                const buffer = new ArrayBuffer(8);
                const view = new DataView(buffer);
                view.setBigInt64(0, BigInt(Math.trunc(value)));
                return view.getFloat64(0);
            };
            const $rt_toRadians = (value) => value * Math.PI / 180;
            const $rt_toDegrees = (value) => value * 180 / Math.PI;
            const $rt_rint = (value) => {
                if (!Number.isFinite(value)) return value;
                const floor = Math.floor(value);
                const fraction = value - floor;
                if (fraction < 0.5) return floor;
                if (fraction > 0.5) return floor + 1;
                return floor % 2 === 0 ? floor : floor + 1;
            };
            const $rt_ieeeRemainder = (x, y) => x - $rt_rint(x / y) * y;
            const $rt_ulp = (value) => {
                if (Number.isNaN(value)) return NaN;
                const x = Math.abs(value);
                if (x === Infinity) return Infinity;
                if (x === 0) return Number.MIN_VALUE;
                return Math.pow(2, Math.floor(Math.log2(x)) - 52);
            };
            const $rt_nextUp = (value) => {
                if (Number.isNaN(value) || value === Infinity) return value;
                if (value === 0) return Number.MIN_VALUE;
                return value + $rt_ulp(value);
            };
            const $rt_nextDown = (value) => {
                if (Number.isNaN(value) || value === -Infinity) return value;
                if (value === 0) return -Number.MIN_VALUE;
                return value - $rt_ulp(value);
            };
            const $rt_nextAfter = (value, direction) => direction > value ? $rt_nextUp(value) : direction < value ? $rt_nextDown(value) : direction;
            const $rt_getExponent = (value) => {
                if (!Number.isFinite(value) || value === 0) return 0;
                return Math.floor(Math.log2(Math.abs(value)));
            };
            const $rt_scalb = (value, scale) => value * Math.pow(2, scale);
            const $rt_floorDiv = (a, b) => Math.floor(a / b);
            const $rt_floorMod = (a, b) => a - Math.floor(a / b) * b;
            const $rt_ceilDiv = (a, b) => -Math.floor(-a / b);
            const $rt_ceilMod = (a, b) => a - $rt_ceilDiv(a, b) * b;
            const $rt_clamp = (value, min, max) => value < min ? min : value > max ? max : value;
            const $rt_isDigit = (c) => {
                const code = $rt_charCode(c);
                return (code >= 48 && code <= 57) || (code >= 0x0660 && code <= 0x0669) || (code >= 0x06F0 && code <= 0x06F9);
            };
            const $rt_isLetter = (c) => {
                const code = $rt_charCode(c);
                return (code >= 65 && code <= 90) || (code >= 97 && code <= 122)
                        || (code >= 0xC0 && code <= 0x24F && code !== 0xD7 && code !== 0xF7);
            };
            const $rt_isLetterOrDigit = (c) => $rt_isLetter(c) || $rt_isDigit(c);
            const $rt_isWhitespace = (c) => {
                const code = $rt_charCode(c);
                return code === 32 || (code >= 9 && code <= 13) || code === 0x1680 || (code >= 0x2000 && code <= 0x2006)
                        || (code >= 0x2008 && code <= 0x200a) || code === 0x2028 || code === 0x2029 || code === 0x205f || code === 0x3000;
            };
            const $rt_isSpaceChar = (c) => {
                const code = $rt_charCode(c);
                return code === 32 || code === 0xA0 || code === 0x1680 || (code >= 0x2000 && code <= 0x200A) || code === 0x202F || code === 0x205F || code === 0x3000;
            };
            const $rt_charUpper = (c) => $rt_char(c).toUpperCase().charAt(0);
            const $rt_charLower = (c) => $rt_char(c).toLowerCase().charAt(0);
            const $rt_charTitle = (c) => $rt_charUpper(c);
            const $rt_codePointUpper = (c) => String.fromCodePoint(c).toUpperCase().codePointAt(0);
            const $rt_codePointLower = (c) => String.fromCodePoint(c).toLowerCase().codePointAt(0);
            const $rt_codePointTitle = (c) => $rt_codePointUpper(c);
            const $rt_isUpperCase = (c) => {
                const text = $rt_char(c);
                return text !== text.toLowerCase() && text === text.toUpperCase();
            };
            const $rt_isLowerCase = (c) => {
                const text = $rt_char(c);
                return text !== text.toUpperCase() && text === text.toLowerCase();
            };
            const $rt_isTitleCase = (c) => false;
            const $rt_charDigit = (c, radix) => {
                const code = $rt_charCode(c);
                let value;
                if (code >= 48 && code <= 57) value = code - 48;
                else if (code >= 65 && code <= 90) value = code - 55;
                else if (code >= 97 && code <= 122) value = code - 87;
                else return -1;
                return value < radix ? value : -1;
            };
            const $rt_charNumeric = (c) => $rt_charDigit(c, 36);
            const $rt_forDigit = (digit, radix) => digit < 0 || digit >= radix ? "\\0" : String.fromCharCode(digit < 10 ? 48 + digit : 87 + digit);
            const $rt_compareChar = (a, b) => $rt_charCode(a) - $rt_charCode(b);
            const $rt_isSurrogate = (c) => { const code = $rt_charCode(c); return code >= 0xD800 && code <= 0xDFFF; };
            const $rt_isHighSurrogate = (c) => { const code = $rt_charCode(c); return code >= 0xD800 && code <= 0xDBFF; };
            const $rt_isLowSurrogate = (c) => { const code = $rt_charCode(c); return code >= 0xDC00 && code <= 0xDFFF; };
            const $rt_isSurrogatePair = (a, b) => $rt_isHighSurrogate(a) && $rt_isLowSurrogate(b);
            const $rt_isBmpCodePoint = (c) => c >= 0 && c <= 0xFFFF;
            const $rt_isValidCodePoint = (c) => c >= 0 && c <= 0x10FFFF;
            const $rt_charCount = (c) => c >= 0x10000 ? 2 : 1;
            const $rt_toCodePoint = (high, low) => 0x10000 + (($rt_charCode(high) - 0xD800) << 10) + ($rt_charCode(low) - 0xDC00);
            const $rt_highSurrogate = (c) => String.fromCharCode(0xD800 + ((c - 0x10000) >> 10));
            const $rt_lowSurrogate = (c) => String.fromCharCode(0xDC00 + ((c - 0x10000) & 0x3FF));
            const $rt_codePointAt = (value, index) => Array.isArray(value) ? $rt_codePointAt(value.join(""), index) : String(value).codePointAt(index);
            const $rt_codePointBefore = (value, index) => {
                const text = Array.isArray(value) ? value.join("") : String(value);
                const low = text.charCodeAt(index - 1);
                if (low >= 0xDC00 && low <= 0xDFFF && index >= 2) {
                    const high = text.charCodeAt(index - 2);
                    if (high >= 0xD800 && high <= 0xDBFF) return 0x10000 + ((high - 0xD800) << 10) + (low - 0xDC00);
                }
                return low;
            };
            const $rt_codePointCount = (value, begin, end) => {
                const text = Array.isArray(value) ? value.join("") : String(value);
                let count = end - begin;
                for (let i = begin; i < end - 1; i++) {
                    const code = text.charCodeAt(i);
                    if (code >= 0xD800 && code <= 0xDBFF) {
                        const low = text.charCodeAt(i + 1);
                        if (low >= 0xDC00 && low <= 0xDFFF) {
                            count--;
                            i++;
                        }
                    }
                }
                return count;
            };
            const $rt_offsetByCodePoints = (value, index, offset) => {
                const text = Array.isArray(value) ? value.join("") : String(value);
                let position = index;
                while (offset > 0) {
                    position += text.charCodeAt(position) >= 0xD800 && text.charCodeAt(position) <= 0xDBFF ? 2 : 1;
                    offset--;
                }
                while (offset < 0) {
                    position -= 1;
                    if (position > 0 && text.charCodeAt(position) >= 0xDC00 && text.charCodeAt(position) <= 0xDFFF
                            && text.charCodeAt(position - 1) >= 0xD800 && text.charCodeAt(position - 1) <= 0xDBFF) position--;
                    offset++;
                }
                return position;
            };
            const $rt_codePointToChars = (c, destination, index) => {
                if (c < 0x10000) {
                    destination[index] = String.fromCharCode(c);
                    return 1;
                }
                destination[index] = $rt_highSurrogate(c);
                destination[index + 1] = $rt_lowSurrogate(c);
                return 2;
            };
            const $rt_reverseBytes16 = (c) => {
                const code = $rt_charCode(c);
                return String.fromCharCode(((code & 0xFF) << 8) | ((code >>> 8) & 0xFF));
            };
            const $rt_isISOControl = (c) => { const code = $rt_charCode(c); return code <= 0x1F || (code >= 0x7F && code <= 0x9F); };
            const $rt_isJavaIdentifier = (c, start) => {
                const code = $rt_charCode(c);
                return code === 36 || code === 95 || $rt_isLetter(c) || (start === false && $rt_isDigit(c));
            };
            const $rt_join = (separator, values) => {
                if (values === null || values === undefined) return "";
                if (!Array.isArray(values)) {
                    values = $rt_listValues(values);
                }
                return values.map(value => $rt_toString(value)).join(separator);
            };
            const $rt_format = (format, ...args) => {
                if (args.length === 1 && Array.isArray(args[0])) args = args[0];
                let index = 0;
                return String(format).replace(/%([0-9]+\\$)?([-#+ 0,(<]*)?(\\d+)?(?:\\.(\\d+))?([a-zA-Z%])/g, (match, position, flags, width, precision, type) => {
                    if (type === "%") return "%";
                    if (type === "n") return "\\n";
                    let value = position ? args[parseInt(position) - 1] : args[index++];
                    switch (type) {
                    case "d":
                        value = String(Math.trunc(Number(value)));
                        break;
                    case "o":
                        value = (Math.trunc(Number(value)) >>> 0).toString(8);
                        break;
                    case "x":
                    case "X":
                        value = (Math.trunc(Number(value)) >>> 0).toString(16);
                        if (type === "X") value = value.toUpperCase();
                        break;
                    case "f":
                        value = Number(value).toFixed(precision === undefined ? 6 : parseInt(precision));
                        break;
                    case "e":
                    case "E":
                        value = Number(value).toExponential(precision === undefined ? 6 : parseInt(precision));
                        if (type === "E") value = value.toUpperCase();
                        break;
                    case "g":
                    case "G":
                        value = String(Number(value));
                        if (type === "G") value = value.toUpperCase();
                        break;
                    case "b":
                        value = value === null || value === false || value === undefined ? "false" : "true";
                        break;
                    case "c":
                        value = $rt_char(value);
                        break;
                    default:
                        value = $rt_toString(value);
                        break;
                    }
                    if (width) {
                        const length = parseInt(width);
                        const padding = flags && flags.includes("0") ? "0" : " ";
                        while (value.length < length) value = padding + value;
                    }
                    return value;
                });
            };
            const $rt_replaceAll = (text, pattern, replacement, all) => String(text).replace(new RegExp(pattern, all ? "g" : ""), replacement);
            const $rt_matches = (text, pattern) => new RegExp("^(?:" + pattern + ")$").test(String(text));
            const $rt_sbReverse = (builder) => { builder.$value = builder.$value.split("").reverse().join(""); return builder; };
            const $rt_sbAppendChars = (builder, chars, offset, length) => {
                const start = offset === undefined ? 0 : offset;
                builder.$value += (chars || []).slice(start, length === undefined ? undefined : start + length).join("");
                return builder;
            };
            const $rt_sbInsertChars = (builder, index, chars, offset, length) => $rt_sbInsert(builder, index, (chars || []).slice(offset, offset + length).join(""));
            const $rt_sbInsert = (builder, index, value) => {
                const text = value === null || value === undefined ? "null" : String(value);
                builder.$value = builder.$value.slice(0, index) + text + builder.$value.slice(index);
                return builder;
            };
            const $rt_sbDelete = (builder, start, end) => {
                builder.$value = builder.$value.slice(0, start) + builder.$value.slice(end);
                return builder;
            };
            const $rt_sbDeleteCharAt = (builder, index) => $rt_sbDelete(builder, index, index + 1);
            const $rt_sbReplace = (builder, start, end, value) => {
                builder.$value = builder.$value.slice(0, start) + String(value) + builder.$value.slice(end);
                return builder;
            };
            const $rt_sbSetCharAt = (builder, index, value) => {
                builder.$value = builder.$value.slice(0, index) + $rt_char(value) + builder.$value.slice(index + 1);
                return builder;
            };
            const $rt_sbSetLength = (builder, length) => {
                if (length <= builder.$value.length) {
                    builder.$value = builder.$value.slice(0, length);
                } else {
                    builder.$value += String.fromCharCode(0).repeat(length - builder.$value.length);
                }
                return builder;
            };
            const $rt_sbCharAt = (builder, index) => builder.$value.charAt(index);
            const $rt_sbIndexOf = (builder, value, from) => from === undefined ? builder.$value.indexOf($rt_char(value)) : builder.$value.indexOf($rt_char(value), from);
            const $rt_sbLastIndexOf = (builder, value, from) => from === undefined ? builder.$value.lastIndexOf($rt_char(value)) : builder.$value.lastIndexOf($rt_char(value), from);
            const $rt_sbSubstring = (builder, start, end) => builder.$value.substring(start, end);
            const $rt_sbCapacity = (builder) => builder.$value.length;
            const $rt_sbGetChars = (builder, start, end, destination, index) => {
                const text = builder.$value.substring(start, end);
                for (let i = 0; i < text.length; i++) destination[index + i] = text.charAt(i);
            };
            const $rt_arraycopy = (source, sourceIndex, destination, destinationIndex, length) => {
                if (source === null || destination === null) throw new Error("NullPointerException");
                const copy = source.slice(sourceIndex, sourceIndex + length);
                for (let i = 0; i < copy.length; i++) destination[destinationIndex + i] = copy[i];
            };
            const $rt_arrayToString = (array, deep) => {
                if (array === null || array === undefined) return "null";
                const values = [];
                for (let i = 0; i < array.length; i++) {
                    const value = array[i];
                    if (Array.isArray(value)) {
                        values.push(deep ? $rt_arrayToString(value, true) : String(value));
                    } else {
                        values.push($rt_toString(value));
                    }
                }
                return "[" + values.join(", ") + "]";
            };
            const $rt_sortArray = (array, from, to, comparator) => {
                const start = from === undefined ? 0 : from;
                const end = to === undefined ? array.length : to;
                const values = array.slice(start, end);
                values.sort((a, b) => $rt_compareWith(comparator, a, b));
                for (let i = 0; i < values.length; i++) array[start + i] = values[i];
                return array;
            };
            const $rt_arrayCopyOf = (array, length, initial) => {
                const result = new Array(length).fill(initial);
                const size = Math.min(array.length, length);
                for (let i = 0; i < size; i++) result[i] = array[i];
                return result;
            };
            const $rt_arrayCopyOfRange = (array, from, to, initial) => {
                const length = Math.max(0, to - from);
                const result = new Array(length).fill(initial);
                for (let i = from; i < Math.min(to, array.length); i++) result[i - from] = array[i];
                return result;
            };
            const $rt_arrayFill = (array, from, to, value) => {
                for (let i = from; i < to; i++) array[i] = value;
                return array;
            };
            const $rt_arrayEquals = (left, right, deep) => {
                if (left === right) return true;
                if (left === null || left === undefined || right === null || right === undefined) return false;
                if (left.length !== right.length) return false;
                for (let i = 0; i < left.length; i++) {
                    if (deep && Array.isArray(left[i]) && Array.isArray(right[i])) {
                        if (!$rt_arrayEquals(left[i], right[i], true)) return false;
                    } else if (!$rt_equals(left[i], right[i])) {
                        return false;
                    }
                }
                return true;
            };
            const $rt_arrayHash = (array, deep) => {
                if (array === null || array === undefined) return 0;
                let hash = 1;
                for (let i = 0; i < array.length; i++) {
                    hash = (hash * 31 + (deep && Array.isArray(array[i]) ? $rt_arrayHash(array[i], true) : $rt_hashCode(array[i]))) | 0;
                }
                return hash;
            };
            const $rt_compareWith = (comparator, a, b) => {
                if (comparator === null || comparator === undefined) return $rt_compare(a, b);
                if (typeof comparator === "function") return comparator(a, b);
                if (typeof comparator.compare$Ljava_lang_Object_Ljava_lang_Object_ === "function") return comparator.compare$Ljava_lang_Object_Ljava_lang_Object_(a, b);
                return comparator.compare(a, b);
            };
            const $rt_listValues = (list) => {
                if (list === null || list === undefined) return [];
                if (Array.isArray(list)) return list.slice();
                if (list.array !== undefined && list.size !== undefined) return list.array.slice(0, list.size);
                const values = [];
                const iterator = $rt_iterator(list);
                if (iterator) {
                    while ($rt_hasNext(iterator)) values.push($rt_next(iterator));
                } else {
                    values.push(...Array.from($rt_iterable(list)));
                }
                return values;
            };
            const $rt_listReplace = (list, values) => {
                if (list.array !== undefined && list.size !== undefined) {
                    list.array = values;
                    list.size = values.length;
                } else {
                    if (typeof list.clear$ === "function") list.clear$();
                    if (typeof list.add$Ljava_lang_Object_ === "function") {
                        for (const value of values) list.add$Ljava_lang_Object_(value);
                    }
                }
                return list;
            };
            const $rt_sort = (list, comparator) => $rt_listReplace(list, $rt_listValues(list).sort((a, b) => $rt_compareWith(comparator, a, b)));
            const $rt_reverse = (list) => $rt_listReplace(list, $rt_listValues(list).reverse());
            const $rt_swap = (list, first, second) => {
                const values = $rt_listValues(list);
                const value = values[first];
                values[first] = values[second];
                values[second] = value;
                return $rt_listReplace(list, values);
            };
            const $rt_fillList = (list, value) => $rt_listReplace(list, $rt_listValues(list).map(() => value));
            const $rt_copyList = (destination, source) => {
                const values = $rt_listValues(source);
                if (destination.array !== undefined && destination.size !== undefined) {
                    for (let i = 0; i < values.length; i++) destination.array[i] = values[i];
                } else if (typeof destination.set$intLjava_lang_Object_ === "function") {
                    for (let i = 0; i < values.length; i++) destination.set$intLjava_lang_Object_(i, values[i]);
                }
                return undefined;
            };
            const $rt_max = (collection, comparator) => {
                const values = $rt_listValues(collection);
                if (values.length === 0) throw new Error("NoSuchElementException");
                return values.reduce((a, b) => $rt_compareWith(comparator, a, b) >= 0 ? a : b);
            };
            const $rt_min = (collection, comparator) => {
                const values = $rt_listValues(collection);
                if (values.length === 0) throw new Error("NoSuchElementException");
                return values.reduce((a, b) => $rt_compareWith(comparator, a, b) <= 0 ? a : b);
            };
            const $rt_binarySearch = (list, key, comparator) => {
                const values = $rt_listValues(list);
                let low = 0;
                let high = values.length - 1;
                while (low <= high) {
                    const middle = (low + high) >>> 1;
                    const diff = $rt_compareWith(comparator, values[middle], key);
                    if (diff < 0) low = middle + 1;
                    else if (diff > 0) high = middle - 1;
                    else return middle;
                }
                return -(low + 1);
            };
            const $rt_reverseOrder = (comparator) => (a, b) => -$rt_compareWith(comparator, a, b);
            const $rt_nanoTime = () => Date.now() * 1000000;
            const $rt_identityIds = new WeakMap();
            let $rt_identityNext = 1;
            const $rt_identityHashCode = (value) => {
                if (value === null) return 0;
                if (typeof value === "number") return value | 0;
                if (typeof value === "string") return $rt_hashCode(value);
                if (typeof value !== "object" && typeof value !== "function") return 0;
                let id = $rt_identityIds.get(value);
                if (id === undefined) {
                    id = $rt_identityNext++;
                    $rt_identityIds.set(value, id);
                }
                return id;
            };
            const $rt_l = (x) => typeof x === "bigint" ? x : BigInt(Math.trunc(x));
            const $rt_lr = (x) => BigInt.asIntN(64, x);
            const $rt_ltoi = (x) => Number(BigInt.asIntN(32, $rt_l(x)));
            const $rt_ltod = (x) => Number(x);
            const $rt_dtol = (x) => {
                if (typeof x === "bigint") return x;
                if (Number.isNaN(x)) return 0n;
                if (x <= -9223372036854775808) return -9223372036854775808n;
                if (x >= 9223372036854775808) return 9223372036854775807n;
                return BigInt(Math.trunc(x));
            };
            const $rt_ldiv = (a, b) => {
                a = $rt_l(a); b = $rt_l(b);
                if (b === 0n) throw new Error("ArithmeticException");
                return $rt_lr(a / b);
            };
            const $rt_lrem = (a, b) => {
                a = $rt_l(a); b = $rt_l(b);
                if (b === 0n) throw new Error("ArithmeticException");
                return a % b;
            };
            const $rt_lushr = (a, b) => $rt_lr(BigInt.asUintN(64, $rt_l(a)) >> ($rt_l(b) & 63n));
            const $rt_lcmp = (a, b) => {
                a = $rt_l(a); b = $rt_l(b);
                return a < b ? -1 : a > b ? 1 : 0;
            };
            const $rt_lhash = (x) => {
                x = $rt_l(x);
                return Number(BigInt.asIntN(32, x ^ (BigInt.asUintN(64, x) >> 32n)));
            };
            const $rt_ltoString = (x, radix) => $rt_l(x).toString(radix === undefined ? 10 : radix);
            const $rt_lunsigned = (x, radix) => BigInt.asUintN(64, $rt_l(x)).toString(radix === undefined ? 10 : radix);
            const $rt_lparse = (text, radix) => {
                const value = String(text).trim();
                const base = radix === undefined ? 10 : radix;
                if (value.length === 0 || base < 2 || base > 36) throw new Error("NumberFormatException");
                let index = 0;
                let negative = false;
                if (value[0] === "+" || value[0] === "-") {
                    negative = value[0] === "-";
                    index = 1;
                }
                if (index === value.length) throw new Error("NumberFormatException");
                const bigBase = BigInt(base);
                let result = 0n;
                for (; index < value.length; index++) {
                    const code = value.charCodeAt(index);
                    let digit;
                    if (48 <= code && code <= 57) digit = code - 48;
                    else if (97 <= code && code <= 122) digit = code - 87;
                    else if (65 <= code && code <= 90) digit = code - 55;
                    else throw new Error("NumberFormatException");
                    if (digit >= base) throw new Error("NumberFormatException");
                    result = result * bigBase + BigInt(digit);
                }
                if (negative) result = -result;
                if (result < -9223372036854775808n || result > 9223372036854775807n) throw new Error("NumberFormatException");
                return result;
            };
            const $rt_lbitCount = (x) => {
                let value = BigInt.asUintN(64, $rt_l(x));
                let count = 0;
                while (value !== 0n) {
                    count += Number(value & 1n);
                    value >>= 1n;
                }
                return count;
            };
            const $rt_lnumberOfLeadingZeros = (x) => {
                let value = BigInt.asUintN(64, $rt_l(x));
                if (value === 0n) return 64;
                let count = 0;
                while ((value & 0x8000000000000000n) === 0n) {
                    count++;
                    value <<= 1n;
                    value = BigInt.asUintN(64, value);
                }
                return count;
            };
            const $rt_lnumberOfTrailingZeros = (x) => {
                let value = BigInt.asUintN(64, $rt_l(x));
                if (value === 0n) return 64;
                let count = 0;
                while ((value & 1n) === 0n) {
                    count++;
                    value >>= 1n;
                }
                return count;
            };
            const $rt_lreverse = (x) => {
                let value = BigInt.asUintN(64, $rt_l(x));
                let result = 0n;
                for (let i = 0; i < 64; i++) {
                    result = (result << 1n) | (value & 1n);
                    value >>= 1n;
                }
                return $rt_lr(result);
            };
            const $rt_lreverseBytes = (x) => {
                let value = BigInt.asUintN(64, $rt_l(x));
                let result = 0n;
                for (let i = 0; i < 8; i++) {
                    result = (result << 8n) | (value & 0xffn);
                    value >>= 8n;
                }
                return $rt_lr(result);
            };
            const $rt_lrotateLeft = (x, distance) => {
                const shift = BigInt(distance & 63);
                const value = BigInt.asUintN(64, $rt_l(x));
                if (shift === 0n) return $rt_lr(value);
                return $rt_lr((value << shift) | (value >> (64n - shift)));
            };
            const $rt_lrotateRight = (x, distance) => {
                const shift = BigInt(distance & 63);
                const value = BigInt.asUintN(64, $rt_l(x));
                if (shift === 0n) return $rt_lr(value);
                return $rt_lr((value >> shift) | (value << (64n - shift)));
            };
            const $rt_lsignum = (x) => {
                x = $rt_l(x);
                return x > 0n ? 1 : x < 0n ? -1 : 0;
            };
            const $rt_ldivideUnsigned = (a, b) => {
                a = BigInt.asUintN(64, $rt_l(a)); b = BigInt.asUintN(64, $rt_l(b));
                if (b === 0n) throw new Error("ArithmeticException");
                return $rt_lr(a / b);
            };
            const $rt_lremainderUnsigned = (a, b) => {
                a = BigInt.asUintN(64, $rt_l(a)); b = BigInt.asUintN(64, $rt_l(b));
                if (b === 0n) throw new Error("ArithmeticException");
                return $rt_lr(a % b);
            };
            const $rt_lcompareUnsigned = (a, b) => {
                a = BigInt.asUintN(64, $rt_l(a)); b = BigInt.asUintN(64, $rt_l(b));
                return a < b ? -1 : a > b ? 1 : 0;
            };
            const $rt_lfloorDiv = (a, b) => {
                a = $rt_l(a); b = $rt_l(b);
                if (b === 0n) throw new Error("ArithmeticException");
                let quotient = a / b;
                if (a % b !== 0n && (a < 0n) !== (b < 0n)) quotient -= 1n;
                return $rt_lr(quotient);
            };
            const $rt_lfloorMod = (a, b) => {
                a = $rt_l(a); b = $rt_l(b);
                if (b === 0n) throw new Error("ArithmeticException");
                let remainder = a % b;
                if (remainder !== 0n && (remainder < 0n) !== (b < 0n)) remainder += b;
                return remainder;
            };
            const $rt_doubleToLongBitsBig = (value) => {
                const buffer = new ArrayBuffer(8);
                const view = new DataView(buffer);
                view.setFloat64(0, value);
                return view.getBigInt64(0);
            };
            const $rt_longBitsToDoubleBig = (value) => {
                const buffer = new ArrayBuffer(8);
                const view = new DataView(buffer);
                view.setBigInt64(0, $rt_l(value));
                return view.getFloat64(0);
            };
            const $rt_printText = (error, text) => {
                if (typeof process !== "undefined" && process.stdout) {
                    (error ? process.stderr : process.stdout).write(text);
                } else if (typeof console !== "undefined") {
                    if (text.endsWith("\\n")) text = text.substring(0, text.length - 1);
                    (error ? console.error : console.log)(text);
                }
            };
            const $rt_printValue = (value) => {
                if (value === null || value === undefined) return "null";
                if (Array.isArray(value)) return value.join("");
                return String(value);
            };
            const $rt_printStream = (error) => ({
                print(...values) { $rt_printText(error, values.map($rt_printValue).join("")); },
                println(...values) { $rt_printText(error, values.map($rt_printValue).join("") + "\\n"); },
                printf(format, ...args) { $rt_printText(error, $rt_format(format, ...args)); },
                format(format, ...args) { $rt_printText(error, $rt_format(format, ...args)); },
                write(value) { $rt_printText(error, typeof value === "number" ? String.fromCharCode(value) : $rt_printValue(value)); },
                append(value) { this.print(value); return this; },
                flush() {},
                close() {},
                checkError() { return false; }
            });
            const $rt_out = $rt_printStream(false);
            const $rt_err = $rt_printStream(true);""";

    /** The core runtime source code for the class registration based output. */
    static final String Core = """
            const $c = Object.create(null);

            const $rt = (() => {
                const classes = $c;
                const direct = Object.create(null);
                const names = Object.create(null);

                function define(name, superName, interfaces, definition, metadata) {
                    if (classes[name]) {
                        return classes[name];
                    }

                    const superClass = resolve(superName);

                    function Class() {
                        const id = arguments.length === 0 ? 0 : arguments[arguments.length - 1];
                        const constructor = this["$" + id];
                        if (typeof constructor === "function") {
                            constructor.apply(this, arguments);
                        }
                    }

                    const prototype = Class.prototype = Object.create(superClass.prototype);

                    for (let i = 0; i < interfaces.length; i++) {
                        const type = direct[interfaces[i]] || classes[interfaces[i]];
                        if (type) {
                            for (const key in type.prototype) {
                                if (!(key in prototype)) {
                                    prototype[key] = type.prototype[key];
                                }
                            }
                        }
                    }

                    let init = null;
                    for (const key in definition) {
                        if (key === "_") {
                            init = definition[key];
                        } else if (key.charAt(0) === "_") {
                            Class[key.substring(1)] = definition[key];
                        } else {
                            prototype[key] = definition[key];
                        }
                    }

                    prototype.$rt = Class;
                    Class.$meta = { name: name, super: superName, interfaces: interfaces, metadata: metadata || null };
                    Class.$super = superClass;
                    direct[name] = Class;

                    if (metadata && metadata.n) {
                        names[metadata.n] = Class;
                    }

                    if (init === null) {
                        classes[name] = Class;
                    } else {
                        Object.defineProperty(classes, name, {
                            configurable: true,
                            enumerable: true,
                            get() {
                                Object.defineProperty(classes, name, {
                                    value: Class,
                                    configurable: true,
                                    enumerable: true,
                                    writable: true
                                });
                                const task = init;
                                init = null;
                                task.call(Class);
                                return Class;
                            }
                        });
                    }
                    return Class;
                }

                function resolve(name) {
                    if (!name) {
                        return Object;
                    }
                    if (direct[name]) {
                        return direct[name];
                    }
                    if (classes[name]) {
                        return classes[name];
                    }
                    if (name.startsWith("$rt.")) {
                        let value = $rt;
                        const parts = name.substring(4).split(".");
                        for (let i = 0; i < parts.length; i++) {
                            value = value[parts[i]];
                            if (!value) {
                                return Object;
                            }
                        }
                        return value;
                    }
                    return Object;
                }

                function isInstance(type, value) {
                    if (value === null || value === undefined) {
                        return false;
                    }
                    return isAssignableFrom(type, classOf(value));
                }

                function isAssignableFrom(target, type) {
                    for (let current = type; current; current = current.$super) {
                        if (current === target) {
                            return true;
                        }
                        const interfaces = current.$meta ? current.$meta.interfaces : [];
                        for (let i = 0; i < interfaces.length; i++) {
                            const it = direct[interfaces[i]] || classes[interfaces[i]];
                            if (it && isAssignableFrom(target, it)) {
                                return true;
                            }
                        }
                    }
                    return false;
                }

                function classOf(value) {
                    if (value === null || value === undefined) {
                        return null;
                    }
                    if (typeof value === "string") {
                        return String;
                    }
                    if (Array.isArray(value)) {
                        return Array;
                    }
                    return value.$rt || value.constructor || Object;
                }

                class Enum {
                    constructor(name, ordinal) {
                        this.$name = name;
                        this.$ordinal = ordinal;
                    }
                    name() {
                        return this.$name;
                    }
                    ordinal() {
                        return this.$ordinal;
                    }
                    toString() {
                        return this.$name;
                    }
                    compareTo(other) {
                        return this.$ordinal - other.$ordinal;
                    }
                    equals(other) {
                        return this === other;
                    }
                    hashCode() {
                        return this.$ordinal;
                    }
                }

                function enumValue(value, name, ordinal) {
                    value.$name = name;
                    value.$ordinal = ordinal;
                    return value;
                }

                function enumValueOf(type, name) {
                    const values = type.values();
                    for (let i = 0; i < values.length; i++) {
                        if (values[i].$name === name) {
                            return values[i];
                        }
                    }
                    throw new Error("No enum constant " + type.$meta.name + "." + name);
                }

                return { define: define, resolve: resolve, isInstance: isInstance, isAssignableFrom: isAssignableFrom, classOf: classOf, names: names, Enum: Enum, enumValue: enumValue, enumValueOf: enumValueOf };
            })();""";

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

                sort(comparator) {
                    this.elements.sort((a, b) => $rt_compareWith(comparator, a, b));
                }

                replaceAll(operator) {
                    this.elements = this.elements.map(value => operator(value));
                }

                removeIf(predicate) {
                    const retained = [];
                    for (const value of this.elements) {
                        if (!predicate(value)) {
                            retained.push(value);
                        }
                    }
                    const changed = retained.length !== this.elements.length;
                    this.elements = retained;
                    return changed;
                }

                subList(from, to) {
                    const list = new List();
                    list.elements = this.elements.slice(from, to);
                    return list;
                }

                stream() {
                    return Stream.of(this.elements);
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

                remove(...args) {
                    const index = this.indexOfKey(args[0]);
                    if (index < 0) {
                        return args.length === 1 ? null : false;
                    }
                    if (args.length !== 1) {
                        if (!List.$equals(this.$values[index], args[1])) {
                            return false;
                        }
                        this.$keys.splice(index, 1);
                        this.$values.splice(index, 1);
                        return true;
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
                    const set = new Set();
                    set.elements = this.$keys.slice();
                    return set;
                }

                values() {
                    const list = new List();
                    list.elements = this.$values.slice();
                    return list;
                }

                entrySet() {
                    const set = new Set();
                    for (let index = 0; index < this.$keys.length; index++) {
                        set.add(new Entry(this.$keys[index], this.$values[index]));
                    }
                    return set;
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

                putIfAbsent(key, value) {
                    const index = this.indexOfKey(key);
                    if (index < 0) {
                        this.put(key, value);
                        return null;
                    }
                    return this.$values[index];
                }

                computeIfAbsent(key, mappingFunction) {
                    const index = this.indexOfKey(key);
                    if (index < 0) {
                        const value = mappingFunction(key);
                        if (value !== null && value !== undefined) {
                            this.put(key, value);
                        }
                        return value;
                    }
                    return this.$values[index];
                }

                computeIfPresent(key, remappingFunction) {
                    const index = this.indexOfKey(key);
                    if (index < 0) {
                        return null;
                    }
                    const value = remappingFunction(key, this.$values[index]);
                    if (value === null || value === undefined) {
                        this.remove(key);
                        return null;
                    }
                    this.$values[index] = value;
                    return value;
                }

                compute(key, remappingFunction) {
                    const index = this.indexOfKey(key);
                    const value = remappingFunction(key, index < 0 ? null : this.$values[index]);
                    if (value === null || value === undefined) {
                        if (0 <= index) {
                            this.remove(key);
                        }
                        return null;
                    }
                    if (0 <= index) {
                        this.$values[index] = value;
                    } else {
                        this.put(key, value);
                    }
                    return value;
                }

                merge(key, value, remappingFunction) {
                    const index = this.indexOfKey(key);
                    if (index < 0) {
                        if (value !== null && value !== undefined) {
                            this.put(key, value);
                        }
                        return value;
                    }
                    const merged = remappingFunction(this.$values[index], value);
                    if (merged === null || merged === undefined) {
                        this.remove(key);
                        return null;
                    }
                    this.$values[index] = merged;
                    return merged;
                }

                replace(key, ...args) {
                    const index = this.indexOfKey(key);
                    if (index < 0) {
                        return args.length === 1 ? null : false;
                    }
                    if (args.length !== 1) {
                        if (!List.$equals(this.$values[index], args[0])) {
                            return false;
                        }
                        this.$values[index] = args[1];
                        return true;
                    }
                    const previous = this.$values[index];
                    this.$values[index] = args[0];
                    return previous;
                }

                static of(...args) {
                    const map = new Map();
                    for (let index = 0; index < args.length; index += 2) {
                        map.put(args[index], args[index + 1]);
                    }
                    return map;
                }

                static entry(key, value) {
                    return new Entry(key, value);
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
            class TreeMap extends Map {
                put(key, value) {
                    const previous = super.put(key, value);
                    const entries = this.$keys.map((item, index) => [item, this.$values[index]]);
                    entries.sort((a, b) => $rt_compare(a[0], b[0]));
                    this.$keys = entries.map(entry => entry[0]);
                    this.$values = entries.map(entry => entry[1]);
                    return previous;
                }
            }
            class Hashtable extends Map {}

            class Set {
                constructor(...args) {
                    this.elements = [];
                    if (args.length !== 0) {
                        const source = args[0];
                        if (source !== null && source !== undefined && typeof source !== "number") {
                            for (const value of $rt_iterable(source)) {
                                this.add(value);
                            }
                        }
                    }
                }

                add(value) {
                    if (this.contains(value)) {
                        return false;
                    }
                    this.elements.push(value);
                    return true;
                }

                remove(value) {
                    const index = this.indexOf(value);
                    if (index < 0) {
                        return false;
                    }
                    this.elements.splice(index, 1);
                    return true;
                }

                size() { return this.elements.length; }
                isEmpty() { return this.elements.length === 0; }
                contains(value) { return this.indexOf(value) >= 0; }
                indexOf(value) { return this.elements.findIndex(x => List.$equals(x, value)); }
                clear() { this.elements.length = 0; }

                addAll(collection) {
                    let changed = false;
                    for (const value of $rt_iterable(collection)) {
                        changed = this.add(value) || changed;
                    }
                    return changed;
                }

                removeAll(collection) {
                    let changed = false;
                    for (const value of $rt_iterable(collection)) {
                        while (this.remove(value)) {
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
                    for (const value of $rt_iterable(collection)) {
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
                    if (other instanceof Set) {
                        if (this.elements.length !== other.elements.length) {
                            return false;
                        }
                        for (const value of this.elements) {
                            if (!other.contains(value)) {
                                return false;
                            }
                        }
                        return true;
                    }
                    return false;
                }

                hashCode() {
                    let hash = 0;
                    for (const value of this.elements) {
                        hash = (hash + List.$hash(value)) | 0;
                    }
                    return hash;
                }

                toString() {
                    return "[" + this.elements.map(List.$string).join(", ") + "]";
                }

                [Symbol.iterator]() {
                    return this.elements[Symbol.iterator]();
                }

                stream() {
                    return Stream.of(this.elements);
                }

                static of(...values) {
                    const set = new Set();
                    for (const value of values) {
                        set.add(value);
                    }
                    return set;
                }

                static copyOf(collection) {
                    return new Set(collection);
                }
            }

            class HashSet extends Set {}
            class LinkedHashSet extends Set {}
            class TreeSet extends Set {
                constructor(...args) {
                    super(...args);
                    this.elements.sort((a, b) => $rt_compare(a, b));
                }
                add(value) {
                    const changed = super.add(value);
                    if (changed) {
                        this.elements.sort((a, b) => $rt_compare(a, b));
                    }
                    return changed;
                }
            }

            class Queue {
                constructor(...args) {
                    this.elements = [];
                    if (args.length !== 0) {
                        const source = args[0];
                        if (source !== null && source !== undefined && typeof source !== "number") {
                            for (const value of $rt_iterable(source)) {
                                this.elements.push(value);
                            }
                        }
                    }
                }

                add(value) { this.elements.push(value); return true; }
                offer(value) { this.elements.push(value); return true; }
                poll() { return this.elements.length === 0 ? null : this.elements.shift(); }
                peek() { return this.elements.length === 0 ? null : this.elements[0]; }
                element() {
                    if (this.elements.length === 0) {
                        throw new Error("NoSuchElementException");
                    }
                    return this.elements[0];
                }
                remove(...args) {
                    if (args.length !== 0) {
                        const index = this.indexOf(args[0]);
                        if (index < 0) {
                            return false;
                        }
                        this.elements.splice(index, 1);
                        return true;
                    }
                    if (this.elements.length === 0) {
                        throw new Error("NoSuchElementException");
                    }
                    return this.elements.shift();
                }

                size() { return this.elements.length; }
                isEmpty() { return this.elements.length === 0; }
                contains(value) { return this.indexOf(value) >= 0; }
                indexOf(value) { return this.elements.findIndex(x => List.$equals(x, value)); }
                clear() { this.elements.length = 0; }

                addAll(collection) {
                    let changed = false;
                    for (const value of $rt_iterable(collection)) {
                        changed = this.add(value) || changed;
                    }
                    return changed;
                }

                removeAll(collection) {
                    let changed = false;
                    for (const value of $rt_iterable(collection)) {
                        while (this.remove(value)) {
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
                    for (const value of $rt_iterable(collection)) {
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
                    if (other instanceof Queue) {
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

                stream() {
                    return Stream.of(this.elements);
                }
            }

            class Deque extends Queue {
                addFirst(value) { this.elements.unshift(value); }
                addLast(value) { this.elements.push(value); }
                offerFirst(value) { this.elements.unshift(value); return true; }
                offerLast(value) { this.elements.push(value); return true; }
                pollFirst() { return this.poll(); }
                pollLast() { return this.elements.length === 0 ? null : this.elements.pop(); }
                peekFirst() { return this.peek(); }
                peekLast() { return this.elements.length === 0 ? null : this.elements[this.elements.length - 1]; }
                push(value) { this.elements.unshift(value); }
                pop() {
                    if (this.elements.length === 0) {
                        throw new Error("NoSuchElementException");
                    }
                    return this.elements.shift();
                }
                removeFirst() {
                    if (this.elements.length === 0) {
                        throw new Error("NoSuchElementException");
                    }
                    return this.elements.shift();
                }
                removeLast() {
                    if (this.elements.length === 0) {
                        throw new Error("NoSuchElementException");
                    }
                    return this.elements.pop();
                }
                getFirst() {
                    if (this.elements.length === 0) {
                        throw new Error("NoSuchElementException");
                    }
                    return this.elements[0];
                }
                getLast() {
                    if (this.elements.length === 0) {
                        throw new Error("NoSuchElementException");
                    }
                    return this.elements[this.elements.length - 1];
                }
                descendingIterator() {
                    let index = this.elements.length;
                    const elements = this.elements;
                    return {
                        hasNext() { return 0 < index; },
                        next() { return elements[--index]; }
                    };
                }
            }

            class ArrayDeque extends Deque {}
            class PriorityQueue extends Queue {
                poll() {
                    if (this.elements.length === 0) {
                        return null;
                    }
                    this.elements.sort((a, b) => $rt_compare(a, b));
                    return this.elements.shift();
                }
            }

            class Optional {
                constructor(value) {
                    this.value = value;
                }
                static of(value) {
                    if (value === null || value === undefined) throw new Error("NullPointerException");
                    return new Optional(value);
                }
                static ofNullable(value) { return new Optional(value); }
                static empty() { return new Optional(null); }
                isPresent() { return this.value !== null && this.value !== undefined; }
                isEmpty() { return !this.isPresent(); }
                get() {
                    if (!this.isPresent()) throw new Error("NoSuchElementException");
                    return this.value;
                }
                orElse(other) { return this.isPresent() ? this.value : other; }
                orElseGet(supplier) { return this.isPresent() ? this.value : supplier(); }
                orElseThrow() {
                    if (!this.isPresent()) throw new Error("NoSuchElementException");
                    return this.value;
                }
                ifPresent(action) { if (this.isPresent()) action(this.value); }
                ifPresentOrElse(action, emptyAction) { if (this.isPresent()) action(this.value); else emptyAction(); }
                map(mapper) { return this.isPresent() ? Optional.ofNullable(mapper(this.value)) : this; }
                flatMap(mapper) { return this.isPresent() ? mapper(this.value) : this; }
                filter(predicate) { return this.isPresent() && predicate(this.value) ? this : Optional.empty(); }
                stream() { return Stream.of(this.isPresent() ? [this.value] : []); }
                equals(other) { return other instanceof Optional && List.$equals(this.value, other.value); }
                hashCode() { return this.isPresent() ? List.$hash(this.value) : 0; }
                toString() { return this.isPresent() ? "Optional[" + List.$string(this.value) + "]" : "Optional.empty"; }
            }

            class Stream {
                constructor(values) {
                    this.values = values;
                }
                static of(...values) {
                    return new Stream(values.length === 1 && Array.isArray(values[0]) ? values[0].slice() : values.slice());
                }
                static empty() { return new Stream([]); }
                static iterate(seed, operator) { return new Stream([seed]); }
                map(mapper) { return new Stream(this.values.map(value => mapper(value))); }
                flatMap(mapper) {
                    const result = [];
                    for (const value of this.values) {
                        for (const item of $rt_iterable(mapper(value))) result.push(item);
                    }
                    return new Stream(result);
                }
                filter(predicate) { return new Stream(this.values.filter(value => predicate(value))); }
                distinct() {
                    const result = [];
                    for (const value of this.values) {
                        if (!result.some(x => List.$equals(x, value))) result.push(value);
                    }
                    return new Stream(result);
                }
                sorted(comparator) {
                    const result = this.values.slice();
                    result.sort((a, b) => $rt_compareWith(comparator, a, b));
                    return new Stream(result);
                }
                limit(max) { return new Stream(this.values.slice(0, max)); }
                skip(count) { return new Stream(this.values.slice(count)); }
                peek(action) { this.values.forEach(action); return this; }
                forEach(action) { this.values.forEach(action); }
                forEachOrdered(action) { this.values.forEach(action); }
                count() { return this.values.length; }
                anyMatch(predicate) { return this.values.some(value => predicate(value)); }
                allMatch(predicate) { return this.values.every(value => predicate(value)); }
                noneMatch(predicate) { return !this.values.some(value => predicate(value)); }
                findFirst() { return new Optional(this.values.length === 0 ? null : this.values[0]); }
                findAny() { return this.findFirst(); }
                reduce(...args) {
                    let identity;
                    let accumulator;
                    if (args.length === 1) {
                        accumulator = args[0];
                        let result;
                        for (const value of this.values) result = result === undefined ? value : accumulator(result, value);
                        return new Optional(result);
                    }
                    identity = args[0];
                    accumulator = args[1];
                    let result = identity;
                    for (const value of this.values) result = accumulator(result, value);
                    return result;
                }
                collect(collector) { return collector(this.values); }
                toArray(generator) { return this.values.slice(); }
                toList() { return List.of(this.values); }
                iterator() {
                    let index = 0;
                    const values = this.values;
                    return {
                        hasNext() { return index < values.length; },
                        next() { return values[index++]; }
                    };
                }
                [Symbol.iterator]() { return this.values[Symbol.iterator](); }
            }

            class Pattern {
                constructor(pattern, flags) {
                    this.pattern = pattern;
                    this.flags = flags === undefined ? "" : flags;
                    this.regexp = new RegExp(pattern, this.flags);
                }
                static compile(value, flags) { return new Pattern(value, $rt_patternFlags(flags)); }
                static quote(value) { return String(value).replace(/[.*+?^${}()|[\\]\\\\]/g, "\\\\$&"); }
                matcher(input) { return new Matcher(this, String(input)); }
                pattern() { return this.pattern; }
                split(input, limit) {
                    const result = String(input).split(this.regexp);
                    return limit === undefined || limit <= 0 ? result : result.slice(0, limit);
                }
                toString() { return this.pattern; }
            }

            class Matcher {
                constructor(pattern, input) {
                    this.pattern = pattern;
                    this.input = input;
                    this.match = null;
                    this.index = 0;
                }
                matches() {
                    this.match = new RegExp("^(?:" + this.pattern.pattern + ")$", this.pattern.flags).exec(this.input);
                    return this.match !== null;
                }
                find() {
                    const regexp = new RegExp(this.pattern.pattern, this.pattern.flags + "g");
                    regexp.lastIndex = this.index;
                    this.match = regexp.exec(this.input);
                    if (this.match === null) {
                        return false;
                    }
                    this.index = regexp.lastIndex === this.index ? this.index + 1 : regexp.lastIndex;
                    return true;
                }
                group(index) {
                    if (this.match === null) throw new Error("IllegalStateException");
                    return this.match[index === undefined ? 0 : index];
                }
                start() {
                    if (this.match === null) throw new Error("IllegalStateException");
                    return this.match.index;
                }
                end() {
                    if (this.match === null) throw new Error("IllegalStateException");
                    return this.match.index + this.match[0].length;
                }
                replaceAll(replacement) {
                    return this.input.replace(new RegExp(this.pattern.pattern, this.pattern.flags + "g"), replacement);
                }
                replaceFirst(replacement) {
                    return this.input.replace(new RegExp(this.pattern.pattern, this.pattern.flags), replacement);
                }
                reset(input) {
                    this.input = String(input);
                    this.index = 0;
                    this.match = null;
                    return this;
                }
            }

            const $rt_patternFlags = (flags) => {
                let result = "";
                if (flags === undefined || flags === null) return result;
                if ((flags & 2) !== 0) result += "i";
                if ((flags & 8) !== 0) result += "m";
                if ((flags & 32) !== 0) result += "s";
                return result;
            };

            const $rt_reflectCache = new WeakMap();
            const $rt_primitiveClasses = Object.create(null);
            const $rt_args = (args) => args.length === 1 && Array.isArray(args[0]) ? args[0] : args;
            const $rt_annotationValue = (value) => {
                if (Array.isArray(value)) return value.map($rt_annotationValue);
                if (value !== null && typeof value === "object") {
                    if (value.t !== undefined && value.v !== undefined) return $rt_annotation(value.t, value.v);
                    if (value.c !== undefined) {
                        const found = $rt.names[value.c];
                        return found ? $rt.reflect(found) : $rt_standardClass(value.c);
                    }
                    if (value.e !== undefined) {
                        const type = $rt.names[value.e];
                        return type ? type[value.n] : null;
                    }
                }
                return value;
            };
            const $rt_standardClasses = Object.create(null);
            const $rt_standardClass = (name) => {
                if (name === "java.lang.Class") return $rt.Class;
                let found = $rt_standardClasses[name];
                if (!found) {
                    let type = undefined;
                    switch (name) {
                    case "java.lang.Object": type = Object; break;
                    case "java.lang.String": type = String; break;
                    case "java.lang.Number": type = Number; break;
                    case "java.lang.Boolean": type = Boolean; break;
                    }
                    if (type !== undefined) return $rt.reflect(type);
                    found = new Class(type, name, false, false, []);
                    $rt_standardClasses[name] = found;
                }
                return found;
            };
            const $rt_annotation = (type, values) => {
                const annotation = { $type: type, $values: values };
                annotation.annotationType = () => $rt.Class.forName(type);
                for (const key in values) {
                    if (!(key in annotation)) {
                        annotation[key] = () => $rt_annotationValue(values[key]);
                    }
                }
                annotation.equals = (other) => other !== null && other !== undefined && other.$type === type;
                annotation.hashCode = () => $rt_hashCode(type);
                annotation.toString = () => "@" + type;
                return annotation;
            };
            const $rt_annotations = (list) => (list || []).map(a => $rt_annotation(a.t, a.v));
            const $rt_annotated = (target) => {
                target.getAnnotations = () => target.annotations;
                target.getDeclaredAnnotations = () => target.annotations;
                target.getAnnotation = (type) => {
                    const name = type.getName();
                    return target.annotations.find(a => a.$type === name) || null;
                };
                target.getDeclaredAnnotation = (type) => target.getAnnotation(type);
                target.isAnnotationPresent = (type) => target.getAnnotation(type) !== null;
                target.getAnnotationsByType = (type) => target.annotations.filter(a => a.$type === type.getName());
                target.getDeclaredAnnotationsByType = (type) => target.getAnnotationsByType(type);
                return target;
            };

            class Type {
            }
            class TypeVariable extends Type {
                constructor(name, declaration, bounds) {
                    super();
                    this.name = name;
                    this.declaration = declaration;
                    this.bounds = bounds;
                }
                getName() { return this.name; }
                getTypeName() { return this.name; }
                getGenericDeclaration() { return this.declaration; }
                getBounds() { return this.bounds; }
                toString() { return this.name; }
            }
            class ParameterizedType extends Type {
                constructor(raw, args) {
                    super();
                    this.raw = raw;
                    this.args = args;
                }
                getRawType() { return this.raw; }
                getActualTypeArguments() { return this.args; }
                getOwnerType() { return null; }
                getTypeName() { return $rt_typeName(this.raw) + "<" + this.args.map($rt_typeName).join(", ") + ">"; }
                toString() { return this.getTypeName(); }
            }
            class GenericArrayType extends Type {
                constructor(component) {
                    super();
                    this.component = component;
                }
                getGenericComponentType() { return this.component; }
                getTypeName() { return $rt_typeName(this.component) + "[]"; }
                toString() { return this.getTypeName(); }
            }
            class WildcardType extends Type {
                constructor(upper, lower) {
                    super();
                    this.upper = upper;
                    this.lower = lower;
                }
                getUpperBounds() { return this.upper; }
                getLowerBounds() { return this.lower; }
                getTypeName() {
                    if (this.lower.length !== 0) return "? super " + $rt_typeName(this.lower[0]);
                    if (this.upper.length === 0 || (this.upper.length === 1 && $rt_typeName(this.upper[0]) === "java.lang.Object")) return "?";
                    return "? extends " + this.upper.map($rt_typeName).join(" & ");
                }
                toString() { return this.getTypeName(); }
            }
            const $rt_typeName = (type) => type === null || type === undefined ? "?" : typeof type.getTypeName === "function" ? type
                    .getTypeName() : typeof type.getName === "function" ? type.getName() : String(type);
            const $rt_primitiveName = (code) => ({
                Z: "boolean",
                C: "char",
                B: "byte",
                S: "short",
                I: "int",
                J: "long",
                F: "float",
                D: "double",
                V: "void"
            })[code];
            const $rt_classByName = (name) => {
                if (!name) return null;
                if (name.charAt(0) === "[") return $rt_arrayClass(name);
                switch (name) {
                case "boolean":
                case "char":
                case "byte":
                case "short":
                case "int":
                case "long":
                case "float":
                case "double":
                case "void":
                    return Class.primitive(name);
                }
                const found = $rt.names[name];
                return found ? $rt.reflect(found) : $rt_standardClass(name);
            };
            const $rt_arrayClass = (name) => {
                let dimensions = 0;
                while (name.charAt(dimensions) === "[") dimensions++;
                const component = name.substring(dimensions);
                let type = component.length === 1 ? Class.primitive($rt_primitiveName(component)) : $rt_classByName(component
                        .substring(1, component.length - 1).replace(/\\//g, "."));
                for (let i = 0; i < dimensions; i++) type = Class.array(type);
                return type;
            };
            const $rt_typeVariables = (declaration) => {
                if (!declaration.$typeVariables) {
                    declaration.$typeVariables = Object.create(null);
                }
                return declaration.$typeVariables;
            };
            const $rt_type = (metadata, declaration, owner) => {
                if (metadata === null || metadata === undefined) return null;
                switch (metadata.k) {
                case "c":
                    return $rt_classByName(metadata.n);
                case "v": {
                    let found = $rt_typeVariables(declaration)[metadata.n];
                    if (!found && owner) {
                        found = $rt_typeVariables(owner)[metadata.n];
                    }
                    if (!found) {
                        found = new TypeVariable(metadata.n, declaration, []);
                        $rt_typeVariables(declaration)[metadata.n] = found;
                    }
                    return found;
                }
                case "p":
                    return new ParameterizedType($rt_type(metadata.r, declaration, owner), (metadata.a || []).map(x => $rt_type(x, declaration, owner)));
                case "a":
                    return new GenericArrayType($rt_type(metadata.c, declaration, owner));
                case "w":
                    return new WildcardType((metadata.u || []).map(x => $rt_type(x, declaration, owner)), (metadata.l || []).map(x => $rt_type(x, declaration, owner)));
                default:
                    return null;
                }
            };
            const $rt_types = (list, declaration, owner) => (list || []).map(x => $rt_type(x, declaration, owner));
            const $rt_typeParameters = (metadata, declaration, owner) => {
                const variables = $rt_typeVariables(declaration);
                return (metadata || []).map(x => {
                    let found = variables[x.n];
                    if (!found) {
                        found = new TypeVariable(x.n, declaration, []);
                        variables[x.n] = found;
                    }
                    if (found.bounds.length === 0) {
                        found.bounds = $rt_types(x.b, declaration, owner);
                    }
                    return found;
                });
            };

            class Class {
                constructor(jsClass, name, primitive, array, annotations, component) {
                    this.jsClass = jsClass;
                    this.name = name;
                    this.primitive = primitive === true;
                    this.array = array === true;
                    this.annotations = annotations || [];
                    this.component = component;
                    $rt_annotated(this);
                }
                static forName(name) {
                    const found = $rt.names[name];
                    if (!found) throw new Error("ClassNotFoundException: " + name);
                    return $rt.reflect(found);
                }
                static primitive(name) {
                    let found = $rt_primitiveClasses[name];
                    if (!found) {
                        found = new Class(null, name, true, false);
                        $rt_primitiveClasses[name] = found;
                    }
                    return found;
                }
                static array(component) {
                    return new Class(null, "[" + component.getName(), false, true, [], component);
                }
                getName() { return this.name; }
                getTypeName() { return this.name; }
                getSimpleName() { return this.name.substring(this.name.lastIndexOf('.') + 1).substring(this.name.substring(this.name.lastIndexOf('.') + 1).lastIndexOf('$') + 1); }
                getCanonicalName() { return this.name.replace(/\\$/g, "."); }
                metadata() { return this.jsClass && this.jsClass.$meta ? this.jsClass.$meta.metadata : null; }
                getTypeParameters() {
                    const metadata = this.metadata();
                    return $rt_typeParameters(metadata ? metadata.tp : null, this);
                }
                getGenericSuperclass() {
                    this.getTypeParameters();
                    const metadata = this.metadata();
                    return $rt_type(metadata ? metadata.gs : null, this);
                }
                getGenericInterfaces() {
                    this.getTypeParameters();
                    const metadata = this.metadata();
                    return $rt_types(metadata ? metadata.gi : null, this);
                }
                getModifiers() { return this.jsClass && this.jsClass.$meta && this.jsClass.$meta.metadata ? this.jsClass.$meta.metadata.m : 0; }
                isPrimitive() { return this.primitive; }
                isArray() { return this.array; }
                getComponentType() { return this.component || null; }
                isInterface() { return (this.getModifiers() & 512) !== 0; }
                isEnum() { return this.jsClass && this.jsClass.$meta && this.jsClass.$meta.metadata ? this.jsClass.$meta.metadata.e === true : false; }
                isAnnotation() { return (this.getModifiers() & 8192) !== 0; }
                isInstance(value) { return this.jsClass !== null && $rt.isInstance(this.jsClass, value); }
                isAssignableFrom(other) {
                    return other instanceof Class && this.jsClass !== null && other.jsClass !== null && $rt.isAssignableFrom(this.jsClass, other.jsClass);
                }
                cast(value) { return value; }
                getSuperclass() {
                    if (this.primitive || this.array || this.isInterface()) return null;
                    const parent = this.jsClass && this.jsClass.$super;
                    return parent ? $rt.reflect(parent) : null;
                }
                getInterfaces() {
                    if (!this.jsClass) return [];
                    return this.jsClass.$meta.interfaces.map(name => $rt.resolve(name)).filter(value => value && value !== Object).map(value => $rt.reflect(value));
                }
                getDeclaredMethods() {
                    if (this.$methods) return this.$methods;
                    const metadata = this.metadata();
                    if (!metadata || !metadata.ms) return this.$methods = [];
                    this.$methods = metadata.ms.map(m => new Method(this, m[0], m[1], m[2], m[3], $rt_annotations(m[4]), m[5], m[6], m[7], m[8], m[9]));
                    return this.$methods;
                }
                getMethods() {
                    const result = [];
                    for (let type = this; type; type = type.getSuperclass()) {
                        result.push(...type.getDeclaredMethods());
                    }
                    return result;
                }
                getDeclaredMethod(name, ...types) {
                    types = $rt_args(types);
                    const found = this.getDeclaredMethods().find(m => m.name === name && m.params === types.length);
                    if (!found) throw new Error("NoSuchMethodException: " + name);
                    return found;
                }
                getMethod(name, ...types) {
                    types = $rt_args(types);
                    for (let type = this; type; type = type.getSuperclass()) {
                        try { return type.getDeclaredMethod(name, ...types); } catch (e) { }
                    }
                    throw new Error("NoSuchMethodException: " + name);
                }
                getDeclaredFields() {
                    if (this.$fields) return this.$fields;
                    const metadata = this.metadata();
                    if (!metadata || !metadata.fs) return this.$fields = [];
                    this.$fields = metadata.fs.map(f => new Field(this, f[0], f[1], f[2], $rt_annotations(f[3]), f[4]));
                    return this.$fields;
                }
                getFields() {
                    const result = [];
                    for (let type = this; type; type = type.getSuperclass()) {
                        result.push(...type.getDeclaredFields());
                    }
                    return result;
                }
                getDeclaredField(name) {
                    const found = this.getDeclaredFields().find(f => f.name === name);
                    if (!found) throw new Error("NoSuchFieldException: " + name);
                    return found;
                }
                getField(name) {
                    for (let type = this; type; type = type.getSuperclass()) {
                        try { return type.getDeclaredField(name); } catch (e) { }
                    }
                    throw new Error("NoSuchFieldException: " + name);
                }
                getDeclaredConstructors() {
                    if (this.$constructors) return this.$constructors;
                    const metadata = this.metadata();
                    if (!metadata || !metadata.cs) return this.$constructors = [];
                    this.$constructors = metadata.cs.map(c => new Constructor(this, c[0], c[1], c[2], c[3], $rt_annotations(c[4]), c[5], c[6], c[7], c[8]));
                    return this.$constructors;
                }
                getConstructors() {
                    return this.getDeclaredConstructors();
                }
                getDeclaredConstructor(...types) {
                    types = $rt_args(types);
                    const found = this.getDeclaredConstructors().find(c => c.params === types.length);
                    if (!found) throw new Error("NoSuchMethodException: <init>");
                    return found;
                }
                getConstructor(...types) {
                    return this.getDeclaredConstructor(...types);
                }
                newInstance() {
                    return this.getDeclaredConstructor().newInstance();
                }
                toString() {
                    return (this.isInterface() ? "interface " : "class ") + this.name;
                }
            }

            class Method {
                constructor(owner, name, descriptor, modifiers, params, annotations, typeParameters, genericParameters, genericReturn, genericExceptions, exceptionTypes) {
                    this.owner = owner;
                    this.name = name;
                    this.descriptor = descriptor;
                    this.modifiers = modifiers;
                    this.params = params;
                    this.annotations = annotations || [];
                    this.typeParameters = typeParameters;
                    this.genericParameters = genericParameters;
                    this.genericReturn = genericReturn;
                    this.genericExceptions = genericExceptions;
                    this.exceptionTypes = exceptionTypes;
                    this.$parameterTypes = undefined;
                    this.$exceptionTypes = undefined;
                    $rt_annotated(this);
                }
                getTypeParameters() { return $rt_typeParameters(this.typeParameters, this, this.owner); }
                getGenericParameterTypes() { this.getTypeParameters(); return $rt_types(this.genericParameters, this, this.owner); }
                getGenericReturnType() { this.getTypeParameters(); return $rt_type(this.genericReturn, this, this.owner); }
                getGenericExceptionTypes() { this.getTypeParameters(); return $rt_types(this.genericExceptions, this, this.owner); }
                getParameterTypes() {
                    if (this.$parameterTypes === undefined) this.$parameterTypes = $rt_parseTypes(this.descriptor);
                    return this.$parameterTypes;
                }
                getReturnType() { return $rt_returnType(this.descriptor); }
                getExceptionTypes() {
                    if (this.$exceptionTypes === undefined) this.$exceptionTypes = $rt_types(this.exceptionTypes, this, this.owner);
                    return this.$exceptionTypes;
                }
                getName() { return this.name; }
                getModifiers() { return this.modifiers; }
                getParameterCount() { return this.params; }
                invoke(target, ...args) {
                    args = $rt_args(args);
                    const fn = target === null || target === undefined ? this.owner.jsClass[this.name] : target[this.name];
                    if (typeof fn !== "function") throw new Error("IllegalArgumentException: " + this.name);
                    return fn.apply(target, args);
                }
                setAccessible(value) { return this; }
                toString() { return this.owner.getName() + "." + this.name; }
            }

            class Field {
                constructor(owner, name, descriptor, modifiers, annotations, genericType) {
                    this.owner = owner;
                    this.name = name;
                    this.descriptor = descriptor;
                    this.modifiers = modifiers;
                    this.annotations = annotations || [];
                    this.genericType = genericType;
                    $rt_annotated(this);
                }
                getGenericType() { if (this.owner) this.owner.getTypeParameters(); return $rt_type(this.genericType, this.owner); }
                getName() { return this.name; }
                getModifiers() { return this.modifiers; }
                getType() { return $rt_classForDescriptor(this.descriptor); }
                get(target) { return target[this.name]; }
                set(target, value) { target[this.name] = value; }
                setAccessible(value) { return this; }
                toString() { return this.owner.getName() + "." + this.name; }
            }

            class Constructor {
                constructor(owner, descriptor, modifiers, index, params, annotations, typeParameters, genericParameters, genericExceptions, exceptionTypes) {
                    this.owner = owner;
                    this.descriptor = descriptor;
                    this.modifiers = modifiers;
                    this.index = index;
                    this.params = params;
                    this.annotations = annotations || [];
                    this.typeParameters = typeParameters;
                    this.genericParameters = genericParameters;
                    this.genericExceptions = genericExceptions;
                    this.exceptionTypes = exceptionTypes;
                    this.$parameterTypes = undefined;
                    this.$exceptionTypes = undefined;
                    $rt_annotated(this);
                }
                getTypeParameters() { return $rt_typeParameters(this.typeParameters, this, this.owner); }
                getGenericParameterTypes() { this.getTypeParameters(); return $rt_types(this.genericParameters, this, this.owner); }
                getGenericExceptionTypes() { this.getTypeParameters(); return $rt_types(this.genericExceptions, this, this.owner); }
                getParameterTypes() {
                    if (this.$parameterTypes === undefined) this.$parameterTypes = $rt_parseTypes(this.descriptor);
                    return this.$parameterTypes;
                }
                getExceptionTypes() {
                    if (this.$exceptionTypes === undefined) this.$exceptionTypes = $rt_types(this.exceptionTypes, this, this.owner);
                    return this.$exceptionTypes;
                }
                getModifiers() { return this.modifiers; }
                getParameterCount() { return this.params; }
                newInstance(...args) {
                    args = $rt_args(args);
                    return new this.owner.jsClass(...args, this.index);
                }
                setAccessible(value) { return this; }
                toString() { return this.owner.getName() + ".<init>"; }
            }

            class Modifier {
                static isPublic(value) { return (value & 1) !== 0; }
                static isPrivate(value) { return (value & 2) !== 0; }
                static isProtected(value) { return (value & 4) !== 0; }
                static isStatic(value) { return (value & 8) !== 0; }
                static isFinal(value) { return (value & 16) !== 0; }
                static isSynchronized(value) { return (value & 32) !== 0; }
                static isVolatile(value) { return (value & 64) !== 0; }
                static isTransient(value) { return (value & 128) !== 0; }
                static isNative(value) { return (value & 256) !== 0; }
                static isInterface(value) { return (value & 512) !== 0; }
                static isAbstract(value) { return (value & 1024) !== 0; }
                static isStrict(value) { return (value & 2048) !== 0; }
            }

            class ArrayReflect {
                static getLength(array) { return array.length; }
                static get(array, index) { return array[index]; }
                static set(array, index, value) { array[index] = value; }
                static newInstance(component, length) {
                    const value = component && component.getName() === "boolean" ? false : component && component.isPrimitive() ? 0 : null;
                    return new Array(length).fill(value);
                }
            }

            const $rt_reflect = (jsClass) => {
                if (jsClass === null || jsClass === undefined) return null;
                let found = $rt_reflectCache.get(jsClass);
                if (!found) {
                    const metadata = jsClass.$meta ? jsClass.$meta.metadata : null;
                    let name = metadata && metadata.n ? metadata.n : jsClass.name;
                    if (!metadata) {
                        if (jsClass === Object) name = "java.lang.Object";
                        else if (jsClass === String) name = "java.lang.String";
                        else if (jsClass === Number) name = "java.lang.Number";
                        else if (jsClass === Boolean) name = "java.lang.Boolean";
                    }
                    found = new Class(jsClass, name, false, false, $rt_annotations(metadata ? metadata.as : null));
                    $rt_reflectCache.set(jsClass, found);
                }
                return found;
            };
            const $rt_classForDescriptor = (descriptor) => {
                if (!descriptor) return null;
                switch (descriptor) {
                case "Z": return Class.primitive("boolean");
                case "C": return Class.primitive("char");
                case "B": return Class.primitive("byte");
                case "S": return Class.primitive("short");
                case "I": return Class.primitive("int");
                case "J": return Class.primitive("long");
                case "F": return Class.primitive("float");
                case "D": return Class.primitive("double");
                case "V": return Class.primitive("void");
                default:
                    if (descriptor.startsWith("[")) return Class.array($rt_classForDescriptor(descriptor.substring(1)));
                    if (descriptor.startsWith("L")) return $rt_classByName(descriptor.substring(1, descriptor.length - 1).replace(/\\//g, "."));
                    return null;
                }
            };
            const $rt_parseTypes = (descriptor) => {
                if (!descriptor) return [];
                const types = [];
                let index = descriptor.charAt(0) === "(" ? 1 : 0;
                while (index < descriptor.length && descriptor.charAt(index) !== ")") {
                    let start = index;
                    while (descriptor.charAt(index) === "[") index++;
                    if (descriptor.charAt(index) === "L") index = descriptor.indexOf(";", index);
                    index++;
                    types.push($rt_classForDescriptor(descriptor.substring(start, index)));
                }
                return types;
            };
            const $rt_returnType = (descriptor) => {
                if (!descriptor) return null;
                const close = descriptor.lastIndexOf(")");
                return $rt_classForDescriptor(close === -1 ? descriptor : descriptor.substring(close + 1));
            };
            $rt.Class = Class;
            $rt.Method = Method;
            $rt.Field = Field;
            $rt.Constructor = Constructor;
            $rt.Modifier = Modifier;
            $rt.Array = ArrayReflect;
            $rt.Type = Type;
            $rt.TypeVariable = TypeVariable;
            $rt.ParameterizedType = ParameterizedType;
            $rt.GenericArrayType = GenericArrayType;
            $rt.WildcardType = WildcardType;
            $rt.reflect = $rt_reflect;""";

    /**
     * Hide constructor.
     */
    private JavascriptRuntime() {
    }
}
