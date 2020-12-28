package com.automation.pageobjects.gmail;

import com.automation.pageobjects.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PasswordPage extends BasePage {

    @FindBy(xpath = "//input[@name='password']")
    WebElement passwordField;
    @FindBy(id = "passwordNext")
    WebElement nextButton;

    public PasswordPage(WebDriver driver) {
        super(driver);
    }

    public void enterPassword(String testPassword) {
        waitForElementToBeClickable(passwordField);
        passwordField.sendKeys(testPassword);
    }

    public void clickNext() {
        waitForElementToBeClickable(nextButton);
        nextButton.click();
    }
}