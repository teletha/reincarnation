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
import reincarnation.classlib.java.lang.Cloneable;

/**
 * The Java implementation of {@link java.util.Locale} which is transpiled into JavaScript.
 */
public final class Locale implements Cloneable, Serializable {

    /** The root locale. */
    public static final Locale ROOT = new Locale("", "", "");

    /** English. */
    public static final Locale ENGLISH = new Locale("en", "");

    /** French. */
    public static final Locale FRENCH = new Locale("fr", "");

    /** German. */
    public static final Locale GERMAN = new Locale("de", "");

    /** Italian. */
    public static final Locale ITALIAN = new Locale("it", "");

    /** Japanese. */
    public static final Locale JAPANESE = new Locale("ja", "");

    /** Chinese. */
    public static final Locale CHINESE = new Locale("zh", "");

    /** Korean. */
    public static final Locale KOREAN = new Locale("ko", "");

    /** United States. */
    public static final Locale US = new Locale("en", "US");

    /** United Kingdom. */
    public static final Locale UK = new Locale("en", "GB");

    /** Canada. */
    public static final Locale CANADA = new Locale("en", "CA");

    /** Canada French. */
    public static final Locale CANADA_FRENCH = new Locale("fr", "CA");

    /** France. */
    public static final Locale FRANCE = new Locale("fr", "FR");

    /** Germany. */
    public static final Locale GERMANY = new Locale("de", "DE");

    /** Italy. */
    public static final Locale ITALY = new Locale("it", "IT");

    /** Japan. */
    public static final Locale JAPAN = new Locale("ja", "JP");

    /** China. */
    public static final Locale CHINA = new Locale("zh", "CN");

    /** PRC. */
    public static final Locale PRC = new Locale("zh", "CN");

    /** Taiwan. */
    public static final Locale TAIWAN = new Locale("zh", "TW");

    /** Simplified Chinese. */
    public static final Locale SIMPLIFIED_CHINESE = new Locale("zh", "CN");

    /** Traditional Chinese. */
    public static final Locale TRADITIONAL_CHINESE = new Locale("zh", "TW");

    /** Korea. */
    public static final Locale KOREA = new Locale("ko", "KR");

    /** The language. */
    private final String language;

    /** The script. */
    private final String script;

    /** The country. */
    private final String country;

    /** The variant. */
    private final String variant;

    /** The canonical language tag. */
    private final String tag;

    /** The default display locale. */
    private static Locale defaultDisplay;

    /** The default format locale. */
    private static Locale defaultFormat;

    /**
     * Create the locale.
     * 
     * @param language A language.
     */
    public Locale(String language) {
        this(language, "", "");
    }

    /**
     * Create the locale.
     * 
     * @param language A language.
     * @param country A country.
     */
    public Locale(String language, String country) {
        this(language, country, "");
    }

    /**
     * Create the locale.
     * 
     * @param language A language.
     * @param country A country.
     * @param variant A variant.
     */
    public Locale(String language, String country, String variant) {
        if (language == null || country == null || variant == null) {
            throw new NullPointerException();
        }
        this.language = language.toLowerCase();
        this.script = "";
        this.country = country.toUpperCase();
        this.variant = variant;
        this.tag = null;
    }

    /**
     * Create the locale.
     * 
     * @param language A language.
     * @param script A script.
     * @param country A country.
     * @param variant A variant.
     * @param tag A canonical language tag.
     */
    private Locale(String language, String script, String country, String variant, String tag) {
        this.language = language;
        this.script = script;
        this.country = country;
        this.variant = variant;
        this.tag = tag;
    }

    /**
     * Get the locale of the language tag.
     * 
     * @param tag A language tag.
     * @return A locale.
     */
    public static Locale forLanguageTag(String tag) {
        if (tag == null) {
            throw new NullPointerException("languageTag");
        }
        if (tag.isEmpty()) {
            return ROOT;
        }
        String canonical = NativeIntl.canonicalizeOrNull(tag);
        if (canonical == null || canonical.isEmpty() || canonical.equals("und")) {
            return ROOT;
        }
        String[] parts = split(canonical, '-');
        String language = parts[0].toLowerCase();
        String script = "";
        String country = "";
        String variant = "";
        for (int i = 1; i < parts.length; i++) {
            String part = parts[i];
            if (part.length() == 1) {
                if (part.equals("x") && i + 2 < parts.length && parts[i + 1].equals("lvariant")) {
                    variant = parts[i + 2];
                }
                break;
            }
            if (script.isEmpty() && part.length() == 4 && isAlpha(part)) {
                script = part.substring(0, 1).toUpperCase() + part.substring(1).toLowerCase();
            } else if (country.isEmpty() && ((part.length() == 2 && isAlpha(part)) || (part.length() == 3 && isNumeric(part)))) {
                country = part.toUpperCase();
            } else if (variant.isEmpty()) {
                variant = part;
            } else {
                variant = variant + "_" + part;
            }
        }
        return new Locale(language, script, country, variant, canonical);
    }

    /**
     * Get the default locale.
     * 
     * @return A locale.
     */
    public static Locale getDefault() {
        if (defaultDisplay == null) {
            defaultDisplay = forLanguageTag(NativeIntl.defaultLocale());
        }
        return defaultDisplay;
    }

    /**
     * Get the default locale of the category.
     * 
     * @param category A category.
     * @return A locale.
     */
    public static Locale getDefault(Category category) {
        if (category == Category.FORMAT) {
            if (defaultFormat == null) {
                defaultFormat = forLanguageTag(NativeIntl.defaultLocale());
            }
            return defaultFormat;
        }
        return getDefault();
    }

    /**
     * Set the default locale.
     * 
     * @param locale A locale.
     */
    public static void setDefault(Locale locale) {
        if (locale == null) {
            throw new NullPointerException("newLocale");
        }
        defaultDisplay = locale;
        defaultFormat = locale;
    }

    /**
     * Set the default locale of the category.
     * 
     * @param category A category.
     * @param locale A locale.
     */
    public static void setDefault(Category category, Locale locale) {
        if (locale == null) {
            throw new NullPointerException("newLocale");
        }
        if (category == Category.FORMAT) {
            defaultFormat = locale;
        } else {
            defaultDisplay = locale;
        }
    }

    /**
     * Get the available locales.
     * 
     * @return Locales.
     */
    public static Locale[] getAvailableLocales() {
        String[] languages = getISOLanguages();
        Locale[] locales = new Locale[languages.length + 12];
        int index = 0;
        locales[index++] = US;
        locales[index++] = UK;
        locales[index++] = CANADA;
        locales[index++] = CANADA_FRENCH;
        locales[index++] = FRANCE;
        locales[index++] = GERMANY;
        locales[index++] = ITALY;
        locales[index++] = JAPAN;
        locales[index++] = CHINA;
        locales[index++] = TAIWAN;
        locales[index++] = KOREA;
        locales[index++] = ROOT;
        for (int i = 0; i < languages.length; i++) {
            locales[index++] = new Locale(languages[i], "");
        }
        return locales;
    }

    /**
     * Get the ISO languages.
     * 
     * @return Languages.
     */
    public static String[] getISOLanguages() {
        return split(LANGUAGES, ',');
    }

    /**
     * Get the ISO countries.
     * 
     * @return Countries.
     */
    public static String[] getISOCountries() {
        return split(COUNTRIES, ',');
    }

    /**
     * Get the language.
     * 
     * @return A language.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Get the script.
     * 
     * @return A script.
     */
    public String getScript() {
        return script;
    }

    /**
     * Get the country.
     * 
     * @return A country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Get the variant.
     * 
     * @return A variant.
     */
    public String getVariant() {
        return variant;
    }

    /**
     * Check whether this locale is the root locale.
     * 
     * @return A result.
     */
    public boolean hasExtensions() {
        return false;
    }

    /**
     * Strip the extensions.
     * 
     * @return A locale.
     */
    public Locale stripExtensions() {
        return this;
    }

    /**
     * Get the language tag.
     * 
     * @return A language tag.
     */
    public String toLanguageTag() {
        if (tag != null && !tag.isEmpty()) {
            return tag;
        }
        if (language.isEmpty()) {
            return "und";
        }
        StringBuilder builder = new StringBuilder(language);
        if (!script.isEmpty()) {
            builder.append('-').append(script);
        }
        if (!country.isEmpty()) {
            builder.append('-').append(country);
        }
        if (!variant.isEmpty()) {
            if (script.isEmpty() && variant.length() == 4 && isAlpha(variant)) {
                builder.append("-x-lvariant-").append(variant);
            } else {
                builder.append('-').append(variant);
            }
        }
        return builder.toString();
    }

    /**
     * Get the display name.
     * 
     * @return A display name.
     */
    public String getDisplayName() {
        return getDisplayName(getDefault(Category.DISPLAY));
    }

    /**
     * Get the display name.
     * 
     * @param displayLocale A display locale.
     * @return A display name.
     */
    public String getDisplayName(Locale displayLocale) {
        if (language.isEmpty()) {
            return "";
        }
        String languageName = getDisplayLanguage(displayLocale);
        StringBuilder builder = new StringBuilder();
        if (!script.isEmpty()) {
            builder.append(getDisplayScript(displayLocale));
        }
        if (!country.isEmpty()) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(getDisplayCountry(displayLocale));
        }
        if (!variant.isEmpty()) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(getDisplayVariant(displayLocale));
        }
        return builder.length() == 0 ? languageName : languageName + " (" + builder + ")";
    }

    /**
     * Get the display language.
     * 
     * @return A display language.
     */
    public String getDisplayLanguage() {
        return getDisplayLanguage(getDefault(Category.DISPLAY));
    }

    /**
     * Get the display language.
     * 
     * @param displayLocale A display locale.
     * @return A display language.
     */
    public String getDisplayLanguage(Locale displayLocale) {
        if (language.isEmpty()) {
            return "";
        }
        String name = NativeIntl.displayName(displayTag(displayLocale), "language", language);
        return name == null ? language : name;
    }

    /**
     * Get the display script.
     * 
     * @return A display script.
     */
    public String getDisplayScript() {
        return getDisplayScript(getDefault(Category.DISPLAY));
    }

    /**
     * Get the display script.
     * 
     * @param displayLocale A display locale.
     * @return A display script.
     */
    public String getDisplayScript(Locale displayLocale) {
        if (script.isEmpty()) {
            return "";
        }
        String name = NativeIntl.displayName(displayTag(displayLocale), "script", script);
        return name == null ? script : name;
    }

    /**
     * Get the display country.
     * 
     * @return A display country.
     */
    public String getDisplayCountry() {
        return getDisplayCountry(getDefault(Category.DISPLAY));
    }

    /**
     * Get the display country.
     * 
     * @param displayLocale A display locale.
     * @return A display country.
     */
    public String getDisplayCountry(Locale displayLocale) {
        if (country.isEmpty()) {
            return "";
        }
        String name = NativeIntl.displayName(displayTag(displayLocale), "region", country);
        return name == null ? country : name;
    }

    /**
     * Get the display variant.
     * 
     * @return A display variant.
     */
    public String getDisplayVariant() {
        return getDisplayVariant(getDefault(Category.DISPLAY));
    }

    /**
     * Get the display variant.
     * 
     * @param displayLocale A display locale.
     * @return A display variant.
     */
    public String getDisplayVariant(Locale displayLocale) {
        if (variant.isEmpty()) {
            return "";
        }
        if (variant.equals("POSIX")) {
            return "Computer";
        }
        return variant;
    }

    /**
     * Get the display tag.
     * 
     * @param displayLocale A display locale.
     * @return A display tag.
     */
    private static String displayTag(Locale displayLocale) {
        Locale locale = displayLocale == null ? getDefault(Category.DISPLAY) : displayLocale;
        String value = locale.toLanguageTag();
        return value.isEmpty() || value.equals("und") ? "en" : value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object clone() {
        return new Locale(language, script, country, variant, tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Locale)) {
            return false;
        }
        Locale locale = (Locale) other;
        return language.equals(locale.language) && script.equals(locale.script) && country.equals(locale.country) && variant.equals(locale.variant);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return (language.toLowerCase() + script.toLowerCase() + country.toLowerCase() + variant).hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if (language.isEmpty() && script.isEmpty() && country.isEmpty() && variant.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder(language);
        if (!country.isEmpty()) {
            builder.append('_').append(country);
        }
        if (!script.isEmpty()) {
            builder.append(variant.isEmpty() ? "_#" : "_").append(script);
        }
        if (!variant.isEmpty()) {
            builder.append('_').append(variant);
        }
        return builder.toString();
    }

    /**
     * Split the text.
     * 
     * @param text A text.
     * @param separator A separator.
     * @return Parts.
     */
    private static String[] split(String text, char separator) {
        int count = text.length() == 0 ? 0 : 1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == separator) {
                count++;
            }
        }
        String[] values = new String[count];
        int index = 0;
        int start = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == separator) {
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
     * Check whether the text is alphabetic.
     * 
     * @param text A text.
     * @return A result.
     */
    private static boolean isAlpha(String text) {
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check whether the text is numeric.
     * 
     * @param text A text.
     * @return A result.
     */
    private static boolean isNumeric(String text) {
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    /** The ISO 639-1 language codes. */
    private static final String LANGUAGES =
        "aa,ab,ae,af,ak,am,an,ar,as,av,ay,az,ba,be,bg,bh,bi,bm,bn,bo,"
        +         "br,bs,ca,ce,ch,co,cr,cs,cu,cv,cy,da,de,dv,dz,ee,el,en,eo,es,"
        +         "et,eu,fa,ff,fi,fj,fo,fr,fy,ga,gd,gl,gn,gu,gv,ha,he,hi,ho,hr,"
        +         "ht,hu,hy,hz,ia,id,ie,ig,ii,ik,in,io,is,it,iu,iw,ja,ji,jv,ka,"
        +         "kg,ki,kj,kk,kl,km,kn,ko,kr,ks,ku,kv,kw,ky,la,lb,lg,li,ln,lo,"
        +         "lt,lu,lv,mg,mh,mi,mk,ml,mn,mo,mr,ms,mt,my,na,nb,nd,ne,ng,nl,"
        +         "nn,no,nr,nv,ny,oc,oj,om,or,os,pa,pi,pl,ps,pt,qu,rm,rn,ro,ru,"
        +         "rw,sa,sc,sd,se,sg,si,sk,sl,sm,sn,so,sq,sr,ss,st,su,sv,sw,ta,"
        +         "te,tg,th,ti,tk,tl,tn,to,tr,ts,tt,tw,ty,ug,uk,ur,uz,ve,vi,vo,"
        +         "wa,wo,xh,yi,yo,za,zh,zu";

    /** The ISO 3166-1 country codes. */
    private static final String COUNTRIES =
        "AD,AE,AF,AG,AI,AL,AM,AO,AQ,AR,AS,AT,AU,AW,AX,AZ,BA,BB,BD,BE,"
        +         "BF,BG,BH,BI,BJ,BL,BM,BN,BO,BQ,BR,BS,BT,BV,BW,BY,BZ,CA,CC,CD,"
        +         "CF,CG,CH,CI,CK,CL,CM,CN,CO,CR,CU,CV,CW,CX,CY,CZ,DE,DJ,DK,DM,"
        +         "DO,DZ,EC,EE,EG,EH,ER,ES,ET,FI,FJ,FK,FM,FO,FR,GA,GB,GD,GE,GF,"
        +         "GG,GH,GI,GL,GM,GN,GP,GQ,GR,GS,GT,GU,GW,GY,HK,HM,HN,HR,HT,HU,"
        +         "ID,IE,IL,IM,IN,IO,IQ,IR,IS,IT,JE,JM,JO,JP,KE,KG,KH,KI,KM,KN,"
        +         "KP,KR,KW,KY,KZ,LA,LB,LC,LI,LK,LR,LS,LT,LU,LV,LY,MA,MC,MD,ME,"
        +         "MF,MG,MH,MK,ML,MM,MN,MO,MP,MQ,MR,MS,MT,MU,MV,MW,MX,MY,MZ,NA,"
        +         "NC,NE,NF,NG,NI,NL,NO,NP,NR,NU,NZ,OM,PA,PE,PF,PG,PH,PK,PL,PM,"
        +         "PN,PR,PS,PT,PW,PY,QA,RE,RO,RS,RU,RW,SA,SB,SC,SD,SE,SG,SH,SI,"
        +         "SJ,SK,SL,SM,SN,SO,SR,SS,ST,SV,SX,SY,SZ,TC,TD,TF,TG,TH,TJ,TK,"
        +         "TL,TM,TN,TO,TR,TT,TV,TW,TZ,UA,UG,UM,US,UY,UZ,VA,VC,VE,VG,VI,"
        +         "VN,VU,WF,WS,YE,YT,ZA,ZM,ZW";

    /**
     * The locale category.
     */
    public enum Category {

        /** The display category. */
        DISPLAY,

        /** The format category. */
        FORMAT
    }
}
