package com.automation.businessobjects.gmail;

import com.automation.businessobjects.BaseFlow;
import com.automation.pageobjects.gmail.InboxPage;
import com.automation.pageobjects.gmail.NewEmailPage;
import org.openqa.selenium.WebDriver;

public class CreateDraftFlow extends BaseFlow {

    public CreateDraftFlow(WebDriver driver) {
        super(driver);
    }

    public void createDraft(String testSubject) {
        InboxPage inboxObj = new InboxPage(driver);
        inboxObj.clickComposeButton();
        NewEmailPage emailObj = new NewEmailPage(driver);
        emailObj.createDraft(reader.getUsername(), testSubject);
    }
}
