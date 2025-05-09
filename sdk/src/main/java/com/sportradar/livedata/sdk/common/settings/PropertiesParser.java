package com.sportradar.livedata.sdk.common.settings;

import ch.qos.logback.classic.Level;
import com.sportradar.livedata.sdk.common.exceptions.InvalidPropertyException;
import com.sportradar.livedata.sdk.common.exceptions.MissingPropertyException;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatterBuilder;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sportradar.livedata.sdk.common.classes.Nulls.checkNotNull;

public class PropertiesParser {

    private final Properties properties;


    public PropertiesParser(final Properties properties) {
        checkNotNull(properties);
        this.properties = properties;
    }

    public Set<Object> entrySet() {
        return properties.keySet();
    }

    public Boolean getBooleanProperty(String key) throws MissingPropertyException, InvalidPropertyException {
        return getBooleanProperty(key, false);
    }

    public Boolean getBooleanProperty(String key, boolean mandatory) throws MissingPropertyException, InvalidPropertyException {
        checkNotNull(key);
        String property = properties.getProperty(key);

        if (property == null) {
            if (mandatory) {
                throw new MissingPropertyException(key);
            }
            return null;
        }
        if (property.equalsIgnoreCase("true")) {
            return true;
        } else if (property.equalsIgnoreCase("false")) {
            return false;
        } else {
            throw new InvalidPropertyException("Invalid boolean property", key, property);
        }
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public Duration getDurationProperty(String key) throws MissingPropertyException {
        return getDurationProperty(key, false);
    }

    public Duration getDurationProperty(String key, boolean mandatory) throws MissingPropertyException {
        checkNotNull(key);
        String property = properties.getProperty(key);

        if (property == null) {
            if (mandatory) {
                throw new MissingPropertyException(key);
            }
            return null;
        }
        return parseDurationString(property);
    }

    private Duration parseDurationString(String input) {
        PeriodFormatterBuilder builder = new PeriodFormatterBuilder();
        builder.appendDays()
                .appendSeparator(":")
                .appendHours()
                .appendSeparator(":")
                .appendMinutes()
                .appendSeparator(":")
                .appendSeconds()
                .appendSeparator(".")
                .appendMillis();
        Period period = builder.toFormatter().parsePeriod(input);
        return new Duration(period.toStandardDuration());
    }

    public Integer getIntegerProperty(String key) throws MissingPropertyException, InvalidPropertyException {
        return getIntegerProperty(key, false);
    }

    public Integer getIntegerProperty(String key, boolean mandatory) throws InvalidPropertyException, MissingPropertyException {
        checkNotNull(key);
        String input = properties.getProperty(key);
        if (input == null) {
            if (mandatory) {
                throw new MissingPropertyException(key);
            }
            return null;
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidPropertyException(key, "Expecting int value", input);
        }
    }

    public Level getLevelProperty(String key) throws MissingPropertyException, InvalidPropertyException {
        return getLevelProperty(key, false);
    }

    public Level getLevelProperty(String key, boolean mandatory) throws InvalidPropertyException, MissingPropertyException {
        checkNotNull(key);
        String input = properties.getProperty(key);
        if (input == null) {
            if (mandatory) {
                throw new MissingPropertyException(key);
            }
            return null;
        }
        switch (input.toLowerCase()) {
            case "off":
                return Level.OFF;
            case "error":
                return Level.ERROR;
            case "warn":
                return Level.WARN;
            case "info":
                return Level.INFO;
            case "debug":
                return Level.DEBUG;
            case "trace":
                return Level.TRACE;
            case "all":
                return Level.ALL;
            default:
                throw new InvalidPropertyException(key, "Unrecognized level", input);
        }
    }

    public List<LimiterData> getLimitersProperty(String key) throws MissingPropertyException, InvalidPropertyException {
        return getLimitersProperty(key, false);
    }

    public List<LimiterData> getLimitersProperty(String key, boolean mandatory) throws InvalidPropertyException, MissingPropertyException {
        checkNotNull(key);
        String input = properties.getProperty(key);
        if (input == null) {
            if (mandatory) {
                throw new MissingPropertyException(key);
            }
            return null;
        }
        try {
            return parseLimiters(input);
        } catch (IllegalArgumentException e) {
            throw new InvalidPropertyException(key, e.getMessage(), input);
        }
    }

    public List<LimiterData> parseLimiters(String input) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile("\"(.*?)\"");
        Matcher matcher = pattern.matcher(input);
        List<LimiterData> limiters = new ArrayList<>();
        while (matcher.find()) {
            String[] values = matcher.group().replace("\"", "").split(";");
            if (values.length != 2) {
                throw new IllegalArgumentException("Should only contain limit and duration separated by semicolon");
            }
            int limit = Integer.parseInt(values[0].trim());
            Duration duration = parseDurationString(values[1].trim());
            limiters.add(new LimiterData(limit, duration, "customLimiter"));
        }

        return limiters;
    }

    public String getProperty(String key) throws MissingPropertyException {
        return getProperty(key, false);
    }

    public String getProperty(String key, boolean mandatory) throws MissingPropertyException {
        checkNotNull(key);
        String property = properties.getProperty(key);
        if (property == null && mandatory) {
            throw new MissingPropertyException(key);
        }
        return property;
    }

    public Set<String> parseCSVProperty(String key) throws MissingPropertyException {
        return parseCSVProperty(key, false);
    }

    public Set<String> parseCSVProperty(String key, boolean mandatory) throws MissingPropertyException {
        checkNotNull(key);
        String input = properties.getProperty(key);
        if (input == null) {
            if (mandatory) {
                throw new MissingPropertyException(key);
            }
            return null;
        }
        final String SEPARATOR = ",";
        String[] splitted = input.split(SEPARATOR);
        if (splitted.length == 0) {
            return new HashSet<>();
        }
        return new HashSet<>(Arrays.asList(splitted));
    }

    /**
     * Getting private key from the property and parses to {@link RSAPrivateKey}.
     *
     * @param key {@link String} key of the property
     * @return {@link RSAPrivateKey} parsed private key
     * @throws MissingPropertyException if property is missing
     * @throws InvalidPropertyException if could not parse the key
     */
    public RSAPrivateKey parsePrivateKey(String key) throws MissingPropertyException, InvalidPropertyException {
        String pkString = this.getProperty(key, true)
                .replaceAll("-{5}[\\w\\s]*-{5}|\\s", ""); // Removes headers, footers, and all whitespace
        try {
            byte[] decoded = Base64.getDecoder().decode(pkString);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
            return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(keySpec);
        } catch (Exception e) {
            throw new InvalidPropertyException(e.getMessage(), key, "***");
        }
    }
}
