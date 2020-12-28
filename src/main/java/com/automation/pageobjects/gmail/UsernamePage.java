package com.automation.pageobjects.gmail;

import com.automation.pageobjects.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UsernamePage extends BasePage {

    @FindBy(css = "input[type='email']")
    WebElement usernameField;
    @FindBy(css = "#identifierNext")
    WebElement nextButton;

    public UsernamePage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String testUsername) {
        usernameField.sendKeys(testUsername);
    }

    public void clickNext() {
        nextButton.click();
    }
}