package com.api.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigReader — Singleton utility class for reading
 * configuration properties from config.properties file.
 * Implements Singleton pattern to ensure single instance.
 */
public class ConfigReader {

    private static ConfigReader instance;
    private Properties properties;

    private ConfigReader() {
        properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(
                "src/main/resources/config.properties");
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException(
                "Failed to load config.properties: " 
                + e.getMessage());
        }
    }

    /**
     * Returns single instance of ConfigReader (Singleton)
     */
    public static ConfigReader getInstance() {
        if (instance == null) {
            synchronized (ConfigReader.class) {
                if (instance == null) {
                    instance = new ConfigReader();
                }
            }
        }
        return instance;
    }

    /**
     * Gets a property value by key
     */
    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException(
                "Property '" + key + "' not found in config.properties");
        }
        return value.trim();
    }

    /**
     * Gets base URI from config
     */
    public String getBaseURI() {
        return getProperty("baseURI");
    }

    /**
     * Gets username from config
     */
    public String getUsername() {
        return getProperty("username");
    }

    /**
     * Gets password from config
     */
    public String getPassword() {
        return getProperty("password");
    }

    /**
     * Gets environment from config
     */
    public String getEnvironment() {
        return getProperty("environment");
    }
}
