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

import js.lang.NativeIntl;
import reincarnation.classlib.java.io.Serializable;

/**
 * The Java implementation of {@link java.util.Currency} which is transpiled into JavaScript.
 */
public final class Currency implements Serializable {

    /** The currency code. */
    private final String code;

    /**
     * Create the currency.
     * 
     * @param code A currency code.
     */
    private Currency(String code) {
        this.code = code;
    }

    /**
     * Get the currency of the code.
     * 
     * @param currencyCode A currency code.
     * @return A currency.
     */
    public static Currency getInstance(String currencyCode) {
        if (currencyCode == null) {
            throw new NullPointerException("currencyCode");
        }
        if (!currencyCode.equals(currencyCode.toUpperCase())) {
            throw new IllegalArgumentException("Invalid currency code: " + currencyCode);
        }
        if (!NativeIntl.validCurrency(currencyCode)) {
            throw new IllegalArgumentException("Invalid currency code: " + currencyCode);
        }
        return new Currency(currencyCode);
    }

    /**
     * Get the currency of the locale.
     * 
     * @param locale A locale.
     * @return A currency.
     */
    public static Currency getInstance(Locale locale) {
        String country = locale.getCountry();
        String code = currencyOfCountry(country);
        if (code == null) {
            throw new IllegalArgumentException("Cannot find currency for locale: " + locale);
        }
        return new Currency(code);
    }

    /**
     * Get the available currencies.
     * 
     * @return Currencies.
     */
    public static Currency[] getAvailableCurrencies() {
        String values = NativeIntl.supportedValues("currency");
        if (values == null || values.isEmpty()) {
            values = "USD,EUR,GBP,JPY,CNY,KRW,CAD,AUD,INR,CHF";
        }
        String[] codes = split(values);
        Currency[] currencies = new Currency[codes.length];
        for (int i = 0; i < codes.length; i++) {
            currencies[i] = new Currency(codes[i]);
        }
        return currencies;
    }

    /**
     * Get the currency of the country.
     * 
     * @param country A country code.
     * @return A currency code or null.
     */
    private static String currencyOfCountry(String country) {
        if (country.equals("US")) {
            return "USD";
        }
        if (country.equals("GB")) {
            return "GBP";
        }
        if (country.equals("JP")) {
            return "JPY";
        }
        if (country.equals("CN")) {
            return "CNY";
        }
        if (country.equals("KR")) {
            return "KRW";
        }
        if (country.equals("CA")) {
            return "CAD";
        }
        if (country.equals("AU")) {
            return "AUD";
        }
        if (country.equals("IN")) {
            return "INR";
        }
        if (country.equals("CH")) {
            return "CHF";
        }
        if (country.equals("RU")) {
            return "RUB";
        }
        if (country.equals("BR")) {
            return "BRL";
        }
        if (country.equals("DE") || country.equals("FR") || country.equals("IT") || country.equals("ES") || country.equals("NL") || country.equals("BE") || country.equals("AT") || country.equals("IE") || country.equals("PT") || country.equals("FI") || country.equals("GR")) {
            return "EUR";
        }
        return null;
    }

    /**
     * Get the currency code.
     * 
     * @return A currency code.
     */
    public String getCurrencyCode() {
        return code;
    }

    /**
     * Get the symbol.
     * 
     * @return A symbol.
     */
    public String getSymbol() {
        return getSymbol(Locale.getDefault(Locale.Category.DISPLAY));
    }

    /**
     * Get the symbol.
     * 
     * @param locale A locale.
     * @return A symbol.
     */
    public String getSymbol(Locale locale) {
        String symbol = NativeIntl.currencySymbol(displayTag(locale), code);
        return symbol == null || symbol.isEmpty() ? code : symbol;
    }

    /**
     * Get the display name.
     * 
     * @return A display name.
     */
    public String getDisplayName() {
        return getDisplayName(Locale.getDefault(Locale.Category.DISPLAY));
    }

    /**
     * Get the display name.
     * 
     * @param locale A locale.
     * @return A display name.
     */
    public String getDisplayName(Locale locale) {
        String name = NativeIntl.displayName(displayTag(locale), "currency", code);
        return name == null ? code : name;
    }

    /**
     * Get the default fraction digits.
     * 
     * @return Fraction digits.
     */
    public int getDefaultFractionDigits() {
        return NativeIntl.currencyFractionDigits(displayTag(null), code);
    }

    /**
     * Get the numeric code.
     * 
     * @return A numeric code.
     */
    public int getNumericCode() {
        if (code.equals("USD")) {
            return 840;
        }
        if (code.equals("EUR")) {
            return 978;
        }
        if (code.equals("JPY")) {
            return 392;
        }
        if (code.equals("GBP")) {
            return 826;
        }
        if (code.equals("CNY")) {
            return 156;
        }
        if (code.equals("KRW")) {
            return 410;
        }
        if (code.equals("CAD")) {
            return 124;
        }
        if (code.equals("AUD")) {
            return 36;
        }
        if (code.equals("INR")) {
            return 356;
        }
        if (code.equals("CHF")) {
            return 756;
        }
        return 0;
    }

    /**
     * Get the display tag.
     * 
     * @param locale A locale.
     * @return A display tag.
     */
    private static String displayTag(Locale locale) {
        Locale value = locale == null ? Locale.getDefault(Locale.Category.DISPLAY) : locale;
        String tag = value.toLanguageTag();
        return tag.isEmpty() || tag.equals("und") ? "en" : tag;
    }

    /**
     * Split the values.
     * 
     * @param text A text.
     * @return Values.
     */
    private static String[] split(String text) {
        int count = text.length() == 0 ? 0 : 1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ',') {
                count++;
            }
        }
        String[] values = new String[count];
        int index = 0;
        int start = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ',') {
                values[index++] = text.substring(start, i);
                start = i + 1;
            }
        }
        if (count > 0) {
            values[index] = text.substring(start, text.length());
        }
        return values;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof Currency currency && code.equals(currency.code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return code.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return code;
    }
}
