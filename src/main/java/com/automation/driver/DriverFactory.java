package com.automation.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DriverFactory {

    private static WebDriver driverInstance = null;
    private final static Logger LOGGER = Logger.getLogger(DriverFactory.class.getName());

    private DriverFactory(String browser) {
        switch (browser) {
            case ("Chrome"): {
                WebDriverManager.chromedriver().setup();
                driverInstance = new ChromeDriver();
                break;
            }
            case ("Firefox"): {
                WebDriverManager.firefoxdriver().setup();
                driverInstance = new FirefoxDriver();
                break;
            }
            default:
                LOGGER.log(Level.WARNING, "Could not initialize driver");
        }
    }

    public static WebDriver getDriverInstance(String browser) {
        if (driverInstance == null) {
            new DriverFactory(browser);
        }
        return driverInstance;
    }
}