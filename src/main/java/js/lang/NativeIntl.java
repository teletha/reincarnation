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

/**
 * The JavaScript Intl API.
 */
@Native
public final class NativeIntl {

    /**
     * Hide constructor.
     */
    private NativeIntl() {
    }

    /**
     * Canonicalize the locale tag.
     * 
     * @param tag A locale tag.
     * @return A canonical locale tag.
     */
    @Native("Intl.getCanonicalLocales($0)[0]")
    public static String canonicalize(String tag) {
        return null;
    }

    /**
     * Canonicalize the locale tag or return null.
     * 
     * @param tag A locale tag.
     * @return A canonical locale tag or null.
     */
    @Native("(() => { try { return Intl.getCanonicalLocales($0)[0]; } catch (e) { return null; } })()")
    public static String canonicalizeOrNull(String tag) {
        return null;
    }

    /**
     * Get the default locale.
     * 
     * @return A locale tag.
     */
    @Native("typeof Intl === 'undefined' ? 'en' : Intl.DateTimeFormat().resolvedOptions().locale")
    public static String defaultLocale() {
        return null;
    }

    /**
     * Get the short name of the system time zone.
     * 
     * @param millis Epoch milliseconds.
     * @return A short name or null.
     */
    @Native("typeof Intl === 'undefined' ? null : (() => { const p = new Intl.DateTimeFormat(undefined, {timeZoneName: 'short'}).formatToParts(new Date(Number($0))); const v = p.find(x => x.type === 'timeZoneName'); return v ? v.value : null; })()")
    public static String systemZoneShort(long millis) {
        return null;
    }

    /**
     * Get the display name.
     * 
     * @param locale A display locale.
     * @param type A display type.
     * @param code A code.
     * @return A display name.
     */
    @Native("(() => { try { return new Intl.DisplayNames([$0], {type: $1}).of($2); } catch (e) { return $2; } })()")
    public static String displayName(String locale, String type, String code) {
        return null;
    }

    /**
     * Compare the texts.
     * 
     * @param locale A locale tag.
     * @param left A left text.
     * @param right A right text.
     * @return A compared result.
     */
    @Native("typeof Intl === 'undefined' ? ($1 < $2 ? -1 : $1 > $2 ? 1 : 0) : new Intl.Collator($0).compare($1, $2)")
    public static int compare(String locale, String left, String right) {
        return 0;
    }

    /**
     * Compare the texts with the sensitivity.
     * 
     * @param locale A locale tag.
     * @param left A left text.
     * @param right A right text.
     * @param sensitivity A sensitivity.
     * @return A compared result.
     */
    @Native("typeof Intl === 'undefined' ? ($1 < $2 ? -1 : $1 > $2 ? 1 : 0) : new Intl.Collator($0, {sensitivity: $3}).compare($1, $2)")
    public static int collatorCompare(String locale, String left, String right, String sensitivity) {
        return 0;
    }

    /**
     * Check whether the currency is valid.
     * 
     * @param currency A currency code.
     * @return A result.
     */
    @Native("(() => { try { new Intl.NumberFormat('en', {style: 'currency', currency: $0}); return true; } catch (e) { return false; } })()")
    public static boolean validCurrency(String currency) {
        return false;
    }

    /**
     * Get the currency symbol.
     * 
     * @param locale A locale tag.
     * @param currency A currency code.
     * @return A currency symbol.
     */
    @Native("(() => { try { const p = new Intl.NumberFormat($0, {style: 'currency', currency: $1}).formatToParts(0); const v = p.find(x => x.type === 'currency'); return v ? v.value : $1; } catch (e) { return $1; } })()")
    public static String currencySymbol(String locale, String currency) {
        return null;
    }

    /**
     * Get the default fraction digits of the currency.
     * 
     * @param locale A locale tag.
     * @param currency A currency code.
     * @return Fraction digits.
     */
    @Native("(() => { try { return new Intl.NumberFormat($0, {style: 'currency', currency: $1}).resolvedOptions().maximumFractionDigits; } catch (e) { return 2; } })()")
    public static int currencyFractionDigits(String locale, String currency) {
        return 0;
    }

    /**
     * Format the number.
     * 
     * @param locale A locale tag.
     * @param value A value.
     * @return A formatted text.
     */
    @Native("typeof Intl === 'undefined' ? String(Number($1)) : new Intl.NumberFormat($0, {roundingMode: 'halfEven'}).format(Number($1))")
    public static String formatNumber(String locale, double value) {
        return null;
    }

    /**
     * Format the number with the options.
     * 
     * @param locale A locale tag.
     * @param value A value.
     * @param minimumFractionDigits Minimum fraction digits.
     * @param maximumFractionDigits Maximum fraction digits.
     * @param grouping Whether to use grouping.
     * @return A formatted text.
     */
    @Native("typeof Intl === 'undefined' ? String(Number($1)) : new Intl.NumberFormat($0, {minimumFractionDigits: Number($2), maximumFractionDigits: Number($3), useGrouping: $4, roundingMode: 'halfEven'}).format(Number($1))")
    public static String formatNumber(String locale, double value, int minimumFractionDigits, int maximumFractionDigits, boolean grouping) {
        return null;
    }

    /**
     * Format the number as a currency.
     * 
     * @param locale A locale tag.
     * @param currency A currency code.
     * @param value A value.
     * @return A formatted text.
     */
    @Native("typeof Intl === 'undefined' ? String(Number($2)) : new Intl.NumberFormat($0, {style: 'currency', currency: $1, roundingMode: 'halfEven'}).format(Number($2))")
    public static String formatCurrency(String locale, String currency, double value) {
        return null;
    }

    /**
     * Format the number as a currency with the options.
     * 
     * @param locale A locale tag.
     * @param currency A currency code.
     * @param value A value.
     * @param minimumFractionDigits Minimum fraction digits.
     * @param maximumFractionDigits Maximum fraction digits.
     * @param grouping Whether to use grouping.
     * @return A formatted text.
     */
    @Native("typeof Intl === 'undefined' ? String(Number($2)) : new Intl.NumberFormat($0, {style: 'currency', currency: $1, minimumFractionDigits: Number($3), maximumFractionDigits: Number($4), useGrouping: $5, roundingMode: 'halfEven'}).format(Number($2))")
    public static String formatCurrency(String locale, String currency, double value, int minimumFractionDigits, int maximumFractionDigits, boolean grouping) {
        return null;
    }

    /**
     * Format the number as a percentage.
     * 
     * @param locale A locale tag.
     * @param value A value.
     * @return A formatted text.
     */
    @Native("typeof Intl === 'undefined' ? String(Number($1)) : new Intl.NumberFormat($0, {style: 'percent', roundingMode: 'halfEven'}).format(Number($1))")
    public static String formatPercent(String locale, double value) {
        return null;
    }

    /**
     * Format the number as a percentage with the options.
     * 
     * @param locale A locale tag.
     * @param value A value.
     * @param minimumFractionDigits Minimum fraction digits.
     * @param maximumFractionDigits Maximum fraction digits.
     * @param grouping Whether to use grouping.
     * @return A formatted text.
     */
    @Native("typeof Intl === 'undefined' ? String(Number($1)) : new Intl.NumberFormat($0, {style: 'percent', minimumFractionDigits: Number($2), maximumFractionDigits: Number($3), useGrouping: $4, roundingMode: 'halfEven'}).format(Number($1))")
    public static String formatPercent(String locale, double value, int minimumFractionDigits, int maximumFractionDigits, boolean grouping) {
        return null;
    }

    /**
     * Format the date.
     * 
     * @param locale A locale tag.
     * @param dateStyle A date style.
     * @param timeStyle A time style.
     * @param millis Epoch milliseconds.
     * @return A formatted text.
     */
    @Native("typeof Intl === 'undefined' ? new Date(Number($3)).toString() : new Intl.DateTimeFormat($0, {dateStyle: $1 || undefined, timeStyle: $2 || undefined}).format(new Date(Number($3)))")
    public static String formatDate(String locale, String dateStyle, String timeStyle, long millis) {
        return null;
    }

    /**
     * Get the supported values.
     * 
     * @param key A value key.
     * @return A comma separated values.
     */
    @Native("typeof Intl === 'undefined' || typeof Intl.supportedValuesOf !== 'function' ? '' : Intl.supportedValuesOf($0).join(',')")
    public static String supportedValues(String key) {
        return null;
    }
}
