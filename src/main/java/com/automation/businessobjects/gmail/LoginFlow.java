package com.automation.businessobjects.gmail;

import com.automation.businessobjects.BaseFlow;
import com.automation.pageobjects.gmail.PasswordPage;
import com.automation.pageobjects.gmail.UsernamePage;
import org.openqa.selenium.WebDriver;

public class LoginFlow extends BaseFlow {

    public LoginFlow(WebDriver driver) {
        super(driver);
    }

    public void login() {
        UsernamePage usernameObj = new UsernamePage(driver);
        usernameObj.enterUsername(reader.getUsername());
        usernameObj.clickNext();
        PasswordPage passwordObj = new PasswordPage(driver);
        passwordObj.enterPassword(reader.getPassword());
        passwordObj.clickNext();
    }
}
