/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.time;

import js.lang.NativeTemporal;
import reincarnation.classlib.java.io.Serializable;

/**
 * The Java implementation of {@link java.time.ZoneId} which is transpiled into JavaScript.
 */
public class ZoneId implements Serializable {

    /** The time zone id. */
    private final String id;

    /**
     * Create the time zone.
     * 
     * @param id A time zone id.
     */
    ZoneId(String id) {
        this.id = id;
    }

    /**
     * Get the system default time zone.
     * 
     * @return A time zone.
     */
    public static ZoneId systemDefault() {
        return new ZoneId(NativeTemporal.systemZone());
    }

    /**
     * Get the time zone of the id.
     * 
     * @param id A time zone id.
     * @return A time zone.
     */
    public static ZoneId of(String id) {
        if (id == null) {
            throw new NullPointerException("zoneId");
        }
        if (id.equals("Z")) {
            return ZoneOffset.UTC;
        }
        if (isOffset(id)) {
            return ZoneOffset.of(id);
        }
        if (!NativeTemporal.validZone(id)) {
            throw new IllegalArgumentException("Unknown time-zone ID: " + id);
        }
        return new ZoneId(id);
    }

    /**
     * Get the time zone of the prefix and the offset.
     * 
     * @param prefix A prefix.
     * @param offset An offset.
     * @return A time zone.
     */
    public static ZoneId ofOffset(String prefix, ZoneOffset offset) {
        if (prefix == null) {
            throw new NullPointerException("prefix");
        }
        if (prefix.length() == 0) {
            return offset;
        }
        return new ZoneId(prefix + offset.getId());
    }

    /**
     * Get the time zone id.
     * 
     * @return A time zone id.
     */
    public String getId() {
        return id;
    }

    String temporalId() {
        return id.equals("Z") ? "+00:00" : id;
    }

    /**
     * Normalize this time zone.
     * 
     * @return A normalized time zone.
     */
    public ZoneId normalized() {
        if (this instanceof ZoneOffset) {
            return this;
        }
        if (id.equals("UTC") || id.equals("GMT") || id.equals("UT") || isOffset(id)) {
            return ZoneOffset.of(id);
        }
        return this;
    }

    /**
     * Check whether the id is an offset.
     * 
     * @param id An id.
     * @return A result.
     */
    private static boolean isOffset(String id) {
        return id.length() > 0 && (id.charAt(0) == '+' || id.charAt(0) == '-');
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof ZoneId zone && id.equals(zone.id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return id.hashCode() + 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return id;
    }
}
