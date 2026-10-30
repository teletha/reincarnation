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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The JavaScript object functions.
 */
@Native
public final class NativeObject {

    /** The JVM representation of the properties. */
    private final Map<String, Object> values = new LinkedHashMap();

    /** The JVM representation of the property descriptors. */
    private final Map<String, PropertyDescriptor> descriptors = new LinkedHashMap();

    /**
     * Create an empty object.
     */
    @Native("({})")
    public NativeObject() {
    }

    /**
     * Get the identity hash code.
     * 
     * @param value A value.
     * @return An identity hash code.
     */
    @Native("$rt_identityHashCode($0)")
    public static int identityHashCode(Object value) {
        return java.lang.System.identityHashCode(value);
    }

    /**
     * Invoke the named function of the receiver.
     * 
     * @param receiver A receiver.
     * @param name A function name.
     * @param args Arguments.
     * @return A result.
     */
    @Native("$0[$1](...$2)")
    public static Object invoke(Object receiver, String name, Object[] args) {
        return null;
    }

    @Native("typeof $rt.reflect === 'function' ? $rt.reflect($rt.classOf($0)) : $rt.classOf($0)")
    public static Class classOf(Object value) {
        return value.getClass();
    }

    /**
     * Get the enum constants.
     * 
     * @param typeOrValue An enum class or value.
     * @return Constants.
     */
    @Native("(() => { const type = (typeof $0 === 'function' ? $0 : ($0 && $0.jsClass ? $0.jsClass : $rt.classOf($0))); return type && typeof type.values === 'function' ? type.values() : []; })()")
    public static Object[] enumConstants(Object typeOrValue) {
        if (typeOrValue instanceof Class) {
            return ((Class) typeOrValue).getEnumConstants();
        }
        return typeOrValue.getClass().getEnumConstants();
    }

    /**
     * Get the JavaScript class of the reflection class.
     * 
     * @param type A reflection class.
     * @return A JavaScript class.
     */
    @Native("$0.jsClass")
    public static Object classObject(Object type) {
        return null;
    }

    /**
     * Create the instance of the JavaScript class.
     * 
     * @param type A JavaScript class.
     * @param args Arguments.
     * @return An instance.
     */
    @Native("new $0(...$1)")
    public static Object construct(Object type, Object[] args) {
        return null;
    }

    /**
     * Create an empty object.
     * 
     * @return An object.
     */
    @Native("Object.create(null)")
    public static NativeObject create() {
        return new NativeObject();
    }

    /**
     * Get the property.
     * 
     * @param index An index.
     * @return A value.
     */
    @Native("$this[$0]")
    public Object getProperty(int index) {
        String key = String.valueOf(index);
        return values.containsKey(key) ? values.get(key) : NativeUndefined.INSTANCE;
    }

    /**
     * Get the property.
     * 
     * @param key A key.
     * @return A value.
     */
    @Native("$this[$0]")
    public Object getProperty(Object key) {
        String name = String.valueOf(key);
        return values.containsKey(name) ? values.get(name) : NativeUndefined.INSTANCE;
    }

    /**
     * Get the property or the default value.
     * 
     * @param index An index.
     * @param defaultValue A default value.
     * @return A value.
     */
    @Native("($this[$0] === undefined ? $1 : $this[$0])")
    public Object getProperty(int index, Object defaultValue) {
        Object value = getProperty(index);
        return value == NativeUndefined.INSTANCE ? defaultValue : value;
    }

    /**
     * Get the property or the default int value.
     * 
     * @param index An index.
     * @param defaultValue A default value.
     * @return A value.
     */
    @Native("($this[$0] === undefined ? $1 : ($this[$0] | 0))")
    public int getProperty(int index, int defaultValue) {
        Object value = getProperty(index);
        return value == NativeUndefined.INSTANCE || !(value instanceof Number) ? defaultValue : ((Number) value).intValue();
    }

    /**
     * Get the property or the default value.
     * 
     * @param key A key.
     * @param defaultValue A default value.
     * @return A value.
     */
    @Native("($this[$0] === undefined ? $1 : $this[$0])")
    public Object getProperty(Object key, Object defaultValue) {
        Object value = getProperty(key);
        return value == NativeUndefined.INSTANCE ? defaultValue : value;
    }

    /**
     * Get the property as the specified type.
     * 
     * @param <A> A value type.
     * @param type A value type.
     * @param key A key.
     * @return A value.
     */
    @Native("$this[$1]")
    public <A> A getPropertyAs(Class<A> type, Object key) {
        return (A) getProperty(key);
    }

    /**
     * Get the int property.
     * 
     * @param key A key.
     * @return A value.
     */
    @Native("(Number($this[$0]) | 0)")
    public int getInt(Object key) {
        Object value = getProperty(key);
        return value instanceof Number ? ((Number) value).intValue() : 0;
    }

    /**
     * Get the long property.
     * 
     * @param key A key.
     * @return A value.
     */
    @Native("$rt_l($this[$0])")
    public long getLong(Object key) {
        Object value = getProperty(key);
        return value instanceof Number ? ((Number) value).longValue() : 0L;
    }

    /**
     * Get the float property.
     * 
     * @param key A key.
     * @return A value.
     */
    @Native("Number($this[$0])")
    public float getFloat(Object key) {
        Object value = getProperty(key);
        return value instanceof Number ? ((Number) value).floatValue() : 0F;
    }

    /**
     * Get the double property.
     * 
     * @param key A key.
     * @return A value.
     */
    @Native("Number($this[$0])")
    public double getDouble(Object key) {
        Object value = getProperty(key);
        return value instanceof Number ? ((Number) value).doubleValue() : 0D;
    }

    /**
     * Get the byte property.
     * 
     * @param key A key.
     * @return A value.
     */
    @Native("(Number($this[$0]) | 0)")
    public byte getByte(Object key) {
        Object value = getProperty(key);
        return value instanceof Number ? ((Number) value).byteValue() : 0;
    }

    /**
     * Get the short property.
     * 
     * @param key A key.
     * @return A value.
     */
    @Native("(Number($this[$0]) | 0)")
    public short getShort(Object key) {
        Object value = getProperty(key);
        return value instanceof Number ? ((Number) value).shortValue() : 0;
    }

    /**
     * Get the boolean property.
     * 
     * @param key A key.
     * @return A value.
     */
    @Native("Boolean($this[$0])")
    public boolean getBoolean(Object key) {
        Object value = getProperty(key);
        return value instanceof Boolean ? ((Boolean) value).booleanValue() : false;
    }

    /**
     * Get the char property.
     * 
     * @param key A key.
     * @return A value.
     */
    @Native("$this[$0]")
    public char getChar(Object key) {
        Object value = getProperty(key);
        if (value instanceof Character) {
            return ((Character) value).charValue();
        }
        if (value instanceof String && !((String) value).isEmpty()) {
            return ((String) value).charAt(0);
        }
        return 0;
    }

    /**
     * Set the property value.
     * 
     * @param key A key.
     * @param value A value.
     */
    private void put(String key, Object value) {
        PropertyDescriptor descriptor = descriptors.get(key);
        values.put(key, value);
        descriptors.put(key, descriptor == null ? new PropertyDescriptor(value) : new PropertyDescriptor(value, descriptor.writable, descriptor.enumerable, descriptor.configurable));
    }

    /**
     * Set the property.
     * 
     * @param index An index.
     * @param value A value.
     */
    @Native("$this[$0] = $1")
    public void setProperty(int index, Object value) {
        put(String.valueOf(index), value);
    }

    /**
     * Set the property.
     * 
     * @param key A key.
     * @param value A value.
     */
    @Native("$this[$0] = $1")
    public void setProperty(Object key, int value) {
        put(String.valueOf(key), value);
    }

    /**
     * Set the property.
     * 
     * @param key A key.
     * @param value A value.
     */
    @Native("$this[$0] = $1")
    public void setProperty(Object key, Object value) {
        put(String.valueOf(key), value);
    }

    /**
     * Set the int property.
     * 
     * @param key A key.
     * @param value A value.
     */
    @Native("$this[$0] = $1")
    public void setInt(Object key, int value) {
        setProperty(key, value);
    }

    /**
     * Set the long property.
     * 
     * @param key A key.
     * @param value A value.
     */
    @Native("$this[$0] = $1")
    public void setLong(Object key, long value) {
        setProperty(key, value);
    }

    /**
     * Set the float property.
     * 
     * @param key A key.
     * @param value A value.
     */
    @Native("$this[$0] = $1")
    public void setFloat(Object key, float value) {
        setProperty(key, value);
    }

    /**
     * Set the double property.
     * 
     * @param key A key.
     * @param value A value.
     */
    @Native("$this[$0] = $1")
    public void setDouble(Object key, double value) {
        setProperty(key, value);
    }

    /**
     * Set the byte property.
     * 
     * @param key A key.
     * @param value A value.
     */
    @Native("$this[$0] = $1")
    public void setByte(Object key, byte value) {
        setProperty(key, value);
    }

    /**
     * Set the short property.
     * 
     * @param key A key.
     * @param value A value.
     */
    @Native("$this[$0] = $1")
    public void setShort(Object key, short value) {
        setProperty(key, value);
    }

    /**
     * Set the boolean property.
     * 
     * @param key A key.
     * @param value A value.
     */
    @Native("$this[$0] = $1")
    public void setBoolean(Object key, boolean value) {
        setProperty(key, value);
    }

    /**
     * Set the char property.
     * 
     * @param key A key.
     * @param value A value.
     */
    @Native("$this[$0] = $1")
    public void setChar(Object key, char value) {
        put(String.valueOf(key), value);
    }

    /**
     * Check whether the property exists.
     * 
     * @param index An index.
     * @return A result.
     */
    @Native("$0 in $this")
    public boolean hasProperty(int index) {
        return values.containsKey(String.valueOf(index));
    }

    /**
     * Check whether the property exists.
     * 
     * @param key A key.
     * @return A result.
     */
    @Native("$0 in $this")
    public boolean hasProperty(Object key) {
        return values.containsKey(String.valueOf(key));
    }

    /**
     * Check whether the own property exists.
     * 
     * @param key A key.
     * @return A result.
     */
    @Native("$this.hasOwnProperty($0)")
    public boolean hasOwnProperty(Object key) {
        return values.containsKey(String.valueOf(key));
    }

    /**
     * Delete the property.
     * 
     * @param index An index.
     * @return A result.
     */
    @Native("delete $this[$0]")
    public boolean deleteProperty(int index) {
        String key = String.valueOf(index);
        descriptors.remove(key);
        return values.remove(key) != null;
    }

    /**
     * Delete the property.
     * 
     * @param key A key.
     * @return A result.
     */
    @Native("delete $this[$0]")
    public boolean deleteProperty(Object key) {
        String name = String.valueOf(key);
        descriptors.remove(name);
        return values.remove(name) != null;
    }

    /**
     * Get the keys.
     * 
     * @return Keys.
     */
    @Native("Object.keys($this)")
    public NativeArray keys() {
        NativeArray array = new NativeArray();
        for (String key : values.keySet()) {
            array.push(key);
        }
        return array;
    }

    /**
     * Check whether this object is an array.
     * 
     * @return A result.
     */
    @Native("Array.isArray($this)")
    public boolean isArray() {
        return false;
    }

    /**
     * Get the own property descriptor.
     * 
     * @param object An object.
     * @param name A name.
     * @return A descriptor or null.
     */
    @Native("Object.getOwnPropertyDescriptor($0, $1)")
    public static PropertyDescriptor getOwnPropertyDescriptor(Object object, String name) {
        if (object instanceof NativeObject) {
            NativeObject target = (NativeObject) object;
            if (target.values.containsKey(name)) {
                PropertyDescriptor descriptor = target.descriptors.get(name);
                Object value = target.values.get(name);
                if (descriptor == null) {
                    return new PropertyDescriptor(value);
                }
                return new PropertyDescriptor(value, descriptor.writable, descriptor.enumerable, descriptor.configurable);
            }
        }
        return null;
    }

    /**
     * Define the property.
     * 
     * @param object An object.
     * @param name A name.
     * @param descriptor A descriptor.
     */
    @Native("Object.defineProperty($0, $1, $2)")
    public static void defineProperty(Object object, String name, PropertyDescriptor descriptor) {
        if (object instanceof NativeObject) {
            NativeObject target = (NativeObject) object;
            target.values.put(name, descriptor.value);
            target.descriptors.put(name, descriptor);
        }
    }

    /**
     * Check whether the property exists.
     * 
     * @param object An object.
     * @param name A name.
     * @return A result.
     */
    @Native("$1 in $0")
    public static boolean hasProperty(Object object, String name) {
        return object instanceof NativeObject && ((NativeObject) object).values.containsKey(name);
    }

    /**
     * Check whether the own property exists.
     * 
     * @param object An object.
     * @param name A name.
     * @return A result.
     */
    @Native("Object.hasOwn($0, $1)")
    public static boolean hasOwn(Object object, String name) {
        return object instanceof NativeObject && ((NativeObject) object).values.containsKey(name);
    }

    /**
     * Check whether the property is enumerable.
     * 
     * @param key A key.
     * @return A result.
     */
    @Native("$this.propertyIsEnumerable($0)")
    public boolean propertyIsEnumerable(Object key) {
        return values.containsKey(String.valueOf(key));
    }

    /**
     * Get the own property names.
     * 
     * @return Property names.
     */
    @Native("Object.getOwnPropertyNames($this)")
    public NativeArray getOwnPropertyNames() {
        return keys();
    }

    /**
     * Get the property names.
     * 
     * @param object An object.
     * @return Property names.
     */
    @Native("Object.getOwnPropertyNames($0)")
    public static NativeArray getOwnPropertyNames(Object object) {
        return keys(object);
    }

    /**
     * Get the values.
     * 
     * @return Values.
     */
    @Native("Object.values($this)")
    public NativeArray values() {
        NativeArray array = new NativeArray();
        for (Object value : values.values()) {
            array.push(value);
        }
        return array;
    }

    /**
     * Get the values.
     * 
     * @param object An object.
     * @return Values.
     */
    @Native("Object.values($0)")
    public static NativeArray values(Object object) {
        if (object instanceof NativeObject) {
            return ((NativeObject) object).values();
        }
        return new NativeArray();
    }

    /**
     * Get the entries.
     * 
     * @return Entries.
     */
    @Native("Object.entries($this)")
    public NativeArray entries() {
        NativeArray array = new NativeArray();
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            NativeArray pair = new NativeArray();
            pair.push(entry.getKey());
            pair.push(entry.getValue());
            array.push(pair);
        }
        return array;
    }

    /**
     * Get the entries.
     * 
     * @param object An object.
     * @return Entries.
     */
    @Native("Object.entries($0)")
    public static NativeArray entries(Object object) {
        if (object instanceof NativeObject) {
            return ((NativeObject) object).entries();
        }
        return new NativeArray();
    }

    /**
     * Get the keys.
     * 
     * @param object An object.
     * @return Keys.
     */
    @Native("Object.keys($0)")
    public static NativeArray keys(Object object) {
        if (object instanceof NativeObject) {
            return ((NativeObject) object).keys();
        }
        return new NativeArray();
    }

    /**
     * Create the object from the entries.
     * 
     * @param entries Entries.
     * @return An object.
     */
    @Native("Object.fromEntries($0)")
    public static NativeObject fromEntries(NativeArray entries) {
        NativeObject object = new NativeObject();
        for (int i = 0; i < entries.length(); i++) {
            Object value = entries.get(i);
            if (value instanceof NativeArray) {
                NativeArray pair = (NativeArray) value;
                object.setProperty(pair.get(0), pair.get(1));
            }
        }
        return object;
    }

    /**
     * Assign the properties.
     * 
     * @param target A target.
     * @param sources Sources.
     * @return A target.
     */
    @Native("Object.assign($0, ...$1)")
    public static Object assign(Object target, Object... sources) {
        if (target instanceof NativeObject) {
            NativeObject object = (NativeObject) target;
            for (Object source : sources) {
                if (source instanceof NativeObject) {
                    object.values.putAll(((NativeObject) source).values);
                    object.descriptors.putAll(((NativeObject) source).descriptors);
                }
            }
        }
        return target;
    }

    /**
     * Compare the values.
     * 
     * @param left A left value.
     * @param right A right value.
     * @return A result.
     */
    @Native("Object.is($0, $1)")
    public static boolean is(Object left, Object right) {
        return left == right;
    }

    /**
     * Get the prototype.
     * 
     * @return A prototype or null.
     */
    @Native("Object.getPrototypeOf($this)")
    public Object getPrototypeOf() {
        return null;
    }

    /**
     * Get the prototype.
     * 
     * @param object An object.
     * @return A prototype or null.
     */
    @Native("Object.getPrototypeOf($0)")
    public static Object getPrototypeOf(Object object) {
        return null;
    }

    /**
     * Set the prototype.
     * 
     * @param prototype A prototype.
     */
    @Native("Object.setPrototypeOf($this, $0)")
    public void setPrototypeOf(Object prototype) {
    }

    /**
     * Set the prototype.
     * 
     * @param object An object.
     * @param prototype A prototype.
     * @return An object.
     */
    @Native("Object.setPrototypeOf($0, $1)")
    public static Object setPrototypeOf(Object object, Object prototype) {
        return object;
    }

    /**
     * Check whether the object is extensible.
     * 
     * @return A result.
     */
    @Native("Object.isExtensible($this)")
    public boolean isExtensible() {
        return true;
    }

    /**
     * Prevent the extensions.
     */
    @Native("Object.preventExtensions($this)")
    public void preventExtensions() {
    }

    /**
     * Freeze the object.
     */
    @Native("Object.freeze($this)")
    public void freeze() {
    }

    /**
     * Check whether the object is frozen.
     * 
     * @return A result.
     */
    @Native("Object.isFrozen($this)")
    public boolean isFrozen() {
        return false;
    }

    /**
     * Seal the object.
     */
    @Native("Object.seal($this)")
    public void seal() {
    }

    /**
     * Check whether the object is sealed.
     * 
     * @return A result.
     */
    @Native("Object.isSealed($this)")
    public boolean isSealed() {
        return false;
    }

    /**
     * Get the locale string.
     * 
     * @return A text.
     */
    @Native("$this.toLocaleString()")
    public String toLocaleString() {
        return toString();
    }

    /**
     * Get the value.
     * 
     * @return A value.
     */
    @Native("$this.valueOf()")
    public Object valueOf() {
        return this;
    }
}
