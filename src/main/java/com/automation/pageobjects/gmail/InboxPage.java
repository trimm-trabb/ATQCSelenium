package com.automation.pageobjects.gmail;

import com.automation.pageobjects.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class InboxPage extends BasePage {

    @FindBy(className = "zA")
    List<WebElement> emailList;
    @FindBy(css = ".T-I.T-I-KE.L3")
    WebElement composeButton;
    By draftsLinkLocator = By.cssSelector("a[href$='drafts']");
    WebElement draftsLink;

    public InboxPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getLastEmails(int num) {
        waitForElementsToBeVisible(emailList);
        return emailList.subList(0, num);
    }

    public void printLastEmails(List<WebElement> emails) {
        for (WebElement e : emails) {
            String emailText = e.getText();
            String[] lines = emailText.split("\\r?\\n");
            System.out.println("Sender: " + lines[0] + " Title: " + lines[1]);
        }
    }

    public void clickComposeButton() {
        waitForElementToBeClickable(composeButton);
        composeButton.click();
    }

    public void clickDraftsLink() {
        draftsLink = findElement(draftsLinkLocator);
        draftsLink.click();
        wait.until(ExpectedConditions.urlContains("drafts"));
    }

    public boolean checkIfItemPresent(String subject) {
        boolean emailPresent = false;
        for (WebElement email : emailList) {
            if (email.getText().contains(subject)) {
                emailPresent = true;
                break;
            }
        }
        return emailPresent;
    }

    public void openEmail(String subject) {
        boolean emailPresent = false;
        for (WebElement email : emailList) {
            if (email.getText().contains(subject)) {
                email.click();
                emailPresent = true;
                break;
            }
        }
        if (!emailPresent)
            System.out.println("Email with subject *" + subject + "* does not exist");
    }
}