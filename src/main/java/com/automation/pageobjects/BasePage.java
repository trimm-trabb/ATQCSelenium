package com.automation.pageobjects;

import com.automation.dataproviders.ConfigFileReader;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Wait<WebDriver> fWait;
    protected ConfigFileReader reader;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        reader = new ConfigFileReader();
        wait = new WebDriverWait(driver, reader.getWait());
        PageFactory.initElements(driver, this);
        fWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(reader.getWait()))
                .pollingEvery(Duration.ofMillis(reader.getPollingTime()))
                .ignoring(StaleElementReferenceException.class);
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

    protected WebElement findElement(By locator) {
        return fWait.until(driver -> driver.findElement(locator));
    }
}