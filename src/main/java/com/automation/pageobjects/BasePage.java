package com.automation.pageobjects;

import com.automation.dataproviders.ConfigFileReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected ConfigFileReader reader;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        reader = new ConfigFileReader();
        wait = new WebDriverWait(driver, reader.getWait());
        PageFactory.initElements(driver, this);
    }

    protected void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void waitForElementsToBeVisible(WebElement... elements) {
        wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    protected void waitForElementsToBeVisible(List<WebElement> elements) {
        wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }
}