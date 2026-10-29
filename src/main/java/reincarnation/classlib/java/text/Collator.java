/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.text;

import js.lang.NativeIntl;
import reincarnation.classlib.java.io.Serializable;
import reincarnation.classlib.java.lang.Cloneable;
import reincarnation.classlib.java.util.Locale;

/**
 * The Java implementation of {@link java.text.Collator} which is transpiled into JavaScript.
 */
public class Collator implements Cloneable, Serializable {

    /** The primary strength. */
    public static final int PRIMARY = 0;

    /** The secondary strength. */
    public static final int SECONDARY = 1;

    /** The tertiary strength. */
    public static final int TERTIARY = 2;

    /** The identical strength. */
    public static final int IDENTICAL = 3;

    /** The no decomposition mode. */
    public static final int NO_DECOMPOSITION = 0;

    /** The canonical decomposition mode. */
    public static final int CANONICAL_DECOMPOSITION = 1;

    /** The locale tag. */
    private final String locale;

    /** The strength. */
    private int strength;

    /** The decomposition mode. */
    private int decomposition;

    /**
     * Create the collator.
     * 
     * @param locale A locale tag.
     */
    protected Collator(String locale) {
        this.locale = locale;
        this.strength = TERTIARY;
        this.decomposition = CANONICAL_DECOMPOSITION;
    }

    /**
     * Get the collator of the default locale.
     * 
     * @return A collator.
     */
    public static Collator getInstance() {
        return getInstance(Locale.getDefault());
    }

    /**
     * Get the collator of the locale.
     * 
     * @param locale A locale.
     * @return A collator.
     */
    public static Collator getInstance(Locale locale) {
        if (locale == null) {
            throw new NullPointerException("desiredLocale");
        }
        String tag = locale.toLanguageTag();
        return new Collator(tag.isEmpty() || tag.equals("und") ? "en" : tag);
    }

    /**
     * Compare the texts.
     * 
     * @param source A source.
     * @param target A target.
     * @return A compared result.
     */
    public int compare(String source, String target) {
        return NativeIntl.collatorCompare(locale, source, target, sensitivity());
    }

    /**
     * Compare the texts.
     * 
     * @param source A source.
     * @param target A target.
     * @return A compared result.
     */
    public int compare(Object source, Object target) {
        return compare(String.valueOf(source), String.valueOf(target));
    }

    /**
     * Get the sensitivity.
     * 
     * @return A sensitivity.
     */
    private String sensitivity() {
        if (strength == PRIMARY) {
            return "base";
        }
        if (strength == SECONDARY) {
            return "accent";
        }
        return "variant";
    }

    /**
     * Get the strength.
     * 
     * @return A strength.
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Set the strength.
     * 
     * @param strength A strength.
     */
    public void setStrength(int strength) {
        if (strength < PRIMARY || strength > IDENTICAL) {
            throw new IllegalArgumentException("Invalid strength: " + strength);
        }
        this.strength = strength;
    }

    /**
     * Get the decomposition mode.
     * 
     * @return A decomposition mode.
     */
    public int getDecomposition() {
        return decomposition;
    }

    /**
     * Set the decomposition mode.
     * 
     * @param decomposition A decomposition mode.
     */
    public void setDecomposition(int decomposition) {
        if (decomposition != NO_DECOMPOSITION && decomposition != CANONICAL_DECOMPOSITION) {
            throw new IllegalArgumentException("Invalid decomposition: " + decomposition);
        }
        this.decomposition = decomposition;
    }

    /**
     * Get the locale.
     * 
     * @return A locale.
     */
    public Locale getLocale() {
        return Locale.forLanguageTag(locale);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object clone() {
        Collator collator = new Collator(locale);
        collator.strength = strength;
        collator.decomposition = decomposition;
        return collator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Collator)) {
            return false;
        }
        Collator collator = (Collator) other;
        return locale.equals(collator.locale) && strength == collator.strength && decomposition == collator.decomposition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return locale.hashCode() ^ strength;
    }
}
