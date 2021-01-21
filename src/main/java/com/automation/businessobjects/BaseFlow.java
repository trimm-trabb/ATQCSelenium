package com.automation.businessobjects;

import com.automation.dataproviders.ConfigFileReader;
import org.openqa.selenium.WebDriver;

public class BaseFlow {

    protected WebDriver driver;
    protected ConfigFileReader reader;

    public BaseFlow(WebDriver driver) {
        this.driver = driver;
        reader = new ConfigFileReader();
    }
}
