package com.automation.dataproviders;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigFileReader {

    private Properties properties;
    private final static String fileName = "config.properties";
    private final static Logger LOGGER = Logger.getLogger(ConfigFileReader.class.getName());

    public ConfigFileReader() {
        ClassLoader classLoader = ConfigFileReader.class.getClassLoader();
        try {
            File file = new File(classLoader.getResource(fileName).getFile());
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                properties = new Properties();
                properties.load(reader);
            }
        } catch (NullPointerException | IOException ex) {
            LOGGER.log(Level.SEVERE, "Could not read configuration file " + fileName, ex);
        }
    }

    public String getUsername() {
        String username = properties.getProperty("username");
        if (username != null)
            return username;
        else
            throw new RuntimeException("Username not specified in the config.properties file");
    }

    public String getPassword() {
        String password = properties.getProperty("password");
        if (password != null)
            return password;
        else
            throw new RuntimeException("Password not specified in the config.properties file");
    }

    public String getGmailUrl() {
        String gmailUrl = properties.getProperty("gmailUrl");
        if (gmailUrl != null)
            return gmailUrl;
        else
            throw new RuntimeException("Gmail url time not specified in the config.properties file");
    }

    public int getWait() {
        String wait = properties.getProperty("wait");
        if (wait != null)
            return Integer.parseInt(wait);
        else
            throw new RuntimeException("Wait time not specified in the config.properties file");
    }

    public int getPollingTime() {
        String pollingTime = properties.getProperty("pollingTime");
        if (pollingTime != null)
            return Integer.parseInt(pollingTime);
        else
            throw new RuntimeException("Polling time not specified in the config.properties file");
    }
}