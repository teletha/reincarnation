/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.locale;

import java.text.Collator;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for {@link Locale}.
 */
class LocaleTest extends JavascriptCodeVerifier {

    @Test
    void constants() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return Locale.ENGLISH.getLanguage() + "|" + Locale.US.toString() + "|" + Locale.US.toLanguageTag() + "|" + Locale.ROOT.toString() + "|" + Locale.ROOT.toLanguageTag() + "|" + Locale.JAPAN.getCountry() + "|" + Locale.UK.getDisplayCountry(Locale.ENGLISH);
            }
        });
    }

    @Test
    void languageTag() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Locale locale = Locale.forLanguageTag("zh-Hant-TW");
                return locale.getLanguage() + "|" + locale.getScript() + "|" + locale.getCountry() + "|" + locale.getVariant() + "|" + locale.toString() + "|" + locale.toLanguageTag();
            }
        });
    }

    @Test
    void constructor() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Locale locale = new Locale("EN", "us", "POSIX");
                return locale.getLanguage() + "|" + locale.getCountry() + "|" + locale.getVariant() + "|" + locale.toString() + "|" + locale.toLanguageTag();
            }
        });
    }

    @Test
    void displayName() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Locale display = Locale.ENGLISH;
                return Locale.US.getDisplayName(display) + "|" + Locale.JAPAN.getDisplayName(display) + "|" + Locale.forLanguageTag("zh-Hant-TW").getDisplayName(display) + "|" + Locale.ENGLISH.getDisplayLanguage(display) + "|" + Locale.US.getDisplayCountry(display) + "|" + Locale.forLanguageTag("zh-Hant-TW").getDisplayScript(display);
            }
        });
    }

    @Test
    void japaneseDisplayName() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return Locale.ENGLISH.getDisplayLanguage(Locale.JAPANESE) + "|" + Locale.US.getDisplayCountry(Locale.JAPANESE);
            }
        });
    }

    @Test
    void equality() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Locale created = Locale.forLanguageTag("en-US");
                return created.equals(Locale.US) + "|" + (created.hashCode() == Locale.US.hashCode() ? "same" : "diff") + "|" + Locale.US.equals(Locale.UK);
            }
        });
    }

    @Test
    void isoCodes() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return Locale.getISOLanguages().length + "|" + Locale.getISOCountries().length + "|" + Locale.getISOLanguages()[0] + "|" + Locale.getISOCountries()[0];
            }
        });
    }

    @Test
    void defaultLocale() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                Locale previous = Locale.getDefault();
                try {
                    Locale.setDefault(Locale.FRANCE);
                    return Locale.getDefault().equals(Locale.FRANCE);
                } finally {
                    Locale.setDefault(previous);
                }
            }
        });
    }

    @Test
    void currency() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Currency usd = Currency.getInstance("USD");
                return usd.getCurrencyCode() + "|" + usd.getSymbol(Locale.US) + "|" + usd.getDisplayName(Locale.ENGLISH) + "|" + usd.getDefaultFractionDigits() + "|" + usd.getNumericCode() + "|" + Currency.getInstance("JPY").getDefaultFractionDigits() + "|" + Currency.getInstance(Locale.JAPAN).getCurrencyCode() + "|" + Currency.getInstance("USD").equals(usd);
            }
        });
    }

    @Test
    void collator() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Collator collator = Collator.getInstance(Locale.ENGLISH);
                int forward = collator.compare("a", "b");
                int same = collator.compare("a", "a");
                int backward = collator.compare("b", "a");
                collator.setStrength(Collator.PRIMARY);
                int primary = collator.compare("a", "A");
                return forward + "|" + same + "|" + backward + "|" + (Collator.getInstance(Locale.ENGLISH).equals(collator) ? "same" : "diff") + "|" + primary;
            }
        });
    }

    @Test
    void numberFormat() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NumberFormat number = NumberFormat.getInstance(Locale.US);
                NumberFormat integer = NumberFormat.getIntegerInstance(Locale.US);
                NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
                NumberFormat percent = NumberFormat.getPercentInstance(Locale.US);
                NumberFormat german = NumberFormat.getInstance(Locale.GERMANY);
                NumberFormat yen = NumberFormat.getCurrencyInstance(Locale.JAPAN);
                number.setGroupingUsed(false);
                return number.format(1234.5) + "|" + NumberFormat.getInstance(Locale.US).format(1234567.891) + "|" + german.format(1234.5) + "|" + integer.format(1234.5) + "|" + currency.format(1234.5) + "|" + percent.format(0.1234) + "|" + yen.format(1234.5);
            }
        });
    }

    @Test
    void dateFormat() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Date date = new Date(0L);
                return DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US).format(date) + "|" + DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.US).format(date) + "|" + DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.US).format(date) + "|" + DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).format(date) + "|" + DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.JAPAN).format(date);
            }
        });
    }
}
