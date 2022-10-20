/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.feed.common.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Localized string container where a string is represented by its international value and
 * a collection of translations to other languages.
 */
public class LocalizedString implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(LocalizedString.class);
    private static final long serialVersionUID = 2298500399338632521L;
    private String internationalValue;
    private Map<String, String> translations;


    /**
     * Constructs a new localized string.
     *
     * @param internationalValue International value
     */
    public LocalizedString(String internationalValue) {
        if (internationalValue == null) {
            throw new IllegalArgumentException("internationalValue");
        }

        this.internationalValue = internationalValue.intern();
        translations = new HashMap<>(16);
    }

    /**
     * For Serializable
     */
    protected LocalizedString() {

    }


    /**
     * Adds a new translation to the string.
     * Overrides the previous language translations if existed.
     *
     * @param lang        Language code
     * @param translation Translation for this language
     */
    public void addTranslation(String lang, String translation) {
        if (lang == null || lang.isEmpty()) {
            throw new IllegalArgumentException("lang");
        }

        synchronized (translations) {
            if (translations.containsKey(lang)) {
                logger.warn(String.format("Overwriting translation for lang %1$s to %2$s", lang, translation));
            }

            translations.put(lang.intern(), translation.intern());
        }
    }

    /**
     * Check for equality.
     * <p>
     * Note : Compares by the international value only.
     * </p>
     *
     * @param other other object
     * @return true when equal; false else
     */
    public boolean equals(LocalizedString other) {
        return other != null && this.internationalValue.equals(other.internationalValue);
    }

    /**
     * Available language codes this instance is translated to.
     * For instance "de", "en" and so on.
     * <p>
     * Note: Might be empty, then you still have the international name
     * </p>
     * @return all available language codes
     */
    public Set<String> getAvailableTranslationLanguages() {
        synchronized (translations) {
            return Collections.unmodifiableSet(translations.keySet());
        }
    }

    /**
     * Returns international string.
     * @return international value
     */
    public String getInternational() {
        return internationalValue;
    }

    /**
     * Returns translation for the specified language.
     * Falls back to international string if translation does not exist.
     *
     * @param lang Language code (null for international string)
     * @return Translation for the specified language.
     */
    public String getTranslation(String lang) {
        synchronized (translations) {
            if (lang != null && !lang.isEmpty() && translations.containsKey(lang)) {
                return translations.get(lang);
            } else {
                return getInternational();
            }
        }
    }

    /**
     * Serves as a hash function for a particular type.
     *
     * @return Hash for current object
     */
    @Override
    public int hashCode() {
        return internationalValue.hashCode();
    }

    /**
     * Check for equality.
     *
     * @param obj other object
     * @return true when equal; false else
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof LocalizedString) {
            return equals((LocalizedString) obj);
        } else {
            return super.equals(obj);
        }
    }

    /**
     * Returns international string, if available.
     *
     * @return International string, if available
     */
    @Override
    public String toString() {
        return getInternational();
    }
}
