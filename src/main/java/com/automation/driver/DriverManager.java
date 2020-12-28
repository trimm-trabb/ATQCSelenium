package com.automation.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverManager {

    private static WebDriver driverInstance = null;

    private DriverManager() {
        WebDriverManager.chromedriver().setup();
        driverInstance = new ChromeDriver();
    }

    public static WebDriver getDriverInstance() {
        if (driverInstance == null) {
            new DriverManager();
        }
        return driverInstance;
    }
}