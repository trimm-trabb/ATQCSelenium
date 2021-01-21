package com.automation.pageobjects.gmail;

import com.automation.pageobjects.BasePage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class NewEmailPage extends BasePage {

    @FindBy(xpath = "//textarea[@name='to']")
    private WebElement toField;
    @FindBy(xpath = "//input[@name='subjectbox']")
    private WebElement subjectField;
    @FindBy(css = ".Ha")
    private WebElement closeButton;
    @FindBy(css=".oh.J-Z-I.J-J5-Ji.T-I-ax7")
    private WebElement deleteButton;


    public NewEmailPage(WebDriver driver) {
        super(driver);
    }

    public void createDraft(String to, String subject) {
        waitForElementsToBeVisible(toField, subjectField, closeButton);
        toField.sendKeys(to);
        subjectField.sendKeys(subject);
        closeButton.click();
    }

    public void editDraftSubject(String subject) {
        waitForElementToBeClickable(subjectField);
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.focus();");
        jse.executeScript("arguments[0].focus();", subjectField);
        subjectField.clear();
        subjectField.sendKeys(subject);
        closeButton.click();
    }

    public void deleteEmail() {
        waitForElementToBeClickable(deleteButton);
        deleteButton.click();
    }
}