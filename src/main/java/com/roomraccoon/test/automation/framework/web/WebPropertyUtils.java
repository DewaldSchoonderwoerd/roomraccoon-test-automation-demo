package com.roomraccoon.test.automation.framework.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import static com.roomraccoon.test.automation.framework.constants.Environments.QA;
import static com.roomraccoon.test.automation.framework.constants.Environments.STAGING;

public class WebPropertyUtils {

    // Logger for logging messages
    private static final Logger LOG = LoggerFactory.getLogger(WebPropertyUtils.class);

    // Properties object to store web URL properties
    private static Properties webUrlProperties = null;

    // Static initializer block to load web URL properties when the class is loaded
    static {
        LOG.info("Loading mandatory web properties...");
        webUrlProperties = loadProperties(basePath() + "webUrl.properties");
    }

    // Method to retrieve a specific web URL based on the provided key
    public static String getWebUrlDetails(String key) {
        return webUrlProperties.getProperty(key);
    }

    // Method to construct and retrieve a web URL based on the key and environment
    public static String getWebUrl(String key, String environment) {
        if (key.isEmpty()) {
            LOG.info("Empty URL initiated");
            return "";
        } else if (key.contains("https")) {
            LOG.info("Complete URL " + key);
            return key;
        } else if (environment.equalsIgnoreCase(STAGING)) {
            key = "staging." + key;
        } else if (environment.equalsIgnoreCase(QA)) {
            key = "qa." + key;
        } else {
            throw new WebAutomationException("Invalid url Key");
        }
        String url = getWebUrlDetails(key);
        if (url == null) {
            LOG.error(key + " not found in webUrl.properties file");
        }
        return url;
    }

    // Method to load properties from a file path
    public static Properties loadProperties(String filePath) {
        Properties properties = null;
        try {
            properties = new Properties();
            properties.load(new FileReader(new File(filePath)));
        } catch (Exception e) {
            properties.clear();
            throw new WebAutomationException(filePath + " not found");
        }
        return properties;
    }

    // Method to get the base path for resources
    private static String basePath() {
        return System.getProperty("user.dir") + "/src/main/resources/";
    }
}