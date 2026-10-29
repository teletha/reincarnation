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
import reincarnation.classlib.java.util.Currency;
import reincarnation.classlib.java.util.Locale;

/**
 * The Java implementation of {@link java.text.NumberFormat} which is transpiled into JavaScript.
 */
public class NumberFormat extends Format {

    /** The number style. */
    private static final int NUMBER = 0;

    /** The currency style. */
    private static final int CURRENCY = 1;

    /** The percent style. */
    private static final int PERCENT = 2;

    /** The integer style. */
    private static final int INTEGER = 3;

    /** The locale tag. */
    private final String locale;

    /** The style. */
    private final int style;

    /** The currency code. */
    private String currency;

    /** The minimum fraction digits. */
    private int minimumFractionDigits;

    /** The maximum fraction digits. */
    private int maximumFractionDigits;

    /** Whether the grouping is used. */
    private boolean grouping;

    /** Whether only integers are parsed. */
    private boolean parseIntegerOnly;

    /**
     * Create the number format.
     * 
     * @param locale A locale tag.
     * @param style A style.
     */
    private NumberFormat(String locale, int style) {
        this.locale = locale;
        this.style = style;
        this.grouping = true;
        this.minimumFractionDigits = 0;
        this.maximumFractionDigits = 3;
        if (style == INTEGER) {
            this.maximumFractionDigits = 0;
            this.parseIntegerOnly = true;
        } else if (style == PERCENT) {
            this.maximumFractionDigits = 0;
        } else if (style == CURRENCY) {
            this.currency = currencyTag(locale);
            int digits = NativeIntl.currencyFractionDigits(locale, currency);
            this.minimumFractionDigits = digits;
            this.maximumFractionDigits = digits;
        }
    }

    /**
     * Get the currency of the locale tag.
     * 
     * @param locale A locale tag.
     * @return A currency code.
     */
    private static String currencyTag(String locale) {
        try {
            return Currency.getInstance(Locale.forLanguageTag(locale)).getCurrencyCode();
        } catch (RuntimeException e) {
            return "USD";
        }
    }

    /**
     * Get the number format.
     * 
     * @return A number format.
     */
    public static NumberFormat getInstance() {
        return getNumberInstance(Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Get the number format.
     * 
     * @param locale A locale.
     * @return A number format.
     */
    public static NumberFormat getInstance(Locale locale) {
        return getNumberInstance(locale);
    }

    /**
     * Get the number format.
     * 
     * @return A number format.
     */
    public static NumberFormat getNumberInstance() {
        return getNumberInstance(Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Get the number format.
     * 
     * @param locale A locale.
     * @return A number format.
     */
    public static NumberFormat getNumberInstance(Locale locale) {
        return new NumberFormat(tag(locale), NUMBER);
    }

    /**
     * Get the integer format.
     * 
     * @return An integer format.
     */
    public static NumberFormat getIntegerInstance() {
        return getIntegerInstance(Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Get the integer format.
     * 
     * @param locale A locale.
     * @return An integer format.
     */
    public static NumberFormat getIntegerInstance(Locale locale) {
        return new NumberFormat(tag(locale), INTEGER);
    }

    /**
     * Get the currency format.
     * 
     * @return A currency format.
     */
    public static NumberFormat getCurrencyInstance() {
        return getCurrencyInstance(Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Get the currency format.
     * 
     * @param locale A locale.
     * @return A currency format.
     */
    public static NumberFormat getCurrencyInstance(Locale locale) {
        return new NumberFormat(tag(locale), CURRENCY);
    }

    /**
     * Get the percent format.
     * 
     * @return A percent format.
     */
    public static NumberFormat getPercentInstance() {
        return getPercentInstance(Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Get the percent format.
     * 
     * @param locale A locale.
     * @return A percent format.
     */
    public static NumberFormat getPercentInstance(Locale locale) {
        return new NumberFormat(tag(locale), PERCENT);
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
     * Format the value.
     * 
     * @param value A value.
     * @return A formatted text.
     */
    public String format(Object value) {
        if (!(value instanceof Number)) {
            throw new IllegalArgumentException("Cannot format given Object as a Number");
        }
        return format(((Number) value).doubleValue());
    }

    /**
     * Format the value.
     * 
     * @param value A value.
     * @return A formatted text.
     */
    public String format(long value) {
        return format((double) value);
    }

    /**
     * Format the value.
     * 
     * @param value A value.
     * @return A formatted text.
     */
    public String format(double value) {
        if (style == CURRENCY) {
            return NativeIntl.formatCurrency(locale, currencyTagOrCurrent(), value, minimumFractionDigits, maximumFractionDigits, grouping);
        }
        if (style == PERCENT) {
            return NativeIntl.formatPercent(locale, value, minimumFractionDigits, maximumFractionDigits, grouping);
        }
        return NativeIntl.formatNumber(locale, value, minimumFractionDigits, maximumFractionDigits, grouping);
    }

    /**
     * Get the current currency code.
     * 
     * @return A currency code.
     */
    private String currencyTagOrCurrent() {
        return currency == null ? "USD" : currency;
    }

    /**
     * Get the currency.
     * 
     * @return A currency or null.
     */
    public Currency getCurrency() {
        return currency == null ? null : Currency.getInstance(currency);
    }

    /**
     * Set the currency.
     * 
     * @param currency A currency.
     */
    public void setCurrency(Currency currency) {
        if (style != CURRENCY) {
            throw new UnsupportedOperationException("Currency is only supported by the currency instance");
        }
        this.currency = currency.getCurrencyCode();
    }

    /**
     * Get the maximum fraction digits.
     * 
     * @return Maximum fraction digits.
     */
    public int getMaximumFractionDigits() {
        return maximumFractionDigits;
    }

    /**
     * Set the maximum fraction digits.
     * 
     * @param digits Digits.
     */
    public void setMaximumFractionDigits(int digits) {
        this.maximumFractionDigits = digits;
    }

    /**
     * Get the minimum fraction digits.
     * 
     * @return Minimum fraction digits.
     */
    public int getMinimumFractionDigits() {
        return minimumFractionDigits;
    }

    /**
     * Set the minimum fraction digits.
     * 
     * @param digits Digits.
     */
    public void setMinimumFractionDigits(int digits) {
        this.minimumFractionDigits = digits;
    }

    /**
     * Get the maximum integer digits.
     * 
     * @return Maximum integer digits.
     */
    public int getMaximumIntegerDigits() {
        return 40;
    }

    /**
     * Set the maximum integer digits.
     * 
     * @param digits Digits.
     */
    public void setMaximumIntegerDigits(int digits) {
    }

    /**
     * Get the minimum integer digits.
     * 
     * @return Minimum integer digits.
     */
    public int getMinimumIntegerDigits() {
        return 1;
    }

    /**
     * Set the minimum integer digits.
     * 
     * @param digits Digits.
     */
    public void setMinimumIntegerDigits(int digits) {
    }

    /**
     * Check whether the grouping is used.
     * 
     * @return A result.
     */
    public boolean isGroupingUsed() {
        return grouping;
    }

    /**
     * Set whether the grouping is used.
     * 
     * @param grouping A result.
     */
    public void setGroupingUsed(boolean grouping) {
        this.grouping = grouping;
    }

    /**
     * Check whether only integers are parsed.
     * 
     * @return A result.
     */
    public boolean isParseIntegerOnly() {
        return parseIntegerOnly;
    }

    /**
     * Set whether only integers are parsed.
     * 
     * @param parseIntegerOnly A result.
     */
    public void setParseIntegerOnly(boolean parseIntegerOnly) {
        this.parseIntegerOnly = parseIntegerOnly;
    }

    /**
     * Parse the text.
     * 
     * @param source A text.
     * @return A number.
     */
    public Number parse(String source) {
        throw new UnsupportedOperationException("NumberFormat.parse is not supported");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object clone() {
        NumberFormat format = new NumberFormat(locale, style);
        format.currency = currency;
        format.minimumFractionDigits = minimumFractionDigits;
        format.maximumFractionDigits = maximumFractionDigits;
        format.grouping = grouping;
        format.parseIntegerOnly = parseIntegerOnly;
        return format;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NumberFormat)) {
            return false;
        }
        NumberFormat format = (NumberFormat) other;
        return style == format.style && locale.equals(format.locale) && minimumFractionDigits == format.minimumFractionDigits && maximumFractionDigits == format.maximumFractionDigits && grouping == format.grouping && parseIntegerOnly == format.parseIntegerOnly && currencyTagOrCurrent().equals(format.currencyTagOrCurrent());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return locale.hashCode() ^ style * 31 + minimumFractionDigits * 17 + maximumFractionDigits;
    }
}
