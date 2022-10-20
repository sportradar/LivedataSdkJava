/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.classes;

import com.google.common.base.Strings;
import com.sportradar.sdk.common.interfaces.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public class SdkVersion implements Version {

    public final String PREFIX = "javasdk-";
    public final String DEFAULT_VERSION = "0.0.0.0";
    public final String POSTFIX = "";

    public final String ARTIFACT_ID = "sdk";
    public final String GROUP_ID = "com.sportradar.sdk";

    private final static Logger logger = LoggerFactory.getLogger(SdkVersion.class);

    public SdkVersion() {
        logger.info("SDK starting up, version {}", getVersion());
    }

    public String getVersion() {
        return String.format("%s%s%s", PREFIX, obtainVersion(), POSTFIX);
    }

    public synchronized String obtainVersion() {
        String version = "";

        // try to load from maven properties first
        try {
            Properties p = new Properties();
            InputStream is = getClass().getResourceAsStream(String.format("/META-INF/maven/%s/%s/pom.properties", GROUP_ID, ARTIFACT_ID));
            if (is != null) {
                p.load(is);
                version = p.getProperty("version", "");
                if(!Strings.isNullOrEmpty(version))
                {
                    return version;
                }
            }
        } catch (Exception e) {
//            logger.debug("Error getting version");
        }

        try {
            InputStream is = getClass().getResourceAsStream("/version/sdk.properties");
            Properties props = new Properties();
            props.load(is);
            is.close();
            version = props.getProperty("version");
            if(!Strings.isNullOrEmpty(version))
            {
                return version;
            }
        } catch (Exception e) {
//            logger.debug("Error getting version from sdk.properties");
        }

        // fallback to using Java API
        if (version.equals("")) {
            Package aPackage = getClass().getPackage();
            if (aPackage != null) {
                version = aPackage.getImplementationVersion();
                if (version == null) {
                    version = aPackage.getSpecificationVersion();
                }
            }
        }

        if (Strings.isNullOrEmpty((version))) {
            // we could not compute the version so use a blank
            version = DEFAULT_VERSION;
        }

        return version;
    }
}