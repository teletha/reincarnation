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
import reincarnation.classlib.java.util.Date;
import reincarnation.classlib.java.util.Locale;

/**
 * The Java implementation of {@link java.text.DateFormat} which is transpiled into JavaScript.
 */
public class DateFormat extends Format {

    /** The full style. */
    public static final int FULL = 0;

    /** The long style. */
    public static final int LONG = 1;

    /** The medium style. */
    public static final int MEDIUM = 2;

    /** The short style. */
    public static final int SHORT = 3;

    /** The default style. */
    public static final int DEFAULT = MEDIUM;

    /** The locale tag. */
    private final String locale;

    /** The date style. */
    private final String dateStyle;

    /** The time style. */
    private final String timeStyle;

    /**
     * Create the date format.
     * 
     * @param locale A locale tag.
     * @param dateStyle A date style.
     * @param timeStyle A time style.
     */
    private DateFormat(String locale, String dateStyle, String timeStyle) {
        this.locale = locale;
        this.dateStyle = dateStyle;
        this.timeStyle = timeStyle;
    }

    /**
     * Get the date and time format.
     * 
     * @return A date format.
     */
    public static DateFormat getInstance() {
        return getDateTimeInstance(SHORT, SHORT, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Get the date and time format.
     * 
     * @return A date format.
     */
    public static DateFormat getDateInstance() {
        return getDateInstance(DEFAULT, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Get the date format.
     * 
     * @param style A style.
     * @return A date format.
     */
    public static DateFormat getDateInstance(int style) {
        return getDateInstance(style, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Get the date format.
     * 
     * @param style A style.
     * @param locale A locale.
     * @return A date format.
     */
    public static DateFormat getDateInstance(int style, Locale locale) {
        return new DateFormat(tag(locale), style(style), "");
    }

    /**
     * Get the time format.
     * 
     * @return A date format.
     */
    public static DateFormat getTimeInstance() {
        return getTimeInstance(DEFAULT, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Get the time format.
     * 
     * @param style A style.
     * @return A date format.
     */
    public static DateFormat getTimeInstance(int style) {
        return getTimeInstance(style, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Get the time format.
     * 
     * @param style A style.
     * @param locale A locale.
     * @return A date format.
     */
    public static DateFormat getTimeInstance(int style, Locale locale) {
        return new DateFormat(tag(locale), "", style(style));
    }

    /**
     * Get the date and time format.
     * 
     * @return A date format.
     */
    public static DateFormat getDateTimeInstance() {
        return getDateTimeInstance(DEFAULT, DEFAULT, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Get the date and time format.
     * 
     * @param dateStyle A date style.
     * @param timeStyle A time style.
     * @return A date format.
     */
    public static DateFormat getDateTimeInstance(int dateStyle, int timeStyle) {
        return getDateTimeInstance(dateStyle, timeStyle, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Get the date and time format.
     * 
     * @param dateStyle A date style.
     * @param timeStyle A time style.
     * @param locale A locale.
     * @return A date format.
     */
    public static DateFormat getDateTimeInstance(int dateStyle, int timeStyle, Locale locale) {
        return new DateFormat(tag(locale), style(dateStyle), style(timeStyle));
    }

    /**
     * Get the available locales.
     * 
     * @return Locales.
     */
    public static Locale[] getAvailableLocales() {
        return Locale.getAvailableLocales();
    }

    /**
     * Get the locale tag.
     * 
     * @param locale A locale.
     * @return A locale tag.
     */
    private static String tag(Locale locale) {
        String tag = locale.toLanguageTag();
        return tag.isEmpty() || tag.equals("und") ? "en" : tag;
    }

    /**
     * Get the style name.
     * 
     * @param style A style.
     * @return A style name.
     */
    private static String style(int style) {
        if (style == FULL) {
            return "full";
        }
        if (style == LONG) {
            return "long";
        }
        if (style == MEDIUM) {
            return "medium";
        }
        if (style == SHORT) {
            return "short";
        }
        throw new IllegalArgumentException("Illegal date format style: " + style);
    }

    /**
     * Format the date.
     * 
     * @param date A date.
     * @return A formatted text.
     */
    public String format(Date date) {
        String text = NativeIntl.formatDate(locale, dateStyle, timeStyle, date.getTime());
        if (timeStyle.isEmpty()) {
            return text;
        }
        return text.replace(" AM", "\u202FAM").replace(" PM", "\u202FPM");
    }

    /**
     * Format the date.
     * 
     * @param date A date.
     * @return A formatted text.
     */
    public String format(Object date) {
        if (!(date instanceof Date)) {
            throw new IllegalArgumentException("Cannot format given Object as a Date");
        }
        return format((Date) date);
    }

    /**
     * Parse the text.
     * 
     * @param source A text.
     * @return A date.
     */
    public Date parse(String source) {
        throw new UnsupportedOperationException("DateFormat.parse is not supported");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object clone() {
        return new DateFormat(locale, dateStyle, timeStyle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DateFormat)) {
            return false;
        }
        DateFormat format = (DateFormat) other;
        return locale.equals(format.locale) && dateStyle.equals(format.dateStyle) && timeStyle.equals(format.timeStyle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return locale.hashCode() ^ dateStyle.hashCode() * 31 + timeStyle.hashCode();
    }
}
